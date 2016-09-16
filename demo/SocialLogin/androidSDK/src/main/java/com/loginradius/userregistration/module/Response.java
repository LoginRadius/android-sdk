package com.loginradius.userregistration.module;


public class Response {
    Action actionReponse;
    boolean isSuccess;;
    String errorMSG;
    Provider provider;
    /**
     * Constructor for Error response
     * @param action
     * @param isSuccess
     * @param msg
     */
    public Response(Action action,boolean isSuccess,String msg){
        this.actionReponse = action;
        this.isSuccess = isSuccess;
        this.errorMSG = msg;
    }

    /**
     * Constructor for native login
     * @param action
     * @param isSuccess
     * @param provider
     */
    public Response(Action action,boolean isSuccess,Provider provider){
        this.actionReponse = action;
        this.isSuccess = isSuccess;
        this.provider = provider;
    }
    /**
     * Constructor for success response
     * @param action
     */
    public Response(Action action){
        this(action,true,"");
    }

    @Override
    public String toString() {
        return "Response{" +
                "actionReponse=" + actionReponse +
                ", isSuccess=" + isSuccess +
                ", errorMSG='" + errorMSG + '\'' +
                '}';
    }

    public Action getActionReponse() {
        return actionReponse;
    }

    public void setActionReponse(Action actionReponse) {
        this.actionReponse = actionReponse;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorMSG() {
        return errorMSG;
    }

    public void setErrorMSG(String errorMSG) {
        this.errorMSG = errorMSG;
    }

    public enum Action{login,registration,forget,socialregistration,tokennull}

    public enum Provider {facebook, google, twitter, linkedin, yahoo, live,
        persona ,  wordpress ,  vkontakte ,  aol ,  myopenid ,  mixi ,  steamcommunity ,  hyves ,  livejournal ,
        verisign ,  virgilio ,  orange ,  github ,  openid ,  renren ,  kaixin ,  qq ,  stackexchange  }
}
