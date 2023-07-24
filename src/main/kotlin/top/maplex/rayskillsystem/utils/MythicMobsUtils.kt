package top.maplex.rayskillsystem.utils

import ink.ptms.um.Mythic
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity

object MythicMobsUtils {

    fun isMythicMob(entity: Entity): Boolean {
        return Mythic.API.getMob(entity) != null
    }


    fun taunt(target: LivingEntity, source: LivingEntity, amount: Double) {
        if (target == source) {
            return
        }
        val mob = Mythic.API.getMob(target) ?: return
        if (amount > 0) {
            mob.addThreat(target, source, amount)
        } else if (amount < 0) {
            mob.reduceThreat(target, source, -amount)
        }
    }
}
