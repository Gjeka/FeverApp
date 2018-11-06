package com.fever.feverapp.handlers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.fever.feverapp.R;
import com.fever.feverapp.objects.Node;
import com.fever.feverapp.objects.Admin;
import com.fever.feverapp.objects.Chat;
import com.fever.feverapp.objects.LinkedBag;
import com.fever.feverapp.objects.Song;
import com.fever.feverapp.objects.User;
import com.fever.feverapp.objects.chatSongRating;
import com.fever.feverapp.objects.chatText;
import com.fever.feverapp.objects.ratingRequest;

import java.util.ArrayList;
import java.util.Date;

public class chatPageActivity extends Activity{
    public Admin administrator;
    private Chat chat;
    private Node<User> turn;

    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.chat = (Chat) getIntent().getSerializableExtra("chatSelected");
        this.administrator = (Admin) getIntent().getSerializableExtra("adminClass");
        this.turn = chat.turn;
        // remove this chat from the openers chatlist and add back after closing the chat.
        this.administrator.getCurrent().getChatList().remove(this.getChat());
        refreshChat();

        setContentView(R.layout.chat);
        ListView mListView = (ListView) findViewById(R.id.chatItems);

        User user1 = new User("John", "john123@gmail.com", "647-887-8933", "admin", "");

        ArrayList<User> UserList = new ArrayList<>();
        UserList.add(user1);

        UserListAdapter adapter = new UserListAdapter(this, R.layout.chat_item, UserList);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        // basically when we close the chat.
        this.administrator.getCurrent().addChatBymodified(this.getChat());
        super.onDestroy();
    }

    //********************************************************************************************//

    public Chat getChat() {
        return chat;
    }
    public void setChat(Chat chat) {
        this.chat = chat;
    }
    public Node<User> getTurn() {
        return turn;
    }

    public boolean refreshChat(){
        Date now = new Date();
        long diff = now.getTime() - this.getChat().getTurnStart().getTime();
        long diffHours = diff /(60 * 60 *1000) % 24;
        if ( diffHours > 5){
            this.getChat().nextTurn();
        }
        return true;
    }
    //********************************************************************************************//
    /*  Methods related to buttons on the chat game page.
     */


    public boolean skipTurnButton(){
        this.getChat().nextTurn();
        return true;
    }
    public void addtextButton(String s){
        chatText text = new chatText(s, this.administrator.getCurrent());
        this.getChat().addChatText(text);
    }
    public boolean addSongButton(Song song, User user, String sentiment){
         if(user.equals(this.turn)){
             chatSongRating last = this.getChat().getSongRatingHistory().getListStart().getData();
             if (last != null & last.howComplete() == 0.0){
                 System.err.print("Last song has not been rated");
                 return false;
             }
             this.getChat().nextTurn();
             this.getChat().setAction_need(true);
             chatSongRating tmp = new chatSongRating(sentiment, user, song, this.getChat());
             this.getChat().addSongRating(tmp);
             if (!getRates(tmp, user)){
                 System.err.print("Could not request rating from all involved");
                 return false;
             }
             return true;
         }
         return false;
    }
    public boolean handleRatingRequest(ratingRequest request, double rate){
        if(this.administrator.getCurrent().equals(request.forUser)){
            return request.completeRequest(rate);
        }
        return false;
    }

    //********************************************************************************************//
    /* Methods Used by Handler
     */
    public boolean getRates(chatSongRating song, User creator){
        if (this.getChat().action_need){
            Node<User> head = this.getChat().getInvolved().getListStart();
            while (head != null & !(head.getData().equals(creator))){
                ratingRequest request = new ratingRequest(song, head.getData());
                this.getChat().addActiveRatingRequests(request);
                head = head.getNext();
            }
            this.getChat().setAction_need(false);
            return true;
        }
        return false;
    }

    public LinkedBag<ratingRequest> viewRatingRequests(User user){
        LinkedBag<ratingRequest> myrequests = new LinkedBag<>();
        Node<ratingRequest> head = this.getChat().getActiveRatingRequests().getListStart();
        while( head != null){
            if (head.getData().forUser.equals(user)){
                myrequests.add(head.getData());
            }
            head = head.getNext();
        }
        return myrequests;
    }

    public LinkedBag<chatText> viewChatofGame(){
         return this.getChat().getChatHistory();
    }

}
