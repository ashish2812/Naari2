package com.educationhub.naari.LoginSignUP;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.educationhub.naari.R;

import java.util.Calendar;
import java.util.Objects;

public class DateOfBirth extends AppCompatActivity {

    DatePicker mDatePicker;
    String mName, mConfirmPassword, mPhoneNumber,mUserUid,mProfilePhotoUrl,mfromWhere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_of_birth);
        mDatePicker = findViewById(R.id.age_picker);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void add(View view) {
        if (!validateAge()) {
            return;
        }
        int day = mDatePicker.getDayOfMonth();
        int month = mDatePicker.getMonth();
        int year = mDatePicker.getYear();
        String dateOfBirth = day + "/" + month + "/" + year;


        mName = getIntent().getStringExtra("mName");
        mfromWhere = getIntent().getStringExtra("mFromWhere");
    //    mPhoneNumber = getIntent().getStringExtra("mPhoneNumber");
     //   mConfirmPassword = getIntent().getStringExtra("mConfirmPassword");
   //     mUserUid = getIntent().getStringExtra("mUserUid");
     //   mProfilePhotoUrl = getIntent().getStringExtra("mPersonPhotoURL");

        Intent intent = new Intent(getApplicationContext(), AddPeriods.class);
        intent.putExtra("mName", mName);
        intent.putExtra("mFromWhere", mfromWhere);

        if (Objects.equals(getIntent().getStringExtra("mFromWhere"), "fromPhoneNumberSignIn"))
        {
            mPhoneNumber = getIntent().getStringExtra("mPhoneNumber");
            mConfirmPassword = getIntent().getStringExtra("mConfirmPassword");
            intent.putExtra("mPhoneNumber", mPhoneNumber);
            intent.putExtra("mConfirmPassword", mConfirmPassword);

        }else{
            mUserUid = getIntent().getStringExtra("mUserUid");
            mProfilePhotoUrl = getIntent().getStringExtra("mPersonPhotoURL");
            intent.putExtra("mUserUid", mUserUid);
            intent.putExtra("mPersonPhotoURL", mProfilePhotoUrl);

        }
        

        intent.putExtra("mDateOfBirth", dateOfBirth);

        startActivity(intent);
    }

    private boolean validateAge() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int userAge = mDatePicker.getYear();
        int isAgeValid = currentYear - userAge;

        if (isAgeValid < 3) {
            Toast.makeText(this, "You are not eligible to apply", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }
}