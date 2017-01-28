package io.acoia.sets;

import static org.junit.Assert.*;

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

    for (int i = -100; i < 100; i++) {
      uf.addMember(i);
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
}
