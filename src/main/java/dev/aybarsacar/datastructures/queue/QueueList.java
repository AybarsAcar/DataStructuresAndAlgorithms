package dev.aybarsacar.datastructures.queue;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Queue implementation with a doubly linked list
 * using java.util.LinkedList class
 */
public class QueueList<T> implements Iterable<T>, Queue<T>
{
  private LinkedList<T> list = new LinkedList<>();

  public QueueList()
  {
  }

  public QueueList(T firstElem)
  {
    if (firstElem == null) throw new IllegalArgumentException("Cannot offer a null element");

    offer(firstElem);
  }

  @Override
  public void offer(T elem)
  {
    if (elem == null) throw new IllegalArgumentException("Cannot offer a null element");

    list.addLast(elem);
  }

  @Override
  public T poll()
  {
    if (isEmpty()) throw new RuntimeException("Queue Empty");

    return list.removeFirst();
  }

  @Override
  public T peek()
  {
    if (isEmpty()) throw new RuntimeException("Queue Empty");

    return list.peekFirst();
  }

  @Override
  public int size()
  {
    return list.size();
  }

  @Override
  public boolean isEmpty()
  {
    return list.isEmpty();
  }

  @Override
  public Iterator<T> iterator()
  {
    return list.iterator();
  }
}
