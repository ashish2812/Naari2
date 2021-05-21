package com.educationhub.naari.LoginSignUP;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.educationhub.naari.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class AuthenticationActivity extends AppCompatActivity {

    TextView mName, mDescription;
    String nName, nConfirmPassword, nPhoneNumber,backOtp,enteredOtp;
    PinView pinView;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);


        mName = findViewById(R.id.nameTv);
        mDescription = findViewById(R.id.description);
        pinView = findViewById(R.id.PinView);

        mName.setText(getIntent().getStringExtra("mName"));
        String description = "Enter the OTP sent to " + getIntent().getStringExtra("mPhoneNumber") + " if \n your phone number is in this phone you don\'t \nneed to enter.";
        mDescription.setText(description);


        nName = getIntent().getStringExtra("mName");
        nConfirmPassword = getIntent().getStringExtra("mConfirmPassword");
        nPhoneNumber = getIntent().getStringExtra("mPhoneNumber");
        backOtp = getIntent().getStringExtra("backEndOtp");
        Log.d("PhoneNumber", nPhoneNumber);

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void continueButton(View view) {

        enteredOtp = Objects.requireNonNull(pinView.getText()).toString().trim();
        Log.d("OTP",enteredOtp);
        if (enteredOtp.isEmpty() || enteredOtp.length() < 6) {
            Toast.makeText(AuthenticationActivity.this, "Code Error", Toast.LENGTH_SHORT).show();
            pinView.requestFocus();
            return;
        }
        Log.d("OTP",enteredOtp);
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
            backOtp,enteredOtp);
        FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Variefied",Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    assert user != null;

                    Intent intent = new Intent(getApplicationContext(),DateOfBirth.class);
                    intent.putExtra("mName",nName);
                    intent.putExtra("mConfirmPassword",nConfirmPassword);
                    intent.putExtra("mPhoneNumber",nPhoneNumber);
                    intent.putExtra("mFromWhere","fromPhoneNumberSignIn");
                    Toast.makeText(getApplicationContext(), user.getUid(),Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"failed",Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}