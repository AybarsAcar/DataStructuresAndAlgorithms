package dev.aybarsacar.datastructures.lists.doublylinkedlists;

/**
 * Node implementation class to be stored in a Doubly Linked List implementation
 * stores integer as a data
 * Boxed integer so it could hold null data
 */
public class NodeDLL
{
  private final Integer data;
  private NodeDLL next;
  private NodeDLL previous;

  public NodeDLL(Integer data)
  {
    this.data = data;
  }

  public Integer getData()
  {
    return data;
  }

  public NodeDLL getNext()
  {
    return next;
  }

  public void setNext(NodeDLL next)
  {
    this.next = next;
  }

  public NodeDLL getPrevious()
  {
    return previous;
  }

  public void setPrevious(NodeDLL previous)
  {
    this.previous = previous;
  }

  @Override
  public String toString()
  {
    return data.toString();
  }
}
