package com.hk.surl.domain.dbo.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : HK意境
 * @ClassName : ShortURL
 * @date : 2022/4/13 21:27
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortURL {

    String longUrl ;
    String shortUrl ;

}
