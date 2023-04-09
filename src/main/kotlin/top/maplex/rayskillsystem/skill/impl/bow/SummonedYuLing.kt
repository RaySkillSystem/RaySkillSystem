package top.maplex.rayskillsystem.skill.impl.bow

import ink.ptms.adyeshach.core.entity.EntityInstance
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import taboolib.common.platform.function.submit
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

    override fun attack(target: LivingEntity, value: Double): Boolean {
        follow = false
        move(getPos(target.location))
        player?.let { player ->
            if (!CooldownAPI.check(player, "SummonedYuLing_${entity.uniqueId}")) {
                return false
            }
            submit(period = 1) {
                if (getLocation().distance(target.location) < damageDistanc) {
                    target.damage(value, player)
                    follow = true
                    if (target.isDead){
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
        if (target != null) {
            if (target!!.isDead) {
                target = null
            } else {
                attack(target!!, 5.0)
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