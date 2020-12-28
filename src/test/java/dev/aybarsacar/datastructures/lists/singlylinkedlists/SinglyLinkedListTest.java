package dev.aybarsacar.datastructures.lists.singlylinkedlists;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SinglyLinkedListTest
{
  SinglyLinkedList<String> list;
  SinglyLinkedList<Integer> list2;

  @BeforeEach
  public void init()
  {
    list = new SinglyLinkedList<>();
    list.add("Hello");
    list.add("Hello");
    list.add("World");
    list.add("Hello");

    list2 = new SinglyLinkedList<>();
    list2.add(1);
    list2.add(10);
    list2.add(1141);
    list2.add(7);
  }

  @Test
  public void addToStringList()
  {
    list.add("lelelele");
    System.out.println(list);
  }

  @Test
  public void addToIntList()
  {
    list2.add(20);
    System.out.println(list2);
  }

  @Test
  public void removeFromList()
  {
    list.add("Hello");
    list.add("Hello");
    list.add("World");
    list.add("Hello");

    Assertions.assertEquals("Hello", list.remove());

    list.add("HAHHAH");
    Assertions.assertEquals("HAHHAH", list.remove());

    System.out.println(list);
  }

  @Test
  public void removesASpecificItemAndReturnsTrueOrReturnsFalse()
  {
    boolean isRemoved = list2.removeItem(10);
    Assertions.assertTrue(isRemoved);

    Assertions.assertFalse(list2.removeItem(20));

    System.out.println(list2);
  }

  @Test
  public void removesAndReturnsTheItemAtGivenLocation()
  {
    int data = list2.remove(0);
    Assertions.assertEquals(7, data);

    int data2 = list2.remove(2);
    Assertions.assertEquals(1, data2);
  }

  @Test
  public void getsAnItemByLocation()
  {
    Assertions.assertEquals(10, list2.get(2));
  }
}
