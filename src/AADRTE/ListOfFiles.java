package AADRTE;
import java.io.File;
import java.io.FileFilter;

// TODO: Auto-generated Javadoc
/*
 * The Class ListOfFiles.
 *
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
// TODO: Auto-generated Javadoc
/**
 * The Class ListOfFiles.
 */

public class ListOfFiles {
	
	/** The files is an array contains the filed within the domain. */
	File[] files = null;
	
	/** The file filter This filter only returns files. */
	FileFilter fileFilter = new FileFilter() {
//		@Override
		public boolean accept(File file) {
			return file.isFile();
		}
	}; // This filter only returns files.

	/**
	 * Instantiates a new list of files.
	 *
	 * @param corpus the corpus
	 * @param domsinFolder the domains folders
	 */
	public ListOfFiles(String corpus, String domsinFolder) {
		try {
			String folderName = "";
			if (corpus == "")
				folderName = domsinFolder;
			else
				folderName = corpus + "/" + domsinFolder;
			File folder = new File(folderName);
			files = folder.listFiles(fileFilter);
		} catch (Exception ex) {
			// System.out.println(" list of file "+ex.toString());
		}
	}
}
