package dev.aybarsacar.datastructures.lists.singlylinkedlists;

/**
 * Singly Linked List implementation that stores the Node class
 */
public class SinglyLinkedListInt
{
  private NodeCLL head;       // first node
  private int size;           // convenience variable to return the size of the list in constant time

  public SinglyLinkedListInt()
  {
    size = 0;
  }

  public SinglyLinkedListInt(NodeCLL head)
  {
    this.head = head;
    size = 1;
  }

  public NodeCLL getHead()
  {
    return head;
  }

  public int getSize()
  {
    return size;
  }

  public boolean isEmpty()
  {
    return size == 0;
  }

  /**
   * adds a new node to the beginning of the list
   *
   * @param n
   */
  public void add(int n)
  {
    NodeCLL nodeToAdd = new NodeCLL(n);
    nodeToAdd.setNext(head);
    head = nodeToAdd;
    size++;
  }

  /**
   * removes and returns the head
   *
   * @return the removed node(head)
   */
  public NodeCLL remove()
  {
    if (isEmpty()) return null;

    NodeCLL nodeToRemove = head;
    head = head.getNext();

    nodeToRemove.setNext(null);

    size--;

    return nodeToRemove;
  }

  /**
   * returns the first position of the data if it exists
   * if not returns null
   *
   * @param data
   * @return
   */
  public int contains(int data)
  {
    int pos = 0;
    NodeCLL current = head;

    while (current != null)
    {
      if (current.getData() == data)
      {
        return pos;
      }

      current = current.getNext();
      pos++;
    }
    return -1;
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    NodeCLL current = head;

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
