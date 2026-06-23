public class Main {

    public static void main(String[] args) {

        part1();
        part2();
        Part3.run();
    }


    static void part1(){
        int[] data = {30, 20, 40, 10, 11, 12, 13, 9};
 
        StringBuilder header = new StringBuilder("Inserting nodes: ");
        for (int i = 0; i < data.length; i++) {
            header.append(data[i]);
            if (i < data.length - 1) header.append(", ");
        }
        System.out.println(header);
        System.out.println();
 
        AvlTree tree = new AvlTree(true);

        for (int k : data) tree.insert(k);
 
        System.out.println();
        tree.inOrder();
        System.out.println();

        // InOrder Traversal
    }

    static void part2() {
        int[] data = {30, 20, 40, 10, 11, 12, 13, 9};

        StringBuilder header = new StringBuilder("Inserting nodes: ");
        for (int i = 0; i < data.length; i++) {
            header.append(data[i]);
            if (i < data.length - 1) header.append(", ");
        }
        System.out.println(header);
        System.out.println();

        UnbalancedTree tree = new UnbalancedTree();
        for (int k : data) tree.insert(k);

        System.out.println();
        tree.inOrder();
        System.out.println();
    }

    // access modifiers
    // public private protected static
    // Encapsulation — bundling fields + methods together and hiding internal state behind a controlled interface (getters/setters, validation in setters, etc).

    // Inheritance class Dog extends Animal

    // Polymorphism — a subclass can override a parent method (@Override

    // Abstraction — abstract class (can't instantiate directly, may have unimplemented methods

    // Static vs instance members — static belongs to the class (shared, accessed via ClassName.member), instance belongs to each object (accessed via objectRef.member).

    // Nested/inner classes — a class defined inside another, useful for tightly coupled helper types.

    // toString(), equals(), hashCode() — every class inherits these from Object; commonly overridden so objects print/comparison meaningfully (e.g. in HashMaps/HashSets, equals+hashCode need to be consistent).

}
