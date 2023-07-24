package top.maplex.rayskillsystem.skill.impl

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.team.TeamManager
import top.maplex.rayskillsystem.skill.tools.target.TargetRange

object SkillTest : AbstractSkill {

    override val name: String = "测试"

    override val type: String = "无"

    override val cooldown: Long = 3 * 20


    override fun onCondition(livingEntity: LivingEntity, level: Int): Boolean {
        return true
    }

    override fun onRun(livingEntity: LivingEntity, level: Int): Boolean {
        TargetRange.get(livingEntity, 10.0, false).filter {
            !TeamManager.canAttack(livingEntity, it)
        }.let {
            if (it.size >= 2) {
                it.subList(0, 1)
            } else {
                it
            }
        }
        return true
    }


}
