package com.abchina.http;

/**
 * @author jerrylz
 * @date 2021/2/27
 */
public class RequestFacade extends RequestWrapper {

    private Request request;

    public RequestFacade(Request request){
        this.request = request;
    }

    public RequestFacade(String requestInfo){
        this.request = new Request(requestInfo);
    }

}
