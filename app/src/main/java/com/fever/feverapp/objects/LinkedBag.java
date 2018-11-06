package com.fever.feverapp.objects;


import java.io.Serializable;
import java.util.ArrayList;

public class LinkedBag<E> implements Serializable
{
    //Head node and number of nodes in bag
    private Node<E> head;
    private int numNodes;

    //Constructor
    public LinkedBag(){
        head = null;
        numNodes = 0;
    }
    //********************************************************************************************//
    /*Getters and Setters.
     */
    //Returns the number of nodes in the bag
    public int getSize() {
        return numNodes;
    }
    // Increment the size of the LinkedBag
    public void increment(){
        this.numNodes++;
    }
    //Returns the node that is at the head of the linked list
    public Node<E> getListStart() {
        return head;
    }


    //********************************************************************************************//
    /* Methods Needed by the class.
     */
    //Adds a node to the beginning of the list
    public void add(E element) {
        head = new Node(element, head); //Creates a new node pointing to the head and sets the head of the linked bag to the new Node
        numNodes++;        //Increments Node counter
    }
    // pops the last element off the liked list
    public Node<E> pop() {
        Node<E> curr = this.head;
        Node<E> prev = null;
        //if list is empty
        if (curr == null){
            return null;
        }
        // if list is one item
        if (curr.next == null ){
            this.head = null;
            return curr;
        }
        //finding the last element
        while(curr != null){
            prev = curr;
            curr = curr.next;
        }
        prev.next = null;
        return curr;
    }
    //Checks if Node [target] exists within the bag
    public boolean exists(E target) {
        if(head == null){
            return false;
        }
        Node<E> cursor = head;      //Sets temporary Node [cursor] to the same value and pointer as head
        while(cursor != null) {    //Loops until the next Node contains no value
            if(cursor.getData().equals(target))     //Checks if current Node is [target] and returns true if true
                return true;
            cursor=cursor.getNext();        //Cursor continues down linked list
        }
        return false;       //Returns false if cursor goes through entire linked list and [target] isn't found
    }

    //Checks if Node [target] exists within the bag and removes the first occurence of it
    public boolean remove(E target) {
        if(head == null)          //Returns false if bag is empty
            return false;
        Node<E> cursor = head;
        Node<E> prev = null; //Sets temporary Node [cursor] to the same value and pointer as head
        while(cursor != null){ //Cursor continues down linked list
            if(cursor.getData().equals(target))   //If the next node's data is [target]
            {   //if prev if null make head
                if (prev == null){
                    this.head = cursor.getNext();
                }else {
                    prev.setNext(cursor.getNext()); //Sets current Node's link to the next Node's link, by passing the next Node
                }
                numNodes--;            //Decrements Node counter
                return true;            //Returns true, found [target]
            }
            prev = cursor;
            cursor = cursor.getNext();
        }
        return false;           //Returns false, [target] not found
    }

    public ArrayList<E> toArray(){
        ArrayList<E> result = new ArrayList<>();

        Node<E> head = this.getListStart();
        while( head != null){
            result.add(head.getData());
            head = head.getNext();
        }
        return result;
    }
}