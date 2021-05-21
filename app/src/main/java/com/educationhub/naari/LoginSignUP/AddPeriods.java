package com.educationhub.naari.LoginSignUP;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.educationhub.naari.HelperClass.UserHelperClassGoogleFb;
import com.educationhub.naari.HelperClass.UserHelperClassPhoneNumber;
import com.educationhub.naari.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Objects;

public class AddPeriods extends AppCompatActivity {

    private static Button continueBtn, addBtn;
    private String mName,mDateOfBirth;
    static String mPeriodsDate;
    static TextView txt_date1;
    static TextView reEnter;
    public static final String DATE_DIALOG_1 = "datePicker1";
    private static int mYear1;
    private static int mMonth1;
    private static int mDay1;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_periods);


        continueBtn = findViewById(R.id.continueBtn);
        addBtn = findViewById(R.id.addBtn);
        txt_date1 = findViewById(R.id.periodDateTV);
        reEnter = findViewById(R.id.reEnter);
        txt_date1.setVisibility(View.GONE);

        mName = getIntent().getStringExtra("mName");
        mDateOfBirth = getIntent().getStringExtra("mDateOfBirth");


        String mFromWhere = getIntent().getStringExtra("mFromWhere");
        Toast.makeText(this, mFromWhere,Toast.LENGTH_SHORT).show();

        if (getIntent().getStringExtra("mFromWhere").equals("fromPhoneNumberSignIn")) {
            String mConfirmPassword = getIntent().getStringExtra("mConfirmPassword");
            String mPhoneNumber = getIntent().getStringExtra("mPhoneNumber");
            Log.d("AddPeriods", mPhoneNumber +" "+ mConfirmPassword);
            Toast.makeText(this, mConfirmPassword + mPhoneNumber,Toast.LENGTH_SHORT).show();
        } else {

            String mUserUid = getIntent().getStringExtra("mUserUid");
            String mPersonPhotoURL = getIntent().getStringExtra("mPersonPhotoURL");
            Log.d("AddPeriods", mUserUid +" "+ mPersonPhotoURL);
            Toast.makeText(this, mFromWhere,Toast.LENGTH_SHORT).show();
           // intent.putExtra("mUserUid", mUserUid);
           // intent.putExtra("mPersonPhotoURL", mPersonPhotoURL);
            Toast.makeText(this, mUserUid + mPersonPhotoURL,Toast.LENGTH_SHORT).show();

        }
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment1 = new DatePickerFragment1();
                newFragment1.show(getSupportFragmentManager(), DATE_DIALOG_1);
            }
        });

        reEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment1 = new DatePickerFragment1();
                newFragment1.show(getSupportFragmentManager(), DATE_DIALOG_1);
            }
        });


    }

    public static String month(int monthNumber) {
        String month = "";

        switch (monthNumber) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "March";
                break;
            case 4:
                month = "April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "June";
                break;
            case 7:
                month = "July";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + monthNumber);
        }
        return month;

    }
    public void continueBtn(View view) {

        rootNode = FirebaseDatabase.getInstance();
        reference=rootNode.getReference("Users");
        if(getIntent().getStringExtra("mFromWhere").equals("fromPhoneNumberSignIn")){
            String mConfirmPassword = getIntent().getStringExtra("mConfirmPassword");
            String mPhoneNumber = getIntent().getStringExtra("mPhoneNumber");
            UserHelperClassPhoneNumber user = new UserHelperClassPhoneNumber(mName,mPhoneNumber,mConfirmPassword
            ,mDateOfBirth,mPeriodsDate);
            assert mPhoneNumber != null;
            reference.child(mPhoneNumber).setValue(user);


        }
        else{
            String mUserUid = getIntent().getStringExtra("mUserUid");
            String mPersonPhotoURL = getIntent().getStringExtra("mPersonPhotoURL");
            UserHelperClassGoogleFb user = new UserHelperClassGoogleFb(mName,mUserUid,mPersonPhotoURL,
                    mDateOfBirth,mPeriodsDate);
            assert mUserUid != null;
            reference.child(mUserUid).setValue(user);


        }


    }


    public static class DatePickerFragment1 extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // set default date

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);


            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), R.style.DatePicker1, this, year, month, day);

        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            continueBtn.setVisibility(View.VISIBLE);
            reEnter.setVisibility(View.VISIBLE);
            addBtn.setVisibility(View.GONE);
            mYear1 = year;
            mMonth1 = month;
            mDay1 = dayOfMonth;
            mPeriodsDate = dayOfMonth + "/" + month + "/" + year;
            txt_date1.setVisibility(View.VISIBLE);
            txt_date1.setText(new StringBuffer().append("Your Last Period Was on \n").append(mDay1 + " ").append(month(month + 1)));
        }
    }
}