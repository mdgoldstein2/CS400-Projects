// Title: HashTable
// Files: HashTableADT, DataStructureADT
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 3/14/19

/**
 * The author's implementation of a hash table data structure
 * 
 * @author Michael Goldstein
 *
 * @param <K> The key type of the hash table class
 * @param <V> The value the has table stores
 */

import java.util.LinkedList;

// The hash function used is the hashCode of the key modulo table size
// The hash function uses LinkedList buckets in a hashTable array to handle collisions

public class HashTable<K extends Comparable<K>, V> implements HashTableADT<K, V> {
  /**
   * An inner class which acts as a node in a hash table to hold both a key and a value
   * 
   * @author Michael Goldstein
   *
   */
  private class HashTableNode {
    private K key; // the key of the HashTableNode
    private V value; // the value of the HashTableNode

    /**
     * Constructor for HashTableNode. Initializes key and value variables with the key and value
     * given by parameters.
     * 
     * @param key   The key of the HashTableNode
     * @param value The value of the HashTableNode
     */
    public HashTableNode(K key, V value) {
      this.key = key;
      this.value = value;
    }

    /**
     * Returns the key of the HashTableNode
     * 
     * @return the key of the HashTableNode
     */
    public K getKey() {
      return key;
    }

    /**
     * Returns the value of the HashTableNode
     * 
     * @return the value of the HashTableNode
     */
    public V getValue() {
      return value;
    }
  }

  // instance variables for HashTable
  private int currentNumberOfPairsInTable; // the current number of key value pairs in the table
  private double loadFactorThreshold; // the load factor at which the hash table will expand
  private LinkedList<HashTableNode>[] hashTable; // the hash table which stores key value pairs

  /**
   * The default constructor. Initializes instance variables by calling constructor with parameters.
   * Uses an initial capacity of 50 and a load factor threshold of 0.75
   * 
   * @see HashTable#HashTable(int, double)
   */
  public HashTable() {
    // calls other constructor with initial capacity of 50 and load factor threshold of 0.75
    this(50, 0.75);
  }


  /**
   * Constructor which has initial capacity and load factor threshold as parameters. It initializes
   * all instance variables and is called by the default constructor
   * 
   * @param initialCapacity     the initial maximum capacity of the hash table
   * @param loadFactorThreshold the the load factor at which the hash table will expand (resize and
   *                            rehash)
   */
  public HashTable(int initialCapacity, double loadFactorThreshold) {
    // initializes a hash table with a given size. Has casting from LinkedList to
    // LinkedList<HashTableNode> because constructor thrown when initializing an array of
    // HashtableNode
    hashTable = (LinkedList<HashTableNode>[]) new LinkedList[initialCapacity];

    // initializes current size (the number of pairs in table) and the load factor threshold
    currentNumberOfPairsInTable = 0;
    this.loadFactorThreshold = loadFactorThreshold;
  }

  /**
   * Method which transforms a key's hash code into an integer which is the index in the hash table
   * where the key, value pair for the key will be stored.
   * 
   * @param the key of the node to be inserted
   * @return the hash index for the given key
   */
  private int hashFunction(K key) {
    return Math.abs(key.hashCode()) % hashTable.length;
  }

  /**
   * Method which expands the size of the hash table when the load factor reaches the load factor
   * threshold
   */
  private void rehash() {
    // creates reference for current hash table so it is not lost on resizing
    LinkedList<HashTableNode>[] currentHashTable = hashTable;

    // makes hashTable refer to a new array with double the capacity. Does so so hashFunction will
    // work correctly (table size is correct) during rehashing
    hashTable = (LinkedList<HashTableNode>[]) new LinkedList[currentHashTable.length * 2 + 1];

    // loops through all buckets of the current hash table
    for (int index = 0; index < currentHashTable.length; index++) {
      if (currentHashTable[index] != null) { // makes sure that bucket is not null
        for (int index2 = 0; index2 < currentHashTable[index].size(); index2++) {
          // initializes linked list bucket where element will be rehashed to if it is not already
          // initialized
          if (hashTable[hashFunction(currentHashTable[index].get(index2).getKey())] == null) {
            hashTable[hashFunction(currentHashTable[index].get(index2).getKey())] =
                new LinkedList<HashTableNode>();
          }

          // rehashes element from current hash table to new hash table. Does not check
          // for duplicates as that was done on insertion
          hashTable[hashFunction(currentHashTable[index].get(index2).getKey())]
              .push(currentHashTable[index].get(index2));
        }
      }
    }
  }

  /**
   * Inserts a new key, value pair in the hash table, calls for a rehash of the table if necessary,
   * throws an IllegalNullKey exception if the given key is null, throws a DuplicateKeyException if
   * a duplicate key exists in the hash table
   * 
   * @param key   the key of the new element to be inserted into the hash table
   * @param value the value of the new element to be inserted into the hash table
   * @throws IllegalNullKeyException if given key is null
   * @throws DuplicateKeyException   if given key is already in hash table
   * @see HashTable#rehash()
   * @see HashTable#hashFunction(Comparable)
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null) { // if key is null, throws exception
      throw new IllegalNullKeyException();
    }

    // checks if adding one more element to the hash table would reach the load factor threshold
    // if doing so does, the hash table is rehashed before inserting another element to save
    // operation time
    if (((double) currentNumberOfPairsInTable + 1)
        / ((double) hashTable.length) >= loadFactorThreshold) {
      rehash();
    }

    // initializes bucket if it is not already initialized
    int hashIndex = hashFunction(key);
    if (hashTable[hashIndex] == null) {
      hashTable[hashIndex] = new LinkedList<HashTableNode>();
    }

    // checks if the linked list the new element is going into already has an element with the same
    // key
    for (int index = 0; index < hashTable[hashIndex].size(); index++) {
      if (hashTable[hashIndex].get(index).getKey().equals(key)) {
        throw new DuplicateKeyException();
      }
    }

    // inserts new element (key, value pair) at start of the list
    hashTable[hashIndex].push(new HashTableNode(key, value));
    currentNumberOfPairsInTable++;
  }

  /**
   * Removes a key value pair from the hash table with the given key. Returns true if a pair with
   * the given key is removed, false if the key is not in the hash table and therefore cannot be
   * removed.
   * 
   * @param key the key to be removed from the hash table
   * @return true if the key exists in the hash table and is removed successfully, false if not
   * @throws IllegalNullKeyException if the given key is null
   * @see HashTable#hashFunction(Comparable)
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException {
    if (key == null) { // if key is null, throws exception
      throw new IllegalNullKeyException();
    }

    // checks if the bucket where the key should be is initialized. If not, key is not in hash table
    int hashIndex = hashFunction(key);
    if (hashTable[hashIndex] == null) {
      return false; //
    }

    // searches through the list where the node with the given key should be based on the hash
    // function
    for (int index = 0; index < hashTable[hashIndex].size(); index++) {
      if (hashTable[hashIndex].get(index).getKey().equals(key)) {
        hashTable[hashIndex].remove(index); // removes the node with the given key from the list
        currentNumberOfPairsInTable--;
        return true;
      }
    }
    return false; // returns false if none of the nodes in the list have the given key
  }

  /**
   * Returns the value for the given key in the hash table. Throws an IllegalNullKeyException if the
   * given key is null. Throws KeyNotFoundException if key is not in the hash table
   * 
   * @param key the key whose value in the hash table is returned
   * @return the value for the given key in the hash table
   * @throws IllegalNullKeyException if the given key is null
   * @throws KeyNotFoundException    if the given key is not in the hash table
   * @see HashTable#hashFunction(Comparable)
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) { // if key is null, throws exception
      throw new IllegalNullKeyException();
    }

    // checks if the bucket where the key should be is initialized. If not, key is not in hash table
    int hashIndex = hashFunction(key);
    if (hashTable[hashIndex] == null) {
      // throws exception since key is not in hash table if bucket is not initialized
      throw new KeyNotFoundException();
    }

    // searches through the list where the node with the given key should be based on the hash
    // function
    for (int index = 0; index < hashTable[hashIndex].size(); index++) {
      if (hashTable[hashIndex].get(index).getKey().equals(key)) {
        return hashTable[hashIndex].get(index).getValue();
      }
    }

    // throws KeyNotFoundException if node with given key was not in list
    throw new KeyNotFoundException();
  }

  /**
   * Returns the number of keys (key value pairs in this case) stored in the hash table
   * 
   * @return the number of keys (key value pairs in this case) stored in the hash table
   */
  @Override
  public int numKeys() {
    return currentNumberOfPairsInTable;
  }

  /**
   * Returns the load factor threshold of the hash table
   * 
   * @return the load factor threshold of the hash table
   */
  @Override
  public double getLoadFactorThreshold() {
    return loadFactorThreshold;
  }

  /**
   * Returns the current load factor of the hash table (the number of pairs in the table divided by
   * the size of the table)
   * 
   * @return the current load factor of the hash table
   */
  @Override
  public double getLoadFactor() {
    // casts division to doubles as otherwise an integer result, 0 or 1, would be returned
    return ((double) currentNumberOfPairsInTable) / ((double) hashTable.length);
  }

  /**
   * Returns the capacity of the hash table (the hash table's size)
   * 
   * @return the capacity of the hash table
   */
  @Override
  public int getCapacity() {
    return hashTable.length;
  }

  /**
   * Method which returns an integer code corresponding to the collision resolution method used.
   * 
   * @return 5 because this implementation of a hash table uses an array of linked lists
   */
  @Override
  public int getCollisionResolution() {
    return 5;
  }
}
