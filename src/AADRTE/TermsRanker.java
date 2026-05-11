package AADRTE;
/*
 * @author Manar S. Fayyad
 * @email Manar_fayyad@hotmail.com
 * @alternateEmail mfayyad@gmail.com
 */
import java.io.File;
import java.util.StringTokenizer;
import java.util.Vector;


// TODO: Auto-generated Javadoc
// import java.lang.Math;
/**
 * The Class TermsRanker.
 */
public class TermsRanker {
	
	/** The m is number of documents in the corpus. */
	double M = 0; // number of documents in the corpus .
	
	/** The Ftc is frequency summation of all candidate terms within the corpus. */
	double Ftc = 0; // frequency summation of all candidate terms within the 
					// corpus.
	/** The domain name. */
					String domain = "";
	
	/** The document frequency of term  a is number of documents the term a appear in. */
	double docFreqA = 0; // number of documents the term a appear in
	
	/** The Termfreqad is frequency of term a over the target domain d. */
	double TermFreqAD = 0; // frequency of term a over the target domain d.
	
	/** The Termfreqadnot frequency of term a over the rest of corpus. */
	double TermFreqADnot = 0; // frequency of term a over the rest of corpus.
	
	/** The Dwa is the discriminative weight of term a. */
	double DWa = 0;
	
	/** The ACDwa is the average contextual discriminative weight of term a. */
	double ACDWa = 0;
	
	/** The term length. */
	int termLength = 0;
	
	/** The terms is the terms vector. */
	Vector<Vector<String>> terms;
	
	/** The dociter is  the document iteration of term a. */
	Vector<Vector<String>> docIter;
	
	/** The termiter is the term iteration in corpus. */
	Vector<Vector<String>> termIter;
	
	/** The domain list. */
	File[] domainList;
	
	/** The max token. */
	int maxToken = 0;
	
	/** The index  is the calling for term index retrieval class. */
	TermIndexRetreval index = new TermIndexRetreval();

	/**
	 * Instantiates a new terms ranker.
	 *
	 * @param domList the domain list
	 * @param courpusFiles the corpus files
	 * @param totalCandidateTermOcurance the total candidate term occurrence
	 */
	public TermsRanker(File[] domList, int courpusFiles,
			int totalCandidateTermOcurance) {
		System.out.println("Start a new ranker");
		domainList = domList;
		M = courpusFiles;
		Ftc = totalCandidateTermOcurance;
	}

	/**
	 * Acc is a method to calculate the adjusted contextual contribution.
	 *
	 * @param a is the term to be evaluated.
	 * @return the double
	 */
	private double ACC(String a) {
		double d = (ACDWa * ((Math.exp(1 - ((ACDWa + 1) / (DWa + 1))) * Math
				.exp(1 - (DWa + 1) / (ACDWa + 1))) / (log2((ACDWa + 1)
				/ (DWa + 1)) + 1)));
		// glopalcounter++;System.out.print(" |glopalcounter="+glopalcounter+"  ");
		// System.out.println("|5="+d);
		return d;
	}

	/**
	 * Acdw is a method to calculate  the average contextual discriminative weight of term a .
	 *
	 * @param a is the term to be evaluated.
	 * @return the double
	 */
	private double ACDW(String a) {
		double sum = 0.0;
		String c = "";
		StringTokenizer tokenizer = new StringTokenizer(a);
		double Ca = tokenizer.countTokens();
		if (Ca < 2) {
			sum = DW(a);
		} else {
			while (tokenizer.hasMoreTokens()) {
				c = tokenizer.nextToken().trim() + " ";
				sum += DW(c) * sim(a, c);
				// System.out.println("|4= Ca="+Ca +" Sim(a,c)="+sim(a,c) );
			}
		}
		double d = sum / Ca;
		// glopalcounter++;System.out.print(" |glopalcounter="+glopalcounter+"  ");
		// System.out.println("6="+d+" Ca="+Ca +" sum(Sim("+a+","+c+"))="+sum );
		return d;
	}

	/**
	 * Document frequency is a method to calculate the document frequencies of term a .
	 *
	 * @param a is the term to be evaluated.
	 * @return the int
	 */
	private int documentFrequency(String a) {
		StringTokenizer tokenizer = new StringTokenizer(a);
		int l = tokenizer.countTokens();

		int docItterSum = 0;
		for (int i = l - 1; i < terms.size(); i += maxToken) {
			Vector<String> termsVector = terms.elementAt(i);
			Vector<String> termsDocItter = docIter.elementAt(i);
			int indx = index.binarySerch(termsVector, a, 0,
					termsVector.size() - 1);
			if (indx > -1) {
				docItterSum += Integer.parseInt(termsDocItter.get(indx)
						.toString());
				// System.out.println("2)documentFrequency of "+a+" is = "+termsDocItter.get(indx));
			}
		}
		return docItterSum;
	}

	/**
	 * Domain not term frequency.
	 *
	 * @param a the term a
	 * @return the int
	 */
	private int domainNotTermFrequency(String a) {
		StringTokenizer tokenizer = new StringTokenizer(a);
		int l = tokenizer.countTokens();

		int termItterSum = 0;
		for (int i = 0; i < domainList.length; i++)
			if (!domainList[i].getName().equals(domain)) {
				int j = i * maxToken + l - 1;
				Vector<String> termsVector = terms.elementAt(j);
				Vector<String> termsItter = termIter.elementAt(j);
				int indx = index.binarySerch(termsVector, a, 0,
						termsVector.size() - 1);
				if (indx > -1) {
					termItterSum += Integer.parseInt(termsItter.get(indx)
							.toString());
					// System.out.println("4)domainNotTermFrequency of "+a+" is = "+termsItter.get(indx));
				}
			}
		return termItterSum;
	}

	/**
	 * Domain term frequency.
	 *
	 * @param a the term a
	 * @return the int
	 */
	private int domainTermFrequency(String a) {
		StringTokenizer tokenizer = new StringTokenizer(a);
		int l = tokenizer.countTokens();
		int termItterSum = 0;
		for (int i = 0; i < domainList.length; i++)
			if (domainList[i].getName().equals(domain)) {
				int j = i * maxToken + l - 1;
				Vector<String> termsVector = terms.elementAt(j);
				Vector<String> termsItter = termIter.elementAt(j);
				int indx = index.binarySerch(termsVector, a, 0,
						termsVector.size() - 1);
				if (indx > -1) {
					termItterSum += Integer.parseInt(termsItter.get(indx)
							.toString());
					// System.out.println("3)domainTermFrequency of "+a+" is = "+termsItter.get(indx));
				}
			}
		return termItterSum;
	}

	/**
	 * Dp is a method to calculate the domain prevalence for term a.
	 *
	 * @param a the term a
	 * @return the double
	 */
	private double DP(String a) {
		// Domain Prevalence for complex term a
		StringTokenizer tokenizer = new StringTokenizer(a);
		double d = 0;
		if (tokenizer.countTokens() > 1) {
			String h = tokenizer.nextToken().trim() + " ";
			d = (Math.log(domainTermFrequency(a) + 10) * DPh(h) * MF(a));
			// System.out.println("8)DP("+h+")="+DP(h));
		} else {
			d = (Math.log(domainTermFrequency(a) + 10) * Math
					.log(Ftc
							/ (domainTermFrequency(a) + domainNotTermFrequency(a))
							+ 10));
		}
		// glopalcounter++;System.out.print(" |glopalcounter="+glopalcounter+"  ");
		// System.out.println("10="+d+" |tokenizer.nextToken()="+a+" |"+domainTermFrequency(a)+MF(a));
		return d;
	}

	/**
	 * Dph is a method to calculate the domain prevalence for term header h .
	 *
	 * @param h the term header h
	 * @return the double
	 */
	private double DPh(String h) {
		double d = 0;
		d = (Math.log(domainTermFrequency(h) + 10) * Math
				.log(Ftc
						/ (domainTermFrequency(h) + domainNotTermFrequency(h))
						+ 10));
		// TODO Auto-generated method stub
		return d;
	}

	/**
	 * Dt is a method to calculate the domain tendency for term a.
	 *
	 * @param a the term a
	 * @return the double
	 */
	private double DT(String a) {

		double d = (log2(((domainTermFrequency(a) + 1) / (domainNotTermFrequency(a) + 1)) + 1));
		// glopalcounter++;System.out.print(" |glopalcounter="+glopalcounter+"  ");
		// System.out.println("12="+d+domainTermFrequency(a)+domainNotTermFrequency(a));
		return d;
	}

	/**
	 * Dw is a method to calculate the discriminative weight for term a.
	 *
	 * @param a the term a
	 * @return the double
	 */
	private double DW(String a) {
		double d = (DP(a) * DT(a));
		// glopalcounter++;System.out.print(" |glopalcounter="+glopalcounter+"  ");
		// System.out.println("9="+d);
		return d;
	}

	/**
	 * Log2.
	 *
	 * @param num the num
	 * @return the double
	 */
	private double log2(double num) {
		return (Math.log(num) / Math.log(2));
	}

	/**
	 * Mf is a method to calculate the modifier factor for term a.
	 *
	 * @param a the term a
	 * @return the double
	 */
	private double MF(String a) {
		double sumd = 0.0;
		double sumdnot = 0.0;
		String c = "";
		StringTokenizer tokenizer = new StringTokenizer(a);
		while (tokenizer.hasMoreTokens()) {
			c = tokenizer.nextToken();
			sumd += domainTermFrequency(c);
			sumdnot += domainNotTermFrequency(c);
		}
		double d = log2((sumd + 1) / (sumdnot + 1) + 1);
		// glopalcounter++;System.out.print(" |glopalcounter="+glopalcounter+"  ");
		// System.out.println("11="+d);
		return d;
	}

	/**
	 * Ngd is a method to calculate the normalized google distance between term a and term c .
	 *
	 * @param a the term a
	 * @param c the term c
	 * @return the double
	 */
	private double NGD(String a, String c) {
		double docFreqC = documentFrequency(c);
		double d = 0;
		d = ((Math.max(Math.log(docFreqA), Math.log(docFreqC)) - Math
				.log(documentFrequency(a))) / (Math.log(M) - Math.min(
				Math.log(docFreqA), Math.log(docFreqC))));
		// glopalcounter++;System.out.print(" |glopalcounter="+glopalcounter+"  ");
		// System.out.println("8)docFreqC="+docFreqC+" |NGD("+a+","+c+")="+d+" |docFreqA="+docFreqA);
		return d;
	}

	/**
	 * Rank is a method to calculate the total rank for term a.
	 *
	 * @param a the term a
	 * @param domain1 is the term domain name 
	 * @param termCandidate is the term candidate
	 * @param docIteration is the document iteration
	 * @param termIteration is the term iteration
	 * @return the double
	 */
	public double rank(String a, String domain1,
			Vector<Vector<String>> termCandidate,
			Vector<Vector<String>> docIteration,
			Vector<Vector<String>> termIteration) {
		// System.out.println("1)term="+a);
		terms = termCandidate;
		docIter = docIteration;
		termIter = termIteration;
		maxToken = terms.size() / domainList.length;

		// StringTokenizer tokenizer = new StringTokenizer(a);
		// termLength=tokenizer.countTokens();
		domain = domain1;
		docFreqA = documentFrequency(a);
		TermFreqAD = domainTermFrequency(a);
		TermFreqADnot = domainNotTermFrequency(a);

		DWa = DW(a);
		ACDWa = ACDW(a);

		double rank = TH(a);

		return rank;
	}

	/**
	 * Sim is a method to calculate the similarity between term a and term c .
	 *
	 * @param a the term a
	 * @param c the term c
	 * @return the double
	 */
	private double sim(String a, String c) {
		double theta = 1; // a constant for scaling the NGD normalized google
							// distance.
		double d = (1 - NGD(a, c) * theta);
		// glopalcounter++;System.out.print(" |glopalcounter="+glopalcounter+"  ");
		// System.out.println("7="+d+" NGD(a,c)="+NGD(a,c));
		return d;
	}

	/**
	 * Th is a method to calculate the termhood for term a.
	 *
	 * @param a the term a
	 * @return the double
	 */
	private double TH(String a) {
		double d = DWa + ACC(a);
		// glopalcounter++;System.out.print(" |glopalcounter="+glopalcounter+"  ");
		// System.out.println("|1="+d);
		return d;
	}

}
