public class AvlTree {

        static class Node {

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


    // Notes for AvlTree
    // for every node abs( height(left) - height(right) ) <= 1
    // We can only place at leaves
    // in some sense the AvlTree+ different trees -- is just a layer to decide how + when to rotate leaves
    //

    private Node root;
    private boolean verbose;

    AvlTree() {
        this.root = null;
        this.verbose = false;
    }

    AvlTree(boolean verbose) {
        this.root = null;
        this.verbose = verbose;
    }

    int getHeight(Node node){
        if (node == null) return 0;
        return node.height;
    }

    int getBalance(Node node){
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    void updateHeight(Node node){
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    // Rotations
    Node rotateRight(Node root){
        // node
        // x = new head
        // temp = x.right
        // x.right = node
        // at this point old head.left is guaranteed to be null/available for us since x was old heads.left
        // oldhead.left = temp
        // return new head

        // save old root
        // update new root to left child tree

        // starting from left child as new root
        // take new root, promote
        // save new root right child
        // make old root new right child
        //
        if (verbose) System.out.println("  Rotate Right on node " + root.key);
        Node left = root.left;
        Node temp = left.right;

        left.right = root;
        root.left = temp;

        updateHeight(root);
        updateHeight(left);

        return left;
    }

    Node rotateLeft(Node root){
        // mirror of rotateRight: right child becomes new head,
        // its left subtree gets handed to old head's right slot
        if (verbose) System.out.println("  Rotate Left on node " + root.key);
        Node right = root.right;
        Node temp = right.left;

        right.left = root;
        root.right = temp;

        updateHeight(root);
        updateHeight(right);

        return right;
    }

    Node rotateLeftRight(Node node){
        // left child is right-heavy: straighten it out first, then rotate right
        if (verbose) System.out.println("  Rotate Left-Right on node " + node.key);
        node.left = rotateLeft(node.left);
        return rotateRight(node);
    }

    Node rotateRightLeft(Node node){
        // right child is left-heavy: straighten it out first, then rotate left
        if (verbose) System.out.println("  Rotate Right-Left on node " + node.key);
        node.right = rotateRight(node.right);
        return rotateLeft(node);
    }

    // Rebalance: update height + balance check, pick rotation if needed.
    // Shared by insert and delete since both walk back up the same path fixing things.
    private Node rebalance(Node node) {
        updateHeight(node);
        int balance = getBalance(node);

        // left-heavy
        if (balance > 1) {
            if (getBalance(node.left) < 0) {
                return rotateLeftRight(node); // left child is right-heavy
            }
            return rotateRight(node);
        }

        // right-heavy
        if (balance < -1) {
            if (getBalance(node.right) > 0) {
                return rotateRightLeft(node); // right child is left-heavy
            }
            return rotateLeft(node);
        }

        return node;
    }

    boolean contains(int key) {
        Node node = root;
        while (node != null) {
            if (key == node.key) return true;
            node = key < node.key ? node.left : node.right;
        }
        return false;
    }

    void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(Node node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.key + " ");
        inOrder(node.right);
    }

    void insert(int key) {
        if (verbose) System.out.println("Inserting: " + key);
        root = insert(root, key);
    }

    // Insert: regular BST insert, then walk back up fixing height + balance.
    // Each recursive call returns the (possibly new) root of the subtree it was given,
    // so the caller can re-link node.left/node.right to whatever rotation produced.
    private Node insert(Node node, int key) {
        if (node == null) {
            return new Node(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        } else {
            return node; // no duplicate keys
        }

        return rebalance(node);
    }

    void delete(int key) {
        root = delete(root, key);
    }

    private Node delete(Node node, int key) {
        if (node == null) return null;
        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            // found
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right; // 0 or 1 child
            } else {
                Node succ = node.right;           // inorder successor = min of right subtree
                while (succ.left != null) succ = succ.left;
                node.key = succ.key;
                node.right = delete(node.right, succ.key);
            }
        }
        if (node == null) return null; // deleted a leaf
        return rebalance(node);
    }
}
