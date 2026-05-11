/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
package AADRTE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;


// TODO: Auto-generated Javadoc
/**
 * The Class VectorToFileWriter.
 */
public class VectorToFileWriter {

	/**
	 * Instantiates a new vector to file writer.
	 */
	public VectorToFileWriter() {
	}

	/**
	 * Write is a method to write the vector to a file.
	 *
	 * @param vector is the vector to be written to a file.
	 * @param name is the name of the file. 
	 */
	public void Write(Vector<String> vector, String name) {
		try {
			File file = new File(name);
			file.createNewFile();
			FileWriter fileWriter = new FileWriter(file);
			BufferedWriter fileBuffer = new BufferedWriter(fileWriter);
			for (int i = 0; i < vector.size(); i++) {
				fileBuffer.write(vector.get(i).toString());
				fileBuffer.newLine();
			}
			fileBuffer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}