public class AvlNode {

    // Notes for AvlNode
    // for every node abs( height(left) - height(right) ) <= 1
    // We can only place at leaves
    // in some sense the AvlNode+ different trees -- is just a layer to decide how + when to rotate leaves
    // 

    private int key; 
    // could use <T extence Comparable<T>> but we only have integer keys.
    private AvlNode left, right;
    private int height;

    void AvlNode(int key) {
        this .key = key;
        this .left = null;
        this .right = null;
        this .height = 1;
    }

    int getHeight(AvlNode node){
        if (node == null) return 0;
        return node.height;
    }

    int getBalance(AvlNode node){
        if (node == null) return 0;
        return getHeight(node.left) - getHeight(node.right);
    }

    void updateHeight(AvlNode node){
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    // Rotations
    AvlNode rotateRight(AvlNode node){
        if (node == null ){
            return AvlNode(key, null, null, 1);
        }

        if (node.left != null) {
            AvlNode x = node.left;
        }
        

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

    }

    // constructors

    // methods

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
