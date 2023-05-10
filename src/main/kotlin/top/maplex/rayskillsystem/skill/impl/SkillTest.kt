package top.maplex.rayskillsystem.skill.impl

import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.target.TargetRange

object SkillTest : AbstractSkill {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "测试"

    override val type: String = "无"

    override val cooldown: Long = 3 * 20


    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetRange.get(player, 10.0, false).filter {
            !Team.canAttack(player, it)
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