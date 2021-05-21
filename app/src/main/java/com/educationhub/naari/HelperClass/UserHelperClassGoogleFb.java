package com.educationhub.naari.HelperClass;

public class UserHelperClassGoogleFb {
    String Name,UserUid,ProfilePicture,DateOfBirth,LastPeriod;
    public UserHelperClassGoogleFb(){}

    public UserHelperClassGoogleFb(String name, String userUid, String profilePicture, String dateOfBirth, String lastPeriod) {
        Name = name;
        UserUid = userUid;
        ProfilePicture = profilePicture;
        DateOfBirth = dateOfBirth;
        LastPeriod = lastPeriod;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getUserUid() {
        return UserUid;
    }

    public void setUserUid(String userUid) {
        UserUid = userUid;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        ProfilePicture = profilePicture;
    }

    public String getDateOfBirth() {
        return DateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        DateOfBirth = dateOfBirth;
    }

    public String getLastPeriod() {
        return LastPeriod;
    }

    public void setLastPeriod(String lastPeriod) {
        LastPeriod = lastPeriod;
    }
}
