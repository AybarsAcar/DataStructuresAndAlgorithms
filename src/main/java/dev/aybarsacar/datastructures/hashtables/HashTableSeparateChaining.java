package dev.aybarsacar.datastructures.hashtables;

import java.util.*;

/**
 * this is a chained hash table implementation
 * each array index holds a liked list of the hashed values
 */
@SuppressWarnings("unchecked")
public class HashTableSeparateChaining<K, V> implements Iterable<K>
{
  /**
   * represents the element stored in the hash table
   * key, value pairs
   */
  private class Entry
  {
    K key;
    V value;
    int hash;

    public Entry(K key, V value)
    {
      this.key = key;
      this.value = value;
      this.hash = key.hashCode();
    }

    public boolean equals(Entry o)
    {
      if (hash != o.hash) return false;
      return key.equals(o.key);
    }
  }

  private static final int DEFAULT_CAPACITY = 3;
  private static final double DEFAULT_LOAD_FACTOR = 0.75;

  private LinkedList<Entry>[] hashtable;
  private int capacity, threshold, size = 0;
  private double maxLoadFactor;

  public HashTableSeparateChaining()
  {
    this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
  }

  public HashTableSeparateChaining(int capacity)
  {
    this(capacity, DEFAULT_LOAD_FACTOR);
  }

  public HashTableSeparateChaining(int capacity, double maxLoadFactor)
  {
    if (capacity < 0) throw new IllegalArgumentException("Capacity must be greater than 0");
    if (maxLoadFactor <= 0 || Double.isNaN(maxLoadFactor) || Double.isInfinite(maxLoadFactor))
      throw new IllegalArgumentException("Illegal maxLoadFactor");

    this.capacity = Math.max(DEFAULT_CAPACITY, capacity);
    this.maxLoadFactor = maxLoadFactor;
    threshold = (int) (this.capacity * maxLoadFactor);
    hashtable = new LinkedList[this.capacity];

    for (int i = 0; i < hashtable.length; i++)
    {
      hashtable[i] = new LinkedList<>();
    }
  }

  public int size()
  {
    return size;
  }

  public boolean isEmpty()
  {
    return size == 0;
  }

  public void clear()
  {
    Arrays.fill(hashtable, null);
    size = 0;
  }

  /**
   * inserts a key value pair into the hash table
   *
   * @param key
   * @param value
   */
  public void put(K key, V value)
  {
    if (key == null) throw new IllegalArgumentException("Null key");

    int hashedKey = hashKey(key);
    hashtable[hashedKey].add(new Entry(key, value));
  }

  /**
   * @param key - raw key of the element
   * @return - the value for the key if the key exists, otherwise null
   */
  public V get(K key)
  {
    int hashedKey = hashKey(key);
    for (Entry entry : hashtable[hashedKey])
    {
      if (entry.key.equals(key))
      {
        return entry.value;
      }
    }
    return null;
  }

  /**
   * removes the element and returns it if it exists with the key
   *
   * @param key
   * @return
   */
  public V remove(K key)
  {
    int hashedKey = hashKey(key);
    for (Entry entry : hashtable[hashedKey])
    {
      if (entry.key.equals(key))
      {
        hashtable[hashedKey].remove(entry);
        return entry.value;
      }
    }
    return null;
  }

  /**
   * Simple hashing algorithm to index the elements
   *
   * @param key raw key of the element
   * @return the index location in the hashtable
   */
  private int hashKey(K key)
  {
    return Math.abs(key.hashCode()) % hashtable.length;
  }

  private void resizeHashTable()
  {
    capacity *= 2;
    threshold = (int) (capacity * maxLoadFactor);

    LinkedList<Entry>[] newTable = new LinkedList[capacity];

    for (int i = 0; i < hashtable.length; i++)
    {
      if (hashtable[i] != null)
      {

        for (Entry entry : hashtable[i])
        {
          int bucketIndex = hashKey(entry.key);
          LinkedList<Entry> bucket = newTable[bucketIndex];
          if (bucket == null) newTable[bucketIndex] = bucket = new LinkedList<>();
          bucket.add(entry);
        }

        // Avoid memory leak. Help the GC
        hashtable[i].clear();
        hashtable[i] = null;
      }
    }

    hashtable = newTable;
  }

  /**
   * returns a list of keys
   *
   * @return
   */
  public List<K> keys()
  {
    List<K> keys = new ArrayList<>(size());
    for (LinkedList<Entry> bucket : hashtable)
    {
      if (bucket != null)
        for (Entry entry : bucket)
        {
          keys.add(entry.key);
        }
    }
    return keys;
  }

  /**
   * returns a list of keys
   *
   * @return
   */
  public List<V> values()
  {
    List<V> values = new ArrayList<>(size());
    for (LinkedList<Entry> bucket : hashtable)
    {
      if (bucket != null)
        for (Entry entry : bucket)
        {
          values.add(entry.value);
        }
    }
    return values;
  }

  /**
   * returns an iterator to iterate over the keys of the table
   *
   * @return
   */
  @Override
  public Iterator<K> iterator()
  {
    final int elementCount = size();

    return new Iterator<K>()
    {
      int bucketIdx = 0;
      Iterator<Entry> bucketIter = (hashtable[0] == null) ? null : hashtable[0].iterator();

      @Override
      public boolean hasNext()
      {
//        an item added or removed while iterating
        if (elementCount != size) throw new ConcurrentModificationException();

//        no iterator or the current iterator is empty
        if (bucketIter == null || !bucketIter.hasNext())
        {
          while (++bucketIdx < capacity)
          {
            if (hashtable[bucketIdx] != null)
            {
              Iterator<Entry> nextIter = hashtable[bucketIdx].iterator();
              if (nextIter.hasNext())
              {
                bucketIter = nextIter;
                break;
              }
            }
          }
        }
        return bucketIdx < capacity;
      }

      @Override
      public K next()
      {
        return bucketIter.next().key;
      }
    };
  }

  @Override
  public String toString()
  {
    StringBuilder sb = new StringBuilder();

    sb.append("{\n");
    for (LinkedList<Entry> entries : hashtable)
    {
      if (entries == null) continue;
      for (Entry entry : entries)
        sb.append("\t").append(entry.key).append(": ").append(entry.value).append(", \n");
    }
    sb.append("}");
    return sb.toString();
  }
}
