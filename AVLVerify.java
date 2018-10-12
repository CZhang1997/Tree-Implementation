package cxz173430;
/**
 * @author 		Churong Zhang 
 * 				cxz173430
 * 				October 5 2018
 * 				Dr. Raghavachari
 * 				Assignment 7
 * 				AVL Tree verify
 */
public class AVLVerify{

	class BST<T> {
	    class Entry {
		T element;
		Entry left, right;
	    }

	    Entry root;  // root node of BST
	    int size;    // number of elements in tree
	
	}

	class AVL<T> extends BST<T> {
	    class Entry extends BST.Entry {
		int height;
	    }
	    
	 // T(n) = O(n)
 boolean verify() { 

	    	return verify((AVL.Entry)root);
	    }
		// RT: wrost case at case three, 16 + T(n.left) + T(n.right). n.left + n.right = size of the tree
 		// T(n) = O(n)
 boolean verify(Entry ent) 
{  
	 if(ent != null)										
	 {
	    	// when the entry does not has any child
	   	if(ent.left == null && ent.right == null)				// case 1 total: 4 steps
	   		{															// 3 steps
	   			if(ent.height > 1)										// 1 step
	   			{
    				return false;										// 1 step
	    		}
	   		
	    	}
 		else if(ent.left != null || ent.right != null)		// case 2 total: 8 + T(n) steps where n = size of the left tree or right tree of ent
	    {	// when the Entry has one child							// 3 steps
 			if(ent.left != null)									// 1 step
			{
  				if(ent.left.element.compareTo(ent.element)			// 1 step
  						>= 0)
   				{
					return false;	    							//	1 step
				}
    		}
    		else
    		{
    			if(ent.right.element.compareTo(ent.element)			// 1 step
    						<= 0)
    			{
    				return false;									// 1 step
    			}
    		}
 				boolean ver = verify(ent.left != null				// T(n) step, n = size of the left tree or right tree of ent
	 					? (Entry)ent.left : (Entry)ent.right);		
	   			if(!ver || ent.height > 1)							// 1 step
    			{
	   				return false;									// 1 step
	   			}
	   			
	    	}
	    else		//when the entry has two children				// case 3 total: 16 + T(n.left) + T(n.right)
	    {	// check if left < ent < right
	    	if(ent.left.element.compareTo(ent.element) >= 0 			// 4 steps
	   				|| ent.right.element.compareTo(ent.element) <= 0)
	   		{
	    		return false;											// 1 step
	   		}
	    	
	 		boolean verLeft = verify((Entry)ent.left); 					// T(n) where n is the size of the left tree
    		if(verLeft)													// 1 step
	   		{
    			boolean verRight = verify((Entry)ent.right);			// T(n) where n is the size of the Right tree
    			if(verRight)											// 1 step
    			{
    				if(Math.abs(((AVL.Entry)ent.left).height - 			// 4 steps
        					((AVL.Entry)ent.right).height) <= 1)
    	   			{
    	   				int max = Math.max(((AVL.Entry)ent.left).height,	// 1 step
    	   									((AVL.Entry)ent.right).height);
    	   				if(ent.height != max +1)							// 3 step
    	   				{
    	   					return false;									// 1 step
    	   				}
       				}
        			else
        				return false;										// 1 step
    			}
	   		}
	   		else
	   			return false;												// 1 step
	   		
	   	}
	  }
	    	return true;
}
	    
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
