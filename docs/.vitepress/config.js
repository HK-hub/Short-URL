// vitepress/config.js
module.exports = {

    // 网站配置
    title: "Short-Url", // 网站标题
    // 网站描述
    // tagline: "Short-Url 短链接生成器是一个集合了多种生成策略，加密安全配置，缓存配置,支持短链接访问信息统计，用户喜好数据统计的可高度自定义的短链接生成工具包。"
    //description: `Short-Url 短链接生成器是一个集合了多种生成策略，加密安全配置，缓存配置，<br> 支持访问信息统计，用户数据统计的可高度自定义的短链接生成工具包。`,


    lang: 'zh-CN',
    base: '/Short-URL/', //  部署时的路径 默认 /  可以使用二级地址 /base/


    // 网页头部配置，引入需要图标，css，js
    head: [
        // 改变title的图标
        [
            'link',
            {
                rel: 'icon',
                href: '/img/short-url.png', //图片放在public文件夹下
            },
        ],
    ],


    // 主题配置
    themeConfig: {
        logo: '/img/short-url.png',
        repo: 'https://github.com/HK-hub/Short-URL', // 你的 github 仓库地址，网页的右上角会跳转
        repoLabel: 'GitHub',
        docsDir: 'docs',
        docsBranch: 'website',
        editLinks: true,
        editLinkText: '欢迎帮助我们改善页面!',
        lastUpdated: '最近更新时间',
        //   头部导航
        nav: [{
                text: '首页',
                link: '/'
            },
            {
                text: '入门指南',
                link: '/quick_start/'
            },
            {
                text: '在线体验',
                items: [
                    { text: 'v1.x', link: '/use/v1/' },
                    { text: 'v2.x', link: '/use/v2/' },
                ]
            },

            {
                text: '相关文档',
                //link: '/document/'
                items: [
                    { text: '开始使用', link: '/start/' },
                    { text: '开放API', link: '/open_api/' },
                ]
            },
            {
                text: '模板配置',
                link: '/config/'
            },
            {
                text: '关于项目',
                link: '/about/'
            },
        ],
        //   侧边导航
        sidebar: [
            { text: '我的', link: '/mine/' }
        ]
    }
}