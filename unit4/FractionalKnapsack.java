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

    /** Sort by ratio desc, fill greedily, print steps, return total value. */
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

    public static void main(String[] args) {
        Item[] items = {
            new Item("A", 60, 10),
            new Item("B", 100, 20),
            new Item("C", 120, 30)
        };
        solve(items, 50);

        // Part 2: timing on large random inputs
        System.out.println("\n--- Performance test (greedy, O(n log n)) ---");
        Random rand = new Random(42);
        int[] sizes = {1_000, 10_000, 100_000, 1_000_000};
        for (int n : sizes) {
            Item[] big = new Item[n];
            for (int i = 0; i < n; i++) {
                big[i] = new Item("I" + i, 1 + rand.nextInt(100), 1 + rand.nextInt(50));
            }
            long start = System.nanoTime();
            solveQuiet(big, n * 10.0);
            System.out.printf("n = %,d items -> %d ms%n", n, (System.nanoTime() - start) / 1_000_000);
        }
    }

    /** Same algorithm without printing, for timing. */
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
}