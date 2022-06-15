package com.hk.surl.service.util;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.hk.surl.common.entity.LongUrlDTO;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : HK意境
 * @ClassName : ExcelResolveService
 * @date : 2022/6/15 21:35
 * @description : excel 文件解析生成处理服务类
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class ExcelResolveService {

    /**
     * @methodName : resolveImportExcel
     * @author : HK意境
     * @date : 2022/6/15 21:45
     * @description :
     * @Todo :
     * @apiNote 解析导入的excel 文件
     * @params :
         * @param excelStream 文件输入流
     * @return null
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    public List<String> resolveImportExcel(InputStream excelStream) throws IOException {

        // 通过输入流拿到数据
        ExcelReader reader = ExcelUtil.getReader(excelStream);
        List<List<Object>> read = reader.read(1);


        // 构造结果集
        List<String> longUrls = new ArrayList<>();
        for (List<Object> objects : read) {
            for (Object object : objects) {
                longUrls.add((String) object);
            }
        }

        return longUrls ;
    }


}
