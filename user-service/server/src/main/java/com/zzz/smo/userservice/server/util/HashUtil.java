package com.zzz.smo.userservice.server.util;

import com.google.common.hash.HashCode;
import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;

import java.nio.charset.Charset;

/**
 * @Author: Sean
 * @Date: 2019/1/13 13:14
 */
public class HashUtil {
    private static final HashFunction FUNCTION = Hashing.sha256();
    private static final String SALT = "supermarket_salt";
    public static String encryPassword(String password){
        HashCode hashCode = FUNCTION.hashString(password+SALT, Charset.forName("UTF-8"));
        return hashCode.toString();
    }
}
