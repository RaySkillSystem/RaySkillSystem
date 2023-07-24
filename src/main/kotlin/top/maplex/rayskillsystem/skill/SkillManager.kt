package top.maplex.rayskillsystem.skill

import org.bukkit.entity.Player
import top.maplex.rayskillsystem.skill.tools.attribute.AttributeManager
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import top.maplex.rayskillsystem.utils.error
import top.maplex.rayskillsystem.utils.info
import java.util.function.Consumer

object SkillManager {

    val skills = HashMap<String, AbstractSkill>()

    fun eval(player: Player, name: String, level: Int, callBack: Consumer<AbstractSkill>): Boolean {
        return eval(player, name, level) {
            callBack.accept(this)
        }
    }

    fun eval(player: Player, name: String, level: Int, callBack: AbstractSkill.() -> Unit = {}): Boolean {
        val skill = getSkill(name) ?: return false
        if (skill.cooldown > 0) {
            if (!CooldownAPI.check(player, "Skill_${name}")) {
                val has = CooldownAPI.getTime(player, "Skill_${name}")
                player.error("技能&f $name &7尚在冷却 &c(${has})")
                return false
            }
        }
        if (!skill.onCondition(player, level)) {
            return false
        }
        if (!skill.onPreRun(player, level)) {
            return false
        }
        if (!skill.onRun(player, level)) {
            return false
        }
        player.getNearbyEntities(30.0, 30.0, 30.0).forEach {
            if (it is Player) {
                it.info("&f${player.name}&7 释放了技能 &f${skill.name}")
            }
        }
        player.info("&f${player.name}&7 释放了技能 &f${skill.name}")
        if (!skill.onOver(player, level)) {
            return false
        }
        if (skill.cooldown > 0) {
            val cooldown = AttributeManager.attributes.getCooldown(player)
            val value = if (cooldown * 0.01 <= 0.3) 0.3 else cooldown * 0.01
            val newCooldown = (skill.cooldown - (skill.cooldown * value)).toLong()
            CooldownAPI.set(player, "Skill_$name", newCooldown)
        }
        callBack.invoke(skill)
        return true
    }

    fun getSkill(name: String): AbstractSkill? {
        return skills[name]
    }

}
