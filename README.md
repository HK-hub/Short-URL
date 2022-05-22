# Short-Url 短链接生产器
## 背景
  在当前万物互联的时代，各种网络中的Web 应用，自媒体的数据传播已经到达了前所未有的高速时代，这当中 `url` 超链接承当了绝大多数的Web 网络上的数据访问工作。
  
随着 `url` 的爆炸生产，也产生了很多的问题：
- 复制容易出错，长链接URL较长，有时参数不止一个，复制容易遗漏或在粘贴时被编辑器截断；
- 容易被屏蔽，绝大部分长链接暴露了资源来源及分配策略，在投放第三方时容易被屏蔽，比如被短信屏蔽，（淘宝宝贝长链接）被微信屏蔽......；
- 如果企业存在大量的资源服务内容(参考B站视频),使用长链接不适合对每一个资源进行映射；
- URL链接过长不利于网站的SEO优化以及用户体验：过长的URL 在搜索引擎进行收录的时候需要进行一系列的处理，浏览器也对URL长度有限制；
- 严格规律的长`url` 里面可能回携带企业内部资源的敏感信息，可能回暴露企业内容: 例如文章，视频，图片等的资源的url 传输的时候如果不对名字进行特殊处理可能回暴露企业的类似的其他资源(但是此资源不希望用户可见)；
- ......

那么相比于我们的长链接，短链接到底有什么好处呢？除了有能够解决以上问题的能力外，还有以下的能力：
- 短链接相比我们的长链接更加的安全：可以保护企业内部资源，保护落地域名等。
- 短链接对于用户来说用户体验更好，短小，点击方便，操作简单。
- 节约空间，如一些社交网站会对字数有要求。
- 便于追踪，可做数据分析等用途。

所以我们有必要对一个长链接进行改造产生符合意愿的短链接，并且能够进行数据统计。其实这也是很多企业都一直在默默使用的功能：B站的全部视频都是经过短链接变换后的URL地址，淘宝分享商品的时候就是使用短链接，新浪的短链接，百度短的链接系统等等，可以说市场上对于短链接的需求相当大的。

那我们来看看市面上开源的短链接的解决方案：Github 里面排名考前的短链接系统的实现方法都很单一，要么就是随机算法，要么就是Redis 自增算法，提供的数据信息统计等功能更是没有.....

## 简介

`Short-Url` 短链接生成器是个人制作的一个集合了多种生成策略，加密安全配置，缓存配置等可选可自定义的短链接生成工具包。除了核心这些核心功能外，未来还将支持短链接访问信息统计，用户喜好数据统计等。
`Short-Url`目前已经开源出来啦，开源地址如下：

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
下面是一个简单的入门简单案例，需要引入我们的一下第三方包: `commons-codec` 来进行加密的操作，除此之外我们的`Short-URL` 还会提供`Template`模板直接进行快速生成：
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

**待办：**

- 302 重定向日志
- 短链接信息统计
- 用户偏好信息记录
- 压缩算法更新补充
- 发号器算法重构
- 雪花算法优化
- Spring Boot Starter 构建
- 发布Maven 仓库依赖



## 更新日志

- 1. 2022-4-20：发布了具备核心功能，基本功能的第一个版本。
- 2. 2022-4-18：构建了最基本的短链接生成算法: 随机算法，雪花算法，发号器算法，压缩算法，加密算法。
- 3. 2022-5-23：准备开始着手开发，短链接访问数据，用户画像数据等的搭建。