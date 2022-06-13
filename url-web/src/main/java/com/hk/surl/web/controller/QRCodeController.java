package com.hk.surl.web.controller;

import com.hk.surl.common.response.ResponseResult;
import com.hk.surl.common.util.QRCodeUtil;
import com.hk.surl.domain.vo.ShortUrlVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * @author : HK意境
 * @ClassName : QRCodeController
 * @date : 2022/6/13 19:46
 * @description : 二维码测试
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
@RestController("/qr")
public class QRCodeController {

    /**
     * @methodName : getQrCode
     * @author : HK意境
     * @date : 2022/6/13 19:52
     * @description : 同一个 url 链接地址生成的二维码是同一个
     * @Todo :
     * @apiNote : 生成content链接的二维码
     * @params :
         * @param content 需要跳转的url 链接
     * @return ResponseResult<ShortUrlVo>
     * @throws:
     * @Bug :
     * @Modified :
     * @Version : 1.0.0
     */
    @GetMapping("/get")
    public ResponseResult<ShortUrlVo> getQrCode(@RequestParam(name = "content") String content , HttpServletResponse response){

        //log.info("content: {}",content);
        try {
            QRCodeUtil.createCodeToOutputStream(content, response.getOutputStream());

        } catch (IOException e) {
            log.error("生成二维码错误,错误信息是：{}", e.getMessage());
        }
        return ResponseResult.SUCCESS().setData(new ShortUrlVo(content, "https://blog.csdn.net/private_name/article/details/124144802"));
    }


}
