package dev.aybarsacar.datastructures.lists.singlylinkedlists;

/**
 * Node implementation class to be stored in a Singly Linked List implementation
 * stores integer as a data
 * Boxed integer so it could hold null data
 */
public class NodeCLL
{
  private final Integer data;
  private NodeCLL next;

  public NodeCLL(Integer data)
  {
    this.data = data;
  }

  public Integer getData()
  {
    return data;
  }

  public NodeCLL getNext()
  {
    return next;
  }

  public void setNext(NodeCLL next)
  {
    this.next = next;
  }

  @Override
  public String toString()
  {
    return data.toString();
  }
}
