/*
 * @author Manar S. Fayyad (2012)(uploded 2026)
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
package AADRTE;

import java.io.IOException;
import java.util.Vector;

/**
 * The Class AADRTE is the class that calls all the sup classes to perform a complete sequence of the model processes.
* This class contains the real sequence of events
* <br>1)StartTermCandidateExtractionProcess Generates the term candidate for each domain
* <br>2)LoadStatistics Loads the statistic files to statistics matrix.
* <br>3)StartRankingProcess Starts the ranking process depending on term hood method.
* <br>4)LoadRankresultsAndTerms loads the Saved ranking results.
* <br>5)TermDistriputionProcess Starts term distribution depending on the rank results.
* <br>6)LoadDistriputedDomainWords loads the distributed domain words into domain word vector.
* <br>7)TestingStage Starts the testing process for a document.
* <br>8)WriteAllDataToOneFile writes all the vectors to one file.   
*/
public class AADRTE {
	
	/** The corpus. */
	static String corpus = "corpus";
	
	/** The testing corpus. */
	static String testingCorpus = "Testing_corpus";
	
	/** The maximum term length. */
	static int maximumTermLength = 4; 


	/** The total docunment number. */
	int totalDocunmentNumber = 0;

	/** The total term candidate number. */
	int totalTermCandidateNumber = 0;

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 */
	public static void main(String[] args) {
		try {
			new AADRTE();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Instantiates a new aadrte.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws Exception
	 *             the exception
	 */
	public AADRTE() throws IOException, Exception {
// Loading the names of the domains from the corpus - each folder represents a domain.
		ListOfDomains domains = new ListOfDomains(corpus); 
// Start the process
// 1) Generating the term candidate for each domain
		new StartTermCandidateExtractionProcess(corpus, 
												maximumTermLength);
// 2) Load the statistic files to statistics matrix.
		LoadStatistics stat = new LoadStatistics(domains.domainList,
												 maximumTermLength); 
// 3) Load the total counters for documents and terms.
		SingleLineFileToVectorReader st = new SingleLineFileToVectorReader();
		Vector<String> stVector = st.read("Corpus_Statistix/stat.txt");
		totalDocunmentNumber = Integer.parseInt(stVector.elementAt(0));
		totalTermCandidateNumber = Integer.parseInt(stVector.elementAt(1));
// 4) Start the ranking process depending on Termhood method.
		new StartRankingProcess(corpus,	
								stat.terms, 
								stat.docitter,
								stat.termsitter, 
								maximumTermLength, 
								totalDocunmentNumber,
								totalTermCandidateNumber);
// 5) load the Saved ranking results.		
		LoadRankresultsAndTerms rankresult = new LoadRankresultsAndTerms(corpus, 
																		 maximumTermLength);
// 6) Start term distribution depending on the rank results.
		TermDistriputionProcess distripute = new TermDistriputionProcess(rankresult.rankVector, 
																		 rankresult.terms); 
		distripute.startDistripution(domains.domainList, 
									 maximumTermLength);
// 7) load the distributed domain words into domain word vector.		
		LoadDistriputedDomainWords domainWords = new LoadDistriputedDomainWords(corpus, 
																				maximumTermLength);
// 8) Start the testing process for a document
		new TestingStage(domainWords.list,
						 maximumTermLength, 
						 testingCorpus);
// 9) Write all data to one file 
		new writeAllDataToOneFile(domains.domainList);
//End the process
		System.out.println("End of the Experement");
	}
}
