package com.Util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @Creator Ming
 * @Time 2019/8/15
 * @Other 加密算法
 */
public class UserLoginAndRegister {
    public static String getRandomSalt() {
        return new SecureRandomNumberGenerator().nextBytes().toHex();
    }

    public static String getPasswordCiph(String password, String salt) {
        if (salt == null) {
            return new Md5Hash(password, null, 3).toString();
        }
        return new Md5Hash(password, salt, 3).toString();
    }

}
