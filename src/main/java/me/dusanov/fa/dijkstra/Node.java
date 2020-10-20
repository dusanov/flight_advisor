package me.dusanov.fa.dijkstra;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

public class Node {

    private String name;
    private LinkedList<Node> cheapestPath = new LinkedList<>();
    private BigDecimal price = BigDecimal.valueOf(Integer.MAX_VALUE);
    private Map<Node, BigDecimal> destinationNodes = new HashMap<>();

    public Node(String name) { this.name = name; }
    public Node() {}

	public Node deepCopy(Map<Node, Node> isomorphism) {
        Node copy = isomorphism.get(this);

        if (copy == null) {
            copy = new Node(this.name);
            isomorphism.put(this, copy);
            
            Iterator<Entry<Node, BigDecimal>> it = destinationNodes.entrySet().iterator();
            
            while (it.hasNext()){
            	Map.Entry<Node,BigDecimal> pair = (Map.Entry<Node,BigDecimal>)it.next();
            	copy.destinationNodes.put(pair.getKey().deepCopy(isomorphism),pair.getValue());
            }        
        }

        return copy;
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

	public LinkedList<Node> getCheapestPath() {
		return cheapestPath;
	}

	public void setCheapestPath(LinkedList<Node> shortestPath) {
		this.cheapestPath = shortestPath;
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
		builder.append(", cheapestPath=");
		builder.append(cheapestPath);
		builder.append(", price=");
		builder.append(price);
//		builder.append(", destinationNodes=");
//		builder.append(destinationNodes);
		builder.append("]");
		return builder.toString();
	}
}
