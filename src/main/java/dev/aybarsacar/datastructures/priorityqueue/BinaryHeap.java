package dev.aybarsacar.datastructures.priorityqueue;

import java.util.*;

/**
 * a min priority queue implementation using a binary heap
 */
public class BinaryHeap<T extends Comparable<T>>
{
  //  A dynamic list to store the binary head
  private List<T> heap = null;

//  to store the values and the indices so searching will be O(1)
//  and removal will be done in O(log(n))
//  private Map<T, TreeSet<Integer>> map = new HashMap<>();

  public BinaryHeap()
  {
    this(10);
  }

  public BinaryHeap(int size)
  {
    heap = new ArrayList<>(size);
  }

  /**
   * constructor to initialise the heap with an array of elements
   * constructed in O(n) time
   *
   * @param elems
   */
  public BinaryHeap(T[] elems)
  {
    int heapSize = elems.length;
    heap = new ArrayList<>(heapSize);

//    place all elements
    for (int i = 0; i < heapSize; i++) heap.add(elems[i]);

//    Heapify O(n)
    for (int i = Math.max(0, (heapSize / 2) - 1); i >= 0; i--) sink(i);
  }

  public boolean isEmpty()
  {
    return size() == 0;
  }

  public void clear()
  {
    for (int i = 0; i < size(); i++)
    {
      heap.set(i, null);
    }
//    map.clear();
  }

  public int size()
  {
    return heap.size();
  }

  /**
   * returns the value of the element with the lowest priority in this priority queue
   * if the pq is empty returns null
   *
   * @return
   */
  public T peek()
  {
    if (isEmpty()) return null;
    return heap.get(0);
  }

  /**
   * removes the root of the heap
   * O(log(n))
   *
   * @return
   */
  public T poll()
  {
    return removeAt(0);
  }

  /**
   * @param elem
   * @return
   */
  public boolean contains(T elem)
  {
    for (int i = 0; i < size(); i++)
      if (heap.get(i).equals(elem)) return true;

    return false;
  }

  /**
   * adds an element to the priority queue
   *
   * @param elem
   */
  public void add(T elem)
  {
    if (elem == null) throw new IllegalArgumentException("Cannot add a null element to the heap");

    heap.add(elem);

    int indexOfLastElem = size() - 1;
    swim(indexOfLastElem);
  }

  /**
   * tests if the value of node i <= node j
   * this method assumes i and j are valid indices
   * O(1)
   *
   * @param i
   * @param j
   * @return
   */
  private boolean less(int i, int j)
  {
    T node1 = heap.get(i);
    T node2 = heap.get(j);

    return node1.compareTo(node2) <= 0;
  }

  /**
   * performs bottom up node swim
   * O(log(n))
   *
   * @param k
   */
  private void swim(int k)
  {
//    Grab the index of the next parent node WRT to k
    int parent = (k - 1) / 2;

//    keep swimming while we have not reached the root and while we are less than our parent
    while (k > 0 && less(k, parent))
    {
//      Exchange k wiht the parent
      swap(parent, k);
      k = parent;

//      Grab the index of the next parent node WRT to k
      parent = (k - 1) / 2;
    }
  }

  /**
   * Top down node sink
   * O(log(n))
   *
   * @param k
   */
  private void sink(int k)
  {
    int heapSize = size();
    while (true)
    {
      int left = 2 * k + 1;         // left child
      int right = 2 * k + 2;        // right child
      int smallest = left;          // Assume left is the smallest node of the two children

//      Find which is smaller left or right
//      if right is smaller set smallest to be right
      if (right < heapSize && less(right, left)) smallest = right;

//      Stop if we are outside the bounds of the tree
//      or stop early if we cannot sink k anymore
      if (left >= heapSize || less(k, smallest)) break;

//      move down the tree following the smallest node
      swap(smallest, k);
      k = smallest;
    }
  }

  /**
   * swaps to nodes at indexes i and j and assumes i and j are valid indexes
   * O(1)
   *
   * @param i
   * @param j
   */
  private void swap(int i, int j)
  {
    T elemI = heap.get(i);
    T elemJ = heap.get(j);

    heap.set(i, elemJ);
    heap.set(j, elemI);
  }

  /**
   * does linear removing
   * O(n)
   * can be optimised by adding a hash table to the class
   * and storing the indexes of the values in a hash table
   *
   * @param element
   * @return
   */
  public boolean remove(T element)
  {
    if (element == null) return false;

//    linear removal via search
    for (int i = 0; i < size(); i++)
    {
      if (element.equals(heap.get(i)))
      {
        removeAt(i);
        return true;
      }
    }
    return false;
  }

  /**
   * removes a node at a particular index i
   * O(log(n))
   *
   * @param i
   */
  private T removeAt(int i)
  {
    if (isEmpty()) return null;

    int indexOfLastElem = size() - 1;
    T nodeToRemove = heap.get(i);
    swap(i, indexOfLastElem);

    heap.remove(indexOfLastElem);

//    check if the last element was removes
    if (i == indexOfLastElem) return nodeToRemove;

    T elem = heap.get(i);

    sink(i);

//    if sink doesn't (nothing happens with swim) work try swim
    if (heap.get(i).equals(elem)) swim(i);

    return nodeToRemove;
  }

  /**
   * recursively checks if this heap is a min heap
   * this method is for testing purposes to make sure our sink and swims after adding and removals
   * do NOT violate the heap invariant
   *
   * @param k
   * @return
   */
  public boolean isMinHeap(int k)
  {
    int heapSize = size();

//    if we are out of bounds return true
    if (k >= heapSize) return true;

    int leftChild = 2 * k + 1;
    int rightChild = 2 * k + 2;

//    return false if the parent is not less than its children
    if (leftChild < heapSize && !less(k, leftChild)) return false;
    if (rightChild < heapSize && !less(k, leftChild)) return false;

//    recursively call it on the children
    return isMinHeap(leftChild) && isMinHeap(rightChild);
  }

  @Override
  public String toString()
  {
    return heap.toString();
  }
}
