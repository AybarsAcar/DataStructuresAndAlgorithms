package dev.aybarsacar.datastructures.arrays;

import java.util.Iterator;

/**
 * Dynamic Array implementation
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class DynamicArray<T> implements Iterable<T>
{
  private T[] arr;                    // backing array
  private int len = 0;                // pointer - size on the user interface
  private int capacity = 0;           // size of the array

  public DynamicArray()
  {
    this(20);
  }

  public DynamicArray(int capacity)
  {
    if (capacity < 0) throw new IllegalArgumentException("Capacity can not be less than 0");
    this.capacity = capacity;
    arr = (T[]) new Object[capacity];
  }

  public int size()
  {
    return len;
  }

  public boolean isEmpty()
  {
    return len == 0;
  }

  /**
   * @param index
   * @return
   */
  public T get(int index)
  {
    if (index >= len || index < 0) throw new IndexOutOfBoundsException();
    return arr[index];
  }

  /**
   * @param index
   * @param elem
   */
  public void set(int index, T elem)
  {
    if (index >= len || index < 0) throw new IndexOutOfBoundsException();
    arr[index] = elem;
  }

  public void clear()
  {
    for (int i = 0; i < len; i++) arr[i] = null;
    len = 0;
  }

  /**
   * appends an element to the end of the dynamic array
   * O(1) most of the time
   * O(n) when resizing the array
   *
   * @param elem
   */
  public void add(T elem)
  {
//    resize if the capacity is not enough
    if (len + 1 >= capacity)
    {
      if (capacity == 0) capacity = 10;
      else capacity *= 2; // double the size of the backing array

//      create the new array and copy the elements
      T[] newArray = (T[]) new Object[capacity];
      for (int i = 0; i < len; i++) newArray[i] = arr[i];
      arr = newArray;
    }

//    insert the element to arr[len] then increment it
    arr[len++] = elem;
  }

  /**
   * removes an element at an index and returns it
   *
   * @param index
   * @return
   */
  public T removeAt(int index)
  {
    if (index >= len || index < 0) throw new IndexOutOfBoundsException();
    T data = arr[index];
    T[] newArray = (T[]) new Object[len - 1];
    for (int i = 0, j = 0; i < len; i++, j++)
    {
      if (i == index) j--;
      else newArray[j] = arr[i];
    }

    arr = newArray;
    capacity = --len;
    return data;
  }

  public boolean remove(T elem)
  {
    int index = indexOf(elem);
    if (index == -1) return false;
    removeAt(index);
    return true;
  }

  public int indexOf(T elem)
  {
    for (int i = 0; i < len; i++)
    {
      if (elem == null)
      {
        if (arr[i] == null) return i;
      }
      else
      {
        if (elem.equals(arr[i])) return i;
      }
    }
    return -1;
  }

  public boolean contains(T elem)
  {
    return indexOf(elem) != -1;
  }

  @Override
  public Iterator<T> iterator()
  {
    return new Iterator<>()
    {
      int index = 0;

      @Override
      public boolean hasNext()
      {
        return index < len;
      }

      @Override
      public T next()
      {
        return arr[index++];
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
    if (len == 0) return "[]";

    StringBuilder sb = new StringBuilder(len).append("[");
    for (int i = 0; i < len - 1; i++)
    {
      sb.append(arr[i]).append(", ");
    }
    sb.append(arr[len - 1]).append("]");
    return sb.toString();
  }
}
