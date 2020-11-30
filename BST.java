import java.util.Iterator;

public class BST<K extends Comparable, V> implements Iterable<Node<K, V>> {

    private Node<K, V> root;
    private long size; 

    public BST() {
        root = null;
    }

    public Iterator<Node<K, V>> iterator() {
        return null; 
    }

    private Node<K, V> findSpot(K key) {
        Node<K, V> currNode = root;
        while (true) {
            if (key.compareTo(currNode.getKey()) == 0) {
                break; 
            }
            // if key is less than currNode go left
            if (key.compareTo(currNode.getKey()) < 0) {
                if (currNode.getLeft() == null) {
                    break; 
                }
                currNode = currNode.getLeft();
                // if key is greater than currNode go right
            } else {
                if (currNode.getRight() == null) {
                    break; 
                }
                currNode = currNode.getRight();
            }
        }
        return currNode;   
    }

    private Node<K, V> findParent(K key) {
        if (root.getKey().equals(key)) {
            return root; 
        }
        Node<K, V> currNode = root;
        while (true) {
            if (currNode.getLeft() == null) {
                break; 
            } else if (currNode.getRight() == null) {
                break; 
            }
            if (key.compareTo(currNode.getLeft().getKey()) == 0 ||
                key.compareTo(currNode.getRight().getKey()) == 0) {
                // we found it 
                break; 
            }
            // we didn't find it yet 

            // if key is less than currNode go left
            if (key.compareTo(currNode.getKey()) < 0) {
                currNode = currNode.getLeft();
            // if key is greater than currNode go right
            } else {
                currNode = currNode.getRight();
            }
        }
        return currNode;   
    }

    private Node<K, V> minNode(Node<K, V> node) { 
        Node<K, V> currNode = node; 
        while (currNode.getLeft() != null) { 
            currNode = currNode.getLeft(); 
        } 
        return currNode; 
    } 

    public V remove(K key) {
        Node<K, V> currNode = findSpot(key); 
        Node<K, V> currParent = null; 
        // many a cast 
        K rootKey    = (K)root.getKey();

        if (! currNode.getKey().equals(rootKey)) {
            currParent = findParent(key);
        } // if currNode is root, currParent is null 

        // three cases to consider
        if (currNode.getSize() == 0) {
            if (currNode == root) {
                V value = root.getValue();
                root = null;
                return value; 
            }
            // no children (leaf)
            if (currParent.getLeft() == currNode) {
                currParent.setLeft(null);
            } else {
                currParent.setRight(null);
            }
        } else if (currNode.getSize() == 1) {
            System.out.println("In size 1 with: " + currNode.getKey());
            // copy child to the node 
            if (currNode.getLeft() != null) {
                K leftKey = (K)currNode.getLeft().getKey();
                V leftValue = (V)currNode.getLeft().getValue();
                currNode.setKey(leftKey);
                currNode.setValue(leftValue);
                Node<K, V> leftNode = currNode.getLeft();
                Node<K, V> rightNode = currNode.getLeft().getRight();
                currNode.setLeft(leftNode);
                currNode.setRight(rightNode);
                
            } else {
                K rightKey = (K)currNode.getRight().getKey();
                V rightValue = (V)currNode.getRight().getValue();
                currNode.setKey(rightKey);
                currNode.setValue(rightValue);
                Node<K, V> rightNode = currNode.getRight();
                Node<K, V> leftNode = currNode.getRight().getLeft();
                currNode.setRight(rightNode);    
                currNode.setLeft(leftNode);
            }
        } else if (currNode.getSize() == 2) {
            System.out.println("In size 2 with: " + currNode.getKey());
            // two children
            Node<K, V> successNode = nodeSuccessor(currNode);
            Node<K, V> parentSuccessNode = findParent(successNode.getKey());
            
            K sKey = (K)successNode.getKey();
            V sVal = (V)successNode.getValue();
            
            currNode.setKey(sKey);
            currNode.setValue(sVal);

            if (parentSuccessNode.getLeft() == successNode) {
                parentSuccessNode.setLeft(successNode.getLeft());
            } else if (parentSuccessNode.getRight() == successNode) {
                parentSuccessNode.setRight(successNode.getRight());
            }
        }
        return null;
    }

    private Node<K, V> nodeSuccessor(Node<K, V> currNode) {
        if (currNode.getRight() != null) { 
            return minNode(currNode.getRight()); 
        } 
        Node<K, V> parentNode = findParent(currNode.getKey());
        while (parentNode != null && currNode == parentNode.getRight()) { 
            currNode = parentNode; 
            parentNode = findParent(parentNode.getKey()); 
        } 
        return parentNode; 
    }

    public void put(K key, V value) {
        if (key == null || value == null) {
            return; 
        }
        Node<K, V> newNode = new Node<K, V>(key, value);
        if (root == null || key.equals(root)) {
            root = newNode; 
            return;
        } else {
            Node<K, V> currNode = findSpot(key);
            if (key.compareTo(currNode.getKey()) == 0) {
                currNode.setValue(value);
            } else if (key.compareTo(currNode.getKey()) < 0) {
                currNode.setLeft(newNode);
            } else {
                currNode.setRight(newNode);
            }    
        }
        size += 1;      
    }

    public V get(K key) {
        Node<K, V> retNode = this.findSpot(key);
        if (retNode == null || ! retNode.getKey().equals(key)) {
            return null; 
        } else {
            return retNode.getValue();
        }
    }

    public long size() {
        return this.size; 
    }

    public static void main(String[] args) {
        BST<Integer, String> isBST = new BST<Integer, String>();

        isBST.put(7, "seven");
        isBST.put(8, "eight");
        isBST.put(9, "nine");
        isBST.put(6, "six");
        isBST.put(4, "four");
        isBST.put(5, "five");
        
        System.out.println(isBST.get(4));
        System.out.println(isBST.get(8));
        System.out.println(isBST.get(9));
        System.out.println(isBST.get(7));
        System.out.println(isBST.get(6));
        System.out.println(isBST.get(5));
        System.out.println("-------");
        isBST.remove(7);
        isBST.remove(8);
        isBST.remove(4);
        isBST.remove(9);
        System.out.println(isBST.get(4));
        System.out.println(isBST.get(8));
        System.out.println(isBST.get(9));
        System.out.println(isBST.get(7));
        System.out.println(isBST.get(6));
        System.out.println(isBST.get(5));
    }
}
