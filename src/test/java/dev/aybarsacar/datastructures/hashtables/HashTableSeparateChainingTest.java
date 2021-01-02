package dev.aybarsacar.datastructures.hashtables;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashTableSeparateChainingTest
{
  HashTableSeparateChaining<String, Integer> table;

  @BeforeEach
  public void init()
  {
    table = new HashTableSeparateChaining<>()
    {{
      put("Aybars", 28);
      put("Shuang", 23);
      put("Zuhre", 20);
      put("Isil", 54);
    }};
  }

  @Test
  public void printsTheTable()
  {
    System.out.println(table);
  }
}
