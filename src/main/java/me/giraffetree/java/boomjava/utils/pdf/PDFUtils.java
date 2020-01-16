package me.giraffetree.java.boomjava.utils.pdf;


import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;

public class PDFUtils {

    /**
     * 读PDF文件，使用了pdfbox开源项目
     *
     * @param fileName
     */
    public static String readPDF(String fileName) {
        File file = new File(fileName);
        FileInputStream in = null;
        try {
            in = new FileInputStream(fileName);
            // 新建一个PDF解析器对象
            PDFParser parser = new PDFParser(new RandomAccessFile(file, "rw"));
            // 对PDF文件进行解析
            parser.parse();
            // 获取解析后得到的PDF文档对象
            PDDocument pdfdocument = parser.getPDDocument();
            // 新建一个PDF文本剥离器
            PDFTextStripper stripper = new PDFTextStripper();
            // 从PDF文档对象中剥离文本
            return stripper.getText(pdfdocument);

        } catch (Exception e) {
            System.out.println("读取PDF文件" + file.getAbsolutePath() + "生失败！" + e);
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e1) {
                }
            }
        }
        return null;
    }


    /**
     * 测试pdf文件的创建
     *
     * @param args
     */
    public static void main(String[] args) {

//        String fileName = "C:\\Users\\pc\\Desktop\\彻底解剖g1gc-算法.pdf";
//        String result = readPDF(fileName);

    }
}