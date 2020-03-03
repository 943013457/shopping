package com.test;

import com.Util.UserUtil;
import org.junit.Test;

/**
 * @Creator Ming
 * @Time 2019/8/15
 * @Other
 */
public class md5Test {
    @Test
    public void test() {
        String salt = UserUtil.getRandomSalt();
        String md5 = UserUtil.getPasswordCiph("1234", "007c73c99577fea8f1d176be00aefd36");
        //007c73c99577fea8f1d176be00aefd36 salt
        //dd1c06f90269b988b15422dbd477dd8a md5
        System.out.println(salt);
        System.out.println(md5);
    }
}
