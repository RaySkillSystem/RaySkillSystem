package top.maplex.rayskillsystem.skill.tools.team

import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.utils.toConsole

interface AbstractTeam {

    val name: String

    fun register() {
        TeamManager.team = this
        toConsole("队伍系统由 $name 进行接管！", true)
    }

    /**
     * true = 敌人
     * false = 队友
     */
    fun canAttack(damager: LivingEntity, entity: LivingEntity): Boolean

}
