package me.dusanov.fa.dijkstra;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONObject;

import me.dusanov.fa.domains.Airport;

public class Node {

    //private String name;
	private Airport airport;
    private LinkedList<Node> cheapestPath = new LinkedList<>();
    private BigDecimal price = BigDecimal.valueOf(Integer.MAX_VALUE);

    public Node(Airport airport) { this.airport = airport; }
    public Node() {}

	public Node deepCopy(Map<Node, Node> isomorphism) {
        Node copy = isomorphism.get(this);

        if (copy == null) {
            copy = new Node(this.airport);
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
    
    public BigDecimal getDestinationPrice(Node destination) {
    	return destinationNodes.get(destination);
    }

	public Airport getAirport() {
		return airport;
	}

	public void setAirport(Airport airport) {
		this.airport = airport;
	}

	public LinkedList<Node> getCheapestPath() {
		return cheapestPath;
	}
	private Map<Node, BigDecimal> destinationNodes = new HashMap<>();

	public void setCheapestPath(LinkedList<Node> shortestPath) {
		this.cheapestPath = shortestPath;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Map<Node, BigDecimal> getDestinationNodes() {
		return destinationNodes;
	}

	public void setDestinationNodes(Map<Node, BigDecimal> destinationNodes) {
		this.destinationNodes = destinationNodes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Node [Airport=");
		builder.append(airport);
		builder.append(", cheapestPath=");
		builder.append(cheapestPath);
		builder.append(", price=");
		builder.append(price);
//		builder.append(", destinationNodes=");
//		builder.append(destinationNodes);
		builder.append("]");
		return builder.toString();
	}
	
	public JSONObject toGeoJsonString() {
		
	    JSONObject point = new JSONObject();
	    point.put("type", "Point");
        // construct a JSONArray from a string; can also use an array or list
        JSONArray coord = new JSONArray("["+airport.getLongitude()+","+airport.getLatitude()+"]");
        point.put("coordinates", coord);

		return point;
	}
}
