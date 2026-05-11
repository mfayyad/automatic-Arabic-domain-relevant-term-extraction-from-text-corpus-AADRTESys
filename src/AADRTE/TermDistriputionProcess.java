package AADRTE;
/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.File;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class TermDistriputionProcess.
 * a simple distribution process for distributing the terms over the domains depending on there rank value.
 */
public class TermDistriputionProcess {
	
	/** The ranking vector. */
	static Vector<Vector<String>> rankingVector = new Vector<Vector<String>>();
	
	/** The terms vector. */
	static Vector<Vector<String>> termsVector = new Vector<Vector<String>>();
	
	/** The domain words. */
	Vector<Vector<String>> domainWords = new Vector<Vector<String>>();
	
	/** The maxtoken. */
	int maxtoken = 0;
	
	/** The minrank is the minimum rank value that could be accepted for the term to be distributed. */
	int minRank = -1; // the minimum ranking value the is allowed to participate
						// in the distribution process
	/** instantiate The vector to file writer. */
		VectorToFileWriter writer = new VectorToFileWriter();
	
	/** instantiate The index retrievers. */
	TermIndexRetreval index = new TermIndexRetreval();

	/**
	 * Instantiates a new term distribution process.
	 *
	 * @param rankVector the rank vector
	 * @param termCandidate the term candidate
	 */
	public TermDistriputionProcess(Vector<Vector<String>> rankVector,
			Vector<Vector<String>> termCandidate) {
		// System.out.println("Starting the term Distribution process over the domains");
		rankingVector = rankVector;
		termsVector = termCandidate;
	}

	/**
	 * Distribute the vector.
	 *
	 * @param i is the number of the vector to be distributed  
	 * @param x is the vector contains the distributed terms
	 */
	private void distriputeTheVector(int i, Vector<String> x) {
		Vector<String> terms = termsVector.elementAt(i);
		for (int j = 0; j < terms.size(); j++) {
			int termRank = Integer.parseInt(rankingVector.elementAt(i)
					.elementAt(j).toString());
			if (termRank > minRank) {
				Boolean termRankLessThanOtheres = false;
				for (int k = 0; k < (termsVector.size()); k += maxtoken) {
					int indx = index.binarySerch(termsVector.elementAt(k),
							terms.elementAt(j).toString().trim(), 0,
							termsVector.elementAt(k).size() - 1);
					if (indx > -1) {
						int rnk = Integer.parseInt(rankingVector.elementAt(k)
								.get(indx).toString());
						if (rnk > termRank) {
							termRankLessThanOtheres = true;
						}
					}
				}
				if (!termRankLessThanOtheres)
					x.addElement(terms.elementAt(j));
			}
		}
	}

	/**
	 * Start distribution.
	 *
	 * @param domainList the domain list
	 * @param maxtokens the max tokens
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startDistripution(File[] domainList, int maxtokens)
			throws IOException {
		maxtoken = maxtokens;
		for (int i = 0; i < rankingVector.size(); i++) {
			System.out.println("DistriputedDomainTerms/"
					+ domainList[i / maxtokens].getName());
			Vector<String> x = new Vector<String>();
			distriputeTheVector(i, x);
			domainWords.addElement(x);
			StringTokenizer tokenizer = new StringTokenizer(x.get(0).toString());
			int termLength = tokenizer.countTokens();
			writer.Write(x, "DistriputedDomainTerms/"
					+ domainList[i / maxtokens].getName() + termLength
					+ "Words.txt");
		}
	}
}
