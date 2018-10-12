package cxz173430;

/**
 * @author 		Churong Zhang 
 * 				cxz173430
 * 				October 5 2018
 * 				Dr. Raghavachari
 * 				This class is for Intensive Track Assignment 4 problem 1 & 2.
 * 				Binary Search Tree
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<? super T>> implements Iterable<T> {
    static class Entry<T> {
        T element;
        Entry<T> left, right;
        /**
         * create an Entry with an element, left Entry and right Entry
         * @param x
         * @param left Entry
         * @param right Entry
         */
        public Entry(T x, Entry<T> left, Entry<T> right) {
            this.element = x;
	    this.left = left;
	    this.right = right;
        }
    }
    
    Entry<T> root;
    int size;
    int toArrayIndex;
    Stack<Entry<T>> stack;
    
    // for floor and ceiling value
    T floor;
    T X;
    T ceiling;
 
    /**
     * create a BinarySearchTree with size 0
     */
    public BinarySearchTree() {
	root = null;
	size = 0;
    }
    /**
     * find the element in the tree
     * @param x the element to find
     * @return the Entry that contain x or near x
     */
    private Entry<T> find(T x)
    {
    	stack = new Stack<>();
    	stack.push(null);
    	return find(root, x);
    }
    /**
     * find the element in the tree
     * @param ent the beginning entry
     * @param x the element to find
     * @return the Entry that contain x or near x
     */
    private Entry<T> find(Entry<T>ent, T x)
    {
    	if(ent == null || ent.element.compareTo(x) == 0)
    	{
    		return ent;
    	}
    	while(true)
    	{
    		if(x.compareTo(ent.element)< 0)
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
    		else if(x.compareTo(ent.element) > 0)
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
    
    /** x contained in tree?
     * @param x the element to check
     * @return true if it exists
     */
    public boolean contains(T x) {
    	Entry<T> ent = find(x);
    	if(ent == null || ent.element.compareTo(x) != 0)
    	{
    		return false;
    	}
    	
	return true;
    }

    /** Is there an element that is equal to x in the tree?
     *  Element in tree that is equal to x is returned, null otherwise.
     *  @param x the element to find
     *  @return the element that is equal to x or null
     */
    public T get(T x) {
    	Entry<T> ent = find(x);
    	if(ent == null || ent.element.compareTo(x) != 0)
    	{
    		return null;
    	}
    	else
    		return ent.element;
    }

    /** TO DO: Add x to tree. 
     *  If tree already contains a node with same key, add is rejected.
     *  @param x the element to add
     *  @returns true if x was added to tree.
     */
    public boolean add(T x) {
    	if(root == null)
    	{
    		root = new Entry<T>(x, null, null);
    		size++;
    		return true;
    	}
    	else
    	{
    		Entry<T> ent = find(x);
        	
        	if(ent.element.compareTo(x) == 0)
        	{
        		return false;
        	}
        	else if(ent.element.compareTo(x) > 0)
        	{
        		ent.left = new Entry<T>(x, null, null);
        	}
        	else
        	{
        		ent.right = new Entry<T>(x, null, null);
        	}
        	size ++;
        	return true;
    	}
    	
    }
    
    
    /** TO DO: Remove x from tree. 
     *  @param x the element to remove
     *  @return x if found, otherwise return null
     */
    public T remove(T x) {
    	Entry<T> temp = find(x);
    	if(temp == null || temp.element.compareTo(x) != 0)
    		return null;
    	T result = temp.element;
    	
    	if(temp.left == null || temp.right == null)
    	{
    		bypass(temp);
    	}
    	else //if(temp.left!= null && temp.right != null)
    	{
    		stack.push(temp);
    		Entry<T> minRight = find(temp.right, x);
    		temp.element = minRight.element;
    		bypass(minRight);
    		
    	}
    	size --;
	return result;
    }
    /**
     * skip the given entry
     * @param ent the entry to skip
     */
    public void bypass(Entry<T> ent)
    {
   	if(ent == root)
    {
   		if(ent.right != null)
   		{
   			stack.push(ent);
   			Entry<T> minRight = find(ent.right, ent.element);
    		ent.element = minRight.element;
    		
    		bypass(minRight);
   		}
   		else if(ent.left != null)
   		{
   			stack.push(ent);
   			Entry<T> minLeft = find(ent.left, ent.element);
    		ent.element = minLeft.element;
    		bypass(minLeft);
   		}
   		else
   		{
   			root = null;
   		}
    }
    else 
    	{
    		Entry<T> parent = stack.peek();
        	Entry<T> child = ent.left == null ? ent.right: ent.left;
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
     * @return smallest element in tree.
     */
    public T min() {
    	if(root == null)
    	{
    		return null;
    	}
    	Entry<T> temp = root;
    	while(temp.left != null)
    	{
    		temp = temp.left;
    	}
	return temp.element;
    }

    /** 
     * @return largest element in tree.
     */
    public T max() {
    	if(root == null)
    	{
    		return null;
    	}
    	Entry<T> temp = root;
    	while(temp.right != null)
    	{
    		temp = temp.right;
    	}
	return temp.element;
    }

    /**
     * Create an array with the elements using in-order traversal of tree
     * @return the array that has every element of the tree
     */
    public Comparable[] toArray() {
    Comparable[] arr = new Comparable[size];
	toArrayIndex = 0;
	inOrder(root, arr);
	/* write code to place elements in array here */
	return arr;
    }
    /**
     * Create an array with the elements using in-order traversal of tree
     * @param ent the start entry
     * @param arr the array to modify 
     */
    private void inOrder(Entry<T> ent, Comparable[] arr)
    {
    	if(ent != null)
    	{
    		inOrder(ent.left,arr);
    		arr[toArrayIndex] = ent.element;
    		toArrayIndex++;
    		inOrder(ent.right,arr);
    	}
    	
    }


// Start of Optional problem 2

    /** Optional problem 2: Iterate elements in sorted order of keys
	Solve this problem without creating an array using in-order traversal (toArray()).
     */
    /**
     * @return the iterator
     */
    public Iterator<T> iterator() {
	return new BSTIterator();
    }
    protected class BSTIterator implements Iterator<T>
    {
    	Entry<T> next;
    	Stack<Entry<T>> stack;
    	/**
    	 * construct the iterator
    	 */
    	public BSTIterator()
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
		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#hasNext()
		 */
		public boolean hasNext() {
			return next != null;
		}

		/*
		 * (non-Javadoc)
		 * @see java.util.Iterator#next()
		 */
		public T next() {
			// TODO Auto-generated method stub
			Entry<T> ret = next;
			
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
			
			
			return ret.element;
		}
    	
    }

    // Optional problem 2.  Find largest key that is no bigger than x.  Return null if there is no such key.
    /**
     * Find largest key that is no bigger than x
     * @param x the target we want 
     * @return null if there is no such key.
     */
    public T floor(T x) {
    	
    	if(X == null || X.compareTo(x) != 0)
    	{
    		getFloorCeiling(x);
    	}
        return floor;
    }

    // Optional problem 2.  Find smallest key that is no smaller than x.  Return null if there is no such key.
    /**
     * Find smallest key that is no smaller than x.
     * @param x the target we want
     * @return null if there is no such key.
     */
    public T ceiling(T x) {
    	if(X.compareTo(x) != 0)
    	{
    		getFloorCeiling(x);
    	}
        return ceiling;
    }
    /**
     * helper function to find the floor and ceiling of x
     * @param x the target
     */
    private void getFloorCeiling(T x)
    {
    	X = x;
    	Entry<T> temp = root;
    	while (temp != null)
    	{
    		if(temp.element.compareTo(X) == 0)
    		{
    			floor = temp.element;
    			ceiling = temp.element;
    			return;
    		}
    		else if(temp.element.compareTo(X) > 0)
    		{
    			if(ceiling == null)
    				ceiling = temp.element;
    			else if(ceiling.compareTo(X) > temp.element.compareTo(X))
    			{
    				ceiling = temp.element;
    			}
    			
    			temp = temp.left;
    				
    		}
    		else
    		{
    			if(floor == null)
    			{
    				floor = temp.element;
    			}
    			else if(X.compareTo(floor) > X.compareTo(temp.element))
    			{
    				floor = temp.element;
    			}
    			temp = temp.right;
    		}
    			
    	}
    }
    // Optional problem 2.  Find predecessor of x.  If x is not in the tree, return floor(x).  Return null if there is no such key.
    /**
     * Find predecessor of x
     * @param x the target 
     * @return the predecessor or the floor of x
     */
    public T predecessor(T x) {
    	if(size == 0)
    	{
    		return null;
    	}
    	Entry<T> temp = find(x);
    	if(temp.element.compareTo(x) == 0)
    	{
    		return stack.pop().element;
    	}
    	else
    	{
    		return floor(x);
    	}
       
    }

    // Optional problem 2.  Find successor of x.  If x is not in the tree, return ceiling(x).  Return null if there is no such key.
    /**
     * Find successor of x
     * @param x the target
     * @return the successor or ceiling(x)
     */
    public T successor(T x) {
    	if(size == 0)
    	{
    		return null;
    	}
    	Entry<T> temp = find(x);
    	if(temp.element.compareTo(x) == 0)
    	{
    		if(temp.left != null)
    		{
    			return temp.left.element;
    		}
    		else if(temp.right != null)
    		{
    			return temp.right.element;
    		}
    		else
    		{
    			return null;
    		}
    	}
    	else
    	{
    		return floor(x);
    	}
    }

// End of Optional problem 2

    public static void main(String[] args) {
	BinarySearchTree<Integer> t = new BinarySearchTree<>();
        Scanner in = new Scanner(System.in);
        while(in.hasNext()) {
            int x = in.nextInt();
            if(x > 0) {
                System.out.print("Add " + x + " : ");
                t.add(x);
                t.printTree();
            } else if(x < 0) {
                System.out.print("Remove " + x + " : ");
                t.remove(-x);
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
        System.out.println("Max: "+ t.max() + " \nMin: " + t.min());
        int te = 6;
        System.out.println("the floor of " + te + ": " + t.floor(te) + "\nthe ceiling of " + te+ ": " + t.ceiling(te));
        
        te = 3;
        System.out.println("the floor of " + te + ": " + t.floor(te) + "\nthe ceiling of " + te+ ": " + t.ceiling(te));
        
        te = 8;
        System.out.println("the predecessor of " + te + ": " + t.predecessor(te) + "\nthe successor of " + te+ ": " + t.successor(te));
        
        te = 5;
        System.out.println("the predecessor of " + te + ": " + t.predecessor(te) + "\nthe successor of " + te+ ": " + t.successor(te));
        
        System.out.println("Itertate throught the tree");
        Iterator<Integer> iter = t.iterator();
        while(iter.hasNext())
        {
        	System.out.print(iter.next()+ " ");
        }
    }


    public void printTree() {
	System.out.print("[" + size + "]");
	printTree(root);
	System.out.println();
    }

    // Inorder traversal of tree
    void printTree(Entry<T> node) {
	if(node != null) {
	    printTree(node.left);
	    System.out.print(" " + node.element);
	    printTree(node.right);
		}
    }

}
/*
Sample input:
1 3 5 7 9 2 4 6 8 10 -3 -6 -3 0

Output:
Add 1 : [1] 1
Add 3 : [2] 1 3
Add 5 : [3] 1 3 5
Add 7 : [4] 1 3 5 7
Add 9 : [5] 1 3 5 7 9
Add 2 : [6] 1 2 3 5 7 9
Add 4 : [7] 1 2 3 4 5 7 9
Add 6 : [8] 1 2 3 4 5 6 7 9
Add 8 : [9] 1 2 3 4 5 6 7 8 9
Add 10 : [10] 1 2 3 4 5 6 7 8 9 10
Remove -3 : [9] 1 2 4 5 6 7 8 9 10
Remove -6 : [8] 1 2 4 5 7 8 9 10
Remove -3 : [8] 1 2 4 5 7 8 9 10
Final: 1 2 4 5 7 8 9 10 

*/