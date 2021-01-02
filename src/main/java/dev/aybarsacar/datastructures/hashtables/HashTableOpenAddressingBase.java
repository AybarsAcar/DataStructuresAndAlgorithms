package dev.aybarsacar.datastructures.hashtables;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;

/**
 * Base class for hash-tables that implements open addressing collision resolution techniques
 * such as linear and quadratic probing and double hashing
 *
 * @param <K> - Key
 * @param <V> - Value
 */
@SuppressWarnings("unchecked")
public abstract class HashTableOpenAddressingBase<K, V> implements Iterable<K>
{
  protected double loadFactor;
  protected int capacity, threshold, modificationCount = 0;

  //  count the total number of used buckets inside the hash-table (includes cells marked as deleted)
  protected int usedBuckets = 0;

  //  tracks the number of unique keys currently inside the hash-table
  protected int keyCount = 0;

  //  arrays to store the key value pairs
  protected K[] keys;
  protected V[] values;

  //  special market token used to indicate the deletion of a key value pair
  protected final K TOMBSTONE = (K) new Object();

  private static final int DEFAULT_CAPACITY = 2;
  private static final double DEFAULT_LOAD_FACTOR = 0.65;

  public HashTableOpenAddressingBase()
  {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  public HashTableOpenAddressingBase(int capacity)
  {
    this(capacity, DEFAULT_LOAD_FACTOR);
  }

  public HashTableOpenAddressingBase(int capacity, double loadFactor)
  {
    if (capacity <= 0) throw new IllegalArgumentException("Illegal capacity");
    if (loadFactor <= 0 || Double.isNaN(loadFactor) || Double.isInfinite(loadFactor))
      throw new IllegalArgumentException("Illegal load factor");

    this.loadFactor = loadFactor;
    this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
    adjustCapacity();
    threshold = (int) (this.capacity * loadFactor);

    keys = (K[]) new Object[this.capacity];
    values = (V[]) new Object[this.capacity];
  }

  /**
   * these 3 following methods are used to dictate how the probing is to actually occur for
   * whatever open addressing scheme we are implementing
   *
   * @param key
   */
  protected abstract void setupProbing(K key);

  protected abstract int probe(int x);

  /**
   * adjusts the capacity of the hash table after its been made larger
   * this is important that is implemented separately for each class since each probing function
   * requires a different strategy to adjust the capacity of the backing array to avoid cycles
   * less than the length of the backing array to avoid infinite loops
   */
  protected abstract void adjustCapacity();

  /**
   * naively increases the capacity of the hash table
   */
  protected void increaseCapacity()
  {
    capacity = (2 * capacity) + 1;
  }

  private void clear()
  {
    for (int i = 0; i < capacity; i++)
    {
      keys[i] = null;
      values[i] = null;
    }
    keyCount = usedBuckets = 0;
    modificationCount++;
  }

  public int size()
  {
    return keyCount;
  }

  public int getCapacity()
  {
    return capacity;
  }

  public boolean isEmpty()
  {
    return keyCount == 0;
  }

  public V put(K key, V value)
  {
    return insert(key, value);
  }

  public V add(K key, V value)
  {
    return insert(key, value);
  }

  public boolean containsKey(K key)
  {
    return hasKey(key);
  }

  /**
   * returns a list of keys
   *
   * @return
   */
  public List<K> keys()
  {
    List<K> hashTableKeys = new ArrayList<>();
    for (int i = 0; i < capacity; i++)
    {
      if (keys[i] != null && keys[i] != TOMBSTONE) hashTableKeys.add(keys[i]);
    }
    return hashTableKeys;
  }

  /**
   * returns a list of keys
   *
   * @return
   */
  public List<V> values()
  {
    List<V> hashTableValues = new ArrayList<>();
    for (int i = 0; i < capacity; i++)
    {
      if (keys[i] != null && keys[i] != TOMBSTONE) hashTableValues.add(values[i]);
    }
    return hashTableValues;
  }

  protected void resizeTable()
  {
    increaseCapacity();               // doubles the size
    adjustCapacity();                 // adjusts depending on the hashing and probing function we implement

    threshold = (int) (capacity * loadFactor);

    K[] oldKeyTable = (K[]) new Object[capacity];
    V[] oldValueTable = (V[]) new Object[capacity];

//    perform key table pointer swap
    K[] keyTableTmp = keys;
    keys = oldKeyTable;
    oldKeyTable = keyTableTmp;

//    perform value table pointer swap
    V[] valueTableTmp = values;
    values = oldValueTable;
    oldValueTable = valueTableTmp;

//    reset the key count and the buckets since we will now reinsert the values
    keyCount = usedBuckets = 0;

//    reinsert the key-value pairs to the new resized backing array
    for (int i = 0; i < oldKeyTable.length; i++)
    {
      if (oldKeyTable[i] != null || oldKeyTable[i] != TOMBSTONE)
      {
        insert(oldKeyTable[i], oldValueTable[i]);
      }
      oldKeyTable[i] = null;
      oldValueTable[i] = null;
    }
  }

  /**
   * converts the hash value into an index
   * bitwise operator is == Math.abs() -> domain [0, capacity)
   *
   * @param keyHash
   * @return
   */
  protected final int normalizeIndex(int keyHash)
  {
    return (keyHash & 0x7FFFFFFF) % capacity;
  }

  /**
   * finds the greatest common denominator of a and b
   *
   * @param a
   * @param b
   * @return
   */
  protected static final int gcd(int a, int b)
  {
    if (b == 0) return a;
    return gcd(b, a % b);
  }

  /**
   * inserts a key-value pair inside the hash table
   * or updates if the value already exists
   *
   * @param key
   * @param val
   * @return
   */
  public V insert(K key, V val)
  {
    if (key == null) throw new IllegalArgumentException("Null key");
    if (usedBuckets >= threshold) resizeTable();

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());

//    i - current index we are at at the hash table
//    j - the position of first tombstone we encounter if we encounter one, otherwise it's -1
//    x - the probe offset
    for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++)))
    {
//      the current slot was previously deleted
      if (keys[i] == TOMBSTONE)
      {
        if (j == -1) j = i; // update j to store Tombstone position index
      }
//      the current cell already contains a key
      else if (keys[i] != null)
      {
//        the key we are trying to insert already exists in the hash-table
//        so update its value with the most recent value
        if (keys[i].equals(key))
        {
          V oldValue = values[i];

          if (j == -1)
          {
            values[i] = val;
          }
          else
          {
//            we do a swap with the tombstone for small optimisation
            keys[i] = TOMBSTONE;
            values[i] = null;
            keys[j] = key;
            values[j] = val;
          }
          modificationCount++;
          return oldValue;
        }
      }
//      current cell is null so an insertion/update can occur
      else
      {
//        no previously encountered deleted buckets
        if (j == -1)
        {
          usedBuckets++;
          keyCount++;
          keys[i] = key;
          values[i] = val;
        }
//        previously seen a deleted bucket which is a tombstone.
//        Instead of inserting the new element at i where null
//        element is, insert it where the deleted token was found
        else
        {
          keyCount++;
          keys[j] = key;
          values[j] = val;
        }
        modificationCount++;
        return null;
      }
    }
  }

  /**
   * @param key
   * @return if the key exists
   */
  public boolean hasKey(K key)
  {
    if (key == null) throw new IllegalArgumentException("Null key");

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());

//    Start at the original hash value and probe until we find a spot where our key is or
//    hit a null element in which case our element does not exist
    for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++)))
    {
//      ignore deleted cells, but recor where the first index of a deleted cell is found to
//      perform lazy relocation later
      if (keys[i] == TOMBSTONE)
      {
        if (j == -1) j = i;
      }
//      we hit a non-null key, perhaps it's the one we're looking for
      else if (keys[i] != null)
      {
//        the key we want is in the hash-table
        if (keys[i].equals(key))
        {
//          If j != -1 this means we previously encountered a deleted cell.
//          We can perform an optimisation by swapping the entries in cells i and j
//          so that the next time we search for this key it will be found faster.
//          This is called lazy deletion / relocation
          if (j != -1)
          {
//            swap the key-value pairs of positions i and j
            keys[j] = keys[i];
            values[j] = values[i];
            keys[i] = TOMBSTONE;
            values[i] = null;
          }
          return true;
        }
      }
      else
      {
//        key was not found in the hash-table
        return false;
      }
    }
  }

  /**
   * get the value associated with the key
   *
   * @param key
   * @return
   */
  public V get(K key)
  {
    if (key == null) throw new IllegalArgumentException("Null key");

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());

//    Start at the original hash value and probe until we find a spot where our key is or
//    hit a null element in which case our element does not exist
    for (int i = offset, j = -1, x = 1; ; i = normalizeIndex(offset + probe(x++)))
    {
      if (keys[i] == TOMBSTONE)
      {
        if (j == -1) j = i;
      }
      else if (keys[i] != null)
      {
        if (keys[i].equals(key))
        {
          if (j != -1)
          {
            keys[j] = keys[i];
            values[j] = values[i];
            keys[i] = TOMBSTONE;
            values[i] = null;
            return values[j];
          }
          else
          {
            return values[i];
          }
        }
      }
//      element was not found in the hash-table
      else return null;
    }
  }

  /**
   * @param key
   * @return
   */
  public V remove(K key)
  {
    if (key == null) throw new IllegalArgumentException("Null key");

    setupProbing(key);
    final int offset = normalizeIndex(key.hashCode());

    for (int i = offset, x = 1; ; i = normalizeIndex(offset + probe(x++)))
    {
      if (keys[i] == TOMBSTONE) continue;

      if (keys[i] == null) return null;

      if (keys[i].equals(key))
      {
        keyCount--;
        modificationCount++;
        V oldValue = values[i];
        keys[i] = TOMBSTONE;
        values[i] = null;
        return oldValue;
      }
    }
  }

  @Override
  public Iterator<K> iterator()
  {
//    Before the iteration begins record the number of modifications done to the hash table.
//    this value should not change as we are iterating otherwise changes been made to the hash table
    final int MODIFICATION_COUNT = modificationCount;

    return new Iterator<K>()
    {
      int index, keysLeft = keyCount;

      @Override
      public boolean hasNext()
      {
        if (MODIFICATION_COUNT != modificationCount) throw new ConcurrentModificationException();
        return keysLeft != 0;
      }

      @Override
      public K next()
      {
        while (keys[index] == null || keys[index] == TOMBSTONE) index++;
        keysLeft--;
        return keys[index++];
      }
    };
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();

    sb.append("{\n");
    for (int i = 0; i < capacity; i++)
    {
      if (keys[i] != null || keys[i] != TOMBSTONE)
      {
        sb.append("\t").append(keys[i]).append(": ").append(values[i]).append(", \n");
      }
    }
    sb.append("}");
    return sb.toString();
  }
}
