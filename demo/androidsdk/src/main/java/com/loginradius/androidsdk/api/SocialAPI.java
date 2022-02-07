package com.loginradius.androidsdk.api;

import com.loginradius.androidsdk.helper.LoginRadiusSDK;
import com.loginradius.androidsdk.resource.QueryMapHelper;
import com.loginradius.androidsdk.handler.ApiInterface;
import com.loginradius.androidsdk.handler.AsyncHandler;
import com.loginradius.androidsdk.handler.ExceptionResponse;
import com.loginradius.androidsdk.handler.RestRequest;
import com.loginradius.androidsdk.resource.Endpoint;
import com.loginradius.androidsdk.resource.QueryParams;
import com.loginradius.androidsdk.response.LoginRadiusContactCursorResponse;
import com.loginradius.androidsdk.response.PostAPIResponse;
import com.loginradius.androidsdk.response.album.LoginRadiusAlbum;
import com.loginradius.androidsdk.response.audio.LoginRadiusAudio;
import com.loginradius.androidsdk.response.checkin.LoginRadiusCheckIn;
import com.loginradius.androidsdk.response.company.LoginRadiusCompany;
import com.loginradius.androidsdk.response.event.LoginRadiusEvent;
import com.loginradius.androidsdk.response.following.LoginRadiusFollowing;
import com.loginradius.androidsdk.response.group.LoginRadiusGroup;
import com.loginradius.androidsdk.response.like.LoginRadiusLike;
import com.loginradius.androidsdk.response.mention.LoginRadiusMention;
import com.loginradius.androidsdk.response.page.LoginRadiusPage;
import com.loginradius.androidsdk.response.photo.LoginRadiusPhoto;
import com.loginradius.androidsdk.response.post.LoginRadiusPost;
import com.loginradius.androidsdk.response.status.LoginRadiusStatus;
import com.loginradius.androidsdk.response.userprofile.LoginRadiusUltimateUserProfile;
import com.loginradius.androidsdk.response.video.LoginRadiusVideo;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 12-Oct-17.
 */

public class SocialAPI {
    private ApiInterface apiService = RestRequest.getClient().create(ApiInterface.class);

    public SocialAPI() {
        if (!LoginRadiusSDK.validate()) {
            throw new LoginRadiusSDK.InitializeException();
        }
    }

    public void updateStatus(QueryParams queryParams, final AsyncHandler<PostAPIResponse> handler) {
        apiService.getStatusUpdate(Endpoint.API_V2_STATUS, QueryMapHelper.getMapStatusUpdate(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new DisposableObserver<PostAPIResponse>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(PostAPIResponse response) {
                        handler.onSuccess(response);
                    }});
    }

    public void postMessage(QueryParams queryParams, final AsyncHandler<PostAPIResponse> handler) {
        apiService.getMessage(Endpoint.API_V2_MESSAGE, QueryMapHelper.getMapMessage(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<PostAPIResponse>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);}

                    @Override
                    public void onNext(PostAPIResponse response) {
                        handler.onSuccess(response);
                    }});}

    public void getAlbum(QueryParams queryParams, final AsyncHandler<LoginRadiusAlbum[]> handler) {
        apiService.getAlbum(Endpoint.API_V2_ALBUM,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusAlbum[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);}
                    @Override
                    public void onNext(LoginRadiusAlbum[] response) {
                        handler.onSuccess(response);
                    }

                });}

    public void getAudio(QueryParams queryParams, final AsyncHandler<LoginRadiusAudio[]> handler) {
        apiService.getAudio(Endpoint.API_V2_AUDIO,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusAudio[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);}
                    @Override
                    public void onNext(LoginRadiusAudio[] response) {
                        handler.onSuccess(response);
                    }
                });}

    public void getCheckIn(QueryParams queryParams, final AsyncHandler<LoginRadiusCheckIn[]> handler) {
        apiService.getCheckin(Endpoint.API_V2_CHECKIN,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusCheckIn[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusCheckIn[] response) {
                        handler.onSuccess(response);
                    }});}

    public void getCompany(QueryParams queryParams,final AsyncHandler<LoginRadiusCompany[]> handler) {
        apiService.getCompany(Endpoint.API_V2_COMPANY,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusCompany[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusCompany[] response) {
                        handler.onSuccess(response);
                    }});}


    public void getContact(QueryParams queryParams,final AsyncHandler<LoginRadiusContactCursorResponse> handler) {
        apiService.getContact(Endpoint.API_V2_CONTACT,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusContactCursorResponse>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusContactCursorResponse response) {
                        handler.onSuccess(response);
                    }});}

    public void getEvent(QueryParams queryParams,final AsyncHandler<LoginRadiusEvent[]> handler) {
        apiService.getEvent(Endpoint.API_V2_EVENT,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusEvent[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusEvent[] response) {
                        handler.onSuccess(response);
                    }

                });}

    public void getFollowing(QueryParams queryParams,final AsyncHandler<LoginRadiusFollowing[]> handler) {
        apiService.getFollowing(Endpoint.API_V2_FOLLOWING,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusFollowing[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusFollowing[] response) {
                        handler.onSuccess(response);
                    }});}


    public void getGroup(QueryParams queryParams,final AsyncHandler<LoginRadiusGroup[]> handler) {
        apiService.getGroup(Endpoint.API_V2_GROUP,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusGroup[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);}
                    @Override
                    public void onNext(LoginRadiusGroup[] response) {
                        handler.onSuccess(response);
                    }

                });}

    public void getLike(QueryParams queryParams,final AsyncHandler<LoginRadiusLike[]> handler) {
        apiService.getLike(Endpoint.API_V2_LIKE,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusLike[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusLike[] response) {
                        handler.onSuccess(response);
                    }

                });}


    public void getMention(QueryParams queryParams,final AsyncHandler<LoginRadiusMention[]> handler) {
        apiService.getMention(Endpoint.API_V2_MENTION,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusMention[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusMention[] response) {
                        handler.onSuccess(response);
                    }});}


    public void getPage(QueryParams queryParams, final AsyncHandler<LoginRadiusPage> handler){
        apiService.getPage(Endpoint.API_V2_PAGE, QueryMapHelper.getMapPage(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusPage>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusPage response) {
                        handler.onSuccess(response);
                    }

                });}

    public void getPhoto(QueryParams queryParams,final AsyncHandler<LoginRadiusPhoto[]> handler) {
        apiService.getPhoto(Endpoint.API_V2_PHOTO, QueryMapHelper.getMapPhoto(queryParams)).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusPhoto[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusPhoto[] response) {
                        handler.onSuccess(response);
                    }});}


    public void getPost(QueryParams queryParams,final AsyncHandler<LoginRadiusPost[]> handler) {
        apiService.getPost(Endpoint.API_V2_POST,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusPost[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);
                    }
                    @Override
                    public void onNext(LoginRadiusPost[] response) {
                        handler.onSuccess(response);
                    }

                });}

    public void getStatus(QueryParams queryParams,final AsyncHandler<LoginRadiusStatus[]> handler) {
        apiService.getStatus(Endpoint.API_V2_STATUS,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusStatus[]>() {
                    @Override
                    public void onComplete() {}
                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);}
                    @Override
                    public void onNext(LoginRadiusStatus[] response) {
                        handler.onSuccess(response);
                    }});}




    public void getVideo(QueryParams queryParams,final AsyncHandler<LoginRadiusVideo[]> handler) {
        apiService.getVideo(Endpoint.API_V2_VIDEO,queryParams.getAccess_token()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<LoginRadiusVideo[]>() {
                    @Override
                    public void onComplete() {}

                    @Override
                    public void onError(Throwable e) {
                        ExceptionResponse exceptionResponse = ExceptionResponse.HandleException(e);
                        handler.onFailure(exceptionResponse.t, exceptionResponse.message);

                    }
                    @Override
                    public void onNext(LoginRadiusVideo[] response) {
                        handler.onSuccess(response);
                    }

                });}
}
