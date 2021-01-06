package dev.aybarsacar.algorithms.sorting;

// TODO: maybe combine the sort methods in one class
// TODO: contribute to Fibonacci class on github Java Algorithms
public class BubbleSort<T extends Comparable<T>> extends Sorter<T>
{
  public BubbleSort()
  {
  }

  @Override
  void sort(T[] input)
  {
    for (int lastUnsortedIndex = input.length - 1; lastUnsortedIndex > 0; lastUnsortedIndex--)
    {
      for (int i = 0; i < lastUnsortedIndex; i++)
      {
        if (input[i].compareTo(input[i + 1]) > 0)
        {
          swap(input, i, i + 1);
        }
      }
    }
  }
}
