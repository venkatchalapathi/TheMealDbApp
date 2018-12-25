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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.venky.capstoneproject.R;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername;
    private EditText etPassword;
    private Button btGo;
    private CardView cv;
    private FloatingActionButton fab;
    private AdView mAdView;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;
    AlertDialog.Builder a_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dialog = new ProgressDialog(this);
        a_dialog = new AlertDialog.Builder(this);
        getSupportActionBar().setTitle(R.string.login);
        mAuth = FirebaseAuth.getInstance();
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        btGo = findViewById(R.id.bt_go);
        cv = findViewById(R.id.cv);
        fab = findViewById(R.id.fab);
        setListener();
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
        Glide.with(LoginActivity.this)
                .load(R.drawable.anim).into(imageView);
        builder.show();
    }

    private void showProgress() {

        dialog.setTitle(getString(R.string.login_prog_title));
        dialog.setMessage(getString(R.string.login_prog_msg));
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 4) {
            if (resultCode == Activity.RESULT_OK) {
                etUsername.setText(data.getStringExtra(RegisterActivity.EMAIL_KEY));
                etPassword.setText(data.getStringExtra(RegisterActivity.PWD_KEY));
            }
        }
    }

    private void setListener() {
        btGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    if (isNetworkAvailable()) {
                        showProgress();
                        mAuth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            dialog.dismiss();
                                            Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                        } else {
                                            dialog.dismiss();
                                            Toast.makeText(LoginActivity.this, R.string.auth_failed,
                                                    Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                    } else {
                        dialog.dismiss();
                        showAlert();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, R.string.please_enter, Toast.LENGTH_SHORT).show();
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 4);
            }
        });
    }

    public void forgotPassword(View view) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View v = inflater.inflate(R.layout.forgot_screen, null);
        final EditText mail = v.findViewById(R.id.resetmail);
        a_dialog.setView(v);
        a_dialog.setTitle(R.string.for_titile);
        a_dialog.setPositiveButton(R.string.forgot_po_btn, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialogInterface, int i) {
                if (isNetworkAvailable()) {
                    showProgress();
                    String sMail = mail.getText().toString();
                    FirebaseAuth.getInstance().sendPasswordResetEmail(sMail)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, R.string.reset_success, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(LoginActivity.this, R.string.reset_failed, Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }
                            });
                }else {
                    showAlert();
                }
            }
        });
        a_dialog.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        a_dialog.show();

    }
}
