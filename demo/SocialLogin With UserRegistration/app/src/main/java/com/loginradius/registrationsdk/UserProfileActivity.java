package com.loginradius.registrationsdk;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loginradius.sdk.api.ContactAPI;
import com.loginradius.sdk.api.StatusAPI;
import com.loginradius.sdk.api.UserProfileAPI;
import com.loginradius.sdk.models.LoginRadiusContactCursorResponse;
import com.loginradius.sdk.models.contact.LoginRadiusContact;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.status.LoginRadiusStatus;
import com.loginradius.sdk.models.userprofile.LoginRadiusUltimateUserProfile;
import com.loginradius.sdk.util.AsyncHandler;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import org.apache.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;


public class UserProfileActivity extends Activity {


    private static final int TIMEOUT_CONNECTION = 180000;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    ImageView ivUserProfile, ivUserProfile1;
    TextView nameView, emailView, mobileView;
    Button logout;
    ProgressDialog progressBar;
    private List<String> info;
    private ArrayAdapter<String> adapter;

    private static OkHttpClient getOkHttpInstance() {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(TIMEOUT_CONNECTION, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(TIMEOUT_CONNECTION, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(TIMEOUT_CONNECTION, TimeUnit.MILLISECONDS);
        return okHttpClient;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Intent intent = getIntent();
        setContentView(R.layout.activity_user_profile);

        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            logout = (Button) findViewById(R.id.logout);

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    progressBar = new ProgressDialog(UserProfileActivity.this);
                    progressBar.setMessage("Loading");
                    progressBar.show();

                    long delayInMillis2 = 3000;
                    Timer timer2 = new Timer();
                    timer2.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            progressBar.dismiss();
                            Intent intent = new Intent(getApplication(), MainActivity.class);

                            startActivity(intent);
                        }
                    }, delayInMillis2);


                }
            });
        } else {
            System.out.println("------" + "No Internet Connection");
            Toast.makeText(getApplicationContext(), "No Internet Connection Available", Toast.LENGTH_SHORT).show();
        }
        ivUserProfile = (ImageView) findViewById(R.id.profile_image);
        ivUserProfile1 = (ImageView) findViewById(R.id.profile_image1);
        nameView = (TextView) findViewById(R.id.name_view);
        nameView.setVisibility(View.INVISIBLE);
        emailView = (TextView) findViewById(R.id.email_view);
        emailView.setVisibility(View.INVISIBLE);
        mobileView = (TextView) findViewById(R.id.mobile_view);
        mobileView.setVisibility(View.INVISIBLE);


        if (intent != null) {
            String token = intent.getStringExtra("accesstoken");
            Log.d("token", token);
            ListView listview = (ListView) findViewById(R.id.listView);
            info = new ArrayList<>();
            adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, info);
            listview.setAdapter(adapter);

            lrAccessToken accessToken = new lrAccessToken();
            accessToken.access_token = token;
            accessToken.provider = "facebook"; // just for demo
            getUserData(accessToken);
            getStatus(accessToken);
            getContacts(accessToken);

        }

    }

    /**
     * Get Profile Info
     **/
    private void getUserData(lrAccessToken accessToken) {
        UserProfileAPI userAPI = new UserProfileAPI();
        userAPI.getResponse(accessToken, new AsyncHandler<LoginRadiusUltimateUserProfile>() {
            @Override
            public void onSuccess(LoginRadiusUltimateUserProfile userProfile) {
                List<String> result = new ArrayList<String>();
                /**
                 * While we could easily pull any desired fields, we can also just grab every
                 * fields that is not null. Many fields are not strings, but you can extract
                 * their information manually.
                 */
                try {
                    for (Field field : userProfile.getClass().getDeclaredFields()) {
                        field.setAccessible(true);
                        Object value = field.get(userProfile);
                        if (value != null && value instanceof String) {
                            result.add(field.getName() + ": " + value);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                info.addAll(result);
                adapter.notifyDataSetChanged();


                nameView.setText(userProfile.FullName);
                nameView.setVisibility(View.VISIBLE);
                if (userProfile.Email != null && userProfile.Email.size() > 0) {
                    emailView.setText(userProfile.Email.get(0).Value);
                    emailView.setVisibility(View.VISIBLE);
                }
                if (userProfile.PhoneNumbers != null && userProfile.PhoneNumbers.size() > 0) {
                    mobileView.setText(userProfile.PhoneNumbers.get(0).phoneNumber);
                    mobileView.setVisibility(View.VISIBLE);
                } else {

                }
                if (userProfile.ImageUrl != null && !userProfile.ImageUrl.equals("")) {
                    ivUserProfile1.setVisibility(View.INVISIBLE);
                    new DownloadProfileImagesTask(userProfile.ImageUrl).execute();
                }
            }


            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("UserProfileAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * Some APIs are Provider-specific (such as StatusAPI). If the API is not available
     * for the provider, it will just return 'onFailure' with an errorcode.
     */
    private void getStatus(lrAccessToken token) {
        StatusAPI statusAPI = new StatusAPI();
        statusAPI.getResponse(token, new AsyncHandler<LoginRadiusStatus[]>() {
            @Override
            public void onSuccess(LoginRadiusStatus[] data) {
                if (data.length == 0 || data[0] == null) return;
                info.add("Recent status: " + data[0]);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("StatusAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                }
            }
        });

    }

    /**
     * Load ten contacts into list
     *
     * @param token
     */
    private void getContacts(lrAccessToken token) {

        ContactAPI contactAPI = new ContactAPI();
        contactAPI.getResponse(token, new AsyncHandler<LoginRadiusContactCursorResponse>() {
            @Override
            public void onSuccess(LoginRadiusContactCursorResponse data) {
                int index = 0;
                for (LoginRadiusContact c : data.Data) {
                    if (index >= 10) break;
                    info.add("Contact " + index + ": " + c.name);
                    index++;
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable error, String errorcode) {
                if (errorcode.equals("lr_API_NOT_SUPPORTED")) {
                    info.add("ContactAPI is not supported by this provider.");
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplication(), MainActivity.class);
        // intent.putExtra("action", "LOGIN");
        startActivity(intent);
    }

    class DownloadProfileImagesTask extends AsyncTask<Void, Integer, Void> {
        String profilePath;
        Bitmap bitmap;

        public DownloadProfileImagesTask(String profilePath) {
            this.profilePath = profilePath;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... objects) {
            com.squareup.okhttp.Response clientResponse = null;
            try {
                Request request = new Request.Builder()
                        .url(profilePath)
                        .build();
                clientResponse = getOkHttpInstance().newCall(request).execute();
                int statusCode = HttpStatus.SC_METHOD_FAILURE;
                if (clientResponse != null) {
                    statusCode = clientResponse.code();
                }
                if (statusCode == HttpStatus.SC_OK) {
                    InputStream inputStream = clientResponse.body().byteStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                }
            } catch (IOException e) {
                if (e.getMessage() != null) {
                }
            } catch (Exception e) {
                if (e.getMessage() != null) {
                }
            } finally {
                if (clientResponse != null) {
                    try {
                        clientResponse.body().close();
                    } catch (IOException e) {
                        if (e.getMessage() != null) {
                        }
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            try {
                if (bitmap != null) {
                    ivUserProfile.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                Log.v(this.getClass().getSimpleName(), "", e);
            }
        }
    }
}
