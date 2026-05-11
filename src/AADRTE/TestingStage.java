/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
package AADRTE;

import java.io.IOException;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class TestingStage.
 */
public class TestingStage {

	/**
	 * Instantiates a new testing stage.
	 *
	 * @param domainWords the domain words
	 * @param maximumTermLength the maximum term length
	 * @param testingCorpus the testing corpus
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public TestingStage(Vector<Vector<String>> domainWords,
			int maximumTermLength, String testingCorpus) throws IOException {
		VectorToFileWriter writer = new VectorToFileWriter();
		ListOfDomains testdomains = new ListOfDomains(testingCorpus); // Loading
																		// the
																		// names
																		// of
																		// the
																		// domains
																		// from
																		// the
																		// corpus
																		// -
																		// each
																		// folder
																		// represents
																		// a
																		// domain.
		Vector<String> report = new Vector<String>();
		for (int i = 0; i < testdomains.domainList.length; i++) {
			Vector<String> classifyResult = new Vector<String>();
			ListOfFiles testing = new ListOfFiles(testingCorpus,
					testdomains.domainList[i].getName());
			int errorSum = 0;
			for (int j = 0; j < testing.files.length; j++) {
				ClassifyDocument classifier = new ClassifyDocument(domainWords,
						maximumTermLength);
				int d=classifier.classify("Testing_corpus/"
						+ testdomains.domainList[i].getName() + "/"
						+ testing.files[j].getName());
				if ( d == i) {
					classifyResult.addElement(Integer
							.toString(classifier.domRankMax));
//					 System.out.println("the classifier.domRankMax of classified is ("+classifier.domRankMax+") ");
				} else {
//					System.out.println(d+"real domain is :"+i);
					classifyResult.addElement("-1"+d+"real domain is :"+i);
					errorSum += 1;
				}
			}
			System.out.println("the number of wrongly classified is ("
					+ errorSum + ") from (" + testing.files.length + ") in ("
					+ testdomains.domainList[i].getName() + ") domain");
			report.addElement("the number of wrongly classified is ("
					+ errorSum + ") from (" + testing.files.length + ") in ("
					+ testdomains.domainList[i].getName() + ") domain");
			writer.Write(classifyResult, "Calssify_Results/"
					+ testdomains.domainList[i].getName() + ".txt");
		}
		writer.Write(report, "Calssify_Results/report.txt");

	}

}
