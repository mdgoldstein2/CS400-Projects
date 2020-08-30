// Title: AVL
// Files: BSTNode, BST
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 2/21/19

// Outside Sources
// Persons: None
// Online Sources: None

/**
 * An AVL tree which extends BST. Automatically keeps tree in balance through rotations
 * 
 * @author Michael Goldstein
 *
 * @param <K> The key type of nodes in the AVL tree
 * @param <V> The value type of the stored values in the AVL tree
 */
public class AVL<K extends Comparable<K>, V> extends BST<K, V> {

  /**
   * Constructor for AVL tree. Calls default constructor of superclass (BST)
   * 
   * @see BST#BST()
   */
  public AVL() {
    super();
  }

  /**
   * Insert method for AVL tree. Runs insert method from BST (inserts a new node with the given key
   * and values) then rebalances
   * 
   * @throws IllegalNullKeyException if key is null
   * @throws DuplicateKeyException   if another node with the given key exists in the tree
   * 
   * @see BST#insert(Comparable, Object)
   * @see AVL#rebalance(BSTNode)
   */
  @Override
  public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
    super.insert(key, value);
    root = rebalance(root);
  }

  /**
   * Remove method for AVL tree. Runs remove method from BST (remove a node with the given key) then
   * rebalances
   * 
   * @throws IllegalNullKeyExcpetion if given key is null
   * @throws KeyNotFoundException    if node with given key is not found in tree
   * 
   * @see BST#remove(Comparable)
   * @see AVL#rebalance(BSTNode)
   */
  @Override
  public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
    super.remove(key);
    root = rebalance(root);
    return true;
  }

  /**
   * Calculates the balance factor (difference of heights of left and right subtrees) of a given
   * node
   * 
   * @param n the node whose balance factor is to be computed
   * @return the balance factor of the given node
   */
  private int balanceFactor(BSTNode<K, V> n) {
    if (n != null) {
      return heightHelper(n.left) - heightHelper(n.right);
    } else {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Rebalances subtree with n as root using the four rotations of an AVL tree
   * 
   * @param n the root of the subtree to be rebalance
   * @return the root of the subtree after all necessary rebalancings have been done
   * 
   * @see AVL#balanceFactor(BSTNode)
   */
  private BSTNode<K, V> rebalance(BSTNode<K, V> n) {
    if (n != null) {
      // calls rebalance on children before it runs on parent to make sure cascading does not
      // break the tree
      n.left = rebalance(n.left);
      n.right = rebalance(n.right);

      // calculates balance factor and calls the appropriate rotation if necessary
      int balanceFactor = balanceFactor(n);
      if (balanceFactor > 1) {
        if (balanceFactor(n.left) > 0) { // should rotate right if n's BF>1 and n.left's BF>0
          n = rightRotate(n);
        } else { // should rotate right if n's BF>1 and n.left's BF<=0
          n = leftRightRotate(n);
        }
      } else if (balanceFactor < -1) {
        if (balanceFactor(n.right) > 0) { // should rotate right if n's BF<-1 and n.left's BF>0
          n = rightLeftRotate(n);
        } else { // should rotate right if n's BF><-1 and n.left's BF<=0
          n = leftRotate(n);
        }
      }
      return n;
    }
    return null;
  }

  /**
   * Performs a right rotate on the given node, its left child, and that child's left child
   * 
   * @param n the "grandparent" node, the node which has a balance factor >1 or <-1
   * @return the node that should be in the place of the given (grandparent) node after rotation
   *         (should be the "parent" node)
   */
  private BSTNode<K, V> rightRotate(BSTNode<K, V> n) {
    BSTNode<K, V> g = n; // grandparent node
    BSTNode<K, V> p = g.left; // parent node
    // key node does not require moving

    g.left = p.right; // sets the grandparent's left child to the parent's right child
    p.right = g; // sets the parent's right child to the grandparent
    return p;
  }

  /**
   * Performs a left-right rotate on the given node, its left child, and that child's right child
   * 
   * @param n the "grandparent" node, the node which has a balance factor >1 or <-1
   * @return the node that should be in the place of the given (grandparent) node after rotation
   *         (should be the "key" node)
   */
  private BSTNode<K, V> leftRightRotate(BSTNode<K, V> n) {
    BSTNode<K, V> g = n; // grandparent node
    BSTNode<K, V> p = g.left; // parent node
    BSTNode<K, V> k = p.right; // key node

    p.right = k.left; // sets the parent's right child to the key's left child
    g.left = k.right; // sets the grandparent's left child to the key's right child
    k.left = p; // sets the key's left child to the parent
    k.right = g; // sets the key's right child to the grandparent
    return k;
  }

  /**
   * Performs a right-left rotate on the given node, its right child, and that child's left child
   * 
   * @param n the "grandparent" node, the node which has a balance factor >1 or <-1
   * @return the node that should be in the place of the given (grandparent) node after rotation
   *         (should be the "key" node)
   */
  private BSTNode<K, V> rightLeftRotate(BSTNode<K, V> n) {
    BSTNode<K, V> g = n; // grandparent node
    BSTNode<K, V> p = g.right; // parent node
    BSTNode<K, V> k = p.left; // key node

    p.left = k.right; // sets the parent's left child to the key's right child
    g.right = k.left; // sets the grandparent's right child to the key's left child
    k.left = g; // sets the key's left child to the grandparent
    k.right = p; // sets the key's right child to the parent
    return k;
  }

  /**
   * Performs a left rotate on the given node, its right child, and that child's right child
   * 
   * @param n the "grandparent" node, the node which has a balance factor >1 or <-1
   * @return the node that should be in the place of the given (grandparent) node after rotation
   *         (should be the "parent" node)
   */
  private BSTNode<K, V> leftRotate(BSTNode<K, V> n) {
    BSTNode<K, V> g = n; // grandparent node
    BSTNode<K, V> p = g.right; // parent node
    // key node does not require moving

    g.right = p.left; // sets the grandparent's right child to the parent's left child
    p.left = g; // sets the parent's left child to the grandparent
    return p;
  }
}
