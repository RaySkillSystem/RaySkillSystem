package top.maplex.rayskillsystem.utils

import ink.ptms.adyeshach.api.AdyeshachAPI
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored

fun buildHologramNear(sender: Entity, info: List<String>, stay: Long = 15L) {
    sender.getNearbyEntities(30.0, 30.0, 30.0).mapNotNull { it as? Player }.forEach { player ->
        val data = AdyeshachAPI.createHologram(player, getRandom(sender.location), info.colored())
        submit(delay = stay) {
            data.delete()
        }
    }
}

private fun getRandom(location: Location): Location {
    var locations = getRandomLocation(location)
    while (locations.block.type != Material.AIR) {
        locations = getRandomLocation(location)
    }
    return locations
}

private fun getRandomLocation(location: Location): Location {
    val radius = 1.0
    val radians = Math.toRadians((0..360).random().toDouble())
    val x = kotlin.math.cos(radians) * radius
    val z = kotlin.math.sin(radians) * radius
    return location.add(x, 1.0, z)
}