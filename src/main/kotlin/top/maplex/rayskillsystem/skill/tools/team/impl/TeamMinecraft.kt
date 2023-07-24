package top.maplex.rayskillsystem.skill.tools.team.impl

import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.tools.team.AbstractTeam

object TeamMinecraft : AbstractTeam {

    override val name: String = "默认队伍系统"

    private val api by lazy {
        Bukkit.getScoreboardManager()?.mainScoreboard
    }

    override fun canAttack(damager: LivingEntity, entity: LivingEntity): Boolean {
        if (damager == entity) {
            return false
        }
        val from = api?.getEntryTeam(damager.name) ?: return true
        val to = api?.getEntryTeam(entity.name) ?: return true
        return from != to
    }

}
