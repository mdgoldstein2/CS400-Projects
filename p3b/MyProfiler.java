// Title: MyProfiler
// Files: HashTable, TreeMap
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 3/28/19

import java.util.TreeMap;

/**
 * Class which tests whether or not the author's implementation of a hashTable is more efficient
 * than Java's TreeMap Class
 * 
 * @author Michael Goldstein
 *
 * @param <K> the key type used by the TreeMap and HashTable
 * @param <V> the value type stored in the TreeMap and HashTable
 * 
 * @see HashTable
 * @see TreeMap
 */
public class MyProfiler<K extends Comparable<K>, V> {
  HashTableADT<K, V> hashtable; // the hash table to be used in testing
  TreeMap<K, V> treemap; // the tree map to be used in testing

  /**
   * Constructor which initializes hash table and tree map for testing
   */
  public MyProfiler() {
    hashtable = new HashTable<K, V>();
    treemap = new TreeMap<K, V>();
  }

  /**
   * Inserts an entry with the given key and value into the hash table and the tree map
   * 
   * @param key   the key of the entry to be inserted
   * @param value the value of the entry to be inserted
   * 
   * @throws DuplicateKeyException   if the given key is already in the hash table (and tree)
   *                                 (thrown by hash table insert)
   * @throws IllegalNullKeyException if the given key is null (throws by hash table insert)
   * 
   * @see HashTable#insert(Comparable, Object)
   * @see TreeMap#put(Object, Object)
   */
  public void insert(K key, V value) throws DuplicateKeyException, IllegalNullKeyException {
    hashtable.insert(key, value); // inserts an entry with the given key and value into hash table
    treemap.put(key, value); // inserts an entry with the given key and value into tree map
  }

  /**
   * Runs "get" methods on the hash table using the given key. The get() methods return the value
   * associated with the given key.
   * 
   * @param key the key of the element in the tree and hash table whose value should be returned
   * 
   * @throws KeyNotFoundException    if the given key is not in the hash table (and tree) (thrown by
   *                                 hash table get)
   * @throws IllegalNullKeyException if the given key is null (throws by hash table get)
   * 
   * @see HashTable#get(Comparable)
   * @see TreeMap#get(Object)
   */
  public void retrieve(K key) throws KeyNotFoundException, IllegalNullKeyException {
    hashtable.get(key); // gets the value of an entry with the given key from hash table
    treemap.get(key); // gets the value of an entry with the given key from hash table
  }

  /**
   * Driver method which inserts and removes the given number of elements to test runtime
   * performance of hash table and tree map
   * 
   * @param args input from command line which determines number of inserts and removes
   */
  public static void main(String[] args) {
    try {
      int numElements = Integer.parseInt(args[0]); // gets value from command line

      // creates instance of profiler which will add and get elements
      MyProfiler<Integer, Integer> profiler = new MyProfiler<Integer, Integer>();

      // adds the given number of items
      for (int index = 0; index < numElements; index++) {
        profiler.insert(index, index);
      }

      // runs the retrieve operation on number of items
      for (int index = 0; index < numElements; index++) {
        profiler.retrieve(index);
      }

      // prints out message on completion
      String msg = String.format("Inserted and retreived %d (key,value) pairs", numElements);
      System.out.println(msg);
    } catch (Exception e) {
      // prints out message in case any exception is thrown as none should be thrown
      System.out
          .println("Usage: java MyProfiler failed! A(n) " + e.getClass().getName() + " was thrown");
      System.exit(1);
    }
  }
}
