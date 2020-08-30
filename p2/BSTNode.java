// Title: BSTNode
// Files: None
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein (submitter, but created by instructors)
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 2/21/19

// Outside Sources
// Persons: None
// Online Sources: None

// Students may use and edit this class as they choose
// Students may add or remove or edit fields, methods, constructors for this class
// Students may inherit from an use this class in any way internally in other classes.
// Students are not required to use this class.
// BUT, IF YOUR CODE USES THIS CLASS, BE SURE TO SUBMIT THIS CLASS
//
// RECOMMENDED: do not use public or private visibility modifiers
// package level access means that all classes in the package can access directly.
//
// Classes that use this type: <BST>

/**
 * A class which is a node in a binary search tree.
 * 
 * @author Michael Goldstein (submitter, actually created by instructors_
 *
 * @param <K> the type which the key of the BSTNode is.
 * @param <V> the type that the value of the BSTNode is storing
 */
class BSTNode<K, V> {
  // instance variables. Are package protected for access purposes
  K key; // key of the BSTNode
  V value; // the value of the BSTNode
  BSTNode<K, V> left; // the left child of the BSTNode
  BSTNode<K, V> right; // the right child of the BSTNode

  /**
   * Constructor for BSTNode. Initializes instance variables
   * 
   * @param key        the key of the BSTNode. Used to place BSTNode in tree
   * @param value      the value the BSTNode is storing
   * @param leftChild  the left child of the BSTNode
   * @param rightChild the right child of the BSTNodeS
   */
  BSTNode(K key, V value, BSTNode<K, V> leftChild, BSTNode<K, V> rightChild) {
    this.key = key;
    this.value = value;
    this.left = leftChild;
    this.right = rightChild;
  }

  /**
   * Additional Constructor for BSTNode. Calls previous constructor with leftChild and rightChild
   * arguments as null.
   * 
   * @param key
   * @param value
   */
  BSTNode(K key, V value) {
    this(key, value, null, null);
  }


}
