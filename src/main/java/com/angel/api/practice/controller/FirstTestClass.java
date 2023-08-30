package com.angel.api.practice.controller;


import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import com.angel.api.practice.errorhandle.ApiResponse;
import com.angel.api.practice.model.Login;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@RestController
@RequestMapping(path = "/Api")
public class FirstTestClass {

	private final ObjectMapper objectMapper = new ObjectMapper();
	
    @ResponseBody
    @PostMapping(path = "/home",produces="application/json", consumes="application/json")
    public ApiResponse login(@org.springframework.web.bind.annotation.RequestBody Login  login)  {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
                   
            String requestBodyJson =objectMapper.writeValueAsString(login);
              
            RequestBody body = RequestBody.create(mediaType, requestBodyJson);


		String name;
            
            Request request = new Request.Builder().url("https://apiconnect.angelbroking.com/rest/auth/angelbroking/user/v1/loginByPassword")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("X-UserType", "USER")
                    .addHeader("X-SourceID", "WEB")
                    .addHeader("X-ClientLocalIP", "CLIENT_LOCAL_IP")
                    .addHeader("X-ClientPublicIP", "CLIENT_PUBLIC_IP")
                    .addHeader("X-MACAddress", "MAC_ADDRESS")
                    .addHeader("X-PrivateKey", "RCvbyZRP")
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                
             // Parse the JSON response body
                JsonNode responseJson = objectMapper.readTree(responseBody);

                return new ApiResponse(true, false, responseJson);
            } else {
                return new ApiResponse(false, false, "Request failed with response code: " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ApiResponse(false, true, e.getMessage());
        }
    }

}

