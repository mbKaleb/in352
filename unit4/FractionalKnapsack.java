import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class FractionalKnapsack {

    static class Item {
        String name;
        double value;
        double weight;

        Item(String name, double value, double weight) {
            this.name = name;
            this.value = value;
            this.weight = weight;
        }

        double ratio() {
            return value / weight;
        }
    }


    public static double solve(Item[] items, double capacity) {
        Arrays.sort(items, Comparator.comparingDouble(Item::ratio).reversed());

        System.out.println("Items sorted by value-to-weight ratio (descending):");
        System.out.printf("%-6s %-8s %-8s %-8s%n", "Item", "Value", "Weight", "Ratio");
        for (Item it : items) {
            System.out.printf("%-6s %-8.1f %-8.1f %-8.2f%n", it.name, it.value, it.weight, it.ratio());
        }
        System.out.println();

        double remaining = capacity;
        double totalValue = 0;

        System.out.println("Filling knapsack (capacity = " + capacity + "):");
        for (Item it : items) {
            if (remaining <= 0) break;
            if (it.weight <= remaining) {
                remaining -= it.weight;
                totalValue += it.value;
                System.out.printf("  Added 100%% of item %s (weight %.1f, value %.1f)%n",
                        it.name, it.weight, it.value);
            } else {
                double fraction = remaining / it.weight;
                totalValue += it.value * fraction;
                System.out.printf("  Added %.1f%% of item %s (weight %.1f, value %.1f)%n",
                        fraction * 100, it.name, remaining, it.value * fraction);
                remaining = 0;
            }
        }

        System.out.printf("%nTotal knapsack value: %.2f%n", totalValue);
        return totalValue;
    }

    /** Same greedy algorithm without printing, for timing. */
    static double solveQuiet(Item[] items, double capacity) {
        Arrays.sort(items, Comparator.comparingDouble(Item::ratio).reversed());
        double remaining = capacity, total = 0;
        for (Item it : items) {
            if (remaining <= 0) break;
            if (it.weight <= remaining) { remaining -= it.weight; total += it.value; }
            else { total += it.value * (remaining / it.weight); remaining = 0; }
        }
        return total;
    }

    /**
     * bottom-up dynamic programming.
     */
    static long dpKnapsack01(int[] values, int[] weights, int capacity) {
        long[] dp = new long[capacity + 1];
        for (int i = 0; i < values.length; i++) {
            for (int c = capacity; c >= weights[i]; c--) {
                dp[c] = Math.max(dp[c], dp[c - weights[i]] + values[i]);
            }
        }
        return dp[capacity];
    }

    /**
     * 0/1 Knapsack via backtracking: full enumeration of include/exclude
     * decisions. Time O(2^n) — only feasible for small n.
     */
    static long backtrackKnapsack01(int[] values, int[] weights, int capacity) {
        return backtrack(values, weights, 0, capacity, 0);
    }

    private static long backtrack(int[] values, int[] weights, int i, int remaining, long current) {
        if (i == values.length) return current;
        // Branch 1: exclude item i
        long best = backtrack(values, weights, i + 1, remaining, current);
        // Branch 2: include item i (if it fits)
        if (weights[i] <= remaining) {
            best = Math.max(best,
                    backtrack(values, weights, i + 1, remaining - weights[i], current + values[i]));
        }
        return best;
    }

    public static void main(String[] args) {
        // Part 1: assignment instance
        Item[] items = {
            new Item("A", 60, 10),
            new Item("B", 100, 20),
            new Item("C", 120, 30)
        };
        solve(items, 50);

        int[] vals = {60, 100, 120};
        int[] wts  = {10, 20, 30};
        System.out.println("\n--- Same instance, 0/1 versions (items indivisible) ---");
        System.out.println("DP (0/1) optimal value:           " + dpKnapsack01(vals, wts, 50));
        System.out.println("Backtracking (0/1) optimal value: " + backtrackKnapsack01(vals, wts, 50));
        System.out.println("(Fractional greedy got 240.00 - allowed to split item C.)");

        // Part 2a: greedy scales to huge n
        System.out.println("\n--- Performance test 1: greedy alone, O(n log n) ---");
        Random rand = new Random(42);
        int[] sizes = {1_000, 10_000, 100_000, 1_000_000};
        for (int n : sizes) {
            Item[] big = new Item[n];
            for (int i = 0; i < n; i++) {
                big[i] = new Item("I" + i, 1 + rand.nextInt(100), 1 + rand.nextInt(50));
            }
            long start = System.nanoTime();
            solveQuiet(big, n * 10.0);
            System.out.printf("n = %,9d items -> %5d ms%n", n, (System.nanoTime() - start) / 1_000_000);
        }
        System.out.println("\n--- Performance test 2: greedy vs DP vs backtracking ---");
        System.out.printf("%-10s %-14s %-14s %-16s%n", "n", "Greedy (ms)", "DP (ms)", "Backtrack (ms)");
        int[] compareSizes = {15, 20, 25, 28};
        for (int n : compareSizes) {
            int capacity = n * 10;
            int[] v = new int[n];
            int[] w = new int[n];
            Item[] greedyItems = new Item[n];
            for (int i = 0; i < n; i++) {
                v[i] = 1 + rand.nextInt(100);
                w[i] = 1 + rand.nextInt(50);
                greedyItems[i] = new Item("I" + i, v[i], w[i]);
            }

            long t0 = System.nanoTime();
            solveQuiet(greedyItems, capacity);
            double greedyMs = (System.nanoTime() - t0) / 1_000_000.0;

            t0 = System.nanoTime();
            dpKnapsack01(v, w, capacity);
            double dpMs = (System.nanoTime() - t0) / 1_000_000.0;

            t0 = System.nanoTime();
            backtrackKnapsack01(v, w, capacity);
            double btMs = (System.nanoTime() - t0) / 1_000_000.0;

            System.out.printf("%-10d %-14.3f %-14.3f %-16.3f%n", n, greedyMs, dpMs, btMs);
        }
        System.out.println("\nGreedy and DP stay in milliseconds; backtracking's time");
        System.out.println("roughly doubles with each extra item (O(2^n) growth).");
    }
}
