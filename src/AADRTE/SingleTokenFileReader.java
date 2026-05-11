package AADRTE;
/*
 * @author Shereen Khoja.
 * @modified by Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Vector;


// TODO: Auto-generated Javadoc
/**
 * The Class SingleTokenFileReader.
 * this class reads the content of document an put each token into a single item in a vector.
 */
public class SingleTokenFileReader {
	
	/** The file vector which contains all the tokens in the document . */
	Vector<String> fileVector = new Vector<String>();

	/**
	 * Instantiates a new single token file reader.
	 *
	 * @param inFile the in file
	 */
	public SingleTokenFileReader(String inFile) {
		if (!addVectorFromFile(inFile))
			System.out.println("Fail to convert the file to vector. ");
	}

	/**
	 * Adds the vector from file.
	 *
	 * @param fileName the file name
	 * @return true, if successful
	 */
	protected boolean addVectorFromFile(String fileName) {
		boolean returnValue;
		try {
			File file = new File(fileName);
			FileInputStream fileInputStream = new FileInputStream(file);
			InputStreamReader inputStreamReader = new InputStreamReader(
					fileInputStream, "UTF-8");
			BufferedReader bufferedReader = new BufferedReader(
					inputStreamReader, 20000); // If the bufferedReader is not
												// big enough for a file, I
												// should change the size of it
												// here

			// read in the text a line at a time
			String part;
			StringBuffer word = new StringBuffer();
			while ((part = bufferedReader.readLine()) != null) {
				// add spaces at the end of the line
				part = part + "  ";

				// for each line
				for (int index = 0; index < part.length() - 1; index++) {
					// if the character is not a space, append it to a word
					if (!(Character.isWhitespace(part.charAt(index)))) {
						word.append(part.charAt(index));
					}
					// otherwise, if the word contains some characters, add it
					// to the vector
					else {
						if (word.length() != 0) {
							fileVector.addElement(word.toString());
							word.setLength(0);
						}
					}
				}
			}

			// trim the vector
			fileVector.trimToSize();

			// destroy the buffered reader
			bufferedReader.close();
			fileInputStream.close();

			returnValue = true;
		} catch (Exception exception) {
			returnValue = false;
		}
		return returnValue;
	}

	// --------------------------------------------------------------------------

}