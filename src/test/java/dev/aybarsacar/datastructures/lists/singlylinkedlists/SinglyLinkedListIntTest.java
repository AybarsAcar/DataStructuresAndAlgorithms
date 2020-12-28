package dev.aybarsacar.datastructures.lists.singlylinkedlists;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SinglyLinkedListIntTest
{
  SinglyLinkedListInt list;

  @BeforeEach
  public void init()
  {
    list = new SinglyLinkedListInt();

    list.add(1);
    list.add(39);
    list.add(11);
    list.add(93);
    list.add(10);
    list.add(19212);
  }

  @Test
  public void printsList()
  {
    System.out.println(list.toString());
  }

  @Test
  public void addsToList()
  {
    list.add(60);
    Assertions.assertEquals(60, list.getHead().getData());
  }

  @Test
  public void findsAndReturnsTheFistPosition()
  {
    list.add(60);
    Assertions.assertEquals(0, list.contains(60));

    Assertions.assertEquals(5, list.contains(39));

    list.add(39);
    Assertions.assertEquals(0, list.contains(39));
  }
}
