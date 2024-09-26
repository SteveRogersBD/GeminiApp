package com.example.chatgptapi;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @Headers({
            "Content-Type: application/json",
            "Authorization: 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" // Replace with your actual API key
    })
    @POST("v1/chat/completions")
    Call<ChatGPTResponse> getChatResponse(@Body ChatGPTRequest request);
}
