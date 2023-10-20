package AVLTreeADT;

public class AVLNodeADT<T extends Comparable> {

	public AVLNodeADT<T> dad,left,right;
	public int bf; // (balance factor)
	public T elem;
	
	public AVLNodeADT() {
		dad=null;
		left=null;
		right=null;
		elem = null;
		bf=0;
	}
	
	public AVLNodeADT(T elem) {
		dad=null;
		left=null;
		right=null;
		this.elem = elem;	
	}

	public AVLNodeADT<T> getDad() {
		return dad;
	}

	public void setDad(AVLNodeADT<T> dad) {
		this.dad = dad;
	}

	public AVLNodeADT<T> getLeft() {
		return left;
	}

	public void setLeft(AVLNodeADT<T> left) {
		this.left = left;
	}

	public AVLNodeADT<T> getRight() {
		return right;
	}

	public void setRight(AVLNodeADT<T> right) {
		this.right = right;
	}

	public int getBf() {
		return bf;
	}

	public void setBf(int bf) {
		this.bf = bf;
	}

	public T getElem() {
		return elem;
	}

	public void setElem(T elem) {
		this.elem = elem;
	}
	
	@SuppressWarnings("unchecked")
	public void hangTo(AVLNodeADT<T> newDad) {
		this.setDad(newDad);
		if(newDad.getElem().compareTo(this.getElem())>0)
			newDad.setLeft(this);
		else
			newDad.setRight(this);
	}
	
	
	//This method returns true if THIS node is the right son of dad
	//Otherwise it returns false (if it is the left son of dad)
	public boolean whichSonAmIfrom(AVLNodeADT<T> dad) {
		return dad.getRight()!=null && dad.getRight().equals(this);
	}
	
	public AVLNodeADT<T> rightRight() {  
		AVLNodeADT<T> dad = getDad(); //this is the current dad of THIS node
		AVLNodeADT<T> top = getRight(); //"top" is the node that will be at the current position of this node
		if(top.getLeft()!=null) {  // if top has a son, we connect it to THIS
			setRight(top.getLeft());
			top.getLeft().setDad(this);
		}
		else 
			setRight(null); // we establish right son as the null left son of top

		top.setLeft(this); // here, top is raised to the "top"
		setDad(top);	// currentNode is the new left son of top
		
		if(dad!=null) 
			top.hangTo(dad);
		else
			top.setDad(null);
		return top; //returns the node that will be at the position of this node
	}
	
	
	public AVLNodeADT<T> leftLeft() {   
		AVLNodeADT<T> dad = getDad(); //this is the current dad of THIS node
		AVLNodeADT<T> top = getLeft(); //"top" is the node that will be at the current position of this node
		if(top.getRight()!=null) {  // if top has a son, we connect it to THIS
			setLeft(top.getRight());
			top.getRight().setDad(this);
		}
		else 
			setLeft(null); // we establish left son as the null left son of top

		top.setRight(this); // here, top is raised to the "top"
		setDad(top);	// currentNode is the new right son of top
		
		if(dad!=null) 
			top.hangTo(dad);
		
		else
			top.setDad(null);
		return top;
	}
	
	public AVLNodeADT<T> leftRight() { 
		left.rightRight(); //This does a right-right rotation in the left son
		return leftLeft(); //then, we left-left rotate THIS
	}
	
	public AVLNodeADT<T> rightLeft () {
		right.leftLeft();  //This does a left-left rotation in the right son
		return rightRight(); // then, we right-right rotate THIS
	}
	
	public AVLNodeADT<T> rotate () { //This method executes the appropriate rotation in each case, considering the balance factors,
		// and updates as needed
		if (bf == -2 && left.bf == 0) {
			setBf(bf+1);
			left.setBf(left.bf+1);
			return leftLeft();
		}
		else if(bf==-2 && left.bf == -1) {
			setBf(0);
			left.setBf(0);
			return leftLeft();
		}
		else if (bf == 2 && right.bf ==0) {
			setBf(bf-1);
			right.setBf(right.bf-1);
			return rightRight();
		}
		else if (bf == 2 && right.bf == 1) {
			setBf(0);
			right.setBf(0);
			return rightRight();
		}
		else if (bf == -2 && left.bf == 1) { //Left-Right rotation
			if(left.right.bf==1) {
				left.right.bf=0;
				bf=0;
				left.bf=-1;
				return leftRight();
			}
			else if (left.right.bf == -1) {
				left.right.bf=0;
				left.bf=0;
				bf=1;
				return leftRight();
			}
			else {
				bf=0;
				left.bf=0;
				left.right.bf=0;
				return leftRight();
			}
			
		}
		else //if (bf == 2 && right.bf == -1) 
		{  // Right- Left rotation
			if(right.left.bf==-1) {
				bf=0;
				right.left.bf =0;
				right.bf =1;
				return rightLeft();
			}
			else if (right.left.bf == 1) {
				right.left.bf=0;
				right.bf=0;
				bf=-1;
				return rightLeft();
			}
			else {
				bf=0;
				right.bf=0;
				right.left.bf=0;
				return rightLeft();
			}
		}
	}
	
	public String toString() {
		return "{Elem: " +elem.toString() + ", BF: "+ bf +"} ";
	}
	
	
	
	
}










