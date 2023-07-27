import React from 'react';
import clsx from 'clsx';
import styles from './styles.module.css';

const FeatureList = [
    {
        title: '灵活',
        Svg: require('@site/static/img/程序.svg').default,
        description: (
            <>
                允许使用多种语言编写技能。
                支持用户自定义大多数模块，接入自己需要的属性，队伍，等服务
            </>
        ),
    },
    {
        title: '高效',
        Svg: require('@site/static/img/电脑优化.svg').default,
        description: (
            <>
                本项目基于强劲的<code>Taboolib</code>
                使用Kotlin与Java混合开发
                在JavaScript使用上采用全局预编译提高效率
            </>
        ),
    },
    {
        title: '开源',
        Svg: require('@site/static/img/代码.svg').default,
        description: (
            <>
                本项目基于EPL-2.0进行开源
                支持 非营利性开源项目 学习使用
                若您的项目为营利性项目 请购买授权
            </>
        ),
    },
];

function Feature({Svg, title, description}) {
    return (
        <div className={clsx('col col--4')}>
            <div className="text--center">
                <Svg className={styles.featureSvg} role="img"/>
            </div>
            <div className="text--center padding-horiz--md">
                <h3>{title}</h3>
                <p>{description}</p>
            </div>
        </div>
    );
}

export default function HomepageFeatures() {
    return (
        <section className={styles.features}>
            <div className="container">
                <div className="row">
                    {FeatureList.map((props, idx) => (
                        <Feature key={idx} {...props} />
                    ))}
                </div>
            </div>
        </section>
    );
}
