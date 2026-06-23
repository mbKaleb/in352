public class UnbalancedTree {

    private TreeNode root;

    UnbalancedTree() {
        this.root = null;
    }

    void insert(int key) {
        root = insert(root, key);
    }

    private TreeNode insert(TreeNode node, int key) {
        if (node == null) {
            return new TreeNode(key);
        }

        if (key < node.key) {
            node.left = insert(node.left, key);
        } else if (key > node.key) {
            node.right = insert(node.right, key);
        }
        // equal keys: ignore, no duplicates

        return node;
    }

    boolean contains(int key) {
        return contains(root, key);
    }

    private boolean contains(TreeNode node, int key) {
        if (node == null) return false;
        if (key == node.key) return true;
        return key < node.key ? contains(node.left, key) : contains(node.right, key);
    }

    static class TreeNode {
        int key;
        TreeNode left, right;

        TreeNode(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }
}
