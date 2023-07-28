package top.maplex.rayskillsystem.skill

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import top.maplex.rayskillsystem.skill.tools.attribute.AttributeManager
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import top.maplex.rayskillsystem.utils.error
import top.maplex.rayskillsystem.utils.info
import java.util.function.Consumer

object SkillManager {

    val skills = HashMap<String, AbstractSkill>()

    fun eval(livingEntity: LivingEntity, name: String, level: Int, callBack: Consumer<AbstractSkill>): Boolean {
        return eval(livingEntity, name, level) {
            callBack.accept(this)
        }
    }

    fun eval(livingEntity: LivingEntity, name: String, level: Int, callBack: AbstractSkill.() -> Unit = {}): Boolean {
        val skill = getSkill(name) ?: return false
        if (skill.getCooldown(livingEntity, level) > 0) {
            if (!CooldownAPI.check(livingEntity, "Skill_${name}")) {
                val has = CooldownAPI.getTime(livingEntity, "Skill_${name}")
                livingEntity.error("技能&f $name &7尚在冷却 &c(${has})")
                return false
            }
        }
        if (!skill.onCondition(livingEntity, level)) {
            return false
        }
        if (!skill.onPreRun(livingEntity, level)) {
            return false
        }
        if (!skill.onRun(livingEntity, level)) {
            return false
        }
        livingEntity.getNearbyEntities(30.0, 30.0, 30.0).forEach {
            if (it is Player) {
                it.info("&f${livingEntity.name}&7 释放了技能 &f${skill.name}")
            }
        }
        livingEntity.info("&f${livingEntity.name}&7 释放了技能 &f${skill.name}")
        if (!skill.onOver(livingEntity, level)) {
            return false
        }
        if (skill.getCooldown(livingEntity, level) > 0) {
            val cooldown = AttributeManager.attributes.getCooldown(livingEntity)
            val value = if (cooldown * 0.01 <= 0.3) 0.3 else cooldown * 0.01
            val newCooldown = (skill.getCooldown(livingEntity, level) - (skill.getCooldown(livingEntity, level) * value)).toLong()
            CooldownAPI.set(livingEntity, "Skill_$name", newCooldown)
        }
        callBack.invoke(skill)
        return true
    }

    fun getSkill(name: String): AbstractSkill? {
        return skills[name]
    }

}
