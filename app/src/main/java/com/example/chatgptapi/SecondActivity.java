package com.example.chatgptapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatgptapi.databinding.ActivitySecondBinding;
import com.example.chatgptapi.models.ChatModel;
import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;

import com.google.ai.client.generativeai.BuildConfig;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SecondActivity extends AppCompatActivity {

    ActivitySecondBinding binding;
    ChatAdapter chatAdapter;
    List<ChatModel>messageList;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySecondBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        messageList = new ArrayList<>();
        chatAdapter = new ChatAdapter(SecondActivity.this,messageList);
        binding.mainRecycler.setAdapter(chatAdapter);
        LinearLayoutManager lm = new LinearLayoutManager(SecondActivity.this);
        binding.mainRecycler.setLayoutManager(lm);


        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!binding.textEt.getText().equals(""))
                {
                    String query = binding.textEt.getText().toString();
                    ChatModel newMessage = new ChatModel(query,ChatModel.SENT_BY_ME);
                    messageList.add(newMessage);
                    chatAdapter.notifyDataSetChanged();
                    binding.mainRecycler.smoothScrollToPosition(messageList.size());
                    callGemini(query);
                }
            }
        });


    }

    private void callGemini(String query){
        String apiKey = getString(R.string.geminiKey);
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash",apiKey);
        GenerativeModelFutures model = GenerativeModelFutures.from(gm);

        Content content =
                new Content.Builder().addText(query).build();

// For illustrative purposes only. You should use an executor that fits your needs.
        Executor executor = Executors.newSingleThreadExecutor();

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(
                response,
                new FutureCallback<GenerateContentResponse>() {
                    @Override
                    public void onSuccess(GenerateContentResponse result) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //textView.setText(result.getText());
                                ChatModel replyMessage = new ChatModel(result.getText(),ChatModel.SENT_BY_BOT);
                                messageList.add(replyMessage);
                                chatAdapter.notifyDataSetChanged();
                                binding.mainRecycler.smoothScrollToPosition(messageList.size());

                            }
                        });
                    }


                    @Override
                    public void onFailure(Throwable t) {
                        t.printStackTrace();
                    }
                },
                executor);
    }
}