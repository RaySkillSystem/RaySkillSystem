package top.maplex.rayskillsystem.skill.tools.mechanism.damage

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Creature
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.utils.MythicMobsUtils

fun AbstractSkill.taunt(damager: LivingEntity, target: List<LivingEntity>, value: Double) {
    target.forEach {
        if (MythicMobsUtils.isMythicMob(it)) {
            MythicMobsUtils.taunt(it, damager, value)
        } else {
            (it as Creature).target = damager
        }
    }
}

fun AbstractSkill.damage(damager: LivingEntity, target: List<LivingEntity>, value: Double) {
    target.forEach {
        if (Team.canAttack(damager, it)) {
            it.damage(value, damager)
            MythicMobsUtils.taunt(it, damager, value)
        }
    }
}

fun AbstractSkill.damage(damager: LivingEntity, target: LivingEntity, value: Double) {
    if (Team.canAttack(damager, target)) {
        target.damage(value, damager)
        MythicMobsUtils.taunt(target, damager, value)
    }
}
fun damage(damager: LivingEntity, target: LivingEntity, value: Double) {
    if (Team.canAttack(damager, target)) {
        target.damage(value, damager)
        MythicMobsUtils.taunt(target, damager, value)
    }
}
fun damage(damager: LivingEntity, target: List<LivingEntity>, value: Double) {
    target.forEach {
        if (Team.canAttack(damager, it)) {
            it.damage(value, damager)
            MythicMobsUtils.taunt(it, damager, value)
        }
    }
}

fun AbstractSkill.heal(damager: LivingEntity, target: List<LivingEntity>, value: Double) {
    target.forEach {
        if (!Team.canAttack(damager, it)) {
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
