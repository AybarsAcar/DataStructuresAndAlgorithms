package dev.aybarsacar.datastructures.stacks;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StackSLLTest
{
  StackSLL<Integer> stack;

  @BeforeEach
  public void init()
  {
    stack = new StackSLL<>();
    stack.push(10);
    stack.push(12);
  }

  @Test
  public void pushTest()
  {
    System.out.println(stack);

    stack.push(22);
    Assertions.assertEquals(22, stack.peek());
  }

  @Test
  public void popTest()
  {
    Integer poppedItem = stack.pop();
    Assertions.assertEquals(12, poppedItem);
    Assertions.assertEquals(1, stack.size());
  }
}
