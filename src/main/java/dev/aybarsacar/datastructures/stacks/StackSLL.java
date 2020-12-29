package dev.aybarsacar.datastructures.stacks;

import dev.aybarsacar.datastructures.lists.singlylinkedlists.SinglyLinkedList;

import java.util.EmptyStackException;
import java.util.Iterator;

/**
 * stack implementation using the singly linked list class created by Aybars Acar
 */
public class StackSLL<T> implements Iterable<T>, Stack<T>
{
  SinglyLinkedList<T> list = new SinglyLinkedList<>();

  public StackSLL()
  {
  }

  public StackSLL(T firstElem)
  {
    push(firstElem);
  }

  public int size()
  {
    return list.size();
  }

  public boolean isEmpty()
  {
    return size() == 0;
  }

  public void push(T elem)
  {
    list.add(elem);
  }

  public T pop()
  {
    if (isEmpty()) throw new EmptyStackException();
    return list.remove();
  }

  public T peek()
  {
    if (isEmpty()) throw new EmptyStackException();
    return list.get(0);
  }

  @Override
  public Iterator<T> iterator()
  {
    return list.iterator();
  }

  @Override
  public String toString()
  {
    return list.toString();
  }
}
