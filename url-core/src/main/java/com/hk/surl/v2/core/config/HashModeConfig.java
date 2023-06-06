package com.hk.surl.v2.core.config;

import com.hk.surl.v2.core.strategy.hash.HashStrategy;
import com.hk.surl.v2.core.strategy.hash.HashType;
import com.hk.surl.v2.util.HashUtil;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : HK意境
 * @ClassName : HashModeConfig
 * @date : 2023/6/5 19:04
 * @description : Hash 算法配置
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class HashModeConfig {

    /**
     * hash 算法采用类型，默认 MD5
     */
    protected HashType type = HashType.MurmurHash;

    /**
     * 自动长度控制：对于原始链接较短的url 采用hash 生成之后可能长度(md5 生成 32字符，murmurHash生成8个字符)会超过原来的长度。
     * true: 打开长度自动控制之后，将会对这种情况进行处理，对生成的 hash 值采用截取头，中，尾的方式进行生成小于原始链接的短链。
     * false: 关闭自动长度控制，将不会对生成之后的短链进行长度处理。
     */
    protected Boolean autoLengthControl = false;

}
