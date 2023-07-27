---
sidebar_position: 3
---

# 队友判断

## 介绍

队友判断是一种特殊的判断，用于判断目标是否为队友。

队友也不是 一成不变的 只要最后返回的值是 true 那么都认为是敌人

## Js实现

这是一个简单的例子 每个类型的生物是一组

玩家和玩家是一组 怪物和怪物是一组
```javascript
var script_type = "Team";

function canAttack(livingEntity, target) {
    return livingEntity.getType() != target.getType();
}
```

准确来说 并没组这个概念 只不过是一个判断函数
