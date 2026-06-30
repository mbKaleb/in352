//Dijkstra.java by Kaleb Franken
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Comparator;

public class Dijkstra {
    private ExampleGraph graph;

    public Dijkstra(ExampleGraph graph) {
        this.graph = graph;
    }

    /**
     * Find shortest path from start to end node
     */
    public ArrayList<Node> findShortestPath(Node start, Node end) {
        // Initialize distances
        resetDistances();
        start.distance = 0;

        // Priority queue: nodes ordered by distance
        PriorityQueue<Node> queue = new PriorityQueue<>(
            Comparator.comparingInt(n -> n.distance)
        );
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // Process neighbors
            for (NodePair edge : current.edges) {
                Node neighbor = edge.first();
                int weight = edge.second();

                // Relaxation
                int newDistance = current.distance + weight;
                if (newDistance < neighbor.distance) {
                    neighbor.distance = newDistance;
                    neighbor.previous = current;
                    queue.add(neighbor);
                }
            }
        }

        // Reconstruct path
        return reconstructPath(start, end);
    }

    /**
     * Rebuild path from start to end using previous pointers
     */
    private ArrayList<Node> reconstructPath(Node start, Node end) {
        ArrayList<Node> path = new ArrayList<>();
        Node current = end;

        while (current != null) {
            path.add(0, current);
            current = current.previous;
        }

        return path;
    }

    /**
     * Reset all distances to infinity
     */
    private void resetDistances() {
        // TODO: Reset all nodes in graph
    }

    /**
     * Print path and total distance
     */
    public void printResult(ArrayList<Node> path) {
        System.out.print("Path: ");
        for (Node n : path) {
            System.out.print(n.name + " ");
        }
        System.out.println("\nTotal distance: " + path.get(path.size() - 1).distance);
    }
}