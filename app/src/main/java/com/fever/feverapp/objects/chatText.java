package com.fever.feverapp.objects;

import java.io.Serializable;
import java.util.Date;

/* A new piece of text that will be added to a chats text history.
 */
public class chatText implements Serializable{
    private String text;
    private User owner;
    private Date created;

    public chatText(String text, User owner) {
        this.text = text;
        this.owner = owner;
        this.created = new Date();
    }

    //*******************************************************************************************//
    /* Getters and Setters.
     */
    // Return the text
    public String getText() {
        return text;
    }
    // set the text.
    public void setText(String text) {
        this.text = text;
    }
    // gets the owner of this chat.
    public User getOwner() {
        return owner;
    }
    // get the date the text was created
    public Date getCreated() {
        return created;
    }

    //****************************************************************************************//
    /* Methods used by class
     */
    //check if chatText is equal
    public boolean equals(chatText obj) {
        if(!this.getText().equals(obj.getText())){
            return false;
        }
        if(!this.getOwner().equals(obj.getOwner())){
            return false;
        }
        if(!this.getCreated().equals(obj.getCreated())){
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return this.getText();
    }
}
