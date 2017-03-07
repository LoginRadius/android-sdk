package com.loginradius.androidsdk.resource;

/**
 * Created by loginradius on 9/9/2016.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class CustomizeLanguageResponse {

    @SerializedName("socialprovider")
    @Expose
    private String socialprovider;
    @SerializedName("firstname")
    @Expose
    private String firstname;
    @SerializedName("lastname")
    @Expose
    private String lastname;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("emailid")
    @Expose
    private String emailid;
    @SerializedName("phonenumber")
    @Expose
    private String phonenumber;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("confirmpassword")
    @Expose
    private String confirmpassword;
    @SerializedName("birthdate")
    @Expose
    private String birthdate;
    @SerializedName("prefix")
    @Expose
    private String prefix;
    @SerializedName("suffix")
    @Expose
    private String suffix;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("postalcode")
    @Expose
    private String postalcode;
    @SerializedName("otpbutton")
    @Expose
    private String otpbutton;
    @SerializedName("otpedittext")
    @Expose
    private String otpedittext;
    @SerializedName("otpalertmessage")
    @Expose
    private String otpalertmessage;
    @SerializedName("verificationheader")
    @Expose
    private String verificationheader;
    @SerializedName("resetheader")
    @Expose
    private String resetheader;
    @SerializedName("Buttons")
    @Expose
    public List<CustomizeButton> buttons = new ArrayList<CustomizeButton>();
    @SerializedName("Gender")
    @Expose
    public List<Gender> gender = new ArrayList<Gender>();
    @SerializedName("Customfields")
    @Expose
    public List<CustomizeCustomfield> customfields = new ArrayList<CustomizeCustomfield>();
    @SerializedName("ValidationMessage")
    @Expose
    public List<ValidationMessage> validationMessage = new ArrayList<ValidationMessage>();
    @SerializedName("SuccessMessage")
    @Expose
    public List<CustomizeSuccessMessage> successMessage = new ArrayList<CustomizeSuccessMessage>();
    @SerializedName("ErrorMessage")
    @Expose
    public Object errorMessage;

    /**
     * @return The socialprovider
     */
    public String getSocialprovider() {
        return socialprovider;
    }

    /**
     * @param socialprovider The socialprovider
     */
    public void setSocialprovider(String socialprovider) {
        this.socialprovider = socialprovider;
    }

    /**
     * @return The firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname The firstname
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return The lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname The lastname
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return The emailid
     */
    public String getEmailid() {
        return emailid;
    }

    /**
     * @param emailid The emailid
     */
    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    /**
     * @return The phonenumber
     */
    public String getPhonenumber() {
        return phonenumber;
    }

    /**
     * @param phonenumber The phonenumber
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * @return The password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password The password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return The confirmpassword
     */
    public String getConfirmpassword() {
        return confirmpassword;
    }

    /**
     * @param confirmpassword The confirmpassword
     */
    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    /**
     * @return The birthdate
     */
    public String getBirthdate() {
        return birthdate;
    }

    /**
     * @param birthdate The birthdate
     */
    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * @return The prefix
     */
    public String getPrefix() {
        return prefix;
    }

    /**
     * @param prefix The prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * @return The suffix
     */
    public String getSuffix() {
        return suffix;
    }

    /**
     * @param suffix The suffix
     */
    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * @return The city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city The city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return The state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state The state
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return The country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country The country
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return The postalcode
     */
    public String getPostalcode() {
        return postalcode;
    }

    /**
     * @param postalcode The postalcode
     */
    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    /**
     * @return The otpbutton
     */
    public String getOtpbutton() {
        return otpbutton;
    }

    /**
     * @param otpbutton The otpbutton
     */
    public void setOtpbutton(String otpbutton) {
        this.otpbutton = otpbutton;
    }

    /**
     * @return The otpedittext
     */
    public String getOtpedittext() {
        return otpedittext;
    }

    /**
     * @param otpedittext The otpedittext
     */
    public void setOtpedittext(String otpedittext) {
        this.otpedittext = otpedittext;
    }

    /**
     * @return The otpalertmessage
     */
    public String getOtpalertmessage() {
        return otpalertmessage;
    }

    /**
     * @param otpalertmessage The otpalertmessage
     */
    public void setOtpalertmessage(String otpalertmessage) {
        this.otpalertmessage = otpalertmessage;
    }

    /**
     * @return The verificationheader
     */
    public String getVerificationheader() {
        return verificationheader;
    }

    /**
     * @param verificationheader The verificationheader
     */
    public void setVerificationheader(String verificationheader) {
        this.verificationheader = verificationheader;
    }

    /**
     * @return The resetheader
     */
    public String getResetheader() {
        return resetheader;
    }

    /**
     * @param resetheader The resetheader
     */
    public void setResetheader(String resetheader) {
        this.resetheader = resetheader;
    }

    /**
     * @return The buttons
     */
    public List<CustomizeButton> getButtons() {
        return buttons;
    }

    /**
     * @param buttons The Buttons
     */
    public void setButtons(List<CustomizeButton> buttons) {
        this.buttons = buttons;
    }

    /**
     * @return The gender
     */
    public List<Gender> getGender() {
        return gender;
    }

    /**
     * @param gender The Gender
     */
    public void setGender(List<Gender> gender) {
        this.gender = gender;
    }

    /**
     * @return The customfields
     */
    public List<CustomizeCustomfield> getCustomfields() {
        return customfields;
    }

    /**
     * @param customfields The Customfields
     */
    public void setCustomfields(List<CustomizeCustomfield> customfields) {
        this.customfields = customfields;
    }

    /**
            *
            * @return
            * The validationMessage
    */
    public List<ValidationMessage> getValidationMessage() {
        return validationMessage;
    }

    /**
     *
     * @param validationMessage
     * The ValidationMessage
     */
    public void setValidationMessage(List<ValidationMessage> validationMessage) {
        this.validationMessage = validationMessage;
    }



    /**
     *
     * @return
     * The successMessage
     */
    public List<CustomizeSuccessMessage> getSuccessMessage() {
        return successMessage;
    }

    /**
     *
     * @param successMessage
     * The SuccessMessage
     */
    public void setSuccessMessage(List<CustomizeSuccessMessage> successMessage) {
        this.successMessage = successMessage;
    }

    /**
     * @return The errorMessage
     */
    public Object getErrorMessage() {
        return errorMessage;
    }





}
