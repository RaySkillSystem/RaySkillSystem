package top.maplex.rayskillsystem.skill.tools.mechanism.effect

import org.bukkit.Bukkit
import org.bukkit.Color
import org.bukkit.Particle
import org.bukkit.entity.Player
import taboolib.common.util.Location
import taboolib.platform.util.toBukkitLocation
import top.maplex.rayskillsystem.api.script.auto.InputEngine

@InputEngine("EffectSender")
object EffectTools {
    fun getLooker(location: Location): List<Player> {
        return Bukkit.getOnlinePlayers().filter { it.location.world?.name == (location.world ?: "") }
    }

    fun spawnColor(count: Int, loc: Location, R: Int, G: Int, B: Int, size: Float) {
        loc.toBukkitLocation().world?.spawnParticle(
            Particle.REDSTONE,
            loc.toBukkitLocation(),
            count,
            Particle.DustOptions(Color.fromRGB(R, G, B), size)
        )
    }

    fun spawnColor(count: Int, loc: Location, type: Particle) {
        loc.toBukkitLocation().world?.spawnParticle(
            type,
            loc.toBukkitLocation(),
            count,
        )
    }
}
