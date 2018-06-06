package com.nicqiang.commons.FileUtils;

import info.monitorenter.cpdetector.io.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by
 *  引用Github项目
 * @Author: nicqiang
 * @DATE: 2018/6/6
 */
public class CPDetector {
    private static final Logger logger = LoggerFactory.getLogger(CPDetector.class);
    /**
     * get file encode
     * @param path file path
     * @return file
     */
    public static String getFileEncode(String path) throws Exception{
        File file = new File(path);
        if(!file.exists()){
            throw new Exception("file not exist");
        }
        if(file.isDirectory()){
            throw new Exception("this is directory, not a file");
        }
        try {
            return getFileEncode(file.toURI().toURL());
        } catch (MalformedURLException e) {
            logger.error("get file encode failed",e);
        }
        return null;
    }

    /**
     * 获取文件编码
     * @param url uri
     * @return file encode
     */
    private static String getFileEncode(URL url) {

        /**
         * detector是探测器，它把探测任务交给具体的探测实现类的实例完成。
         * cpDetector内置了一些常用的探测实现类，这些探测实现类的实例可以通过add方法 加进来，如ParsingDetector、
         * JChardetFacade、ASCIIDetector、UnicodeDetector。
         * detector按照“谁最先返回非空的探测结果，就以该结果为准”的原则返回探测到的
         * 字符集编码。使用需要用到三个第三方JAR包：antlr.jar、chardet.jar和cpdetector.jar
         * cpDetector是基于统计学原理的，不保证完全正确。
         */
        CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();

        /**
         * ParsingDetector可用于检查HTML、XML等文件或字符流的编码,构造方法中的参数用于
         * 指示是否显示探测过程的详细信息，为false不显示。
         */
        detector.add(new ParsingDetector(false));

        /**
         * JChardetFacade封装了由Mozilla组织提供的JChardet，它可以完成大多数文件的编码
         * 测定。所以，一般有了这个探测器就可满足大多数项目的要求，如果你还不放心，可以
         * 再多加几个探测器，比如下面的ASCIIDetector、UnicodeDetector等。
         * 用到antlr.jar,chardet.jar
         */
        detector.add(JChardetFacade.getInstance());
        /**
         * ASCIIDetector用于ASCII编码测定
         */
        detector.add(ASCIIDetector.getInstance());
        /**
         * UnicodeDetector用于Unicode家族编码的测定
         */
        detector.add(UnicodeDetector.getInstance());
        Charset charset = null;
        try {
            charset = detector.detectCodepage(url);
        } catch (Exception e) {
            logger.error("detect error",e);
        }
        if (charset != null){
            return charset.name();
        }
        return null;
    }

}
