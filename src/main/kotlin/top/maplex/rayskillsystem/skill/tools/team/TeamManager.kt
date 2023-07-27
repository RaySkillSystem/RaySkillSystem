package top.maplex.rayskillsystem.skill.tools.team

import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.api.script.auto.InputEngine
import top.maplex.rayskillsystem.skill.tools.team.impl.TeamMinecraft

@InputEngine("Team")
object TeamManager {

    var team: AbstractTeam = TeamMinecraft

    //true是可以攻击
    //false是不可以
    fun canAttack(damager: LivingEntity, entity: LivingEntity): Boolean {
        return team.canAttack(damager, entity)
    }
}
