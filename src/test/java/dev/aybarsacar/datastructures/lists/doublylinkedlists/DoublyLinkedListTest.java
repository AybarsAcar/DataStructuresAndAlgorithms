package dev.aybarsacar.datastructures.lists.doublylinkedlists;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoublyLinkedListTest
{
  //  TODO: implement
  DoublyLinkedList<Integer> list;

  @BeforeEach
  public void init()
  {
    list = new DoublyLinkedList<>()
    {{
      add(10);
      add(2);
      add(11);
      add(10);
      add(30);
      add(92);
    }};
  }

  @Test
  public void printsList()
  {
    System.out.println(list);
    System.out.println(list.indexOf(2));
  }
}
