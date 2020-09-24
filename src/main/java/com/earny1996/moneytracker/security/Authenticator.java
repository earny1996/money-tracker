package com.earny1996.moneytracker.security;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Authenticator {

    private static Authenticator me;

    public static Authenticator getInstance(){
        if(me == null){
            me = new Authenticator();
        }
        return me;
    }

    public String generateHash(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] bytes = md.digest(password.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, bytes);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;

        } catch (NoSuchAlgorithmException nsae){
            throw new RuntimeException(nsae);
        }
    }

    public boolean authenticate(String passwordToCheck, String userPassword){
        return userPassword.equals(generateHash(passwordToCheck));
    }
}