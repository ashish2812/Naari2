package com.educationhub.naari.LoginSignUP;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.educationhub.naari.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "Google SignUp";
    EditText mPhoneNumber, mName, mConfirmPassword, mPassword;
    CountryCodePicker mCountryCodePicker;
    ProgressBar progressBar;
    Button signupButton;
    ImageView googleSignInButton;
    LoginButton facebookSignIn;
    ImageView twitterSignIn;
    GoogleSignInClient mGoogleSignInClient;
    private static int RC_SIGN_IN = 100;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        hookUps();
        mCallbackManager = CallbackManager.Factory.create();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInMethod();
            }
        });
        facebookSignIn.setReadPermissions("email", "public_profile");
        facebookSignIn.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });

    }



    private void googleSignInMethod() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            Intent intent = new Intent(getApplicationContext(),DateOfBirth.class);
                            intent.putExtra("mName",user.getDisplayName());
                            intent.putExtra("mUserUid",user.getUid());
                          //  intent.putExtra("mUserUid", user.getPhoneNumber());
                            intent.putExtra("mPersonPhotoURL", Objects.requireNonNull(user.getPhotoUrl()).toString());
                            Log.d("PhoneUrl", Objects.requireNonNull(user.getPhotoUrl()).toString());
                            intent.putExtra("mFromWhere","fromGoogleSignIn");
                            Toast.makeText(SignUpActivity.this, "Authentication Success."+user.getDisplayName()+user.getPhoneNumber(),
                                    Toast.LENGTH_SHORT).show();

                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            assert user != null;
                            Intent intent = new Intent(getApplicationContext(),DateOfBirth.class);
                            intent.putExtra("mName",user.getDisplayName());
                            intent.putExtra("mUserUid",user.getUid());
                            intent.putExtra("mPhoneNumber",user.getPhoneNumber());
                            intent.putExtra("mPersonPhotoURL", Objects.requireNonNull(user.getPhotoUrl()).toString());
                            intent.putExtra("mFromWhere","fromFacebookLogin");
                            startActivity(intent);

                            Toast.makeText(SignUpActivity.this, "Authentication Success."+user.getDisplayName()+user.getPhoneNumber(),
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }






    public void logIn(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void signUp(View view) {
        if (!validateName() | !validatePhoneNumber()) {
            return;
        }
        if (!validateNewPassword() || !validateConfirmPassword()) {
            return;
        }

        final String name = mName.getText().toString();
        String phoneNumber = mPhoneNumber.getText().toString();
        final String phoneNumberWithCountryCode = "+" + mCountryCodePicker.getFullNumber() + phoneNumber;
        final String confirmPassword = mConfirmPassword.getText().toString().trim();
        Log.d("Texts", name + " " + phoneNumberWithCountryCode + " " + confirmPassword);
        progressBar.setVisibility(View.VISIBLE);
        signupButton.setVisibility(View.GONE);
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumberWithCountryCode)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                            }

                            @Override
                            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(), AuthenticationActivity.class);
                                intent.putExtra("mName", name);
                                intent.putExtra("mPhoneNumber", phoneNumberWithCountryCode);
                                intent.putExtra("mConfirmPassword", confirmPassword);
                                intent.putExtra("backEndOtp", s);
                                startActivity(intent);
                            }
                        })
                        // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }


    public void hookUps() {
        mPhoneNumber = findViewById(R.id.logInPhoneNumber);
        mPassword = findViewById(R.id.logInPassword);
        mConfirmPassword = findViewById(R.id.confirmPassword);
        mName = findViewById(R.id.signUpName);
        mCountryCodePicker = findViewById(R.id.SignIn_CountryCodePicker);
        progressBar = findViewById(R.id.progress_circular);
        signupButton = findViewById(R.id.signUpButton);
        googleSignInButton = findViewById(R.id.googleSignin);
        facebookSignIn = findViewById(R.id.facebookSignin);
        twitterSignIn = findViewById(R.id.twitterSignin);
    }


    private Boolean validateName() {
        String val = mName.getText().toString();
        if (val.isEmpty()) {
            mName.setError("Field cannot be Empty");
            return false;
        } else
            mName.setError(null);
        return true;
    }

    private Boolean validatePhoneNumber() {
        String val = mPhoneNumber.getText().toString();
        if (val.isEmpty()) {
            mPhoneNumber.setError("Field cannot be Empty");
            return false;

        } else if (val.length() <= 9) {
            mPhoneNumber.setError("Please Check the number");
            return false;
        } else
            mPhoneNumber.setError(null);
        return true;


    }

    private Boolean validateNewPassword() {
        String value = mPassword.getText().toString().trim();
        if (value.isEmpty()) {
            mPassword.setError("Field cannot be empty");
            return false;
        } else {
            mPassword.setError(null);
            return true;
        }

    }

    private Boolean validateConfirmPassword() {
        String confirm = mConfirmPassword.getText().toString().trim();
        if (confirm.isEmpty()) {
            mConfirmPassword.setError("Field cannot be empty");
            return false;


        } else if (!mPassword.getText().toString().equals(confirm)) {

            mConfirmPassword.setError("Password didn't match");
            return false;
        } else {
            mConfirmPassword.setError(null);
            return true;
        }

    }


}