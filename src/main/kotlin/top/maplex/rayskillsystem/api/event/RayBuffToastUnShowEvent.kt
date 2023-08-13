package top.maplex.rayskillsystem.api.event

import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff

class RayBuffToastUnShowEvent(
    val sender: LivingEntity,
    val buff: AbstractBuff,
    val level: Int,
    var show: Boolean
) : RaySkillEvent() {

}
