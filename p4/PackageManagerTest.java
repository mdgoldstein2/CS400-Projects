// Title: PackageManagerTest
// Files: PackageManager, Set, Stack, LinkedList, HashSet, Queue
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 4/16/19

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * JUnit testing class which tests that the methods of PackageManager work correctly
 * 
 * @author Michael Goldstein
 * @see PackageManager
 *
 */
public class PackageManagerTest {
  PackageManager testManager; // package manager on which all tests will be run

  /**
   * Method which runs before each test method. Initializes the testing package manager
   */
  @BeforeEach
  public void beforeEach() {
    testManager = new PackageManager();
  }

  /**
   * Method which runs after each test method. Dereferences the testing package manager
   */
  @AfterEach
  public void afterEach() {
    testManager = null;
  }


  /**
   * Tests that the testManager's basic getter work correctly without having constructed a graph
   * 
   * @see PackageManager#getAllPackages()
   */
  @Test
  public void test000_getAllPackages() {
    if (!testManager.getAllPackages().isEmpty()) { // there should be no packages at start
      fail("The list of all packages should be empty when no graph is constructed, but the "
          + "list is not empty");
    }
  }

  /**
   * Checks that PackageManager correctly constructs a correct dependency graph from a valid JSON
   * file
   * 
   * @see PackageManager#constructGraph(String)
   */
  @Test
  public void test001_constructGraphWithValidJSON() {
    try {
      testManager.constructGraph("testJSON001.json");

      // checks that all packages are in the graph
      Set<String> packages = testManager.getAllPackages();
      if (!packages.contains("A")) {
        fail("The JSON file lists A as a package, but it is not in the graph");
      }

      if (!packages.contains("B")) {
        fail("The JSON file lists B as a package, but it is not in the graph");
      }

      if (!packages.contains("C")) {
        fail("The JSON file lists C as a package, but it is not in the graph");
      }

      if (!packages.contains("D")) {
        fail("The JSON file lists D as a package, but it is not in the graph");
      }

      if (!packages.contains("E")) {
        fail("The JSON file lists E as a package, but it is not in the graph");
      }

      if (!packages.contains("G")) {
        fail("The JSON file lists G as a dependecy and it should be in graph,"
            + ", but it is not in the graph");
      }
    } catch (Exception e) {
      fail("No exception should be thrown here as the JSON file is correctly formatted");
    }
  }

  /**
   * Tests that constructGraph() throws a FileNotFoundException when an invalid file name is given
   * 
   * @see PackageManager#constructGraph(String)
   */
  @Test
  public void test002_fileNotFound() {
    try {
      testManager.constructGraph("badFilepath.json");
      fail("constructGraph() should have thrown a FileNotFoundException when an invalid "
          + "filename was given");
    } catch (FileNotFoundException e) {
      // this should occur as the filename does not point to a JSON file
    } catch (Exception e) {
      fail("constructGraph() threw a " + e.getClass()
          + " instead of a FileNotFoundException when an invald JSON file name was given");
    }
  }

  /**
   * Tests that constructGraph() can properly handle an incorrectly formatted JSON file
   * 
   * @see PackageManager#constructGraph(String)
   */
  @Test
  public void test003_badJSONFile() {
    try {
      testManager.constructGraph("testJSON002.json");
      fail("An exception should be thrown when an incorrectly formatted JSON file was given,"
          + ", but no exception was thrown");
    } catch (Exception e) {
      // this should happen as the poorly formatted json file should cause a NullPointerException
    }
  }

  /**
   * Tests that the correct installation orders are given for a valid, acyclic JSON
   * 
   * @see PackageManager#getInstallationOrder(String)
   */
  @Test
  public void test004_getInstallOrder() {
    try {
      testManager.constructGraph("testJSON001.json"); // constructs graph

      // gets all actual installation orders
      List<String> installOrderA = testManager.getInstallationOrder("A");
      List<String> installOrderB = testManager.getInstallationOrder("B");
      List<String> installOrderC = testManager.getInstallationOrder("C");
      List<String> installOrderD = testManager.getInstallationOrder("D");
      List<String> installOrderE = testManager.getInstallationOrder("E");
      List<String> installOrderG = testManager.getInstallationOrder("G");

      // creates expected installation order
      List<String> expectecdInstallOrderA =
          new LinkedList<String>(Arrays.asList("G", "E", "D", "B", "A"));
      List<String> expectecdInstallOrderB =
          new LinkedList<String>(Arrays.asList("G", "E", "D", "B"));
      List<String> expectecdInstallOrderC = new LinkedList<String>(Arrays.asList("G", "E", "C"));
      List<String> expectecdInstallOrderD = new LinkedList<String>(Arrays.asList("G", "E", "D"));
      List<String> expectecdInstallOrderE = new LinkedList<String>(Arrays.asList("G", "E"));
      List<String> expectecdInstallOrderG = new LinkedList<String>(Arrays.asList("G"));

      // makes sure actual and expected orders match
      if (!installOrderA.equals(expectecdInstallOrderA)) {
        fail("The install order for package A was " + installOrderA + ", but should have been "
            + expectecdInstallOrderA);
      }
      if (!installOrderB.equals(expectecdInstallOrderB)) {
        fail("The install order for package B was " + installOrderB + ", but should have been "
            + expectecdInstallOrderB);
      }
      if (!installOrderC.equals(expectecdInstallOrderC)) {
        fail("The install order for package C was " + installOrderC + ", but should have been "
            + expectecdInstallOrderC);
      }
      if (!installOrderD.equals(expectecdInstallOrderD)) {
        fail("The install order for package D was " + installOrderD + ", but should have been "
            + expectecdInstallOrderD);
      }
      if (!installOrderE.equals(expectecdInstallOrderE)) {
        fail("The install order for package E was " + installOrderE + ", but should have been "
            + expectecdInstallOrderE);
      }
      if (!installOrderG.equals(expectecdInstallOrderG)) {
        fail("The install order for package G was " + installOrderG + ", but should have been "
            + expectecdInstallOrderG);
      }
    } catch (Exception e) {
      fail("No exception should be thrown when finding installation order for valid, acyclic graph "
          + ", but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Tests that getInstallationOrder() returns the correct installation order when a dependency of a
   * package is listed before the package in another package's dependency list
   * 
   * @see PackageManager#getInstallationOrder(String)
   */
  @Test
  public void test005_getInstallOrderDependencyBeforePackageWhenBothDependenciesofSamePackage() {
    try {
      testManager.constructGraph("testJSON005.json"); // constructs graph

      // install order for G. W, X, and Y and dependencies of G, and X is a dependency of Y, but is
      // listed before Y in G's dependency list
      List<String> installOrderG = testManager.getInstallationOrder("G");

      // expected install order for g
      List<String> expectecdInstallOrderG =
          new LinkedList<String>(Arrays.asList("E", "W", "X", "Y", "A", "Z", "G"));

      // checks that the install order for G matches the expected install order for G
      if (!installOrderG.equals(expectecdInstallOrderG)) {
        fail("The install order for package G was " + installOrderG + ", but should have been "
            + expectecdInstallOrderG);
      }
    } catch (Exception e) {
      fail("No exception should be thrown when finding installation order for valid, acyclic graph "
          + ", but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Checks that getInstallOrder() finds a cycle in the install order when the graph is cyclic
   * 
   * @see PackageManager#getInstallationOrder(String)
   */
  @Test
  public void test006_getInstallOrderFindsCycle() {
    try {
      testManager.constructGraph("testJSON003.json"); // constructs graph
      testManager.getInstallationOrder("A");
      fail("A CycleException should be thrown as the install order for A has a cycle");
    } catch (CycleException e) {
      // there is a cycle in the install order for "A"
    } catch (Exception e) {
      fail("A CycleException should be thrown when finding installation "
          + "order for A in from a cyclic graph, but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Checks that getInstallOrder() ignores a cycle in the graph when it is not in the installation
   * order
   * 
   * @see PackageManager#getInstallationOrder(String)
   */
  @Test
  public void test007_getInstallOrderDoesNotFindsCycle() {
    try {
      testManager.constructGraph("testJSON004.json"); // constructs graph with cycle
      testManager.getInstallationOrder("A"); // tries to get installation order with cycle
    } catch (Exception e) {
      fail("No exception should be thrown when finding installation order for A in from a cyclic "
          + "graph since the installation order of A has not cycle, but a " + e.getClass()
          + " was thrown");
    }
  }

  /**
   * Checks that getInstallOrder() ignores a cycle in the graph when it is not in the installation
   * order
   * 
   * @see PackageManager#getInstallationOrder(String)
   */
  @Test
  public void test008_getInstallOrderNotInGraph() {
    try {
      testManager.constructGraph("testJSON004.json"); // constructs graph
      testManager.getInstallationOrder("Z"); // installation order of package not in graph
    } catch (PackageNotFoundException e) {
      // this should occur as "Z" is never added through JSON to graph
    } catch (Exception e) {
      fail(
          "A PackageNotFoundException should be thrown when finding installation order for Z since "
              + "Z is not in graph, but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Tests that toInstall returns the correct installation order when both packages are in the graph
   * and neither package has a cycle
   * 
   * @see PackageManager#toInstall(String, String)
   */
  @Test
  public void test009_toInstallValid() {
    try {
      testManager.constructGraph("testJSON001.json"); // constructs graph

      // installation orders of A given B is installed and A given C is installed
      List<String> toInstallAB = testManager.toInstall("A", "B");
      List<String> toInstallAC = testManager.toInstall("A", "C");

      // expected installation orders of A given B is installed and A given C is installed
      List<String> expectedToInstallAB = new LinkedList<String>(Arrays.asList("A"));
      List<String> expectedToInstallAC = new LinkedList<String>(Arrays.asList("D", "B", "A"));

      // compares actual installation orders to expected installation orders
      if (!toInstallAB.equals(expectedToInstallAB)) {
        fail("The installation order for A given that B is installed was " + toInstallAB
            + ", but should have been " + expectedToInstallAB);
      }

      if (!toInstallAC.equals(expectedToInstallAC)) {
        fail("The installation order for A given that C is installed was " + toInstallAC
            + ", but should have been " + expectedToInstallAC);
      }
    } catch (Exception e) {
      fail("No exception should be thrown when finding installation order for A given B is"
          + " installed as both are in graph, but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Checks that toInstall() finds a cycle in the install order when the graph is cyclic for either
   * node
   * 
   * @see PackageManager#toInstall(String, String)
   */
  @Test
  public void test010_toInstallOrderFindsCycle() {
    try {
      testManager.constructGraph("testJSON003.json"); // constructs graph
      testManager.toInstall("C", "F"); // tries to run toInstall on packages with cycles
      fail("A CycleException should be thrown as the install orders for C and F have a cycle");
    } catch (CycleException e) {
      // there is a cycle in the install order for "A"
    } catch (Exception e) {
      fail("A CycleException should be thrown when finding installation "
          + "order for C given F from a cyclic graph, but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Tests that toInstall() throws a PackageNotFoundException when any of its package arguments are
   * not in the graph
   * 
   * @see PackageManager#toInstall(String, String)
   */
  @Test
  public void test011_ToInstallPackagesNotInGraph() {
    // tries toInstall with installed package not in graph
    try {
      testManager.constructGraph("testJSON004.json"); // constructs graph
      testManager.toInstall("A", "Z"); // toInstall with installed package not in graph
    } catch (PackageNotFoundException e) {
      // this should occur as "Z" is never added through JSON to graph
    } catch (Exception e) {
      fail("A PackageNotFoundException should be thrown when toInstall is called with Z as the "
          + "installed package as Z is not in graph, but a " + e.getClass() + " was thrown");
    }

    // tries toInstall with package to be installed not in graph
    try {
      testManager.constructGraph("testJSON004.json"); // constructs graph
      testManager.toInstall("Z", "A"); // toInstall with package to be installed not in graph
    } catch (PackageNotFoundException e) {
      // this should occur as "Z" is never added through JSON to graph
    } catch (Exception e) {
      fail("A PackageNotFoundException should be thrown when toInstall is called with Z as the "
          + "package to be installed as Z is not in graph, but a " + e.getClass() + " was thrown");
    }

    // tries toInstall with both packages not in graph
    try {
      testManager.constructGraph("testJSON004.json"); // constructs graph
      testManager.toInstall("Z", "Q"); // toInstall with both packages
    } catch (PackageNotFoundException e) {
      // this should occur as "Z" is never added through JSON to graph
    } catch (Exception e) {
      fail("A PackageNotFoundException should be thrown when toInstall is called with two packages "
          + "not in graph, but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Tests the getPackageWithMaxDependencies() returns the package with the most dependencies
   * 
   * @see PackageManager#getPackageWithMaxDependencies()
   */
  @Test
  public void test012_getPackageWithMaxDependencies() {
    try {
      testManager.constructGraph("testJSON001.json"); // constructs graph

      // finds package with most dependencies
      String packageWithMostDependencies = testManager.getPackageWithMaxDependencies();

      // expected package with most dependencies, which should be A
      String expectedPackageWithMostDependencies = "A";

      // compares actual to expected package with most dependencies
      if (!packageWithMostDependencies.equals(expectedPackageWithMostDependencies)) {
        fail("The package with the most dependencies was " + packageWithMostDependencies
            + ", but should have been " + expectedPackageWithMostDependencies);
      }
    } catch (Exception e) {
      fail("No exception should be thrown when finding the package with the most "
          + "dependencies in a graph without a cycle,, but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Checks that getPackageWithMaxDependencies() detects a cycle when the graph has a cycle
   * 
   * @see PackageManager#getPackageWithMaxDependencies()
   */
  @Test
  public void test013_getPackageWithMaxDependenciesDetectsCycle() {
    try {
      testManager.constructGraph("testJSON004.json"); // constructs graph

      // tries to find package with most dependencies on acyclic graph
      testManager.getPackageWithMaxDependencies();
      fail("A CycleException should have been thrown as the graph has a cycle");
    } catch (CycleException e) {
      // this should happen
    } catch (Exception e) {
      fail("A CycleException should be thrown when finding the package with the most "
          + "dependencies in a graph with a cycle,, but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Tests that getInstallationOrderForAllPackages returns the correct installation order
   * 
   * @see PackageManager#getInstallationOrderForAllPackages()
   */
  @Test
  public void test014_getInstallationOrderForAllPackages() {
    try {
      testManager.constructGraph("testJSON001.json"); // constructs graph with cycle

      // finds installation order for all packages
      List<String> installationOrder = testManager.getInstallationOrderForAllPackages();

      // expected installation order for all packages
      List<String> expectedInstallationOrder =
          new LinkedList<String>(Arrays.asList("G", "E", "C", "D", "B", "A"));

      // compares actual to expected package with most dependencies
      if (!installationOrder.equals(expectedInstallationOrder)) {
        fail("The package with the most dependencies was " + installationOrder
            + ", but should have been " + expectedInstallationOrder);
      }
    } catch (Exception e) {
      fail("No exception should be thrown when finding the installation order for all pacakges in a"
          + " graph without a cycle,, but a " + e.getClass() + " was thrown");
    }
  }

  /**
   * Tests that getInstallationOrderForAllPackages detects a cycle in the graph
   * 
   * @see PackageManager#getInstallationOrderForAllPackages()
   */
  @Test
  public void test015_getInstallationOrderForAllPackagesFindsCycle() {
    try {
      testManager.constructGraph("testJSON004.json"); // constructs graph with cycle

      // tries to find installation order for all packages of an acyclic graph
      testManager.getPackageWithMaxDependencies();
      fail("A CycleException should have been thrown as the graph has a cycle");
    } catch (CycleException e) {
      // this should happen
    } catch (Exception e) {
      fail("A CycleException should be thrown when finding the installation order for all packages "
          + "in a graph with a cycle, but a " + e.getClass() + " was thrown");
    }
  }
}
