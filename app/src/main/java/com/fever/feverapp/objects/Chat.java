package com.fever.feverapp.objects;


import java.io.Serializable;
import java.util.Date;
import java.util.Random;

public class Chat implements Serializable {
    // properties
    private String UID;
    public String name;
    private LinkedBag<User> involved = new LinkedBag<>();
    public boolean action_need;
    public double chemistry;
    public Date date_created;
    public Date last_modified;
    public Node<User> turn;
    public Date turnStart;
    private LinkedBag<chatText> chatHistory;
    private LinkedBag<chatSongRating> songRatingHistory;
    private LinkedBag<ratingRequest> activeRatingRequests;

    //*******************************************************************************************//
    // Constructor.
    public Chat(String name, User[] members){
        this.UID =  name+String.valueOf(members.length);
        this.name = name;
        this.chemistry = 50.0;
        this.action_need = false;
        this.date_created = new Date();
        this.last_modified = new Date();
        this.chatHistory = new LinkedBag<>();
        this.songRatingHistory = new LinkedBag<>();
        this.activeRatingRequests = new LinkedBag<>();
        updateMembers(members);
        start(members);
    }


    //********************************************************************************************//
    /* Getters and Setters for the Chat
       Equals method for the class.
     */
    //get the name of the chat
    public String getName() {
        return name;
    }
    // set the name of the chat
    public void setName(String name) {
        this.name = name;
    }
    // get the list of users involved
    public LinkedBag<User> getInvolved() {
        return involved;
    }
    // return the if action is needed.
    public boolean isAction_need() {
        return action_need;
    }
    // set the bool value of action need
    public void setAction_need(boolean action_need) {
        this.action_need = action_need;
    }
    // return the chemistry of chat
    public double getChemistry() {
        return chemistry;
    }
    // get the date of creation
    public Date getDate_created() {
        return date_created;
    }
    // time current turn started
    public Date getTurnStart() {
        return turnStart;
    }
    //set chemistry of chat
    public void setChemistry(double chemistry) {
        this.chemistry = chemistry;
    }
    public LinkedBag<chatText> getChatHistory() {
        return chatHistory;
    }
    public LinkedBag<chatSongRating> getSongRatingHistory() {
        return songRatingHistory;
    }
    public LinkedBag<ratingRequest> getActiveRatingRequests() {
        return activeRatingRequests;
    }
    //get unique id of chat
    public String getUID() {
        return UID;
    }
    // .equals method for the chats
    public boolean equals(Chat obj) {
        return this.getUID().equals(obj.getUID());
    }

    //********************************************************************************************//
    /* Methods that check for existing objects
       Methods that add to existing history Lists
     */

    //check is a user is involved in a chat
    public boolean isInvolved(User user){
        return this.getInvolved().exists(user);
    }
    // add a user to the chat
    public boolean addInvolved(User user) {
        if(this.getInvolved().exists(user)){
            System.err.print("User already involved in chat: Cannot add \n");
            return false;
        }
        this.last_modified = new Date();
        this.getInvolved().add(user);
        user.addChatBymodified(this);
        return true;
    }

    // add chat text to chat history
    public void addChatText(chatText ctext){
        this.getChatHistory().add(ctext);
        this.last_modified = new Date();
    }
    //remove chat text from history
    public boolean removeChatText(chatText ctext){
        this.last_modified = new Date();
        return this.getChatHistory().remove(ctext);
    }
    // Add rating requests to active rating requests
    public void addActiveRatingRequests(ratingRequest request) {
        this.activeRatingRequests.add(request);
    }
    //
    public boolean addSongRating(chatSongRating pick){
        this.last_modified = new Date();
        if (!this.getSongRatingHistory().exists(pick)){
            this.getSongRatingHistory().add(pick);
            // after every song rated the overall chemistry of the chat is evaluated.
            this.evalChemistry();
            //After every five songs added, give every user involved a weight boost based on the rating of the chat.
            this.updateWeights();
            return true;
        }
        return false;
    }

   //*********************************************************************************************//
    /* Methods to assist in playing a Fever game
     */
    public void evalChemistry(){
        Node<chatSongRating> songRating = this.getSongRatingHistory().getListStart();
        while(songRating != null){
            if (!songRating.getData().isApplied() & songRating.getData().isComplete()) {
                double finalRating = songRating.getData().getFinalrating();
                double tmp = (finalRating / 100) * 2;
                User createdBy = songRating.getData().getCreator();
                //adding or removing  from chemistry of the chat based on final rating of song.
                if (finalRating < this.getChemistry()) {
                    this.setChemistry(this.getChemistry() - tmp);
                }
                if (finalRating >= this.getChemistry()) {
                    this.setChemistry(this.getChemistry() + tmp);
                }
                //adding or removing from creators rank based on final rating of song.
                createdBy.addtoRank(finalRating, ((finalRating / 100) * 0.5));
                songRating.getData().setApplied(true);
            }
            songRating = songRating.getNext();
        }
    }
    public void updateWeights(){
        if((this.getSongRatingHistory().getSize() % 5) == 0  ){
            Node<User> head = this.getInvolved().getListStart();
            while(head != null){
                User tmp = head.getData();
                // adding chemistry of chat to all involved
                tmp.addtoRank(this.getChemistry() ,(this.getChemistry()/100));
                head = head.getNext();
            }
        }
    }
    // Starts a new chat with a random turn and checks id previous chemistry applicable
    public boolean start(User[] members){
        if(turn == null & this.getInvolved().getSize() > 1){
            Random rn = new Random();
            int index = rn.nextInt(members.length);
            Node<User> head = this.getInvolved().getListStart();
            while(head!= null){
                if (head.getData().equals(members[index])){
                    this.turn = head;
                }
                head = head.getNext();
            }
            this.turnStart = new Date();
            // if a two people chat then check if they have previously applicable chemistry
            if (this.getInvolved().getSize() == 2){
                head = this.getInvolved().getListStart();
                Friends temp2 = head.getData().areFriends(head.getNext().getData());
                if( temp2 != null){
                    this.setChemistry(temp2.getChemistry());
                }
            }
            return true;
        }
        this.turn = null;
        return false;
    }
    // Chooses someone to play next
    public void nextTurn(){
        if(this.turn.getNext() == null){
            this.turn = this.getInvolved().getListStart();
        }else {
            this.turn = this.turn.getNext();
        }
        this.setChemistry(this.getChemistry()+0.1);
        this.turnStart = new Date();
    }

    public boolean removeInvovled(User user){
        if (this.isInvolved(user)){
            // adding chemistry of chat to removed user involved
            user.addtoRank(this.getChemistry() ,(this.getChemistry()/100));
            user.deleteChat(this);
            return true;
        }
        System.err.print("user is not involved in the chat");
        return false;
    }
    public void updateMembers(User[] members){
        for (User u : members) {
            this.getInvolved().add(u);
            u.addChatBymodified(this);
        }
        Node<User> head = this.getInvolved().getListStart();
        while (head != null){
            Node<User> nxt = head.getNext();
            while(nxt != null){
                Friends us = head.getData().areFriends(nxt.getData());
                if( us != null) {
                    us.setChatsBetween(us.getChatsBetween() + 1);
                    // increases chemistry between friends the more they chat
                    us.setChemistry(us.getChemistry() + 0.35);
                }
                nxt = nxt.getNext();
            }
            head = head.getNext();
        }
    }
}
