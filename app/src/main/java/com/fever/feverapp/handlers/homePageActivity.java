package com.fever.feverapp.handlers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.fever.feverapp.objects.Admin;
import com.fever.feverapp.objects.Chat;
import com.fever.feverapp.objects.LinkedBag;
import com.fever.feverapp.objects.User;
import com.fever.feverapp.R;

public class homePageActivity extends Activity{
    public Admin administrator;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.administrator =  (Admin) getIntent().getSerializableExtra("adminClass");
        // TODO: STUFF ON THE LAYOUT OF THE PAGE.
        setContentView(R.layout.chat);
        //ListAdapter chatAdapt = new ArrayAdapter<User>(this, android.R.layout., this.administrator.getCurrent().getChatList().toArray());
        ListView chatItems = (ListView) findViewById(R.id.chatItems);
    }

    public User getProfile() {
        return this.administrator.getCurrent();
    }

    public LinkedBag<Chat> getchatsButton() {
        User user = this.administrator.getCurrent();
        return user.getChatList();
    }
    public LinkedBag<User> getFriendsButton() {
        User user = this.administrator.getCurrent();
        LinkedBag<User> friends = administrator.showFriends(user);
        return friends;
    }

    public void openchatButton(Chat chat){
        Intent intent = new Intent(this, chatPageActivity.class);
        intent.putExtra("adminClass", this.administrator);
        intent.putExtra("chatSelected", chat);
        startActivity(intent);
        return;
    }
    public boolean deleteChatButton(Chat chat) {
        User user = this.administrator.getCurrent();
        return this.administrator.deleteChatOne(chat, user);
    }
    public LinkedBag<User> searchExistFriend(String name){
        return this.administrator.getCurrent().findmyFriend(name);
    }
    public LinkedBag<User> searchAllUsers(String name){
        return this.administrator.findUserlbyName(name);
    }
    public boolean logOutButton(){
        if (this.administrator.exitUser(this.administrator.getCurrent())) {
            if (this.administrator.getActiveUsers().getSize() == 0) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, homePageActivity.class);
                intent.putExtra("adminClass", this.administrator);
                startActivity(intent);
            }
            return true;
        }
        return false;
    }
    public boolean deleteAccount(){
        return this.administrator.deleteUser(this.administrator.getCurrent());
        //TODO: INTENT
    }

    public boolean switchUser(User user){
        if(this.administrator.getActiveUsers().exists(user)){
            this.administrator.setCurrent(user);
            Intent intent = new Intent(this, homePageActivity.class);
            intent.putExtra("adminClass", this.administrator);
            startActivity(intent);
            return true;
        }
        return false;
    }
}
