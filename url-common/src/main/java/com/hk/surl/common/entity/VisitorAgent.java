package com.hk.surl.common.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author : HK意境
 * @ClassName : VisitorAgent
 * @date : 2022/6/14 11:32
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VisitorAgent {

    // 浏览器
    private String browser ;
    // 平台操作系统
    private String platform ;



}
