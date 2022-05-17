package com.example.optionalcoursesfp.hashing;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    public String hashString(String string) throws NoSuchAlgorithmException {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(string.getBytes());
            byte[] digest = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : digest) {
                stringBuilder.append(String.format("%02x", b & 0xff));
            }
            return stringBuilder.toString();
        }

    }

