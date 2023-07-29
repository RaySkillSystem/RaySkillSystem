package top.maplex.rayskillsystem.api.event

import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.AbstractSkill

class RaySkillCastPreRunEvent(
    val sender: LivingEntity,
    val skill: AbstractSkill,
    val level: Int,
    var canRun: Boolean
) : RaySkillEvent() {

}
