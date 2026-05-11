/*
 * The Class writeAllDataToOneFile.
 *
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */

package AADRTE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
// TODO: Auto-generated Javadoc
/**
 * The Class writeAllDataToOneFile.
 */
public class writeAllDataToOneFile {

	/**
	 * Instantiates a new write all data to one file.
	 *
	 * @param domainList the domain list
	 */
	public writeAllDataToOneFile(File[] domainList) {
		String name = "All Data.text";
		String termFileName = "";
		String docItterFileName = "";
		String termItterFileName = "";
		String rankFileName = "";

		try {
			File file = new File(name);
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter fileBuffer = new BufferedWriter(fileWriter);
			fileBuffer.write("Term,DocItter,TermItter,RankValu");
			fileBuffer.newLine();

			for (int i = 0; i < domainList.length; i++) {
				for (int j = 0; j < 4; j++) {
					// System.out.println("Corpus_Statistix/termCandidate"+domains[i].getName().trim()+j+".txt");
					termFileName = "Corpus_Statistix/termCandidate"
							+ domainList[i].getName().trim() + j + ".txt";
					termItterFileName = "Corpus_Statistix/termIteration"
							+ domainList[i].getName().trim() + j + ".txt";
					docItterFileName = "Corpus_Statistix/DocIteration"
							+ domainList[i].getName().trim() + j + ".txt";
					rankFileName = "RankResults/Ranking"
							+ domainList[i].getName().trim() + (i * 4 - i + j)
							+ ".txt";
					File term = new File(termFileName);
					File termItter = new File(termItterFileName);
					File docItter = new File(docItterFileName);
					File rank = new File(rankFileName);
					FileInputStream termInputStream = new FileInputStream(term);
					FileInputStream termItterInputStream = new FileInputStream(
							termItter);
					FileInputStream docItterInputStream = new FileInputStream(
							docItter);
					FileInputStream rankInputStream = new FileInputStream(rank);
					InputStreamReader termInputStreamReader = new InputStreamReader(
							termInputStream, "UTF-8");
					InputStreamReader termItterInputStreamReader = new InputStreamReader(
							termItterInputStream, "UTF-8");
					InputStreamReader docItterInputStreamReader = new InputStreamReader(
							docItterInputStream, "UTF-8");
					InputStreamReader rankInputStreamReader = new InputStreamReader(
							rankInputStream, "UTF-8");
					BufferedReader termInputStreambufferedReader = new BufferedReader(
							termInputStreamReader);
					BufferedReader termItterInputStreambufferedReader = new BufferedReader(
							termItterInputStreamReader);
					BufferedReader docItterInputStreambufferedReader = new BufferedReader(
							docItterInputStreamReader);
					BufferedReader rankInputStreambufferedReader = new BufferedReader(
							rankInputStreamReader);

					String termPart;
					String termItterPart;
					String docItterPart;
					String rankPart;
					while ((termPart = termInputStreambufferedReader.readLine()) != null) {
						termItterPart = termItterInputStreambufferedReader
								.readLine();
						docItterPart = docItterInputStreambufferedReader
								.readLine();
						rankPart = rankInputStreambufferedReader.readLine();
						fileBuffer.write(termPart);
						fileBuffer.write(",");
						fileBuffer.write(docItterPart);
						fileBuffer.write(",");
						fileBuffer.write(termItterPart);
						fileBuffer.write(",");
						fileBuffer.write(rankPart);
						fileBuffer.write(",");
						fileBuffer.write(domainList[i].getName().trim()
								.toString());
						fileBuffer.newLine();

					}
					termInputStreambufferedReader.close();
					termItterInputStreambufferedReader.close();
					docItterInputStreambufferedReader.close();
					rankInputStreambufferedReader.close();
					termInputStream.close();
					termItterInputStream.close();
					docItterInputStream.close();
					rankInputStream.close();
				}
			}

			fileBuffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	// TODO Auto-generated constructor stub
}
