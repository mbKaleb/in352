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

    void inOrder() {
        inOrder(root);
        System.out.println();
    }

    private void inOrder(TreeNode node) {
        if (node == null) return;
        inOrder(node.left);
        System.out.print(node.key + " ");
        inOrder(node.right);
    }

    void delete(int key) {
        root = delete(root, key);
    }

    private TreeNode delete(TreeNode node, int key) {
        if (node == null) return null;
        if (key < node.key) {
            node.left = delete(node.left, key);
        } else if (key > node.key) {
            node.right = delete(node.right, key);
        } else {
            // found
            if (node.left == null || node.right == null) {
                return (node.left != null) ? node.left : node.right; // 0 or 1 child
            }
            TreeNode succ = node.right;           // inorder successor = min of right subtree
            while (succ.left != null) succ = succ.left;
            node.key = succ.key;
            node.right = delete(node.right, succ.key);
        }
        return node;
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
