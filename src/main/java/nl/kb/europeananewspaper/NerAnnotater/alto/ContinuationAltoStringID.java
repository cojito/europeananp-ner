package nl.kb.europeananewspaper.NerAnnotater.alto;

import edu.stanford.nlp.ling.CoreAnnotation;

/**
 * Tagger class for the id of the the ALTO element that is the second-part of a
 * combined element.
 * 
 * @author rene
 * 
 */
public class ContinuationAltoStringID implements CoreAnnotation<String> {

	public Class<String> getType() {
		return String.class;
	}

}