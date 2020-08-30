import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Assert;
import static org.junit.jupiter.api.Assertions.fail;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;


// @SuppressWarnings("rawtypes")
public class AVLTest extends BSTTest {

  AVL<String, String> avl;
  AVL<Integer, String> avl2;

  /**
   * Method that runs before each test case. Creates two AVL trees.
   * 
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception {
    dataStructureInstance = bst = avl = createInstance();
    dataStructureInstance2 = bst2 = avl2 = createInstance2();
  }

  /**
   * Method that runs after each test case. Sets references to the two created AVL trees to null
   * 
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception {
    avl = null;
    avl2 = null;
  }


  /**
   * Creates an instance of an AVL tree with String keys
   * 
   * @return an AVL tree with String keys and values
   * @see DataStructureADTTest#createInstance()
   */
  @Override
  protected AVL<String, String> createInstance() {
    return new AVL<String, String>();
  }


  /**
   * Creates an instance of an AVL tree with Integer keys
   * 
   * @return an AVL tree with Integer keys and String values
   * @see DataStructureADTTest#createInstance2()
   */
  @Override
  protected AVL<Integer, String> createInstance2() {
    return new AVL<Integer, String>();
  }

  /**
   * Inserts three values in sorted order and then checks the root, left, and right keys to see if
   * rebalancing occurred.
   * 
   * @see AVL#insert(Comparable, Object)
   */
  @Test
  void testAVL_001_insert_sorted_order_simple() {
    try {
      // inserts 10-20-30
      avl2.insert(10, "10");
      if (!avl2.getKeyAtRoot().equals(10)) {
        fail("avl insert at root does not work");
      }

      avl2.insert(20, "20");
      if (!avl2.getKeyOfRightChildOf(10).equals(20)) {
        fail("avl insert to right child of root does not work");
      }

      avl2.insert(30, "30");
      Integer k = avl2.getKeyAtRoot();
      if (!k.equals(20)) {
        fail("avl rotate does not work");
      }

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(avl2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(avl2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(avl2.getKeyOfRightChildOf(20), new Integer(30));

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Insert three values in reverse sorted order and then check the roots, left, and right keys to
   * see if rebalancing occurred in the other direction.
   * 
   * @see AVL#insert(Comparable, Object)
   */
  @Test
  void testAVL_002_insert_reversed_sorted_order_simple() {
    try {
      // inserts 30-20-10
      avl2.insert(30, "30");
      if (!avl2.getKeyAtRoot().equals(30)) {
        fail("avl insert at root does not work");
      }


      avl2.insert(20, "20");
      if (!avl2.getKeyOfLeftChildOf(30).equals(20)) {
        fail("avl insert to right child of root does not work");
      }

      avl2.insert(10, "10");
      Integer k = avl2.getKeyAtRoot();
      if (!k.equals(20)) {
        fail("avl rotate does not work");
      }

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(avl2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(avl2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(avl2.getKeyOfRightChildOf(20), new Integer(30));

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Insert three values so that a right-left rotation is needed to fix the balance.
   * 
   * Example: 10-30-20
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred in the other
   * direction.
   * 
   * @see AVL#insert(Comparable, Object)
   */
  @Test
  void testAVL_003_insert_smallest_largest_middle_order_simple() {
    try {
      // inserts 10-30-20
      avl2.insert(10, "10");
      if (!avl2.getKeyAtRoot().equals(10)) {
        fail("avl insert at root does not work");
      }


      avl2.insert(30, "30");
      if (!avl2.getKeyOfRightChildOf(10).equals(30)) {
        fail("avl insert to right child of root does not work");
      }

      avl2.insert(20, "20");
      Integer k = avl2.getKeyAtRoot();
      if (!k.equals(20)) {
        fail("avl rotate does not work");
      }

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(avl2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(avl2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(avl2.getKeyOfRightChildOf(20), new Integer(30));

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Insert three values so that a left-right rotation is needed to fix the balance.
   * 
   * Example: 30-10-20
   * 
   * Then check the root, left, and right keys to see if rebalancing occurred in the other
   * direction.
   * 
   * @see AVL#insert(Comparable, Object)
   */
  @Test
  void testAVL_004_insert_largest_smallest_middle_order_simple() {
    try {
      // inserts 30-10-20
      avl2.insert(30, "30");
      if (!avl2.getKeyAtRoot().equals(30)) {
        fail("avl insert at root does not work");
      }


      avl2.insert(10, "10");
      if (!avl2.getKeyOfLeftChildOf(30).equals(10)) {
        fail("avl insert to right child of root does not work");
      }

      avl2.insert(20, "20");
      Integer k = avl2.getKeyAtRoot();
      if (!k.equals(20)) {
        fail("avl rotate does not work");
      }

      // IF rebalancing is working,
      // the tree should have 20 at the root
      // and 10 as its left child and 30 as its right child

      Assert.assertEquals(avl2.getKeyAtRoot(), new Integer(20));
      Assert.assertEquals(avl2.getKeyOfLeftChildOf(20), new Integer(10));
      Assert.assertEquals(avl2.getKeyOfRightChildOf(20), new Integer(30));

    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Checks if insert() correctly balances a larger tree correctly by checking that its level order
   * traversal is correct. The insert pattern ensures all four rotations must be used to for the
   * tree to rebalance correctly
   * 
   * Insert order : 10-20-30-5-15-25-3
   * 
   * @see AVL#insert(Comparable, Object)
   */
  @Test
  void testAVL_005_insert_rebalancing_larger_out_of_Balance_Trees() {
    // inserts 10, 20, 30, 15, 12, 28, 29, 9, 8 to create an unbalanced tree
    // which require all four rotation types to work correctly
    try {
      avl2.insert(10, "1st key inserted");
      avl2.insert(20, "2nd key inserted");
      avl2.insert(30, "3rd key inserted");
      avl2.insert(15, "4th key inserted");
      avl2.insert(12, "5th key inserted");
      avl2.insert(29, "6th key inserted");
      avl2.insert(28, "7th key inserted");
      avl2.insert(9, "8th key inserted");
      avl2.insert(8, "9th key inserted");

      // creates expected level order traversal
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20);
      expectedOrder.add(12);
      expectedOrder.add(29);
      expectedOrder.add(9);
      expectedOrder.add(15);
      expectedOrder.add(28);
      expectedOrder.add(30);
      expectedOrder.add(8);
      expectedOrder.add(10);

      // gets actual level order traversal and compares it
      List<Integer> actualOrder = avl2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 013: " + e.getMessage());
    }
  }

  /**
   * Deletes the nodes of a tree which results in a situation where a simple right rotation is
   * required to balance the tree. Sees if tree is rotated correctly by checking if its level order
   * traversal is correct.
   * 
   * @see AVL#remove(Comparable)
   */
  @Test
  void testAVL_006_remove_right_rotation() {
    try {
      // creates a tree which on insert does not require rebalancing
      avl2.insert(20, "1st key inserted");
      avl2.insert(10, "2nd key inserted");
      avl2.insert(30, "3rd key inserted");
      avl2.insert(35, "4th key inserted");

      avl2.remove(10); // removes node so tree must now be rebalanced with right rotate

      // creates expected level order traversal
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(30);
      expectedOrder.add(20);
      expectedOrder.add(35);

      // gets actual level order traversal and compares it
      List<Integer> actualOrder = avl2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Deletes the nodes of a tree which results in a situation where a left right rotation is
   * required to balance the tree. Sees if tree is rotated correctly by checking if its level order
   * traversal is correct.
   * 
   * @see AVL#remove(Comparable)
   */
  @Test
  void testAVL_007_remove_left_right_rotation() {
    try {
      // creates a tree which on insert does not require rebalancing
      avl2.insert(20, "1st key inserted");
      avl2.insert(10, "2nd key inserted");
      avl2.insert(30, "3rd key inserted");
      avl2.insert(15, "4th key inserted");

      avl2.remove(30); // removes node so tree must now be rebalanced with left-right rotate

      // creates expected level order traversal
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(15);
      expectedOrder.add(10);
      expectedOrder.add(20);

      // gets actual level order traversal and compares it
      List<Integer> actualOrder = avl2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Deletes the nodes of a tree which results in a situation where a right left rotation is
   * required to balance the tree. Sees if tree is rotated correctly by checking if its level order
   * traversal is correct.
   * 
   * @see AVL#remove(Comparable)
   */
  @Test
  void testAVL_008_remove_right_left_rotation() {
    try {
      // creates a tree which on insert does not require rebalancing
      avl2.insert(20, "1st key inserted");
      avl2.insert(10, "2nd key inserted");
      avl2.insert(30, "3rd key inserted");
      avl2.insert(25, "4th key inserted");

      avl2.remove(10); // removes node so tree must now be rebalanced with right-left rotate

      // creates expected level order traversal
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(25);
      expectedOrder.add(20);
      expectedOrder.add(30);

      // gets actual level order traversal and compares it
      List<Integer> actualOrder = avl2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Deletes the nodes of a tree which results in a situation where a left rotation is required to
   * balance the tree. Sees if tree is rotated correctly by checking if its level order traversal is
   * correct.
   * 
   * @see AVL#remove(Comparable)
   */
  @Test
  void testAVL_009_remove_left_rotation() {
    try {
      // creates a tree which on insert does not require rebalancing
      avl2.insert(20, "1st key inserted");
      avl2.insert(10, "2nd key inserted");
      avl2.insert(30, "3rd key inserted");
      avl2.insert(35, "4th key inserted");

      avl2.remove(10); // removes node so tree must now be rebalanced with left rotate

      // creates expected level order traversal
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(30);
      expectedOrder.add(20);
      expectedOrder.add(35);

      // gets actual level order traversal and compares it
      List<Integer> actualOrder = avl2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) {
      e.printStackTrace();
      fail("Unexpected exception AVL 000: " + e.getMessage());
    }
  }

  /**
   * Checks if insert() correctly balances a larger tree correctly by checking that its level order
   * traversal is correct. The insert pattern ensures all four rotations must be used to for the
   * tree to rebalance correctly
   * 
   * Insert order : 10-20-30-5-15-25-3
   * 
   * @see AVL#insert(Comparable, Object)
   */
  @Test
  void testAVL_010_remove_rebalancing_larger_out_of_Balance_Trees() {
    // inserts 10, 20, 30, 15, 12, 28, 29, 9, 8 which should not need rebalancing
    try {
      avl2.insert(20, "1st key inserted");
      avl2.insert(12, "2nd key inserted");
      avl2.insert(29, "3rd key inserted");
      avl2.insert(9, "4th key inserted");
      avl2.insert(15, "5th key inserted");
      avl2.insert(28, "6th key inserted");
      avl2.insert(30, "7th key inserted");
      avl2.insert(8, "8th key inserted");
      avl2.insert(10, "9th key inserted");

      // removes nodes to cause rebalancing
      avl2.remove(15); // causes right rotation
      avl2.remove(8); // causes right-left rotation
      avl2.remove(10); // removing 10, 12, and 9 causes left rotation
      avl2.remove(12);
      avl2.remove(9);
      avl2.remove(30); // causes left-right rotation

      // creates expected level order traversal
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(28);
      expectedOrder.add(20);
      expectedOrder.add(29);

      // gets actual level order traversal and compares it
      List<Integer> actualOrder = avl2.getLevelOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 013: " + e.getMessage());
    }
  }

  // Tests below override tests from BSTTest that would fail if run on a BST because in BSTTest the
  // test assume the tree does not rebalance

  /**
   * Test that the pre-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST (but AVL will rebalance if)
   * 
   * Insert order: 10-20-30 Pre-Order traversal order: 10-20-30 after rebalance
   * 
   * @see AVL#insert(Comparable, Object)
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  @Override
  void testBST_008_check_preOrder_for_not_balanced_insert_order() {
    // inserts 10-20-30 Out of Balance
    try {
      avl2.insert(10, "1st key inserted");
      avl2.insert(20, "2nd key inserted");
      avl2.insert(30, "3rd key inserted");

      // expected preOrder 20 10 30
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(20);
      expectedOrder.add(10);
      expectedOrder.add(30);

      // GET Pre-ORDER and check
      List<Integer> actualOrder = avl2.getPreOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 009: " + e.getMessage());
    }
  }

  /**
   * Test that the post-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST (but is rebalanced by AVL)
   * 
   * Insert order: 10-20-30 Post-Order traversal order: 10-20-30 after rebalance
   * 
   * @see AVL#insert(Comparable, Object)
   * @see BST#getInOrderTraversal()
   * @see BST#getPreOrderTraversal()
   * @see BST#getPostOrderTraversal()
   * @see BST#getLevelOrderTraversal()
   */
  @Test
  @Override
  void testBST_009_check_postOrder_for_not_balanced_insert_order() {
    // inserts 10-20-30 Out of Balance
    try {
      avl2.insert(10, "1st key inserted");
      avl2.insert(20, "2nd key inserted");
      avl2.insert(30, "3rd key inserted");

      // expected postOrder 10 30 20
      List<Integer> expectedOrder = new ArrayList<Integer>();
      expectedOrder.add(10);
      expectedOrder.add(30);
      expectedOrder.add(20);

      // GET Post-ORDER and check
      List<Integer> actualOrder = avl2.getPostOrderTraversal();
      assertEquals(expectedOrder, actualOrder);
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 010: " + e.getMessage());
    }
  }

  /**
   * Test that the level-order traversal order is correct if the items are entered in a way that
   * creates an un-balanced BST (but is rebalanced by AVL)
   * 
   * Insert order: 10-20-30 Level-Order traversal order: 20-10-30 after rebalance
   * 
   * @see AVL#insert(Comparable, Object)
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
      expectedOrder.add(20);
      expectedOrder.add(10);
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
   * Checks if getHeight() method returns the correct height of an out of balance tree (but AVL will
   * rebalance)
   * 
   * Insert order : 10-20-30-5-15-25-35-34 Expected height : 4 after rebalance
   * 
   * @see AVL#insert(Comparable, Object)
   * @see BST#getHeight()
   */
  @Test
  void testBST_012_height_of_out_of_balance_tree() {
    // inserts 10, 20, 30, 5, 15, 25, 35, 34 to create an unbalanced tree of height 5
    // but a balanced tree height of 4
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

      if (actualHeight != 4) {
        fail("getHeight() should have returned 5 but returned " + actualHeight);
      }
    } catch (Exception e) { // catches unexpected exception that should not occur
      e.printStackTrace();
      fail("Unexpected exception 013: " + e.getMessage());
    }
  }
}
