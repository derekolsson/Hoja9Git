/*****************************************
TAKEN FROM:
	http://moxie.oswego.edu/~daniello/csc365/splay_tree/SplayBST.java
******************************************/



import java.util.Scanner;
import java.util.ArrayList;

public class SplayBST<E extends Comparable<E>> {
   Node root;
   int count;

   public SplayBST() {
      root = null;
      count = 0;
   }

   //print the tree in pre order
   public void printPreOrder(){
      System.out.println("      [Pre Order Tree]");
      printPreOrder(root,0,"root");
   }
   //print the tree in order
   public void printInOrder(){
      System.out.println("      [In Order Tree]");
      printInOrder(root,0,"root");
   }
   //print the tree in reverse order
   public void printReverseOrder(){
      System.out.println("      [Reverse Order Tree]");
      printReverseOrder(root,0,"root");
   }
   
   //print a tree graphically. Generally the tree looks pretty good, 
   //although sometimes you need to use your imagination to see how
   //it is organized. 
   public void printGraphically(){
      System.out.println("   [Graphical Tree Output]");
      ArrayList<Node> ordered = new ArrayList<Node>();
      
      //this method runs through the tree in order and sets each elements level variable.
      //it also passes the above arraylist to insert them in an ordered list so each can
      //be assigned a value based on it's position
      printGraphicallyHelper(root,0,"",ordered);
         
         
      //find number of elements, tell each element it's position, and find the maximum level of tree.
      int count = 0;
      int maxLevel = 0;
      for(Node n:ordered){
         count++;
         n.ith = count;
         //System.out.println(n.value+" at level "+n.level+" and is element number "+n.ith);
         if(n.level>maxLevel){
         maxLevel=n.level;
         }
      } 
      
      //print the tree based on their positions
      for(int i =0;i<maxLevel+1;i++){
         //the position of the last element printed per row
         //used to dictate how many spaces are printed between each 
         //element per line.
         int lastIth = 0;
         for(Node n:ordered){
            if(n.level==i){  
               //depending on what kind of child each element is (left or right), print 
               //a slash to represent that.
               if(n.childType.equals("l")){
                  printSpaces((n.ith-lastIth)/2);
                  System.out.print(n.value+"/");
               }else if(n.childType.equals("r")){
                  printSpaces((n.ith-lastIth-2)/2);
                  System.out.print("\\"+n.value);
               }else{
                  printSpaces((n.ith-lastIth)/2);
                  System.out.print(n.value);
               }
               lastIth=n.ith;
            }
         }
         System.out.println("");
      }      
      
      
      
   }
   
   //print i number of spaces to the console.
   public void printSpaces(int i){
      for(int j=0;j<i;j++){
         System.out.print(" ");
      }
   }
   
    //assumes no duplicates
   public void add(Comparable x) {
	   root = splayInsert(root,x); count++;
   }

    // moves node containing x to the root and returns whether it was found or not.
    //also displays results
   public boolean search(Comparable x) {
      root = splaySearch(root,x);
      return x==root.value;
   }

   
   //_-----------------------------_//
   //***INSERTION (SPLAY)***//
   //_-----------------------------_//
   
   // 4 cases for two levels down in the search path from the root:
   // left-left:  rotate right at root twice
   // left-right: rotate left at left child; rotate right at root
   // right-right: rotate left at root twice
   // right-left: rltate right at right child, rotate left at root
    
   //Refinement 1:
   //Made some small minor changes and tree worked fine when elements were inserted in the proper order. (IE, insert(1), insert(2), insert(3)...etc.). 
   //However, when items were placed out of order the SplayBST program crashed.
   
   //Refinement 2:
   //Made tree so that it rotated at root before rotating at new child. This is how a proper rotation should be done. Beforehand it would rotate childten
   //first
   
   Node splayInsert(Node h, Comparable x) {
      if (h==null) return new Node(x);
      
      //if value being inserted is less than node's value
      if (x.compareTo(h.value)<0) {

         //simple case 1, insert at left child because left child does not exist and value being inserted
         //is less than current node.
   	   if (h.left == null) {
            h.left = new Node(x);
            //rotate the newly inserted node up to root
            return rotateRight(h);
   	   }
         
         //complex case 1, left left child
         //if value being inserted is less than left child 
         //then insert at  left child of left node
   	   if (x.compareTo(h.left.value)<0) {
            h.left.left = splayInsert(h.left.left,x);
            h = rotateRight(h);
            h = rotateRight(h);
         //complex Case 2
         //else if value is greater than left child (but still less than root), insert at left child's right child. 
   	   }else {
            h.left.right = splayInsert(h.left.right,x);
            h.left = rotateLeft(h.left);
            h = rotateRight(h);
   	   }
         //rotate new root at left child.
   	   return h;
      //if value being inserted is greater than node's value
      }else{ //x.compareTo(h.value)>0  
         //simple case 2, if right child is null, insert here then rotate
   	   if (h.right == null) {
            h.right = new Node(x);
            //rotate node so new value is root
            return rotateLeft(h);
   	   }

         //complex case 3, value is greater than right child's value
   	   if (x.compareTo(h.right.value)>0) {
            h.right.right = splayInsert(h.right.right,x);
            h = rotateLeft(h);
            h = rotateLeft(h);
         //complex case 4, value is greater than right child's left child
   	   }else {
            h.right.left = splayInsert(h.right.left,x);
            //then rotate the newly inserted node to the left, making it root of left child's sub tree
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
         }
         //rotate new right child
         return h;
      }
   }

   
   
   //basically above method but checks value and children to see if they are equal to 
   //value being searched and returns them if they are.
   Node splaySearch(Node h, Comparable x) {
      //if equal return
      if (x.compareTo(h.value)==0) return h;
      
      //if value being Searched is less than node's value
      if (x.compareTo(h.value)<0) {
      
         //if equal return
         if(x.compareTo(h.left.value)==0){
            return h = rotateRight(h);
         }
         
   	   if (x.compareTo(h.left.value)<0) {
            h.left.left = splaySearch(h.left.left,x);
            h = rotateRight(h);
            h = rotateRight(h);
         //complex Case 2
         //else if value is greater than left child (but still less than root), Search at left child's right child. 
   	   }else {
            h.left.right = splaySearch(h.left.right,x);
            h.left = rotateLeft(h.left);
            h = rotateRight(h);
   	   }
         //rotate new root at left child.
   	   return h;
      //if value being Searched is greater than node's value
      }else{ //x.compareTo(h.value)>0  
         //if equal return
         if(x.compareTo(h.right.value)==0){
            return h = rotateLeft(h);
   	   }

         //complex case 3, value is greater than right child's value
   	   if (x.compareTo(h.right.value)>0) {
            h.right.right = splaySearch(h.right.right,x);
            h = rotateLeft(h);
            h = rotateLeft(h);
         //complex case 4, value is greater than right child's left child
   	   }else {
            h.right.left = splaySearch(h.right.left,x);
            h.right = rotateRight(h.right);
            h = rotateLeft(h);
         }
         //rotate new right child
         return h;
      }
   }


   
   //rotate the current node to the left
   Node rotateLeft(Node h) {
      //grab right node
      Node x = h.right;
      //if left node of grabbed node is not null, 
      //set right sub tree to the left node of grabbed node
		if(x.left!=null){
			h.right = x.left;
		}else{
			h.right=null;
		}
      //set left child of grabbed node to rotated node
      x.left = h;
      return x;
   }
    
   //rotate the current node to the right
   Node rotateRight(Node h) {
      //grab left node
      Node x = h.left;
      //if right node of grabbed node is not null, 
      //set left sub tree to the right node of grabbed node
		if(x.right!=null){
			h.left = x.right;
		}else{
			h.left=null;
		}
      //set right child of grabbed node to rotated node
      x.right = h;
      return x;
   }
    
    
   class Node {
      int ith=0;
      int level=0;
   	Node left;
   	Node right;
   	Comparable value;
      Comparable childType="";
	
      //instantiate a new node with null children and a value set via input parameter
   	public Node(Comparable x) { 
   	   left = null;
   	   right = null;
   	   value=x;
   	}
   }
    
   //recursive method to print tree in order
   public void printInOrder(Node n, int i, Comparable side){
      if(n.left!=null){
         printInOrder(n.left,i+1,"left child of value " + n.value);
      }
      System.out.println(n.value +" @ level " + i + " as "+ side);
      if(n.right!=null){
         printInOrder(n.right,i+1,"right child of value " + n.value);
      }
   }
   
   //helper method for print graphically.
   //this method runs through the tree in order and sets each elements level variable
   //and child type.
   //it also passes the above arraylist to insert them in order so each can
   //be assigned a value based on it's position later
   public void printGraphicallyHelper(Node n, int i, Comparable t, ArrayList<Node> ordered){
      n.level=i; 
      n.childType=t;
      if(n.left!=null){
        printGraphicallyHelper(n.left,i+1,"l",ordered);
      }
      ordered.add(n);
      if(n.right!=null){
         printGraphicallyHelper(n.right,i+1,"r",ordered);
      }
   }
   
   
   //recursive method to print tree in preorder
   public void printPreOrder(Node n, int i, Comparable side){
      System.out.println(n.value +" @ level " + i + " as "+ side);
      if(n.left!=null){
         printPreOrder(n.left,i+1,"left child of value " + n.value);
      }
      if(n.right!=null){
         printPreOrder(n.right,i+1,"right child of value " + n.value);
      }
      
   }
   
   //recursive method to print tree in reverse order
   public void printReverseOrder(Node n, int i, Comparable side){
      if(n.right!=null){
         printReverseOrder(n.right,i+1,"right chil of value " + n.value);
      }
      System.out.println(n.value +" @ level " + i + " as "+ side);
      if(n.left!=null){
         printReverseOrder(n.left,i+1,"left child of value " + n.value);
      }
      
   }

}
