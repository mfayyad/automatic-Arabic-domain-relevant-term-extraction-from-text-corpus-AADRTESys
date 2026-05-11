package AADRTE;
import java.io.IOException;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/*
 * The Class LoadRankresultsAndTerms.
 *
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
/**
 * The Class LoadRankresultsAndTerms for loading the rank results from rank files in the rankrResults directory.
 *
 */
public class LoadRankresultsAndTerms {
	// String pathToStatisticFiles = "TermsAndItteration/";
	/** The terms is a vector for storing the candidate terms. */
	Vector<Vector<String>> terms = new Vector<Vector<String>>();
	
	/** The rank vector is a vector contains the rank values for the terms. */
	Vector<Vector<String>> rankVector = new Vector<Vector<String>>();
	
	/** The readfile a class to read the content of file line by line. */
	SingleLineFileToVectorReader readfile = new SingleLineFileToVectorReader();

	/**
	 * Instantiates a new load rankresults and terms.
	 *
	 * @param corpus the corpus
	 * @param maxTermLength the maximum term length
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public LoadRankresultsAndTerms(String corpus, int maxTermLength)
			throws IOException {
		ListOfDomains domains = new ListOfDomains(corpus);
		for (int i = 0; i < domains.domainList.length; i++) {
			for (int j = 0; j < maxTermLength; j++) {
				// System.out.println("Corpus_Statistix/termCandidate"+domains[i].getName().trim()+j+".txt");
				terms.addElement(readfile.read("Corpus_Statistix/termCandidate"
						+ domains.domainList[i].getName().trim() + j + ".txt"));
			}
		}
		for (int i = 0; i < terms.size(); i++) {
			rankVector.addElement(readfile.read("RankResults/Ranking"
					+ domains.domainList[i / maxTermLength].getName().trim()
					+ (i - (i / maxTermLength)) + ".txt"));
		}
		System.out.println("read in rank results files successfully");
	}
}
