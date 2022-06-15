package com.hk.surl.web.test;

import cn.hutool.poi.excel.ExcelUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : ExcelTest
 * @date : 2022/6/15 21:07
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class ExcelTest {

    private String filePath = "F:\\JavaCode\\Short-URL\\url-web\\src\\main\\resources\\template\\longUrlTemplate.xlsx";


    @Test
    public void readExcelFile() throws FileNotFoundException {

        InputStream inputStream = new FileInputStream(filePath);

        List<String> read = ExcelUtil.getReader(new File(filePath)).read(0, 1, String.class);
        //List<String> list = ExcelUtil.getReader(inputStream).read();

        read.forEach(System.out::println);

    }




}
