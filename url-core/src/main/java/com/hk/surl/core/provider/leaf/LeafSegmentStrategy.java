package com.hk.surl.core.provider.leaf;

import com.hk.surl.core.provider.GenerateStrategy;
import com.hk.surl.core.generator.Generator;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : HK意境
 * @ClassName : LeafSegmentStrategy
 * @date : 2022/4/18 19:40
 * @description : 美团发号器算法：Leaf-Segment
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(fluent = true)
public class LeafSegmentStrategy implements GenerateStrategy {

    // 该标签下的最大号码
    private Long maxId ;
    // 段: 每一次申请的一个号码段长度
    private Integer segment ;
    // 每次号码增加的步长
    private Integer step =2;
    // 当前发号器已经分发的到号码
    private Long cunrrent;

    public LeafSegmentStrategy() {
    }

    public LeafSegmentStrategy(Long maxId, Integer step, Long cunrrent) {
        this.maxId = maxId;
        this.step = step;
        this.cunrrent = cunrrent;
    }

    @Override
    public String provideShortUrl(Generator builder) {



        return null;
    }

}
