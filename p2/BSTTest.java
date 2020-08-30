// Title: BSTTest
// Files: BST
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 2/21/19

// Outside Sources
// Persons: None
// Online Sources: None

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * JUnit Testing class to check if BST functions correctly
 * 
 * @author Michael Goldstein
 * @see BST
 */
public class BSTTest extends DataStructureADTTest {

  BST<String, String> bst;
  BST<Integer, String> bst2;

  /**
   * Runs before all tests. Creates two instances of a BST
   * 
   * @throws java.lang.Exception if BST's fail to initialize correctly
   */
  @BeforeEach
  void setUp() throws Exception {
    // The setup must initialize this class's instances
    // and the super class instances.
    // Because of the inheritance between the interfaces and classes,
    // we can do this by calling createInstance() and casting to the desired type
    // and assigning that same object reference to the super-class fields.
    dataStructureInstance = bst = createInstance();
    dataStructureInstance2 = bst2 = createInstance2();
  }

  /**
   * Runs after all tests completed. Sets existing BST to null
   * 
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    dataStructureInstance = bst = null;
    dataStructureInstance2 = bst2 = null;
  }

  /**
   * Creates an instance of a BST with String keys
   * 
   * @return a BST with String keys and String Values
   * @see DataStructureADTTest#createInstance()
   */
  @Override
  protected BST<String, String> createInstance() {
    return new BST<String, String>();
  }

  /**
   * Creates an instance of a BST with Integer keys
   * 
   * @return a BST with Integer keys and String values
   * @see DataStructureADTTest#createInstance2()
   */
  @Override
  protected BST<Integer, String> createInstance2() {
    return new BST<Integer, String>();
  }

  /**
   * Test that empty trees still produce a valid but empty traversal list for each of the four
   * traversal orders.
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_001_empty_traversal_orders() {
    try {
      List<String> expectedOrder = new ArrayList<String>(); // empty list

      // Get the actual traversal order lists for each type
      List<String> inOrder = bst.getInOrderTraversal();
      List<String> preOrder = bst.getPreOrderTraversal();
      List<String> postOrder = bst.getPostOrderTraversal();
      List<String> levelOrder = bst.getLevelOrderTraversal();

      // UNCOMMENT IF DEBUGGING THIS TEST
      // System.out.println(" EXPECTED: "+expectedOrder);
      // System.out.println(" In Order: "+inOrder);
      // System.out.println(" Pre Order: "+preOrder);
      // System.out.println(" Post Order: "+postOrder);
      // System.out.println("Level Order: "+levelOrder);

      // all traversals should return an empty list as tree is empty
      assertEquals(expectedOrder, inOrder);
      assertEquals(expectedOrder, preOrder);
      assertEquals(expectedOrder, postOrder);
      assertEquals(expectedOrder, levelOrder);

    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 002: " + e.getMessage());
    }

  }

  /**
   * Test that trees with one key,value pair produce valid traversal lists for each of the four
   * traversal orders.
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_002_check_traversals_after_insert_one() {

    try {
      // creates and adds list with one element
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      bst2.insert(10, "ten");

      if (bst2.numKeys() != 1) {
        fail("added 10, size should be 1, but was " + bst2.numKeys());
      }

      List<Integer> inOrder = bst2.getInOrderTraversal();
      List<Integer> preOrder = bst2.getPreOrderTraversal();
      List<Integer> postOrder = bst2.getPostOrderTraversal();
      List<Integer> levelOrder = bst2.getLevelOrderTraversal();

      // UNCOMMENT IF DEBUGGING THIS TEST
      // System.out.println(" EXPECTED: " + expectedOrder);
      // System.out.println(" In Order: " + inOrder);
      // System.out.println(" Pre Order: " + preOrder);
      // System.out.println(" Post Order: " + postOrder);
      // System.out.println("Level Order: " + levelOrder);

      // checks that all traversals return 10 as they should
      assertEquals(expectedOrder, inOrder);
      assertEquals(expectedOrder, preOrder);
      assertEquals(expectedOrder, postOrder);
      assertEquals(expectedOrder, levelOrder);

    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 003: " + e.getMessage());
    }

  }


  /**
   * Test that the in-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 In-Order traversal order: 10-20-30
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_003_check_inOrder_for_balanced_insert_order() {
    // inserts 20-10-30 BALANCED
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(20);
      expectedOrder.add(30);

      // GET IN-ORDER and check
      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 004: " + e.getMessage());
    }
  }

  /**
   * Test that the pre-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 Pre-Order traversal order: 20-10-30
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_004_check_preOrder_for_balanced_insert_order() {
    // inserts 20-10-30 BALANCED
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected preOrder 20 10 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20);
      expectedOrder.add(10);
      expectedOrder.add(30);

      // GET Pre-ORDER and check
      List<Integer> actualOrder = bst2.getPreOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 005: " + e.getMessage());
    }
  }

  /**
   * Test that the post-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 Post-Order traversal order: 10-30-20
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_005_check_postOrder_for_balanced_insert_order() {
    // inserts 20-10-30 BALANCED
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected postOrder 10 30 20
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(30);
      expectedOrder.add(20);

      // GET Post-ORDER and check
      List<Integer> actualOrder = bst2.getPostOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 006: " + e.getMessage());
    }
  }

  /**
   * Test that the level-order traversal order is correct if the items are entered in a way that
   * creates a balanced BST
   * 
   * Insert order: 20-10-30 Level-Order traversal order: 20-10-30
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_006_check_levelOrder_for_balanced_insert_order() {
    // inserts 20-10-30 BALANCED
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected levelOrder 20 10 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20);
      expectedOrder.add(10);
      expectedOrder.add(30);

      // GET Level-ORDER and check
      List<Integer> actualOrder = bst2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 007: " + e.getMessage());
    }
  }

  /**
   * Test that the in-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 In-Order traversal order: 10-20-30
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_007_check_inOrder_for_not_balanced_insert_order() {
    // inserts 10-20-30 Out of Balance
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected inOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(20);
      expectedOrder.add(30);

      // GET In-ORDER and check
      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 008: " + e.getMessage());
    }
  }

  /**
   * Test that the pre-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Pre-Order traversal order: 10-20-30
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_008_check_preOrder_for_not_balanced_insert_order() {
    // inserts 10-20-30 Out of Balance
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected preOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(20);
      expectedOrder.add(30);

      // GET Pre-ORDER and check
      List<Integer> actualOrder = bst2.getPreOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 009: " + e.getMessage());
    }
  }

  /**
   * Test that the post-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Post-Order traversal order: 30-20-10
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_009_check_postOrder_for_not_balanced_insert_order() {
    // inserts 10-20-30 Out of Balance
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected postOrder 30 20 10
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(30);
      expectedOrder.add(20);
      expectedOrder.add(10);

      // GET Post-ORDER and check
      List<Integer> actualOrder = bst2.getPostOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 010: " + e.getMessage());
    }
  }

  /**
   * Test that the level-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST
   * 
   * Insert order: 10-20-30 Level-Order traversal order: 30-20-10
   * 
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  void testBST_010_check_levelOrder_for_not_balanced_insert_order() {
    // inserts 10-20-30 Out of Balance
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");

      // expected levelOrder 10 20 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(20);
      expectedOrder.add(30);

      // GET Level-ORDER and check
      List<Integer> actualOrder = bst2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 011: " + e.getMessage());
    }
  }

  /**
   * Checks if getHeight() method returns the correct height of a balanced tree
   * 
   * Insert order : 20-10-30-5-15-25-35 Expected height : 3
   * 
   * @see BST#getHeight()
   */
  @Test
  void testBST_011_height_of_balanced_tree() {
    // inserts 20, 10, 30, 5, 15, 25, 35 to create a perfect, height-balanced tree of height 3
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");
      bst2.insert(5, "4th key inserted");
      bst2.insert(15, "5th key inserted");
      bst2.insert(25, "6th key inserted");
      bst2.insert(35, "7th key inserted");

      int actualHeight = bst2.getHeight();

      if (actualHeight != 3) {
        fail("getHeight() should have returned 3 but returned " + actualHeight);
      }
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 012: " + e.getMessage());
    }
  }

  /**
   * Checks if getHeight() method returns the correct height of an out of balance tree
   * 
   * Insert order : 10-20-30-5-15-25-35-34 Expected height : 5
   * 
   * @see BST#getHeight()
   */
  @Test
  void testBST_012_height_of_out_of_balance_tree() {
    // inserts 10, 20, 30, 5, 15, 25, 35, 34 to create an unbalanced tree of height 5
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");
      bst2.insert(5, "4th key inserted");
      bst2.insert(15, "5th key inserted");
      bst2.insert(25, "6th key inserted");
      bst2.insert(35, "7th key inserted");
      bst2.insert(34, "8th key inserted");

      int actualHeight = bst2.getHeight();

      if (actualHeight != 5) {
        fail("getHeight() should have returned 5 but returned " + actualHeight);
      }
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 013: " + e.getMessage());
    }
  }

  /**
   * Tests if BST get() returns the correct values for the given keys, which do exist in the BST.
   * 
   * @see BST#get(Comparable)
   */
  @Test
  void testBST_013_get_returns_correct_values() {
    // inserts 20, 10, 30, 5, 15, 25, 35 to create a perfect, height-balanced tree of height 3
    try {
      bst2.insert(20, "4");
      bst2.insert(10, "2");
      bst2.insert(30, "6");
      bst2.insert(5, "1");
      bst2.insert(15, "3");
      bst2.insert(25, "5");
      bst2.insert(35, "7");

      // checks that get function returns correct value for every key (as each value is a String
      // form of the key divided by 5
      for (int index = 1; index < 8; index++) {
        if (!bst2.get(index * 5).equals(Integer.toString(index))) {
          fail("get() should have returned " + index + " for a key " + index * 5 + ", but returned "
              + bst2.get(index));
        }
      }

    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 014: " + e.getMessage());
    }
  }

  /**
   * Tests if BST get() returns the correct values for the given keys, which do exist in the BST.
   * 
   * @see BST#contains(Comparable)
   */
  @Test
  void testBST_014_contains_returns_true_when_nodes_present() {
    // inserts 20, 10, 30, 5, 15, 25, 35 to create a perfect, height-balanced tree of height 3
    try {
      bst2.insert(20, "4");
      bst2.insert(10, "2");
      bst2.insert(30, "6");
      bst2.insert(5, "1");
      bst2.insert(15, "3");
      bst2.insert(25, "5");
      bst2.insert(35, "7");

      // checks that get() returns true when nodes with given keys do exist
      for (int index = 1; index < 8; index++) {
        if (!bst2.contains(index * 5)) {
          fail("get() method returned false despite a node with key " + index * 5
              + " existing in the BST");
        }
      }

    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 015: " + e.getMessage());
    }
  }

  /**
   * Tests whether or not illegal inserts are correctly handled (the correct exceptions should be
   * thrown)
   * 
   * @see BST#insert(Comparable, Object)
   */
  @Test
  void testBST_015_illegal_inserts() {
    // tests that a null insert should thrown an IllegalNullKeyException
    try {
      bst2.insert(null, "1st insert");
      fail("no exception thrown when null key is given as parameter in insert()");
    } catch (IllegalNullKeyException a) {
      // makes sure size has not changed because of illegal insert
      if (bst2.numKeys() != 0) {
        fail("insert() changed size when an illegal insert was performed");
      }
    } catch (Exception a) {
      fail("incorrect exception thrown when null key given as insert() parameter");
    }

    // tests that inserting a duplicate key will throw a DuplicateKeyException
    try {
      bst.insert("original", "1st insert");
      bst.insert("original", "2nd insert");
      fail("no exception thrown when duplicate key is given as parameter in insert()");
    } catch (DuplicateKeyException a) {
      // makes sure size has not changed because of illegal insert
      if (bst.numKeys() != 1) {
        fail("insert() changed size when an illegal insert was performed");
      }
    } catch (Exception a) {
      fail("incorrect exception thrown when duplicate key given as insert() parameter");
    }
  }

  /**
   * Tests whether or not illegal gets are correctly handled (the correct exceptions should be
   * thrown)
   * 
   * @see BST#get(Comparable)
   */
  @Test
  void testBST_016_illegal_gets() {
    // tests that a null get should thrown an IllegalNullKeyException
    try {
      bst2.get(null);
      fail("no exception thrown when null key is given as parameter in get()");
    } catch (IllegalNullKeyException a) {
      // makes sure size has not changed because of illegal get
      if (bst2.numKeys() != 0) {
        fail("get() changed size when an illegal get was performed");
      }
    } catch (Exception a) {
      fail("incorrect exception thrown when null key given as get() parameter");
    }

    // tests that getting a node with a key no in the tree will throw a KeyNotFoundException
    try {
      bst.insert("original", "1st insert");
      bst.get("unoriginal");
      fail("no exception thrown when a key not in BST is given as parameter in insert()");
    } catch (KeyNotFoundException a) {
      // makes sure size has not changed because of illegal get
      if (bst.numKeys() != 1) {
        fail("get() changed size when an illegal get was performed");
      }
    } catch (Exception a) {
      fail("incorrect exception thrown when key not in BST given as insert() parameter");
    }
  }

  /**
   * Tests whether or not illegal contains() are correctly handled and contains() returns false when
   * when an element is not in the BST
   * 
   * @see BST#contains(Comparable)
   */
  @Test
  void testBST_017_illegal_contains() {
    // tests that a null get should thrown an IllegalNullKeyException
    try {
      bst2.contains(null);
      fail("no exception thrown when null key is given as parameter in contains()");
    } catch (IllegalNullKeyException a) {
      // makes sure size has not changed because of illegal get
      if (bst2.numKeys() != 0) {
        fail("contains() changed size when an illegal contains was performed");
      }
    } catch (Exception a) {
      fail("incorrect exception thrown when null key given as contains() parameter");
    }

    // tests that checking a node with a key no in the tree will return false
    try {
      bst.insert("original", "1st insert");
      bst.contains("unoriginal");
      if (bst.numKeys() != 1) {
        fail("contains() changed size when an illegal get was performed");
      }
    } catch (Exception a) {
      fail("Unexpected exception 016");
    }
  }

  /**
   * Tests the getKeyAtRoot(), getKeyOfLeftChildOf(), getKeyOfRightChildOf() failure/null cases work
   * correctly
   * 
   * @see BST#getKeyAtRoot()
   * @see BST#getKeyOfLeftChildOf(Comparable)
   * @see BST#getKeyOfRightChildOf(Comparable)
   */
  @Test
  void testBST_018_getKeyAtRoot_Left_Child_Right_Child() {
    // checks if getKeyAtRoot returns null when root is null
    if (bst2.getKeyAtRoot() != null) {
      fail("getKeyAtRoot() did not return null when root of tree was null");
    }

    // checks if getKeyOfLeftChildOf throws an exception when given key is null
    try {
      bst2.getKeyOfLeftChildOf(null);
    } catch (IllegalNullKeyException a) {
      // nothing should happen here as the statement as a null should throw an exception
    } catch (Exception a) {
      fail("getKeyOfLeftChildOf() should have thrown an IllegalNullKeyExcpetion");
    }

    // checks if getKeyOfRightChildOf throws an exception when given key is null
    try {
      bst2.getKeyOfRightChildOf(null);
    } catch (IllegalNullKeyException a) {
      // nothing should happen here as the statement as a null should throw an exception
    } catch (Exception a) {
      fail("getKeyOfRightChildOf() should have thrown an IllegalNullKeyExcpetion");
    }

    // checks if getKeyOfLeftChildOf throws an exception when given key is not in tree
    try {
      bst2.getKeyOfLeftChildOf(10);
    } catch (KeyNotFoundException a) {
      // nothing should happen here as the statement as a nonexistent key should throw an exception
    } catch (Exception a) {
      fail("getKeyOfLeftChildOf() should have thrown an KeyNotFoundException");
    }

    // checks if getKeyOfRightChildOf throws an exception when given key is not in treee
    try {
      bst2.getKeyOfRightChildOf(12);
    } catch (KeyNotFoundException a) {
      // nothing should happen here as the statement as a nonexistent key should throw an exception
    } catch (Exception a) {
      fail("getKeyOfRightChildOf() should have thrown an KeyNotFoundException");
    }
  }

  /**
   * Tests that getKeyAtRoot(), getKeyOfLeftChildOf(), getKeyOfRightChildOf() methods work correctly
   * after creating a tree and checking what the methods return against what is expected.
   * 
   * @see BST#getKeyAtRoot()
   * @see BST#getKeyOfLeftChildOf(Comparable)
   * @see BST#getKeyOfRightChildOf(Comparable)
   */
  @Test
  void testBST_019_getKey_method_work_correctly() {
    // inserts 20, 10, 30, 5, 15, 25, 35 to create a perfect, height-balanced tree of height 3
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");
      bst2.insert(5, "4th key inserted");
      bst2.insert(15, "5th key inserted");
      bst2.insert(25, "6th key inserted");
      bst2.insert(35, "7th key inserted");

      // checks that all above methods return correct answers
      if (bst2.getKeyAtRoot() != 20) {
        fail("getKeyAtRoot() should have returned 20 but returned " + bst2.getKeyAtRoot());
      }

      // checks children of 20
      if (bst2.getKeyOfLeftChildOf(20) != 10) {
        fail("getKeyOfLeftChildOf() should have returned 10 but returned "
            + bst2.getKeyOfLeftChildOf(20));
      }
      if (bst2.getKeyOfRightChildOf(20) != 30) {
        fail("getKeyOfRightChildOf() should have returned 30 but returned "
            + bst2.getKeyOfRightChildOf(20));
      }

      // checks children of 10
      if (bst2.getKeyOfLeftChildOf(10) != 5) {
        fail("getKeyOfLeftChildOf() should have returned 5 but returned "
            + bst2.getKeyOfLeftChildOf(10));
      }
      if (bst2.getKeyOfRightChildOf(10) != 15) {
        fail("getKeyOfRightChildOf() should have returned 15 but returned "
            + bst2.getKeyOfRightChildOf(10));
      }

      // checks children of 30
      if (bst2.getKeyOfLeftChildOf(30) != 25) {
        fail("getKeyOfLeftChildOf() should have returned 25 but returned "
            + bst2.getKeyOfLeftChildOf(30));
      }
      if (bst2.getKeyOfRightChildOf(30) != 35) {
        fail("getKeyOfRightChildOf() should have returned 35 but returned "
            + bst2.getKeyOfRightChildOf(30));
      }

      // checks children of 5
      if (bst2.getKeyOfLeftChildOf(5) != null) {
        fail("getKeyOfLeftChildOf() should have returned null but returned "
            + bst2.getKeyOfLeftChildOf(5));
      }
      if (bst2.getKeyOfRightChildOf(5) != null) {
        fail("getKeyOfRightChildOf() should have returned null but returned "
            + bst2.getKeyOfRightChildOf(5));
      }

      // checks children of 30
      if (bst2.getKeyOfLeftChildOf(15) != null) {
        fail("getKeyOfLeftChildOf() should have returned null but returned "
            + bst2.getKeyOfLeftChildOf(15));
      }
      if (bst2.getKeyOfRightChildOf(15) != null) {
        fail("getKeyOfRightChildOf() should have returned null but returned "
            + bst2.getKeyOfRightChildOf(15));
      }

      // checks children of 30
      if (bst2.getKeyOfLeftChildOf(25) != null) {
        fail("getKeyOfLeftChildOf() should have returned null but returned "
            + bst2.getKeyOfLeftChildOf(25));
      }
      if (bst2.getKeyOfRightChildOf(25) != null) {
        fail("getKeyOfRightChildOf() should have returned null but returned "
            + bst2.getKeyOfRightChildOf(25));
      }

      // checks children of 30
      if (bst2.getKeyOfLeftChildOf(35) != null) {
        fail("getKeyOfLeftChildOf() should have returned null but returned "
            + bst2.getKeyOfLeftChildOf(35));
      }
      if (bst2.getKeyOfRightChildOf(35) != null) {
        fail("getKeyOfRightChildOf() should have returned null but returned "
            + bst2.getKeyOfRightChildOf(35));
      }
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 017: " + e.getMessage());
    }
  }

  /**
   * Tests that removing a leaf node works correctly
   * 
   * @see BST#remove(Comparable)
   */
  @Test
  void testBST_020_remove_leaf_node() {
    // inserts 20, 10, 30, 5, 15, 25, 35 to create a perfect, height-balanced tree of height 3
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");
      bst2.insert(5, "4th key inserted");
      bst2.insert(15, "5th key inserted");
      bst2.insert(25, "6th key inserted");
      bst2.insert(35, "7th key inserted");

      // removes 15 and checks that correct structure is maintains by checking traversal orders
      bst2.remove(15);

      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(5);
      expectedOrder.add(10);
      expectedOrder.add(20);
      expectedOrder.add(25);
      expectedOrder.add(30);
      expectedOrder.add(35);

      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 017: " + e.getMessage());
    }
  }

  /**
   * Tests that removing an inner node with two children works correctly
   * 
   * @see BST#remove(Comparable)
   */
  @Test
  void testBST_021_remove_inner_node_with_two_children() {
    // inserts 20, 10, 30, 5, 15, 25, 35 to create a perfect, height-balanced tree of height 3
    try {
      bst2.insert(20, "1st key inserted");
      bst2.insert(10, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");
      bst2.insert(5, "4th key inserted");
      bst2.insert(15, "5th key inserted");
      bst2.insert(25, "6th key inserted");
      bst2.insert(35, "7th key inserted");

      // removes 15 and checks that correct structure is maintains by checking traversal orders
      bst2.remove(30);

      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(5);
      expectedOrder.add(10);
      expectedOrder.add(15);
      expectedOrder.add(20);
      expectedOrder.add(25);
      expectedOrder.add(35);

      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 017: " + e.getMessage());
    }
  }

  /**
   * Tests that removing an inner node with only a left child works correctly
   * 
   * @see BST#remove(Comparable)
   */
  @Test
  void testBST_022_remove_inner_node_with_left_child() {
    // inserts 10, 20, 30, 5, 15, 25, 35, 34 to create an unbalanced tree of height 5
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");
      bst2.insert(5, "4th key inserted");
      bst2.insert(15, "5th key inserted");
      bst2.insert(25, "6th key inserted");
      bst2.insert(35, "7th key inserted");
      bst2.insert(34, "8th key inserted");

      // removes 15 and checks that correct structure is maintains by checking traversal orders
      bst2.remove(35);

      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(5);
      expectedOrder.add(10);
      expectedOrder.add(15);
      expectedOrder.add(20);
      expectedOrder.add(25);
      expectedOrder.add(30);
      expectedOrder.add(34);

      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 017: " + e.getMessage());
    }
  }

  /**
   * Tests that removing an inner node with only a left child works correctly
   * 
   * @see BST#remove(Comparable)
   */
  @Test
  void testBST_023_remove_inner_node_with_right_child() {
    // inserts 10, 20, 30, 5, 25, 35, 34 to create an unbalanced tree of height 5
    try {
      bst2.insert(10, "1st key inserted");
      bst2.insert(20, "2nd key inserted");
      bst2.insert(30, "3rd key inserted");
      bst2.insert(5, "4th key inserted");
      bst2.insert(25, "6th key inserted");
      bst2.insert(35, "7th key inserted");
      bst2.insert(34, "8th key inserted");

      // removes 15 and checks that correct structure is maintains by checking traversal orders
      bst2.remove(20);

      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(5);
      expectedOrder.add(10);
      expectedOrder.add(25);
      expectedOrder.add(30);
      expectedOrder.add(34);
      expectedOrder.add(35);

      List<Integer> actualOrder = bst2.getInOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 017: " + e.getMessage());
    }
  }

  /**
   * Tests whether or not illegal removes are correctly handled (the correct exceptions should be
   * thrown)
   * 
   * @see BST#remove(Comparable)
   */
  @Test
  void testBST_024_illegal_removes() {
    // tests that a null get should thrown an IllegalNullKeyException
    try {
      bst2.remove(null);
      fail("no exception thrown when null key is given as parameter in remove()");
    } catch (IllegalNullKeyException a) {
      // makes sure size has not changed because of illegal get
      if (bst2.numKeys() != 0) {
        fail("remove() changed size when an illegal remove was performed");
      }
    } catch (Exception a) {
      fail("incorrect exception thrown when null key given as remove() parameter");
    }

    // tests that getting a node with a key no in the tree will throw a KeyNotFoundException
    try {
      bst.insert("original", "1st insert");
      bst.remove("unoriginal");
      fail("no exception thrown when a key not in BST is given as parameter in insert()");
    } catch (KeyNotFoundException a) {
      // makes sure size has not changed because of illegal get
      if (bst.numKeys() != 1) {
        fail("remove() changed size when an illegal remove was performed");
      }
    } catch (Exception a) {
      fail("incorrect exception thrown when key not in BST given as remove() parameter");
    }
  }
}
