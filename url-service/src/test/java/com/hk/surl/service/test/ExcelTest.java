package com.hk.surl.service.test;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.hk.surl.common.entity.LongUrlDTO;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : ExcelTest
 * @date : 2022/6/15 22:12
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public class ExcelTest {

    private String filePath = "F:\\JavaCode\\Short-URL\\url-web\\src\\main\\resources\\template\\longUrlTemplate.xlsx";


    @Test
    public void read() throws FileNotFoundException {

        InputStream inputStream = new FileInputStream(new File(filePath));

        ExcelReader reader = ExcelUtil.getReader(inputStream);
        List<List<Object>> read = reader.read();


        //List<List<Object>> lists = ExcelUtil.getReader(inputStream).read();

        List<String> longUrls = new ArrayList<>();

        for (List<Object> list : read) {
            for (Object s : list) {
                longUrls.add((String) s);
            }
        }



    }


}
