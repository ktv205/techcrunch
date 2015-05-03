package com.example.krishnateja.buddiesnearby.Activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.krishnateja.buddiesnearby.Models.AppConstants;
import com.example.krishnateja.buddiesnearby.Models.RequestParams;
import com.example.krishnateja.buddiesnearby.R;
import com.example.krishnateja.buddiesnearby.Utils.CommonFunctions;
import com.example.krishnateja.buddiesnearby.Utils.SendDataAsyncTask;

/**
 * Created by krishnateja on 5/3/2015.
 */
public class FeedBackActivity extends AppCompatActivity {
    private static final int RESULT_OK = 1;
    private static final int FEEDBABK_EMPTY = 2;
    private String mFeedback;
    private Context mContext;
    private static final String FEEDBACK = "feedback";
    private static final String MESSAGE = "please wait while we send your feedback";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();
        setContentView(R.layout.activity_feedback);
        Button button = (Button) findViewById(R.id.feedback_button);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int id = getFields();
                // String message;
                if (id == FEEDBABK_EMPTY) {
                    Toast.makeText(FeedBackActivity.this,
                            "feedback cannot be empty", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    String[] paths = { "safety", "public", "index.php",FEEDBACK,
                            FeedBackActivity.this.getSharedPreferences(AppConstants.AppSharedPref.NAME, Context.MODE_PRIVATE)
                                    .getString(AppConstants.AppSharedPref.USER_ID,""), mFeedback};
                    RequestParams params = CommonFunctions.setParams(paths,mContext);
                    new SendDataAsyncTask().execute(params);

                }
            }
        });
    }

    public int getFields() {
        EditText feedbackEdittext = (EditText) findViewById(R.id.feedback_edittext);
        mFeedback = feedbackEdittext.getText().toString();
        if (mFeedback.isEmpty()) {
            return RESULT_OK;
        } else {
            return FEEDBABK_EMPTY;
        }

    }



}
