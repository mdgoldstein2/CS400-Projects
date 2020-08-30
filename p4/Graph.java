// Title: Graph
// Files: Set, List, LinkedList, HashSet, Iterator, GraphADT, VertexAndAdjList
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 4/16/19

import java.util.List;
import java.util.LinkedList;
import java.util.Iterator;
import java.util.HashSet;
import java.util.Set;

/**
 * The author's implementation of an unweighted, directed graph data structure
 * 
 * @author Michael Goldstein
 * @see GraphADT
 * @see VertexAndAdjList
 *
 */
public class Graph implements GraphADT {
  /**
   * A holder class which contains a vertex from a graph and its adjacency list
   * 
   * @author Michael Goldstein
   *
   */
  private class VertexAndAdjList {
    // instance variables are not private to allow for access without setters and getters
    private String vertex; // the vertex of this holder for a vertex and its adjacency list
    private LinkedList<String> adjList; // the adjacency of this holder for a vertex and its
                                        // adjacency list

    /**
     * Constructor for VertexAndAdjList
     * 
     * @param vertex  the given vertex to be stored in this holder class
     * @param adjList that given vertex's given adjacency list to be stored in this holder class
     */
    VertexAndAdjList(String vertex, LinkedList<String> adjList) {
      this.vertex = vertex;
      this.adjList = adjList;
    }
  }

  // array of vertexes and their adjacency lists, combined in a holder class called VertexAndAdjList
  private LinkedList<VertexAndAdjList> vertexList; // works as an adjacency list
  private int size; // the number of edges in the graph

  /*
   * Default no-argument constructor. Initializes instance variables
   */
  public Graph() {
    vertexList = new LinkedList<VertexAndAdjList>();
    size = 0;
  }

  /**
   * Adds a new vertex to the graph. If the given vertex is null or already exists, method ends
   * without adding a vertex or throwing an exception. Valid argument conditions: 1. vertex is
   * non-null 2. vertex is not already in the graph
   * 
   * @param vertex the vertex to be added to the graph
   */
  public void addVertex(String vertex) {
    // checks if given vertex is null
    if (vertex == null) {
      return; // method ends without adding vertex or throwing an exception if given vertex is null
    }

    // checks that given vertex is not in the graph already by iterating through vertexList and
    // checking if given vertex is the same a the vertex of a node in vertexList
    // uses Iterator for runtime efficiency as for loop with get() will result in 0(n^2)
    Iterator<VertexAndAdjList> vertexListIterator = vertexList.iterator();
    while (vertexListIterator.hasNext()) {
      String vertexInList = vertexListIterator.next().vertex;
      if (vertexInList.equals(vertex)) {
        return; // method ends without inserting if given vertex is already in graph
      }
    }

    // if given vertex is not null or a duplicate, is added to vertex list
    vertexList.add(new VertexAndAdjList(vertex, new LinkedList<String>()));
  }


  /**
   * Removes a vertex and all associated edges from the graph. If vertex is null or does not exist,
   * method ends without removing a vertex, edges, or throwing an exception. Valid argument
   * conditions: 1. vertex is non-null 2. vertex is not already in the graph
   * 
   * @param vertex the vertex to be removed
   */
  public void removeVertex(String vertex) {
    // checks if given vertex is null
    if (vertex == null) {
      return; // method ends without remove vertex or throwing an exception if given vertex is null
    }

    // removes vertex by removing the VertexAndAdjList which has the given vertex
    // uses Iterator for runtime efficiency as for loop with get() will result in 0(n^2)
    Iterator<VertexAndAdjList> vertexListIterator = vertexList.iterator();
    int index = 0; // keeps track of which index the iterator is on
    boolean removed = false; // checks if the vertex has existed and been removed
    while (vertexListIterator.hasNext()) {
      String vertexInList = vertexListIterator.next().vertex; // gets vertex at that point in list
      if (vertexInList.equals(vertex)) { // checks if current vertex is the one to be removed
        vertexList.remove(index); // removes the VertexAndAdjList which has the given vertex
        removed = true; // sets removed to true so next loop can remove other edges of vertex
        break; // stops the loop from running unnecessarily
      } else {
        index++; // increments index if current VertexAndAdjList does not have the given vertex
      }
    }

    // removes all other edges the vertex to be removed was associated with that were not removed by
    // the previous loop. Uses Iterator for runtime efficiency as for loop with get() will result in
    // 0(n^2) Also checks that vertex existed so loop does not run unnecessarily
    if (removed) {
      Iterator<VertexAndAdjList> vertexListIterator2 = vertexList.iterator();
      while (vertexListIterator2.hasNext()) {
        LinkedList<String> adjList = vertexListIterator2.next().adjList;
        adjList.remove(vertex);
      }
    }
  }

  /**
   * Adds the edge from vertex1 to vertex2 to this graph. (edge is directed and unweighted) If
   * either vertex does not exist, the missing vertices and then the edge are added. If the edge
   * exists in the graph, no edge is added and no exception is thrown. Valid argument conditions: 1.
   * neither vertex is null 2. the edge is not in the graph
   * 
   * @param vertex1 the vertex the edge to be inserted is going from
   * @param vertex2 the vertex the edge to be inserted is going to
   */
  public void addEdge(String vertex1, String vertex2) {
    // checks that neither given vertex is null, method ends if either is null
    if (vertex1 == null) {
      return;
    }
    if (vertex2 == null) {
      return;
    }

    // checks that vertex1 is in the graph.
    // uses Iterator for runtime efficiency as for loop with get() will result in 0(n^2)
    Iterator<VertexAndAdjList> vertexListIterator = vertexList.iterator();
    boolean vertex1Found = false; // keeps track whether or not vertex1 was found
    while (vertexListIterator.hasNext()) {
      String vertex = vertexListIterator.next().vertex;
      if (vertex.equals(vertex1)) {
        vertex1Found = true; // sets found status to true if found
        break; // ends loop early once found to not waste time
      }
    }

    // adds vertex1 if it is not found
    if (!vertex1Found) {
      addVertex(vertex1);
    }

    // checks that vertex2 is in the graph.
    // uses Iterator for runtime efficiency as for loop with get() will result in 0(n^2)
    Iterator<VertexAndAdjList> vertexListIterator2 = vertexList.iterator();
    boolean vertex2Found = false; // keeps track whether or not vertex2 was found
    while (vertexListIterator2.hasNext()) {
      String vertex = vertexListIterator2.next().vertex;
      if (vertex.equals(vertex2)) {
        vertex2Found = true; // sets found status to true if found
        break; // ends loop early once found to not waste time
      }
    }

    // adds vertex2 if it is not found
    if (!vertex2Found) {
      addVertex(vertex2);
    }

    // inserts edge starting at vertex1 going to vertex2 , also checking if vertex1 is in graph
    // by iterating over vertexList and checking if any VertexAndAdjList is the one with vertex1 as
    // its vertex and inserting vertex2, representing an edge, into its adjacency list
    // uses Iterator for runtime efficiency as for loop with get() will result in 0(n^2)
    Iterator<VertexAndAdjList> vertexListIterator3 = vertexList.iterator();
    while (vertexListIterator3.hasNext()) {
      VertexAndAdjList current = vertexListIterator3.next();
      String vertex = current.vertex; // current vertex
      LinkedList<String> adjList = current.adjList; // current adjacency list

      // checks if the current vertex is vertex1 (essentially checking if vertex 1 is in graph)
      if (vertex.equals(vertex1)) {
        if (!adjList.contains(vertex2)) {
          adjList.add(vertex2); // adds vertex2 into vertex's adjacency matrix if vertex is vertex1
        }
        size++; // increments size
        return; // ends loop once inserted
      }
    }
  }

  /**
   * Removes the edge from vertex1 to vertex2 from this graph. (edge is directed and unweighted) If
   * either vertex does not exist, or if an edge from vertex1 to vertex2 does not exist, no edge is
   * removed and no exception is thrown. Valid argument conditions: 1. neither vertex is null 2.
   * both vertices are in the graph 3. the edge from vertex1 to vertex2 is in the graph
   * 
   * @param vertex1 the vertex from which the edge to be removed starts
   * @param vertex2 the vertex at which the edge to be removed ends
   */
  public void removeEdge(String vertex1, String vertex2) {
    // checks that neither given vertex is null, method ends if either is null
    if (vertex1 == null) {
      return;
    }
    if (vertex2 == null) {
      return;
    }

    // checks that vertex2 is in the graph. vertex2 is checked first as vertex1 will be checked when
    // trying to find the correct VertexAndAdjList to insert into
    // uses Iterator for runtime efficiency as for loop with get() will result in 0(n^2)
    Iterator<VertexAndAdjList> vertexListIterator = vertexList.iterator();
    boolean vertex2Found = false; // keeps track whether or not vertex2 was found
    while (vertexListIterator.hasNext()) {
      String vertex = vertexListIterator.next().vertex;
      if (vertex.equals(vertex2)) {
        vertex2Found = true; // sets found status to true if found
        break; // ends loop early once found to not waste time
      }
    }

    if (!vertex2Found) {
      return; // method ends if vertex2 was not found
    }

    // removes edge starting at vertex1 going to vertex2 , also checking if vertex1 is in graph
    // by iterating over vertexList and checking if any VertexAndAdjList is the one with vertex1 as
    // its vertex and removing vertex2, representing an edge, from its adjacency list
    // uses Iterator for runtime efficiency as for loop with get() will result in 0(n^2)
    Iterator<VertexAndAdjList> vertexListIterator2 = vertexList.iterator();
    while (vertexListIterator2.hasNext()) {
      VertexAndAdjList current = vertexListIterator2.next();
      String vertex = current.vertex; // current vertex
      LinkedList<String> adjList = current.adjList; // current adjacency list

      // checks if the current vertex is vertex1 (essentially checking if vertex 1 is in graph)
      if (vertex.equals(vertex1)) {
        // removes vertex2 from vertex's adjacency matrix if vertex is vertex1
        adjList.remove(vertex2);
        size--; // decrements size
      }
    }
  }

  /**
   * Returns a Set that contains all the vertices
   * 
   * @return a Set of all vertices in the graph
   * 
   */
  public Set<String> getAllVertices() {
    // iterates over vertexList, adding each vertex from each VertexAndAdjList
    // uses Iterator for runtime efficiency as for loop with get() will result in 0(n^2)
    Iterator<VertexAndAdjList> vertexListIterator = vertexList.iterator();
    HashSet<String> set = new HashSet<String>(); // set to be filled will all vertexes and returned
    while (vertexListIterator.hasNext()) {
      set.add(vertexListIterator.next().vertex); // adds vertex from VertexAndAdjList
    }
    return set; // returns the set once all vertices has been added
  }


  /**
   * Returns all the neighbor (adjacent) vertices of a vertex
   * 
   * @param vertex the specified vertex whose adjacent vertices are to be returned
   * @return a List<String> of all the adjacent vertices for specified vertex
   *
   */
  public List<String> getAdjacentVerticesOf(String vertex) {
    // iterates over vertexList to find and return the adjList for the given vertex
    // uses Iterator for runtime efficiency as for loop with get() will result in 0(n^2)
    Iterator<VertexAndAdjList> vertexListIterator = vertexList.iterator();
    while (vertexListIterator.hasNext()) {
      VertexAndAdjList current = vertexListIterator.next();
      String currVertex = current.vertex; // current vertex
      LinkedList<String> adjList = current.adjList; // current adjacency list

      // checks if the current vertex is the given vertex
      if (currVertex.equals(vertex)) {
        return adjList; // returns the vertex's adjacency list
      }
    }

    return null; // returns null if the given vertex is null or not in graph
  }

  /**
   * Returns the number of edges in this graph.
   * 
   * @return the number of edges in this graph (size)
   */
  public int size() {
    return size;
  }

  /**
   * Returns the number of vertices in this graph.
   * 
   * @return the number of vertices in this graph (order)
   */
  public int order() {
    return vertexList.size();
  }
}
