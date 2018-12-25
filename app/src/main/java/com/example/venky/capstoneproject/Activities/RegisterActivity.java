package com.example.venky.capstoneproject.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.venky.capstoneproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private CardView cvAdd;
    EditText u_name;
    EditText password;
    public static final String EMAIL_KEY = "email_key";
    public static final String PWD_KEY = "password_key";
    ProgressDialog dialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        dialog = new ProgressDialog(this);
        getSupportActionBar().setTitle("User Registration");
        mAuth = FirebaseAuth.getInstance();
        fab = findViewById(R.id.fab);
        cvAdd = findViewById(R.id.cv_add);
        u_name = findViewById(R.id.et_username);
        password = findViewById(R.id.et_repeatpassword);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }

        });
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager conMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED
                || conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        } else {
            return false;
        }

    }

    public void showAlert() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_msg);
        builder.setTitle(R.string.alert_title);
        builder.setPositiveButton(R.string.alert_pos_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(
                        Settings.ACTION_WIRELESS_SETTINGS));
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        setContentView(R.layout.error_loading_screen);
        ImageView imageView = findViewById(R.id.errorimg);
        Glide.with(RegisterActivity.this)
                .load(R.drawable.anim).into(imageView);
        builder.show();
    }

    private void showProgress() {

        dialog.setTitle(getString(R.string.reg_prog_title));
        dialog.setMessage(getString(R.string.reg_prog_msg));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    public void go(View view) {
        final String email = u_name.getText().toString();
        final String pswd = password.getText().toString();
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pswd)) {
            if (isNetworkAvailable()) {
                showProgress();
                mAuth.createUserWithEmailAndPassword(email, pswd)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Intent intent = new Intent();
                                    intent.putExtra(EMAIL_KEY, email);
                                    intent.putExtra(PWD_KEY, pswd);
                                    setResult(Activity.RESULT_OK, intent);
                                    Toast.makeText(RegisterActivity.this, R.string.reg_success, Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    finish();
                                } else {
                                    dialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, R.string.reg_failed, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            } else {
                dialog.dismiss();
                showAlert();
            }
        } else {
            Toast.makeText(this, R.string.please_enter, Toast.LENGTH_SHORT).show();
        }
    }
}

