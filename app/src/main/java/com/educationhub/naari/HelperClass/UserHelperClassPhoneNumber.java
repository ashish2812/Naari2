package com.educationhub.naari.HelperClass;

public class UserHelperClassPhoneNumber {
    String mName,mPhoneNumber, mConfirmPassword,mDateOfBirth,mLastPeriod;

    public UserHelperClassPhoneNumber(){};

    public UserHelperClassPhoneNumber(String mName, String mPhoneNumber, String mConfirmPassword, String mDateOfBirth, String mLastPeriod) {
        this.mName = mName;
        this.mPhoneNumber = mPhoneNumber;
        this.mConfirmPassword = mConfirmPassword;
        this.mDateOfBirth = mDateOfBirth;
        this.mLastPeriod = mLastPeriod;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmConfirmPassword() {
        return mConfirmPassword;
    }

    public void setmConfirmPassword(String mConfirmPassword) {
        this.mConfirmPassword = mConfirmPassword;
    }

    public String getmDateOfBirth() {
        return mDateOfBirth;
    }

    public void setmDateOfBirth(String mDateOfBirth) {
        this.mDateOfBirth = mDateOfBirth;
    }

    public String getmLastPeriod() {
        return mLastPeriod;
    }

    public void setmLastPeriod(String mLastPeriod) {
        this.mLastPeriod = mLastPeriod;
    }
}
