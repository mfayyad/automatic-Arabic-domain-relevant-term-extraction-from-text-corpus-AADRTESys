package AADRTE;
/*
 * @author Shereen Khoja.
 * @modified br Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Vector;


// TODO: Auto-generated Javadoc
/**
 * The Class SingleLineFileToVectorReader.
 * this class is to read the content of the file and add each line content as one element.
 */
public class SingleLineFileToVectorReader {
	
	/**
	 * Instantiates a new single line file to vector reader.
	 */
	public SingleLineFileToVectorReader() {
	}

	/**
	 * Read.
	 *  this method is to start reading the file and putting it in a vector. 
	 *
	 * @param fileName the file name
	 * @return the vector
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Vector<String> read(String fileName) throws IOException {
		System.out.println(fileName);
		Vector<String> vectorFromFile = new Vector<String>();
		File file = new File(fileName);
		FileInputStream fileInputStream = new FileInputStream(file);
		InputStreamReader inputStreamReader = new InputStreamReader(
				fileInputStream, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
		String part;
		while ((part = bufferedReader.readLine()) != null) {
			vectorFromFile.addElement(part);
		}
		vectorFromFile.trimToSize();
		bufferedReader.close();
		fileInputStream.close();
		return vectorFromFile;
	}

}
