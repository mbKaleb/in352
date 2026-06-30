//Node.java by Kaleb Franken
import java.util.ArrayList;

public class Node {
        public ArrayList<NodePair> edges;
        public String name;
        int distance;
        Node previous;

        Node(String name) {
            this.name = name;
            this.edges = new ArrayList<NodePair>();
            this.distance = Integer.MAX_VALUE;
            this.previous = null;
        }

        Node(ArrayList<Node> neighbors) {

        }
    }