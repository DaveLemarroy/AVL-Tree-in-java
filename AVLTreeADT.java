package AVLTreeADT;

import java.util.ArrayList;
import java.util.Iterator;
import Queue.CArrayQueue;

public class AVLTreeADT<T extends Comparable> {

	AVLNodeADT<T> root;
	int count;
	
	public AVLTreeADT() {
	}
	
	public ArrayList<T> inOrder() {
		ArrayList<T> list = new ArrayList();
		inOrder(root,list);
		return list;
	}
	
	public void inOrder (AVLNodeADT<T> current, ArrayList<T> list) {
		if(current == null)
			return;
		inOrder(current.getLeft(),list);
		list.add(current.getElem());
		inOrder(current.getRight(),list);	
	}
	
	public ArrayList<AVLNodeADT<T>> inOrderNodes() {
		ArrayList<AVLNodeADT<T>> list = new ArrayList();
		inOrderNodes(root,list);
		return list;
	}
	
	public void inOrderNodes (AVLNodeADT<T> current, ArrayList<AVLNodeADT<T>> list) {
		if(current == null)
			return;
		inOrderNodes(current.getLeft(),list);
		list.add(current);
		inOrderNodes(current.getRight(),list);	
	}
	
	public ArrayList<T> levelOrder(){
		ArrayList<T> result = new ArrayList();
		CArrayQueue<AVLNodeADT<T>> queue = new CArrayQueue();
		queue.enqueue(root);
		while(!queue.isEmpty()) {
			AVLNodeADT<T> current = queue.dequeue();
			if(current.getLeft()!=null)
				queue.enqueue(current.getLeft());
			if(current.getRight()!=null)
				queue.enqueue(current.getRight());
			result.add(current.getElem());
		}
		return result;
	}
	
	public ArrayList<AVLNodeADT<T>> levelOrderNodes(){
		ArrayList<AVLNodeADT<T>> result = new ArrayList();
		CArrayQueue<AVLNodeADT<T>> queue = new CArrayQueue();
		queue.enqueue(root);
		while(!queue.isEmpty()) {
			AVLNodeADT<T> current = queue.dequeue();
			if(current.getLeft()!=null)
				queue.enqueue(current.getLeft());
			if(current.getRight()!=null)
				queue.enqueue(current.getRight());
			result.add(current);
		}
		return result;
	}
	
	
	
	
	@SuppressWarnings("unchecked")
	
	public AVLNodeADT<T> search(T elem) {
		AVLNodeADT<T> current = root;
		while(current != null) {
			if(current.getElem().equals(elem))
				break;
			else if(current.getElem().compareTo(elem)>0)
				current = current.getLeft();
			else
				current = current.getRight();
		}
		return current;
	}
	
	
	
	//New method
	
	// Public method to insert an element into the tree (A new node containing the element will be created)
	public void insertRv(T elem) {
		count++;
		insertRv(root,new AVLNodeADT(elem));
	}
	
	
	// An internal recursive method to insert a new node
	private  void insertRv (AVLNodeADT<T> current, AVLNodeADT<T> newNode){
        if(root==null){
            root = newNode;
            return;
        }
        if(newNode.getElem().compareTo(current.getElem())<0)    
            if(current.getLeft()==null) {
                newNode.hangTo(current);
                balanceInsertion(current,false);
            }
            else
                insertRv(current.getLeft(),newNode);
        else{
            if (current.getRight()==null) {
            newNode.hangTo(current);
            
            balanceInsertion(current,true);
            }
            else
                insertRv(current.getRight(),newNode);
        }
        
    }
	
	// This method will balance the tree as we insert a node, 
	// traversing it from the inserted node to the root
	private void balanceInsertion(AVLNodeADT<T> node, boolean add) {
		if(add)
			node.setBf(node.getBf()+1);
		else
			node.setBf(node.getBf()-1);
		
		if(node.getBf() == 2 || node.getBf() == -2) {
			if(node.equals(root))
					root = node.rotate();
			else
			node.rotate();
			return;
		}
		
		if (node.getBf() == 0)
			return;
		
		else if(! node.equals(root))
			balanceInsertion(node.getDad(),node.whichSonAmIfrom(node.getDad()));
	}
	
	
	
	
	 public void delete (T elem){
         AVLNodeADT<T> current = search (elem);
         if(current==null)
             return;
         
         count--;
         
         // IF THE NODE IS A LEAF 
         if(current.getLeft() == null && current.getRight()==null){
             //IF THAT LEAF IS THE ROOT OF THIS TREE:
             if(current.equals(root))
                 root=null;
             else{
                 AVLNodeADT<T> parent = current.getDad();
                 if(parent.getLeft()!= null && parent.getLeft().equals(current)) {
                     parent.setLeft(null);
                     balanceDeletion(parent,false);
                 }
                 else {
                     parent.setRight(null);
                     balanceDeletion(parent,true);
                 }
                 current.setDad(null);
             }       
         }
         //CASE OF ONLY ONE CHILDREN:
         
         // IF THE ONLY CHILDREN IS ON THE LEFT
         else if(current.getLeft()!=null && current.getRight()==null){
             if(current.equals(root)) {
                 root = current.getLeft();
                 root.setBf(0);
             }
             else{
                 AVLNodeADT<T> parent = current.getDad();
                 if(parent.getLeft() != null && parent.getLeft().equals(current)) {
                     parent.setLeft(current.getLeft());
                     balanceDeletion (parent,false);
                 }
                 else{
                     parent.setRight(current.getLeft());
                     balanceDeletion (parent,true);
                 }
             }  
         }
         
      // IF THE ONLY CHILDREN IS ON THE RIGHT
         else if(current.getRight()!=null && current.getLeft()==null){
             if(current.equals(root)) {
                 root = current.getRight();
                 root.setBf(0);
             }
             else{
                 AVLNodeADT<T> parent = current.getDad();
                 if(parent.getLeft() != null && parent.getLeft().equals(current)) {
                     parent.setLeft(current.getRight());
                     balanceDeletion(parent,false);
                 }
                 else{
                     parent.setRight(current.getRight());
                     current.getRight().setDad(parent);
                     current=null;
                     balanceDeletion(parent,true);
                 }
             }
         }
         
         // CASE OF TWO CHILDREN
         else{
             AVLNodeADT<T> suc = inOrderSuccessor(current);
             T e = suc.getElem();
             deleteNode(suc);
             current.setElem(e);
         }   
	 }
	 
	 
	 
	 
	 // An auxiliary method to delete a node receiving it as a parameter, to avoid confusions 
	 // between repeated node as we use inorderSuccessor
	 public void deleteNode (AVLNodeADT<T> node){
         AVLNodeADT<T> current = node;
         if(current==null)
             return;
         
         // This node has to be a leaf
                 AVLNodeADT<T> parent = current.getDad();
                 parent.setLeft(null);
                 balanceDeletion(parent,false);
                 current.setDad(null);
     }
	 
	 
	 
	 
	 
	 
	 // An auxiliary method to balance the tree from a node deleted, to the root
	 private void balanceDeletion(AVLNodeADT<T> node, boolean add) {
			if(add)
				node.setBf(node.getBf()-1);
			else
				node.setBf(node.getBf()+1);
			
			if(node.getBf() == 2 || node.getBf() == -2) {
				if(node.equals(root)) {
						root = root.rotate();
						return;
				}
				else 
				{
				AVLNodeADT<T> top =node.rotate();
				balanceDeletion (top.getDad(),top.whichSonAmIfrom(top.getDad()));
				}
			}
			
			if (node.getBf() == 1 || node.getBf() == -1)
				return;
			
			else if(! node.equals(root))
				balanceDeletion(node.getDad(),node.whichSonAmIfrom(node.getDad()));
		}
	 
	 
	 // An auxiliary method to help delete a node with two children, 
	 // returning an AVLNode to make the process easier
	 public AVLNodeADT<T> inOrderSuccessor(AVLNodeADT<T> node){
		 ArrayList<AVLNodeADT<T>> list = inOrderNodes();
		 AVLNodeADT<T> successor = null;
		 for(int i=0; i<list.size(); i++) 
			 if(node.equals(list.get(i))){
				 successor = list.get(i+1);
				 break;
			 }
		 return successor;
	 }	 
	 
	
	
	public static void main (String[] args) {
		AVLTreeADT<Integer> myAVL = new AVLTreeADT();
		myAVL.insertRv(100);
		myAVL.insertRv(300);
		myAVL.insertRv(400);
		myAVL.insertRv(50);
		myAVL.insertRv(200);
		myAVL.insertRv(250);
		myAVL.insertRv(75);
		myAVL.insertRv(350);
		myAVL.insertRv(500);
		myAVL.insertRv(375);
		//77myAVL.delete(5);
		//myAVL.delete(1);
		
		
		System.out.println(myAVL.levelOrderNodes());
		myAVL.delete(500);
		myAVL.delete(400);
		myAVL.delete(200);
		System.out.println(myAVL.levelOrderNodes());
		
	
	}
	
	
	
}

























