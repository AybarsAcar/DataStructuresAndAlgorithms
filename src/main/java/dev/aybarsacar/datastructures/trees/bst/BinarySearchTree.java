package dev.aybarsacar.datastructures.trees.bst;

import dev.aybarsacar.datastructures.trees.utils.TreePrinter;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;

/**
 * Binary Search Tree implementation
 * Binary Search Tree is a special Binary Tree
 * where the smaller value is stored as the left child of the parent
 * and the greater value is stored as the right child of the parent
 * this implementation does not allow duplicate values
 */
public class BinarySearchTree<T extends Comparable<T>> implements Iterable<T>
{
  /**
   * Node class that stores the data
   * to be stored in the tree
   */
  private class Node implements TreePrinter.PrintableNode
  {
    public T value;                 // the value, data contained within node
    public Node left, right;        // the left and right children of this node

    public Node(T value)
    {
      this.value = value;
      left = null;
      right = null;
    }

    @Override
    public TreePrinter.PrintableNode getLeft()
    {
      return left;
    }

    @Override
    public TreePrinter.PrintableNode getRight()
    {
      return right;
    }

    @Override
    public String getText()
    {
      return value.toString();
    }
  }

  private Node root;
  private int nodeCount;

  /**
   * initialises an empty BST
   */
  public BinarySearchTree()
  {
    nodeCount = 0;
  }

  /**
   * construct an BST with the initial node its root
   *
   * @param root
   */
  public BinarySearchTree(Node root)
  {
    this.root = root;
    nodeCount = 1;
  }

  public int size()
  {
    return nodeCount;
  }

  /**
   * @return the value of the root node
   */
  public T root()
  {
    return root.value;
  }

  /**
   * @param value
   * @return
   */
  public boolean contains(T value)
  {
    return contains(root, value);
  }

  /**
   * @param current
   * @param value
   * @return
   */
  private boolean contains(Node current, T value)
  {
    if (current == null) return false;

    int comparison = value.compareTo(current.value);

    if (comparison < 0) return contains(current.left, value);
    if (comparison > 0) return contains(current.right, value);

    return true;
  }

  /**
   * adds a node to the root
   *
   * @param value
   */
  public boolean insert(T value)
  {
    if (value == null) return false;

    if (!contains(root, value))
    {
      root = insert(root, value);
      nodeCount++;
      return true;
    }
    return false;
  }

  /**
   * private method to add a node to a node recursively
   * until it finds the correct place
   *
   * @param current
   * @param value
   * @return
   */
  private Node insert(Node current, T value)
  {
//    base case
    if (current == null) return new Node(value);

//    if value < current.value
    if (value.compareTo(current.value) < 0) current.left = insert(current.left, value);

//    if value > current.value
    else if (value.compareTo(current.value) > 0) current.right = insert(current.right, value);

    else return current; // value already exists, we will never hit this block

    return current;
  }

  /**
   * gets access to the value of that first instance
   *
   * @param value
   * @return
   */
  public T find(T value)
  {
    return find(root, value);
  }

  private T find(Node node, T value)
  {
    if (node == null) return null;

    int comparison = value.compareTo(node.value);

    if (comparison < 0) return find(node.left, value);
    else if (comparison > 0) return find(node.right, value);
    else return node.value;
  }

  /**
   * @param value
   * @return
   */
  public boolean remove(T value)
  {
    if (value == null) return false;

    if (contains(root, value))
    {
      root = remove(root, value);
      nodeCount--;
      return true;
    }
    return false; // the value does not exist in the tree
  }

  private Node remove(Node current, T value)
  {
    if (current == null) return null;

    int comparison = value.compareTo(current.value);
    if (comparison < 0)
    {
      current.left = remove(current.left, value);
    }
    else if (comparison > 0)
    {
      current.right = remove(current.right, value);
    }
    else
    {
//      we have found the node to be deleted
//      node with only one child or no child
      if (current.left == null)
        return current.right;
      else if (current.right == null)
        return current.left;

//      node with 2 children
      current.value = findMin(current.right);

      current.right = remove(current.right, value);
    }

    return current;
  }

  /**
   * @return the minimum value in the BST
   */
  public T findMin()
  {
    return findMin(root);
  }

  /**
   * @return the maximum value in the BST
   */
  public T findMax()
  {
    return findMax(root);
  }

  private T findMin(Node node)
  {
    while (node.left != null)
      node = node.left;

    return node.value;
  }

  private T findMax(Node node)
  {
    while (node.right != null)
      node = node.right;

    return node.value;
  }

  /**
   * clears the tree
   * works because Java is garbage collected
   * so anything we cannot reach anymore is garbage collected
   */
  public void clear()
  {
    root = null;
  }

  @Override
  public Iterator<T> iterator()
  {
    final int expectedNodeCount = nodeCount;
    final Stack<Node> stack = new Stack<>();
    stack.push(root);

    return new Iterator<T>()
    {
      Node trav = root;

      @Override
      public boolean hasNext()
      {
        if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
        return root != null && !stack.isEmpty();
      }

      @Override
      public T next()
      {
        if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();

        while (trav != null && trav.left != null)
        {
          stack.push(trav.left);
          trav = trav.left;
        }

        Node node = stack.pop();

        if (node.right != null)
        {
          stack.push(node.right);
          trav = node.right;
        }

        return node.value;
      }

      @Override
      public void remove()
      {
        throw new UnsupportedOperationException();
      }
    };
  }

  @Override
  public String toString()
  {
    return TreePrinter.getTreeDisplay(root);
  }
}
