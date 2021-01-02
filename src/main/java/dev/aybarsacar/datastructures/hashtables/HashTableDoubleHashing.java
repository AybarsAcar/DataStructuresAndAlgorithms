package dev.aybarsacar.datastructures.hashtables;

import java.math.BigInteger;

/**
 * An implementation of a hashtable using double hashing as a collision resolution technique
 *
 * @param <K> key
 * @param <V> value
 */
public class HashTableDoubleHashing<K extends SecondaryHash, V> extends HashTableOpenAddressingBase<K, V>
{
  private int hash;

  public HashTableDoubleHashing()
  {
  }

  public HashTableDoubleHashing(int capacity)
  {
    super(capacity);
  }

  public HashTableDoubleHashing(int capacity, double loadFactor)
  {
    super(capacity, loadFactor);
  }

  @Override
  protected void setupProbing(K key)
  {
//    cache second hash value
    hash = normalizeIndex(key.hashCode2());

//    fail safe to avoid infinite loop
    if (hash == 0) hash = 1;
  }

  @Override
  protected int probe(int x)
  {
    return x * hash;
  }

  /**
   * adjust the capacity until it is a prime number. The reason for doing this is to help ensure that
   * GCD(hash, capacity) = 1 when probing so that all cells can be reached
   */
  @Override
  protected void adjustCapacity()
  {
    while (!(new BigInteger(String.valueOf(capacity)).isProbablePrime(20)))
    {
      capacity++;
    }
  }
}
