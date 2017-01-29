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

}
