package top.maplex.rayskillsystem.skill.tools.summoned.impl

import ink.ptms.adyeshach.core.entity.EntityInstance
import ink.ptms.adyeshach.impl.entity.trait.impl.setTraitTitle
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import taboolib.common.platform.function.submit
import top.maplex.rayskillsystem.skill.tools.summoned.SummonedEntity
import top.maplex.rayskillsystem.skill.tools.summoned.SummonedManager
import top.maplex.rayskillsystem.skill.tools.summoned.expand.Follow
import top.maplex.rayskillsystem.skill.tools.summoned.expand.Limited
import java.util.*

open class SummonedAdyeshach(
    override val master: UUID,
    open val entity: EntityInstance,
    override var deviationX: Double,
    override var deviationZ: Double,
    override var deviationY: Double,
    override val overTime: Long,
    override var follow: Boolean = true,
    override var distance: Int = 10,
) : SummonedEntity("adyeshach", master), Follow, Limited {

    val player by lazy {
        Bukkit.getPlayer(master)
    }

    override fun attack(target: LivingEntity, value: Double): Boolean {
        move(target.location)
        target.damage(value, player)
        return super.attack(target, value)
    }

    override fun move(destination: Location): Boolean {
        if (entity.isRemoved) {
            return true
        }
        entity.controllerMoveTo(destination)
        return true
    }

    override fun delete() {
        entity.teleport(0.0, 0.0, 0.0)
        submit(delay = 2) {
            entity.remove()
            delete = true
        }
    }

    override fun getLocation(): Location {
        return entity.getLocation()
    }

    override fun teleport(destination: Location): Boolean {
        if (entity.isRemoved) {
            return true
        }
        entity.teleport(destination)
        return true
    }

    override fun onUpdate(): Boolean {
        checkTime(this)
        entity.isCollision = false
        player?.location?.let {
            followEval(this, it)
        } ?: delete()
        if (delete || entity.isRemoved) {
            SummonedManager.data.remove(this)
        }
        return super.onUpdate()
    }

}