package com.sevketbuyukdemir.qr_code_scanner;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;


import androidx.appcompat.app.AppCompatActivity;

public class QrWebSite extends AppCompatActivity {
    public Context context = this;
    private WebView webView;
    private String current_qr_url;
    CustomWebViewClient webViewClient;

    private void init(){
        webView = findViewById(R.id.webView);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_website);
        init();
        Intent intent = getIntent();
        current_qr_url = intent.getStringExtra("current_url");

        webViewClient = new CustomWebViewClient();
        webView = findViewById(R.id.webView);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.setWebViewClient(webViewClient);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(current_qr_url);
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return url.startsWith(current_qr_url);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);

        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Intent intent = new Intent(context, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            AlertDialog.Builder exit = new AlertDialog.Builder(context);
            exit.setTitle(getString(R.string.exit_alert_title));
            exit.setMessage(getString(R.string.exit_alert_message));
            exit.setCancelable(true);
            exit.setPositiveButton(getString(R.string.exit_alert_do_not_exit_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            exit.setNegativeButton(getString(R.string.exit_alert_exit_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    QrWebSite.super.onBackPressed();
                }
            });
            exit.show();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
