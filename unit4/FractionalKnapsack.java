import java.util.Arrays;
import java.util.Comparator;

public class FractionalKnapsack {

    static class Item {
        String name;
        double value;
        double weight;

        Item(String name, double value, double weight) {

        }

        double ratio() {

            return 0;
        }
    }

    /** Sort by ratio desc, fill greedily, print steps, return total value. */
    public static double solve(Item[] items, double capacity) {




        double remaining = capacity;
        double totalValue = 0;

        for (Item it : items) {
        }

        // TODO: print total value
        return totalValue;
    }

    public static void main(String[] args) {
        Item[] items = {
            new Item("A", 60, 10),
            new Item("B", 100, 20),
            new Item("C", 120, 30)
        };
        solve(items, 50);

    }
}