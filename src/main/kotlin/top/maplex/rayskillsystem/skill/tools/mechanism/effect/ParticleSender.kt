package top.maplex.rayskillsystem.skill.tools.mechanism.effect

import org.bukkit.Bukkit
import org.bukkit.Particle
import org.bukkit.entity.Player
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.platform.util.toBukkitLocation

class ParticleSender : ParticleSpawner {

    var particle = Particle.VILLAGER_HAPPY
    var posX = 0.0
    var posY = 0.0
    var posZ = 0.0

    override fun spawn(location: Location) {
        getLooker(location).forEach {
            it.spawnParticle(
                particle,
                location.toBukkitLocation().clone().add(posX, posY, posZ),
                1
            )
        }
    }

    fun getLooker(location: Location): List<Player> {
        return Bukkit.getOnlinePlayers().filter { it.location.world?.name == (location.world ?: "") }
    }
}