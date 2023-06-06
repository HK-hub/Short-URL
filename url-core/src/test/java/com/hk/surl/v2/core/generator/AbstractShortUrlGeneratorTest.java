package com.hk.surl.v2.core.generator;

import cn.hutool.core.date.StopWatch;
import com.hk.surl.v2.core.generator.simple.DefaultShortUrlGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;


/**
 * @author : HK意境
 * @ClassName : AbstractShortUrlGeneratorTest
 * @date : 2023/6/4 18:08
 * @description :
 * @Todo :
 * @Bug :
 * @Modified :
 * @Version : 1.0
 */
@Slf4j
class AbstractShortUrlGeneratorTest {

    @Test
    void generate() {
        DefaultShortUrlGenerator generator = new DefaultShortUrlGenerator();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        for (int i = 0; i < 1000000; i++) {
            generator.generate(null);
        }
        stopWatch.stop();

        long totalTimeMillis = stopWatch.getTotalTimeMillis();
        log.info("Total time: {}", totalTimeMillis);
    }
}