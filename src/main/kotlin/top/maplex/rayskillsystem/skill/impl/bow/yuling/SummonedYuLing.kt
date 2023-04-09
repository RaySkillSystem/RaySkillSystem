package top.maplex.rayskillsystem.skill.impl.bow.yuling

import ink.ptms.adyeshach.core.entity.EntityInstance
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import taboolib.common.platform.function.submit
import top.maplex.panlingitem.api.PanLingAPI
import top.maplex.panlingitem.api.PanLingStatic
import top.maplex.rayskillsystem.skill.tools.summoned.impl.SummonedAdyeshach
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import java.util.*

class SummonedYuLing(
    override val master: UUID,
    override val entity: EntityInstance,
    override var deviationX: Double,
    override var deviationZ: Double,
    override var deviationY: Double,
    override val overTime: Long,
    override var follow: Boolean = true,
    override var distance: Int = 10,
    var target: LivingEntity? = null,
    var damageDistanc: Double = 1.8,
) : SummonedAdyeshach(master, entity, deviationX, deviationZ, deviationY, overTime, follow, distance) {

    override fun move(destination: Location): Boolean {
        entity.moveSpeed = 0.5
        return super.move(destination)
    }

    override fun attack(target: LivingEntity, value: Double): Boolean {
        follow = false
        move(getPos(target.location))
        entity.controllerLookAt(target)
        player?.let { player ->
            if (!CooldownAPI.check(player, "SummonedYuLing_${entity.uniqueId}")) {
                return false
            }
            submit(period = 1) {
                if (getLocation().distance(target.location) < damageDistanc) {
                    target.damage(value, player)
                    follow = true
                    if (target.isDead) {
                        followEval(this@SummonedYuLing, player.location)
                    }
                    CooldownAPI.set(player, "SummonedYuLing_${entity.uniqueId}", 20)
                    cancel()
                }
            }
        }
        return true
    }

    override fun onUpdate(): Boolean {
        setTimeName()
        player?.let { player ->
            if (target != null) {
                if (target!!.isDead || getLocation().distance(target!!.location) >= 20) {
                    target = null
                } else {
                    val value = PanLingAPI.getPlayerData(player, PanLingStatic.STRENGTH_BOW).toInt() * 0.3
                    attack(target!!, value + 5)
                }
            }
        }
        return super.onUpdate()
    }

    fun setTimeName() {
        val time = (overTime - System.currentTimeMillis()) / 1000.0
        entity.setCustomName("${time.toInt()}s")
    }

    fun getPos(source: Location): Location {
        return source.clone()
            .add(source.clone().direction.normalize().setY(0).multiply((-1..1).random().toDouble()))
            .add(source.clone().apply { yaw += 90 }.direction.normalize().setY(0).multiply((-1..1).random().toDouble()))
    }

}