package top.maplex.rayskillsystem.skill

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import taboolib.common.platform.event.SubscribeEvent
import top.maplex.rayskillsystem.api.event.*
import top.maplex.rayskillsystem.api.script.auto.InputEngine
import top.maplex.rayskillsystem.skill.tools.attribute.AttributeManager
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import top.maplex.rayskillsystem.utils.error
import top.maplex.rayskillsystem.utils.info
import java.util.function.Consumer

@InputEngine("SkillManager")
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
            val temp = CooldownAPI.check(livingEntity, "Skill_${name}")
            val get = skill.getCooldown(livingEntity, level)
            RaySkillCastGetCooldownEvent(livingEntity, skill, level, get, temp)
                .callEvent<RaySkillCastGetCooldownEvent>()
                .let { event ->
                    if (event.isCooldown) {
                        livingEntity.error("技能 &f${skill.name}&7 冷却中,剩余 &f${event.cooldown / 1000}&7 秒")
                        return false
                    }
                }
        }
        if (!skill.onCondition(livingEntity, level).run original@{
                RaySkillCastOnConditionEvent(livingEntity, skill, level, this)
                    .callEvent<RaySkillCastOnConditionEvent>()
                    .isCondition
            }) {
            return false
        }

        if (!skill.onPreRun(livingEntity, level).run {
                RaySkillCastPreRunEvent(livingEntity, skill, level, this)
                    .callEvent<RaySkillCastPreRunEvent>()
                    .canRun
            }) {
            return false
        }
        if (!skill.onRun(livingEntity, level).run {
                RaySkillCastRunEvent(livingEntity, skill, level, this)
                    .callEvent<RaySkillCastRunEvent>()
                    .canRun
            }) {
            return false
        }
        livingEntity.getNearbyEntities(30.0, 30.0, 30.0).forEach {
            if (it is Player) {
                it.info("&f${livingEntity.name}&7 释放了技能 &f${skill.name}")
            }
        }
        livingEntity.info("&f${livingEntity.name}&7 释放了技能 &f${skill.name}")
        if (!skill.onOver(livingEntity, level).run {
                RaySkillCastOverEvent(livingEntity, skill, level, this)
                    .callEvent<RaySkillCastOverEvent>()
                    .canRun
            }) {
            return false
        }

        if (skill.getCooldown(livingEntity, level) > 0) {
            val cooldown = AttributeManager.attributes.getCooldown(livingEntity)
            val value = if (cooldown * 0.01 <= 0.3) 0.3 else cooldown * 0.01
            val newCooldown =
                (skill.getCooldown(livingEntity, level) - (skill.getCooldown(livingEntity, level) * value)).toLong()
            CooldownAPI.set(livingEntity, "Skill_$name", newCooldown)
        }
        callBack.invoke(skill)
        return true
    }

    fun getSkill(name: String): AbstractSkill? {
        return skills[name]
    }

}
