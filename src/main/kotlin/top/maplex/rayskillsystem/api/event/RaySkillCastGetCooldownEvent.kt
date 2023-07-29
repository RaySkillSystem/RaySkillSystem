package top.maplex.rayskillsystem.api.event

import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.AbstractSkill

/**
 * 技能冷却事件
 * @param sender 施法者
 * @param skill 技能
 * @param cooldown 冷却时间
 */
class RaySkillCastGetCooldownEvent(
    val sender: LivingEntity,
    val skill: AbstractSkill,
    val level: Int,
    var cooldown: Long,
    var isCooldown: Boolean
) : RaySkillEvent()
