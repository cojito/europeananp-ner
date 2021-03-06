package nl.kbresearch.europeana_newspapers.NerAnnotator.output;

import nl.kbresearch.europeana_newspapers.NerAnnotator.App;
import nl.kbresearch.europeana_newspapers.NerAnnotator.container.ContainerContext;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.io.PrintWriter;

import java.sql.SQLException;

/**
 * Generates a list of result handlers for the configured output formats on a
 * single ALTO document
 * 
 * @author Rene
 * @author Willem Jan Faber
 * 
 */
public class ResultHandlerFactory {
    static Map<Class<? extends ResultHandler>, ResultHandler> registeredHandlers=new LinkedHashMap<Class<? extends ResultHandler>, ResultHandler>();
    
    /**
     * @param context
     * @param name 
     * @param md5sum
     * @return array of ResultHandlers according to the configuration
     */
    public static ResultHandler[] createResultHandlers(final ContainerContext context, final String name, final String versionString, final PrintWriter out) {
        String[] outputFormats = App.getOutputFormats();
        ArrayList<ResultHandler> result = new ArrayList<ResultHandler>();
        
        
        if (outputFormats == null) {
            outputFormats = new String[1];
            outputFormats[0] = "http";
        }

        for (String outputFormat : outputFormats) {
            switch (outputFormat) {
                case "log":
                    LogResultHandler logResultHandler = new LogResultHandler();
                    registeredHandlers.put(LogResultHandler.class, logResultHandler);
                    result.add(logResultHandler);
                    break;
                case "csv":
                    CsvResultHandler csvResultHandler = new CsvResultHandler(context, name );
                    registeredHandlers.put(CsvResultHandler.class, csvResultHandler);
                    result.add(csvResultHandler);
                    break;
                case "alto":
                    AnnotatedAltoResultHandler annotatedAltoResultHandler = new AnnotatedAltoResultHandler(context, name, versionString);
                    registeredHandlers.put(AnnotatedAltoResultHandler.class, annotatedAltoResultHandler);
                    result.add(annotatedAltoResultHandler);
                    break;
                case "html":
                    HtmlResultHandler htmlResultHandler = new HtmlResultHandler(context, name);
                    registeredHandlers.put(HtmlResultHandler.class, htmlResultHandler);
                    result.add(htmlResultHandler);
                    break;
                case "alto2_1":
                    Alto2_1ResultHandler alto2_1ResultHandler = new Alto2_1ResultHandler(context, name, versionString);
                    registeredHandlers.put(Alto2_1ResultHandler.class, alto2_1ResultHandler);
                    result.add(alto2_1ResultHandler);
                    break;
                case "bio":
                    BioResultHandler bioResultHandler = new BioResultHandler(context, name);
                    registeredHandlers.put(BioResultHandler.class, bioResultHandler);
                    result.add(bioResultHandler);
                    break;
                case "http":
                    HttpResultHandler httpResultHandler = new HttpResultHandler(context, name, versionString, out);
                    registeredHandlers.put(HttpResultHandler.class, httpResultHandler);
                    result.add(httpResultHandler);
                    break;
                case "db":
                    try {
                        DbResultHandler dbResultHandler = new DbResultHandler(context, name );
                        registeredHandlers.put(DbResultHandler.class, dbResultHandler);
                        result.add(dbResultHandler);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    break;
                default: throw new IllegalArgumentException("Unknown output format: " + outputFormat);
            }
        }
        return result.toArray(new ResultHandler[result.size()]);
    }
    
    public static void shutdownResultHandlers() {
            for (@SuppressWarnings("rawtypes") Class c: registeredHandlers.keySet()) {
                    registeredHandlers.get(c).globalShutdown();
            }
    }
}
