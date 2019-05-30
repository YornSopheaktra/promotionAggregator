package com.promotion.aggregate.request;

import com.google.gson.Gson;
import com.te.common.util.Debug;
import com.tmc.frmk.core.utils.HttpUtils;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class SendRequest {

    private String  uRl;
    private String dataRequest;

    public SendRequest(String uRl, JSONObject dataRequest){
        this.uRl=uRl;
        this.dataRequest=dataRequest.toString();
    }

    public SendRequest(String url,Map<String,Object>map){
    	this.uRl=url;
    	this.dataRequest=new Gson().toJson(map);
    }
    public JSONObject SendRequest(){
        JSONObject res=new JSONObject();
        Debug.debugObject("Module Url: ", this.uRl);
        Debug.debugObject("Check Module Request: ",this.dataRequest);
        try{
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
            HttpEntity<String> entity = new HttpEntity<String>(dataRequest, headers);
            String response = HttpUtils.post(uRl, entity,String.class);
            res = new JSONObject( response);
        }catch (Exception e){
            e.printStackTrace();
        }
        Debug.debugObject("Check Module Response: ",res);
        return res;
    }
}
