package com.fever.feverapp.handlers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fever.feverapp.objects.Admin;
import com.fever.feverapp.objects.User;
import com.fever.feverapp.R;

public class newUserActivity extends Activity {
    Admin administrator;

    private EditText emailView;
    private EditText passWordView;
    private Button signupButton;
    private Button backLogin;
    private EditText nameView;
    private EditText pnumberView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_user_activity);
        emailView = (EditText) findViewById(R.id.email_signup);
        passWordView = (EditText) findViewById(R.id.password_signup);
        nameView = (EditText) findViewById(R.id.name_signup);
        pnumberView = (EditText) findViewById(R.id.phone_signup);
        signupButton = (Button) findViewById(R.id.signup_button);
        backLogin = (Button) findViewById(R.id.backLogin_signup);

        this.administrator =  (Admin) getIntent().getSerializableExtra("adminClass");

        backLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backLoginButton();
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpButton();
            }
        });
    }

    public void backLoginButton(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    // The handler for the sign up button
    public boolean signUpButton(){
        String email = emailView.getText().toString();
        String name = nameView.getText().toString();
        String password = passWordView.getText().toString();
        String phonenum = pnumberView.getText().toString();
        System.out.print("Signing up");
        if(Admin.checkEmailFormat(email) & Admin.checkPassFormat(password) & !name.isEmpty() & !phonenum.isEmpty()) { //TODO: check if number is numeric
            if (administrator.findbyEmail(email) == null) {
                User user = this.administrator.createNewUser(name, email, phonenum, password);
                if (administrator.loginUser(user)) {
                    Intent intent = new Intent(this, homePageActivity.class);
                    intent.putExtra("adminClass", this.administrator);
                    startActivity(intent);
                }
                return true;
            }
        }
        //TODO : display error messages on screen
        return false;
    }


}
