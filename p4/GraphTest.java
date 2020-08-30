// Title: GraphTest
// Files: Graph, GraphADT, Set, JUnit
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
import java.util.Set;
import java.util.List;

/**
 * JUnit testing class which tests that the author's implementation of a Graph works correctly
 * 
 * @author Michael Goldstein
 *
 */
public class GraphTest {
  Graph testGraph; // graph on which all tests will be run

  /**
   * Method which runs after each test, setting up a graph to be tested
   */
  @BeforeEach
  public void beforeEach() {
    testGraph = new Graph(); // initializes graph on which all tests will be run
  }


  /**
   * Method which runs after each test, setting the testing graph's reference to null
   */
  @AfterEach
  public void afterEach() {
    testGraph = null; // sets graph's reference to null
  }

  /**
   * Checks that without adding any elements to the graph, the graph's size and order are both 0 and
   * the graph's set of all vertices is empty
   * 
   * @see Graph#size()
   * @see Graph#order()
   * @see Graph#getAllVertices()
   */
  @Test
  public void test000_Constructor() {
    // checks that size should be 0 when no vertices or edges added
    if (testGraph.size() != 0) {
      fail("A graph with no vertices or edges added should have a size of 0 but had a size of: "
          + testGraph.size());
    }

    // checks that order should be 0 when no vertices or edges added
    if (testGraph.order() != 0) {
      fail("A graph with no vertices or edges added should have a order of 0 but had a size of: "
          + testGraph.order());
    }

    // checks that the set of all vertices is empty
    if (!testGraph.getAllVertices().isEmpty()) {
      System.out.println(testGraph.getAllVertices());
      fail("The set of all vertices of a graph with no vertices should be empty, but was not");
    }
  }

  /**
   * Adds one vertex, checks that order has increased by 1 and the added vertex is in the set of
   * vertices
   * 
   * @see Graph#addVertex(String)
   */
  @Test
  public void test001_addVertex() {
    // adds one vertex
    testGraph.addVertex("vertex");

    // checks that the added vertex is in the set of all vertices
    Set<String> vertexSet = testGraph.getAllVertices(); // gets the set of all vertices
    if (!vertexSet.contains("vertex")) {
      fail("A vertex called vertex was added to the graph but was not in the set of all vertices");
    }

    // checks that order has been increased to 1 for adding 1 vertex
    if (testGraph.order() != 1) {
      fail("The order after adding 1 vertex should be 1, but was: " + testGraph.order());
    }
  }

  /**
   * Adds 100 vertices, checks that order has increased by the number of added vertices and that all
   * added vertices are in the graph's set of all vertices
   * 
   * @see Graph#addVertex(String)
   */
  @Test
  public void test002_addManyVertices() {
    // adds in 100 vertices
    for (int index = 0; index < 100; index++) {
      testGraph.addVertex("vertex" + index);
    }

    // checks that each added vertex is in the set of all vertices
    Set<String> vertexSet = testGraph.getAllVertices(); // gets the set of all vertices
    for (int index = 0; index < 100; index++) {
      if (!vertexSet.contains("vertex" + index)) {
        fail("A vertex vertex" + index
            + " was added to the graph but was not in the set of all vertices");
      }
    }

    // checks that order has been increased to 100 for adding 100 vertices
    if (testGraph.order() != 100) {
      fail("The order after adding 100 vertices should be 100, but was: " + testGraph.order());
    }
  }

  /**
   * Tests that when a null vertex is used as the parameter for adding a vector, a null vector is
   * not added and order is not increased
   * 
   * @see Graph#addVertex(String)
   */
  @Test
  public void test003_addNullVertex() {
    // tries to add null vertex
    testGraph.addVertex(null);

    // checks that the null vertex was not added to the graph
    Set<String> vertexSet = testGraph.getAllVertices(); // gets the set of all vertices
    if (vertexSet.contains(null)) {
      fail("A null vertex was succesffuly added to the graph, which should not be allowed");
    }

    // checks that order was not increased from 0 by adding a null vertex
    if (testGraph.order() != 0) {
      fail("After trying to add a null vertex, " + "the order should be 0, but was: "
          + testGraph.order());
    }
  }

  /**
   * Tests that when a duplicate vertex is used as the parameter for adding a vector, a duplicate
   * vector is not added and order is not increased
   * 
   * @see Graph#addVertex(String)
   */
  @Test
  public void test004_addDuplicateVertex() {
    // adds 10 vertices
    for (int index = 0; index < 10; index++) {
      testGraph.addVertex("vertex" + index);
    }

    // tries to add duplicate vertex
    testGraph.addVertex("vertex2");

    // checks that duplicate vertex does not appear multiple times in set of all vertices
    Set<String> vertexSet = testGraph.getAllVertices(); // gets the set of all vertices
    Object[] vertexArray = vertexSet.toArray(); // converts set to array
    int timesVertex2Appears = 0; // tracks how many times vertex, the duplicated vertex, appears

    // indexes through array to check if vertex with duplicate added appears twice
    for (int index = 0; index < vertexArray.length; index++) {
      if (vertexArray[index].equals("vertex2")) {
        timesVertex2Appears++;
      }
    }

    if (timesVertex2Appears != 1) { // if the duplicate does appear more or less than once, fails
      fail("A vertex whose duplicate was attempted to be added to the graph should only appear "
          + "once, but appeared " + timesVertex2Appears + " times");
    }

    // checks that order was not increased from 0 by adding a null vertex
    if (testGraph.order() != 10) {
      fail("After trying to add a duplicate vertex, " + "the order should be 10, but was: "
          + testGraph.order());
    }
  }

  /**
   * Checks that an edge is properly added to the graph by checking that an edge exists and size is
   * increased by 1
   * 
   * @see Graph#addEdge(String, String)
   */
  @Test
  public void test005_addEdge() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between the two
    testGraph.addEdge("vertex1", "vertex2");

    // checks that vertex2 is now in the list of adjacent matrices of vertex1
    List<String> adjacencyList = testGraph.getAdjacentVerticesOf("vertex1");
    if (!adjacencyList.contains("vertex2")) {
      fail("An edge was supposed to be added from vertex1 to vertex2 but vertex2"
          + " is not in vertex1's adjacency list");
    }

    // checks that size has now increased to 1
    if (testGraph.size() != 1) {
      fail("The graph's size after adding one edge should be 1 but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an an edge is not added when the edge's vertices are null
   * 
   * @see Graph#addEdge(String, String)
   */
  @Test
  public void test006_addEdgeNullVertices() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between null vertices
    testGraph.addEdge(null, null);

    // checks that size has not increased from 0
    if (testGraph.size() != 0) {
      fail("The graph's size after adding one edge with null vertices should be 0" + " but was: "
          + testGraph.size());
    }
  }

  /**
   * Checks that an an edge is not added when the edge's start vertex is null
   * 
   * @see Graph#addEdge(String, String)
   */
  @Test
  public void test007_addEdge1stVertexNull() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between null and vertex2
    testGraph.addEdge(null, "vertex2");

    // checks that size has not increased from 0
    if (testGraph.size() != 0) {
      fail("The graph's size after adding one edge with a null start vertex should be 0"
          + " but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an an edge is not added when the edge's end vertex is null
   * 
   * @see Graph#addEdge(String, String)
   */
  @Test
  public void test08_addEdge2ndVertexNull() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between vertex1 and null vertex
    testGraph.addEdge("vertex1", null);

    // checks that nonexistent vertex has not been added to vertex1's adjacency list
    List<String> adjacencyList = testGraph.getAdjacentVerticesOf("vertex1");
    if (adjacencyList.contains(null)) {
      fail("An edge should not have been added from vertex1 to a null vertex");
    }

    // checks that size has not increased from 0
    if (testGraph.size() != 0) {
      fail("The graph's size after adding one edge with a null end vertex should be 0"
          + " but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an edge and missing vertex are added when the edge's start vertex is not in graph
   * 
   * @see Graph#addEdge(String, String)
   */
  @Test
  public void test009_addEdge1stVertexNotInGraph() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between vertex3 and vertex2
    testGraph.addEdge("vertex3", "vertex2");

    // checks that nonexistent vertex should have been added
    Set<String> allVertices = testGraph.getAllVertices();
    if (!allVertices.contains("vertex3")) {
      fail("vertex3 was not in graph but should have been added by addEdge()");
    }

    // checks that vertex2 has been added to nonexistent vertex's adjacency list
    List<String> adjacencyList = testGraph.getAdjacentVerticesOf("vertex3");
    if (!adjacencyList.contains("vertex2")) {
      fail("An edge should have been added from vertex3 to vertex2, but was not since "
          + "vertex3 was not separately added to graph");
    }

    // checks that size has not increased from 0
    if (testGraph.size() != 1) {
      fail("The graph's size after adding one edge with a start vertex not in graph should be 1"
          + " but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an edge and missing vertex are added when the edge's end vertex is not in graph
   * 
   * @see Graph#addEdge(String, String)
   */
  @Test
  public void test010_addEdge2ndVertexNotInGraph() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between vertex1 and null vertex
    testGraph.addEdge("vertex1", "vertex3");

    // checks that nonexistent vertex should have been added
    Set<String> allVertices = testGraph.getAllVertices();
    if (!allVertices.contains("vertex3")) {
      fail("vertex3 was not in graph but should have been added by addEdge()");
    }

    // checks that nonexistent vertex has been added to vertex1's adjacency list
    List<String> adjacencyList = testGraph.getAdjacentVerticesOf("vertex1");
    if (!adjacencyList.contains("vertex3")) {
      fail("An edge should have been added from vertex1 to vertex3, but was not since "
          + "vertex3 was " + "not separately added to graph");
    }

    // checks that size has increased to 1
    if (testGraph.size() != 1) {
      fail("The graph's size after adding one edge with an end vertex not in graph should be 1"
          + " because the missing vertex should be added but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an an edge and missing vertices are added when neither vertex is in graph
   * 
   * @see Graph#addEdge(String, String)
   */
  @Test
  public void test011_addEdgeNeitherVertexInGraph() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between vertices not in graph
    testGraph.addEdge("vertex3", "vertex4");

    // checks that nonexistent start vertex should have been added
    Set<String> allVertices = testGraph.getAllVertices();
    if (!allVertices.contains("vertex3")) {
      fail("vertex3 was not in graph but should have been added by addEdge()");
    }

    // checks that nonexistent end vertex should have been added
    if (!allVertices.contains("vertex4")) {
      fail("vertex4 was not in graph but should have been added by addEdge()");
    }

    // checks that nonexistent vertex4 has been added to vertex3's adjacency list
    List<String> adjacencyList = testGraph.getAdjacentVerticesOf("vertex3");
    if (!adjacencyList.contains("vertex4")) {
      fail("An edge should have been added from vertex3 to vertex4, but was not since "
          + "vertex3 and vertex 4 were not separately added to graph");
    }

    // checks that size has increased to 1
    if (testGraph.size() != 1) {
      fail("The graph's size after adding one edge with vertices not in graph should be 1"
          + " but was: " + testGraph.size());
    }
  }

  /**
   * Adds 20 edges and checks that size and all edges are correctly represented
   * 
   * @see Graph#addEdge(String, String)
   */
  @Test
  public void test012_addManyEdges() {
    // adds 10 vertices
    for (int index = 0; index < 10; index++) {
      testGraph.addVertex("vertex" + index);
    }

    // adds edge between a vertex and its next two vertices, looping around for the last two
    // vertices
    for (int index = 0; index < 10; index++) {
      testGraph.addEdge("vertex" + index, "vertex" + ((index + 1) % 10));
      testGraph.addEdge("vertex" + index, "vertex" + ((index + 2) % 10));
    }

    // checks that size should now be 20 as 20 edges added
    if (testGraph.size() != 20) {
      fail("Size after adding 20 edges should be 20 but was: " + testGraph.size());
    }

    // checks that edge has been added between a vertex and its next two vertices
    for (int index = 0; index < 10; index++) {
      List<String> adjList = testGraph.getAdjacentVerticesOf("vertex" + index);

      if (!adjList.contains("vertex" + ((index + 1) % 10))) {
        fail("An edge between vertex vertex" + index + " and vertex" + ((index + 1) % 10)
            + " but  vertex" + ((index + 1) % 10) + " was not in vertex vertex" + index
            + "'s adjacency list");
      }
      if (!adjList.contains("vertex" + ((index + 2) % 10))) {
        fail("An edge between vertex vertex" + index + " and vertex" + ((index + 2) % 10)
            + " but  vertex" + ((index + 2) % 10) + " was not in vertex vertex" + index
            + "'s adjacency list");
      }
    }
  }

  /**
   * Tests that edges with the same vertexes, but in opposite order are allowed to be added
   * 
   * @see Graph#addEdge(String, String)
   */
  @Test
  public void test013_reverseEdgeAllowed() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between vertex1 and vertex2 and vice versa
    testGraph.addEdge("vertex1", "vertex2");
    testGraph.addEdge("vertex2", "vertex1");

    // checks that size has increased to 2
    if (testGraph.size() != 2) {
      fail("The graph's size after adding two edges with opposite orientations should be 2"
          + " but was: " + testGraph.size());
    }

    // checks that edge has been added between vertex1 and vertex2 and vice versa
    List<String> adjList1 = testGraph.getAdjacentVerticesOf("vertex1");
    if (!adjList1.contains("vertex2")) {
      fail("There should be an edge between vertex1 and vertex2, so vertex2 should be in the "
          + "adjacency list of vertex1 but is not");
    }
    List<String> adjList2 = testGraph.getAdjacentVerticesOf("vertex2");
    if (!adjList2.contains("vertex1")) {
      fail("There should be an edge between vertex2 and vertex1, so vertex1 should be in the "
          + "adjacency list of vertex2 but is not");
    }
  }

  /**
   * Adds and deletes one vertex, checks that order is 0 and the added vertex not in the set of
   * vertices
   * 
   * @see Graph#removeVertex(String)
   */
  @Test
  public void test014_removeVertex() {
    // adds one vertex
    testGraph.addVertex("vertex");

    // removes vertex
    testGraph.removeVertex("vertex");

    // checks that the added vertex is not the set of all vertices
    Set<String> vertexSet = testGraph.getAllVertices(); // gets the set of all vertices
    if (vertexSet.contains("vertex")) {
      fail("A vertex called vertex was deleted from the graph but was in the set of all vertices");
    }

    // checks that order is 0
    if (testGraph.order() != 0) {
      fail("The order after removing 1 vertex should be 0, but was: " + testGraph.order());
    }
  }

  /**
   * Adds and removes 100 vertices, checks that order is 0 and that all added and removed vertices
   * are no longer in the graph's set of all vertices
   * 
   * @see Graph#removeVertex(String)
   */
  @Test
  public void test015_deleteManyVertices() {
    // adds in 100 vertices
    for (int index = 0; index < 100; index++) {
      testGraph.addVertex("vertex" + index);
    }

    // removes 100 vertices
    for (int index = 0; index < 100; index++) {
      testGraph.removeVertex("vertex" + index);
    }

    // checks that each added vertex is not in the set of all vertices
    Set<String> vertexSet = testGraph.getAllVertices(); // gets the set of all vertices
    for (int index = 0; index < 100; index++) {
      if (vertexSet.contains("vertex" + index)) {
        fail("A vertex vertex" + index
            + " was removes to the graph but was in the set of all vertices");
      }
    }

    // checks that order is 0
    if (testGraph.order() != 0) {
      fail("The order after removing 100 vertices should be 0, but was: " + testGraph.order());
    }
  }

  /**
   * Tests that when a null vertex is used as the parameter for removing a vector, order is not
   * decreased
   * 
   * @see Graph#removeVertex(String)
   */
  @Test
  public void test016_removeNullVertex() {
    // adds a vertex
    testGraph.addVertex("vertex");

    // tries to remove null vertex
    testGraph.removeVertex(null);

    // checks that order was not decreased from 1 by removing a null vertex
    if (testGraph.order() != 1) {
      fail("After trying to remove a null vertex, " + "the order should be 1, but was: "
          + testGraph.order());
    }
  }

  /**
   * Tests that when a vertex not in the graph is used as the parameter for removing a vector, no
   * vectors are removed and order does not change
   * 
   * @see Graph#removeVertex(String)
   */
  @Test
  public void test017_removeVertexNotInGraph() {
    // adds 10 vertices
    for (int index = 0; index < 10; index++) {
      testGraph.addVertex("vertex" + index);
    }

    // tries to remove vertex not in graph
    testGraph.removeVertex("vertex11");

    // checks that all existing vertices still in graph
    Set<String> vertexSet = testGraph.getAllVertices(); // gets the set of all vertices
    for (int index = 0; index < 10; index++) {
      if (!vertexSet.contains("vertex" + index)) {
        fail("A vertex vertex" + index
            + " was added and not removed from the graph but was not in the set of all vertices");
      }
    }

    // checks that order was not decreased from 0 by adding a null vertex
    if (testGraph.order() != 10) {
      fail("After trying to remove a null vertex, " + "the order should be 10, but was: "
          + testGraph.order());
    }
  }

  /**
   * Checks that edges pointing to a vertex are removed when that vertex is removed
   * 
   * @see Graph#removeVertex(String)
   */
  @Test
  public void test018_removeVertexWithEdges() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between vertex1 and vertex2a
    testGraph.addEdge("vertex1", "vertex2");

    // removes 2nd vertex
    testGraph.removeVertex("vertex2");

    // checks that verex2 has been removed from the graph and vertex1's adjacency list
    Set<String> vertexSet = testGraph.getAllVertices(); // gets the set of all vertices
    if (vertexSet.contains("vertex2")) {
      fail("vertex2 should have been removed from the graph but is still in the graph's set of"
          + " all vertices");
    }

    List<String> adjList = testGraph.getAdjacentVerticesOf("vertex1");
    if (adjList.contains("vertex2")) {
      fail("There should bot be an edge between vertex1 and vertex2, as vertex2 was removed");
    }

    // checks that order was decreased to 1 by removing vertex2
    if (testGraph.order() != 1) {
      fail("After removing a vertex, " + "the order should be 1, but was: " + testGraph.order());
    }
  }

  /**
   * Checks that an edge is properly removed from the graph by checking that the edge no longer
   * exists and size is decreased by 1
   * 
   * @see Graph#removeEdge(String, String)
   */
  @Test
  public void test019_removeEdge() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // inserts an edge between the two
    testGraph.addEdge("vertex1", "vertex2");

    // removes an edge between the two
    testGraph.removeEdge("vertex1", "vertex2");

    // checks that vertex2 is now in the list of adjacent matrices of vertex1
    List<String> adjacencyList = testGraph.getAdjacentVerticesOf("vertex1");
    if (adjacencyList.contains("vertex2")) {
      fail("An edge was supposed to be removed from vertex1 to vertex2 but vertex2"
          + " is in vertex1's adjacency list");
    }

    // checks that size has now decreased to 0
    if (testGraph.size() != 0) {
      fail("The graph's size after removing one edge should be 0 but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an edge is not "removed" (there is no edge to remove) when the edge's vertices are
   * null
   * 
   * @see Graph#removeEdge(String, String)
   */
  @Test
  public void test020_removeEdgeNullVertices() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // removes an edge between null vertices
    testGraph.removeEdge(null, null);

    // checks that size has not decreased from 0
    if (testGraph.size() != 0) {
      fail("The graph's size after removing one edge with null vertices should be 0" + " but was: "
          + testGraph.size());
    }
  }

  /**
   * Checks that an an edge is not "removed" when the edge's start vertex is null
   * 
   * @see Graph#removeEdge(String, String)
   */
  @Test
  public void test021_removeEdge1stVertexNull() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // removes an edge between null and vertex2
    testGraph.removeEdge(null, "vertex2");

    // checks that size has not decreased from 0
    if (testGraph.size() != 0) {
      fail("The graph's size after removing one edge with a null start vertex should be 0"
          + " but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an an edge is not "removed" when the edge's end vertex is null
   * 
   * @see Graph#removeEdge(String, String)
   */
  @Test
  public void test022_removeEdge2ndVertexNull() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // removes an edge between vertex1 and null vertex
    testGraph.removeEdge("vertex1", null);

    // checks that size has not decreased from 0
    if (testGraph.size() != 0) {
      fail("The graph's size after removing one edge with a null end vertex should be 0"
          + " but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an an edge is not "removed" when the edge's start vertex is not in graph
   * 
   * @see Graph#removeEdge(String, String)
   */
  @Test
  public void test023_removeEdge1stVertexNotInGraph() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // removes an edge between vertex3 and vertex2
    testGraph.removeEdge("vertex3", "vertex2");

    // checks that size has not decreased from 0
    if (testGraph.size() != 0) {
      fail("The graph's size after removing one edge with a start vertex not in graph should be 0"
          + " but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an an edge is not "removed" when the edge's end vertex is not in graph
   * 
   * @see Graph#removeEdge(String, String)
   */
  @Test
  public void test024_removeEdge2ndVertexNotInGraph() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // removes an edge between vertex1 and null vertex
    testGraph.removeEdge("vertex1", "vertex3");

    // checks that size has not decreased from 0
    if (testGraph.size() != 0) {
      fail("The graph's size after removing one edge with an end vertex not in graph should be 0"
          + " but was: " + testGraph.size());
    }
  }

  /**
   * Checks that an an edge is not "removed" when the neither vertex in graph
   * 
   * @see Graph#removeEdge(String, String)
   */
  @Test
  public void test025_removeEdgeNeitherVertexInGraph() {
    // inserts two vertices
    testGraph.addVertex("vertex1");
    testGraph.addVertex("vertex2");

    // removes an edge between vertices not in graph
    testGraph.removeEdge("vertex3", "vertex4");

    // checks that size has not decreased from 0
    if (testGraph.size() != 0) {
      fail("The graph's size after removing one edge with vertices not in graph should be 0"
          + " but was: " + testGraph.size());
    }
  }

  /**
   * Removes 20 edges and checks that size and all edges are correctly represented
   * 
   * @see Graph#removeEdge(String, String)
   */
  @Test
  public void test026_removesManyEdges() {
    // adds 10 vertices
    for (int index = 0; index < 10; index++) {
      testGraph.addVertex("vertex" + index);
    }

    // adds edge between a vertex and its next two vertices, looping around for the last two
    // vertices
    for (int index = 0; index < 10; index++) {
      testGraph.addEdge("vertex" + index, "vertex" + ((index + 1) % 10));
      testGraph.addEdge("vertex" + index, "vertex" + ((index + 2) % 10));
    }

    // removes edge between a vertex and its next two vertices, looping around for the last two
    // vertices
    for (int index = 0; index < 10; index++) {
      testGraph.removeEdge("vertex" + index, "vertex" + ((index + 1) % 10));
      testGraph.removeEdge("vertex" + index, "vertex" + ((index + 2) % 10));
    }

    // checks that size should now be 0
    if (testGraph.size() != 0) {
      fail("Size after removing 20 edges should be 0 but was: " + testGraph.size());
    }
  }
}
