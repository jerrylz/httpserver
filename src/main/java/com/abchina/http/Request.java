package com.abchina.http;

/**
 * @author jerrylz
 * @date 2021/2/27
 */
public class Request extends RequestWrapper {

    private String requestInfo;

    public Request(String requestInfo){
        this.requestInfo = requestInfo;
    }
}
