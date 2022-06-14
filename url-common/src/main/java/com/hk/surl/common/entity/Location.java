package com.hk.surl.common.entity;

import lombok.Data;
import lombok.ToString;

/**
 * @author : HK意境
 * @ClassName : Location
 * @date : 2022/6/14 11:19
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@ToString
public class Location {

    private String country ;
    private String province ;
    private String city ;
    private String latitude;
    private String longitude ;


}
