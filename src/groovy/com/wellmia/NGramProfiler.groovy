package com.wellmia

/**
 * Created by IntelliJ IDEA.
 * User: Michael Green
 * Date: 12/18/10
 * Time: 1:49 PM
 * To change this template use File | Settings | File Templates.
 */
class NGramProfiler {

  /** separator char */
  public static final String SEPARATOR = '_'
  public static final int MAXNGRAMLENGTH = 5
  public static final int MINNGRAMLENGTH = 1
  public static final double DEFAULT_BLANK_WEIGHT = 0.0
  public static final int DEFAULT_LENGTH_LIMIT = 3

  private Map<String, Integer> ngramCounts
  private String content
  private int minNGramLength
  private int maxNGramLength

  NGramProfiler (String content) {
    this.content = content
    this.ngramCounts = new HashMap<String, Integer>()
    minNGramLength = MINNGRAMLENGTH
    maxNGramLength = MAXNGRAMLENGTH
  }

  public Map<String, Integer> createProfile() {
    StringBuffer word = new StringBuffer(30).append(SEPARATOR)
    def length = this.content.length()
    for (i in 0..<length) {
      char c = Character.toLowerCase(this.content.charAt(i))
      if (Character.isLetter(c))
      {
        word.append(c)
      }
      else
      {
        addAnalyze(word)
        word.setLength(1)
      }
    }
    addAnalyze(word);

    return ngramCounts
  }

    static public double calculateDifference(Map<String,Integer> contentProfileCounts,
                                             Map<String,Integer> categoryProfileCounts) {

      double sum = 0.0
      double s1  = 0.0
      double s2  = 0.0

      // Treat all NGrams contained in contentProfile;
      contentProfileCounts.each { ngram, ngramCount ->
        double c1 = ngramCount.toDouble()
        if(categoryProfileCounts.containsKey(ngram)) {
          double c2 = categoryProfileCounts[ngram].toDouble()
          double weight = weight(ngram.toString(), c1, c2)
          sum += c1 * c2 * weight
          s1  += c1 * c1 * weight
          s2  += c2 * c2 * weight
        } else {
          double weight = weight(ngram.toString(), c1, 0.0)
          s1  += c1 * c1 * weight
        }
      }

      // Treat NGrams contained ONLY in categoryProfile
      categoryProfileCounts.each { ngram, ngramCount ->
        if(!contentProfileCounts.containsKey(ngram)) {
          double c2 = categoryProfileCounts[ngram].toDouble()
          double weight = weight(ngram.toString(), 0.0, c2)
          s2  += c2 * c2 * weight
        }
      }

      return 1.0 - sum / Math.sqrt(s1*s2)
    }

    static private double weight(String ngram, double w1, double w2)
    {
      double blankWeight = DEFAULT_BLANK_WEIGHT
      int lengthLimit = DEFAULT_LENGTH_LIMIT

      int len = ngram.length()
      if ( len > lengthLimit )
          return 0.0;
      boolean hasBlank = ngram.charAt(0) == '_' || ngram.charAt(len-1) == '_'
      if ( blankWeight == 0.0 && hasBlank )
          return 0.0
      double combo = w1 + w2
      double res = ( len == 1 ? 1.0 : len == 2 ? 3.5 : 2.0 ) /(Math.sqrt(combo))
      if ( hasBlank ) res *= blankWeight;
      return res;
    }

    private void addAnalyze(StringBuffer word)
    {
      if (word.length() > 1)
      {
        word.append(SEPARATOR)
        addNGrams(word)
      }
    }

    /**
   * Add ngrams from a single word to this profile
   *
   * @param word
   */
    private void addNGrams(CharSequence word)
    {
      def limit = (maxNGramLength < word.length()) ? maxNGramLength : (word.length() -1 )
      for (i in minNGramLength..limit) {
        addNGrams(word, i)
      }
    }

      /**
     * @param word
     * @param n sequence length
     */
    private void addNGrams(CharSequence word, int n)
    {
      //for (int i = 0, end = word.length() - n; i <= end; i++)
      def end = word.length() - n
      for (i in 0..end) {
        int nMax = i + n
        CharSequence cs = word.subSequence(i, nMax)

        //Defaults value to zero if the key is not yet in the Map
        int count = ngramCounts.get(cs.toString(),0)
        count++
        ngramCounts[cs.toString()] = count
      }
    }
}
