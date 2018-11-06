package com.fever.feverapp.objects;

import java.io.File;
import java.io.Serializable;

public class User implements Serializable{
    // TODO: to make the searches faster, we can create a dictionary of Users, with the keys being the first letter of their name/email
    //  and the values being a linked userlist of users whose name/email start with the kay. Probabaly reduce the time in serches
    // Todo: this static list of users/somgs will be saved on a private server, and thus will be accessed from the server. not saved on actual device
    private static LinkedBag<User> userList = new LinkedBag<>();

    public String name;// might have to make unique cause we use name to search smtimes.
    public double rank;
    public boolean hasImage;
    private String phoneNum;
    private String email;
    private String imgLink;
    private String Password; // TODO: use regex to make them stronger

    private LinkedBag<User> friendRequests;
    private LinkedBag<Friends> friendList;
    private LinkedBag<Chat> chatList;
    //TODO : story of songs.
    // private LinkedBag<Song> Story = new LinkedBag<>();


    public User(String name, String email, String phoneNum, String password, String imgLink){
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.imgLink = imgLink;
        this.hasImage = false;
        this.rank = 0;
        this.Password = password;
        this.friendList = new LinkedBag<>();
        this.chatList = new LinkedBag<>();
        this.friendRequests = new LinkedBag<>();
        // Add user to the static list of users.
        User.getUserList().add(this);
        // Can improve upon unique id method later
    }

    public User(String name, String email, String phoneNum, String password){
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.imgLink = "";
        this.hasImage = false;
        this.rank = 0;
        this.Password = password;
        this.friendList = new LinkedBag<>();
        this.chatList = new LinkedBag<>();
        this.friendRequests = new LinkedBag<>();
        // Add user to the static list of users.
        User.getUserList().add(this);
        // Can improve upon unique id method later
    }

    //********************************************************************************************//
    /*Getter and Setters
     */
    public boolean equals(User e){
        return this.getEmail().equals(e.getEmail());
    }

    public String getName() {
        return name;
    }

    public static void setUserList(LinkedBag<User> userList) {
        User.userList = userList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public static LinkedBag<User> getUserList() {
        return userList;
    }
    public LinkedBag<Friends> getFriendList() {
        return friendList;
    }
    public LinkedBag<Chat> getChatList() {
        return chatList;
    }
    public LinkedBag<User> getFriendRequests() { return friendRequests; }

    /*
    public boolean setImgLink(File source){
        if( source.exists() && source.isFile() && source.canRead()){
            this.imgLink = source;
            this.hasImage = true;
            return true;
        }
        return false;
    }
    */

    public String getPassword(Admin admin) {
        if (admin.verify()){
            return Password;
        }
        return null;
    }

    public boolean setPassword(String password, Admin admin) {
        if (admin.verify()){
            this.Password = password;
            return true;
        }
        return false;
    }
    public static boolean addemail(String email){
        Node<User> head = User.getUserList().getListStart();
        while(head != null){
            if (head.getData().getEmail().toLowerCase().equals(email.toLowerCase())){
                return false;
            }
            head = head.getNext();
        }
        return true;
    }

    //********************************************************************************************//
    /* Methods needed by the User class:
       areFriends(), deleteChat() addFriend() removeFriend(), addChatbyModified(), findmyFriend()
     */
    // apply the chemistry of a rating or chat to
    public boolean addtoRank(double chem, double rate){
        if(chem >= 40){
            this.setRank(this.getRank() + rate);
        }
        if(chem < 40){
            if( this.getRank() <= 0){
                return true;
            }
            this.setRank(this.getRank() - rate);
        }return true;
    }
    // Checks if me and Uuser are friends.
    public Friends areFriends(User user){
        Node<Friends> head = this.getFriendList().getListStart();
        while(head != null){
            if (head.getData().us(this, user)){
                return head.getData();
            }
            head = head.next;
        }
        return null;
    }
    // Deletes a chat from the active ChatList
    public boolean deleteChat(Chat chat){
        if( this.getChatList().exists(chat)){
            this.getChatList().remove(chat);
            if (chat.isInvolved(this)){
                chat.getInvolved().remove(this);
            }
            return true;
        }
        System.err.print("Chat does not exist in users chat list\n");
        return false;
    }
    // Adds a friend to the users friend List
    public boolean addFriend(User user){
        if (this.areFriends(user) != null){
            System.err.print("Already friends\n");
            return false;
        }
        Friends couple = new Friends(this, user, 50);
        this.getFriendList().add(couple);
        user.getFriendList().add(couple);
        return true;
    }
    // Add friend request to requested  list.
    public boolean addFriendReq(User user){
        if (this.areFriends(user) != null){
            System.err.print("Already friends\n");
            return false;
        }
        user.addFriend(user);
        return true;
    }
    //Removes a friend from a Users friendList
    public boolean removeFriend(User user){
        Friends us = this.areFriends(user);
        if (us != null & user.getFriendList().exists(us)){
             this.getFriendList().remove(us);
             us.friend.getFriendList().remove(us);
             return true;
        }
        System.err.print("user to delete is not a friend.\n");
        return false;
    }
    // Adds a new chat to the list of active chats by when it was modified.
    public boolean addChatBymodified(Chat chat){
        Node<Chat> head = this.getChatList().getListStart();
        Node<Chat> prev = null;
        while(head != null){
            if (chat.last_modified.before(head.getData().last_modified)){
                if (prev == null){
                    this.getChatList().add(chat);
                }
                else{
                    Node<Chat> node = new Node(chat,head);
                    prev.next = node;
                    this.getChatList().increment();
                }
                return true;
            }
            prev = head;
            head = head.next;
        }
        if (head == null){
            this.getChatList().add(chat);
            return true;
        }
        return false;
    }
    // Finds all from my friends whose name startswith of is equal to name
    public LinkedBag<User> findmyFriend(String name){
        LinkedBag<User> toReturn = new LinkedBag<>();
        Node<Friends> head = this.getFriendList().getListStart();
        while(head != null){
            User tmp = head.getData().friend;
            if(tmp.getName().toLowerCase().equals(name.toLowerCase()) | tmp.getName().toLowerCase().startsWith(name.toLowerCase())){
                toReturn.add(tmp);
            }
            head = head.getNext();
        }
        return toReturn;
    }

}
