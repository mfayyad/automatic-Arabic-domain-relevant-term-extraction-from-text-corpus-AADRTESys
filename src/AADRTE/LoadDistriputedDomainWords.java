package AADRTE;
/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */

import java.io.IOException;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class LoadDistriputedDomainWords.
 * Reads the domain term matrix from file
 */
public class LoadDistriputedDomainWords {
	
	/** The list contains the domain term matrix. */
	Vector<Vector<String>> list = new Vector<Vector<String>>();
	
	/** The readfile a class to read the content of file line by line. */
	SingleLineFileToVectorReader readfile = new SingleLineFileToVectorReader();

	/**
	 * Instantiates a new load distributed domain words.
	 *
	 * @param corpus the corpus
	 * @param maximumTermLength the maximum term length
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public LoadDistriputedDomainWords(String corpus, int maximumTermLength)
			throws IOException {
		ListOfDomains domains = new ListOfDomains(corpus);
		for (int i = 0; i < domains.domainList.length; i++) {
			for (int j = 1; j < maximumTermLength + 1; j++) {
				// System.out.println("Corpus_Statistix/termCandidate"+domains[i].getName().trim()+j+".txt");
				list.addElement(readfile.read("DistriputedDomainTerms/"
						+ domains.domainList[i].getName() + j + "Words.txt"));
			}
		}

	}

}
