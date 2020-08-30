// Title: DS_My
// Files: DataStructureADT
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 2/7/19

// Outside Sources
// Persons: None
// Online Sources: None

/**
 * This is the author's implementation of DataStructureADT as a linked list
 * 
 * @author Michael Goldstein
 *
 * @param <K> The key type for all nodes in this data structure
 * @param <V> The value type for all nodes in this data structure
 */
public class DS_My<K extends Comparable<K>, V> implements DataStructureADT<K, V> {
  /**
   * An inner class that is a node in the linked list. Is a holder for the key and value of each
   * node.
   * 
   * @author Michael Goldstein
   *
   * @param <X> The key type of the node
   * @param <Y> The data type of the node
   */
  private class DataNode {
    private DataNode nextNode; // the node's next node in a linked list
    private K key; // the key for the node
    private V value; // the value the node stores

    /**
     * Constructor for DataNode class. Initializes all instance variables
     * 
     * @param key      The key for the node
     * @param value    The value the node stores
     * @param nextNode The node's next node in a linked list
     */
    public DataNode(K key, V value, DataNode nextNode) {
      this.key = key;
      this.value = value;
      this.nextNode = nextNode;
    }

    /**
     * Changes the next node of the current instance of a node
     * 
     * @param nextNode what the nextNode instance variable should be set to
     */
    public void setNext(DataNode nextNode) {
      this.nextNode = nextNode;
    }

    /**
     * Returns the current node instance's key
     * 
     * @return the current node instance's key
     */
    public K getKey() {
      return key;
    }

    /**
     * Returns the current node instance's value
     * 
     * @return the current node instance's value
     */
    public V getValue() {
      return value;
    }

    /**
     * Returns the current node's next node
     * 
     * @return the current node's next node
     */
    public DataNode getNext() {
      return nextNode;
    }
  }

  private DataNode head; // the head node of the linked list
  private int size; // the size of the linked list

  /**
   * The constructor for the linked list. Initializes all instance variables
   */
  public DS_My() {
    head = null;
    size = 0;
  }

  /**
   * Inserts a new element at the end of the linked list as a DataNode
   * 
   * @param key   the key of the new element to be added
   * @param value the value of the new element to be added
   * @throws IllegalArgumentException if the key is null
   * @throws RuntimeException         if the key is the same as that of an element already in the
   *                                  linked list
   */
  @Override
  public void insert(K key, V value) {
    // checks if the given key for a new element is null. Throws an exception if the key is null
    if (key == null) {
      throw new IllegalArgumentException("null key");
    }

    // throws an exception if the given key for a new element already exists in the data structure.
    if (this.contains(key)) {
      throw new RuntimeException("duplicate key");
    }

    // adds node
    if (head == null) {
      head = new DataNode(key, value, null); // if head is null, creates a head node
    } else {
      DataNode curr = head; // if head is not null, indexes through list to find the tail node
      while (curr.getNext() != null) {
        curr = curr.getNext();
      }
      curr.setNext(new DataNode(key, value, null)); // adds new element as next node of tail node
    }
    size++; // increments size by one
  }

  /**
   * Searches through the linked list to remove a node with the given key. Returns true if a node
   * with the given key was found and removed. Returns false if not
   * 
   * @param key the key of the node to be removed
   * @throws IllegalArgumentExcpetion if the given key is null
   */
  @Override
  public boolean remove(K key) {
    // checks if the given key for an element is null. Throws an exception if the key is null
    if (key == null) {
      throw new IllegalArgumentException("null key");
    }

    // searches through list to see if a node with the given key exists
    if (head != null) {
      // if head is the node with the matching key, removes head
      if (head.getKey().equals(key)) {
        head = head.getNext();
        size--;
        return true;
      } else {
        DataNode curr = head;
        // this loop checks through all nodes besides head
        while (curr.getNext() != null) {
          if (curr.getNext().getKey().equals(key)) { // checks if node has same key
            curr.setNext(curr.getNext().getNext()); // removes node
            size--;
            return true;
          } else {
            curr = curr.getNext();
          }
        }
      }
    }
    return false; // returns false if list empty (head is null) or no nodes have the given key
  }

  /**
   * Searches through the linked list to check a node with the given key exists. Returns true if a
   * node with the given key was found. Returns false if not
   * 
   * @param key the key of the node to be found
   */
  @Override
  public boolean contains(K key) {
    // checks if the given key for an element is null. Returns false if the key is null
    if (key == null) {
      return false;
    }

    // searches through list to see if a node with the given key exists
    if (head != null) {
      DataNode curr = head;
      while (curr != null) {
        if (curr.getKey().equals(key)) { // checks if node has same key
          return true; // returns true if node does have same key
        } else {
          curr = curr.getNext();
        }
      }
    }
    return false; // returns false if list empty (head is null) or no nodes have the given key
  }

  /**
   * Searches through the linked list to check a node with the given key exists. Returns the value
   * of the node with the given key, or null if no node with that key does not exist
   * 
   * @param key the key of the node to be found
   * @throws IllegalArgumentExcpetion if the given key is null
   */
  @Override
  public V get(K key) {
    // checks if the given key for an element is null. Throws an exception if the key is null
    if (key == null) {
      throw new IllegalArgumentException("null key");
    }

    // searches through list to see if a node with the given key exists
    if (head != null) {
      DataNode curr = head;
      while (curr != null) {
        if (curr.getKey().equals(key)) { // checks if node has same key
          return curr.getValue(); // returns true if node does have same key
        } else {
          curr = curr.getNext();
        }
      }
    }
    return null; // returns false if list empty (head is null) or no nodes have the given key
  }

  /**
   * Returns size of linked list
   * 
   * @return size of linked list
   */
  @Override
  public int size() {
    return size;
  }
}
