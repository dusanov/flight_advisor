package me.dusanov.fa.dijkstra;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class Dijkstra {

    public static Graph calculateCheapestPathFromSource(Graph graph, Node source) {

        source.setPrice(BigDecimal.ZERO);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getCheapestNode(unsettledNodes);
            unsettledNodes.remove(currentNode);            
            for (Entry<Node, BigDecimal> destinationPair : currentNode.getAdjacentNodes().entrySet()) {
                Node destinationNode = destinationPair.getKey();
                BigDecimal price = destinationPair.getValue();
                if (!settledNodes.contains(destinationNode)) {
                    calculateMinimumPrice(destinationNode, price, currentNode);
                    unsettledNodes.add(destinationNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static void calculateMinimumPrice(Node evaluationNode, BigDecimal edgePrice, Node sourceNode) {
        BigDecimal sourceDistancePrice = sourceNode.getPrice();        
        if (sourceDistancePrice.add(edgePrice).compareTo(evaluationNode.getPrice()) == -1 ) {
            evaluationNode.setPrice(sourceDistancePrice.add(edgePrice));
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getCheapestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setCheapestPath(shortestPath);
        }
    }

    private static Node getCheapestNode(Set<Node> unsettledNodes) {
        Node lowestPriceNode = null;
        BigDecimal lowestPrice = BigDecimal.valueOf(Integer.MAX_VALUE);
        for (Node node : unsettledNodes) {        	
            if (node.getPrice().compareTo(lowestPrice) == -1) {
                lowestPrice = node.getPrice();
                lowestPriceNode = node;
            }
        }
        return lowestPriceNode;
    }
}
