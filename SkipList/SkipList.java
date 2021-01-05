// Ryan Bell
// COP 3503, Summer 2018

import java.io.*;
import java.util.*;
import java.lang.*;


class Node<T extends Comparable<T>>
{
  ArrayList<Node<T>> next;
  T data;

  // Constructors:
  Node(int height)
  {
    next = new ArrayList<Node<T>>();

    for(int i = 0; i < height; i++)
      next.add(null);
  }

  Node(T data, int height)
  {
    this.data = data;
    next = new ArrayList<Node<T>>();

    for(int i = 0; i < height; i++)
      next.add(null);
  }

  // ---------------------------------------------------------------------------

  // Required methods:
  public T value()
  {
    return data;
  }

  public int height()
  {
    return next.size();
  }

  public Node<T> next(int level)
  {
    if(level < 0 || level >= next.size())
      return null;

    return next.get(level);
  }

  // ---------------------------------------------------------------------------

  // Helper methods:
  public void setNext(int level, Node<T> node)
  {
    next.set(level, node);
  }

  public void grow()
  {
    next.add(null);
  }

  public void maybeGrow()
  {
    int n = (int) (Math.random() * 2);
    if(n == 1)
      next.add(null);
  }

  public void trim(int height)
  {
    while(next.size() > height)
      next.remove(next.size() - 1);
  }
}


// -----------------------------------------------------------------------------
// -----------------------------------------------------------------------------
// -----------------------------------------------------------------------------


public class SkipList<T extends Comparable<T>>
{
  Node<T> head;
  int n; // Number of nodes in list excluding "head".

  // Constructors:
  SkipList()
  {
    this(1);
  }

  SkipList(int height)
  {
    if(height < 1)
      height = 1;

    head = new Node<T>(height);
    n = 0;
  }

  // ---------------------------------------------------------------------------

  // Required methods:
  public int size()
  {
    return n;
  }

  public int height()
  {
    return head.height(); // Height of list is synonymous with height of head.
  }

  public Node<T> head()
  {
    return head;
  }

  public void insert(T data)
  {
    int logHeight = getMaxHeight(this.size()), listHeight = this.height();
    int randomHeight = generateRandomHeight(Math.max(listHeight, logHeight));

    insert(data, randomHeight);
  }

  public void insert(T data, int height)
  {
    // Woah! That's a lot of variables! (Each one is important).
    int logHeight, listHeight = this.height(), i;
    Node<T> nude = new Node<T>(data, height), current = head(), next;
    ArrayList<Node<T>> breadCrumbs = new ArrayList<Node<T>>();

    // Do some search stuff; leave some breadcrumbs.
    for(i = listHeight - 1; i >= 0; i--)
    {
      next = current.next(i);

      // Drop down; leave a breadcrumb.
      if(next == null)
        breadCrumbs.add(current);
      else if(next.value().compareTo(data) >= 0)
        breadCrumbs.add(current);

      // Move forward.
      else if(next.value().compareTo(data) < 0)
      {
        current = next;
        i++;
      }
    }

    // Send nude (insert new node, LOL).
    Collections.reverse(breadCrumbs);
    for(i = nude.height() - 1; i >= 0; i--)
    {
        nude.setNext(i, breadCrumbs.get(i).next(i));
        breadCrumbs.get(i).setNext(i, nude);
    }

    // Update logHeight b/c one more node is in the SkipList.
    n++;
    logHeight = getMaxHeight(size());

    // Grow the SkipList if necessary.
    if(logHeight > listHeight)
      growSkipList();
  }

  public void delete(T data)
  {
    // Don't delete anything if data isn't even in the SkipList!
    if(!contains(data))
      return;

    // Woah! That's a lot of variables! (Each one is important).
    int logHeight = getMaxHeight(size()), listHeight = height(), i;
    ArrayList<Node<T>> breadCrumbs = new ArrayList<Node<T>>();
    Node<T> current = head(), next, ntbd = null; // "Node to be deleted".

    for(i = listHeight - 1; i >= 0; i--)
    {
      next = current.next(i);

      // Drop down; no need to leave a breadcrumb.
      if(next == null)
        continue;
      else if(next.value().compareTo(data) > 0)
        continue;

      // Move forward.
      else if(next.value().compareTo(data) < 0)
      {
        current = next;
        i++;
      }

      // Drop down; leave a breadcrumb.
      else
      {
        ntbd = next;
        breadCrumbs.add(current);
      }
    }

    // Delete, and re-wire.
    Collections.reverse(breadCrumbs);
    for(i = ntbd.height() - 1; i >= 0; i--)
      breadCrumbs.get(i).setNext(i, ntbd.next(i));

    // Update logHeight b/c one less node is in the SkipList.
    n--;
    logHeight = getMaxHeight(size());

    // Shrink SkipList if necessary.
    if(logHeight < listHeight)
      trimSkipList();
  }

  public boolean contains(T data)
  {
    Node<T> current = head(), next;

    for(int i = height() - 1; i >= 0; i--)
    {
      next = current.next(i);

      if(next == null)
        continue;
      else if(next.value().compareTo(data) > 0)
        continue;
      else if(next.value().compareTo(data) < 0)
      {
        current = next;
        i++;
      }
      else
        return true;
    }

    return false;
  }

  public Node<T> get(T data)
  {
    Node<T> current = head(), next;

    for(int i = height() - 1; i >= 0; i--)
    {
      next = current.next(i);

      if(next == null)
        continue;
      else if(next.value().compareTo(data) > 0)
        continue;
      else if(next.value().compareTo(data) < 0)
      {
        current = next;
        i++;
      }
      else
        return next;
    }

    return null;
  }

  public static double difficultyRating()
  {
    return 4.0;
  }

  public static double hoursSpent()
  {
    return 30.0;
  }

  // ---------------------------------------------------------------------------

  // Helper methods:
  private static int getMaxHeight(int n)
  {
    if(n == 0 || n == 1)
      return 1; // Defaults a 0 node list to a height of 1.
    else
      return (int) (Math.ceil(Math.log(n) / Math.log(2)));
  }

  private static int generateRandomHeight(int maxHeight)
  {
    for(int i = 1; i < maxHeight; i++)
    {
      // Virtual coin toss:
      if((int) (Math.random() * 2) == 1)
        return i;
    }

    return maxHeight;
  }

  private void growSkipList()
  {
    int oldHeight = height();
    Node<T> current = head(), nxt = current.next(oldHeight - 1);

    // Raise head height by 1, thus growing the SkipList height by 1.
    current.grow();

    while(nxt != null)
    {
      nxt.maybeGrow();

      // If it grew, add a wire.
      if(nxt.height() == oldHeight + 1)
      {
        current.setNext(oldHeight, nxt);
        current = nxt;
      }

      nxt = nxt.next(oldHeight - 1);
    }
  }

  private void trimSkipList()
  {
    int newHeight = getMaxHeight(size()), oldHeight = height();

    // Defensive coding; just being safe.
    if(newHeight >= oldHeight)
      return;

    Node<T> current = head(), nxt;

    // This is a nice, little, clever traversal.
    while(current != null)
    {
      nxt = current.next(newHeight);
      current.trim(newHeight);
      current = nxt;
    }
  }
}
