package com.example.twitter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;

public class MainActivity extends AppCompatActivity {
    public void Login(View v) {
        TextView userField = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        ParseUser user = new ParseUser();
        user.setUsername(userField.getText().toString());
        user.setPassword(userField.getText().toString());
        boolean userState = user.isAuthenticated();
        if (userState) {
            ParseUser.logInInBackground(userField.getText().toString(), password.getText().toString(),
                    new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (e == null) {
                                Log.i("LoginState", "Successful");
                            } else {
                                Log.i("LoginState", "False");
                            }
                        }
                    });

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Twitter: Login");

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("dd455e7b6036347899b6922c173a070c338d452b")
                .clientKey("03f8a51d0981226da3cb54138c7b5c98897c0135")
                .server("http://104.211.54.192:80/parse/")
                .build()
        );
    }
}
