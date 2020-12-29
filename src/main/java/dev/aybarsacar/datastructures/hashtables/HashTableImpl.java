package dev.aybarsacar.datastructures.hashtables;

import java.util.LinkedList;

/**
 * this is a chained hash table implementation
 * each array index holds a liked list of the hashed values
 * TODO: research on more methods
 */
public class HashTableImpl<K, T>
{
  /**
   * represents the element stored in the hash table
   * key, value pairs
   */
  private class Element
  {
    K key;
    T value;

    public Element(K key, T value)
    {
      this.key = key;
      this.value = value;
    }
  }

  private LinkedList<Element>[] hashtable;
  private int numElements = 0;

  public HashTableImpl()
  {
    hashtable = new LinkedList[10];
  }

  public HashTableImpl(int initialSize)
  {
    hashtable = new LinkedList[initialSize];
  }

  /**
   * inserts a key value pair into the hash table
   *
   * @param key
   * @param value
   */
  public void put(K key, T value)
  {
    int hashedKey = hashKey(key);
    hashtable[hashedKey].add(new Element(key, value));
    numElements++;
  }

  /**
   * @param key - raw key of the element
   * @return - the value for the key if the key exists, otherwise null
   */
  public T get(K key)
  {
    int hashedKey = hashKey(key);
    for (Element element : hashtable[hashedKey])
    {
      if (element.key.equals(key))
      {
        return element.value;
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
  public T remove(K key)
  {
    int hashedKey = hashKey(key);
    for (Element element : hashtable[hashedKey])
    {
      if (element.key.equals(key))
      {
        hashtable[hashedKey].remove(element);
        return element.value;
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

  @Override
  public String toString()
  {
//    TODO: create a JSON looking toString
    return super.toString();
  }
}
