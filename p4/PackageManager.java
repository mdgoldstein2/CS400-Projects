// Title: PackageManager
// Files: Graph, JSON, FileReader, Set, Stack, LinkedList, HashSet, Queue
// Course: CS400 Spring 2019
//
// Author: Michael Goldstein
// Email: mdgoldstein2@wisc.edu
// Lecturer's Name: Deb Deppeler
// Due Date: 4/16/19

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.LinkedList;
import java.util.HashSet;
import java.util.Queue;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * A class that processes JSON package dependency files and makes that dependency information
 * available to all users. It processes dependency information using a graph data structure
 * 
 * @author Michael Goldstein
 * @see Graph
 *
 */
public class PackageManager {
  private Graph graph; // the graph used to store dependency information

  /*
   * Package Manager default no-argument constructor. Initializes graph instance variable
   */
  public PackageManager() {
    graph = new Graph();
  }

  /**
   * Takes in a file path for a json file and builds the package dependency graph from it.
   * 
   * @param jsonFilepath the name of json data file with package dependency information
   * @throws FileNotFoundException if file path is incorrect
   * @throws IOException           if the give file cannot be read
   * @throws ParseException        if the given json cannot be parsed
   * 
   * @see PackageManager#constructPackages(JSONArray)
   */
  public void constructGraph(String jsonFilepath)
      throws FileNotFoundException, IOException, ParseException {
    // parses JSON file into a JSON data type for access
    Object obj = new JSONParser().parse(new FileReader(jsonFilepath));
    JSONObject dependencyInfo = (JSONObject) obj;

    // creates array of packages from a JSON array of package information
    JSONArray packageInfo = (JSONArray) dependencyInfo.get("packages");
    Package packages[] = constructPackages(packageInfo);

    // adds all packages to graph
    for (int index = 0; index < packages.length; index++) {
      graph.addVertex(packages[index].getName());
    }

    // adds all dependences to graph
    for (int index = 0; index < packages.length; index++) {
      // array of dependencies for current package
      String[] dependencies = packages[index].getDependencies();

      // adds dependencies as an edge from the initial package to the package it is dependent on
      for (int dependencyIndex = 0; dependencyIndex < dependencies.length; dependencyIndex++)
        graph.addEdge(packages[index].getName(), dependencies[dependencyIndex]);
    }
  }

  /**
   * Helper method which creates and returns an array of packages from a JSONArray of package
   * information
   * 
   * @param packageInfo the JSON array containing package information
   * @return an array of packages created from the package information
   */
  private Package[] constructPackages(JSONArray packageInfo) {
    Package packages[] = new Package[packageInfo.size()]; // regular array to insert package info

    // creates a temporary package object from JSON information and inserts it into array for each
    // package in JSON file
    for (int index = 0; index < packages.length; index++) {
      // creates temporary JSON object and array
      JSONObject tempJSONPackageObject = (JSONObject) packageInfo.get(index);
      JSONArray tempJSONDependencyArray = (JSONArray) tempJSONPackageObject.get("dependencies");

      // converts dependency information from JSON into an array of dependencies for each package
      String[] tempDependencyArray = new String[tempJSONDependencyArray.size()];
      for (int dependencyIndex =
          0; dependencyIndex < tempDependencyArray.length; dependencyIndex++) {
        tempDependencyArray[dependencyIndex] =
            (String) tempJSONDependencyArray.get(dependencyIndex);
      }

      // converts temporary dependency array and name in JSON into a temporary package
      Package tempPackage =
          new Package((String) tempJSONPackageObject.get("name"), tempDependencyArray);

      // adds temporary package object to array,
      packages[index] = tempPackage;
    }

    return packages; // returns array of packages
  }

  /**
   * Getter method to get all packages in the graph.
   * 
   * @return Set<String> of all the packages
   */
  public Set<String> getAllPackages() {
    return graph.getAllVertices(); // gets all vertices from graph, which are the packages
  }

  /**
   * Uses DFS search to see if there is a cycle which the given package is a part of
   * 
   * @param pkg the given package the method checks is in a cycle
   * @return true if the given package is in a cycle, false if not
   */
  private boolean hasCycle(String pkg) {
    Stack<String> DFSStack = new Stack<String>(); // stack for DFS search
    Set<String> visitedPackages = new HashSet<String>(); // tracks visited packages

    // adds given package to stack and marks it as visited in order to start search
    DFSStack.push(pkg);
    visitedPackages.add(pkg);

    // loop which runs DFS as long as stack is not empty
    while (!DFSStack.isEmpty()) {
      boolean hasUnvisitedDependencies = false;
      String currentPackage = DFSStack.peek(); // "current" package in search
      List<String> dependencies = graph.getAdjacentVerticesOf(currentPackage); // all dependencies

      // indexes through all dependencies to check if they point pack to start, adds first
      // unvisited dependency to stack
      for (int index = 0; index < dependencies.size(); index++) {
        // checks if there is a cycle at this point in search (current package has the initial
        // package as a dependency)
        if (dependencies.get(index).equals(pkg)) {
          return true;
        }

        // adds first unvisited dependency to stack and marks it as visited
        if (!visitedPackages.contains(dependencies.get(index)) && !hasUnvisitedDependencies) {
          hasUnvisitedDependencies = true;
          visitedPackages.add(dependencies.get(index));
          DFSStack.push(dependencies.get(index));
        }
      }

      // pops current package from stack if it does not have unvisited dependencies
      if (!hasUnvisitedDependencies) {
        DFSStack.pop();
      }
    }
    return false; // returns false if cycle is never found
  }

  /**
   * Given a package name, returns a list of packages in a valid installation order.
   * 
   * Valid installation order means that each package is listed before any packages that depend upon
   * that package.
   * 
   * @return List<String>, order in which the packages have to be installed
   * 
   * @throws CycleException           if you encounter a cycle in the graph while finding the
   *                                  installation order for a particular package. Tip: Cycles in
   *                                  some other part of the graph that do not affect the
   *                                  installation order for the specified package, should not throw
   *                                  this exception.
   * 
   * @throws PackageNotFoundException if the package passed does not exist in the dependency graph.
   * @see PackageManager#hasCycle(String)
   */
  public List<String> getInstallationOrder(String pkg)
      throws CycleException, PackageNotFoundException {
    // checks if the given package is in graph
    if (!getAllPackages().contains(pkg)) {
      throw new PackageNotFoundException();
    }

    // queue used to perform depth first search, which is used to create installation order
    Queue<String> BFSQueue = new LinkedList<String>();
    Set<String> visitedPackages = new HashSet<String>(); // tracks visited packages
    List<String> installationOrder = new LinkedList<String>(); // tracks installation order

    BFSQueue.add(pkg); // adds package to queue to start BFS loop
    visitedPackages.add(pkg); // marks initial package as visited
    installationOrder.add(pkg); // adds initial package as last package in installation order

    // creates an installation order based on how vertices are visited in BFS
    while (!BFSQueue.isEmpty()) {
      String tempPackage = BFSQueue.remove(); // "current" package in search
      List<String> dependencies = graph.getAdjacentVerticesOf(tempPackage); // gets dependencies
      LinkedList<String> addedDependencies = new LinkedList<String>(); // dependencies to be added

      // adds and marks as visited all unvisited dependencies
      for (int index = 0; index < dependencies.size(); index++) {
        // checks if dependency has been visited. If not, marks it as visited an adds it to queue
        // and list of dependencies to be added.
        if (!visitedPackages.contains(dependencies.get(index))) {
          visitedPackages.add(dependencies.get(index));
          BFSQueue.add(dependencies.get(index));
          addedDependencies.add(dependencies.get(index));
        } else if (installationOrder.indexOf(tempPackage) < installationOrder
            .indexOf(dependencies.get(index))) {
          // if dependency has already been added and comes before the current package in the
          // installation order, switches order to make sure a package is not installed before one
          // of its dependencies
          installationOrder.remove(tempPackage);
          installationOrder.add(installationOrder.indexOf(dependencies.get(index)) + 1,
              tempPackage);
        }
      }
      installationOrder.addAll(0, addedDependencies); // adds dependencies to start of install order
    }

    // checks that there are no cycles for the given installation order
    for (int index = 0; index < installationOrder.size(); index++) {
      if (hasCycle(installationOrder.get(index))) {
        throw new CycleException();
      }
    }

    // returns the installation order
    return installationOrder;
  }

  /**
   * Given two packages - one to be installed and the other installed, return a List of the packages
   * that need to be newly installed.
   * 
   * For example, refer to shared_dependecies.json - toInstall("A","B") If package A needs to be
   * installed and packageB is already installed, return the list ["A", "C"] since D will have been
   * installed when B was previously installed.
   * 
   * @return List<String>, packages that need to be newly installed.
   * 
   * @throws CycleException           if you encounter a cycle in the graph while finding the
   *                                  dependencies of the given packages. If there is a cycle in
   *                                  some other part of the graph that doesn't affect the parsing
   *                                  of these dependencies, cycle exception should not be thrown.
   * 
   * @throws PackageNotFoundException if any of the packages passed do not exist in the dependency
   *                                  graph.
   * @see PackageManager#getInstallationOrder(String)
   */
  public List<String> toInstall(String newPkg, String installedPkg)
      throws CycleException, PackageNotFoundException {
    // gets installation orders for new and already installed packages
    List<String> installOrderNew = getInstallationOrder(newPkg);
    List<String> installOrderInstalled = getInstallationOrder(installedPkg);

    // creates installation order for new package by "removing" all packages installed when
    // installing the already installed package from the list of packages needed to be installed
    LinkedList<String> toInstall = new LinkedList<String>();
    for (int index = 0; index < installOrderNew.size(); index++) {
      if (!installOrderInstalled.contains(installOrderNew.get(index))
          && !installOrderNew.get(index).equals(installedPkg)) {
        // adds package to list if is is not already installed
        toInstall.add(installOrderNew.get(index));
      }
    }

    // returns list of all packages that need to be installed that have not already been installed
    return toInstall;
  }

  /**
   * Return a valid global installation order of all the packages in the dependency graph.
   * 
   * assumes: no package has been installed and you are required to install all the packages
   * 
   * returns a valid installation order that will not violate any dependencies
   * 
   * @return List<String>, order in which all the packages have to be installed
   * @throws CycleException if you encounter a cycle in the graph
   */
  public List<String> getInstallationOrderForAllPackages() throws CycleException {
    Object[] allPackages = getAllPackages().toArray(); // list of all packages

    // checks that there are no cycles in graph
    for (int index = 0; index < allPackages.length; index++) {
      if (hasCycle((String) allPackages[index])) {
        throw new CycleException();
      }
    }

    Stack<String> orderingStack = new Stack<String>(); // stack to be used for topological order
    Set<String> isADependency = new HashSet<String>(); // tracks all packages that are dependencies

    // finds all packages that are dependencies
    for (int index = 0; index < allPackages.length; index++) {
      List<String> dependencies = graph.getAdjacentVerticesOf((String) allPackages[index]);
      isADependency.addAll(dependencies);
    }

    // finds all packages that are not dependencies of any other package (to find topological order)
    for (int index = 0; index < allPackages.length; index++) {
      if (!isADependency.contains((String) allPackages[index])) {
        orderingStack.push((String) allPackages[index]);
      }
    }

    // runs topological order loop (which will find the reverse of the installation)
    Queue<String> topologicalOrderQueue = new LinkedList<String>(); // tracks topological order
    Set<String> visitedPackages = new HashSet<String>();
    while (!orderingStack.isEmpty()) {
      boolean hasUnvisitedDependencies = false; // tracks if current package has dependencies
      String currentPackage = orderingStack.peek(); // "current" package in search
      List<String> dependencies = graph.getAdjacentVerticesOf(currentPackage); // all dependencies

      // adds first unvisited dependency to stack, marks it as visited
      for (int index = 0; index < dependencies.size(); index++) {
        // adds first unvisited dependency to stack and marks it as visited
        if (!visitedPackages.contains(dependencies.get(index)) && !hasUnvisitedDependencies) {
          hasUnvisitedDependencies = true;
          visitedPackages.add(dependencies.get(index));
          orderingStack.push(dependencies.get(index));
        }
      }

      // removes package from stack, adds to topological order if it has no unvisited dependencies
      if (!hasUnvisitedDependencies) {
        topologicalOrderQueue.add(orderingStack.pop());
      }
    }

    // creates installation order list (which is the topological order queue in reverse)
    LinkedList<String> installationOrder = new LinkedList<String>();
    while (!topologicalOrderQueue.isEmpty()) {
      installationOrder.add(topologicalOrderQueue.remove());
    }

    // returns installation order
    return installationOrder;
  }

  /**
   * Find and return the name of the package with the maximum number of dependencies.
   * 
   * Tip: it's not just the number of dependencies given in the json file. The number of
   * dependencies includes the dependencies of its dependencies. But, if a package is listed in
   * multiple places, it is only counted once.
   * 
   * Example: if A depends on B and C, and B depends on C, and C depends on D. Then, A has 3
   * dependencies - B,C and D.
   * 
   * @return String, name of the package with most dependencies.
   * @throws CycleException if you encounter a cycle in the graph
   */
  public String getPackageWithMaxDependencies() throws CycleException {
    Object[] allPackages = getAllPackages().toArray(); // list of all packages
    int maxDependencies; // tracks maximum number of dependencies
    String maxPackage = ""; // tracks package with maximum number of dependencies

    try {
      // starts with baseline of the "first" vertex and its number of dependencies
      maxDependencies = getInstallationOrder((String) allPackages[0]).size();
      maxPackage = (String) allPackages[0];

      // checks if any other packages have a higher number of dependencies, updates the maximum
      // number of dependencies and the vertex with the maximum number of dependences if any other
      // packages do have a higher number of dependencies
      for (int index = 1; index < allPackages.length; index++) {
        int numberOfDependencies = getInstallationOrder((String) allPackages[index]).size();
        if (numberOfDependencies > maxDependencies) {
          maxDependencies = numberOfDependencies;
          maxPackage = (String) allPackages[index];
        }
      }
    } catch (PackageNotFoundException e) {
      // it is impossible for this to be thrown as all packages are in graph
    }

    // returns the package with the maximum number of dependencies
    return maxPackage;
  }


  /**
   * Main driver method for PackageManager. Unused in this case as the author has a separate tester
   * class
   * 
   * @param args command line input from user
   */
  public static void main(String[] args) {
    try {
      PackageManager testManager = new PackageManager();
      testManager.constructGraph("confusing.json");
      System.out.println(testManager.getInstallationOrder("G"));
    } catch (Exception e) {
      System.out.println(e.getClass());
    }
  }
}
