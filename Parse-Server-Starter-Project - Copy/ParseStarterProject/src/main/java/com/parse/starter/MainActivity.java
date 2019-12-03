/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.FileDescriptor;
import java.util.List;


public class MainActivity extends AppCompatActivity {

  EditText user;
  EditText password;



  public void hideKeyboard(View v){
   InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
   inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
  }
public void switchMode(View v){
  Button button = (Button) findViewById(R.id.loginOrSignUp);
  TextView textView = (TextView) v;
  if(v.getTag().toString().equals("0")) {
    button.setText("LOGIN");
    v.setTag(1);
    button.setTag("Login");
    textView.setText("or Sign up");
  }
    else
    {
      button.setText("Sign Up");
      v.setTag(0);
      button.setTag("SignUp");
      textView.setText("or Login");
    }
}

public void onLoginOrSignUpClick(View v){
 String userText = user.getText().toString();
 String passwordText= password.getText().toString();
  if(v.getTag().toString().equals("SignUp")){
    signUpUser(userText,passwordText,0);
  }
  else{//login
    signUpUser(userText,passwordText, 1);
  }
}

public void signUpUser(final String user, String password, int mode){
  ParseUser parseUser = new ParseUser();
  parseUser.setUsername(user);
  parseUser.setPassword(password);
  if(mode==0) {
    parseUser.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
          if(e==null){
            Toast.makeText(getApplicationContext(),"SignUp is successful",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(),ListUsers.class);
            intent.putExtra("user",user);
            startActivity(intent);
          }
          else{
            Toast.makeText(getApplicationContext(),"SignUp is failed",Toast.LENGTH_SHORT).show();
          }
      }

    });
  }
  else{ //login
    ParseUser.logInInBackground(user, password, new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(e==null){
          Toast.makeText(getApplicationContext(),"Log in is successful",Toast.LENGTH_SHORT).show();
          Intent intent = new Intent(getApplicationContext(),ListUsers.class);
          intent.putExtra("user",user.getUsername());
          startActivity(intent);
        }
        else{
          Toast.makeText(getApplicationContext(),"Login failed!",Toast.LENGTH_SHORT).show();
        }
      }
    });
  }
}
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setTitle("Instagram");
    user = (EditText) findViewById(R.id.user);
    password = (EditText) findViewById(R.id.password);
    password.setOnKeyListener(new View.OnKeyListener() {
      @Override
      public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction()==KeyEvent.ACTION_DOWN){
          onLoginOrSignUpClick(findViewById(R.id.loginOrSignUp));
        }
        return false;
      }
    });
// Add your initialization code here
    Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
            .applicationId("dd455e7b6036347899b6922c173a070c338d452b")
            .clientKey("03f8a51d0981226da3cb54138c7b5c98897c0135")
            .server("http://104.211.54.192:80/parse/")
            .build()
    );


    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }
}