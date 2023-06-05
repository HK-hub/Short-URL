package com.hk.surl.v2.entity;


import lombok.Data;
import lombok.experimental.Accessors;


/**
 * @author : HK意境
 * @ClassName : ShortURI
 * @date : 2023/6/3 22:35
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class ShortURI {

    protected String longURI;

    protected String shortCode;

    protected String shortURI;

}
