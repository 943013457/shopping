package com.test;

import com.Util.UrlLink;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Creator Ming
 * @Time 2019/8/17
 * @Other
 */

public class UrlLinkTest {
    @Test
    public void test() {
        UrlLink.setSeparator("12312");

        List<String> list = new ArrayList<>();
        list.add("sdfdsfdsf");
        list.add("3w53253232");
        list.add("fghfghjfghfg");
        list.add("nbvnvbvbvb");
        list.add("retretreg");
        System.out.println(UrlLink.putAll(list));

    }
}
