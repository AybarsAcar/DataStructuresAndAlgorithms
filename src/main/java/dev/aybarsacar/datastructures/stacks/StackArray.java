package dev.aybarsacar.datastructures.stacks;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * Stack implementation using a backing array
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class StackArray<T> implements Stack<T>
{
  T[] arr;                // stores the elements
  int size;               // pointer to the top element
  int capacity;           // capacity of the backing array

  public StackArray()
  {
    this(10);
  }

  public StackArray(int capacity)
  {
    if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be less than 0");

    this.capacity = capacity;
    arr = (T[]) new Object[capacity];
    size = 0;
  }

  public int size()
  {
    return size;
  }

  public boolean isEmpty()
  {
    return size == 0;
  }

  public void push(T elem)
  {
    if (size == capacity) increaseCapacity();

    arr[size] = elem;
    size++;
  }

  private void increaseCapacity()
  {
    capacity *= 2;
    arr = Arrays.copyOf(arr, capacity);
  }

  public T pop()
  {
    if (isEmpty()) throw new EmptyStackException();

    T elem = arr[size - 1];
    size--;
    return elem;
  }

  public T peek()
  {
    if (isEmpty()) throw new EmptyStackException();

    return arr[size - 1];
  }
}
