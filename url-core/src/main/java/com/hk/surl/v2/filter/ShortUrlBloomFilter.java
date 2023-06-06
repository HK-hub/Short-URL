package com.hk.surl.v2.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.hk.surl.v2.core.config.BloomFilterConfig;
import lombok.Data;
import lombok.experimental.Accessors;
import java.nio.charset.StandardCharsets;

/**
 * @author : HK意境
 * @ClassName : ShortUrlBloomFilter
 * @date : 2023/6/6 19:46
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class ShortUrlBloomFilter {

    private static BloomFilter<String> instance;

    /**
     * 布隆过滤器配置
     */
    private BloomFilterConfig config;

    public ShortUrlBloomFilter() {
        // 采用默认配置
        this(new BloomFilterConfig());
    }

    /**
     * 传入 bloom filter 配置
     * @param config
     */
    public ShortUrlBloomFilter(BloomFilterConfig config) {
        this.config = config;

        // 初始化 BloomFilter
        this.init();
    }


    /**
     * 初始化
     */
    private void init() {
        instance = BloomFilter.create(Funnels.stringFunnel(StandardCharsets.UTF_8),
                this.config.getInsertions(), this.config.getFpp());
    }


    public static BloomFilter<String> getInstance() {
        return instance;
    }

    /**
     * 添加元素
     * @param value
     */
    public boolean addElement(String value) {
        return instance.put(value);
    }

    /**
     * 是否可能包含元素：如果不存在则一定不存在
     * @param value
     * @return
     */
    public boolean containsElement(String value) {
        return instance.mightContain(value);
    }


}
