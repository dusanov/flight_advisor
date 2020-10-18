package me.dusanov.fa.dijkstra;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
//@Getter @Setter @RequiredArgsConstructor @ToString 
public class Graph {

    private Map<String, Node> nodes = new HashMap<>();

    public void addNode(String city,Node node) {
        nodes.put(city, node);
    }
}
