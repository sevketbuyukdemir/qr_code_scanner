package com.sevketbuyukdemir.qr_code_scanner;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.regex.*;


public class MainActivity extends AppCompatActivity {
    Button scanQRCodeButton;
    public Context context = this;
    private static final int MY_PERMISSIONS_CAMERA = 1;
    private static final int MY_PERMISSIONS_INTERNET = 2;

    private void init() {
        scanQRCodeButton = findViewById(R.id.scan_button);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        if(!(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) ){
            requestCameraPermission();
        }
        if(!(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED) ){
            requestInternetPermission();
        }

        scanQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
                    startActivityForResult(intent, 0);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == 0) {
            String qrResultText = "null";
            try {
                qrResultText = intent.getStringExtra("SCAN_RESULT");
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            if (resultCode == RESULT_OK) {
                if (isWebSite(qrResultText)) {
                    Intent web_view_intent = new Intent(context, QrWebSite.class);
                    web_view_intent.putExtra("current_url", qrResultText);
                    startActivity(web_view_intent);
                } else {
                    Intent text_area_intent = new Intent(context, QRText.class);
                    text_area_intent.putExtra("current_text", qrResultText);
                    startActivity(text_area_intent);
                }
            }
        }


    }

    @Override
    public void onBackPressed() {
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
                MainActivity.super.onBackPressed();
            }
        });
        exit.show();
    }

    private static boolean isWebSite(String s) {
        String regex = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
        try {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(s);
            return matcher.matches();
        } catch (RuntimeException e) {
            return false;
        }
    }

    private void requestCameraPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.CAMERA},
                    MY_PERMISSIONS_CAMERA);
        }else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA},
                    MY_PERMISSIONS_CAMERA);
        }
    }

    private void requestInternetPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)){
            ActivityCompat.requestPermissions(MainActivity.this,new String[] {Manifest.permission.INTERNET},
                    MY_PERMISSIONS_INTERNET);
        }else{
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.INTERNET},
                    MY_PERMISSIONS_INTERNET);
        }
    }

}


