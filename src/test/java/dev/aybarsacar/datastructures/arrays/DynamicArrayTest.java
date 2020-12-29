package dev.aybarsacar.datastructures.arrays;

import org.junit.jupiter.api.Test;

public class DynamicArrayTest
{
  DynamicArray<Integer> myArray;

  @Test
  public void test()
  {
    myArray = new DynamicArray<>();
    System.out.println(myArray);
    myArray.add(1);
    myArray.add(10);
    myArray.add(40);
    myArray.add(8);
    myArray.add(1);

    System.out.println(myArray);
  }
}
