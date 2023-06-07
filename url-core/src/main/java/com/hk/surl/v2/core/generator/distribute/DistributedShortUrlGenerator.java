package com.hk.surl.v2.core.generator.distribute;

import com.hk.surl.v2.core.config.DistributeConfig;
import com.hk.surl.v2.core.generator.AbstractShortUrlGenerator;
import com.hk.surl.v2.entity.ShortURLExt;
import com.hk.surl.v2.util.SnowFlakeIdWorker;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author : HK意境
 * @ClassName : DistributedShortUrlGenerator
 * @date : 2023/6/7 20:04
 * @description : 用于原始链接非常长的情况，并且系统每天生产大量短链接，对于短链接的数据安全敏感层度较低的业务场景
 *                  因为雪花算法获取到的分布式ID 是递增的，存在生成后的短链接不安全的问题，所以需要更具加密策略进行选择
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Data
@Accessors(chain = true)
public class DistributedShortUrlGenerator extends AbstractShortUrlGenerator<ShortURLExt> {

    protected DistributeConfig distributeConfig;

    protected SnowFlakeIdWorker idWorker;

    public DistributedShortUrlGenerator() {

        this.idWorker = new SnowFlakeIdWorker();
    }

    @Override
    public ShortURLExt preProcess() {
        return null;
    }

    @Override
    public ShortURLExt doGenerate(ShortURLExt shortUrl) {

        long id = this.idWorker.nextId();
        String code = String.valueOf(id);

        if (this.distributeConfig.isSafe()) {
            // 安全控制：
            code = Long.toHexString(id);
        }

        // 设置
        this.shortUrl.setShortCode(code)
                .setShortURI(code);

        return this.shortUrl;
    }

    @Override
    public ShortURLExt postProcess() {
        return null;
    }
}
