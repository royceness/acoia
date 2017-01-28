package io.acoia.sets;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.annotations.Beta;

/**
 * Union find / disjoint-set data structure implementation. 
 * 
 * From wiki (https://en.wikipedia.org/wiki/Disjoint-set_data_structure):
 * 
 * In computer science, a disjoint-set data structure, also called a union–find data structure or
 * merge–find set, is a data structure that keeps track of a set of elements partitioned into a
 * number of disjoint (nonoverlapping) subsets. It supports two useful operations:
 * 
 * Find: Determine which subset a particular element is in. Find typically returns an item from this
 * set that serves as its "representative"; by comparing the result of two Find operations, one can
 * determine whether two elements are in the same subset. 
 * 
 * Union: Join two subsets into a single subset. The other important operation, MakeSet, which makes 
 * a set containing only a given element (a singleton), is generally trivial. With these three operations, 
 * many practical partitioning problems can be solved.
 * 
 * In order to define these operations more precisely, some way of representing the sets is needed.
 * One common approach is to select a fixed element of each set, called its representative, to
 * represent the set as a whole. Then, Find(x) returns the representative of the set that x belongs
 * to, and Union takes two set representatives as its arguments.
 * 
 * This implementation supports essentially constant time find() and union() lookups thanks to 
 * path compression & union by rank.
 * 
 * This UnionFind implementation can accept null as a member and is not threadsafe.
 *  
 * @param <T> the type of elements in this set.  Note that internally this implementation makes use of a java.util.HashMap<T>
 * with T as the key, so make sure T has worthy hashCode() and equals() implementation.
 * 
 * 
 * Copyright (c) 2017 Royce Ausburn
 * This code is available for use in accordance with the MIT License https://opensource.org/licenses/MIT   
 * @author Royce Ausburn (esapersona@royce.id.au)
 */
@Beta
public class UnionFind<T> {
  private class UnionFindEntry {
    UnionFindEntry root = this;
    UnionFindEntry tail = this;
    T entry;
    int size = 1;
    UnionFindEntry next = null;
    
    public UnionFindEntry(T member) {
      this.entry = member;
    }
  }
  
  private final Map<T, UnionFindEntry> entries;
  
  /**
   * Initialises an empty UnionFind.
   */
  public UnionFind() {
    entries = new HashMap<>();
  }
  
  /**
   * Initialises an empty UnionFind with the expected maximum member count of n.
   */
  public UnionFind(int n) {
    entries = new HashMap<>(n);
  }
  
  /**
   * Initialises this UnionFind with the given members.  The members are initially
   * disjoint, that is to say that each of the members is in their own set.
   */
  public UnionFind(Collection<T> members) {
    entries = new HashMap<>(members.size());
    for (T m: members) {
      entries.put(m, new UnionFindEntry(m));
    }
  }
  
  /**
   * Adds the given member to this UnionFind.  The member is initially a disjoint from all other
   * members in this UnionFind.
   * 
   * If the member is already in this UnionFind then this method has no effect. 
   */
  public void addMember(T m) {
    entries.computeIfAbsent(m, k -> new UnionFindEntry(k));
  }
  
  private UnionFindEntry findEntry(T e) {
    UnionFindEntry entry = getEntry(e);

    return findParent(entry);
  }

  private UnionFindEntry findParent(UnionFindEntry entry) {
    UnionFindEntry rep = entry.root;
    if (rep.root != rep) {
      rep = findParent(rep);
      entry.root = rep;
    }
    
    return rep;
  }
  
  /** 
   * Returns the "representative" or "root" member for the given member, which might be itself.
   * Note that the root is merely one of the members of the set - how it is selected is not defined.
   * The "root" for a member may change as a result of a call to join().
   */
  public T findRoot(T e) {
    return findEntry(e).entry;
  }

  private UnionFindEntry getEntry(T e) {
    UnionFindEntry entry = entries.get(e);
    if (entry == null)
      throw new IllegalArgumentException("Argument " + e + " is not in this union find");
    return entry;
  }
  
  /**
   * Joins the members e1 and e2, causing them to be equivalent, or in the same set.  Subsequent calls
   * to find() or members() for e1 and e2 will return the same result.
   * 
   *  If e1 and e2 are already equivalent then this method does nothing.
   */
  public void join(T e1, T e2) {
    UnionFindEntry entry1 = findEntry(e1);
    UnionFindEntry entry2 = findEntry(e2);
    
    if (entry1 == entry2)
      return;  // Already in the same set 
    
    if (entry1.size < entry2.size) {
      UnionFindEntry temp = entry1;
      entry1 = entry2;
      entry2 = temp;
    }
    
    entry1.tail.next = entry2;
    entry1.tail = entry2.tail;
    entry1.size += entry2.size;
    entry2.root = entry1;
  }
  
  /**
   * Returns the Set of members that the given member belongs to.  
   * 
   * If e is not a member of this UnionFind then an IllegalArgumentException is thrown.
   */
  public Set<T> members(T e) {
    UnionFindEntry entry = findEntry(e);
    return getMembers(entry);
  }

  private Set<T> getMembers(UnionFindEntry entry) {
    Set<T> result = new HashSet<>(entry.size);
    while (entry != null) {
      result.add(entry.entry);
      entry = entry.next;
    }
    return result;
  }
  
  /**
   * Returns the number of elements in this UnionFind.
   */
  public int size() {
    return entries.size();
  }

  /**
   * Returns true if e1 and e2 belong to the same set.  If e1 or e2 are not members of this
   * UnionFind then an IllegalArgumentException is thrown. 
   */
  public boolean sameSet(T e1, T e2) {
    return findRoot(e1) == findRoot(e2);
  }
  
  /**
   * Returns true if e is a member of this UnionFind. 
   */
  public boolean contains(T e) {
    return entries.containsKey(e);
  }
  
  /**
   * Returns all the sets within this UnionFind.
   */
  public Collection<Set<T>> sets() {
    Set<Set<T>> result = new HashSet<>(size());
    for (UnionFindEntry e: entries.values()) {
      if (e.root == e) {
        result.add(getMembers(e));
      }
    }
    return result;
  }
}
