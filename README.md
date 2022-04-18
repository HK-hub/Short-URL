# Short-Url 短链接生产器
## 简介
`Short-Url` 短链接生成器是个人制作的一个集合了多种生成策略，加密安全配置，缓存配置等可选可自定义的短链接生成工具包。
目前已经开源出来啦，开源地址如下：

https://github.com/HK-hub/Short-URL
## 功能
**Short-Url** 短链接生成工具包提供了多种短链接生成算法，包括：字符串池随机算法，短地址发号器算法，MD5加密截取算法，BASE64编码截取算法，URL压缩算法，分布式雪花算法等。
此外集成了线程安全配置，缓存配置，加密配置，其他配置等可选可自定义的配置，可以根据自己的需求进行配置。实现对于普通 `URL` 长链接到短链接的转换，缓存，加密，以及对于已经生成的短链接的解密解析，重定向等功能。

## 安装
目前 `Short-Url` 短链接生成工具包还处于开发,天使测试阶段，暂时不支持编译安装，只支持使用 `maven` 或者 `gradle` 安装。`Short-Url` 目前已经打包成为一个 `Starter`, 使用时候只需要导入依赖 `Jar`包即可，如下：

```xml
<dependency>
    <groupId>com.hk</groupId>
    <artifactId>short-url-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 快速开始
下面是一个简单的入门简单案例，需要引入我们的一下第三方包: `commons-codec` 来进行加密的操作
```java
public class ShortUrlTest {

    @Test
    public void test(){
        // 获取 generator 生成器
        Generator generator = new ShortUrlGeneratorBuilder(new ShortUrlExt("https://www.github.com/hk-hub"))
                .length(6)
                .generateStrategy(new RandomStringStrategy())
                .enableCache(false)
                .encryptStrategy(EncryptStrategy.NONE)
                .build();

        // 使用生成器进行链接生成
        ShortURL shortURL = generator.generate();
        System.out.println(shortURL);
    }
}
```
更多的使用方式请参考 `Short-Url`的源码工程，根据里面的策略来进行配置，选择。


## 使用
**Short-Url** 短链接生成器内置了多种短链接生成算法策略，可以根据自己的需求选择使用，如下：
- 字符串池随机算法：`StringPoolRandomStrategy`
- 发号器算法：`SnowFlakeStrategy`
- MD5加密截取算法：`Md5Strategy`
- BASE64编码截取算法：`Base64Strategy`
- URL压缩算法：`UrlCompressStrategy`
- 分布式ID生成算法：`DistributedIdStrategy`

除此之外还聚合了多种缓存配置，加密配置，线程安全配置等可选可自定义的配置，可以根据自己的需求进行配置。


| 线程安全配置   | 缓存配置   | 其他配置   |
| ---- | ---- | ---- |
|   默认配置   | 永久有效   | 短链多级长度配置    |
|   乐观锁配置   | 多级过期时间    | 短链接加密配置    |
|   加锁配置   | 自定义过期时间    | 短链接解析配置    |
|      |不缓存    |  短链接访问配置   |

除此之外，`Short-Url`短链接生成器也提供了几个已经配置好的短链接生成模板，可以直接使用来快速生成和控制。

![短链生成算法.drawio](https://s2.loli.net/2022/04/18/jCYfxiB9grOFtPW.png)

## 其他

欢迎各位喜欢的小伙伴，加入我们一起进行创作，设计和开发

## 反馈

任何有关于 **Short-Url** 工具包的相关疑问，出现的Bug, 错误等都可以联系邮箱：3161880795@qq.com 进行及时的交流与反馈。


## 关于
- 关于作者：作者是一个潜心修炼的 Java 学生，路漫漫其修远兮，吾将上下而求索。
- 作者邮箱：3161880795@qq.com
- Github地址：https://www.github.com/hk-hub/
## 版本
**Short-Url** 目前还是处于第一个版本(天使版) ，还有很多的缺陷和漏洞需要进行修复弥补，也还有很多的功能需要进行扩展，所有静待等候哦。

待办：

- 302 重定向日志
- 短链接信息统计
- 用户偏好记录
- 压缩算法更新补充
- 发号器算法重构
- 雪花算法优化



## 更新日志

2022-4-20：发布了具备核心功能，基本功能的第一个版本。
2022-4-18：构建了最基本的短链接生成算法: 随机算法，雪花算法，发号器算法，压缩算法，加密算法