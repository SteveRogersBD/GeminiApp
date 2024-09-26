package com.example.chatgptapi;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Prepare the request
        ArrayList<ChatGPTRequest.Message> messages = new ArrayList<>();
        messages.add(new ChatGPTRequest.Message("user", "Hello, how are you?"));
        ChatGPTRequest request = new ChatGPTRequest("gpt-3.5-turbo", messages);

        // Make the request
        Call<ChatGPTResponse> call = apiService.getChatResponse(request);
        call.enqueue(new Callback<ChatGPTResponse>() {
            @Override
            public void onResponse(Call<ChatGPTResponse> call, Response<ChatGPTResponse> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    ChatGPTResponse data = response.body();
                    if (data != null && data.getChoices() != null && !data.getChoices().isEmpty()) {
                        String reply = data.getChoices().get(0).getMessage().getContent();
                        Log.d(TAG, "ChatGPT Reply: " + reply);
                        Toast.makeText(MainActivity.this, reply, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "No response available", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Convert response code to a string
                    String errorMessage = "Error: " + response.code();
                    Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    Log.e(TAG, errorMessage);
                }
            }

            @Override
            public void onFailure(Call<ChatGPTResponse> call, Throwable t) {
                Log.e(TAG, "Failure: " + t.getMessage());
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

