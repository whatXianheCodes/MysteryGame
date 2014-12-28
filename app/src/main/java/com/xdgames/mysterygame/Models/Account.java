package com.xdgames.mysterygame.Models;

/**
 * Created by Xianhe on 12/25/2014.
 */
public class Account {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String invitationCode;

    public Account (String firstName, String lastName, String username,
                    String password, String email, String invitationCode ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.invitationCode = invitationCode;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getFirstName () {
        return this.firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getUsername () {
        return this.username;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPassword () {
        return this.password;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getInvitationCode() {
        return this.invitationCode;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    public boolean checkInvitation () {
        //TODO: add security check to determine whether the code is valid
        final String INVITE_CODE_PATTERN = "^[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}-[A-Za-z0-9]{4}$";
        return this.invitationCode.matches(INVITE_CODE_PATTERN);
    }

    public boolean checkName () {
        final String NAME_PATTERN = "[-A-Za-z]{1,}";
        return (this.firstName.matches(NAME_PATTERN) && this.lastName.matches(NAME_PATTERN));
    }

    public boolean checkEmail () {
        return checkEmail(this.email);
    }

    public boolean checkPassword () {
        return (this.password.length()!= 0);
    }

    public boolean checkConfirmPassword (String passwordConfirmValue) {
        return (this.password.equals(passwordConfirmValue));
    }

    public boolean checkUsername () {
        return (this.username.length() != 0);
    }

    public static boolean checkEmail (String email) {
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        return email.matches(EMAIL_PATTERN);
    }
}
