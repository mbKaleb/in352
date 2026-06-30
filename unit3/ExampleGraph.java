// ExampleGraph.java by Kaleb Franken
public class ExampleGraph {

    public Node A = new Node("A");
    public Node B = new Node("B");
    public Node C = new Node("C");
    public Node D = new Node("D");
    public Node E = new Node("E");
    public Node F = new Node("F");

    public ExampleGraph() {
        // A — B (4)
        A.edges.add(new NodePair(B, 4));

        // A — C (2)
        A.edges.add(new NodePair(C, 2));

        // B — C (5)
        B.edges.add(new NodePair(C, 5));

        // B — D (10)
        B.edges.add(new NodePair(D, 10));

        // C — E (3)
        C.edges.add(new NodePair(E, 3));

        // E — D (4)
        E.edges.add(new NodePair(D, 4));

        // D — F (11)
        D.edges.add(new NodePair(F, 11));
    }

    public void printGraph() {
        System.out.println("=== Graph Structure ===");
        System.out.println("Nodes: A, B, C, D, E, F");
        System.out.println("\nEdges and Weights:");
        System.out.println("A — B (4)");
        System.out.println("A — C (2)");
        System.out.println("B — C (5)");
        System.out.println("B — D (10)");
        System.out.println("C — E (3)");
        System.out.println("E — D (4)");
        System.out.println("D — F (11)");
    }
}