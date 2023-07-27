package top.maplex.rayskillsystem.skill.impl

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.team.TeamManager
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import top.maplex.rayskillsystem.utils.auto.RaySkillSystem

@RaySkillSystem
object SkillTest : AbstractSkill {

    override val name: String = "测试"

    override val type: String = "无"

    override fun getCooldown(livingEntity: LivingEntity, level: Int): Long {
        return 3 * 20
    }

    override fun onCondition(livingEntity: LivingEntity, level: Int): Boolean {
        return true
    }

    override fun onRun(livingEntity: LivingEntity, level: Int): Boolean {
        TargetRange.get(livingEntity, 10.0, false).filter {
            !TeamManager.canAttack(livingEntity, it)
        }.let { list ->
            list.forEach {
                if (it is Player) {
                    it.sendMessage("§c你被${livingEntity.name}攻击了")
                }
                it.damage(10.0, livingEntity)
            }
        }
        return true
    }
}
