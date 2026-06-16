import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DataTracker {

    public static void main(String[] args) {

        // p1
        System.out.println("=== Part 1: Hash Outputs from Multiple Inputs ===");
        String[] samples = {"data1", "data2", "hello world"};
        for (String s : samples) {
            System.out.println("Input: " + s);
            System.out.println("Hash:  " + SHA256.hashHex(s));
            System.out.println();
        }

        System.out.println("=== Part 1: Tampering Detection ===");
        String original = "hello world";
        String tampered = "hello_world";
        String origHash = SHA256.hashHex(original);
        String tampHash = SHA256.hashHex(tampered);
        System.out.println("Original  [" + original + "]: " + origHash);
        System.out.println("Tampered  [" + tampered + "]: " + tampHash);
        System.out.println("Match: " + origHash.equals(tampHash));
        System.out.println();

        // p2a
        System.out.println("=== Part 2: LinkedList with Data and Hashes ===");
        LinkedList<String> data = new LinkedList<>();
        data.add("Image1.jpg: cat");
        data.add("Image2.jpg: dog");
        data.add("User42: score=88");
        data.add("Transaction1: $20");
        data.add("Sensor: temperature=72");

        LinkedList<String> hashes = new LinkedList<>();
        for (String entry : data) {
            hashes.add(SHA256.hashHex(entry));
        }

        for (int i = 0; i < data.size(); i++) {
            System.out.println("Data: " + data.get(i));
            System.out.println("Hash: " + hashes.get(i));
            System.out.println();
        }

        // p2b
        System.out.println("=== Part 2: Queue Processing and Hash Match Checks ===");
        Queue<String> queue = new LinkedList<>(data);
        int idx = 0;
        while (!queue.isEmpty()) {
            String entry = queue.poll();
            String reHash = SHA256.hashHex(entry);
            boolean match = reHash.equals(hashes.get(idx++));
            System.out.println("Data: " + entry);
            System.out.println("Hash Match: " + (match ? "Yes" : "No"));
            System.out.println();
        }

        // p2c
        System.out.println("=== Part 2: Stack Undo Operation ===");
        Stack<String> stack = new Stack<>();
        for (String entry : data) stack.push(entry);

        String undone = stack.pop();
        data.remove(undone);

        System.out.println("Removed: " + undone);
        System.out.println("Updated list:");
        for (String entry : data) System.out.println("  " + entry);
    }
}