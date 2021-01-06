package dev.aybarsacar.algorithms.sorting;

public abstract class Sorter<T>
{
  abstract void sort(T[] input);

  protected void swap(T[] array, int i, int j)
  {
    if (i == j)
    {
      return;
    }

    T temp = array[i];
    array[i] = array[j];
    array[j] = temp;
  }
}
