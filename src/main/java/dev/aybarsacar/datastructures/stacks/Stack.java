package dev.aybarsacar.datastructures.stacks;

public interface Stack<T>
{
  public void push(T elem);

  public T pop();

  public T peek();

  public int size();

  public boolean isEmpty();
}
