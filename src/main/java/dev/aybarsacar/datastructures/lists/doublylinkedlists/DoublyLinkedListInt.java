package dev.aybarsacar.datastructures.lists.doublylinkedlists;

/**
 * Doubly Linked List implementation that stores the NodeDLL class
 */
public class DoublyLinkedListInt
{
  private NodeDLL head;               // first node
  private NodeDLL tail;               // last node
  private int size;                   // size of the list

  public DoublyLinkedListInt()
  {
    size = 0;
  }

  public DoublyLinkedListInt(int value)
  {
    NodeDLL node = new NodeDLL(value);

    this.head = node;
    this.tail = node;
    size = 1;
  }

  public NodeDLL getHead()
  {
    return head;
  }

  public NodeDLL getTail()
  {
    return tail;
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
   * add an object to the tail of the list
   *
   * @param value
   */
  public void add(int value)
  {
    addLast(value);
  }

  /**
   * add an object to the head of the list
   *
   * @param value
   */
  public void addFirst(int value)
  {
    NodeDLL nodeToAdd = new NodeDLL(value);

    if (size == 0)
    {
      head = nodeToAdd;
      tail = nodeToAdd;
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
   * add an object to the tail of the list
   *
   * @param value
   */
  public void addLast(int value)
  {
    NodeDLL nodeToAdd = new NodeDLL(value);

    if (size == 0)
    {
      head = nodeToAdd;
      tail = nodeToAdd;
    }
    else
    {
      nodeToAdd.setPrevious(tail);
      tail.setNext(nodeToAdd);
      tail = nodeToAdd;
    }
    size++;
  }

  /**
   * adds the items in a sorted order
   *
   * @param numberToAdd
   */
  public void addSorted(int numberToAdd)
  {
    if (head == null || head.getData() > numberToAdd)
    {
      addFirst(numberToAdd);
      return;
    }
    if (tail.getData() < numberToAdd)
    {
      addLast(numberToAdd);
      return;
    }

//    find the insertion location
    NodeDLL nextNode = head;
    while (nextNode != null && nextNode.getData() < numberToAdd)
    {
      nextNode = nextNode.getNext();
    }

    NodeDLL nodeToAdd = new NodeDLL(numberToAdd);
    NodeDLL prevNode = nextNode.getPrevious();

    nextNode.setPrevious(nodeToAdd);
    prevNode.setNext(nodeToAdd);
    nodeToAdd.setNext(nextNode);
    nodeToAdd.setPrevious(prevNode);

    size++;
  }

  /**
   * returns true if the value exists in our list
   *
   * @param value
   * @return
   */
  public boolean contains(int value)
  {
    if (isEmpty()) return false;

    NodeDLL current = head;
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
   * returns the value at the location
   *
   * @param location
   * @return
   */
  public int get(int location)
  {
    if (location >= size) throw new IndexOutOfBoundsException();

    NodeDLL current = head;
    for (int i = 0; i < location; i++) current = current.getNext();

    return current.getData();
  }

  /**
   * returns the location of the value
   * returns -1 if it doesn't exists
   *
   * @param value
   * @return
   */
  public int indexOf(int value)
  {
    NodeDLL current = head;
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
   * removes the head and returns the value
   * if the list is empty returns null
   *
   * @return
   */
  public Integer removeFirst()
  {
    if (isEmpty()) return null;

    NodeDLL nodeToRemove = head;

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
   * removes the tail and returns the value
   *
   * @return
   */
  public Integer removeLast()
  {
    if (isEmpty()) return null;

    NodeDLL nodeToRemove = tail;

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
  public boolean removeItem(int value)
  {
    if (isEmpty()) return false;

    NodeDLL current = head;
    while (current != null)
    {
      if (current.getData() == value)
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
   * removes the item at a location and returns the value (data) stored in that node
   *
   * @param location
   * @return
   */
  public int remove(int location)
  {
    if (location >= size) throw new IndexOutOfBoundsException();

    NodeDLL current = head;
    for (int i = 0; i < location; i++) current = current.getNext();

    current.getPrevious().setNext(current.getNext());
    current.getNext().setPrevious(current.getPrevious());
    current.setNext(null);
    current.setPrevious(null);

    size--;

    return current.getData();
  }


  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();

    NodeDLL current = head;
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
