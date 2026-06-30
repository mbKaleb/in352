
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
        System.out.println("\n=== Dijkstra's Algorithm (A to D) ===");
        Dijkstra dijkstra = new Dijkstra(graph);

        long dijkstraStart = System.nanoTime();
        ArrayList<Node> dijkstraPath = dijkstra.findShortestPath(graph.A, graph.D);
        long dijkstraEnd = System.nanoTime();
        long dijkstraTime = dijkstraEnd - dijkstraStart;

        dijkstra.printResult(dijkstraPath);
        System.out.println("Time: " + dijkstraTime + " nanoseconds (" + (dijkstraTime / 1_000_000.0) + " ms)");

        // Run Greedy Search
        System.out.println("\n=== Greedy Search (A to D) ===");
        GreedySearch greedy = new GreedySearch();

        long greedyStart = System.nanoTime();
        ArrayList<Node> greedyPath = greedy.findPath(graph.A, graph.D);
        long greedyEnd = System.nanoTime();
        long greedyTime = greedyEnd - greedyStart;

        greedy.printResult(greedyPath);
        System.out.println("Time: " + greedyTime + " nanoseconds (" + (greedyTime / 1_000_000.0) + " ms)");

        System.out.println("Dijkstra's Time:   " + dijkstraTime + " ns");
        System.out.println("Greedy Time:       " + greedyTime + " ns");

    }
}