package dev.aybarsacar.datastructures.priorityqueue.ipq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An implementation of an indexed min D-ary heap priority queue
 * <p>
 * This implementation supports arbitrary keys with comparable values. To use arbitrary keys
 * (such as strings or objects) first map all your keys to the integer domain [0, N) where N is the
 * number of keys you have and then use the mapping with this indexed priority queue.
 * <p>
 * As convention, I denote 'ki' as the index value in the domain [0, N) associated with a key k,
 * therefore: ki = map[k]
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class MinIndexedDHeap<T extends Comparable<T>>
{
  //  current number of elements in the heap
  private int sz;

  //  maximum number of elements in the heap
  private final int N;

  //  the degree of every node in the heap
  private final int D;

  //  lookup arrays to track hte child/parent indexes of each node
  //  so we don't have to compute them dynamically
  private final int[] child, parent;

  //  The position map (pm) maps Key Indexes (ki) to where the position of that key is
  //  represented in teh priority queue in teh domain [0, sz).
  public final int[] pm;

  //  the Inverse Map (im) stores the indexes of the keys in the range [0, sz) which make up the pq
  //  It should be noted that 'im' and 'pm' are inverses of each other
  //  pm[im[i]] == im[pm[i]] == i
  public final int[] im;

  //  the values associated with the keys. it is very important to note that this array is
  //  indexed by the key indexes (ki)
  public final Object[] values;

  /**
   * Initializes a D-ary heap with a maximum capacity of maxSize
   *
   * @param degree
   * @param maxSize
   */
  public MinIndexedDHeap(int degree, int maxSize)
  {
    if (maxSize <= 0) throw new IllegalArgumentException("maxSize cannot be less than or equal to zero");

    D = Math.max(2, degree);
    N = Math.max(D + 1, maxSize);

    im = new int[N];
    pm = new int[N];
    child = new int[N];
    parent = new int[N];
    values = new Object[N];

    for (int i = 0; i < N; i++)
    {
      parent[i] = (i - 1) / D;
      child[i] = i * D + 1;

      pm[i] = im[i] = -1;
    }
  }

  public int size()
  {
    return sz;
  }

  public boolean isEmpty()
  {
    return sz == 0;
  }

  /**
   * @param ki key index
   * @return
   */
  public boolean contains(int ki)
  {
    keyInBoundsOrThrow(ki);
    return pm[ki] != -1;
  }

  public int peekMinKeyIndex()
  {
    isNotEmptyOrThrow();
    return im[0];
  }

  public int pollMinKeyIndex()
  {
    int minKi = peekMinKeyIndex();
    delete(minKi);
    return minKi;
  }

  public T peekMinValue()
  {
    isNotEmptyOrThrow();
    return (T) values[im[0]];
  }

  public T pollMinValue()
  {
    T minValue = peekMinValue();
    delete(peekMinKeyIndex());
    return minValue;
  }

  public void insert(int ki, T value)
  {
    if (contains(ki)) throw new IllegalArgumentException("index already exists; received: " + ki);

    valueNotNullOrThrow(value);

    pm[ki] = sz;
    im[sz] = ki;
    values[ki] = value;

    swim(sz++);
  }

  public T valueOf(int ki)
  {
    keyExistsOrThrow(ki);
    return (T) values[ki];
  }

  public T delete(int ki)
  {
    keyExistsOrThrow(ki);

    final int i = pm[ki];

    swap(i, --sz);

    sink(i);
    swim(i);

//    capture the value and cleanup
    T value = (T) values[ki];
    values[ki] = null;
    pm[ki] = -1;
    im[sz] = -1;

    return value;
  }

  public T update(int ki, T value)
  {
    keyExistsAndValueNotNullOrThrow(ki, value);

    final int i = pm[ki];

    T oldValue = (T) values[ki];
    values[ki] = value;

    sink(i);
    swim(i);

    return oldValue;
  }

  /**
   * Strictly decreases the value associated with 'ki' to 'value'
   *
   * @param ki
   * @param value
   */
  public void decrese(int ki, T value)
  {
    keyExistsAndValueNotNullOrThrow(ki, value);

    if (less(value, values[ki]))
    {
      values[ki] = value;
      swim(pm[ki]);
    }
  }

  /**
   * Strictly increases the value associated with 'ki' to 'value'
   *
   * @param ki
   * @param value
   */
  public void increase(int ki, T value)
  {
    keyExistsAndValueNotNullOrThrow(ki, value);

    if (less(values[ki], value))
    {
      values[ki] = value;
      sink(pm[ki]);
    }
  }

  private void sink(int i)
  {
    for (int j = minChild(i); j != -1; )
    {
      swap(i, j);
      i = j;
      j = minChild(i);
    }
  }

  private void swim(int i)
  {
    while (less(i, parent[i]))
    {
      swap(i, parent[i]);
      i = parent[i];
    }
  }

  /**
   * from the parent node at index i find the minimum child below it
   *
   * @param i
   * @return
   */
  private int minChild(int i)
  {
    int index = -1, from = child[i], to = Math.min(sz, from + D);

    for (int j = from; j < to; j++)
    {
      if (less(j, i)) index = i = j;
    }

    return index;
  }

  /**
   * swaps the values in the position and inverse maps
   *
   * @param i
   * @param j
   */
  private void swap(int i, int j)
  {
    pm[im[j]] = i;
    pm[im[i]] = j;

    int temp = im[i];

    im[i] = im[j];
    im[j] = temp;
  }

  private boolean less(int i, int j)
  {
    return ((Comparable<? super T>) values[im[i]]).compareTo((T) values[im[j]]) < 0;
  }

  private boolean less(Object o1, Object o2)
  {
    return ((Comparable<? super T>) o1).compareTo((T) o2) < 0;
  }

  @Override
  public String toString()
  {
    List<Integer> lst = new ArrayList<>(sz);
    for (int i = 0; i < sz; i++) lst.add(im[i]);
    return lst.toString();
  }

  /* Error Helper Functions */

  private void isNotEmptyOrThrow()
  {
    if (isEmpty()) throw new NoSuchElementException("Priority queue underflow");
  }

  private void keyExistsAndValueNotNullOrThrow(int ki, Object value)
  {
    keyExistsOrThrow(ki);
    valueNotNullOrThrow(value);
  }

  private void keyExistsOrThrow(int ki)
  {
    if (!contains(ki)) throw new NoSuchElementException("Index does not exist; received: " + ki);
  }

  private void valueNotNullOrThrow(Object value)
  {
    if (value == null) throw new IllegalArgumentException("value cannot be null");
  }

  private void keyInBoundsOrThrow(int ki)
  {
    if (ki < 0 || ki >= N)
      throw new IllegalArgumentException("Key index out of bounds; received: " + ki);
  }

  /* Test functions */

  // Recursively checks if this heap is a min heap. This method is used
  // for testing purposes to validate the heap invariant.
  public boolean isMinHeap()
  {
    return isMinHeap(0);
  }

  private boolean isMinHeap(int i)
  {
    int from = child[i], to = Math.min(sz, from + D);
    for (int j = from; j < to; j++)
    {
      if (!less(i, j)) return false;
      if (!isMinHeap(j)) return false;
    }
    return true;
  }
}
