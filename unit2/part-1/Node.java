// Wrote this here but its a blueprint bc the language server is dumb and doesn't resolve it as a type, bad indexing/ idx
public class Node {

    int key;
    Node left, right;
    int height;

    Node(int key) {
        this .key = key;
        this .left = null;
        this .right = null;
        this .height = 1;

    }
}
