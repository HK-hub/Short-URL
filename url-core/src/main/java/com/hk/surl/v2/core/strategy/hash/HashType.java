package com.hk.surl.v2.core.strategy.hash;

import com.hk.surl.v2.util.HashUtil;

/**
 * @ClassName : HashType
 * @author : HK意境
 * @date : 2023/6/5 19:54
 * @description : Hash 枚举算法实现
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
public enum HashType implements HashStrategy {

    // MD5 摘要算法
    MD5() {
        @Override
        public String hash(String plain) {
            return HashUtil.md5(plain);
        }
    },

    // MurmurHash 一种非加密型哈希函数，适用于一般的哈希检索操作
    MurmurHash() {
        @Override
        public String hash(String plain) {
            return HashUtil.murmurHash(plain);
        }
    },

    // 针对短消息设计的伪随机函数
    SipHash() {
        @Override
        public String hash(String plain) {
            return null;
        }
    },
}