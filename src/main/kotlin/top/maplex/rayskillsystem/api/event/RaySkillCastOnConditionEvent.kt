package top.maplex.rayskillsystem.api.event

import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.AbstractSkill

data class RaySkillCastOnConditionEvent(
    val sender: LivingEntity,
    val skill: AbstractSkill,
    val level: Int,
    var isCondition: Boolean
) : RaySkillEvent(
)
