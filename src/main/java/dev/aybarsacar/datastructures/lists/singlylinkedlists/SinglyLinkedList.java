package dev.aybarsacar.datastructures.lists.singlylinkedlists;

/**
 * Singly Linked List implementation for a Generic class
 */
public class SinglyLinkedList<T>
{
  private class Node<T>
  {
    private final T data;
    private Node<T> next;

    public Node(T data)
    {
      this.data = data;
    }

    public T getData()
    {
      return data;
    }

    public Node<T> getNext()
    {
      return next;
    }

    public void setNext(Node<T> next)
    {
      this.next = next;
    }

    @Override
    public String toString()
    {
      return data.toString();
    }
  }

  private Node<T> head;           // head of the singly linked list
  private int size;               // Size of the list, convenience variable

  public SinglyLinkedList()
  {
    size = 0;
  }

  public SinglyLinkedList(Node<T> head)
  {
    this.head = head;
    size = 1;
  }

  public Node<T> getHead()
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
    Node<T> nodeToAdd = new Node<>(data);
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

    Node<T> nodeToRemove = head;

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

    Node<T> previous = head;
    Node<T> current = head.getNext();

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

    Node<T> previous = head;
    for (int i = 0; i < location - 1; i++) previous = previous.getNext();

    Node<T> nodeToRemove = previous.getNext();

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
    Node<T> current = head;
    while (current != null)
    {
      if (current.getData() == value)
      {
        return true;
      }
      current = current.getNext();
    }
    return false;
  }

  /**
   * returns the first encountered location of the value if it exists
   * if not returns -1
   *
   * @param value
   * @return
   */
  public int getItem(T value)
  {
    Node<T> current = head;
    int location = 0;
    while (current != null)
    {
      if (current.getData() == value)
      {
        return location;
      }
      current = current.getNext();
      location++;
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

    Node<T> current = head;
    for (int i = 0; i < location; i++) current = current.getNext();

    return current.getData();
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    Node<T> current = head;

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
