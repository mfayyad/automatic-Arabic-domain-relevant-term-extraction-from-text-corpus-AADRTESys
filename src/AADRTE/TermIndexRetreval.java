package AADRTE;
/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class TermIndexRetreval.
 */
public class TermIndexRetreval {
	
	/**
	 * Instantiates a new term index retrieval.
	 */
	public TermIndexRetreval() {
		// System.out.println("Initializing the term index retrieval and add new element for the new terms");
	}

	/**
	 * Binary search.
	 *
	 * @param localVector the local vector
	 * @param term the term
	 * @param left the left
	 * @param right the right
	 * @return the int
	 */
	public int binarySerch(Vector<String> localVector, String term, int left,
			int right) {
		if (left > right)
			return -1;
		int middle = (left + right) / 2;
		if (localVector.elementAt(middle).toString().trim().equals(term.trim()))
			return middle;

		else if (localVector.elementAt(middle).trim().toString()
				.compareTo(term.trim()) < 0)
			return binarySerch(localVector, term, left, middle - 1);
		else
			return binarySerch(localVector, term, middle + 1, right);
	}

	// index retrieval and add new element for the new terms
	/**
	 * Binary search for insert retrieve index  and add new element for the new terms.
	 *
	 * @param termVector the term vector
	 * @param itterVector the iteration  vector
	 * @param docItterVector the doc iteration vector
	 * @param term the term
	 * @param termItter the term iteration
	 * @param docItter the doc iteration
	 * @param left the left
	 * @param right the right
	 * @return the int
	 */
	public int binarySerchForInsert(Vector<String> termVector,
			Vector<String> itterVector, Vector<String> docItterVector,
			String term, String termItter, String docItter, int left, int right) {
		if (left > right) {
			termVector.insertElementAt(term, left);
			itterVector.insertElementAt(termItter, left);
			docItterVector.insertElementAt(docItter, left);
			return -1;
		}

		int middle = (left + right) / 2;
		if (termVector.elementAt(middle).trim().toString().equals(term.trim()))
			return middle;

		else if (termVector.elementAt(middle).trim().toString()
				.compareTo(term.trim()) < 0)
			return binarySerchForInsert(termVector, itterVector,
					docItterVector, term, termItter, docItter, left, middle - 1);
		else
			return binarySerchForInsert(termVector, itterVector,
					docItterVector, term, termItter, docItter, middle + 1,
					right);
	}
}
