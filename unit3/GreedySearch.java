//GreedySearch.java by Kaleb Franken
import java.util.ArrayList;
import java.util.HashSet;

public class GreedySearch {
    private HashSet<Node> visited;
    private int totalCost;

    public ArrayList<Node> findPath(Node start, Node end) {
        visited = new HashSet<>();
        totalCost = 0;
        ArrayList<Node> path = new ArrayList<>();
        dfs(start, end, path);

        if (!path.isEmpty()) {
            path.get(path.size() - 1).distance = totalCost;
        }
        return path;
    }

    private boolean dfs(Node current, Node end, ArrayList<Node> path) {
        if (current == null) return false;
        if (current == end) {
            path.add(current);
            return true;
        }

        path.add(current);
        visited.add(current);

        Node closest = null;
        int minWeight = Integer.MAX_VALUE;

        for (NodePair edge : current.edges) {
            if (!visited.contains(edge.first()) && edge.second() < minWeight) {
                closest = edge.first();
                minWeight = edge.second();
            }
        }

        if (closest != null) {
            totalCost += minWeight;
            if (dfs(closest, end, path)) {
                return true;
            }
            totalCost -= minWeight;
        }

        path.remove(path.size() - 1);
        return false;
    }

    public void printResult(ArrayList<Node> path) {
        System.out.print("Path: ");
        for (Node n : path) System.out.print(n.name + " ");
        System.out.println("\nTotal cost: " + path.get(path.size() - 1).distance);
    }
}