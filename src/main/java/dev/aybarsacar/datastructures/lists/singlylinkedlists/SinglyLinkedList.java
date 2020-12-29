package dev.aybarsacar.datastructures.lists.singlylinkedlists;

import java.util.Iterator;

/**
 * Singly Linked List implementation for a Generic class
 */
public class SinglyLinkedList<T> implements Iterable<T>
{
  private class Node
  {
    private final T data;
    private Node next;

    public Node(T data)
    {
      this.data = data;
    }

    public T getData()
    {
      return data;
    }

    public Node getNext()
    {
      return next;
    }

    public void setNext(Node next)
    {
      this.next = next;
    }

    @Override
    public String toString()
    {
      return data.toString();
    }
  }

  private Node head;           // head of the singly linked list
  private int size;               // Size of the list, convenience variable

  public SinglyLinkedList()
  {
    size = 0;
  }

  public SinglyLinkedList(Node head)
  {
    this.head = head;
    size = 1;
  }

  public Node getHead()
  {
    return head;
  }

  public int size()
  {
    return size;
  }

  public boolean isEmpty()
  {
    return size == 0;
  }

  /**
   * adds to the beginning of hte list
   *
   * @param data
   */
  public void add(T data)
  {
    Node nodeToAdd = new Node(data);
    nodeToAdd.setNext(head);
    head = nodeToAdd;
    size++;
  }

  /**
   * Removes the first item and returns it
   * returns null if the list is empty
   *
   * @return
   */
  public T remove()
  {
    if (isEmpty()) return null;

    Node nodeToRemove = head;

    head = head.getNext();
    nodeToRemove.setNext(null);

    size--;

    return nodeToRemove.getData();
  }

  /**
   * removes the first instance of a specific element if it exists and returns true
   * if not returns false
   *
   * @param value
   * @return
   */
  public boolean removeItem(T value)
  {
    if (isEmpty()) return false;
    if (head.getData() == value) remove();

    Node previous = head;
    Node current = head.getNext();

    while (current != null)
    {
      if (current.getData() == value)
      {
        previous.setNext(current.getNext());
        current.setNext(null);
        size--;

        return true;
      }
      previous = previous.getNext();
      current = current.getNext();
    }
    return false;
  }

  /**
   * removes an item at the location
   *
   * @param location
   * @return
   */
  public T remove(int location)
  {
    if (location >= size) throw new IndexOutOfBoundsException();

    if (location == 0) return remove();

    Node previous = head;
    for (int i = 0; i < location - 1; i++) previous = previous.getNext();

    Node nodeToRemove = previous.getNext();

    previous.setNext(nodeToRemove.getNext());
    nodeToRemove.setNext(null);
    size--;

    return nodeToRemove.getData();
  }


  /**
   * check if the element exists
   * if it does returns true
   *
   * @return
   */
  public boolean contains(T value)
  {
    return indexOf(value) != -1;
  }

  /**
   * returns the first encountered location of the value if it exists
   * if not returns -1
   *
   * @param value
   * @return
   */
  public int indexOf(T value)
  {
    Node current = head;
    int index = 0;
    while (current != null)
    {
      if (current.getData() == value)
      {
        return index;
      }
      current = current.getNext();
      index++;
    }
    return -1;
  }

  /**
   * returns the item at a specific location
   *
   * @param location
   * @return
   */
  public T get(int location)
  {
    if (location >= size) throw new IndexOutOfBoundsException();

    Node current = head;
    for (int i = 0; i < location; i++) current = current.getNext();

    return current.getData();
  }

  /**
   * turns the list into a primitive array
   *
   * @return an array of T
   */
  public T[] toArray()
  {
    @SuppressWarnings("unchecked")
    T[] arr = (T[]) new Object[size];

    Node current = head;
    int index = 0;
    while (current != null)
    {
      arr[index] = current.data;
      index++;
      current = current.getNext();
    }
    return arr;
  }

  @Override
  public Iterator<T> iterator()
  {
    return new Iterator<T>()
    {
      Node current = head;

      @Override
      public boolean hasNext()
      {
        return current != null;
      }

      @Override
      public T next()
      {
        T data = current.getData();
        current = current.getNext();
        return null;
      }
    };
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    Node current = head;

    sb.append("HEAD -> ");

    while (current != null)
    {
      sb.append(current);
      sb.append(" -> ");
      current = current.getNext();
    }
    sb.append("null");
    return sb.toString();
  }
}
