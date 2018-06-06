package com.nicqiang.commons.FileUtils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by
 *
 * @Author: nicqiang
 * @DATE: 2018/6/6
 */
public class CPDetectorTest {
    @Test
    public void getFileEncode() throws Exception {
        String path = "/Users/nicqiang/my.txt";
        System.out.println(CPDetector.getFileEncode(path));
    }

}