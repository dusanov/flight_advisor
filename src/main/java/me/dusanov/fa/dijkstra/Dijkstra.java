package me.dusanov.fa.dijkstra;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class Dijkstra {

    public static Graph calculateShortestPathFromSource(Graph graph, Node source) {

        source.setPrice(BigDecimal.ZERO);

        Set<Node> settledNodes = new HashSet<>();
        Set<Node> unsettledNodes = new HashSet<>();
        unsettledNodes.add(source);

        while (unsettledNodes.size() != 0) {
            Node currentNode = getLowestDistanceNode(unsettledNodes);
            unsettledNodes.remove(currentNode);            
            for (Entry<Node, BigDecimal> destinationPair : currentNode.getAdjacentNodes().entrySet()) {
                Node destinationNode = destinationPair.getKey();
                BigDecimal price = destinationPair.getValue();
                if (!settledNodes.contains(destinationNode)) {
                    calculateMinimumDistance(destinationNode, price, currentNode);
                    unsettledNodes.add(destinationNode);
                }
            }
            settledNodes.add(currentNode);
        }
        return graph;
    }

    private static void calculateMinimumDistance(Node evaluationNode, BigDecimal edgeWeigh, Node sourceNode) {
        BigDecimal sourceDistance = sourceNode.getPrice();        
        if (sourceDistance.add(edgeWeigh).compareTo(evaluationNode.getPrice()) == -1 ) {
            evaluationNode.setPrice(sourceDistance.add(edgeWeigh));
            LinkedList<Node> shortestPath = new LinkedList<>(sourceNode.getShortestPath());
            shortestPath.add(sourceNode);
            evaluationNode.setShortestPath(shortestPath);
        }
    }

    private static Node getLowestDistanceNode(Set<Node> unsettledNodes) {
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
