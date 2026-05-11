package AADRTE;
/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.Vector;

// TODO: Auto-generated Javadoc
/**
 * The Class StartTermCandidateExtractionProcess.
 * in this class we Extract the candidate terms for the corpus and counting the iteration for each term. 
 * also we count the number of the document the terms appear in.
 * all the previous statistics are saved to files. 
 */
public class StartTermCandidateExtractionProcess {
	
	/** The term candidate Vector. */
	Vector<Vector<String>> termCandidate = new Vector<Vector<String>>();
	
	/** The document iteration Vector. */
	Vector<Vector<String>> docIteration = new Vector<Vector<String>>();
	
	/** The term iteration Vector. */
	Vector<Vector<String>> termIteration = new Vector<Vector<String>>();
	
	/** The stemmed file vector. */
	Vector<Vector<String>> stemedfilevector = new Vector<Vector<String>>();
	
	/** The corpus number of files. */
	int corpusNumberOfFiles = 0;
	
	/** The term candidate occurrence. */
	int termCandidateOccurance = 0; // Summation of the all the candidate term
									// occurrence over the corpus.
	/** The file writer. */
	FileWriter fileWriter;
	
	/** The file buffer. */
	BufferedWriter fileBuffer;
	
	/** instantiate The stemmer. */
	ModifiedLightStemmer stemer = new ModifiedLightStemmer();
	
	/** instantiate The term index. */
	TermIndexRetreval termIndex = new TermIndexRetreval();
	
	/** instantiate The vector file writer. */
	VectorToFileWriter vectorFileWriter = new VectorToFileWriter();

	/**
	 * Instantiates a new start term candidate extraction process.
	 *
	 * @param corpus the corpus
	 * @param maxTokens the max tokens
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public StartTermCandidateExtractionProcess(String corpus, int maxTokens)
			throws IOException {
		ListOfDomains dom = new ListOfDomains(corpus);
		String StemmedWord = "";
		for (int i = 0; i < dom.domainList.length; i++) // loop for the folders
														// (Domains).
		{
			// System.out.println("start domain("+domainList[i].getName()+")");
			ListOfFiles domain = new ListOfFiles(corpus,
					dom.domainList[i].getName());
			corpusNumberOfFiles += domain.files.length;
			for (int tokensInTerm = 0; tokensInTerm < maxTokens; tokensInTerm++) // loop
																					// for
																					// tokens
																					// in
																					// the
																					// Domains.
			{
				termCandidate.addElement(new Vector<String>());
				docIteration.addElement(new Vector<String>());
				termIteration.addElement(new Vector<String>());
			}
			// progressBar.setMaximum(domain.files.length);
			try {
				File WordVectorfile = new File("Wordvector/"
						+ dom.domainList[i].getName() + ".txt");
				if (!WordVectorfile.exists())
					WordVectorfile.createNewFile();
				fileWriter = new FileWriter(WordVectorfile, true);
				fileBuffer = new BufferedWriter(fileWriter);
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int j = 0; j < domain.files.length; j++) // loop for the files
															// within the
															// domain.
			{
				// System.out.println("start processing file no("+j+") word vector length is:"+wordvector.size());
				SingleTokenFileReader file = new SingleTokenFileReader(corpus
						+ "/" + dom.domainList[i].getName() + "/"
						+ domain.files[j].getName());
				for (int tokensInTerm = 0; tokensInTerm < maxTokens; tokensInTerm++)
					stemedfilevector.addElement(new Vector<String>());
				for (int k = 0; k < file.fileVector.size(); k++) // loop for the
																	// tokens or
																	// words
																	// within
																	// the file.
				{
					StemmedWord = stemer.formatTheWord(file.fileVector.get(k)
							.toString()); // light stemming the word
					stemedfilevector.elementAt(0).addElement(StemmedWord);

					try {
						fileBuffer.write(StemmedWord.toString());
						fileBuffer.newLine();
					} catch (IOException e) {
						e.printStackTrace();
					}

					// wordvector.add(StemmedWord);
					// ======================================================================================================
					for (int tokensInTerm = 0; tokensInTerm < maxTokens; tokensInTerm++) {
						int vectorIndex = tokensInTerm;
						if (k > tokensInTerm - 1) {
							String term = "";
							for (int x = k - (tokensInTerm); x < k + 1; x++)
								term += stemedfilevector.elementAt(0).get(x)
										.toString().trim()
										+ " ";
							if (tokensInTerm > 0)
								stemedfilevector.elementAt(tokensInTerm)
										.addElement(term.trim());
							StringTokenizer tokenizer = new StringTokenizer(
									term);
							if (tokenizer.countTokens() - 1 == tokensInTerm) {
								termCandidateOccurance += 1;
								int indx = termIndex.binarySerchForInsert(
										termCandidate.elementAt(vectorIndex),
										termIteration.elementAt(vectorIndex),
										docIteration.elementAt(vectorIndex),
										term, "1", "0", 0,
										termCandidate.elementAt(vectorIndex)
												.size() - 1);
								if (indx > -1) {
									int x = Integer.parseInt(termIteration
											.elementAt(vectorIndex).get(indx)
											.toString());
									x += 1;
									String newValu = Integer.toString(x);
									termIteration.elementAt(vectorIndex).set(
											indx, newValu);
								}
								// System.out.println("domain="+i+" |Vector index="+vectorIndex+" |tokensInTerm="+tokensInTerm+" |term="+term);
							}
						}
					}
					// =========================================================================================================
				}
				for (int tokensInTerm = 0; tokensInTerm < maxTokens; tokensInTerm++) {
					int vectorIndex = tokensInTerm;
					UpdateDocItteration(vectorIndex);
				}
				stemedfilevector.clear();
			}
			try {
				fileBuffer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for (int l = 0; l < termCandidate.size(); l++) {
				// System.out.println("Vector number="+l+" |first term="+termCandidate.elementAt(l).get(0)+" |term iteration="+termIteration.elementAt(l).get(0)+" |doc iteration="+docIteration.elementAt(l).get(0));
				vectorFileWriter.Write(termCandidate.elementAt(l),
						"Corpus_Statistix/termCandidate"
								+ dom.domainList[i].getName().toString()
								+ (l - l / maxTokens) + ".txt");
				vectorFileWriter.Write(termIteration.elementAt(l),
						"Corpus_Statistix/termIteration"
								+ dom.domainList[i].getName().toString()
								+ (l - l / maxTokens) + ".txt");
				vectorFileWriter.Write(docIteration.elementAt(l),
						"Corpus_Statistix/DocIteration"
								+ dom.domainList[i].getName().toString()
								+ (l - l / maxTokens) + ".txt");
			}
			termCandidate.clear();
			termIteration.clear();
			docIteration.clear();

		}

		// System.out.println(termCandidate.size());
		Vector<String> stat = new Vector<String>();
		stat.addElement(Integer.toString(corpusNumberOfFiles));
		stat.addElement(Integer.toString(termCandidateOccurance));
		vectorFileWriter.Write(stat, "Corpus_Statistix/stat.txt");
	}

	/**
	 * Update doc iteration.
	 * this method increase the doc iteration for a term 
	 *
	 * @param vectorIndex the vector index
	 */
	private void UpdateDocItteration(int vectorIndex) {
		StringTokenizer tokenizer = new StringTokenizer(termCandidate
				.elementAt(vectorIndex).get(0).toString());
		int tokensInTerm = tokenizer.countTokens();
		for (int i = 0; i < termCandidate.elementAt(vectorIndex).size(); i++) {
			if (stemedfilevector.elementAt(tokensInTerm - 1).contains(
					termCandidate.elementAt(vectorIndex).get(i).toString()
							.trim())) {
				int x = Integer.parseInt(docIteration.elementAt(vectorIndex)
						.get(i).toString());
				x += 1;
				String newValu = Integer.toString(x);
				// System.out.println(((Vector)DocIteration.elementAt(vectorIndex)).get(i).toString()+" | "+newValu);
				docIteration.elementAt(vectorIndex).set(i, newValu);
			}
		}
	}
}