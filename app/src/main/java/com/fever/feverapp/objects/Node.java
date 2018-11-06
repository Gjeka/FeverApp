package com.fever.feverapp.objects;

import java.io.Serializable;

/* Generic Node
 */
public class Node<E> implements Serializable{
        private E data;
        public Node<E> next;

        public Node(E var, Node<E> nxt) {
            this.data = var;
            this.next = nxt;
        }
        public Node(E var){
            this.data = var;
            this.next = null;
        }
        //****************************************************************************************//
        /* Getters and Setters.
           Equals method.
         */
        // Sets the next node
        protected void setNext(Node<E> var){
            this.next = var;
        }
        // Returns the next node
        public Node<E> getNext() {
            return this.next;
        }
        // Sets the data of the node
        protected void setData(E var){
            this.data = var;
        }
        //Gets the data of the node
        public E getData(){
            return this.data;
        }
        //Checks if two nodes are equal.
        public boolean hasNext(){
            if(this.getNext() != null){
                return true;
            }
            return false;
        }

        @Override
        public boolean equals(Object var){
            if (this.getData().equals(var)) {
                return true;
            }
            return false;
        }
}

