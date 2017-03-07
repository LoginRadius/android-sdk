package com.loginradius.androidsdk.resource;

/**
 * Created by loginradius on 9/9/2016.
 */
public class DefaultLanguage {
    String language="{\n" +
            "  \n" +
            "  \"socialprovider\": \"login with\",\n" +
            "  \"firstname\": \"First Name\",\n" +
            "  \"lastname\": \"Last Name\",\n" +
            "  \"username\": \"User Name\",\n" +
            "  \"emailid\": \"Email\",\n" +
            "  \"phonenumber\": \"Phone Number\",\n" +
            "  \"password\": \"Password\",\n" +
            "  \"confirmpassword\": \"Confirm Password\",\n" +
            "  \"birthdate\": \"Birthdate\",\n" +
            "  \"prefix\": \"Prefix\",\n" +
            "  \"suffix\": \"Suffix\",\n" +
            "  \"city\": \"City\",\n" +
            "  \"state\": \"State\",\n" +
            "  \"country\": \"Country\",\n" +
            "  \"postalcode\": \"PostalCode\",\n" +
            "   \"otpbutton\": \"Submit\",\n" +
            "  \"otpedittext\": \"Enter OTP\",\n" +
            "  \"otpalertmessage\": \"Enter the OTP below in case \\nif we fail to detect the SMS automatically\",\n" +
            "   \"verificationheader\": \"Mobile Verification\",\n" +
            "   \"resetheader\": \"Reset Your Password\",\n" +
            "  \"Buttons\":[{\n" +
            "  \"loginbutton\": \"Login\",\n" +
            "  \"registerbutton\": \"Register\",\n" +
            "  \"forgetpasswordbutton\": \"Forgot Password\"}],\n" +
            "  \n" +
            "   \"Gender\":[{\n" +
            "   \"gender\": \"Gender\",\n" +
            "   \"gender1\": \"Male\",\n" +
            "   \"gender2\": \"Female\"}],\n" +
            "  \n" +
            "  \"Customfields\":[{\n" +
            "  \"customfields1\": \"CustomFields1\",\n" +
            "  \"customfields2\": \"CustomFields2\",\n" +
            "  \"customfields3\": \"CustomFields3\",\n" +
            "  \"customfields4\": \"CustomFields4\",\n" +
            "  \"customfields5\": \"CustomFields5\"}],\n" +
            "  \n" +
            "  \"ValidationMessage\":[{\"email\":\"Enter valid email address\",\n" +
            "                       \"password\":\"The Password field must be at least 6 characters in length\",\n" +
            "                        \"confermpassword\":\"The Confirm Password field does not match the Password field\",\n" +
            "                        \"requiredfield\":\"Field is required\"}],\n" +
            "  \n" +
            "  \"SuccessMessage\":[{\"registermessage\":\"Please Verify your Email\",\n" +
            "                     \"forgotpasswordmessage\":\"Please Check your Email\"\n" +
            "  \n" +
            "  }],\n" +
            "  \n" +
            " \"ErrorMessage\":[{ \"error901\":\"The API key is invalid\",\n" +
            "                   \"error902\":\"The API secret is invalid\",\n" +
            "                   \"error903\":\"Request token is invalid\",\n" +
            "                   \"error904\":\"Request token has expired\",\n" +
            "                   \"error905\":\"Access token is invalid\",\n" +
            "                   \"error906\":\"Access token has expired\",\n" +
            "                   \"error908\":\"A parameter is not formatted correctly\",\n" +
            "                   \"error909\":\"LoginRadius site does not have permission to access this endpoint\",\n" +
            "                   \"error910\":\"Endpoint is not supported by the current provider\",\n" +
            "                   \"error920\":\"API key is invalid\",\n" +
            "                   \"error921\":\"API secret is invalid\",\n" +
            "                   \"error922\":\"API key is missing\",\n" +
            "                   \"error923\":\"API secret is missing\",\n" +
            "                   \"error929\":\"Access token is missing from social ID provider\",\n" +
            "                   \"error930\":\"Token secret is missing from social ID provider\",\n" +
            "                   \"error931\":\"Album ID is required\",\n" +
            "                   \"error936\":\"Email address is already registered with your LoginRadius site\",\n" +
            "                   \"error937\":\"User ID is invalid\",\n" +
            "                   \"error938\":\"User does not exist\",\n" +
            "                   \"error939\":\"Status action is invalid\",\n" +
            "                   \"error946\":\"Permission does not exist\",\n" +
            "                   \"error947\":\"Interface does not exist\",\n" +
            "                   \"error949\":\"The site name is required\",\n" +
            "                   \"error950\":\"Operation failed due to an unknown error\",\n" +
            "                   \"error951\":\"Package does not exist\",\n" +
            "                   \"error954\":\"Permission is invalid\",\n" +
            "                   \"error955\":\"This provider does not support your selected configuration settings\",\n" +
            "                   \"error956\":\"The provider name is invalid\",\n" +
            "                   \"error958\":\"The post body is invalid\",\n" +
            "                   \"error959\":\"The status ID is invalid\",\n" +
            "                   \"error961\":\"Site name is invalid\",\n" +
            "                   \"error966\":\"Invalid user ID and/or password\",\n" +
            "                   \"error967\":\"Current password is invalid\",\n" +
            "                   \"error968\":\"The date has an invalid format\",\n" +
            "                   \"error970\":\"Email is not verified\",\n" +
            "                   \"error971\":\"Invalid request\",\n" +
            "                   \"error973\":\"Invalid email verification link\",\n" +
            "                   \"error981\":\"Site is not configured for RaaS (Registration-as-a-Service)\",\n" +
            "                   \"error983\":\"Account cannot be linked\",\n" +
            "                   \"error984\":\"Account ID is invalid\",\n" +
            "                   \"error986\":\"Account is already linked\",\n" +
            "                   \"error987\":\"Account cannot be unlinked\",\n" +
            "                   \"error988\":\"An account already exists for this email\",\n" +
            "                   \"error989\":\"Provider ID cannot be unlinked\",\n" +
            "                   \"error990\":\"This email is invalid\",\n" +
            "                   \"error991\":\"Your account is blocked\",\n" +
            "                   \"error993\":\"Custom field is invalid\",\n" +
            "                   \"error998\":\"Maximum limit reached for the custom object\",\n" +
            "                   \"error991\":\"Site is not configured for custom object setting\",\n" +
            "                   \"error1000\":\"An error has occurred at the social identity provider’s end\",\n" +
            "                   \"error1005\":\"User account should have at least one email address\",\n" +
            "                   \"error1006\":\"Invalid action for adding or removing email addresses\",\n" +
            "                   \"error1008\":\"This provider cannot be unlinked\",\n" +
            "                   \"error1015\":\"The new password is invalid\",\n" +
            "                   \"error1016\":\"Password cannot be changed. the profile ID is invalid\",\n" +
            "                   \"error1017\":\"This username is already registered with this website.\",\n" +
            "                   \"error1022\":\"Username does not exist\",\n" +
            "                   \"error1023\":\"Email address does not exist\",\n" +
            "                   \"error1024\":\"This username format is invalid\",\n" +
            "                   \"error1025\":\"This email address is already verified\",\n" +
            "                   \"error1026\":\"Cannot login with this social provider as email address is not yet verified\",\n" +
            "                   \"error1027\":\"You cannot login or register with this email address, as an account already exists for this email address\",\n" +
            "                   \"error1030\":\"Email address cannot be registered, as it is already registered under a social account\",\n" +
            "                   \"error1033\":\"Cannot login with this social provider as the same email address is already being used with another account\",\n" +
            "                   \"error1034\":\"Verification emails cannot be sent, as the email verification option is disabled for your site\",\n" +
            "                   \"error1037\":\"Account ID is required\",\n" +
            "                   \"error1039\":\"An email profile is not created or does not exist\",\n" +
            "                   \"error1043\":\"Phone number is invalid or does not exist\",\n" +
            "                   \"error1044\":\"Account ID is already registered with your site\",\n" +
            "                   \"error1046\":\"Email has already been registered with your LoginRadius site\",\n" +
            "                   \"error1048\":\"Secured one time token (sott) is required\",\n" +
            "                   \"error1049\":\"Secured one time token (sott) is invalid\",\n" +
            "                   \"error1051\":\"New social login and registration is not allowed\",\n" +
            "                   \"error1054\":\"Account linking is disabled\",\n" +
            "                   \"error1066\":\"Email is not allowed to register\",\n" +
            "                   \"error1058\":\"Phone number is already registered with your LoginRadius site\",\n" +
            "                   \"error1060\":\"Access token is missing\",\n" +
            "                   \"error1061\":\"Profile ID is missing\",\n" +
            "                   \"error1065\":\"Provider name or Provider Id is invalid\",\n" +
            "                   \"error1066\":\"Phone number is not verified\",\n" +
            "                   \"error1067\":\"Invalid OTP Code\",\n" +
            "                   \"error1068\":\"The OTP has already been used\",\n" +
            "                   \"error1069\":\"Verification OTP has expired\",\n" +
            "                   \"error1070\":\"The OTP cannot be accessed\",\n" +
            "                   \"error1071\":\"The SMS configuration not exist\",\n" +
            "                   \"error1072\":\"The Verification code (OTP) send failed\",\n" +
            "                   \"error1073\":\"This phone number is already verified\",\n" +
            "                   \"error1074\":\"Phone number login is not enabled\",\n" +
            "                   \"error1075\":\"Username is required\",\n" +
            "                   \"error1076\":\"Phone number is required\",\n" +
            "                   \"error1077\":\"Phone number login is enabled\",\n" +
            "                   \"error1079\":\"Put body is invalid\",\n" +
            "                   \"error1084\":\"Delete body is invalid\"\n" +
            "                   \n" +
            "                   \n" +
            "}]\n" +
            "\n" +
            "}";

    public String getLanguage() {
        return language;
    }

}
