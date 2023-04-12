package top.maplex.rayskillsystem.skill.impl.erbai

import ink.ptms.adyeshach.core.entity.EntityInstance
import ink.ptms.adyeshach.impl.entity.trait.impl.setTraitTitle
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import taboolib.common.platform.function.submit
import taboolib.module.ai.navigationMove
import taboolib.platform.util.toBukkitLocation
import top.maplex.rayskillsystem.skill.tools.summoned.SummonedEntity
import top.maplex.rayskillsystem.skill.tools.summoned.expand.Follow
import top.maplex.rayskillsystem.skill.tools.summoned.expand.Limited
import java.util.*

open class SummonedErBai(
    override val master: UUID,
    open val entity: LivingEntity,
    override var deviationX: Double,
    override var deviationZ: Double,
    override var deviationY: Double,
    override var follow: Boolean = true,
    override var distance: Int = 10,
) : SummonedEntity("erbai", master), Follow{

    val player by lazy {
        Bukkit.getPlayer(master)
    }

    override fun move(destination: Location): Boolean {
        entity.navigationMove(destination, 1.5)
        return true
    }

    override fun delete() {
        entity.teleport(getLocation().world!!.spawnLocation.clone().apply {
            y = 0.0
        })
        submit(delay = 2) {
            entity.remove()
            delete = true
        }
    }

    override fun getLocation(): Location {
        return entity.location
    }

    override fun teleport(destination: Location): Boolean {
        entity.teleport(destination)
        return true
    }

    override fun onUpdate(): Boolean {
        player?.location?.let {
            followEval(this, it)
        } ?: delete()
        return super.onUpdate()
    }

}