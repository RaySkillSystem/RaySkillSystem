package top.maplex.rayskillsystem.utils

import io.lumine.mythic.api.MythicProvider
import io.lumine.mythic.bukkit.MythicBukkit
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player

object MythicMobsUtils {

    val api by lazy {
        MythicProvider.get() as MythicBukkit
    }

    fun isMythicMob(entity: Entity): Boolean {
        return api.apiHelper.isMythicMob(entity)
    }

    fun castSkill(caster: Player, skill: String) {
        api.apiHelper.castSkill(caster, skill, caster.location, 1F)
    }

    fun taunt(target: LivingEntity, source: LivingEntity, amount: Double) {
        if (target == source || !isMythicMob(target)) {
            return
        }
        if (amount > 0) {
            api.apiHelper.addThreat(target, source, amount)
        } else if (amount < 0) {
            api.apiHelper.reduceThreat(target, source, -amount)
        }
    }
}