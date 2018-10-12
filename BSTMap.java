
package cxz173430;
/**
 * @author 		Churong Zhang 
 * 				cxz173430
 * 				October 5 2018
 * 				Dr. Raghavachari
 * 				This class is for Intensive Track Assignment 4 problem 3.
 * 				Binary Search Tree
 */
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;


public class BSTMap<K extends Comparable<? super K>, V> implements Iterable<K> {
	
	Entry<K,V> root;
	int size;
	Stack<Entry<K,V>> stack;
	int toArrayIndex;
	
	static class Entry<K,V>
	{
		K key;
		V value;
		Entry<K,V> left;
		Entry<K,V> right;
		/**
		 * create an Entry with key, value, left Entry and right Entry
		 * @param key
		 * @param value
		 * @param left Entry
		 * @param right Entry
		 */
		public Entry(K key, V value, Entry<K,V> left, Entry<K,V> right)
		{
			this.key = key;
			this.value = value;
			this.left = left;
			this.right = right;
		}
		/**
		 * print an entry's key and value
		 * @return a string version of key and value
		 */
		public String toString()
		{
			return "[" + key + "=" + value + "] ";
		}
	}
	/**
	 * create a BST Map with size 0
	 */
    BSTMap() {
    	size = 0;
    	root = null;
    }
    /**
     * @param K the key to find
     * @return the Entry with key x
     */
    private Entry<K,V> find(K x)
    {
    	stack = new Stack<>();
    	stack.push(null);
    	return find(root, x);
    }
    /**
     * @param ent the entry to begin the search
     * @param x the key to find
     * @return the Entry with key x
     */
    private Entry<K,V> find(Entry<K,V> ent, K x)
    {
    	if(ent == null || ent.key.compareTo(x) == 0)
    	{
    		return ent;
    	}
    	while(true)
    	{
    		if(x.compareTo(ent.key)< 0)
    		{
    			if(ent.left == null)
    			{
    				break;
    			}
    			else
    			{
    				stack.push(ent);
    				ent = ent.left;
    			}
    		}
    		else if(x.compareTo(ent.key) > 0)
    		{
    			if(ent.right == null)
    				break;
    			else
    			{
    				stack.push(ent);
    				ent = ent.right;
    			}
    		}
    		else
    		{
    			break;
    		}
    	}
    	return ent;
    }
    
    /** TO DO: Is x contained in treeMap?
     * 	@param x the key to check
     *  @return true if the key x exist in the map
     */
    public boolean contains(K x) {
    	Entry<K,V> ent = find(x);
    	if(ent == null || ent.key.compareTo(x) != 0)
    	{
    		return false;
    	}
    	
	return true;
    }
    /**
     * get the value that is correspond to the key 
     * @param key the key
     * @return the value that is correspond to the key if exist
     */
    public V get(K key) {
    	Entry<K,V> ent = find(key);
    	if(ent == null || ent.key.compareTo(key) != 0)
    	{
    		return null;
    	}
    	else
    		return ent.value;
    }
    /**
     * put the value on the key
     * @param key the key
     * @param value the value
     * @return true if successfully add or replace
     */
    public boolean put(K key, V value) {
    	if(root == null)
    	{
    		root = new Entry<K,V>(key, value, null, null);
    		size++;
    		return true;
    	}
    	else
    	{ 
    		Entry<K,V> ent = find(key);
    		
    		if(ent.key.compareTo(key) == 0)
        	{
        		ent.value = value;
        		size --; // same key can change value but size will not change
        	}
        	else if(ent.key.compareTo(key) > 0)
        	{
        		ent.left = new Entry<K,V>(key, value, null, null);
        	}
        	else
        	{
        		ent.right = new Entry<K,V>(key, value, null, null);
        	}
        	size ++;
        	return true;
    	}
	
    }
    /** TO DO: Remove x from tree. 
     * 	@param x the key to delete
     *  @return x if found, otherwise return null
     */
    public V remove(K x) {
    	Entry<K,V> temp = find(x);
    	if(temp == null || temp.key.compareTo(x) != 0)
    		return null;
    	V result = temp.value;
    	
    	if(temp.left == null || temp.right == null)
    	{
    		bypass(temp);
    	}
    	else //if(temp.left!= null && temp.right != null)
    	{
    		stack.push(temp);
    		Entry<K,V> minRight = find(temp.right, x);
    		temp.key = minRight.key;
    		temp.value = minRight.value;
    		bypass(minRight);
    		
    	}
    	size --;
	return result;
    }
    /**
     * bypass the give Entry
     * @param ent the entry to bypass
     */
    public void bypass(Entry<K,V> ent)
    {
   	if(ent == root)
    {
   		if(ent.right != null)
   		{
   			stack.push(ent);
   			Entry<K,V> minRight = find(ent.right, ent.key);
    		ent.key = minRight.key;
    		ent.value = minRight.value;
    		bypass(minRight);
   		}
   		else if(ent.left != null)
   		{
   			stack.push(ent);
   			Entry<K,V> minLeft = find(ent.left, ent.key);
    		ent.key = minLeft.key;
    		ent.value = minLeft.value;
    		bypass(minLeft);
   		}
   		else
   		{
   			root = null;
   		}
    }
    else 
    	{
    		Entry<K,V> parent = stack.peek();
        	Entry<K,V> child = ent.left == null ? ent.right: ent.left;
    		if(parent.left == ent)
        	{
        		parent.left = child;
        	}
        	else
        	{
        		parent.right = child;
        	}
    	}
    }

    /** 
     * @return the smallest element in tree.
     */
    public K min() {
    	if(root == null)
    	{
    		return null;
    	}
    	Entry<K,V> temp = root;
    	while(temp.left != null)
    	{
    		temp = temp.left;
    	}
	return temp.key;
    }

    /** 
     * @return the largest element in tree.
     */
    public K max() {
    	if(root == null)
    	{
    		return null;
    	}
    	Entry<K,V> temp = root;
    	while(temp.right != null)
    	{
    		temp = temp.right;
    	}
	return temp.key;
    }
    /**Create an array with the elements using in-order traversal of tree
     * 
     * @return an array with the elements using in-order traversal of tree keys
     */
    public Comparable[] toArray() {
    Comparable[] arr = new Comparable[size];
	toArrayIndex = 0;
	inOrder(root, arr);
	/* write code to place elements in array here */
	return arr;
    }
    /**
     * help put the key value to the array
     * @param ent entry to put in the array
     * @param arr the array receive the key
     */
    private void inOrder(Entry<K,V> ent, Comparable[] arr)
    {
    	if(ent != null)
    	{
    		inOrder(ent.left,arr);
    		arr[toArrayIndex] = ent.key;
    		toArrayIndex++;
    		inOrder(ent.right,arr);
    	}
    	
    }
    
    // Iterate over the keys stored in the map, in order
    public Iterator<K> iterator() {
	return new BSTMapIterator();
    }
    protected class BSTMapIterator implements Iterator<K>
    {
    	Entry<K,V> next;
    	Stack<Entry<K,V>> stack;
    	
    	public BSTMapIterator()
    	{
    		next = root;
    		if(next == null)
    			return;
    		
    		stack = new Stack<>();
    		stack.push(null); // to mark the end point
    		while(next.left != null)
    		{
    			stack.push(next);
    			next = next.left;
    		}
    	}
		/**
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return next != null;
		}

		/**
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public K next() {
			// TODO Auto-generated method stub
			Entry<K,V> ret = next;
			
			if(next.right != null)
			{
				next = next.right;
				while(next.left != null)
				{
					stack.push(next);
					next = next.left;
				}
				
			}
			else
			{
				next = stack.pop();
			}	
			return ret.key;
		}
    	
    }
    /**
     * print the treeMap
     */
    public void printTree()
	{
    	System.out.print("[" + size + "]: ");
		printTree(root);
		System.out.println("");
	}
    
    /**
     * print the tree map
     * @param node print the node and its children
     */
    public void printTree(Entry<K,V> node) {
    	if(node != null) {
    	    printTree(node.left);
    	    System.out.print(node);
    	    printTree(node.right);
    		}
        }

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BSTMap<Integer, Integer> t = new BSTMap<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int key = in.nextInt();
   
            if(key > 0) {
            	int value = in.nextInt();
                System.out.println("Add key " + key + " = value: " + value);
                t.put(key, value);
                t.printTree();
            } else if(key < 0) {
                System.out.println("Remove " + key);
                t.remove(-key);
                t.printTree();
            } else {
                Comparable[] arr = t.toArray();
                System.out.print("Final: ");
                for(int i=0; i<t.size; i++) {
                    System.out.print(arr[i] + " ");
                }
                System.out.println();
            	
                break;
                //return;
            }           
        }
        t.printTree();
        System.out.println("Max: "+ t.max() + " \nMin: " + t.min());
        
        System.out.println("Itertate throught the tree");
        Iterator<Integer> iter = t.iterator();
        while(iter.hasNext())
        {
        	System.out.print(iter.next()+ " ");
        }
	}

}
// sample input
// 7 3 5 6 9 5 4 3 1 5 2 9 8 6 3 7 11 14 15 6 -2 -7 -11 -11 0
/*
sample output
Add key 7 = value: 3
[1]: [7=3] 
Add key 5 = value: 6
[2]: [5=6] [7=3] 
Add key 9 = value: 5
[3]: [5=6] [7=3] [9=5] 
Add key 4 = value: 3
[4]: [4=3] [5=6] [7=3] [9=5] 
Add key 1 = value: 5
[5]: [1=5] [4=3] [5=6] [7=3] [9=5] 
Add key 2 = value: 9
[6]: [1=5] [2=9] [4=3] [5=6] [7=3] [9=5] 
Add key 8 = value: 6
[7]: [1=5] [2=9] [4=3] [5=6] [7=3] [8=6] [9=5] 
Add key 3 = value: 7
[8]: [1=5] [2=9] [3=7] [4=3] [5=6] [7=3] [8=6] [9=5] 
Add key 11 = value: 14
[9]: [1=5] [2=9] [3=7] [4=3] [5=6] [7=3] [8=6] [9=5] [11=14] 
Add key 15 = value: 6
[10]: [1=5] [2=9] [3=7] [4=3] [5=6] [7=3] [8=6] [9=5] [11=14] [15=6] 
Remove -2
[9]: [1=5] [3=7] [4=3] [5=6] [7=3] [8=6] [9=5] [11=14] [15=6] 
Remove -7
[8]: [1=5] [3=7] [4=3] [5=6] [8=6] [9=5] [11=14] [15=6] 
Remove -11
[7]: [1=5] [3=7] [4=3] [5=6] [8=6] [9=5] [15=6] 
Remove -11
[7]: [1=5] [3=7] [4=3] [5=6] [8=6] [9=5] [15=6] 
Final: 1 3 4 5 8 9 15 
[7]: [1=5] [3=7] [4=3] [5=6] [8=6] [9=5] [15=6] 
Max: 15 
Min: 1
Itertate throught the tree
1 3 4 5 8 9 15 
 */

