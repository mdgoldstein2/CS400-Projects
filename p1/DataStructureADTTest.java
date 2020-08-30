
// Title: DataStructureADTTest
// Files: JUnit Assertions and Annotations
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 2/7/19

// Outside Sources
// Persons: None
// Online Sources: None

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Abstract superclass which has methods to check whether or not a DataStructureADT implementation
 * works correctly
 * 
 * @author Michael Goldstein
 * @see DataStructureADT
 * @param <T> The data structure implementation being tested
 */
abstract class DataStructureADTTest<T extends DataStructureADT<String, String>> {

  private T dataStructureInstance; // an instance of the data structure implementation to test

  /**
   * Abstract method that creates an instance of the data structure implementation to be tested
   * 
   * @return an instance of the data structure implementation to be tested
   */
  protected abstract T createInstance();

  /**
   * Setup method run before all test methods. Not used in this situations
   * 
   * @throws Exception if any of the methods called within it throws an exception
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception {}

  /**
   * Method which runs after all tests completed. Not used in this case
   * 
   * @throws Exception if any of the methods called within it throws an exception
   */
  @AfterAll
  static void tearDownAfterClass() throws Exception {}

  /**
   * Method which runs before each test. Calls the createInstance() method to create an instance of
   * the data structure implementation to be tested. @ see @
   * 
   * @see DataStructureADTTest#createInstance()
   * 
   * @throws Exception if the createInstance() method throws an exception
   */
  @BeforeEach
  void setUp() throws Exception {
    dataStructureInstance = createInstance();
  }

  /**
   * Method which runs after each test. Not used in this case
   * 
   * @throws Exception if any methods called within it throw an exception
   */
  @AfterEach
  void tearDown() throws Exception {
    dataStructureInstance = null;
  }

  /**
   * Tests if the dataStructure size instance is 0 (as it should be) when no element is inserted.
   * Fails if size is not 0
   */
  @Test
  void test00_empty_ds_size() {
    if (dataStructureInstance.size() != 0) { // checks if the data structure instance's size is 0
      fail("data structure should be empty, with size=0, but size=" + dataStructureInstance.size());
    }
  }

  /**
   * Tests if the data structure instance's size increases to 1 when 1 key, value pair is inserted.
   * Fails if size is not 1
   */
  @Test
  void test01_after_insert_one_size_is_one() {
    dataStructureInstance.insert("testing", "value"); // adds a valid key, value pair

    if (dataStructureInstance.size() != 1) { // checks if the data structure instance's size is 1
      fail("data structure should have one (key, value) pair, with size=1, but size="
          + dataStructureInstance.size());
    }
  }

  /**
   * Tests if the dataStructure size instance is 0 (as it should be) when an element is inserted and
   * removed. Fails if size is not 0
   */
  @Test
  void test02_after_insert_one_remove_one_size_is_0() {
    dataStructureInstance.insert("valid", "value"); // adds a valid key, value pair
    dataStructureInstance.remove("valid"); // should remove the previously added pair

    if (dataStructureInstance.size() != 0) { // checks if the data structure instance's size is 0
      fail("data structure should be empty, with size=0, but size=" + dataStructureInstance.size());
    }
  }

  /**
   * Tests if inserting elements with the same key throws a RuntimeException. If a RuntimeException
   * is not thrown, the test fails.
   * 
   * @see DataStructureADT#insert(Comparable, Object)
   */
  @Test
  void test03_duplicate_exception_is_thrown() {
    dataStructureInstance.insert("valid", "value"); // adds a valid key, value pair

    try {
      // adds an element with the same key as one already added. Should throw RuntimeException
      dataStructureInstance.insert("valid", "value1");
      fail(
          "data structure insert() method did not throw a RuntimeExcpetion when an element with the"
              + " same key as " + "one" + " already inserted in the data structure was inserted");
    } catch (RuntimeException a) {
      // nothing here as test passes if a RuntimeException is caught
    }
  }

  /**
   * Tests if the remove() method returns false when a key not present in the data structure is
   * given as a parameter. Test fails if remove() returns true.
   * 
   * @see DataStructureADT#remove(Comparable);
   */
  @Test
  void test04_remove_returns_false_when_key_not_present() {
    dataStructureInstance.insert("valid", "value"); // adds a valid key, value pair
    dataStructureInstance.insert("valid2", "value1"); // adds a valid key, value pair
    dataStructureInstance.insert("valid3", "value4"); // adds a valid key, value pair

    // remove should return false since key does not exist. If remove returns true, the test fails
    if (dataStructureInstance.remove("notPresentKey")) {
      fail(
          "data structure remove() method did not return false when a key that was not present was "
              + "given as an argument");
    }
  }

  /**
   * Tests if the insert() method throws an IllegalArgumentException when an element with a null key
   * is inserted. Fails if insert() does not throw an IllegalArgumentException.
   * 
   * @see DataStructureADT#insert(Comparable, Object)
   */
  @Test
  void test05_insert_null_key_IllegalArgumentException() {
    try {
      dataStructureInstance.insert(null, "value");
      fail("data structure insert() method did not throw an IllegalArgumentExxception when an "
          + "element " + "with a null key was inserted");
    } catch (IllegalArgumentException a) {
      // nothing here as test passes if an IllegalArgumentException is caught
    }
  }

  /**
   * Tests if the get() method throws an IllegalArgumentException when a null key is given as an
   * argument. Fails if get() does not throw an IllegalArgumentException.
   * 
   * @see DataStructureADT#get(Comparable)
   */
  @Test
  void test06_get_null_key_IllegalArgumentException() {
    dataStructureInstance.insert("valid", "value"); // adds valid element
    dataStructureInstance.insert("also valid", "value"); // adds valid element
    try {
      dataStructureInstance.get(null); // runs get() method with a null key argument
      fail("data structure get() method did not throw an IllegalArgumentExxception when a null key "
          + "was" + " given as the argument");
    } catch (IllegalArgumentException a) {
      // nothing here as test passes if an IllegalArgumentException is caught
    }
  }

  /**
   * Tests if the insert() method throws an IllegalArgumentException when a null key is given as an
   * argument. Fails if insert() does not throw an IllegalArgumentException.
   * 
   * @see DataStructureADT#insert(Comparable, Object)
   */
  @Test
  void test07_insert_null_key_IllegalArgumentException() {
    try {
      dataStructureInstance.insert(null, "value"); // runs insert() method with a null key argument
      fail("data structure insert() method did not throw an IllegalArgumentExxception when a null "
          + "key " + "was" + " given as part of the argument");
    } catch (IllegalArgumentException a) {
      // nothing here as test passes if an IllegalArgumentException is caught
    }
  }

  /**
   * Tests if the get() method returns null when a key which does not exist in the data structure is
   * given as an argument. Fails if get() does not return null
   * 
   * @see DataStructureADT#get(Comparable)
   */
  @Test
  void test08_get_returns_null_when_key_not_present() {
    dataStructureInstance.insert("valid", "value"); // adds valid element
    dataStructureInstance.insert("also valid", "value"); // adds valid element

    // searches for an element with a key that is not in the data structure
    if (dataStructureInstance.get("not present") != null) {
      fail("data structure get() method did not return null when an object was not in the data "
          + "structure");
    }
  }

  /**
   * Tests if the get() method returns the correct value when a key which is in the data structure
   * is given as an argument. Fails if get() does not return the correct value
   * 
   * @see DataStructureADT#get(Comparable)
   */
  @Test
  void test09_contains_returns_true_when_an_element_with_key_is_present() {
    dataStructureInstance.insert("valid", "value"); // adds valid element
    dataStructureInstance.insert("also valid", "value"); // adds valid element

    // searches for an element with a key that is in the data structure
    if (!dataStructureInstance.get("also valid").equals("value")) {
      fail("data structure get() method did not return correct value for an element with the given "
          + "key");
    }
  }

  /**
   * Tests if remove function correctly removes a value from the data structure. Fails if contains
   * returns true for the element which should have been removed.
   * 
   * @see DataStructureADT#remove(Comparable)
   */
  @Test
  void test10_remove_correctly_removes_an_element() {
    dataStructureInstance.insert("valid", "value"); // adds valid element
    dataStructureInstance.insert("also valid", "value"); // adds valid element
    dataStructureInstance.remove("also valid"); // removes last node

    // checks if node is still in data structure
    if (dataStructureInstance.contains("also valid")) {
      fail("data structure remove() method did not remove an element with the given " + "key");
    }
  }

  /**
   * Tests if data structure can successfully insert 500 elements by adding 500 elements, then
   * checking if size is 500. FAils if size is not 500.
   * 
   * @see DataStructureADT#insert(Comparable, Object)
   */
  @Test
  void test11_add_500() {
    for (int index = 0; index < 500; index++) {
      dataStructureInstance.insert("valid" + index, "value"); // adds 500 valid elements
    }

    if (dataStructureInstance.size() != 500) { // checks if size is now 500
      fail("data structure did not properly insert 500 elements");
    }
  }

  /**
   * Checks if an element that has the same key as a removed element can be inserted. Fails if
   * insert() throws a RuntimeExcpetion when an element with the same key as a removed one is added.
   * 
   * @see DataStructureADT#insert(Comparable, Object)
   */
  @Test
  void test12_adding_key_from_removed_element() {
    dataStructureInstance.insert("valid", "value"); // adds valid element
    dataStructureInstance.insert("also valid", "value"); // adds valid element
    dataStructureInstance.remove("also valid"); // removes last node

    try {
      dataStructureInstance.insert("also valid", "value"); // re-inserts removed node
    } catch (RuntimeException a) {
      fail("data structure's insert() threw an exception when a an element with the same "
          + "key as a " + "removed element was inserted");
    }
  }

  /**
   * Tests if data structure can successfully insert 500 elements by adding 500 elements, then
   * checking if size is 500. FAils if size is not 500.
   * 
   * @see DataStructureADT#insert(Comparable, Object)
   */
  @Test
  void test13_removing_does_not_remove_other_element_after() {
    for (int index = 0; index < 10; index++) {
      dataStructureInstance.insert("valid" + index, "value"); // adds 10 valid elements
    }

    dataStructureInstance.remove("valid4"); // removes one

    // checks if element after removed element is in data structure
    if (!dataStructureInstance.contains("valid5")) {
      fail("data structure remove() method removed element from after the element which should"
          + " have been removed");
    }
  }

  /**
   * Tests if data structure can successfully insert 500 elements by adding 500 elements, then
   * checking if size is 500. FAils if size is not 500.
   * 
   * @see DataStructureADT#insert(Comparable, Object)
   */
  @Test
  void test14_removing_does_not_remove_other_element_before() {
    for (int index = 0; index < 10; index++) {
      dataStructureInstance.insert("valid" + index, "value"); // adds 10 valid elements
    }

    dataStructureInstance.remove("valid4"); // removes one

    // checks if element before removed element is in data structure
    if (!dataStructureInstance.contains("valid3")) {
      fail("data structure remove() method removed element from before the element which should"
          + " have been removed");
    }
  }

  /**
   * Tests if get() method alters size of the data structure. Fails if size after get9) is called is
   * different from size before get() is called.
   * 
   * @see DataStructureADT#get()
   */
  @Test
  void test15_get_does_not_change_size() {
    for (int index = 0; index < 10; index++) {
      dataStructureInstance.insert("valid" + index, "value"); // adds 10 valid elements
    }

    // records size of data structure before get() is called. Makes sure that test will pass if
    // get() does not alter size even if size() method returns incorrect size before get() is called
    int sizeBeforeGet = dataStructureInstance.size();

    dataStructureInstance.get("valid7"); // returns value of one element

    // compares sizes before and after get() is called
    if (dataStructureInstance.size() != sizeBeforeGet) {
      fail("data structure get() method has changed size of data structure");
    }
  }

  /**
   * Tests if remove() method throws an IllegalArgumenException when it is asked to remove a node
   * with a null key. Fails if an IllegalArgumentException is not thrown
   * 
   * @see DataStructureADT#remove(Comparable)
   */
  @Test
  void test16_remove_null_throws_exception() {
    for (int index = 0; index < 10; index++) {
      dataStructureInstance.insert("valid" + index, "value"); // adds 10 valid elements
    }

    // tries to remove an element with a null key
    try {
      dataStructureInstance.remove(null);
      fail("data structure remove() method did not throw an exception when a null key was given as "
          + "an argument");
    } catch (IllegalArgumentException a) {
      // nothing happens here as an IllegalArgumentException should be thrown
    }
  }

  /**
   * Tests if remove() method changes the data structure's size when it is asked to remove a node
   * with a null key. Fails if size changes after remove() is called with a null element
   * 
   * @see DataStructureADT#remove(Comparable)
   */
  @Test
  void test17_remove_null_does_not_change_size() {
    for (int index = 0; index < 10; index++) {
      dataStructureInstance.insert("valid" + index, "value"); // adds 10 valid elements
    }

    // records size of data structure before remove() is called. Makes sure that test will pass if
    // remove() does not alter size even if size() method returns incorrect size before remove() is
    // called
    int sizeBeforeRemove = dataStructureInstance.size();

    // tries to remove an element with a null key
    try {
      dataStructureInstance.remove(null);
    } catch (IllegalArgumentException a) {
      // nothing happens here as method does not check if an IllegalArgumentException is thrown
    } finally {
      if (dataStructureInstance.size() != sizeBeforeRemove) {
        fail("data structure remove() method has changed size of data structure when it was asked "
            + "to remove a node with a null key");
      }
    }
  }

  /**
   * Checks if size of the data structure is correctly set to 500 after 500 elements are added to
   * it. Fails if size is not 500.
   * 
   * @see DataStructureADT#size()
   */
  @Test
  void test18_add_500_size_500() {
    for (int index = 0; index < 500; index++) {
      dataStructureInstance.insert("valid" + index, "value"); // adds 500 valid elements
    }

    // checks that size is 500
    if (dataStructureInstance.size() != 500) {
      fail("data structure's size is not 500 despite 500 elements being inserted");
    }
  }
}
