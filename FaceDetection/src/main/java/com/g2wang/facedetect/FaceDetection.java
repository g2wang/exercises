package com.g2wang.facedetect;

import java.io.File;
import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.*;
import com.cloudmersive.client.FaceApi;
import com.cloudmersive.client.model.FaceLocateResponse;

public class FaceDetection {

    public static void main(String[] args) {

        ApiClient defaultClient = Configuration.getDefaultApiClient();
        // Configure API key authorization: Apikey
        ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
        Apikey.setApiKey(System.getenv("CLOUDMERSIVE_KEY"));
        // Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
        //Apikey.setApiKeyPrefix("Token");
        FaceApi apiInstance = new FaceApi();
        File imageFile = new File("/Users/guangdewang/Pictures/sample-photo.jpg"); // File | Image file to perform the operation on.  Common file formats such as PNG, JPEG are supported.
        try {
            FaceLocateResponse result = apiInstance.faceLocate(imageFile);
            System.out.println(result);
        } catch (ApiException e) {
            System.err.println("Exception when calling FaceApi#faceLocate");
            e.printStackTrace();
        }

    }

}
