package dev.aybarsacar.datastructures.priorityqueue.ipq;

/**
 * An implementation of an indexed binary heap priority queue
 *
 * @param <T>
 */
public class MinIndexedBinaryHeap<T extends Comparable<T>> extends MinIndexedDHeap<T>
{
  public MinIndexedBinaryHeap(int maxSize)
  {
    super(2, maxSize);
  }
}
