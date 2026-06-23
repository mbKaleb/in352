import java.util.Random;

public class Part3 {

    static final int INSERT_COUNT = 1000;
    static final int SEARCH_COUNT = 100;
    static final int DELETE_COUNT = 100;

    static void run() {
        Random rand = new Random();

        int[] values = randomValues(INSERT_COUNT, rand);

        AvlTree avl = new AvlTree();
        UnbalancedTree bst = new UnbalancedTree();

        long avlInsertNs = timeInsertAvl(avl, values);
        long bstInsertNs = timeInsertBst(bst, values);

        int[] searchKeys = sample(values, SEARCH_COUNT, rand);
        long avlSearchNs = timeSearchAvl(avl, searchKeys);
        long bstSearchNs = timeSearchBst(bst, searchKeys);

        int[] deleteKeys = sample(values, DELETE_COUNT, rand);
        long avlDeleteNs = timeDeleteAvl(avl, deleteKeys);
        long bstDeleteNs = timeDeleteBst(bst, deleteKeys);

        System.out.println("Insert " + INSERT_COUNT + " values:");
        System.out.println("  AVL Tree: " + avlInsertNs + " ns");
        System.out.println("  BST:      " + bstInsertNs + " ns");

        System.out.println("Search " + SEARCH_COUNT + " values:");
        System.out.println("  AVL Tree: " + avlSearchNs + " ns");
        System.out.println("  BST:      " + bstSearchNs + " ns");

        System.out.println("Delete " + DELETE_COUNT + " values:");
        System.out.println("  AVL Tree: " + avlDeleteNs + " ns");
        System.out.println("  BST:      " + bstDeleteNs + " ns");
    }

    private static int[] randomValues(int count, Random rand) {
        int[] values = new int[count];
        for (int i = 0; i < count; i++) {
            values[i] = rand.nextInt(count * 10);
        }
        return values;
    }

    // sample with replacement from already-inserted values, so search/delete hit real keys
    private static int[] sample(int[] values, int count, Random rand) {
        int[] sample = new int[count];
        for (int i = 0; i < count; i++) {
            sample[i] = values[rand.nextInt(values.length)];
        }
        return sample;
    }

    private static long timeInsertAvl(AvlTree tree, int[] values) {
        long start = System.nanoTime();
        for (int v : values) tree.insert(v);
        return System.nanoTime() - start;
    }

    private static long timeInsertBst(UnbalancedTree tree, int[] values) {
        long start = System.nanoTime();
        for (int v : values) tree.insert(v);
        return System.nanoTime() - start;
    }

    private static long timeSearchAvl(AvlTree tree, int[] keys) {
        long start = System.nanoTime();
        for (int k : keys) tree.contains(k);
        return System.nanoTime() - start;
    }

    private static long timeSearchBst(UnbalancedTree tree, int[] keys) {
        long start = System.nanoTime();
        for (int k : keys) tree.contains(k);
        return System.nanoTime() - start;
    }

    private static long timeDeleteAvl(AvlTree tree, int[] keys) {
        long start = System.nanoTime();
        for (int k : keys) tree.delete(k);
        return System.nanoTime() - start;
    }

    private static long timeDeleteBst(UnbalancedTree tree, int[] keys) {
        long start = System.nanoTime();
        for (int k : keys) tree.delete(k);
        return System.nanoTime() - start;
    }
}
