package top.maplex.rayskillsystem.skill.tools.mechanism.effect.impl

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import taboolib.common.util.Location
import taboolib.module.effect.shape.Ray
import taboolib.platform.util.toBukkitLocation
import java.util.*

fun Ray.backShow(action: Location.() -> Unit = {}, near: LivingEntity.() -> Unit = {}): Ray {
    var i = 0.0
    while (i < maxLength) {
        val vectorTemp = direction.clone().multiply(i)
        val spawnLocation = origin.clone().add(vectorTemp)
        spawnParticle(spawnLocation)
        action.invoke(spawnLocation)
        val list = mutableListOf<UUID>()
        spawnLocation.toBukkitLocation().world?.getNearbyEntities(spawnLocation.toBukkitLocation(), 0.2, 0.2, 0.2) {
            it is LivingEntity
        }?.forEach {
            (it as? LivingEntity)?.let { player ->
                if (!list.contains(player.uniqueId)) {
                    near.invoke(player)
                    list.add(player.uniqueId)
                }
            }
        }
        i += step
    }
    return this
}

fun Ray.getEntity(tolerance: Double): MutableList<LivingEntity> {
    var i = 0.0
    val list = mutableListOf<LivingEntity>()
    while (i < maxLength) {
        val vectorTemp = direction.clone().multiply(i)
        val spawnLocation = origin.clone().add(vectorTemp)
        spawnParticle(spawnLocation)
        spawnLocation.toBukkitLocation().world
            ?.getNearbyEntities(spawnLocation.toBukkitLocation(), tolerance, 3.0, tolerance)?.forEach {
                (it as? LivingEntity)?.let { player ->
                    if (!list.contains(player)) {
                        list.add(player)
                    }
                }
            }
        i += step
    }
    return list
}