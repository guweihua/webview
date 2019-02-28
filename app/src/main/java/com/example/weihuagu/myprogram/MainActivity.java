package com.example.weihuagu.myprogram;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import wendu.webviewjavascriptbridge.WVJBWebView;

public class MainActivity extends AppCompatActivity {

    private WVJBWebView wvjbwebview;

    String url = "http://720yun.com/t/aog8omq6xqsr9oayij&uid=9&PHPSESSID=2uaqcki80afhp6qr4ef6nj181r";
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        wvjbwebview = findViewById(R.id.wvjbwebview);
        webview = findViewById(R.id.webview);


        wvjbwebview.loadUrl(url);


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return false;
            }
        });



    }
}
