// Title: HashTableTest
// Files: HashTable, HashTableADT
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 3/14/19

import static org.junit.jupiter.api.Assertions.*; // org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit testing class which tests if the HashTable implementation works correctly
 * 
 * @author Michael Goldstein
 * @see HashTable
 * @see HashTableADT
 *
 */
public class HashTableTest {

  HashTable<Integer, String> htIntegerKey; // hash table used for testing (capacity: 50, LFT: 0.75)

  /**
   * Runs before any tests are run. Initializes a hash table with Integer keys and String values
   * 
   * @throws Exception if code in method throws an exception
   */
  @Before
  public void setUp() throws Exception {
    htIntegerKey = new HashTable<Integer, String>();
  }

  /**
   * Runs after each test is run. Resets the hash table used for testing by setting its reference to
   * null.
   * 
   * @throws Exception if code in method throws an exception
   */
  @After
  public void tearDown() throws Exception {
    htIntegerKey = null;
  }

  /**
   * Tests that a HashTable returns an integer code indicating which collision resolution strategy
   * is used.
   */
  @Test
  public void test000_collision_scheme() {
    int scheme = htIntegerKey.getCollisionResolution();
    if (scheme < 1 || scheme > 9) {
      fail("collision resolution must be indicated with 1-9");
    }
  }

  /**
   * Tests that insert(null,null) throws IllegalNullKeyException
   */
  @Test
  public void test001_IllegalNullKey_Insert() {
    try {
      htIntegerKey.insert(null, null);
      fail("should not be able to insert null key");
    } catch (IllegalNullKeyException e) {
      // this is expected
    } catch (Exception e) {
      fail("insert null key should not throw exception " + e.getClass().getName());
    }
  }

  /**
   * Tests that remove(null) throws IllegalNullKeyException
   */
  @Test
  public void test002_IllegalNullKey_Remove() {
    try {
      htIntegerKey.remove(null);
      fail("should not be able to remove with a null key");
    } catch (IllegalNullKeyException e) {
      // this is expected
    } catch (Exception e) {
      fail("remove null key should not throw exception " + e.getClass().getName());
    }
  }

  /**
   * Tests that get(null) throws IllegalNullKeyException
   */
  @Test
  public void test003_IllegalNullKey_Get() {
    try {
      htIntegerKey.remove(null);
      fail("should not be able to get with a null key");
    } catch (IllegalNullKeyException e) {
      // this is expected
    } catch (Exception e) {
      fail("get null key should not throw exception " + e.getClass().getName());
    }
  }

  /**
   * Tests inserting a key value pair with a duplicate key correctly throws a DuplicateKeyException
   */
  @Test
  public void test004_Insert_Same_Key() {
    try {
      // inserts two nodes with same key
      htIntegerKey.insert(25, "1st insert");
      htIntegerKey.insert(25, "2nd insert");
      fail("should not be able to insert a key value pair with a duplicate key");
    } catch (DuplicateKeyException e) {
      // this is expected
    } catch (Exception e) {
      fail("inserting a duplicate key should not throw exception " + e.getClass().getName());
    }
  }

  /**
   * Tests the numKeys returns the correct number of keys in the hash table after adding elements.
   * Also tests that insertion of keys with the same value is allowed.
   */
  @Test
  public void test005_numKeys() {
    try {
      // inserts 50 nodes, which should cause a rehashing in case that affects size
      // all 50 nodes have same value, which is "test"
      for (int index = 0; index < 50; index++) {
        htIntegerKey.insert(index, "test");
      }

      // if size is not 50, test fails
      if (htIntegerKey.numKeys() != 50) {
        fail("50 elements were added to a hash table but numKeys returned "
            + htIntegerKey.numKeys());
      }
    } catch (Exception e) {
      fail("no exception should be thrown here");
    }
  }

  /**
   * Tests that get returns the correct value for a given key
   */
  @Test
  public void test006_get() {
    try {
      // inserts 50 nodes, which should cause a rehashing in case that affects size
      for (int index = 0; index < 50; index++) {
        htIntegerKey.insert(index, "test" + index);
      }

      // checks that get returns the correct values for each of the 50 keys
      for (int index = 0; index < htIntegerKey.numKeys(); index++) {
        if (!(htIntegerKey.get(index).equals("test" + index))) {
          fail("get failed to return the correct value for a given key");
        }
      }
    } catch (Exception e) {
      fail("no exception should be thrown here");
    }
  }

  /**
   * Tests that getting the value for a key not in the hash table throws a key not found exception
   */
  @Test
  public void test007_get_not_int_table() {
    try {
      // inserts 50 nodes, which should cause a rehashing in case that affects get
      for (int index = 0; index < 50; index++) {
        htIntegerKey.insert(index, "test" + index);
      }

      // tries to get value of an element whose key is not in table
      htIntegerKey.get(51);
      fail("get should throw a KeyNotFoundException");
    } catch (KeyNotFoundException e) {
      // should throw exception
    } catch (Exception e) {
      fail("getting the value for a key not in table should not throw exception "
          + e.getClass().getName());
    }
  }

  /**
   * Tests that removing a node with a key not in the hash table throws a key not found exception
   */
  @Test
  public void test008_remove_not_int_table() {
    try {
      // inserts 50 nodes, which should cause a rehashing in case that affects remove
      for (int index = 0; index < 50; index++) {
        htIntegerKey.insert(index, "test" + index);
      }

      // tries to get value of an element whose key is not in table
      if (htIntegerKey.remove(51)) {
        fail("remove should return false when removing with a key not in the table");
      }
    } catch (Exception e) {
      fail("no exception should be thrown here");
    }
  }

  /**
   * Tests that removing a node a key not in the hash table throws a key not found exception
   */
  @Test
  public void test009_remove_removes_correctly() {
    try {
      // inserts 50 nodes, which should cause a rehashing in case that affects remove
      for (int index = 0; index < 50; index++) {
        htIntegerKey.insert(index, "test" + index);
      }

      // removes every other key value pair from the hash table
      for (int index = 0; index < 50; index += 2) {
        if (!htIntegerKey.remove(index)) {
          fail("remove returned false on a key which does have a node in the table");
        }
      }

      // checks that size has properly been decreased to 25
      if (htIntegerKey.numKeys() != 25) {
        fail("remove did not decrease size correctly");
      }

      // checks that get can no longer access removed key value pairs (all even keys)
      for (int index = 0; index < 50; index += 2) {
        if (!htIntegerKey.remove(index)) {
          try {
            htIntegerKey.get(index);
            fail("get did not thrown an exception trying to return the value for a removed key");
          } catch (KeyNotFoundException e) {
            // expected as all keys being searched for are not in the table
          }
        }
      }
    } catch (Exception e) {
      fail("no exception should be thrown here");
    }
  }

  /**
   * Tests that the getCapacity(), getLoadFactor(), and getLoadFactorThreshold() methods return the
   * correct values on an empty table
   */
  @Test
  public void test010_initial_capacity_LF_and_LFthreshold() {
    // checks that getCapacity returns the correct initial capacity, which with the default
    // constructor is 50
    if (htIntegerKey.getCapacity() != 50) {
      fail("the getCapacity() method returns " + htIntegerKey.getCapacity()
          + " instead of 50 (which is the default constructor's inital and current capacity as no"
          + " elements have been added");
    }

    // checks that the load factor of a hash table with a no keys is 0
    if (htIntegerKey.getLoadFactor() != 0) {
      fail("the load factor of the hash table is " + htIntegerKey.getLoadFactor()
          + " instead of 0 despite having 0 keys in the table");
    }

    // checks that LFThreshold returns the correct value (0.75 with the default constructor)
    if (htIntegerKey.getLoadFactorThreshold() != 0.75) {
      fail("the getLoadFactorThreshold() returned " + htIntegerKey.getLoadFactorThreshold()
          + " instead of 0.75 (which is the default constructor's threshold value)");
    }
  }

  /**
   * Tests that the hash table's rehashing method works correctly by adding keys to the hash table,
   * checking its capacity, and checking its load factor.
   */
  @Test
  public void test011_test_rehashing() {
    try {
      // adds 36 elements to a hash table of size 50, which should have a LF of 0.72
      for (int index = 0; index < 37; index++) {
        htIntegerKey.insert(index, "test" + index);
      }

      // checks that LF is 0.74
      if (htIntegerKey.getLoadFactor() != 0.74) {
        fail("Load factor of hash table with capacity of 50 and 37 keys should be 0.74 but "
            + "getLoadFactor() returned " + htIntegerKey.getLoadFactor());
      }

      // checks that capacity has not been increased
      if (htIntegerKey.getCapacity() != 50) {
        fail("Capacity was changed despite not enough keys being added to the hash table to cause a"
            + " rehash");
      }

      // adds one more key, which should cause a rehash
      htIntegerKey.insert(37, "test37");

      // checks that LF is 37/101
      if (htIntegerKey.getLoadFactor() != (38.0 / 101.0)) {
        fail(
            "Load factor of hash table with capacity of 101 (due to a rehash) and 37 keys should be "
                + "approximately 0.376 but " + "getLoadFactor() returned "
                + htIntegerKey.getLoadFactor());
      }

      // checks that capacity has been increased to 101
      if (htIntegerKey.getCapacity() != 101) {
        fail("Capacity was not changed to the correct value, 101, upon rehash. Instead, it is "
            + htIntegerKey.getCapacity());
      }

    } catch (Exception e) {
      fail("no exceptions should be thrown here");
    }
  }

  /**
   * Tests collision handling by adding keys which have the same hash index and then checking that
   * the correct values associated with the keys are returned with the get() method.
   */
  @Test
  public void test012_collision_handling() {
    try {
      // adds 10 keys who all have same same hash index to test that collisions are handled
      // properly. Each key should have a hash index of 0
      for (int index = 0; index < 10; index++) {
        htIntegerKey.insert(index * 50, "test" + index);
      }

      // retrieves the values for each key to make sure the correct value is returned for each key
      for (int index = 0; index < 10; index++) {
        if (!(htIntegerKey.get(index * 50).equals("test" + index))) {
          fail("get() failed to return the corect value for a key which causes a collision."
              + " get() returned " + htIntegerKey.get(index * 50) + " instead of " + index);
        }
      }
    } catch (Exception e) {
      fail("no exceptions should be thrown here");
    }
  }

  /**
   * Tests that table can expand to fit 1000 keys
   */
  @Test
  public void test013_add_many_to_table() {
    try {
      // inserts 1000 keys
      for (int index = 0; index < 1000; index++) {
        htIntegerKey.insert(index, "test" + index);
      }

      // checks that size is now 1000
      if (htIntegerKey.numKeys() != 1000) {
        fail("1000 keys were added but the number of keys returned is " + htIntegerKey.numKeys());
      }

      // checks that capacity has expanded 5 times to 1631
      if (htIntegerKey.getCapacity() != 1631) {
        fail(
            "The capacity of the hash table after 5 expansions should be 1631 but the returned capacity is "
                + htIntegerKey.getCapacity());
      }
    } catch (Exception e) {
      fail("no exceptions should be thrown here");
    }
  }

  /**
   * Tests that the non-default constructor works correctly by checking the capacity, load factor,
   * and load factor threshold
   */
  @Test
  public void test014_using_non_default_constructor() {
    // checks that getCapacity returns the correct initial capacity, which with the default
    // constructor is 50

    // creates a hash table with non default constructor
    HashTable<Integer, String> htIntegerKey2 = new HashTable<Integer, String>(25, 0.8);

    // checks that getCapacity returns the correct initial capacity, which in this case is 25
    if (htIntegerKey2.getCapacity() != 25) {
      fail("the getCapacity() method returns " + htIntegerKey.getCapacity()
          + " instead of 50 (which is the default constructor's inital and current capacity as no"
          + " elements have been added");
    }

    // checks that the load factor of a hash table with a no keys is 0
    if (htIntegerKey2.getLoadFactor() != 0) {
      fail("the load factor of the hash table is " + htIntegerKey.getLoadFactor()
          + " instead of 0 despite having 0 keys in the table");
    }

    // checks that LFThreshold returns the correct value 0.8
    if (htIntegerKey2.getLoadFactorThreshold() != 0.8) {
      fail("the getLoadFactorThreshold() returned " + htIntegerKey.getLoadFactorThreshold()
          + " instead of 0.75 (which is the default constructor's threshold value)");
    }
  }

  /**
   * Tests that the hash table expands correctly with the non-default initial capacity and load
   * factor threshold values.
   */
  @Test
  public void test015_expansion_non_default() {
    // creates hash tables with non default constructors
    HashTable<Integer, String> htIntegerKey2 = new HashTable<Integer, String>(25, 0.8);
    HashTable<Integer, String> htIntegerKey3 = new HashTable<Integer, String>(10, 0.6);

    try {
      // adds 19 elements to htIntegerKey2, which should have a LF of 0.76
      for (int index = 0; index < 19; index++) {
        htIntegerKey2.insert(index, "test" + index);
      }

      // checks that LF is 0.76
      if (htIntegerKey2.getLoadFactor() != 0.76) {
        fail("Load factor of hash table with capacity of 25 and 19 keys should be 0.76 but "
            + "getLoadFactor() returned " + htIntegerKey2.getLoadFactor());
      }

      // checks that capacity has not been increased
      if (htIntegerKey2.getCapacity() != 25) {
        fail("Capacity was changed despite not enough keys being added to the hash table to cause a"
            + " rehash");
      }

      // adds one more key, which should cause a rehash
      htIntegerKey2.insert(20, "test20");

      // checks that LF is 20/51
      if (htIntegerKey2.getLoadFactor() != (20.0 / 51.0)) {
        fail(
            "Load factor of hash table with capacity of 51 (due to a rehash) and 20 keys should be "
                + "approximately 0.392 but " + "getLoadFactor() returned "
                + htIntegerKey2.getLoadFactor());
      }

      // checks that capacity has been increased to 51
      if (htIntegerKey2.getCapacity() != 51) {
        fail("Capacity was not changed to the correct value, 51, upon rehash. Instead, it is "
            + htIntegerKey2.getCapacity());
      }

      // adds 5 elements to htIntegerKey3, which should have a LF of 0.5
      for (int index = 0; index < 5; index++) {
        htIntegerKey3.insert(index, "test" + index);
      }

      // checks that LF is 0.5
      if (htIntegerKey3.getLoadFactor() != 0.5) {
        fail("Load factor of hash table with capacity of 10 and 5 keys should be 0.5 but "
            + "getLoadFactor() returned " + htIntegerKey3.getLoadFactor());
      }

      // checks that capacity has not been increased
      if (htIntegerKey3.getCapacity() != 10) {
        fail("Capacity was changed despite not enough keys being added to the hash table to cause a"
            + " rehash");
      }

      // adds one more key, which should cause a rehash
      htIntegerKey3.insert(5, "test5");

      // checks that LF is 6/21
      if (htIntegerKey3.getLoadFactor() != (6.0 / 21.0)) {
        fail("Load factor of hash table with capacity of 21 (due to a rehash) and 5 keys should be "
            + "approximately 0.286 but " + "getLoadFactor() returned "
            + htIntegerKey3.getLoadFactor());
      }

      // checks that capacity has been increased to 21
      if (htIntegerKey3.getCapacity() != 21) {
        fail("Capacity was not changed to the correct value, 21, upon rehash. Instead, it is "
            + htIntegerKey3.getCapacity());
      }
    } catch (Exception e) {
      fail("no exceptions should be thrown here");
    }
  }
}
