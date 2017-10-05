package com.loginradius.demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.loginradius.androidsdk.response.GetSecurityQuestionsResponse;

/**
 * Created by loginradius on 9/8/2017.
 */

public class SecurityQuestionsAdapter extends ArrayAdapter<GetSecurityQuestionsResponse> {

    private GetSecurityQuestionsResponse[] objects;
    private LayoutInflater inflater;

    public SecurityQuestionsAdapter(@NonNull Context context, @NonNull GetSecurityQuestionsResponse[] objects) {
        super(context, R.layout.item_question_answer, objects);
        this.objects = objects;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        QuestionAnswerView view;
        if(convertView == null){
            view = (QuestionAnswerView)inflater.inflate(R.layout.item_question_answer,parent,false);
        }else{
            view = (QuestionAnswerView)convertView;
        }
        view.setContent(objects[position]);
        return view;
    }


}
