package me.dusanov.fa.dijkstra;

import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Map;

import lombok.Data;

@Data
//@Getter @Setter @RequiredArgsConstructor @ToString 
public class Graph {

    private Map<String, Node> nodes = new HashMap<>();
    
    public Graph deepCopy () {
    	  Graph graphCopy = new Graph();
    	  graphCopy.nodes = new HashMap<>();
    	  Map<Node, Node> isomorphism = new IdentityHashMap<Node, Node>();
    	  
    	  nodes.forEach((city,node) -> {
    		  graphCopy.nodes.put(city, node.deepCopy(isomorphism));
    	  });
    	  return graphCopy;
    	}
    

	public void addNode(String city,Node node) {
        nodes.put(city, node);
    }
}
