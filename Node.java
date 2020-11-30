public class Node<K, V> {

    Node left;
    Node right;
    K key;
    V value; 

    public Node (K key, V value) {
        this.key = key;
        this.value = value; 
    }

    public Node getLeft() {
        return this.left;
    }

    public K getKey() {
        return this.key;
    }

    public void setKey(K key) {
        this.key = key; 
    }

    public V getValue() {
        return this.value; 
    }

    public void setValue(V value) {
        this.value = value;
    }

    public Node getRight() {
        return this.right; 
    }

    public int getSize() {
        int size = 0;
        if (right != null || left != null) {
            size += 1;
            if (left != null && right != null) {
                size += 1;
            }
        }
        return size; 
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public void setRight(Node right) {
        this.right = right; 
    }
} 
