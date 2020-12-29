package dev.aybarsacar.datastructures.lists.doublylinkedlists;

import java.util.Iterator;

/**
 * Doubly Linked list implementation
 */
public class DoublyLinkedList<T> implements Iterable<T>
{
  private class Node
  {
    private T data;
    private Node previous, next;

    public Node(T data)
    {
      if (data == null) throw new IllegalArgumentException("Data cannot be null");
      this.data = data;
    }

    public Node(T data, Node previous, Node next)
    {
      if (data == null) throw new IllegalArgumentException("Data cannot be null");
      this.data = data;
      this.previous = previous;
      this.next = next;
    }

    public T getData()
    {
      return data;
    }

    public void setData(T data)
    {
      this.data = data;
    }

    public Node getPrevious()
    {
      return previous;
    }

    public void setPrevious(Node previous)
    {
      this.previous = previous;
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

  private int size = 0;                     // size of the list
  private Node head = null;                 // first node
  private Node tail = null;                 // last node

  /**
   * empties the list so it is garbage collected by Java
   * Time Complexity O(n)
   */
  public void clear()
  {
    Node current = head;
    while (current != null)
    {
      current.setData(null);
      current.setNext(null);
      current.setPrevious(null);

      current = current.getNext();
    }
    head = tail = current = null;
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

  public void add(T value)
  {
    addLast(value);
  }

  public void addLast(T value)
  {
    Node nodeToAdd = new Node(value);

    if (isEmpty())
    {
      head = tail = nodeToAdd;
    }
    else
    {
      nodeToAdd.setPrevious(tail);
      tail.setNext(nodeToAdd);
      tail = nodeToAdd;
    }
    size++;
  }

  public void addFirst(T value)
  {
    Node nodeToAdd = new Node(value);

    if (isEmpty())
    {
      head = tail = nodeToAdd;
    }
    else
    {
      nodeToAdd.setNext(head);
      head.setPrevious(nodeToAdd);
      head = nodeToAdd;
    }
    size++;
  }

  /**
   * inserts at a given index
   *
   * @param index
   */
  public void add(int index, T value)
  {
    if (index < 0) throw new IllegalArgumentException("Illegal Index");

    if (index == 0)
    {
      addFirst(value);
      return;
    }

    if (index == size)
    {
      addLast(value);
      return;
    }

    Node current = head;
    for (int i = 0; i < index - 1; i++) current = current.getNext();

    Node nodeToAdd = new Node(value, current, current.getNext());
    current.getNext().setPrevious(nodeToAdd);
    current.setNext(nodeToAdd);

    size++;
  }

  /**
   * @return the value of head
   */
  public T peekFirst()
  {
    if (isEmpty()) throw new RuntimeException("Empty List");
    return head.getData();
  }

  /**
   * @return the value of tail
   */
  public T peekLast()
  {
    if (isEmpty()) throw new RuntimeException("Empty List");
    return tail.getData();
  }

  /**
   * removes and returns the head of the list
   *
   * @return
   */
  public T removeFirst()
  {
    if (isEmpty()) throw new RuntimeException("Empty List");

    Node nodeToRemove = head;

    if (head.getNext() == null)
    {
      tail = null;
    }
    else
    {
      head.getNext().setPrevious(null);
    }
    head = nodeToRemove.getNext();
    nodeToRemove.setNext(null);
    size--;

    return nodeToRemove.getData();
  }

  /**
   * removes and returns the tail of the list
   *
   * @return
   */
  public T removeLast()
  {
    if (isEmpty()) return null;

    Node nodeToRemove = tail;

    if (tail.getPrevious() == null)
    {
      head = null;
    }
    else
    {
      tail.getPrevious().setNext(null);
    }
    tail = nodeToRemove.getPrevious();
    nodeToRemove.setPrevious(null);
    size--;

    return nodeToRemove.getData();
  }

  /**
   * removes the first instance of item if it exists and returns true
   * otherwise returns false
   *
   * @param value
   * @return
   */
  public boolean removeItem(T value)
  {
    if (isEmpty()) return false;

    Node current = head;
    while (current != null)
    {
      if (current.getData().equals(value))
      {
        current.getPrevious().setNext(current.getNext());
        current.getNext().setPrevious(current.getPrevious());
        current.setNext(null);
        current.setPrevious(null);

        size--;
        return true;
      }
      current = current.getNext();
    }

    return false;
  }

  /**
   * returns the first index of the value encountered
   * returns -1 if the value does not exist
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
      if (current.getData().equals(value))
      {
        return index;
      }
      current = current.getNext();
      index++;
    }
    return -1;
  }

  public boolean contains(T value)
  {
    return indexOf(value) != 1;
  }

  /**
   * removes an element at a given index and returns the value
   *
   * @param index
   * @return
   */
  public T removeAt(int index)
  {
    if (index < 0 || index >= size) throw new IndexOutOfBoundsException();

    Node current;
    int i;
    if (index < size / 2)
    {
      for (i = 0, current = head; i != index; i++) current = current.getNext();
    }
    else
    {
      for (i = size - 1, current = tail; i != index; i--) current = current.getPrevious();
    }

    current.getPrevious().setNext(current.getNext());
    current.getNext().setPrevious(current.getPrevious());
    current.setNext(null);
    current.setPrevious(null);

    size--;

    return current.getData();
  }

  @Override
  public Iterator<T> iterator()
  {
    return new Iterator<>()
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
        return data;
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
      sb.append(" <=> ");
      current = current.getNext();
    }
    sb.append("null");
    return sb.toString();
  }
}
