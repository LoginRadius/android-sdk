package com.loginradius.androidsdk.ui;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog.Builder;
import android.text.InputFilter;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loginradius.androidsdk.R;
import com.loginradius.androidsdk.ui.CountriesDialog.OnCountrySelectedListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by loginradius on 7/13/2017.
 */

public class RequiredFieldsViewGenerator implements OnFocusChangeListener{

    private Context context;
    private int fields_color;

    public RequiredFieldsViewGenerator(Context context,int fields_color) {
        this.context = context;
        if(fields_color == 0){
            this.fields_color = ContextCompat.getColor(context,R.color.wv_fields_color);
        }else{
            this.fields_color = fields_color;
        }
    }

    public ScrollView generateParentView(){
        ScrollView svParent = new ScrollView(context);
        ScrollView.LayoutParams params = new ScrollView.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        svParent.setLayoutParams(params);
        svParent.setBackgroundColor(Color.parseColor("#fafafa"));
        return svParent;
    }

    public LinearLayout generateParentContainerView(){
        LinearLayout linearContainer = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        linearContainer.setLayoutParams(params);
        int tenPx = getPx(10);
        linearContainer.setPadding(tenPx,0,tenPx,tenPx);
        linearContainer.setOrientation(LinearLayout.VERTICAL);
        return linearContainer;
    }

    public TextView generateLabelTextView(String value){
        TextView tvLabel = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        tvLabel.setText(value);
        tvLabel.setTextColor(fields_color);
        params.setMargins(0,getPx(10),0,0);
        tvLabel.setLayoutParams(params);
        return tvLabel;
    }

    public EditText generateEmailEditText(String tag){
        EditText editText = new EditText(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(0,getPx(5),0,0);
        editText.setLayoutParams(params);
        editText.setTag(tag);
        editText.setBackgroundColor(Color.WHITE);
        editText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        editText.setPadding(getPx(10),getPx(10),getPx(10),getPx(10));
        editText.setOnFocusChangeListener(this);
        editText.setBackgroundResource(R.drawable.gray_border);
        return editText;
    }

    public EditText generatePasswordEditText(String tag,int maxLength){
        EditText editText = new EditText(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(0,getPx(5),0,0);
        editText.setLayoutParams(params);
        editText.setBackgroundColor(Color.WHITE);
        editText.setPadding(getPx(10),getPx(10),getPx(10),getPx(10));
        editText.setOnFocusChangeListener(this);
        editText.setBackgroundResource(R.drawable.gray_border);
        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        InputFilter[] arrFilter = new InputFilter[1];
        arrFilter[0] = new InputFilter.LengthFilter(maxLength);
        editText.setFilters(arrFilter);
        editText.setTag(tag);
        return editText;
    }

    public LinearLayout generatePhoneNumberView(String tag){
        final LinearLayout linearLayout = new LinearLayout(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(0,getPx(5),0,0);
        linearLayout.setLayoutParams(params);
        linearLayout.setPadding(getPx(1),getPx(1),getPx(1),getPx(1));
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearLayout.setBackgroundResource(R.drawable.gray_border);
        params = new LinearLayout.LayoutParams(getPx(15),LayoutParams.MATCH_PARENT);
        params.setMargins(getPx(5),0,0,0);
        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(params);
        imageView.setImageResource(R.drawable.down_arrow);
        params = new LinearLayout.LayoutParams(getPx(1),LayoutParams.MATCH_PARENT);
        params.setMargins(getPx(10),0,0,0);
        final LinearLayout separator = new LinearLayout(context);
        separator.setBackgroundColor(Color.parseColor("#8c8c8c"));
        separator.setLayoutParams(params);
        params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(getPx(5),getPx(1),0,getPx(1));
        final EditText etCountryCode = new EditText(context);
        etCountryCode.setText("+1");
        etCountryCode.setTextColor(Color.BLACK);
        etCountryCode.setTextAppearance(context,android.R.style.TextAppearance_Widget_EditText);
        etCountryCode.setBackgroundColor(Color.parseColor("#fafafa"));
        etCountryCode.setLayoutParams(params);
        etCountryCode.setTag(tag+"country_code");
        params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(getPx(5),0,getPx(5),0);
        EditText editText = new EditText(context);
        editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        editText.setLayoutParams(params);
        editText.setPadding(0,0,getPx(5),0);
        editText.setTag(tag);
        editText.setGravity(Gravity.CENTER_VERTICAL);
        editText.setBackgroundColor(Color.parseColor("#fafafa"));
        imageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final ArrayList<String> countries = new ArrayList<>();
                Map<String,String> map = new CountryCodes().map;
                for(Entry<String,String> entry:map.entrySet()){
                    countries.add(entry.getKey());
                }
                Collections.sort(countries,new CountryCodes.StringComparator());
                CountriesDialog dialog = new CountriesDialog(context,countries);
                dialog.setOnCountrySelectedListener(new OnCountrySelectedListener() {
                    @Override
                    public void onCountrySelected(String country) {
                        String callingCode = new CountryCodes().getCallingCode(country);
                        if(callingCode.length()==0){
                            callingCode = "+1";
                        }
                        if(!callingCode.startsWith("+")){
                            callingCode = "+"+callingCode;
                        }
                        etCountryCode.setText(callingCode);
                    }
                });
                dialog.show();
            }
        });
        etCountryCode.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    separator.setBackgroundColor(Color.parseColor("#2299DD"));
                    linearLayout.setBackgroundResource(R.drawable.focus_border);
                }else{
                    separator.setBackgroundColor(Color.parseColor("#8c8c8c"));
                    linearLayout.setBackgroundResource(R.drawable.gray_border);
                }
            }
        });
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    separator.setBackgroundColor(Color.parseColor("#2299DD"));
                    linearLayout.setBackgroundResource(R.drawable.focus_border);
                }else{
                    separator.setBackgroundColor(Color.parseColor("#8c8c8c"));
                    linearLayout.setBackgroundResource(R.drawable.gray_border);
                }
            }
        });
        linearLayout.setTag("view_"+tag);
        linearLayout.addView(imageView);
        linearLayout.addView(etCountryCode);
        linearLayout.addView(separator);
        linearLayout.addView(editText);
        return linearLayout;
    }

    public EditText generateEditText(String tag,boolean isNumeric){
        EditText editText = new EditText(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(0,getPx(5),0,0);
        editText.setLayoutParams(params);
        editText.setTag(tag);
        editText.setBackgroundColor(Color.WHITE);
        editText.setPadding(getPx(10),getPx(10),getPx(10),getPx(10));
        editText.setOnFocusChangeListener(this);
        editText.setBackgroundResource(R.drawable.gray_border);
        if(isNumeric){
            editText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }else{
            editText.setInputType(InputType.TYPE_CLASS_TEXT);
        }
        return editText;
    }

    public TextView generateTextView(String tag,String text){
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(0,getPx(5),0,0);
        textView.setLayoutParams(params);
        textView.setTag(tag);
        textView.setText(text);
        textView.setTextColor(Color.BLACK);
        textView.setBackgroundColor(Color.WHITE);
        textView.setTextAppearance(context,android.R.style.TextAppearance_Widget_EditText);
        textView.setPadding(getPx(10),getPx(10),getPx(10),getPx(10));
        textView.setBackgroundResource(R.drawable.gray_border);
        textView.setClickable(true);
        return textView;
    }

    public TextView generateOptionsTextView(String tag,final String title, final String[] options, final DialogInterface.OnClickListener listener){
        final TextView textView = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        textView.setText("Select");
        textView.setTag(tag);
        textView.setTextColor(Color.BLACK);
        textView.setOnFocusChangeListener(this);
        textView.setBackgroundResource(R.drawable.gray_border);
        textView.setTextAppearance(context,android.R.style.TextAppearance_Widget_EditText);
        textView.setPadding(getPx(10),getPx(10),getPx(10),getPx(10));
        textView.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.down_arrow,0);
        params.setMargins(0,getPx(10),0,0);
        textView.setLayoutParams(params);
        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptionsDialog(title,textView,options,listener);
            }
        });
        return textView;
    }

    private void showOptionsDialog(String title, final TextView tvLabel, final String[] options, final DialogInterface.OnClickListener listener) {
        AlertDialog.Builder alert = new Builder(context);
        alert.setTitle(title);
        alert.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tvLabel.setText(options[which]);
                if(listener!=null){
                    listener.onClick(dialog,which);
                }
            }
        });
        alert.show();
    }

    public TextView generateDateTextView(String tag){
        final TextView tvDate = new TextView(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        tvDate.setText("Select");
        tvDate.setTag(tag);
        tvDate.setTextColor(Color.BLACK);
        tvDate.setOnFocusChangeListener(this);
        tvDate.setBackgroundResource(R.drawable.gray_border);
        tvDate.setTextAppearance(context,android.R.style.TextAppearance_Widget_EditText);
        params.setMargins(0,getPx(10),0,0);
        tvDate.setLayoutParams(params);
        tvDate.setPadding(getPx(10),getPx(11),getPx(10),getPx(11));
        tvDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(tvDate);
            }
        });
        return tvDate;
    }

    private void showDateDialog(final TextView tvDate) {
        Calendar c = Calendar.getInstance();
        DatePickerDialog dialog = new DatePickerDialog(context, new OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dob = String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth) + "-" + year;
                SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                try {
                    Date date = dateFormat.parse(dob);
                    dateFormat.applyPattern("dd MMM yy");
                    String strDob = dateFormat.format(date);
                    tvDate.setText(strDob);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
        dialog.getDatePicker().setMaxDate(new Date().getTime()-1000);
        dialog.show();
    }

    public CheckBox generateCheckBox(String tag,String value,boolean isChecked){
        CheckBox checkBox = new CheckBox(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(0,getPx(10),0,0);
        checkBox.setText(value);
        checkBox.setTag(tag);
        checkBox.setChecked(isChecked);
        checkBox.setTextColor(fields_color);
        checkBox.setLayoutParams(params);
        return checkBox;
    }

    public View generateSeparator(){
        View view = new View(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,getPx(1));
        params.setMargins(0,getPx(5),0,0);
        view.setLayoutParams(params);
        view.setBackgroundColor(Color.parseColor("#2299DD"));
        return view;
    }

    public RelativeLayout generateProgressBar(){
        RelativeLayout layout = new RelativeLayout(context);
        ProgressBar progressBar = new ProgressBar(context);
        progressBar.setId(R.id.progressBar);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setLayoutParams(params);
        layout.addView(progressBar);
        TextView tvLabel = new TextView(context);
        tvLabel.setText("Please wait...");
        tvLabel.setTextColor(Color.BLACK);
        tvLabel.setPadding(0,getPx(10),0,0);
        params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW,progressBar.getId());
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tvLabel.setLayoutParams(params);
        layout.addView(tvLabel);
        return layout;
    }

    public Button generateSubmitButton(String text){
        Button button = new Button(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(0,getPx(20),0,0);
        button.setLayoutParams(params);
        button.setBackgroundColor(fields_color);
        button.setTextColor(Color.WHITE);
        button.setTag("submit");
        button.setText(text);
        button.setAllCaps(false);
        return button;
    }

    public Button generateResendOTPButton(){
        Button button = new Button(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        params.setMargins(0,getPx(20),0,0);
        button.setLayoutParams(params);
        button.setBackgroundColor(fields_color);
        button.setTextColor(Color.WHITE);
        button.setTag("resend_otp");
        button.setText("Resend OTP");
        button.setAllCaps(false);
        return button;
    }

    public RelativeLayout generateOTPLayout(){
        RelativeLayout layout = new RelativeLayout(context);
        LinearLayout container = new LinearLayout(context);
        container.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.WHITE);
        RelativeLayout.LayoutParams relParams = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT);
        relParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        container.setLayoutParams(relParams);
        container.addView(generateLabelTextView("Enter OTP"));
        container.addView(generateEditText("otp",false));
        container.addView(generateSubmitButton("Submit"));
        container.addView(generateResendOTPButton());
        layout.setPadding(getPx(10),0,getPx(10),getPx(10));
        layout.addView(container);
        return layout;
    }

    public HashMap<String,String> getValuesMap(LinearLayout parentContainer){
        HashMap<String,String> map = new HashMap<>();
        for(int i = 0;i<parentContainer.getChildCount();i++){
            View view = parentContainer.getChildAt(i);
            if(view instanceof EditText){
                EditText editText = (EditText)view;
                map.put((String)editText.getTag(),editText.getText().toString().trim());
            }else if(view instanceof CheckBox){
                CheckBox checkBox = (CheckBox)view;
                map.put((String)checkBox.getTag(),String.valueOf(checkBox.isChecked()));
            }else if(view instanceof TextView){
                TextView textView = (TextView)view;
                map.put((String)textView.getTag(),textView.getText().toString());
            }else if(view instanceof LinearLayout){
                LinearLayout layout = (LinearLayout)view;
                EditText editText = (EditText) layout.getChildAt(3);
                EditText etCountryCode = (EditText)layout.getChildAt(1);
                map.put((String)etCountryCode.getTag(),etCountryCode.getText().toString().trim());
                map.put((String)editText.getTag(),editText.getText().toString().trim());
            }
        }
        return map;
    }

    public View getViewByTag(LinearLayout parentContainer,String tag){
        for(int i=0;i<parentContainer.getChildCount();i++){
            View view = parentContainer.getChildAt(i);
            String viewTag = (String)view.getTag();
            if(viewTag!=null && viewTag.equals(tag)){
                return view;
            }
        }
        return null;
    }

    public int getPx(int dp){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if(hasFocus){
            view.setBackgroundResource(R.drawable.focus_border);
        }else{
            view.setBackgroundResource(R.drawable.gray_border);
        }
    }
}
