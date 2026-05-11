package AADRTE;
/*
 * @author Shereen Khoja.
 * @modified by Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.IOException;
import java.util.Vector;


/**
 * The Class ModifiedLightStemmer.
 * 
 * this class is a modification for khoja stemmer also we modified the stemmer files 
 * to accept the Arabic letters only.
 *    
 */
public class ModifiedLightStemmer {
	
	/** The stemmer files. 
	 * this class is to load the stemmer files to a vector for checking the words
	 */
	LoadStemerFiles stemerFiles = new LoadStemerFiles("StemmerFiles");

	/**
	 * Instantiates a new modified light stemmer.
	 */
	public ModifiedLightStemmer() {
		// System.out.print(stemerFiles.stemerFilesVector);
	}

	// check and remove the definite article
	/**
	 * Check definite article.
	 *  this method return  the word removing the  definite article from it.
	 * @param word the word
	 * @return the string
	 */
	private String checkDefiniteArticle(String word) {
		String definiteArticle = "";
		String modifiedWord = "";
		Vector<?> definiteArticles = stemerFiles.stemerFilesVector.elementAt(0);
		// System.out.println("definiteArticles"+definiteArticles);

		// for every definite article in the list
		for (int i = 0; i < definiteArticles.size(); i++) {
			definiteArticle = ((String) definiteArticles.elementAt(i)).trim();
			// if the definite article was found
			if (definiteArticle.regionMatches(0, word, 0,
					definiteArticle.length())) {
				// remove the definite article
				modifiedWord = word.substring(definiteArticle.length(),
						word.length());
			}
		}
		// System.out.println("Curent word :"+ word
		// +"| Modified word :"+modifiedWord);
		if (modifiedWord.length() > 2)
			return modifiedWord;
		return word;

	}

	/**
	 * Format the word.
	 * this method apply all the preprocessing steps on the word 
	 *
	 * @param currentWord the current word
	 * @return the string
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public String formatTheWord(String currentWord) throws IOException {

		StringBuffer modifiedWord = new StringBuffer();

		// System.out.println("1Curent word :"+ currentWord
		// +"| Modified word :"+modifiedWord);

		if (removeNonLetter(currentWord, modifiedWord))
			currentWord = modifiedWord.toString(); // remove the characters that
													// aren't letters.
		modifiedWord.setLength(0);
		modifiedWord.append(checkDefiniteArticle(currentWord)); // remove the
																// DefiniteArticle
																// if exists.
		// System.out.println("3Curent word :"+ currentWord
		// +"| Modified word :"+modifiedWord.toString());

		Vector<?> v = stemerFiles.stemerFilesVector.elementAt(2);
		if (v.contains(currentWord.trim()))
			currentWord = "";
		else
			currentWord = modifiedWord.toString(); // Remove stop words.
			// System.out.println("4Curent word :"+ currentWord
			// +"| Modified word :"+modifiedWord);

		if (currentWord.length() < 2)
			currentWord = "";
		// System.out.println("5Curent word :"+ currentWord
		// +"| Modified word :"+modifiedWord);
		return currentWord;
	}

	// remove non-letters from the word
	/**
	 * Removes the non Arabic letter.
	 *
	 * @param currentWord the current word
	 * @param modifiedWord the modified word
	 * @return true, if successful
	 */
	private boolean removeNonLetter(String currentWord,
			StringBuffer modifiedWord) {
		boolean nonLetterFound = false;
		modifiedWord.setLength(0);
		Vector<?> arabicLetter = stemerFiles.stemerFilesVector.elementAt(1);
		// System.out.println("Punctuation"+noArabicLetter);

		// for every character in the current word, if it is a punctuation then
		// do nothing
		// otherwise, copy this character to the modified word
		for (int i = 0; i < currentWord.length(); i++) {
			if ((arabicLetter.contains(currentWord.substring(i, i + 1)))) {
				if (Character.isLetter(currentWord.charAt(i))) {
					modifiedWord.append(currentWord.substring(i, i + 1));
				}
			} else {
				nonLetterFound = true;
			}
		}

		return nonLetterFound;
	}

	// --------------------------------------------------------------------------

}