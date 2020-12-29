package dev.aybarsacar.datastructures.queue;

/**
 * Queue implementation backed by an array
 * TODO: finish implementing
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

  }

  @Override
  public T poll()
  {
    return null;
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
