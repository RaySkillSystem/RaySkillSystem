---
sidebar_position: 2
---

# 用代码创建一个技能

注：本章将会大量引用源码 点击右侧目录可以快速定位

## 接口讲解

```kotlin
package top.maplex.rayskillsystem.skill

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack
import taboolib.platform.util.buildItem
import top.maplex.rayskillsystem.utils.set
import top.maplex.rayskillsystem.utils.toConsole

interface AbstractSkill {

    // 技能名称
    val name: String

    // 技能类型 并无作用 留给拓展进行判断
    val type: String

    // 获取技能的冷却
    fun getCooldown(livingEntity: LivingEntity, level: Int): Long {
        return 0
    }

    // 显示物品
    fun showItem(livingEntity: LivingEntity, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${this@AbstractSkill.name}"
            colored()
        }.apply {
            set("RaySkill.type", this@AbstractSkill.name)
        }
    }

    // 施法条件 最先判断 通常写施法条件
    fun onCondition(livingEntity: LivingEntity, level: Int): Boolean = true

    // 运行前执行 可以用来判断是否可以运行
    fun onPreRun(livingEntity: LivingEntity, level: Int): Boolean = true

    // 运行代码 可以用来写技能的主要逻辑
    fun onRun(livingEntity: LivingEntity, level: Int): Boolean = true

    // 运行后执行 可以用来写技能的后续逻辑 收尾操作
    fun onOver(livingEntity: LivingEntity, level: Int): Boolean = true

    // 注册
    // 注意 注册技能采用的是覆盖的形式
    // 如非设计 请不要将技能名设置重复
    fun register() {
        SkillManager.skills[name] = this
        toConsole("注册技能:&f $name")
    }

}
```

## Kotlin创建

由于这部分是由Kotlin进行开发 基于Kotlin得天独厚的兼容性

你并不需要完全实现接口里的方法

只需要选择你要的内容 进行实现即可

```kotlin
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.team.TeamManager
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import top.maplex.rayskillsystem.utils.auto.RaySkillSystem
// highlight-next-line
@RaySkillSystem // 使用注解 自动注入
object SkillTest : AbstractSkill {

    override val name: String = "测试"

    override val type: String = "无"

    override fun getCooldown(livingEntity: LivingEntity, level: Int): Long {
        return 3 * 20
    }
    
    override fun onCondition(livingEntity: LivingEntity, level: Int): Boolean {
        return true
    }

    override fun onRun(livingEntity: LivingEntity, level: Int): Boolean {
        TargetRange.get(livingEntity, 10.0, false).filter {
            !TeamManager.canAttack(livingEntity, it)
        }.let { list ->
            list.forEach {
                if (it is Player) {
                    it.sendMessage("§c你被${livingEntity.name}攻击了")
                }
                it.damage(10.0, livingEntity)
            }
        }
        return true
    }
}

```

## Java代码实现

由于Java设计问题 并不可以省略接口里面的方法

所以需要每个方法都写出来

但是你仍然可以执行 接口中的方法 进行快速实现

`AbstractSkill.DefaultImpls.xxx` 相当于 `this.super.xxx`

```java
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.maplex.rayskillsystem.skill.AbstractSkill;
import top.maplex.rayskillsystem.utils.auto.RaySkillSystem;

@RaySkillSystem
public class TestJavaSkill implements AbstractSkill {
    @NotNull
    @Override
    public String getName() {
        return "测试Java技能";
    }

    @NotNull
    @Override
    public String getType() {
        return "无";
    }

    @Override
    public long getCooldown(@NotNull LivingEntity livingEntity, int level) {
        return 100;
    }

    @Override
    public boolean onCondition(@NotNull LivingEntity livingEntity, int level) {
        if (livingEntity instanceof Player && ((Player) livingEntity).getLevel() >= 20) {
            return true;
        } else {
            livingEntity.sendMessage("你的等级无法施展如此高深的技能");
            return false;
        }
    }

    @Override
    public boolean onRun(@NotNull LivingEntity livingEntity, int level) {
        System.out.println("技能运行了！");
        return true;
    }

    @NotNull
    @Override
    public ItemStack showItem(@NotNull LivingEntity livingEntity, int level) {
        return AbstractSkill.DefaultImpls.showItem(this, livingEntity, level);
    }

    @Override
    public boolean onPreRun(@NotNull LivingEntity livingEntity, int level) {
        return AbstractSkill.DefaultImpls.onPreRun(this, livingEntity, level);
    }

    @Override
    public boolean onOver(@NotNull LivingEntity livingEntity, int level) {
        return AbstractSkill.DefaultImpls.onOver(this, livingEntity, level);
    }

    @Override
    public void register() {
        AbstractSkill.DefaultImpls.register(this);
    }
}

```
