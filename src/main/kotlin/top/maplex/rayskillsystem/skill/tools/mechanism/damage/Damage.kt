package top.maplex.rayskillsystem.skill.tools.mechanism.damage

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Creature
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.tools.team.TeamManager
import top.maplex.rayskillsystem.utils.MythicMobsUtils

object Damage {

    fun taunt(damager: LivingEntity, target: List<LivingEntity>, value: Double) {
        target.forEach {
            taunt(damager, it, value)
        }
    }

    fun taunt(damager: LivingEntity, target: LivingEntity, value: Double) {
        if (MythicMobsUtils.isMythicMob(target)) {
            MythicMobsUtils.taunt(target, damager, value)
        } else {
            (target as? Creature)?.target = damager
        }
    }

    fun heal(healer: LivingEntity, target: List<LivingEntity>, value: Double) {
        target.forEach {
            heal(healer, it, value)
        }
    }

    fun heal(healer: LivingEntity, target: LivingEntity, value: Double) {
        if (!TeamManager.canAttack(healer, target)) {
            val maxHeal = target.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
            if (target.health + value >= maxHeal) {
                target.health = maxHeal
            } else {
                target.health += value
            }
            healer.getNearbyEntities(5.0, 5.0, 5.0).forEach { mob ->
                if (mob is LivingEntity) {
                    taunt(mob, healer, value)
                }
            }
        }

    }

    fun damage(damager: LivingEntity, target: LivingEntity, value: Double) {
        if (TeamManager.canAttack(damager, target)) {
            target.damage(value, damager)
            taunt(target, damager, value)
        }
    }

    fun damage(damager: LivingEntity, target: List<LivingEntity>, value: Double) {
        target.forEach {
            if (TeamManager.canAttack(damager, it)) {
                it.damage(value, damager)
                taunt(it, damager, value)
            }
        }
    }

}
