package io.acoia.sets;

import static org.junit.Assert.*;

import java.util.Arrays;

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
      assertEquals((int) i, (int) uf.find(i));
      assertTrue(uf.equivalent(i, i));
      assertFalse(uf.equivalent(i, i-1));
    }
  }
  
  @Test 
  public void testUnionFindNulls() {
    UnionFind<Integer> uf = new UnionFind<>();
    uf.addMember(1);
    uf.addMember(null);
    assertNull(uf.find(null));
    uf.union(null, 1);
    uf.equivalent(null, 1);
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
      uf.union(i-1, i);
      assertTrue(uf.equivalent(-100, i));
      if (i < 99)
        assertFalse(uf.equivalent(-100, i+1));
    }
    
    for (int i = -100; i < 100; i++) {
      assertTrue(uf.equivalent(0, i));
    }
  }
  
  @Test
  public void testPathCompressionBranch() {
    UnionFind<Integer> uf = new UnionFind<>(Arrays.asList(1, 2, 3, 4, 5, 6));
    uf.union(1, 2);
    uf.union(3, 4);
    uf.union(5, 6);
    uf.union(2, 3);
    uf.union(4, 5);
    assertEquals((Integer) 1, uf.find(2));
    assertEquals((Integer) 1, uf.find(5));
    
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
    
    uf.union(1, 6);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 5 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6 } );

    uf.union(6, 6);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 5 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6 } );

    uf.union(6, 5);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6, 5 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 1, 6, 5 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6, 5 } );

    uf.union(4, 6);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );

    uf.union(3, 2);
    Arrays.equals(uf.members(1).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(2).toArray(new Integer[0]), new Integer[] { 3, 2 } );
    Arrays.equals(uf.members(3).toArray(new Integer[0]), new Integer[] { 3, 2 } );
    Arrays.equals(uf.members(4).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(5).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    Arrays.equals(uf.members(6).toArray(new Integer[0]), new Integer[] { 1, 6, 5, 4 } );
    
    uf.union(3, 6);
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
      uf.equivalent(1, 2);
      fail("Expected an IllegalArgumentException as 2 is not in the UnionFind");
    }
    catch (IllegalArgumentException e) {
      // Expected
    }
    try {
      uf.find(2);
      fail("Expected an IllegalArgumentException as 2 is not in the UnionFind");
    }
    catch (IllegalArgumentException e) {
      // Expected
    }

  }
  


}
