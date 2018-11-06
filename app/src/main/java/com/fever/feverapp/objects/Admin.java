package com.fever.feverapp.objects;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fever.feverapp.handlers.securityHandler;

public class Admin implements Serializable{
    // will do stuff like logining in, saving sessions, removing users,
    // ALSO: This way you can have multiple accounts set up to run at the same time;
    private LinkedBag<User> activeUsers;
    private User current;
    private String adminKey;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    //***************************************************************************************//
    /* Constructor: Administrator
       Getter and Setters
     */

    public Admin(String key) {
        this.adminKey = key;
        this.activeUsers = new LinkedBag<>();
        this.current = null;
    }

    public boolean verify() {
        if (this.adminKey.equals(securityHandler.getKey())){
            return true;
        }
        System.err.print("Cannot verify admin class");
        return false;
    }
    public void setCurrent(User user){
        if(this.activeUsers.exists(user)){ this.current = user;}
    }
    public User getCurrent(){
        return this.current;
    }
    public LinkedBag<User> getActiveUsers() {
        return activeUsers;
    }

    //*************************************************************************************//
    /* Search Methods that finds friends based on who is logged on.
       Search Methods for a General Search, by email, by name.
     */
    public User findUser(String name){
        Node<User> head = User.getUserList().getListStart();
        while (head != null){
            User user = head.getData();
            if(user.getName().toLowerCase().equals(name.toLowerCase())){
                return user;
            }
            head = head.getNext();
        }
        return null;
    }
    public User findbyEmail(String email){
        Node<User> head = User.getUserList().getListStart();
        while(head != null){
            if( head.getData().getEmail().equals(email)){
                return head.getData();
            }
            head = head.getNext();
        }
        return null;
    }
    // Finds and returns a linkedList of Users with this name.
    public LinkedBag<User> findUserlbyName(String name){
        LinkedBag<User> toReturn = new LinkedBag<>();
        Node<User> head = User.getUserList().getListStart();
        while(head != null){
            String headName = head.getData().getName().toLowerCase();
            if(headName.equals(name.toLowerCase()) | headName.startsWith(name.toLowerCase())){
                toReturn.add(head.getData());
            }
            head = head.getNext();
        }
        return toReturn;
    }

    //*****************************************************************************************//
    /* General methods to Login, Log-out and delete account

     */
    // login a new User.
    public boolean loginUser(User user){
        if(User.getUserList().exists(user) & !this.getActiveUsers().exists(user)) {
            this.getActiveUsers().add(user);
            this.setCurrent(user);
            return true;
        }
        System.err.print("User already logged in or does not exist\n");
        return false;
    }
    // Sign an activeUser out.
    public boolean exitUser( User user){
        if(this.getActiveUsers().exists(user)){
            if(this.getActiveUsers().remove(user)){
                if(this.getCurrent().equals(user)){
                    this.setCurrent(this.getActiveUsers().getListStart().getData());
                }
                return true;
            }
        }
        System.err.print("User is not logged in\n");
        return false;
    }
    //Delete a user from the major userList
    public boolean deleteUser(User user){
        if(User.getUserList().exists(user)){ // & this.verify()) {
            if (this.getActiveUsers().exists(user)){
                this.getActiveUsers().remove(user);
            }
            User.getUserList().remove(user);
            Node<Friends> head = user.getFriendList().getListStart();
            while(head != null){
                head.getData().friend.removeFriend(user);
                head = head.getNext();
            }
            return true;
        }
        return false;
    }


    //********************************************************************************************//
    /* Methods for Changing Personal Information on the User
       Methods for creating a new Account
       Helper methods Used by Handlers
     */
    // create a new User;
    public User createNewUser(String name, String email, String phoneNum, String password){
        if(User.addemail(email)){
            User user = new User(name, email, phoneNum, password, "");
            return user;
        }
        return null;
    }
    //Change user's Password
    public boolean changePassword(String password, User user){
        return user.setPassword(password, this);
    }
    //edit users current information.
    public void editUser( String name, String email, String num, User user){
        if(User.addemail(email)) {
            user.setEmail(email);
        }
        user.setName(name);
        user.setPhoneNum(num);
    }
    //Get active list of chats(Fever).
    public LinkedBag<Chat> getChats(User user){
        return user.getChatList();
    }
    //Add a new chat.
    public Chat addChat(String name, User[] users){
        Chat chat = new Chat(name, users);
        return chat;
    }
    //search method to search for sings from file;
    public LinkedBag<User> showFriends(User user){
        Node<Friends> head = user.getFriendList().getListStart();
        LinkedBag<User> myFriends = new LinkedBag<>();
        while(head != null){
            myFriends.add(head.getData().friend);
            head = head.next;
        }
        return myFriends;
    }
   //Delete a chat from all the involved people.
    public boolean deleteChatall(Chat chat){
        Node<User> head = chat.getInvolved().getListStart();
        while(head != null){
            head.getData().deleteChat(chat);
            head = head.next;
        }
        return true;
    }
    public boolean deleteChatOne(Chat chat, User user){
        user.deleteChat(chat);
        return chat.isInvolved(user);
    }


    // ******************************************************************************************//
    /* Methods to find a song , by Keyword(mix of both name and artist), by name, by artist
       Methods to

     */
    //Finds and returns song by name and artist on the locally saved group of songs.
    public static Song findSongLocal(String name, String artist){
        Node<Song> head = Song.getSongList().getListStart();
        while (head != null){
            if(head.getData().name.equals(name) & head.getData().artist.equals(artist)){
                return head.getData();
            }
            head = head.next;
        }
        return null;
    }
    // Finds a song by searching the web for the song then creating a record(Song) and returning that.
    public Song findSongGlobal(String name, String artist){
        //TODO: INCOMPLETE
        return null;
    }

    //********************************************************************************************//

    public static boolean checkEmailFormat(String email){
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    public static boolean checkPassFormat(String email){
        //TODO: Add information on regex for passowrds later
        if(email.length() > 3){
            return true;
        }
        return false;
    }



}
