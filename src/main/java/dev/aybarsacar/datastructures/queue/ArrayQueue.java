package dev.aybarsacar.datastructures.queue;

import java.util.NoSuchElementException;

/**
 * Queue implementation backed by an array
 */
@SuppressWarnings("unchecked")
public class ArrayQueue<T> implements Queue<T>
{
  private T[] queue;            // backing array
  private int front;            // pointer to the front of the queue
  private int back;             // pointer to the back of the queue (next available position)

  public ArrayQueue()
  {
    this(10);
  }

  public ArrayQueue(int capacity)
  {
    queue = (T[]) new Object[capacity];
    front = 0;
    back = 0;
  }

  @Override
  public void offer(T elem)
  {
    if (size() == queue.length - 1)
    {
      int numItems = size();

//      increase the size of the array
      T[] newArray = (T[]) new Object[queue.length * 2];

//      when resizing unwrap the queue
      System.arraycopy(queue, front, newArray, 0, queue.length - front);
      System.arraycopy(queue, 0, newArray, queue.length - front, back);

      queue = newArray;

//      reset the front and back pointers
      front = 0;
      back = numItems;
    }

//    add the new element to the available position
    queue[back] = elem;

    if (back < queue.length)
    {
      back++;
    }
    else
    {
      back = 0;   // wrapped
    }
  }

  @Override
  public T poll()
  {
    if (front == back) throw new NoSuchElementException();

    T itemToPoll = queue[front];
    queue[front] = null;
    front++;

//    simple optimisation
    if (front == back)
    {
      front = back = 0;
    }
    else if (front == queue.length)
    {
      front = 0;    // wrap it
    }

    return itemToPoll;
  }

  @Override
  public T peek()
  {
    if (isEmpty()) throw new RuntimeException("Queue is empty");
    return queue[front];
  }

  @Override
  public int size()
  {
//    queue not wrapped
    if (front <= back)
      return back - front;

//    queue is wrapped
    else
      return back - front + queue.length;

  }

  @Override
  public boolean isEmpty()
  {
    return front == back;
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder().append("[");
//    check is the queue is wrapped
    if (front <= back)
    {
      for (int i = front; i < back; i++)
      {
        sb.append(queue[i]).append(", ");
      }
    }
    else
    {
//      the queue is wrapped
      for (int i = front; i < queue.length; i++)
      {
        sb.append(queue[i]).append(", ");
      }
      for (int i = 0; i < back; i++)
      {
        sb.append(queue[i]).append(", ");
      }
    }
    sb.append("]");
    return sb.toString();
  }
}
