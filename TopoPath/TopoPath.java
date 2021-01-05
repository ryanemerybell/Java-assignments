// Ryan Bell
// COP 3503, Summer 2018

import java.io.*;
import java.util.*;
import java.lang.*;

public class TopoPath
{
  public static boolean hasTopoPath(String filename) throws IOException
  {
    Scanner in = new Scanner(new File(filename));
		int N = in.nextInt(), k, i;
    ArrayList<LinkedList<Integer>> adjList;

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

    // If this graph has no cycles and has a Hamiltonian path, then
    // a hamiltonian path in this graph must also be a TopoPath
    // Note that hasHamPathIsh already assumes that the graph has no cycles,
    // As it would never be called (by short circuiting) if there were any cycles.
    return( !hasCycle(adjList) && hasHamPathIsh(adjList) );
  }


  public static double difficultyRating()
  {
    return 3.0;
  }


  public static double hoursSpent()
  {
    return 10.0;
  }

  // -------------------------------------------
  // Helper Functions:


  // Wrapper method for recursive hasCycleRec
  public static boolean hasCycle(ArrayList<LinkedList<Integer>> adjList)
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
        if(hasCycleRec(adjList, unvisited, visiting, visited, i))
          return true;
    }

    // If no cycles have been found thus far, then the graph has no cycles
    return false;
  }

  // Determines whether this graph has a cycle or not using DFS starting at node
  public static boolean hasCycleRec(ArrayList<LinkedList<Integer>> adjList, HashSet<Integer> unv,
                                    HashSet<Integer> ving, HashSet<Integer> ved, int node)
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
      else if(hasCycleRec(adjList, unv, ving, ved, i))
        return true;
    }

    // Switch this node from visiting to visited
    // This node has no more neighbors to explore
    ving.remove(node);
    ved.add(node);

    return false;
  }

  public static boolean hasHamPathIsh(ArrayList<LinkedList<Integer>> adjList)
  {
    int N = adjList.size() - 1, count = 0, current = -1, i;
    int [] incoming = new int[N + 1];

    // Keep track of the in-degrees of the vertices:
    // incoming[x] == in-degree of x
    for(i = 1; i <= N; i++)
      for(int j : adjList.get(i))
        incoming[j]++;

    // This inner for loop is ran once before the main for loop
    // in order to find the starting vertex of the hamPath
    for(i = 1; i <= N; i++)
      if(incoming[i] == 0)
      {
        current = i;
        count++;

        // A DAG cannot have a hamiltonian cycle if more
        // than one vertex has an in-degree of 0
        if(count >= 2)
          return false;
      }

    count = 0;

    // "Delete" the visited vertex of in-degree 0
    for(int k : adjList.get(current))
      incoming[k]--;

    for(i = 1; i <= N-1; i++)
    {
      // As this graph is acyclic at this point,
      // we must have exactly one vertex with in-degree 0
      // Else, the graph has no hamiltonian path
      for(int l : adjList.get(current))
        if(incoming[l] == 0)
        {
          current = l;
          count++;

          // A DAG cannot have a hamiltonian cycle if more
          // than one vertex has an in-degree of 0
          if(count >= 2)
            return false;
        }

      count = 0;

      // "Delete" the visited vertex of in-degree 0
      for(int m : adjList.get(current))
        incoming[m]--;
    }

    return true;
  }
}
