package com.example.rseu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class Login extends AppCompatActivity{

    public static String userName;
    public static String userType;
    public static ArrayList<String> logins;
    public static ArrayList<String> passwords;
    public static ArrayList<Long> ids;
    public static ArrayList<String> types;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        try {
            logins = JsonRead.readLogin(this);
            passwords = JsonRead.readPassword(this);
            ids = JsonRead.readID(this);
            types = JsonRead.readType(this);
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void onMainLoginClick(View view) {
        TextView loginError = findViewById(R.id.login_error);

        EditText password_edit = findViewById(R.id.edit_password);
        EditText user_edit = findViewById(R.id.edit_user);

        if (user_edit.getText().toString().equals("") || password_edit.getText().toString().equals("")) {
            loginError.setText(R.string.login_error);
        }

        boolean access = false;

        for (int i = 0; i < logins.size(); i++) {
            String login = logins.get(i);
            String password = passwords.get(i);
            String type = types.get(i);

            access = login.equals(user_edit.getText().toString()) && password.equals(password_edit.getText().toString());

            if (access) {
                userName = login;
                userType = type;
                break;
            }
        }

        if (access){
            Intent intent = new Intent(this, StartPage.class);
            startActivity(intent);
        }
        else {
            loginError.setText(R.string.login_error);
        }
    }
}
