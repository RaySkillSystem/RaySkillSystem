package top.maplex.rayskillsystem.skill.tools.mechanism.effect.impl

import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import taboolib.common.util.Location
import taboolib.module.effect.shape.Arc
import taboolib.platform.util.toBukkitLocation
import java.util.UUID
import kotlin.math.cos
import kotlin.math.sin

fun Arc.backShow(action: Location.() -> Unit = {}, near: LivingEntity.() -> Unit = {}): Arc {
    var i = startAngle
    while (i < angle) {
        val radians = Math.toRadians(i)
        val x = radius * cos(radians)
        val z = radius * sin(radians)
        this.spawnParticle(origin.clone().add(x, 0.0, z))
        action.invoke(origin.clone().add(x, 0.0, z))
        val list = mutableListOf<UUID>()
        origin.toBukkitLocation().world?.getNearbyEntities(origin.toBukkitLocation(), 0.2, 0.2, 0.2) {
            it is Player
        }?.forEach {
            (it as? Player)?.let { player ->
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