package com.loginradius.demo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.api.GetSecurityQuestionsAPI;
import com.loginradius.androidsdk.api.LoginAPI;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.JsonDeserializer;
import com.loginradius.androidsdk.helper.ErrorResponse;
import com.loginradius.androidsdk.response.GetSecurityQuestionsResponse;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.login.LoginParams;

public class SecurityQuestionsActivity extends AppCompatActivity implements OnClickListener{

    private TextView tvValue;
    private EditText input_password;
    private ProgressDialog pDialog;
    private ProgressBar pbLoad;
    private LinearLayout linearContent;
    private ListView lvQuestions;
    private Button btnProceed;
    private String value,password;
    private boolean isForgotPassword;
    private String[] arrQuestionId,arrAnswer;
    private GetSecurityQuestionsResponse[] securityQuestionsResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security_questions);
        init();
    }

    private void init() {
        pbLoad = (ProgressBar)findViewById(R.id.pbLoad);
        lvQuestions = (ListView)findViewById(R.id.lvQuestions);
        btnProceed = (Button)findViewById(R.id.btnProceed);
        linearContent = (LinearLayout)findViewById(R.id.linearContent);
        tvValue = (TextView)findViewById(R.id.tvValue);
        input_password = (EditText)findViewById(R.id.input_password);
        isForgotPassword = getIntent().getBooleanExtra("isForgotPassword",false);
        value = getIntent().getStringExtra("value");

        if(isForgotPassword){
            tvValue.setVisibility(View.GONE);
            input_password.setVisibility(View.GONE);
        }else{
            tvValue.setText(value);
        }

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setTitle("Please wait");
        pDialog.setCancelable(false);

        btnProceed.setOnClickListener(this);
        getSecurityQuestions();
    }

    private void getSecurityQuestions() {
        GetSecurityQuestionsAPI api = new GetSecurityQuestionsAPI();
        LoginParams params = new LoginParams();
        params.apikey = getString(R.string.api_key);
        if(value.matches(Patterns.EMAIL_ADDRESS.pattern())){
            params.email = value;
        }else if(value.matches(Patterns.PHONE.pattern())){
            params.phone = value;
        }else{
            params.username = value;
        }
        api.getResponse(params, new AsyncHandler<GetSecurityQuestionsResponse[]>() {
            @Override
            public void onSuccess(GetSecurityQuestionsResponse[] data) {
                pbLoad.setVisibility(View.GONE);
                securityQuestionsResponse = data;
                arrQuestionId = new String[data.length];
                arrAnswer = new String[data.length];
                lvQuestions.setAdapter(new SecurityQuestionsAdapter(SecurityQuestionsActivity.this,data));
                linearContent.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                pbLoad.setVisibility(View.GONE);
                ErrorResponse errorResponse = JsonDeserializer.deserializeJson(error.getMessage(), ErrorResponse.class);
                if(errorResponse.getErrorCode() == 1092){
                    AlertDialog.Builder alert = new Builder(SecurityQuestionsActivity.this);
                    alert.setTitle("Message");
                    alert.setMessage("Security question and answer is not saved in profile");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onBackPressed();
                        }
                    });
                    AlertDialog dialog = alert.create();
                    dialog.setCancelable(false);
                    dialog.show();
                }else{
                    NotifyToastUtil.showNotify(SecurityQuestionsActivity.this,error.getMessage());
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onBackPressed();
                        }
                    },1500);
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnProceed:
                for(int i = 0;i<lvQuestions.getChildCount();i++){
                    QuestionAnswerView qaView = (QuestionAnswerView)lvQuestions.getChildAt(i);
                    String answer = qaView.getAnswer();
                    if(answer.length() == 0){
                        NotifyToastUtil.showNotify(SecurityQuestionsActivity.this,"Please fill all security answers");
                        return;
                    }else{
                        arrQuestionId[i] = securityQuestionsResponse[i].getQuestionId();
                        arrAnswer[i] = answer;
                    }
                }
                if(isForgotPassword){
                    startResetPasswordActivity();
                }else {
                    password = input_password.getText().toString().trim();
                    if(password.length() == 0){
                        input_password.setError("Required");
                    }else{
                        loginWithSecurityQuestions();
                    }
                }
                break;
        }
    }

    private void loginWithSecurityQuestions() {
        showProgressDialog();
        LoginAPI api = new LoginAPI();
        LoginParams params = new LoginParams();
        params.apikey = getString(R.string.api_key);
        params.verificationUrl = getString(R.string.verification_url);
        if(value.matches(Patterns.EMAIL_ADDRESS.pattern())){
            params.email = value;
        }else if(value.matches(Patterns.PHONE.pattern())){
            params.phone = value;
        }else{
            params.username = value;
        }
        params.password = password;
        JsonObject qaJson = new JsonObject();
        for(int i = 0;i<arrQuestionId.length;i++){
            qaJson.addProperty(arrQuestionId[i],arrAnswer[i]);
        }
        api.getResponse(params, qaJson, new AsyncHandler<LoginData>() {
            @Override
            public void onSuccess(LoginData data) {
                hideProgressDialog();
                try {
                    Intent intent = new Intent(getApplication(), ProfileActivity.class);
                    intent.putExtra("accesstoken", data.getAccessToken());
                    intent.putExtra("provider", data.getProfile().getProvider().toLowerCase());
                    intent.putExtra("apikey", getString(R.string.api_key));
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                hideProgressDialog();
                NotifyToastUtil.showNotify(SecurityQuestionsActivity.this,error.getMessage());
            }
        });
    }

    private void startResetPasswordActivity() {
        Intent intent = new Intent(this,ResetPasswordBySecurityQuestionActivity.class);
        intent.putExtra("questionIds",arrQuestionId);
        intent.putExtra("answers",arrAnswer);
        intent.putExtra("value",value);
        startActivity(intent);
        finish();
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing()) {
            pDialog.show();
        }
    }

    private void hideProgressDialog() {
        if(pDialog!=null && pDialog.isShowing()){
            pDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if(!isForgotPassword){
            startActivity(new Intent(this,LoginActivity.class));
        }else{
            startActivity(new Intent(this,ForgotPasswordActivity.class));
        }
        finish();
    }
}
