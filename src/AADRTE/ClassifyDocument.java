
package AADRTE;
/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class ClassifyDocument is used to classify the document within the domain folders in the Testing_corpus folder.
 * Each folder represent a domain.
 */
public class ClassifyDocument {
	
	/** The stemmer loads the modified stemmer to stem the words. */
	private ModifiedLightStemmer stemmer = new ModifiedLightStemmer();
	
	/** The index is a class retrieve the index of the word or term in the vector. */
	TermIndexRetreval index = new TermIndexRetreval();
	
	/** The domain terms is a vector that contains the domain term matrix loaded from file . */
	private Vector<Vector<String>> domainTerms = new Vector<Vector<String>>();
	
	/** The stemmed file vector is the vector that contains the stemmed word from the document that need to be classified. */
	private Vector<String> stemedFileVector = new Vector<String>();
	
	/** The max token is the maximum number of tokens allowed in the term. */
	int maxToken = 0;
	
	/** The domains number the number of the domains  . */
	int domainsNumber = 0;
	
	/** The dom is the indicator for the domain of the term. */
	int dom = -1;
	
	/** The dom rank max stores the maximum rank value for the term. */
	int domRankMax = 0;

	/**
	 * Instantiates a new classify document.
	 *
	 * @param domainwords is the domain term matrix vector. 
	 * @param maxTerm is the maximum number of words in the term.  
	 */
	public ClassifyDocument(Vector<Vector<String>> domainwords, int maxTerm) {
		domainTerms = domainwords;
		maxToken = maxTerm;
		domainsNumber = domainwords.size() / maxTerm;
		// System.out.println("start the Document Classifier)");
	}

	/**
	 * Classify .
	 *
	 * @param fileName the file name
	 * @return the integer value for the domain of the classified document 
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public int classify(String fileName) throws IOException {

		SingleTokenFileReader file = new SingleTokenFileReader(fileName);
		for (int i = 0; i < file.fileVector.size() - maxToken; i++)
			stemedFileVector.addElement(stemmer.formatTheWord(
					file.fileVector.elementAt(i)).trim());
		domRankMax = 0;
		for (int d = 0; d < domainTerms.size(); d += maxToken) {
			int domRank = 0;

			for (int i = 0; i < stemedFileVector.size() - maxToken; i++) {
				String term = "";
				for (int j = i; j < i + maxToken; j++) {
					term += stemedFileVector.get(j).toString().trim() + " ";
					StringTokenizer tokenizer = new StringTokenizer(term);
					if (tokenizer.countTokens() > (j - i)) {
						int indx = index.binarySerch(
								domainTerms.elementAt(d + (j - i)), term, 0,
								domainTerms.elementAt(d + (j - i)).size() - 1);
						if (indx > -1)
							domRank += 1;
					}
				}
			}
			if (domRank > domRankMax) {
				dom = d / maxToken;
				domRankMax = domRank;

			}
		}
		return dom;
	}

}
