package com.fever.feverapp.objects;

import java.io.Serializable;
import java.util.Date;

public class Friends implements Serializable {

    public User user;
    public User friend;
    public Date created;
    private double chemistry;
    private int chatsBetween;

    public Friends(User user, User friend, double chemistry) {
        this.user = user;
        this.friend = friend;
        // make sure thisis more than 20 cant be 0.6 or sm
        this.chemistry = chemistry;
        this.created = new Date();
        this.chatsBetween = 0;
    }
    //********************************************************************************************//
    /* Getters and Setters
     */
    //Return the number of chats between them
    public int getChatsBetween() {
        return chatsBetween;
    }
    //Set the number of chats between them
    public void setChatsBetween(int chatsBetween) {
        this.chatsBetween = chatsBetween;
    }
    //Gets the  date this chat was created
    public Date getCreated() {
        return created;
    }
    //Returns the chemistry of this chat
    public double getChemistry() {
        return chemistry;
    }
    // Sets the chemistry of this chat
    public void setChemistry(double chemistry) {
        this.chemistry = chemistry;
    }

    //********************************************************************************************//
    /* Helper Methods
     */
    // Checks is two users are this friend group.
    public boolean us(User u1, User u2){
        if (this.user.equals(u1) & this.friend.equals(u2)){
            return true;
        }
        if(this.user.equals(u2) & this.friend.equals(u1)){
            return true;
        }
        return false;
    }
     public boolean equals(Object E){
        if(E instanceof Friends){
            if(this.user.equals(((Friends) E).user) &  this.friend.equals(((Friends) E).friend)){
                return true;
            }
            if(this.user.equals(((Friends) E).friend) &  this.friend.equals(((Friends) E).user)){
                return true;
            }
        }
        return false;
     }


}
