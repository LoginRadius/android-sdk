package com.loginradius.androidsdk.handler;

import com.google.gson.JsonObject;
import com.loginradius.androidsdk.response.CheckAvailability;
import com.loginradius.androidsdk.response.LoginRadiusContactCursorResponse;
import com.loginradius.androidsdk.response.PostAPIResponse;
import com.loginradius.androidsdk.response.album.LoginRadiusAlbum;
import com.loginradius.androidsdk.response.audio.LoginRadiusAudio;
import com.loginradius.androidsdk.response.checkin.LoginRadiusCheckIn;
import com.loginradius.androidsdk.response.company.LoginRadiusCompany;
import com.loginradius.androidsdk.response.customobject.CreateCustomObject;
import com.loginradius.androidsdk.response.customobject.ReadCustomObject;
import com.loginradius.androidsdk.response.event.LoginRadiusEvent;
import com.loginradius.androidsdk.response.following.LoginRadiusFollowing;
import com.loginradius.androidsdk.response.group.LoginRadiusGroup;
import com.loginradius.androidsdk.response.like.LoginRadiusLike;
import com.loginradius.androidsdk.response.login.LoginData;
import com.loginradius.androidsdk.response.lrAccessToken;
import com.loginradius.androidsdk.response.mention.LoginRadiusMention;
import com.loginradius.androidsdk.response.page.LoginRadiusPage;
import com.loginradius.androidsdk.response.password.ForgotPasswordData;
import com.loginradius.androidsdk.response.password.ForgotResponse;
import com.loginradius.androidsdk.response.phone.PhoneForgotPasswordResponse;
import com.loginradius.androidsdk.response.phone.PhoneResponse;
import com.loginradius.androidsdk.response.phonesendotp.PhoneSendOtpData;
import com.loginradius.androidsdk.response.photo.LoginRadiusPhoto;
import com.loginradius.androidsdk.response.post.LoginRadiusPost;
import com.loginradius.androidsdk.response.register.DeleteResponse;
import com.loginradius.androidsdk.response.register.RegisterResponse;
import com.loginradius.androidsdk.response.register.RegistrationData;
import com.loginradius.androidsdk.response.socialinterface.SocialInterface;
import com.loginradius.androidsdk.response.status.LoginRadiusStatus;
import com.loginradius.androidsdk.response.traditionalinterface.UserRegisteration;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;
import com.loginradius.androidsdk.response.video.LoginRadiusVideo;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by loginradius on 29-May-17.
 */

public interface ApiInterface {


    /**
     * Retrofit2 with RxJava GET
     */


    @GET
    Observable<LoginRadiusUltimateUserProfile> getUserProfile(@Url String url,@Query("access_token") String access_token,@Query("apikey") String apikey);


    @GET
    Observable<LoginRadiusAlbum[]> getAlbum(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusAudio[]> getAudio(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusCheckIn[]> getCheckin(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusCompany[]> getCompany(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusContactCursorResponse> getContact(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusEvent[]> getEvent(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusFollowing[]> getFollowing(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusGroup[]> getGroup(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusLike[]> getLike(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusMention[]> getMention(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusPhoto[]> getPhoto(@Url String url,@Query("access_token") String access_token,@Query("albumId") String albumId);


    @GET
    Observable<LoginRadiusPost[]> getPost(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusStatus[]> getStatus(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusVideo[]> getVideo(@Url String url,@Query("access_token") String access_token);


    @GET
    Observable<LoginRadiusPage> getPage(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<SocialInterface> getSocialProviderInterface(@Url String url);


    @GET
    Observable<List<UserRegisteration>>getTraditionalInterface(@Url String url);


    @GET
    Observable<lrAccessToken> getNativeLogin(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<LoginData> getTraditionalLogin(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<LoginRadiusUltimateUserProfile> getSocialProfile(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<CreateCustomObject> getReadCustomobjectById(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<ReadCustomObject> getReadCustomobjectByToken(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<RegisterResponse> getEmailPromptAutoLogin(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<LoginData> getEmailPromptAutoLoginPing(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<lrAccessToken> getValidateAccessToken(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<RegisterResponse> getInvalidateAccessToken(@Url String url,@QueryMap Map<String, String> options);


    @GET
    Observable<CheckAvailability> getPhoneNumberAvailability(@Url String url, @QueryMap Map<String, String> options);


    @GET
    Observable<PhoneSendOtpData> getphonesendOtp(@Url String url, @QueryMap Map<String, String> options);


    @POST
    Observable<PostAPIResponse> getMessage(@Url String url,@QueryMap Map<String, String> options);


    @POST
    Observable<PostAPIResponse> getStatusUpdate(@Url String url,@QueryMap Map<String, String> options);


    @POST
    Observable<RegisterResponse> getTraditionalRegister(@Url String url,@QueryMap Map<String, String> options, @Body RegistrationData data);


    @POST
    Observable<ForgotResponse> getForgotPasswordByEmail(@Url String url,@QueryMap Map<String, String> options,@Body ForgotPasswordData data);


    @POST
    Observable<PhoneForgotPasswordResponse> getForgotPasswordByPhone(@Url String url,@QueryMap Map<String, String> options, @Body ForgotPasswordData data);


    @POST
    Observable<RegisterResponse> getResendotp(@Url String url,@QueryMap Map<String, String> options, @Body JsonObject data);


    @POST
    Observable<PhoneResponse> getResendotpbytoken(@Url String url,@QueryMap Map<String, String> options);


    @POST
    Observable<RegisterResponse> getAddEmail(@Url String url,@QueryMap Map<String, String> options, @Body JsonObject data);


    @POST
    Observable<CreateCustomObject> getCreateCustomObject(@Url String url,@QueryMap Map<String, String> options, @Body JsonObject data);


    @PUT
    Observable<RegisterResponse> getChangePassword(@Url String url,@QueryMap Map<String, String> options,@Body JsonObject data);


    @PUT
    Observable<PhoneResponse> getUpdatephone(@Url String url,@QueryMap Map<String, String> options, @Body JsonObject data);


    @PUT
    Observable<RegisterResponse> getUpdateprofile(@Url String url,@QueryMap Map<String, String> options, @Body JsonObject data);


    @PUT
    Observable<LoginData> getOtpVerification(@Url String url,@QueryMap Map<String, String> options,@Body JsonObject data);


    @PUT
    Observable<RegisterResponse> getLinking(@Url String url,@QueryMap Map<String, String> options,@Body JsonObject data);


    @PUT
    Observable<RegisterResponse> getResendEmailVerification(@Url String url,@QueryMap Map<String, String> options,@Body JsonObject data);


    @PUT
    Observable<CreateCustomObject> getUpdateCustomObject(@Url String url,@QueryMap Map<String, String> options,@Body JsonObject data);


    @PUT
    Observable<RegisterResponse> getVerifyOtp(@Url String url,@QueryMap Map<String, String> options);


    @HTTP(method = "DELETE",hasBody = true)
    Observable<DeleteResponse> getRemoveEmail(@Url String url,@QueryMap Map<String, String> options, @Body JsonObject data);


    @HTTP(method = "DELETE",hasBody = true)
    Observable<DeleteResponse> getUnlinking(@Url String url,@QueryMap Map<String, String> options, @Body JsonObject jsonObject);


    @DELETE
    Observable<DeleteResponse> getDeleteCustomObject(@Url String url,@QueryMap Map<String, String> options);
}
