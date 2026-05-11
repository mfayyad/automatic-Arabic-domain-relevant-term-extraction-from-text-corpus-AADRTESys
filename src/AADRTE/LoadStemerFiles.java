package AADRTE;
/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.util.Vector;


// TODO: Auto-generated Javadoc
/**
 * The Class LoadStemerFiles.
 * load the files that will be participate in the Preprocessing  stage 
 * like diacritics, definite articles, and stop words. 
 */
public class LoadStemerFiles {
	
	/** The stemmer files vector. */
	Vector<Vector<String>> stemerFilesVector = new Vector<Vector<String>>();

	/**
	 * Instantiates a new load stemmer files.
	 *
	 * @param stemerFilesDirectory the stemmer files directory
	 */
	public LoadStemerFiles(String stemerFilesDirectory) {
		ListOfFiles stemerfiles = new ListOfFiles("", stemerFilesDirectory);
		for (int i = 0; i < stemerfiles.files.length; i++) {
			SingleTokenFileReader file = new SingleTokenFileReader(
					stemerFilesDirectory + "/" + stemerfiles.files[i].getName());
			stemerFilesVector.addElement(file.fileVector);
		}
		// System.out.println( "read in files successfully" );
	}
}
