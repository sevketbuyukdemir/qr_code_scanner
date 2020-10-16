package com.sevketbuyukdemir.qr_code_scanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;


import androidx.appcompat.app.AppCompatActivity;

public class QRText extends AppCompatActivity {
    private EditText qr_code_edit_text;
    private String qr_text;
    private Context context = this;

    private void init(){
        qr_code_edit_text = findViewById(R.id.qr_code_edit_text);
        qr_text = "this is a qr code text for copy and paste";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_text);
        init();
        Intent intent = getIntent();
        qr_text = intent.getStringExtra("current_text");
        qr_code_edit_text.setText(qr_text);
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
                QRText.super.onBackPressed();
            }
        });
        exit.show();
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
