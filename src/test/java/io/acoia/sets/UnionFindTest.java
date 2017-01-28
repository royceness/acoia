package io.acoia.sets;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import io.acoia.sets.UnionFind;

public class UnionFindTest {
  
  @Test
  public void testUnionFindNoUnions() {
    UnionFind<Integer> uf = new UnionFind<>();
    
    for (int i = -100; i < 100; i++) {
      uf.addMember(i);
    }
    
    for (int i = -99; i < 100; i++) {
      assertEquals((int) i, (int) uf.findRoot(i));
      assertTrue(uf.sameSet(i, i));
      assertFalse(uf.sameSet(i, i-1));
    }
  }
  
  @Test 
  public void testUnionFindNulls() {
    UnionFind<Integer> uf = new UnionFind<>();
    uf.addMember(1);
    uf.addMember(null);
    assertNull(uf.findRoot(null));
    uf.join(null, 1);
    uf.sameSet(null, 1);
  }

  @Test
  public void testUnionFindAllSame() {
    UnionFind<Integer> uf = new UnionFind<>();

    int size = 0;
    for (int i = -100; i < 100; i++) {
      assertEquals(size++, uf.size());
      uf.addMember(i);
      assertEquals(size, uf.size());
    }
    
    for (int i = -99; i < 100; i++) {
      uf.join(i-1, i);
      assertTrue(uf.sameSet(-100, i));
      if (i < 99)
        assertFalse(uf.sameSet(-100, i+1));
    }
    
    for (int i = -100; i < 100; i++) {
      assertTrue(uf.sameSet(0, i));
    }
  }
  
  @Test
  public void testPathCompressionBranch() {
    UnionFind<Integer> uf = new UnionFind<>(Arrays.asList(1, 2, 3, 4, 5, 6));
    uf.join(1, 2);
    uf.join(3, 4);
    uf.join(5, 6);
    uf.join(2, 3);
    uf.join(4, 5);
    assertEquals((Integer) 1, uf.findRoot(2));
    assertEquals((Integer) 1, uf.findRoot(5));
    
  }
  
  @Test
  public void testPathMembersTest() {
    UnionFind<Integer> uf = new UnionFind<>(Arrays.asList(1, 2, 3, 4, 5, 6));
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 5 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 6 } );
    
    assertEquals(6, uf.size());
    
    uf.join(1, 6);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 5 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6 } );

    uf.join(6, 6);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 5 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6 } );

    uf.join(6, 5);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6, 5 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 1, 6, 5 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6, 5 } );

    uf.join(4, 6);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );

    uf.join(3, 2);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 3, 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3, 2 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    
    uf.join(3, 6);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4, 3, 2 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4, 3, 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4, 3, 2 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4, 3, 2 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4, 3, 2 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4, 3, 2 } );
    
    

  }
  
  @Test
  public void testUnionFindNonexistantElement() {
    UnionFind<Integer> uf = new UnionFind<>();
    uf.addMember(1);
    try {
      uf.sameSet(1, 2);
      fail("Expected an IllegalArgumentException as 2 is not in the UnionFind");
    }
    catch (IllegalArgumentException e) {
      // Expected
    }
    try {
      uf.findRoot(2);
      fail("Expected an IllegalArgumentException as 2 is not in the UnionFind");
    }
    catch (IllegalArgumentException e) {
      // Expected
    }

  }
  
  @Test
  public void testUnionFindContains() {
    UnionFind<Integer> uf = new UnionFind<>(2);
    uf.addMember(1);
    assertFalse(uf.contains(2));
    uf.addMember(2);
    assertTrue(uf.contains(2));
    uf.join(1, 2);
    assertTrue(uf.contains(1));
    assertTrue(uf.contains(2));
  }
  
  @Test
  public void testUnionFindSets() {
    UnionFind<Integer> uf = new UnionFind<>(2);
    uf.addMember(1);
    Collection<Set<Integer>> sets = uf.sets();
    assertEquals(1, sets.size());
    assertTrue(sets.contains(new HashSet<Integer>(Collections.singleton(1))));
    
    uf.addMember(2);
    sets = uf.sets();
    assertEquals(2, sets.size());
    assertTrue(sets.contains(new HashSet<Integer>(Collections.singleton(1))));
    assertTrue(sets.contains(new HashSet<Integer>(Collections.singleton(2))));
    
    uf.join(1, 2);
    sets = uf.sets();
    assertEquals(1, sets.size());
    assertTrue(sets.contains(new HashSet<Integer>(Arrays.asList(1, 2))));

  }


}
