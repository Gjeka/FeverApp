package com.fever.feverapp.testing;

import junit.framework.TestCase;
import com.fever.feverapp.handlers.securityHandler;
import com.fever.feverapp.objects.Admin;
import com.fever.feverapp.objects.Chat;
import com.fever.feverapp.objects.LinkedBag;
import com.fever.feverapp.objects.Song;
import com.fever.feverapp.objects.User;
import com.fever.feverapp.objects.chatSongRating;


public class chatTesting extends TestCase {
    public Admin admin;
    public User user1, user2, user3, user4;
    public Chat chat, chat2;
    public Song song1, song2;

    protected void setUp() throws Exception {
        super.setUp();

        User.setUserList(new LinkedBag<User>());
        this.admin = new Admin(securityHandler.getKey());
        this.user1 = new User("Tosin", "omisoretosin@yahoo.com", "6474447353", "123");
        this.user2 = new User("Samuel", "samuel@yahoo.com", "6475507382", "123");
        this.user3 = new User("Veronica" , "veronica@hotmail.com", "6477746647", "123");
        this.user4 = new User("Betty", "bet365@yahoo.com", "6474747382", "123");
        assertTrue(this.user1.addFriend(this.user2));
        assertTrue(this.user2.addFriend(this.user4));
        assertTrue(this.user3.addFriend(this.user1));
        assertTrue(this.user1.addFriend(this.user4));
        User[] members = new User[]{user1,user2,user3,user4};
        User[] members2 = new User[]{user3,user1};
        this.chat = new Chat("TheBallbois", members);
        this.chat2 = new Chat("ting", members2);
        this.song1 = new Song("Love Hurts","playboi Carti");
        this.song2 = new Song("Half&Half" , "Playboi Carti");
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
    // Test methods on chat like isInvolved(), removeInvolved(), addInvolved()
    // Test methods in User like deleteChat()
    public void testNewChat(){
        // Check if chats contain the users
        assertTrue(this.chat.isInvolved(this.user1));
        assertTrue(this.chat.isInvolved(this.user2));
        assertTrue(this.chat.isInvolved(this.user3));
        assertTrue(this.chat.isInvolved(this.user4));
        //test delete
        assertTrue(this.chat.removeInvovled(user1));
        assertFalse(this.chat.isInvolved(user1));
        assertTrue(this.chat.removeInvovled(user4));
        assertFalse(this.chat.isInvolved(user4));
        //test reAdd;
        assertTrue(this.chat.addInvolved(user1));
        assertFalse(this.chat.addInvolved(user2));
        assertTrue(this.chat.addInvolved(user4));
        assertFalse(this.chat.addInvolved(user4));
        // Test Users chat list.
        assertTrue(user1.deleteChat(this.chat));
        assertFalse(this.user1.getChatList().exists(this.chat));
        assertTrue(this.chat.addInvolved(user1));
        assertTrue(this.user1.getChatList().exists(this.chat));
        assertTrue(this.user3.getChatList().exists(this.chat));

    }

    public void testChatStart(){
        assertTrue(this.chat.getInvolved().getSize() == 4);
        assertTrue(this.chat.isInvolved(this.chat.turn.getData()));
    }

    public void testSongAddgroup(){
        System.err.print("Testing Half and half in a group chat \n");
        chatSongRating halfandhald = new chatSongRating("Bangin",this.user1,song2,this.chat);
        this.user3.setRank(50);
        this.user2.equals(25);
        // adda fr

        // rate songs
        assertTrue(halfandhald.addRating(70.5, this.user3));
        assertFalse(halfandhald.addRating(70.5, this.user1));
        assertTrue(halfandhald.addRating(50.5, this.user2));
        assertTrue(halfandhald.addRating(40.5, this.user4));
        //

        assertTrue(this.user1.areFriends(user2).getChatsBetween() == 1);
        assertTrue(this.user2.areFriends(user1).getChatsBetween() == 1);
        assertTrue(this.user2.areFriends(user4).getChatsBetween() == 1);
        // Preliminary information
        System.err.print(halfandhald.getFinalrating() + "\n");
        System.err.print(this.chat.getChemistry() + "\n");
        System.err.print(this.user1.getRank() + "\n");
        System.err.print("After adding song to chat\n");
        //test adding song rating to chat
        assertTrue(this.chat.addSongRating(halfandhald));
        System.err.print(this.chat.getChemistry() + "\n");
        System.err.print(this.user1.getRank()+ "\n");

        // Testing a one to one chat
        System.err.print("Testing Love Huts in a 1v1 chat \n");
        chatSongRating loveHurts = new chatSongRating("it does", this.user3, song1, this.chat2);

        assertTrue(loveHurts.addRating(70.5, this.user1));
        assertFalse(loveHurts.addRating(70.5, this.user3));

        assertTrue(this.user3.areFriends(user1).getChatsBetween() == 2);
        //
        // Preliminary information
        System.err.print(loveHurts.getFinalrating());
        System.err.print("chem: " +this.chat2.getChemistry() + "\n");
        System.err.print(this.user3.getRank() + "\n");
        System.err.print("After adding song to chat\n");
        //
        assertTrue(this.chat2.addSongRating(loveHurts));
        System.err.print("chem: " + this.chat2.getChemistry() + "\n");
        System.err.print(this.user3.getRank()+ "\n");


    }
}
