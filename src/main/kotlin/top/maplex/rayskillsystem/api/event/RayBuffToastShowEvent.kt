package top.maplex.rayskillsystem.api.event

import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff

class RayBuffToastShowEvent(
    val sender: LivingEntity,
    val buff: AbstractBuff,
    val time:Long,
    val level: Int,
    var show: Boolean
) : RaySkillEvent() {

}
