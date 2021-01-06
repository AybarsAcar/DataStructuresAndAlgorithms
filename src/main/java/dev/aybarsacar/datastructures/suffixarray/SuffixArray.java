package dev.aybarsacar.datastructures.suffixarray;

/**
 * Abstract class that captures the behaviour of a suffix array
 */
public abstract class SuffixArray
{
  protected final int N;                  // length of the suffix array
  protected int[] T;                      // T is the combined text
  protected int[] sa;                     // the sorted suffix array values
  protected int[] lcp;                    // Longest Common Prefix array

  private boolean constructedSa = false;
  private boolean isConstructedLcpArray = false;

  public SuffixArray(int[] text)
  {
    if (text == null) throw new IllegalArgumentException("Text cannot be null");
    this.T = text;
    this.N = text.length;
  }

  public int getTextLength()
  {
    return T.length;
  }

  public int[] getSa()
  {
    buildSuffixArray();
    return sa;
  }

  public int[] getLcpArray()
  {
    buildLcpArray();
    return lcp;
  }

  protected void buildSuffixArray()
  {
    if (constructedSa) return;
    construct();
    constructedSa = true;
  }

  protected void buildLcpArray()
  {
    if (constructedSa) return;
    buildSuffixArray();
    kasai();
    isConstructedLcpArray = true;
  }

  protected static int[] toIntArray(String s)
  {
    if (s == null) return null;
    int[] t = new int[s.length()];

    for (int i = 0; i < s.length(); i++)
      t[i] = s.charAt(i);

    return t;
  }

  /**
   * The suffix array construction algorithm is left undefined
   * as there are multiple ways to implement this
   */
  protected abstract void construct();

  /**
   * Kasai Algorithm is used to build the lcp array
   */
  private void kasai()
  {
    lcp = new int[N];
    int[] inv = new int[N];

    for (int i = 0; i < N; i++) inv[sa[i]] = i;

    for (int i = 0, len = 0; i < N; i++)
    {
      if (inv[i] > 0)
      {
        int k = sa[inv[i] - 1];

        while ((i + len < N) && (k + len < N) && T[i + len] == T[k + len]) len++;

        lcp[inv[i]] = len;

        if (len > 0) len--;
      }
    }
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("-----i-----SA-----LCP---Suffix\n");

    for (int i = 0; i < N; i++)
    {
      int suffixLen = N - sa[i];
      char[] suffixArray = new char[suffixLen];
      for (int j = sa[i], k = 0; j < N; j++, k++) suffixArray[k] = (char) T[j];
      String suffix = new String(suffixArray);
      String formattedStr = String.format("% 7d % 7d % 7d %s\n", i, sa[i], lcp[i], suffix);
      sb.append(formattedStr);
    }
    return sb.toString();
  }
}
