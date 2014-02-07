package nl.kbresearch.europeana_newspapers.NerAnnotator.output;

import org.jsoup.nodes.Document;

/**
 * Output handler for the result of the NER process on a single ALTO document
 * 
 * @author rene
 * 
 */
public interface ResultHandler {

	/**
	 * Called at the beginning of the document
	 */
	public void startDocument();

	/**
	 * Called at the beginning of each ALTO textblock
	 */
	public void startTextBlock();

	/**
	 * called at the end of a line
	 * 
	 * @param hyphenated
	 *            true, if there is a hyphen at the end
	 */
	public void newLine(boolean hyphenated);

	/**
	 * Called for each token generated by the {@link nl.kbresearch.europeana_newspapers.NerAnnotator.TextElementsExtractor}
	 * after NER processing. This does not include the spaces between the
	 * elements.
	 * 
	 * @param wordid
	 *            the reference to the ALTO element.
	 * @param originalContent
	 *            the uncleaned content of the word. Useful to reconstruct the
	 *            original input.
	 * @param word
	 *            the input used for NER processing. This might include content
	 *            from the Element described by continuationid
	 * @param label
	 *            the label given by the NER processing. Null, if no label was
	 *            attached.
	 * @param continuationid
	 *            if not null, the element referenced by this is the second part
	 *            of the same "word".
	 */
	public void addToken(String wordid, String originalContent, String word,
			String label, String continuationid);

	/**
	 * Called at the end of a text block
	 */
	public void stopTextBlock();

	/**
	 * Called at the end of the document
	 */
	public void stopDocument();

	/**
	 * This is called in any case after the processing of the file.
	 */
	public void close();
	
	/**
	 * 
	 */
	public void globalShutdown();

	/**
	 * @param doc
	 */
	public void setAltoDocument(Document doc);
}
