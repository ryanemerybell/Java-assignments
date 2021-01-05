// Ryan Bell
// COP 3503, Summer 2018

import java.io.*;
import java.util.*;
import java.lang.*;

public class ConstrainedTopoSort
{
  ArrayList<LinkedList<Integer>> adjList;

  // Used to instantiate and initialize the adjaceny list
  public ConstrainedTopoSort(String filename) throws IOException
  {
    Scanner in = new Scanner(new File(filename));
		int N = in.nextInt(), k, i;

    // N+1 is used here to allow vertex i to be stored in adjList.get(i)
    // adjList.get(0) will just be ignored for simplicity
    // note that the number of vertices is adjList.size() - 1
    adjList = new ArrayList<LinkedList<Integer>>(N+1);

    // After instantiating the ArrayList, instantiate each LinkedList
    for(i = 0; i <= N; i++)
      adjList.add(i, new LinkedList<Integer>());

    for (i = 1; i <= N; i++)
    {
      // k represents the amount of out-neighbors to node i
      k = in.nextInt();

      for(int j = 0; j < k; j++)
        adjList.get(i).add(in.nextInt());
    }
  }


  public boolean hasConstrainedTopoSort(int x, int y)
  {
    // Just incase. There's never a sort in which a vertex precedes itself
    if(x == y)
      return false;

    // Used to keep track of visited nodes in hasPath
    boolean [] array = new boolean[adjList.size()];

    // If this graph has no cycles and has no path from y to x,
    // then there must exist a topological sort of this graph
    // in which x preceeds y, else there is no such sort
    return( !hasCycle() && !hasPath(y, x, array) );
  }


  public static double difficultyRating()
  {
    return 3.0;
  }


  public static double hoursSpent()
  {
    return 15.0;
  }

  // -------------------------------------------
  // Helper Functions:


  // Wrapper method for recursive hasCycleRec
  public boolean hasCycle()
  {
    int N = adjList.size() - 1, i;

    // 5 * N to reduce collisions
    HashSet<Integer> unvisited = new HashSet<Integer>(5 * N);
    HashSet<Integer> visiting  = new HashSet<Integer>(5 * N);
    HashSet<Integer> visited  = new HashSet<Integer>(5 * N);

    // Mark all nodes as unvisited
    for(i = 1; i <= N; i++)
      unvisited.add(i);

    // DFS until it's necessary to start another DFS at a different node
    for(i = 1; i <= N; i++)
    {
      // Only DFS at unvisited nodes
      if(unvisited.contains(i))
        if(hasCycleRec(unvisited, visiting, visited, i))
          return true;
    }

    // If no cycles have been found thus far, then the graph has no cycles
    return false;
  }

  // Determines whether this graph has a cycle or not using DFS starting at node
  public boolean hasCycleRec(HashSet<Integer> unv, HashSet<Integer> ving, HashSet<Integer> ved, int node)
  {
    // Switch this node from unvisited to visiting
    // Note that only unvisited nodes are passed to this function
    unv.remove(node);
    ving.add(node);

    // Loop through each of node's out-neighbors
    for(int i : adjList.get(node))
    {
      // Cycle found
      if(ving.contains(i))
        return true;

      // Ignore if visited
      else if(ved.contains(i))
        continue;

      // Perform recursive descent
      else if(hasCycleRec(unv, ving, ved, i))
        return true;
    }

    // Switch this node from visiting to visited
    // This node has no more neighbors to explore
    ving.remove(node);
    ved.add(node);

    return false;

  }

  // Determines whether there is a path from x to y in this graph
  public boolean hasPath(int x, int y, boolean [] array)
  {
    // Base Cases:

    // If we arrive at y through recursive descent,
    // then a path has been found from x to y
    if(x == y)
      return true;

    // Don't check the same node more than once
    if(array[x])
      return false;
    else
      array[x] = true;


    // If there's a path from one of x's out-neighbors to y,
    // then it follows that there's a path from x to y
    for(int i : adjList.get(x))
      if(hasPath(i, y, array))
        return true;

    return false;
  }
}
