package com.fever.feverapp.testing;

import com.fever.feverapp.handlers.securityHandler;
import com.fever.feverapp.objects.Admin;
import com.fever.feverapp.objects.LinkedBag;
import com.fever.feverapp.objects.User;
import junit.framework.TestCase;

public class userTesting extends TestCase {
    public Admin admin;
    public User user1, user2, user3, user4;
    public User user5, user6, user7, user8;

    protected void setUp() throws Exception {
		super.setUp();

		User.setUserList(new LinkedBag<User>());
		this.admin = new Admin(securityHandler.getKey());
		this.user1 = new User("Tosin", "omisoretosin@yahoo.com", "6474447353", "123");
		this.user2 = new User("Samuel", "samuel@yahoo.com", "6475507382", "123");
		this.user3 = new User("Veronica" , "veronica@hotmail.com", "6477746647", "123");
		this.user4 = new User("Betty", "bet365@yahoo.com", "6474747382", "123");
		this.user5 = new User("Muster", "musterpoint@gmail.com", "6477777386", "123");
		this.user6 = new User("John", "carhvalhoJohn@yhotmail.com", "6479887309", "123");
		this.user7 = new User("John-Carl", "carlStone@yahoo.com", "6477657382", "123");
		this.user8 = new User("Jameson", "JamesonEatsass@yahoo.com", "6474247300", "123");

	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testUserEquals(){
	    assertTrue(this.user1.equals(user1));
	    assertFalse(this.user2.equals(user1));
	    assertTrue(this.user3.equals(user3));
	    assertFalse(this.user4.equals(user7));
    }

    public void testLogin() {
        assertTrue(this.admin.loginUser(this.user1));
        assertEquals(this.admin.getCurrent(), this.user1);
        assertTrue(this.admin.loginUser(this.user5));
        assertEquals(this.admin.getCurrent(), this.user5);
        assertTrue(this.admin.getActiveUsers().exists(user1) & this.admin.getActiveUsers().exists(user5));
	}

	public void testFriends(){
	    assertTrue(this.user1.addFriend(this.user2));
	    assertTrue(this.user2.addFriend(this.user4));
	    assertTrue(this.user3.addFriend(this.user6));
	    assertTrue(this.user1.addFriend(this.user4));

	    //Checking if friends were added.
        assertTrue( this.user2.areFriends(this.user1) != null);
        assertTrue(this.user1.areFriends(this.user4) != null);
        assertTrue( this.user4.areFriends(this.user6) == null);
        assertTrue(this.user5.areFriends(this.user7) == null);

        //removing a friend
        assertTrue(this.user1.removeFriend(this.user2));
        assertFalse(this.user2.removeFriend(this.user1));
        assertTrue(this.user3.removeFriend(this.user6));
    }

    public void testFindfriend(){
        assertTrue(this.user1.addFriend(this.user2));
        assertTrue(this.user3.addFriend(this.user6));
        assertTrue(this.user1.addFriend(this.user6));
        assertTrue(this.user1.addFriend(this.user7));
        assertTrue(this.user1.addFriend(this.user8));
        assertTrue(this.user3.addFriend(this.user8));
        assertTrue(this.user3.addFriend(this.user7));

        assertTrue(this.user1.findmyFriend("j").getSize() == 3);
        assertTrue(this.user3.findmyFriend("j").getSize() == 3);
    }

    public void testLogOut(){
        assertTrue(this.admin.loginUser(this.user1));
        assertTrue(this.admin.loginUser(this.user5));
	    assertTrue(this.admin.exitUser(this.user1));
	    assertTrue(this.admin.getActiveUsers().exists(this.user5));
	    assertFalse(this.admin.getActiveUsers().exists(this.user1));
    }

    public void testDeleteUser(){
        assertTrue(admin.deleteUser(this.user7));
        assertTrue(admin.deleteUser(this.user8));
        assertFalse(User.getUserList().exists(user7));
        assertFalse(User.getUserList().exists(user8));
    }


}
