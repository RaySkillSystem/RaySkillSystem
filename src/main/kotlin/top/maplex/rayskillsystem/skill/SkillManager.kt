package top.maplex.rayskillsystem.skill

import org.bukkit.block.Block
import org.bukkit.entity.Player
import top.maplex.panlingcore.PanLingCore
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffLuanSaQingHe
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import top.maplex.rayskillsystem.utils.error
import top.maplex.rayskillsystem.utils.info
import java.util.concurrent.ConcurrentHashMap

object SkillManager {

    val skills = HashMap<String, AbstractSkill>()

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
            val attribute = PlayerManager.getPlayerData(player).attribute
            val value = attribute.getAttribute(AttributeEnum.COOLDOWN_SPEED)
            if (skill is AbstractDanCast) {
                if (BuffManager.getBuff(player, BuffLuanSaQingHe.id) <= 0) {
                    CooldownAPI.set(player, "Skill_${name}", (skill.cooldown - (skill.cooldown * value)).toLong())
                }else{
                    player.info("乱撒状态 无视冷却!")
                }
            } else {
                CooldownAPI.set(player, "Skill_${name}", (skill.cooldown - (skill.cooldown * value)).toLong())
            }
        }
        callBack.invoke(skill)
        return true
    }

    fun getSkill(name: String): AbstractSkill? {
        return skills[name]
    }

}