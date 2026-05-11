package AADRTE;
/*
 * The Class StartRankingProcess.
 *
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class StartRankingProcess.
 */

public class StartRankingProcess {
	
	/** The rank vector. */
	Vector<Vector<String>> rankVector = new Vector<Vector<String>>();

	/**
	 * Instantiates a new start ranking process.
	 *
	 * @param corpus the corpus
	 * @param termCandidate the term candidate
	 * @param docIteration the doc iteration
	 * @param termIteration the term iteration
	 * @param maxTermLength the max term length
	 * @param courpusFiles the courpus files
	 * @param totalCandidateTermOcurance the total candidate term ocurance
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public StartRankingProcess(String corpus,
			Vector<Vector<String>> termCandidate,
			Vector<Vector<String>> docIteration,
			Vector<Vector<String>> termIteration, int maxTermLength,
			int courpusFiles, int totalCandidateTermOcurance)
			throws IOException {
		ListOfDomains domains = new ListOfDomains(corpus);
		TermsRanker ranker = new TermsRanker(domains.domainList, courpusFiles,
				totalCandidateTermOcurance);
		System.out.println("Start The ranking process " + " |courpusFiles"
				+ courpusFiles + " |totalCandidateTermOcurance"
				+ totalCandidateTermOcurance);
		//System.out.println("Manar1 "+ termCandidate.size());

		for (int i = 0; i < termCandidate.size(); i++) {
			File rankingFile = new File("RankResults/Ranking"
					+ domains.domainList[i / maxTermLength].getName().trim()
					+ (i - (i / maxTermLength)) + ".txt");
			rankingFile.createNewFile();
			BufferedWriter rankingFileBuffer = new BufferedWriter(
					new FileWriter(rankingFile));
			Vector<String> x = new Vector<String>();

			for (int j = 0; j < termCandidate.elementAt(i).size(); j++) {
				int rankresult = (int) ranker.rank(termCandidate.elementAt(i)
						.elementAt(j).toString(), domains.domainList[i
						/ maxTermLength].getName().toString(), termCandidate,
						docIteration, termIteration);
				x.addElement(Integer.toString(rankresult));
				rankingFileBuffer.write(Integer.toString(rankresult));
				rankingFileBuffer.newLine();
				// System.out.println("ranking "+domains.domainList[i/maxTermLength].getName().toString()+" |"+((Vector)termCandidate.elementAt(i)).get(j)+" Rnk valu="+rankresult);
			}
			rankVector.addElement(x);
			rankingFileBuffer.close();
		}
	}
}
