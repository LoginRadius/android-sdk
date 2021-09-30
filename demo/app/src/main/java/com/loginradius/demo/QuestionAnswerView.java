package com.loginradius.demo;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION_CODES;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loginradius.androidsdk.response.securityquestions.SecurityQuestionsResponse;

/**
 * Created by loginradius on 9/8/2017.
 */

public class QuestionAnswerView extends LinearLayout {

    private TextView tvQuestion;
    private EditText etAnswer;

    public QuestionAnswerView(Context context) {
        super(context);
    }

    public QuestionAnswerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public QuestionAnswerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    public QuestionAnswerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        tvQuestion = (TextView)findViewById(R.id.tvQuestion);
        etAnswer = (EditText)findViewById(R.id.etAnswer);
    }

    public void setContent(SecurityQuestionsResponse response){
        tvQuestion.setText(response.getQuestion());
    }

    public String getAnswer(){
        return etAnswer.getText().toString().trim();
    }
}
