package io.acoia.graphs;

import static org.junit.Assert.*;

import java.util.stream.IntStream;

import org.junit.Test;

import com.google.common.graph.MutableValueGraph;
import com.google.common.graph.ValueGraph;
import com.google.common.graph.ValueGraphBuilder;

public class MinimumSpanningTreeTest {
  
  @Test
  public void kruskals() {
    MutableValueGraph<Integer, Integer> graph = ValueGraphBuilder
        .undirected()
        .allowsSelfLoops(false)
        .expectedNodeCount(12)
        .build();
    
    for (int i = 0; i < 10; i++) {
      graph.addNode(i);
    }
    
    for (int i = 1; i < 10; i++) {
      graph.putEdgeValue(0, i, 10);
    }
    
    for (int i = 2; i < 10; i++) {
      graph.putEdgeValue(1, i, 20);
    }

    for (int i = 3; i < 10; i++) {
      graph.putEdgeValue(2, i, 30);
    }

    for (int i = 4; i < 10; i++) {
      graph.putEdgeValue(3, i, 100);
    }
    
    graph.addNode(10);
    graph.addNode(11);
    graph.putEdgeValue(10, 11, 100);

    ValueGraph<Integer, Integer> minTree = MinimumSpanningTree.kruskals(graph);
    assertEquals(9, minTree.adjacentNodes(0).size());
    assertEquals(1, minTree.adjacentNodes(1).size());
    assertEquals(1, minTree.adjacentNodes(10).size());
  }
  
  @Test
  public void kruskals2() {
    MutableValueGraph<Integer, Integer> graph = ValueGraphBuilder
        .undirected()
        .allowsSelfLoops(false)
        .expectedNodeCount(12)
        .build();

    for (int i = 1; i <= 7; i++) {
      graph.addNode(i);
    }
    
    graph.putEdgeValue(1, 2, 8);
    graph.putEdgeValue(1, 3, 5);
    graph.putEdgeValue(2, 3, 10);
    graph.putEdgeValue(2, 4, 2);
    graph.putEdgeValue(2, 5, 18);
    graph.putEdgeValue(3, 4, 3);
    graph.putEdgeValue(3, 6, 16);
    graph.putEdgeValue(4, 5, 12);
    graph.putEdgeValue(4, 6, 30);
    graph.putEdgeValue(4, 7, 14);
    graph.putEdgeValue(5, 7, 4);
    graph.putEdgeValue(6, 7, 26);
    
    ValueGraph<Integer, Integer> tree = MinimumSpanningTree.kruskals(graph);
    assertEquals(1, tree.inDegree(1));
    assertEquals(1, tree.inDegree(2));
    assertEquals(3, tree.inDegree(3));
    assertEquals(3, tree.inDegree(4));
    assertEquals(2, tree.inDegree(5));
    assertEquals(1, tree.inDegree(6));
    assertEquals(1, tree.inDegree(7));
    
    assertEquals(5, (int) tree.edgeValue(1, 3));
    assertEquals(2, (int) tree.edgeValue(2, 4));
    assertEquals(3, (int) tree.edgeValue(3, 4));
    assertEquals(16, (int) tree.edgeValue(3, 6));
    assertEquals(12, (int) tree.edgeValue(4, 5));
    assertEquals(4, (int) tree.edgeValue(5, 7));
  }

}
