package dev.aybarsacar.datastructures.trees.bst;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BinarySearchTreeTest
{
  BinarySearchTree<Integer> bst;

  @BeforeEach
  public void init()
  {
    bst = new BinarySearchTree<>()
    {{
      insert(10);
      insert(1234);
      insert(93);
      insert(2);
      insert(39);
      insert(-1);
      insert(1349);
      insert(5);
      insert(100);
    }};
  }

  @Test
  public void printsBST()
  {
    Assertions.assertEquals(9, bst.size());
    System.out.println(bst);
  }

  @Test
  public void containsTest()
  {
    Assertions.assertTrue(bst.contains(100));
    Assertions.assertFalse(bst.contains(140));
  }

  @Test
  public void doesNotInsertSameValue()
  {
    Assertions.assertFalse(bst.insert(100));
    System.out.println(bst);
  }

  @Test
  public void removesNodeWithTwoChildren()
  {
    boolean isRemoved = bst.remove(10);
    System.out.println(bst);
    Assertions.assertTrue(isRemoved);
    Assertions.assertFalse(bst.remove(1042));
  }

  @Test
  public void removesALeafNode()
  {
    boolean isRemoved = bst.remove(-1);
    System.out.println(bst);
    Assertions.assertTrue(isRemoved);
  }

  @Test
  public void findMinTest()
  {
    Assertions.assertEquals(-1, bst.findMin());
  }

  @Test
  public void findMaxTest()
  {
    Assertions.assertEquals(1349, bst.findMax());
  }
}
