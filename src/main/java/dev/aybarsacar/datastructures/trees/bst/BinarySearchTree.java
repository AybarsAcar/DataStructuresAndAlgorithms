package dev.aybarsacar.datastructures.trees.bst;

import dev.aybarsacar.datastructures.trees.utils.TreePrinter;
import dev.aybarsacar.datastructures.trees.utils.TreeTraversalOrder;

import java.util.*;

/**
 * Binary Search Tree implementation
 * Binary Search Tree is a special Binary Tree
 * where the smaller value is stored as the left child of the parent
 * and the greater value is stored as the right child of the parent
 * this implementation does not allow duplicate values
 */
public class BinarySearchTree<T extends Comparable<T>>
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

  public boolean isEmpty()
  {
    return nodeCount == 0;
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
//    base case - found a leaf node to insert
    if (current == null) return new Node(value);

    int comparison = value.compareTo(current.value);

//    if value < current.value
    if (comparison < 0) current.left = insert(current.left, value);

//    if value > current.value
    else if (comparison > 0) current.right = insert(current.right, value);

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

    int comparator = value.compareTo(node.value);

    if (comparator < 0) return find(node.left, value);
    else if (comparator > 0) return find(node.right, value);
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

    int comparator = value.compareTo(current.value);
    if (comparator < 0)
    {
      current.left = remove(current.left, value);
    }
    else if (comparator > 0)
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

  /**
   * @return the height
   */
  public int height()
  {
    return height(root);
  }

  /**
   * @param node - height of the node within the tree
   * @return - the greater height of left or right subtree
   */
  private int height(Node node)
  {
    if (node == null) return 0;
    return Math.max(height(node.right), height(node.left));
  }

  /**
   * this methods returns an iterator for a given TreeTraversalOrder
   * which we can traverse in four ways
   *
   * @param order - Tree Traversal
   * @return - Iterator
   */
  public Iterator<T> traverse(TreeTraversalOrder order)
  {
    switch (order)
    {
      case PRE_ORDER:
        return preOrderTraversal();
      case IN_ORDER:
        return inOrderTraversal();
      case POST_ORDER:
        return postOrderTraversal();
      case LEVEL_ORDER:
        return levelOrderTraversal();
      default:
        return null;
    }
  }

  /**
   * returns an iterator to traverse the tree in pre order
   *
   * @return
   */
  private Iterator<T> preOrderTraversal()
  {
    final int expectedNodeCount = nodeCount;
    final Stack<Node> stack = new Stack<>();
    stack.push(root);

    return new Iterator<>()
    {
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

        Node node = stack.pop();
        if (node.right != null) stack.push(node.right);
        if (node.left != null) stack.push(node.left);

        return node.value;
      }

      @Override
      public void remove()
      {
        throw new UnsupportedOperationException();
      }
    };
  }

  /**
   * returns an iterator to traverse the tree in in order
   *
   * @return
   */
  private Iterator<T> inOrderTraversal()
  {
    final int expectedNodeCount = nodeCount;
    final Stack<Node> stack = new Stack<>();
    stack.push(root);

    return new Iterator<>()
    {
      Node current = root;

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

        while (current != null && current.left != null)
        {
          stack.push(current.left);
          current = current.left;
        }

        Node node = stack.pop();

        if (node.right != null)
        {
          stack.push(node.right);
          current = node.right;
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

  /**
   * returns an iterator to traverse the tree in post order
   *
   * @return
   */
  private Iterator<T> postOrderTraversal()
  {
    final int expectedNodeCount = nodeCount;
    final Stack<Node> stack1 = new Stack<>();
    final Stack<Node> stack2 = new Stack<>();
    stack1.push(root);

    while (!stack1.isEmpty())
    {
      Node node = stack1.pop();
      if (node != null)
      {
        stack2.push(node);
        if (node.left != null) stack1.push(node.left);
        if (node.right != null) stack1.push(node.right);
      }
    }

    return new Iterator<>()
    {
      @Override
      public boolean hasNext()
      {
        if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
        return root != null && !stack2.isEmpty();
      }

      @Override
      public T next()
      {
        if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();

        return stack2.pop().value;
      }

      @Override
      public void remove()
      {
        throw new UnsupportedOperationException();
      }
    };
  }

  /**
   * returns an iterator to traverse the tree in level order
   *
   * @return
   */
  private Iterator<T> levelOrderTraversal()
  {
    final int expectedNodeCount = nodeCount;
    final Queue<Node> queue = new LinkedList<>();
    queue.offer(root);

    return new Iterator<>()
    {
      @Override
      public boolean hasNext()
      {
        if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();
        return root != null && !queue.isEmpty();
      }

      @Override
      public T next()
      {
        if (expectedNodeCount != nodeCount) throw new ConcurrentModificationException();

        Node node = queue.poll();
        if (node.right != null) queue.offer(node.right);
        if (node.left != null) queue.offer(node.left);

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
