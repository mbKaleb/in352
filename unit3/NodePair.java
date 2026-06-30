//NodePair.java by Kaleb Franken
public class NodePair extends Pair<Node, Integer> {
    private Node first;
    private Integer distance;

    NodePair(Node first, Integer distance) {
        this.first = first;
        this.distance = distance;
    }

    public Node first() {
        return this.first;
    }

    public Integer second() {
        return this.distance;
    }
}