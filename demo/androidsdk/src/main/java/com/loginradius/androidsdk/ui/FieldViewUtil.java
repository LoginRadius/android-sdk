package com.loginradius.androidsdk.ui;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.internal.LinkedTreeMap;
import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.ui.CountriesDialog.OnCountrySelectedListener;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegisteration;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by loginradius on 7/19/2017.
 */

public class FieldViewUtil {

    private String country="",countryCode="";
    private String gender[];
    private String genderCode;
    private String email,phone;
    private HashMap<String,Boolean> fieldMap;

    public FieldViewUtil() {
        fieldMap = new HashMap<>();
    }

    public boolean addFieldView(RequiredFieldsViewGenerator gtr, LoginRadiusUltimateUserProfile userProfile, UserRegisteration userField, LinearLayout linearContainer, LinkedTreeMap customFields,boolean promptPassword) {
        switch (userField.getName()){
            case "firstname":
                if(userProfile.FirstName==null || userProfile.FirstName.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "lastname":
                if(userProfile.LastName==null || userProfile.LastName.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "prefix":
                if(userProfile.Prefix==null || userProfile.Prefix.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "suffix":
                if(userProfile.Suffix==null || userProfile.Suffix.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "username":
                if(userProfile.getUserName()==null || userProfile.getUserName().toString().length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "phonenumber":
                if(userProfile.PhoneNumbers==null || userProfile.PhoneNumbers.size()==0 || userProfile.PhoneNumbers.get(0) == null || userProfile.PhoneNumbers.get(0).phoneNumber == null || userProfile.PhoneNumbers.get(0).phoneNumber.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    //linearContainer.addView(gtr.generateEditText(userField.getName(),true));
                    linearContainer.addView(gtr.generatePhoneNumberView(userField.getName()));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "state":
                if(userProfile.State==null || userProfile.State.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "city":
                if(userProfile.City==null || userProfile.City.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "PostalCode":
                if(userProfile.Addresses==null || userProfile.Addresses.size()==0 || userProfile.Addresses.get(0) == null || userProfile.Addresses.get(0).PostalCode == null || userProfile.Addresses.get(0).PostalCode.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),true));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "address1":
                if(userProfile.Addresses==null || userProfile.Addresses.size()==0 || userProfile.Addresses.get(0) == null || userProfile.Addresses.get(0).Address1 == null || userProfile.Addresses.get(0).Address1.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "address2":
                if(userProfile.Addresses==null || userProfile.Addresses.size()==0 || userProfile.Addresses.get(0) == null || userProfile.Addresses.get(0).Address2 == null || userProfile.Addresses.get(0).Address2.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "phoneid":
                if(userProfile.PhoneId==null || userProfile.PhoneId.length()==0 || (userProfile.PhoneId.length()>0 && !userProfile.PhoneIdVerified)){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    //linearContainer.addView(gtr.generateEditText(userField.getName(),true));
                    linearContainer.addView(gtr.generatePhoneNumberView(userField.getName()));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "nickname":
                if(userProfile.NickName==null || userProfile.NickName.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "emailid":
                if(userProfile.Email==null || userProfile.Email.size()==0 || userProfile.Email.get(0) == null || userProfile.Email.get(0).Value == null || userProfile.Email.get(0).Value.length()==0 || (userProfile.Email.get(0).Value.length()>0 && !userProfile.getEmailVerified())){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEmailEditText(userField.getName()));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "password":
                if(userProfile.getPassword() == null && promptPassword){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generatePasswordEditText(userField.getName(),10));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "confirmpassword":
                if(userProfile.getPassword() == null && promptPassword){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generatePasswordEditText("confirmpassword",10));
                    addFieldMap("confirmpassword",userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "country":
                if(userProfile.Country==null || userProfile.Country.Name == null || userProfile.Country.Name.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    final ArrayList<String> countries = new ArrayList<>();
                    Map<String,String> map = new CountryCodes().map;
                    for(Entry<String,String> entry:map.entrySet()){
                        countries.add(entry.getKey());
                    }
                    Collections.sort(countries,new CountryCodes.StringComparator());
                    final TextView textView = gtr.generateTextView("country","Select");
                    textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.down_arrow,0);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CountriesDialog dialog = new CountriesDialog(textView.getContext(),countries);
                            dialog.setOnCountrySelectedListener(new OnCountrySelectedListener() {
                                @Override
                                public void onCountrySelected(String country) {
                                    FieldViewUtil.this.country = country;
                                    textView.setText(country);
                                }
                            });
                            dialog.show();
                        }
                    });
                    linearContainer.addView(textView);
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "gender":
                Log.i("Gender",userField.getOptions().toString());
                if(userProfile.Gender==null || userProfile.Gender.length()==0){
                    final ArrayList<LinkedTreeMap<String,String>> options = (ArrayList<LinkedTreeMap<String,String>>)userField.getOptions();
                    gender = new String[options.size()];
                    for(int j = 0 ; j < options.size() ; j++){
                        gender[j] = options.get(j).get("text");
                    }
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateOptionsTextView("gender", "Select Gender", gender, new OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            genderCode = options.get(which).get("value");
                        }
                    }));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "birthdate":
                if(userProfile.BirthDate==null || userProfile.BirthDate.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateDateTextView(userField.getName()));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "company":
                if(userProfile.Positions==null || userProfile.Positions.size()==0 || userProfile.Positions.get(0) == null || userProfile.Positions.get(0).Company == null || userProfile.Positions.get(0).Company.Name == null || userProfile.Positions.get(0).Company.Name.length()==0){
                    linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                    linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                    addFieldMap(userField.getName(),userField.getRules().contains("required"));
                    return true;
                }
                break;
            case "emailsubscription":
                if(!userProfile.getIsEmailSubscribed()){
                    linearContainer.addView(gtr.generateCheckBox("IsEmailSubscribed",userField.getDisplay(),false));
                    addFieldMap("IsEmailSubscribed",userField.getRules().contains("required"));
                    return true;
                }
                break;
            default:
                String name;
                String value = "";
                if(userField.getName().startsWith("cf_")){
                    name = userField.getName().replace("cf_","");
                    if(customFields!=null && customFields.get(name)!=null){
                        value = customFields.get(name).toString();
                    }
                }
                if(value == null || value.length()==0){
                    if(userField.getType().equals("string") || userField.getType().equals("text")){
                        linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                        if(userField.getRules().contains("numeric")){
                            linearContainer.addView(gtr.generateEditText(userField.getName(),true));
                        }else{
                            linearContainer.addView(gtr.generateEditText(userField.getName(),false));
                        }
                        addFieldMap(userField.getName(),userField.getRules().contains("required"));
                        return true;
                    }else if(userField.getType().equals("option")){
                        if(userField.getOptions()!=null){
                            final ArrayList<LinkedTreeMap<String,String>> optionsList = (ArrayList<LinkedTreeMap<String,String>>)userField.getOptions();
                            String[] arrOptions = new String[optionsList.size()];
                            for(int j = 0 ; j < optionsList.size() ; j++){
                                arrOptions[j] = optionsList.get(j).get("text");
                            }
                            linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                            linearContainer.addView(gtr.generateOptionsTextView(userField.getName(), "Select " + userField.getDisplay(), arrOptions, null));
                            addFieldMap(userField.getName(),userField.getRules().contains("required"));
                            return true;
                        }
                    }else if(userField.getType().equals("multi")){
                        linearContainer.addView(gtr.generateCheckBox(userField.getName(),userField.getDisplay(),false));
                        addFieldMap(userField.getName(),userField.getRules().contains("required"));
                        return true;
                    }else if(userField.getType().equals("password")){
                        linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                        linearContainer.addView(gtr.generatePasswordEditText(userField.getName(),10));
                        addFieldMap(userField.getName(),userField.getRules().contains("required"));
                        return true;
                    }else if(userField.getType().equals("email")){
                        linearContainer.addView(gtr.generateLabelTextView(userField.getDisplay()));
                        linearContainer.addView(gtr.generateEmailEditText(userField.getName()));
                        addFieldMap(userField.getName(),userField.getRules().contains("required"));
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    public boolean validateFields(RequiredFieldsViewGenerator gtr,LinearLayout linearContainer) {
        boolean validated = true;
        HashMap<String,String> map = gtr.getValuesMap(linearContainer);
        for(Map.Entry<String,Boolean> entry:fieldMap.entrySet()){
            String key = entry.getKey();
            String value = map.get(key);
            boolean isRequired = fieldMap.get(key);
            TextView view = (TextView)gtr.getViewByTag(linearContainer,key);
            if(key.equals("birthdate") || key.equals("gender") || key.equals("country")){
                if(value.equals("Select") && isRequired){
                    view.setError("Required");
                    view.setBackgroundResource(R.drawable.red_border);
                    validated = false;
                }
            }else if(key.equals("phonenumber") || key.equals("phoneid")){
                if(value.length() == 0 && isRequired){
                    LinearLayout linearLayout = (LinearLayout)linearContainer.findViewWithTag("view_"+key);
                    EditText editText = (EditText)linearLayout.getChildAt(3);
                    editText.setError("Required");
                    linearLayout.setBackgroundResource(R.drawable.red_border);
                    validated = false;
                }
            } else if(value!=null && value.length()==0 && isRequired){
                view.setError("Required");
                view.setBackgroundResource(R.drawable.red_border);
                validated = false;
            }else if((key.equals("password") || key.equals("confirmpassword")) && isRequired){
                EditText etPassword = (EditText)gtr.getViewByTag(linearContainer,"password");
                EditText etConfirmPassword = (EditText)gtr.getViewByTag(linearContainer,"confirmpassword");
                String pwd = etPassword.getText().toString();
                if(etConfirmPassword!=null){
                    String confirmPwd = etConfirmPassword.getText().toString();
                    if(!pwd.equals(confirmPwd)){
                        etConfirmPassword.setError("Password Mismatch");
                        etConfirmPassword.setBackgroundResource(R.drawable.red_border);
                        validated = false;
                    }
                }
                if(pwd.length()<6){
                    etPassword.setError("Password must be at least 6 characters");
                    etPassword.setBackgroundResource(R.drawable.red_border);
                    validated = false;
                }
            }else if(key.equals("emailid") && isRequired){
                EditText etEmail = (EditText)gtr.getViewByTag(linearContainer,"emailid");
                String email = etEmail.getText().toString();
                if(!email.matches(Patterns.EMAIL_ADDRESS.pattern())){
                    etEmail.setError("Invalid");
                    etEmail.setBackgroundResource(R.drawable.red_border);
                    validated = false;
                }
            }else if(key.equals("IsEmailSubscribed") && isRequired){
                CheckBox cbEmail = (CheckBox)gtr.getViewByTag(linearContainer,"IsEmailSubscribed");
                if(!cbEmail.isChecked()){
                    cbEmail.setError("Required");
                    validated = false;
                }
            }
            if(view!=null){
                view.setBackgroundResource(R.drawable.gray_border);
            }
        }
        return validated;
    }

    public JsonObject getData(RequiredFieldsViewGenerator gtr,LinearLayout linearContainer){
        HashMap<String,String> map = gtr.getValuesMap(linearContainer);
        final JsonObject jsonData = new JsonObject();
        JsonObject jsonCustom = new JsonObject();
        JsonArray arrAddress = new JsonArray();
        JsonObject address = new JsonObject();
        address.addProperty("Type","Primary");
        for(Map.Entry<String,String> entry:map.entrySet()){
            String key = entry.getKey();
            if(key!=null && !key.equals("submit")){

                if(key.equals("birthdate") || key.equals("gender") || key.equals("country")){
                    String value = map.get(key);
                    if(value.equals("Select")){
                        map.put(key,"");
                    }
                }

                if(key.equals("gender")){
                    jsonData.addProperty("gender",genderCode);
                }else if(key.equals("company")){
                    JsonObject jsonCompany = new JsonObject();
                    JsonObject jsonPosition = new JsonObject();
                    JsonArray arrPositions = new JsonArray();
                    jsonCompany.addProperty("Type","Primary");
                    jsonCompany.addProperty("Industry","");
                    jsonCompany.addProperty("Name",map.get(key));
                    jsonPosition.add("Company",jsonCompany);
                    arrPositions.add(jsonPosition);
                    jsonData.add("Positions",arrPositions);
                }else if(key.equals("address1")){
                    address.addProperty("Address1",map.get(key));
                }else if(key.equals("address2")){
                    address.addProperty("Address2",map.get(key));
                } else if(key.equals("PostalCode")){
                    address.addProperty("PostalCode",map.get(key));
                }else if(key.equals("country")){
                    JsonObject jsonCountry = new JsonObject();
                    jsonCountry.addProperty("Name",country);
                    countryCode = new CountryCodes().getCode(country);
                    jsonCountry.addProperty("Code",countryCode);
                    jsonData.add("Country",jsonCountry);
                }else if(key.equals("emailid")){
                    email = map.get(key);
                    JsonArray arrEmail = new JsonArray();
                    JsonObject jsonEmail = new JsonObject();
                    jsonEmail.addProperty("Type","Primary");
                    jsonEmail.addProperty("Value",email);
                    arrEmail.add(jsonEmail);
                    jsonData.add("Email",arrEmail);
                }else if(key.equals("phonenumber")){
                    String countryCode = map.get(key+"country_code");
                    countryCode = countryCode.replace("-","").replace("+","");
                    if(map.get(key).length()>0){
                        JsonArray arrPhone = new JsonArray();
                        JsonObject jsonPhone = new JsonObject();
                        jsonPhone.addProperty("PhoneType","Primary");
                        jsonPhone.addProperty("PhoneNumber",countryCode+map.get(key));
                        arrPhone.add(jsonPhone);
                        jsonData.add("PhoneNumbers",arrPhone);
                    }
                }else if(key.equals("phoneid")){
                    String countryCode = map.get(key+"country_code");
                    countryCode = countryCode.replace("-","").replace("+","");
                    if(map.get(key).length()>0){
                        phone = countryCode+map.get(key);
                        jsonData.addProperty("phoneid",phone);
                    }
                } else if(key.equals("birthdate")){
                    jsonData.addProperty("birthdate",getApiDate(map.get(key)));
                } else if(key.startsWith("cf_")){
                    jsonCustom.addProperty(key.replace("cf_",""),map.get(key));
                    jsonData.add("CustomFields",jsonCustom);
                } else if(!key.contains("country_code")){
                    jsonData.addProperty(key,map.get(key));
                }
            }
        }
        arrAddress.add(address);
        jsonData.add("Addresses",arrAddress);
        return jsonData;
    }

    private String getApiDate(String strDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
        try {
            Date date = dateFormat.parse(strDate);
            dateFormat.applyPattern("MM-dd-yyyy");
            String strDob = dateFormat.format(date);
            return strDob;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void addFieldMap(String name,boolean isRequired){
        fieldMap.put(name,isRequired);
    }

    public String getEmail(){
        return email;
    }

    public String getPhone(){
        return phone;
    }
}
