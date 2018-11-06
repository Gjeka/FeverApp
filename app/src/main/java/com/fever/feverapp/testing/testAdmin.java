package com.fever.feverapp.testing;

import com.fever.feverapp.handlers.securityHandler;
import com.fever.feverapp.objects.Admin;
import com.fever.feverapp.objects.Chat;
import com.fever.feverapp.objects.LinkedBag;
import com.fever.feverapp.objects.Song;
import com.fever.feverapp.objects.User;

import junit.framework.TestCase;

import java.io.File;

public class testAdmin extends TestCase {
    public Admin admin;
    public User user1, user2, user3, user4, user5, user6;
    public Chat chat;
    public Song song1, song2;

    //
    protected void setUp() throws Exception {
        super.setUp();
        User.setUserList(new LinkedBag<User>());
        this.admin = new Admin(securityHandler.getKey());
        this.user1 = new User("Tosin", "omisoretosin@yahoo.com", "6474447353", "123");
        this.user2 = new User("Samuel", "samuel@yahoo.com", "6475507382", "123");
        this.user3 = new User("Veronica" , "veronica@hotmail.com", "6477746647", "123");
        this.user4 = new User("Betty", "bet365@yahoo.com", "6474747382", "123");
        this.user5 = new User("Bethany", "bethlovesd@yahoo.com", "604747382", "123");
        this.user6 = new User("Sarri", "betseri@yahoo.com", "6470037382", "123");
        User[] members = new User[]{user1,user2,user3,user4};
        this.chat = new Chat("TheBallbois", members);
        this.song1 = new Song("Love Hurts","playboi Carti");
        this.song2 = new Song("Half&Half" , "Playboi Carti");
        admin.loginUser(user1);
    }
    // findUser(), findUserbyEmail(), findUserlbyName()
    public void testSearches(){
        //test findUser
        assertEquals(this.admin.findUser("Tosin"), this.user1);
        assertEquals(this.admin.findUser("Sarri"), this.user6);
        assertNull(this.admin.findUser("Jacob"));
        //test Find by email
        assertEquals(this.admin.findbyEmail("omisoretosin@yahoo.com"), this.user1);
        assertEquals(this.admin.findbyEmail("bet365@yahoo.com"), this.user4);
        assertNull(this.admin.findbyEmail("Jacobsantorini@gmail.ca"));
        //test find Userlist
        assertEquals(this.admin.findUserlbyName("bet").getSize(), 2);
        assertEquals(this.admin.findUserlbyName("v").getSize(), 1);
        assertEquals(this.admin.findUserlbyName("s").getSize(), 2);
        assertEquals(this.admin.findUserlbyName("lob").getSize(), 0);
    }
    public void testSerialize(){
        securityHandler.saveuserList(admin);
        File f = new File(securityHandler.getSAVEFILEPATH() +"userList.ser");
        assertTrue(f.exists() && f.isFile());
        User.setUserList(new LinkedBag<User>());
        assertTrue(User.getUserList().getSize() == 0);
        securityHandler.readuserList(admin);
        assertTrue(User.getUserList().getSize() > 0 );
    }
    public void testLogOut(){
        //test Login
        assertTrue(this.admin.loginUser(user2));
        assertEquals(this.admin.getCurrent(), user2);
        assertTrue( this.admin.getActiveUsers().exists(user2));
        assertTrue( this.admin.getActiveUsers().exists(user1));
        // test logout
        assertTrue(this.admin.exitUser(user2));
        assertFalse(this.admin.getActiveUsers().exists(user2));
        assertEquals(this.admin.getCurrent(), user1);
        //test delete user
        this.user6.addFriend(user4);
        assertTrue(this.user4.getFriendList().getSize() == 1);
        assertTrue(this.admin.deleteUser(this.user6));
        assertFalse(User.getUserList().exists(user6));
        assertTrue(this.user4.getFriendList().getSize() == 0);
    }
}
