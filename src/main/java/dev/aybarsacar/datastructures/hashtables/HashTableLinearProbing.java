package dev.aybarsacar.datastructures.hashtables;

/**
 * An implementation of a hash-table that uses open addressing to deal with collisions
 * Linear Probing is implemented for the probing function
 *
 * @param <K> key
 * @param <V> value
 */
public class HashTableLinearProbing<K, V> extends HashTableOpenAddressingBase<K, V>
{
  //  could be any number but make sure to start it as a prime number
  //  to avoid possible infinite loops due to smaller cycles than the length of the hash-table
  private static final int LINEAR_CONSTANT = 17;

  public HashTableLinearProbing()
  {
    super();
  }

  public HashTableLinearProbing(int capacity)
  {
    super(capacity);
  }

  public HashTableLinearProbing(int capacity, double loadFactor)
  {
    super(capacity, loadFactor);
  }

  @Override
  protected void setupProbing(K key)
  {
  }

  @Override
  protected int probe(int x)
  {
    return LINEAR_CONSTANT * x;
  }

  /**
   * makes sure the linear constant and the table length are relatively prime
   * to avoid infinite loops
   */
  @Override
  protected void adjustCapacity()
  {
    while (gcd(LINEAR_CONSTANT, capacity) != 1)
    {
      capacity++;
    }
  }
}
