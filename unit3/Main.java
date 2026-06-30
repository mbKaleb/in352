//Main.java by Kaleb Franken
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Create graph
        ExampleGraph graph = new ExampleGraph();

        // Print graph structure
        System.out.println("=== Graph Details ===");
        graph.printGraph();

        // Run Dijkstra's algorithm
        // System.out.println("\n=== Dijkstra's Algorithm (A to D) ===");
        // Dijkstra dijkstra = new Dijkstra(graph);
        // ArrayList<Node> dijkstraPath = dijkstra.findShortestPath(graph.A, graph.D);
        // dijkstra.printResult(dijkstraPath);

        // Run Greedy Search
        System.out.println("\n=== Greedy Search (A to D) ===");
        GreedySearch greedy = new GreedySearch();
        ArrayList<Node> greedyPath = greedy.findPath(graph.A, graph.D);
        greedy.printResult(greedyPath);
    }
}