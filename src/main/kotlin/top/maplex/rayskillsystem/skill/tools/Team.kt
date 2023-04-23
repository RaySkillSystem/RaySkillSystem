package top.maplex.rayskillsystem.skill.tools

import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity

object Team {

    val api by lazy {
        Bukkit.getScoreboardManager()?.mainScoreboard
    }

    //true是可以攻击
    //false是不可以
    fun canAttack(damager: LivingEntity, it: LivingEntity): Boolean {
        if (damager == it) {
            return false
        }
        val from = api?.getEntryTeam(damager.name) ?: return true
        val to = api?.getEntryTeam(it.name) ?: return true
        return from != to
    }
}