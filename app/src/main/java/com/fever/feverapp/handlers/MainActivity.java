package com.fever.feverapp.handlers;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.fever.feverapp.R;

import com.fever.feverapp.objects.Admin;
import com.fever.feverapp.objects.User;

import java.util.ArrayList;
import java.util.regex.Pattern;


public class MainActivity extends Activity {

    public Admin administrator;

    private EditText emailView;
    private EditText passWordView;
    private Button loginButton;

    private TextView new_user_Text;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =  Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: this part should get the key off the server becuse security handler will be only accessable from our server
        setContentView(R.layout.main_activity);
        this.administrator = new Admin(securityHandler.getKey());

        emailView = (EditText) findViewById(R.id.email);
        passWordView = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login_button);
        new_user_Text =  (TextView) findViewById(R.id.new_user_text);
        new_user_Text.setPaintFlags(new_user_Text.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        loginButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String emailText = emailView.getText().toString();
                String passText = passWordView.getText().toString();
                if(Admin.checkEmailFormat(emailText) & Admin.checkPassFormat(passText)){
                    System.err.print("The number of current users:");
                    System.err.print(User.getUserList().getSize());
                    if (!loginButton(emailText, passText)){
                        emailView.setText("");
                        passWordView.setText("");
                        //TODO: clear the feilds.
                    }
                }

            }
        });

        new_user_Text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoNewUser();
            }
        });

    }
    public void  movetoNewUser(){
        Intent intent = new Intent(this, newUserActivity.class);
        intent.putExtra("adminClass", this.administrator);
        startActivity(intent);
    }



    //*******************************************************************************************//
    // The handler for the login button
    public boolean loginButton(String email, String password){
        User user = administrator.findUser(email);
        if (  user.getPassword(this.administrator).equals(password) & administrator.loginUser(user)){
            Intent intent = new Intent(this, homePageActivity.class);
            intent.putExtra("adminClass", this.administrator);
            startActivity(intent);
            return true;
        }
        //TODO : display error messages on screen
        return false;
    }
    // The handler for the reset password button
    public boolean resetPasswordButton(String email, String newPassword){
        User user = administrator.findbyEmail(email);
        if(user != null){
            administrator.changePassword(newPassword,user);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        //TODO : display error messages on screen
        return false;
    }



}
