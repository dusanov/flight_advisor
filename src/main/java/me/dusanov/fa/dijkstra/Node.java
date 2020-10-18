package me.dusanov.fa.dijkstra;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

//@Data
//@Getter @Setter @RequiredArgsConstructor @ToString
//No lombok here, screws up hashset
public class Node {

    private String name;
    private LinkedList<Node> shortestPath = new LinkedList<>();
    private BigDecimal price = BigDecimal.valueOf(Integer.MAX_VALUE);
    private Map<Node, BigDecimal> destinationNodes = new HashMap<>();

    public Node(String name) {
        this.name = name;
    }

    public void addDestination(Node destination, BigDecimal price) {
        destinationNodes.put(destination, price);
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LinkedList<Node> getShortestPath() {
		return shortestPath;
	}

	public void setShortestPath(LinkedList<Node> shortestPath) {
		this.shortestPath = shortestPath;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Map<Node, BigDecimal> getAdjacentNodes() {
		return destinationNodes;
	}

	public void setAdjacentNodes(Map<Node, BigDecimal> adjacentNodes) {
		this.destinationNodes = adjacentNodes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Node [name=");
		builder.append(name);
		builder.append(", price=");
		builder.append(price);
		builder.append("]");
		return builder.toString();
	}
	
}
