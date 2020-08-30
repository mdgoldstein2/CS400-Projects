// Title: BST
// Files: BSTNode, List, ArrayList, LinkedList, Queue
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 2/21/19

// Outside Sources
// Persons: None
// Online Sources: None

import java.nio.channels.SeekableByteChannel;
import java.util.ArrayList; // allowed for creating traversal lists
import java.util.List; // required for returning List<K>
import java.util.Queue; // Queue used for level-order printing
import java.util.LinkedList; // Imported as it implements Queue and allows for simple queue usage

/**
 * The author's implementation of a binary search tree. Implements BSTADT interface.
 * 
 * @author Michael Goldstein
 *
 * @param <K> The key of each BST node. Used to determine which node goes where. Is Comparable
 * @param <V> The value each BST node stores.
 * @see BSTADT
 */
public class BST<K extends Comparable<K>, V> implements BSTADT<K, V> {
  // Tip: Use protected fields so that they may be inherited by AVL
  protected BSTNode<K, V> root;
  protected int numKeys; // number of keys in BST

  /**
   * Constructor for BST class. Initializes instance variables.
   */
  public BST() {
    root = null;
    numKeys = 0;
  }

  /**
   * Returns a List containing the preorder traversal of the BST. Calls a recursive helper to do so.
   * Returns an empty list if the tree is empty.
   * 
   * @return a List containing the preorder traversal of the BST.
   * @see BST#preOrderTraversalHelper
   */
  @Override
  public List<K> getPreOrderTraversal() {
    ArrayList<K> preOrderTraversalList = new ArrayList<K>(); // list to contain all keys in tree
    preOrderTraversalHelper(root, preOrderTraversalList);
    return preOrderTraversalList;
  }

  /**
   * Recursive helper for getPreOrderTraversal
   * 
   * @param n    the current BSTNode the helper is called on.
   * @param list the list the keys of the BSTNode's children and the BSTNode should be added to.
   * @see BST#getPreOrderTraversal()
   */
  private void preOrderTraversalHelper(BSTNode<K, V> n, List<K> list) {
    if (n != null) { // makes sure n is not null, as n==null is a base case where nothing happens
      list.add(n.key); // before recursive process has run on children, adds current node's key
      preOrderTraversalHelper(n.left, list); // calls helper on left child
      preOrderTraversalHelper(n.right, list); // calls helper on right child
    }
  }

  /**
   * Returns a List containing the postorder traversal of the BST. Calls a recursive helper to do
   * so. Returns an empty list if the tree is empty
   * 
   * @return a List containing the postorder traversal of the BST.
   * @see BST#postOrderTraversalHelper
   */
  @Override
  public List<K> getPostOrderTraversal() {
    ArrayList<K> postOrderTraversalList = new ArrayList<K>(); // list to contain all keys in tree
    postOrderTraversalHelper(root, postOrderTraversalList);
    return postOrderTraversalList;
  }

  /**
   * Recursive helper for getPostOrderTraversal.
   * 
   * @param n    the current BSTNode the helper is called on.
   * @param list the list the keys of the BSTNode's children and the BSTNode should be added to.
   * @see BST#getPostOrderTraversal()
   */
  private void postOrderTraversalHelper(BSTNode<K, V> n, List<K> list) {
    if (n != null) { // makes sure n is not null, as n==null is a base case where nothing happens
      postOrderTraversalHelper(n.left, list); // calls helper on left child
      postOrderTraversalHelper(n.right, list); // calls helper on right child
      list.add(n.key); // after recursive process has run on children, adds current node's key
    }
  }

  /**
   * Returns a List containing the level order traversal of the BST. Calls a recursive helper to do
   * so. Returns an empty list if the tree is empty
   * 
   * @return a List containing the level order traversal of the BST.
   * @see BST#levelOrderTraversalHelper
   */
  @Override
  public List<K> getLevelOrderTraversal() {
    ArrayList<K> levelOrderTraversalList = new ArrayList<K>(); // list to contain all keys in tree
    levelOrderTraversalHelper(root, levelOrderTraversalList);
    return levelOrderTraversalList;
  }

  /**
   * Non-recursive, queue-based helper for getLevelOrderTraversal.
   * 
   * @param n    the current BSTNode the helper is called on.
   * @param list the list the keys of the BSTNode's children and the BSTNode should be added to.
   * @see BST#getLevelOrderTraversal()
   */
  private void levelOrderTraversalHelper(BSTNode<K, V> n, List<K> list) {
    BSTNode<K, V> tempNode = n; // temporary node used to get key
    Queue<BSTNode<K, V>> storageQueue = new LinkedList<BSTNode<K, V>>();
    storageQueue.add(tempNode); // adds n to head of queue to start loop

    // removes the head of queue, adds its key, adds its children to queue
    while (storageQueue.size() > 0) {
      tempNode = storageQueue.remove();
      if (tempNode != null) { // makes sure node is not null, otherwise nullPointerExceptions occur
        list.add(tempNode.key);

        if (tempNode.left != null) {
          storageQueue.add(tempNode.left);
        }
        if (tempNode.right != null) {
          storageQueue.add(tempNode.right);
        }
      }

    }
  }

  /**
   * Returns a List containing the in order traversal of the BST. Calls a recursive helper to do so.
   * Returns an empty list if the tree is empty
   * 
   * @return a List containing the in order traversal of the BST.
   * @see BST#inOrderTraversalHelper
   */
  @Override
  public List<K> getInOrderTraversal() {
    ArrayList<K> inOrderTraversalList = new ArrayList<K>(); // list to contain all keys in tree
    inOrderTraversalHelper(root, inOrderTraversalList);
    return inOrderTraversalList;
  }

  /**
   * Recursive helper for inPostOrderTraversal.
   * 
   * @param n    the current BSTNode the helper is called on.
   * @param list the list the keys of the BSTNode's children and the BSTNode should be added to.
   * @see BST#getPostOrderTraversal()
   */
  private void inOrderTraversalHelper(BSTNode<K, V> n, List<K> list) {
    if (n != null) { // makes sure n is not null, as n==null is a base case where nothing happens
      inOrderTraversalHelper(n.left, list); // calls helper on left child
      list.add(n.key); // after recursive process has run left child, adds current node's key
      inOrderTraversalHelper(n.right, list); // calls helper on right child
    }
  }

  /**
   * Inserts a node with the given key and value into the BST. Throws IllegalNullKeyException if key
   * is null, throws DuplicateKeyExcpetion if there is a node with the given key already in the BST.
   * 
   * @param key the key of the node to be added to the BST
   * @param the value of the node to be added to the BST
   * @throws IllegalNullKeyException if key is null
   * @throws DuplicateKeyException   if another node with the given key exists in the tree
   * @see BST#insertHelper()
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    if (key == null) { // checks if key is null, throws IllegalNullKeyException if it is
      throw new IllegalNullKeyException();
    } else {
      root = insertHelper(key, value, root);
      numKeys++;
    }
  }

  /**
   * Recursive helper for insert method. Inserts a new node with the given key and value. Throws a
   * DuplicateKeyException if a node with the given key already exists in the tree.
   * 
   * @param key   the key of the node to be inserted
   * @param value the value of the new node to be inserted
   * @param n     the current BSTNode the helper method is called on
   * @return the current node the helper is called on.
   * @throws DuplicateKeyException if a node with the given key alread exists in the tree
   * @see BST#insert(Comparable, Object)
   */
  private BSTNode<K, V> insertHelper(K key, V value, BSTNode<K, V> n) throws DuplicateKeyException {
    if (n == null) { // if n is null, assigns the new node to n and returns n
      n = new BSTNode<K, V>(key, value);
      return n;
    }

    if (n.key.equals(key)) { // checks if node with given key exists in tree
      throw new DuplicateKeyException(); // throws excpetion if it does
    }

    // if n is not null and does not have key equal to given key, insertHelper is recursively called
    // on n's left child if the given key is smaller than n's key, or n's left child if the given
    // key is larger than n's key.
    if (n.key.compareTo(key) > 0) {
      n.left = insertHelper(key, value, n.left);
    } else {
      n.right = insertHelper(key, value, n.right);
    }
    return n; // after above recursion is run, returns n
  }

  /**
   * Removes a node from the tree with the given key. If the key is null, throws
   * IllegalNullKeyExcpetion. If a node with the given key is not found in tree, throws
   * KeyNotFoundException. Returns true if node with given key is removed, false if not.
   * 
   * @param key the key of the node to be deleted
   * @return true if the node is successfully removed, false if not
   * @throws IllegalNullKeyExcpetion if given key is null
   * @throws KeyNotFoundException    if node with given key is not found in tree
   * @see BST#removeHelper(Comparable, BSTNode)
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) { // checks if key is null, throws IllegalNullKeyException if it is
      throw new IllegalNullKeyException();
    } else {
      root = removeHelper(key, root);
      numKeys--;
      return true;
    }
  }

  /**
   * Recursive helper method for remove(). Removes a node with the given key from the tree b either
   * returning null, its inorder predecessor, or one of its children.
   * 
   * @param key the key of the node to be removed
   * @param n   the current node the recursive method is on
   * @return what the new node in place of the current node should be set to or the current node if
   *         the current node is not the node to be removed
   * @throws KeyNotFoundException if a node with the given key is not in the tree
   * @see BST#remove(Comparable)
   */
  private BSTNode<K, V> removeHelper(K key, BSTNode<K, V> n) throws KeyNotFoundException {
    // checks if the method has traversed the tree to the point where the node with the given key
    // would be and found nothing, which is what has happened if n is null.
    if (n == null) {
      throw new KeyNotFoundException();
    }

    if (n.key.equals(key)) { // if the current node is the one to be removed
      if (n.left != null && n.right != null) {
        // gives n the key and value of its in order predecessor and returns n if n has two children
        BSTNode<K, V> inOrderPredecessor = getInOrderPredecessor(n);
        n.left = removeHelper(inOrderPredecessor.key, n.left);
        n.key = inOrderPredecessor.key;
        n.value = inOrderPredecessor.value;
        return n;
      } else if (n.left == null) {
        return n.right; // returns right child if n has no left child
      } else if (n.right == null) {
        return n.left; // returns left child of n has no right child
      } else {
        return null; // returns null if n has no children
      }
    } else if (n.key.compareTo(key) > 0) {
      // removes node with given key from n's left subtree if the given key is less than n's key
      n.left = removeHelper(key, n.left);
    } else {
      // removes node with given key from n's right subtree if the given key is greater than n's key
      n.right = removeHelper(key, n.right);
    }
    return n; // returns current node
  }

  /**
   * Returns the inorder predecessor of a given node.
   * 
   * @param n the node whose in order predecessor should be found
   * @return the inorder predecessor of the given node
   */
  private BSTNode<K, V> getInOrderPredecessor(BSTNode<K, V> n) {
    BSTNode<K, V> tempNode = n.left; // gets root of left subtree of n
    while (tempNode.right != null) {
      tempNode = tempNode.right; // traverses to maximum vale of the left subtree
    }
    return tempNode;
  }

  /**
   * Searches for a node with the given key in the BST and returns its value. Throws
   * IllegalNullKeyException if key is null. Throws KeyNotFoundException if node with given key not
   * in tree. Returns value of the node with the given key in the tree, false if it does not.
   * 
   * @param key the key of the node whose value is to be returned.
   * @return the value of the node with the matching key
   * @throws IllegalNullKeyException if key is null
   * @throws KeyNotFoundException    if a node with the given key does not exist in the tree.
   * @see BST#getHelper(Comparable, BSTNode)
   */
  @Override
  public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) { // checks if key is null, throws IllegalNullKeyException if it is
      throw new IllegalNullKeyException();
    } else {
      return getHelper(key, root);
    }
  }

  /**
   * Recursive helper method for get(). Checks if current node's key matches the given key. If it
   * is, returns the current node's value. Else if the given key is greater than the current nodes
   * key, calls recursive method on current node's right child, if the given key is less than the
   * current node's key, calls recursive method on right child. If node is null, throws
   * KeyNotFoundException()
   * 
   * @param key the key of the node whose value should be returned
   * @param n   the current node in the tree
   * @return the value of the node with the matching key
   * @throws KeyNotFoundException if there is no node with the given key in the BST
   * @see BST#get(Comparable)
   */
  private V getHelper(K key, BSTNode<K, V> n) throws KeyNotFoundException {
    // checks if the method has traversed the tree to the point where the node with the given key
    // would be and found nothing, which is what has happened if n is null.
    if (n == null) {
      throw new KeyNotFoundException();
    }

    if (n.key.equals(key)) { // if n's key matches the given key, return n's value
      return n.value;
    } else if (n.key.compareTo(key) > 0) { // if n's key is > than given key, call on left child
      return getHelper(key, n.left);
    } else {
      return getHelper(key, n.right); // if n's key is < than given key, call on left child
    }
  }


  /**
   * Searches for a node with the given key in the BST and returns true if it exists, false if it
   * does not. Throws IllegalNullKeyException if key is null.
   * 
   * @param key the key of the node to be searched for.
   * @return true if a node with the given key is found in the BST, false if not found.
   * @throws IllegalNullKeyException if key is null
   * @see BST#containsHelper(Comparable, BSTNode)
   */
  @Override
  public boolean contains(K key) throws IllegalNullKeyException {
    if (key == null) { // checks if key is null, throws IllegalNullKeyException if it is
      throw new IllegalNullKeyException();
    } else {
      return containsHelper(key, root);
    }
  }

  /**
   * Recursive helper method for contains(). Checks if current node's key matches the given key. If
   * it is, returns true. Else if the given key is greater than the current nodes key, calls
   * recursive method on current node's right child, if the given key is less than the current
   * node's key, calls recursive method on right child. If node is null, throws return false as
   * n=null means that the node with the given key was not found.
   * 
   * @param key the key of the node to be searched for in the BST
   * @param n   the current node in the tree
   * @return true if a node with the given key is found in the BST, false if not found.
   * @see BST#get(Comparable)
   */
  private boolean containsHelper(K key, BSTNode<K, V> n) {
    // checks if the method has traversed the tree to the point where the node with the given key
    // would be and found nothing, which is what has happened if n is null.
    if (n == null) {
      return false;
    }

    if (n.key.equals(key)) { // if n's key matches the given key, return true
      return true;
    } else if (n.key.compareTo(key) > 0) { // if n's key is > than given key, call on left child
      return containsHelper(key, n.left);
    } else {
      return containsHelper(key, n.right); // if n's key is < than given key, call on left child
    }
  }

  /**
   * Returns the number of keys in the BST
   * 
   * @return the number of keys in the BST
   */
  @Override
  public int numKeys() {
    return numKeys;
  }

  /**
   * Returns the key that is in the root node of this BST. If root is null, returns null.
   * 
   * @return key found at root node, or null
   */
  @Override
  public K getKeyAtRoot() {
    if (root == null) {
      return null;
    } else {
      return root.key;
    }
  }

  /**
   * Tries to find a node with a key that matches the specified key. If a matching node is found, it
   * returns the returns the key that is in the left child. If the left child of the found node is
   * null, returns null.
   * 
   * @param key A key to search for
   * @return The key that is in the left child of the found key
   * 
   * @throws IllegalNullKeyException if key argument is null
   * @throws KeyNotFoundException    if key is not found in this BST
   * @see BST#getKeyOfLeftChildOfHelper(Comparable, BSTNode)
   */
  @Override
  public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) { // checks if key is null, throws IllegalNullKeyException if it is
      throw new IllegalNullKeyException();
    } else {
      return getKeyOfLeftChildOfHelper(key, root);
    }
  }

  /**
   * Recursive helper method for getKeyOfLeftChildOf(). Checks if current node's key matches the
   * given key. If it is, returns the current node's left child's key, or null if the left child is
   * null. Else if the given key is greater than the current nodes key, calls recursive method on
   * current node's right child, if the given key is less than the current node's key, calls
   * recursive method on right child. If node is null, throws KeyNotFoundException()
   * 
   * @param key the key of the node whose left child's key is to be returned
   * @param n   the current node in the tree
   * @return the node with the given key's left child's key, or null
   * @throws KeyNotFoundException if there is no node with the given key in the BST
   * @see BST#getKeyOfLeftChildOf(Comparable)
   */
  private K getKeyOfLeftChildOfHelper(K key, BSTNode<K, V> n) throws KeyNotFoundException {
    // checks if the method has traversed the tree to the point where the node with the given key
    // would be and found nothing, which is what has happened if n is null.
    if (n == null) {
      throw new KeyNotFoundException();
    }

    if (n.key.equals(key)) { // if n's key matches the given key, return n's left node's key
      if (n.left == null) {
        return null;
      } else {
        return n.left.key;
      }
    } else if (n.key.compareTo(key) > 0) { // if n's key is > than given key, call on left child
      return getKeyOfLeftChildOfHelper(key, n.left);
    } else {
      // if n's key is < than given key, call on left child
      return getKeyOfLeftChildOfHelper(key, n.right);
    }
  }

  /**
   * Tries to find a node with a key that matches the specified key. If a matching node is found, it
   * returns the returns the key that is in the right child. If the right child of the found node is
   * null, returns null.
   * 
   * @param key A key to search for
   * @return The key that is in the right child of the found key
   * 
   * @throws IllegalNullKeyException if key is null
   * @throws KeyNotFoundException    if key is not found in this BST
   * @see BST#getKeyOfRightChildOfHelper(Comparable, BSTNode)
   */
  @Override
  public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
    if (key == null) { // checks if key is null, throws IllegalNullKeyException if it is
      throw new IllegalNullKeyException();
    } else {
      return getKeyOfRightChildOfHelper(key, root);
    }
  }


  /**
   * Recursive helper method for getKeyOfRightChildOf(). Checks if current node's key matches the
   * given key. If it is, returns the current node's right child's key, or null if the right child
   * is null. Else if the given key is greater than the current nodes key, calls recursive method on
   * current node's right child, if the given key is less than the current node's key, calls
   * recursive method on right child. If node is null, throws KeyNotFoundException()
   * 
   * @param key the key of the node whose left child's key is to be returned
   * @param n   the current node in the tree
   * @return the node with the given key's left child's key, or null
   * @throws KeyNotFoundException if there is no node with the given key in the BST
   * @see BST#getKeyOfRightChildOf(Comparable)
   */
  private K getKeyOfRightChildOfHelper(K key, BSTNode<K, V> n) throws KeyNotFoundException {
    // checks if the method has traversed the tree to the point where the node with the given key
    // would be and found nothing, which is what has happened if n is null.
    if (n == null) {
      throw new KeyNotFoundException();
    }

    if (n.key.equals(key)) { // if n's key matches the given key, return n's right node's key
      if (n.right == null) {
        return null;
      } else {
        return n.right.key;
      }
    } else if (n.key.compareTo(key) > 0) { // if n's key is > than given key, call on left child
      return getKeyOfRightChildOfHelper(key, n.left);
    } else {
      // if n's key is < than given key, call on left child
      return getKeyOfRightChildOfHelper(key, n.right);
    }
  }

  /**
   * Returns the height of this BST. Height is defined as the number of levels in the tree.
   * 
   * @return the number of levels that contain keys in this BST
   * @see BST#heightHelper(BSTNode)
   */
  @Override
  public int getHeight() {
    return heightHelper(root);
  }

  /**
   * Recursive helper for height() method. Returns the height the tree with the given node as root.
   * Returns 0 if n is null, else returns 1 + height of tallest subtree
   * 
   * @param n the current node the method is called on
   * @return the height of the tree with the current node as the root
   * @see BST#getHeight()
   */
  protected int heightHelper(BSTNode<K, V> n) {
    if (n == null) { // checks if n is null. If so, height is 0
      return 0;
    }

    // recursively finds heights of subtrees
    int heightLeftSubtree = heightHelper(n.left);
    int heightRightSubtree = heightHelper(n.right);

    // returns 1 + height of larger subtree as height
    if (heightLeftSubtree > heightRightSubtree) {
      return 1 + heightLeftSubtree;
    } else {
      return 1 + heightRightSubtree;
    }
  }
}
