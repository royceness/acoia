package io.acoia.sets;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
 * @param <T> the type of elements in this set.  Note that internally this implementation makes use of a java.util.HashMap<T>
 * with T as the key, so make sure T has worthy hashCode() and equals() implementation.
 * 
 * 
 * Copyright (c) 2017 Royce Ausburn
 * This code is available for use in accordance with the MIT License https://opensource.org/licenses/MIT   
 * @author Royce Ausburn (esapersona@royce.id.au)
 */
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
  
  public UnionFind() {
    entries = new HashMap<>();
  }
  
  public UnionFind(Collection<T> members) {
    entries = new HashMap<>(members.size());
    for (T m: members) {
      entries.put(m, new UnionFindEntry(m));
    }
  }
  
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
  
  public T find(T e) {
    return findEntry(e).entry;
  }

  private UnionFindEntry getEntry(T e) {
    UnionFindEntry entry = entries.get(e);
    if (entry == null)
      throw new IllegalArgumentException("Argument " + e + " is not in this union find");
    return entry;
  }
  
  public void union(T e1, T e2) {
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
  
  public Set<T> members(T e) {
    UnionFindEntry entry = findEntry(e);
    Set<T> result = new HashSet<>(entry.size);
    while (entry != null) {
      result.add(entry.entry);
      entry = entry.next;
    }
    return result;
  }
  
  public int size() {
    return entries.size();
  }

  public boolean equivalent(T e1, T e2) {
    return find(e1) == find(e2);
  }
}
