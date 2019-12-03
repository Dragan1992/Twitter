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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Switch;

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

import java.util.List;


public class MainActivity extends AppCompatActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
/*
    ParseObject score = new ParseObject("Score");
    score.add("username", "Tamara");
    score.add("score",99);
    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e==null){
          Log.i("uspesno","uspesno");
        }else
        Log.i("error writing", e.toString());
      }
    });
*/
/*
    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
    query.getInBackground("4onUEDPOQ1", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if(object!=null && e==null){
          Log.i("result from parse",object.get("username").toString());
        }
      }
    });
  */

/*
final ParseQuery<ParseObject> query = ParseQuery.getQuery("Score");
query.whereEqualTo("Name","Dragan");
query.setLimit(1);
query.findInBackground(new FindCallback<ParseObject>() {
  @Override
  public void done(List<ParseObject> objects, ParseException e) {

    Log.i("ResultFromBase",objects.get(0).getString("Name"));
  }
});*/

ParseQuery<ParseObject> query =ParseQuery.getQuery("Score");
query.whereGreaterThanOrEqualTo("Score",200);
query.setLimit(3);
query.findInBackground(new FindCallback<ParseObject>() {
  @Override
  public void done(List<ParseObject> objects, ParseException e) {
    for(int i=0;i<objects.size();i++){
      int score = objects.get(i).getInt("Score")+50;
      Log.i("ScoreBonus",Integer.toString(score));
      objects.get(i).put("Score",score);
      objects.get(i).saveInBackground();
      Log.i("extra points" ,objects.get(i).get("Name").toString());
    }
  }
});
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }
}