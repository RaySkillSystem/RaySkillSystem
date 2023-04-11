package top.maplex.rayskillsystem.utils

import eu.decentsoftware.holograms.api.DHAPI
import ink.ptms.adyeshach.api.AdyeshachAPI
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored
import java.util.UUID

fun buildHologramNear(sender: Entity, info: List<String>, stay: Long = 15L) {
    val uuid = UUID.randomUUID().toString()
    DHAPI.createHologram(uuid, getRandom(sender.location), false, info.colored())
    submit(delay = stay) {
        DHAPI.removeHologram(uuid)
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