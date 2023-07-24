package top.maplex.rayskillsystem.skill.tools.mechanism.damage

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Creature
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.team.TeamManager
import top.maplex.rayskillsystem.utils.MythicMobsUtils

object Damage {

    fun taunt(damager: LivingEntity, target: List<LivingEntity>, value: Double) {
        target.forEach {
            if (MythicMobsUtils.isMythicMob(it)) {
                MythicMobsUtils.taunt(it, damager, value)
            } else {
                (it as Creature).target = damager
            }
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
                    MythicMobsUtils.taunt(mob, healer, value)
                }
            }
        }

    }

    fun damage(damager: LivingEntity, target: LivingEntity, value: Double) {
        if (TeamManager.canAttack(damager, target)) {
            target.damage(value, damager)
            MythicMobsUtils.taunt(target, damager, value)
        }
    }

    fun damage(damager: LivingEntity, target: List<LivingEntity>, value: Double) {
        target.forEach {
            if (TeamManager.canAttack(damager, it)) {
                it.damage(value, damager)
                MythicMobsUtils.taunt(it, damager, value)
            }
        }
    }

    fun AbstractSkill.heal(damager: LivingEntity, target: List<LivingEntity>, value: Double) {
        target.forEach {
            if (!TeamManager.canAttack(damager, it)) {
                val maxHeal = it.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
                if (it.health + value >= maxHeal) {
                    it.health = maxHeal
                } else {
                    it.health += value
                }
                damager.getNearbyEntities(5.0, 5.0, 5.0).forEach { mob ->
                    if (mob is LivingEntity) {
                        MythicMobsUtils.taunt(mob, damager, value)
                    }
                }
            }
        }
    }

}
