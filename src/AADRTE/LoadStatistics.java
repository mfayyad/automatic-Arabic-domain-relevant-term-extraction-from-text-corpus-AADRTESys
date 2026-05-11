package AADRTE;
/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.File;
import java.io.IOException;
import java.util.Vector;


// TODO: Auto-generated Javadoc
/**
 * The Class LoadStatistics create the vector composed of vectors containing the statistic files.
 */
public class LoadStatistics {
	// String pathToStatisticFiles = "TermsAndItteration/";
	/** The terms contain the terms. */
	Vector<Vector<String>> terms = new Vector<Vector<String>>();
	
	/** The termsitter contains the term iteration over the corpus. */
	Vector<Vector<String>> termsitter = new Vector<Vector<String>>();
	
	/** The docitter contains the number of documents the term appears in. */
	Vector<Vector<String>> docitter = new Vector<Vector<String>>();
	
	/** The readfile a class to read the content of file line by line. */
	SingleLineFileToVectorReader readfile = new SingleLineFileToVectorReader();

	/**
	 * Instantiates a new load statistics.
	 *
	 * @param domains the domains
	 * @param maxTermLength the maximum term length
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public LoadStatistics(File[] domains, int maxTermLength) throws IOException {
		// create the vector composed of vectors containing the statistic files
		for (int i = 0; i < domains.length; i++) {
			for (int j = 0; j < maxTermLength; j++) {
				// System.out.println("Corpus_Statistix/termCandidate"+domains[i].getName().trim()+j+".txt");
				terms.addElement(readfile.read("Corpus_Statistix/termCandidate"
						+ domains[i].getName().trim() + j + ".txt"));
				termsitter.addElement(readfile
						.read("Corpus_Statistix/termIteration"
								+ domains[i].getName().trim() + j + ".txt"));
				docitter.addElement(readfile
						.read("Corpus_Statistix/DocIteration"
								+ domains[i].getName().trim() + j + ".txt"));
			}
		}
		// System.out.println( "read in statistic files successfully" );
	}
}
