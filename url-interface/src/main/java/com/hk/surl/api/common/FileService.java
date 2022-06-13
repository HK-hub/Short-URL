package com.hk.surl.api.common;

import cn.hutool.core.io.resource.ClassPathResource;
import com.hk.surl.common.exception.BaseException;
import com.hk.surl.common.response.ResultCode;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * @author : HK意境
 * @ClassName : FileService
 * @date : 2022/6/13 20:58
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Service
public class FileService {

    /**
     * @methodName : downloadFile
     * @author : HK意境
     * @date : 2022/6/13 20:58
     * @description : 文件下载
     * @Todo :
     * @apiNote :
     * @params :
         * @param fileName 下载文件名称
     * @return Boolean
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    public Boolean downloadFile(String fileName, HttpServletResponse response) throws IOException {

        // 文件输入流
        InputStream inputStream = null ;

        // 输出到响应中
        ServletOutputStream outputStream = null;

        try{
            // 获取资源中的模板文件
            inputStream = new ClassPathResource(fileName).getStream();

            response.reset();
            response.setContentType("multipart/form-data");

            // 设置响应头
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(fileName.getBytes("UTF-8"), "iso8859-1") + ".xlsx");

            outputStream = response.getOutputStream();

            int b = 0;
            byte[] buffer = new byte[1024];
            while ((b = inputStream.read(buffer)) != -1) {
                //写到输出流(out)中
                outputStream.write(buffer, 0, b);
            }

            return true ;
        }catch(Exception e){
            throw new BaseException(ResultCode.RESOURCE_DOWNLOAD_OR_UPLOAD_ERROR);

        }finally {
            if (outputStream != null) {
                outputStream.flush();
                outputStream.close();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
    }


}
