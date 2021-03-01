package com.abchina.http;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author jerrylz
 * @date 2021/3/1
 */
public class HttpWrapper {

    private static final String CRLF = "\r\n";
    private static final String SP = " ";
    private static final String HEADER_SP = ":";
    //请求行
    private Map<String, String> requestLineMap = new HashMap<>();
    //请求头
    private Map<String, String> requestHeaderMap = new HashMap<>();
    //参数
    private Map<String, List<String>> parameterMap = new HashMap<>();


    public void parseRequestInfo(String requestInfo){
        if(StrUtil.isBlank(requestInfo)){
            return;
        }

        String[] reqArray = StrUtil.split(requestInfo, CRLF);

        for(int i = 0; i < reqArray.length; i++){
            if(i == 0){
                //解析请求行
                parseRequestLine(reqArray[i]);
            }else{
                if(StrUtil.isNotBlank(reqArray[i])){
                    //非空白行，则解析请求头
                    parseRequestHeader(reqArray[i]);
                }
            }
        }

    }

    /**
     * 解析请求行
     * @param requestLine
     */
    private void parseRequestLine(String requestLine) {
        //请求行格式GET /do HTTP/1.1
        //以空格分割
        String[] lineArray = StrUtil.split(requestLine, SP);
        for(int i = 0; i < lineArray.length; i++){
            if(i == 0){
                this.requestLineMap.put("method",lineArray[0].trim());
            }else if(i == 1){
                this.requestLineMap.put("url", lineArray[1].trim());
            }else if(i == 2){
                this.requestLineMap.put("httpVersion", lineArray[2].trim());
            }
        }
    }

    /**
     * 解析请求头数据
     * @param requestHeader
     */
    private void parseRequestHeader(String requestHeader){
        //请求头格式 XXX:XXX
        String[] headerArray = StrUtil.split(requestHeader, HEADER_SP);
        this.requestHeaderMap.put(headerArray[0], headerArray[1]);
    }
}
