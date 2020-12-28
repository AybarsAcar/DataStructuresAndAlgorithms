package dev.aybarsacar.datastructures.lists.doublylinkedlists;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DoublyLinkedListIntTest
{
  DoublyLinkedListInt list;

  @BeforeEach
  public void init()
  {
    list = new DoublyLinkedListInt();
    list.add(10);
    list.add(91);
    list.add(492);
    list.add(20);
    list.add(30);
  }

  @Test
  public void printsTheList()
  {
    System.out.println(list);
  }

  @Test
  public void returnsTheSizeOfTheList()
  {
    Assertions.assertEquals(5, list.size());
  }

  @Test
  public void sizeReturnsZeroForEmptyList()
  {
    DoublyLinkedListInt newList = new DoublyLinkedListInt();
    Assertions.assertEquals(0, newList.size());
  }

  @Test
  public void addsFistTest()
  {
    list.addFirst(11);
    Assertions.assertEquals(11, list.getHead().getData());
  }

  @Test
  public void addsLastTest()
  {
    list.addLast(1100);
    Assertions.assertEquals(1100, list.getTail().getData());
  }

  @Test
  public void addFirstToAnEmptyListTest()
  {
    DoublyLinkedListInt newList = new DoublyLinkedListInt();
    newList.addFirst(10);
    Assertions.assertEquals(10, newList.getHead().getData());
    Assertions.assertEquals(10, newList.getTail().getData());
  }

  @Test
  public void addLastToAnEmptyListTest()
  {
    DoublyLinkedListInt newList = new DoublyLinkedListInt();
    newList.addLast(20);
    Assertions.assertEquals(20, newList.getHead().getData());
    Assertions.assertEquals(20, newList.getTail().getData());
  }

  @Test
  public void containsTest()
  {
    Assertions.assertTrue(list.contains(492));
    Assertions.assertFalse(list.contains(14801));
  }

  @Test
  public void containsReturnsFalseInEmptyList()
  {
    DoublyLinkedListInt newList = new DoublyLinkedListInt();
    Assertions.assertFalse(newList.contains(10));
  }

  @Test
  public void getTest()
  {
    Assertions.assertEquals(20, list.get(3));
    Assertions.assertEquals(10, list.get(0));
  }

  @Test
  public void removeFirstTest()
  {
    Assertions.assertEquals(10, list.removeFirst());
    System.out.println(list);
  }

  @Test
  public void removeLastTest()
  {
    Assertions.assertEquals(30, list.removeLast());
    System.out.println(list);
  }

  @Test
  public void removeWhenSingleItemInTheList()
  {
    DoublyLinkedListInt newList = new DoublyLinkedListInt(20);
    Assertions.assertEquals(20, newList.removeLast());
    System.out.println(newList);
  }

  @Test
  public void addSortedTest()
  {
    DoublyLinkedListInt newList = new DoublyLinkedListInt();
    newList.addSorted(10);
    newList.addSorted(20);
    newList.addSorted(11840);
    newList.addSorted(2);
    newList.addSorted(39);
    newList.addSorted(17);
    newList.addSorted(-20);

    System.out.println(newList);
    Assertions.assertEquals(7, newList.size());
  }

  @Test
  void indexOfTest()
  {
    Assertions.assertEquals(0, list.indexOf(10));
    Assertions.assertEquals(-1, list.indexOf(13004));
  }

  @Test
  void removeItemTest()
  {
    boolean isRemoved = list.removeItem(492);
    boolean isRemoved2 = list.removeItem(1980);

    Assertions.assertTrue(isRemoved);
    Assertions.assertFalse(isRemoved2);

    System.out.println(list);
  }

  @Test
  void removeTest()
  {
    int item = list.remove(3);
    Assertions.assertEquals(20, item);
    System.out.println(list);
  }
}
