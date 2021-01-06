package dev.aybarsacar.datastructures.suffixarray;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SuffixArrayTest
{
  SuffixArray sa;

  @BeforeEach
  public void init()
  {
    sa = new SuffixArrayImpl("ABBABAABAA");
  }

  @Test
  public void findsTheSuffixArrays()
  {
    System.out.println(sa);
  }
}
