package com.loginradius.registrationsdk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.lang.reflect.Field;

import com.loginradius.sdk.api.ContactAPI;
import com.loginradius.sdk.api.StatusAPI;
import com.loginradius.sdk.api.UserProfileAPI;
import com.loginradius.sdk.models.LoginRadiusContactCursorResponse;
import com.loginradius.sdk.models.contact.LoginRadiusContact;
import com.loginradius.sdk.models.lrAccessToken;
import com.loginradius.sdk.models.status.LoginRadiusStatus;
import com.loginradius.sdk.models.userprofile.LoginRadiusUltimateUserProfile;
import com.loginradius.sdk.util.AsyncHandler;

import java.util.ArrayList;
import java.util.List;


public class UserProfileActivity extends Activity {
    private List<String> info;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_user_profile);
        if(intent != null) {
            String token = intent.getStringExtra("accesstoken");
            Log.d("token",token);
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
    /** Get Profile Info **/
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
     * @param token
     */
    private void getContacts(lrAccessToken token) {

        ContactAPI contactAPI = new ContactAPI();
        contactAPI.getResponse(token, new AsyncHandler<LoginRadiusContactCursorResponse>() {
            @Override
            public void onSuccess(LoginRadiusContactCursorResponse data) {
                int index=0;
                for (LoginRadiusContact c : data.Data) {
                    if (index>=10) break;
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
}
