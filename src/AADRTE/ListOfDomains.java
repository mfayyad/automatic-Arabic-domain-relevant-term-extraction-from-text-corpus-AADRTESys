package AADRTE;
/*
 * The Class ListOfDomains return  .
 * 
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.File;
import java.io.FileFilter;

// TODO: Auto-generated Javadoc
/**
 * The Class ListOfDomains read the folders in the corpus as a domain list.
 */
public class ListOfDomains {
	
	/** The domain list contains the names of the domains from corpus folder. */
	File[] domainList = null;
	
	/** The file filter returns the directories only. */
	FileFilter fileFilter = new FileFilter() {
//		@Override
		public boolean accept(File file) {
			return file.isDirectory();
		}
	};

	/**
	 * Instantiates a new list of domains.
	 * 
	 * @param corpus
	 *            the corpus is the main folder name for the text documents in the corpus.
	 */
	public ListOfDomains(String corpus) {
		try {
			File folder = new File(corpus);
			domainList = folder.listFiles(fileFilter);
		} catch (Exception ex) {
			// System.out.println(" list of Domains "+ex.toString());
		}
	}

}
