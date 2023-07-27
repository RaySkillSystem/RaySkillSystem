---
sidebar_position: 1
---

# 技能

## 前言

首先 要了解两个概念 触发器 与 执行器

触发器 原生分为 **指令触发** 与 **快捷施法触发**

两个触发器触发的技能并无差异

## 生命周期

了解了基础概念后 我们就要去写执行器 也就是技能本身

在写技能之前呢 要了解技能的生命周期

1. **getCooldown** 判断冷却
2. **onCondition** 判断消耗
3. **onPreRun** 技能准备
4. **onRun** 技能执行
5. 对周围发送消息
6. **onOver** 技能结束
7. 设置冷却
8. 执行callBack函数

## 讲解

```kotlin
interface AbstractSkill {

    val name: String

    val type: String
    
    // 这个函数是用来获取冷却时间的
    // 但是采用了函数的形式 这是为了方便制作 多段技能 与特殊操作 譬如冷却缩减等操作的
    fun getCooldown(livingEntity: LivingEntity, level: Int): Long {
        return 0
    }

    // 获取快捷施法的物品
    // 只要包含了RaySkill.type 其实 就会被认为是一个
    // 快捷施法的按钮
    fun showItem(livingEntity: LivingEntity, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${this@AbstractSkill.name}"
            colored()
        }.apply {
            set("RaySkill.type", this@AbstractSkill.name)
        }
    }

    //施法条件
    fun onCondition(livingEntity: LivingEntity, level: Int): Boolean = true

    //运行前执行
    fun onPreRun(livingEntity: LivingEntity, level: Int): Boolean = true

    //运行代码
    fun onRun(livingEntity: LivingEntity, level: Int): Boolean = true

    //运行后执行
    fun onOver(livingEntity: LivingEntity, level: Int): Boolean = true

    //注册
    fun register() {
        SkillManager.skills[name] = this
        toConsole("注册技能:&f $name", true)
    }

}

```


## JS

在Js中 函数与Kotlin中的名称是一样的 你只需要稍微换一下写法

```javascript
function getCooldown(livingEntity, level) {
  return 20;
}
```

更多示例查看示例模块
