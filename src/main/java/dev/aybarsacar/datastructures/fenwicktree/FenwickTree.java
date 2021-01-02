package dev.aybarsacar.datastructures.fenwicktree;

import java.util.Arrays;

/**
 * A Fenwick Tree which supports point updates and range queries
 */
public class FenwickTree
{
  final int N;                      // the size of the array holding the Fenwick Tree values
  private long[] tree;              // the array containing the Fenwick Tree ranges

  public FenwickTree(int sz)
  {
    tree = new long[(N = sz + 1)];
  }

  /**
   * @param values that our array has at each index
   */
  public FenwickTree(long[] values)
  {
    if (values == null) throw new IllegalArgumentException("Null values");

    N = values.length;
    values[0] = 0L;

    tree = values.clone();

    for (int i = 1; i < N; i++)
    {
      int parent = i + lsb(i);
      if (parent < N) tree[parent] += tree[i];
    }
  }

  /**
   * returns the value of the least significant bit of an integer
   *
   * @param i
   * @return
   */
  private static int lsb(int i)
  {
    return i & -i;
  }

  /**
   * computes the prefix sum from [1, i]
   * time complexity: O(log(n))
   *
   * @param i
   * @return
   */
  private long prefixSum(int i)
  {
    long sum = 0L;

//    cascade down
    while (i != 0)
    {
      sum += tree[i];
      i &= ~lsb(i);     // same as i -= lsb(i) but faster because compiles into less assembly code
    }
    return sum;
  }

  /**
   * returns the sum of the interval [lower, upper]
   * Time complexity: O(log(n))
   *
   * @param lower lower bound index
   * @param upper upper bound index
   * @return
   */
  public long sum(int lower, int upper)
  {
    if (upper < lower)
      throw new IllegalArgumentException("Upper Bound must be greater than the Lower Bound");

    return prefixSum(upper) - prefixSum(lower - 1);
  }

  /**
   * get the value at index i
   *
   * @param i
   * @return
   */
  public long get(int i)
  {
    return sum(i, i);
  }

  /**
   * add value v at index i
   * Time complexity: O(log(n))
   *
   * @param i index
   * @param v value
   */
  public void add(int i, long v)
  {
//    update all the parents that are responsible for you
    while (i < N)
    {
      tree[i] += v;
      i += lsb(i);
    }
  }

  /**
   * update the value at index i with the value v
   * Time complexity: O(log(n))
   *
   * @param i index
   * @param v value
   */
  public void set(int i, long v)
  {
    add(i, v - sum(i, i));
  }

  @Override
  public String toString()
  {
    return Arrays.toString(tree);
  }
}
