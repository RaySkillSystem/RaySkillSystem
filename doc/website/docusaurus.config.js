// @ts-check
// Note: type annotations allow type checking and IDEs autocompletion

const lightCodeTheme = require('prism-react-renderer/themes/github');
const darkCodeTheme = require('prism-react-renderer/themes/dracula');

/** @type {import('@docusaurus/types').Config} */
const config = {
    title: 'RaySkillSystem',
    tagline: '灵活性极高的技能系统',
    favicon: 'img/Projector.ico',

    // Set the production url of your site here
    url: 'https://your-docusaurus-test-site.com',
    // Set the /<baseUrl>/ pathname under which your site is served
    // For GitHub pages deployment, it is often '/<projectName>/'
    baseUrl: '/RaySkillSystem/',

    // GitHub pages deployment config.
    // If you aren't using GitHub pages, you don't need these.
    organizationName: 'FxRayHughes', // Usually your GitHub org/user name.
    projectName: 'RaySkillSystem-Doc', // Usually your repo name.

    onBrokenLinks: 'throw',
    onBrokenMarkdownLinks: 'warn',

    // Even if you don't use internalization, you can use this field to set useful
    // metadata like html lang. For example, if your site is Chinese, you may want
    // to replace "en" with "zh-Hans".
    i18n: {
        defaultLocale: 'zh-Hans',
        locales: ['zh-Hans'],
    },

    presets: [
        [
            'classic',
            /** @type {import('@docusaurus/preset-classic').Options} */
            ({
                docs: {
                    sidebarPath: require.resolve('./sidebars.js'),
                    // Please change this to your repo.
                    // Remove this to remove the "edit this page" links.
                    editUrl:
                        'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
                },
                blog: {
                    showReadingTime: true,
                    // Please change this to your repo.
                    // Remove this to remove the "edit this page" links.
                    editUrl:
                        'https://github.com/facebook/docusaurus/tree/main/packages/create-docusaurus/templates/shared/',
                },
                theme: {
                    customCss: require.resolve('./src/css/custom.css'),
                },
            }),
        ],
    ],

    themeConfig: ({
        // Replace with your project's social card
        image: 'img/docusaurus-social-card.jpg',
        navbar: {
            title: 'RaySkillSystem',
            logo: {
                alt: 'Logo',
                src: 'img/Projector.svg',
            },
            items: [
                {
                    type: 'docSidebar',
                    sidebarId: 'tutorialSidebar',
                    position: 'left',
                    label: '文档',
                },
                {to: '/blog', label: '新闻', position: 'left'},
                {
                    href: 'https://github.com/RaySkillSystem/RaySkillSystem',
                    label: 'GitHub',
                    position: 'right',
                },
            ],
        },
        footer: {
            style: 'dark',
            links: [
                {
                    title: '文档列表',
                    items: [
                        {
                            label: '文档',
                            to: '/docs/intro',
                        },
                    ],
                },
                {
                    title: '社区',
                    items: [
                        {
                            label: 'GitHub',
                            href: 'https://github.com/RaySkillSystem/RaySkillSystem',
                        },
                        {
                            label: 'BiliBili',
                            href: 'https://space.bilibili.com/36207699/channel/seriesdetail?sid=3483555&ctype=0',
                        },
                        {
                            label: 'QQ群',
                            href: 'http://qm.qq.com/cgi-bin/qm/qr?_wv=1027&k=Pgkv5XmvBCr6Why-ZITp2CE8SBAFS02P&authKey=6aai2wXLovQvTo8RpP9hSLF1gjC5D41jqoB3EWxUyO%2BebXym4yUAAlrj6N2npyyW&noverify=0&group_code=830192024',
                        },
                    ],
                },
                {
                    title: '更多',
                    items: [
                        {
                            label: '新闻',
                            to: '/blog',
                        },
                    ],
                },
            ],
            copyright: `Copyright © ${new Date().getFullYear()} RaySkillSystem, Inc. Built with Docusaurus.`,
        },
        prism: {
            theme: lightCodeTheme,
            darkTheme: darkCodeTheme,
            additionalLanguages: ['kotlin','java'],
        },
    }),
    themes: [
        [
            require.resolve("@easyops-cn/docusaurus-search-local"),
            /** @type {import("@easyops-cn/docusaurus-search-local").PluginOptions} */
            ({
                hashed: true,
                language: ["en", "zh"],
            }),
        ],
    ],
};

module.exports = config;
