package top.maplex.rayskillsystem.skill.impl.dan.def.huo

import ink.ptms.adyeshach.core.bukkit.BukkitPose
import ink.ptms.adyeshach.core.entity.EntityInstance
import ink.ptms.adyeshach.core.entity.type.AdyMob
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.module.effect.shape.Line
import taboolib.module.effect.shape.Ray
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.summoned.impl.SummonedAdyeshach
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import java.util.*

open class SummonedHuoLing(
    override val master: UUID,
    override val entity: EntityInstance,
    override var deviationX: Double,
    override var deviationZ: Double,
    override var deviationY: Double,
    override val overTime: Long,
    override var follow: Boolean = false,
    override var distance: Int = 10,
    open var target: LivingEntity? = null,
    open var damageDistanc: Double = 2.0,
) : SummonedAdyeshach(master, entity, deviationX, deviationZ, deviationY, overTime, follow, distance) {

    override fun attack(target: LivingEntity, value: Double): Boolean {
        player?.let { player ->
            if (!CooldownAPI.check(player, "SummonedHuoLing_${entity.uniqueId}")) {
                return false
            }
            if (getLocation().distance(target.location) < damageDistanc) {
                Line(getLocation().toProxyLocation(), target.location.toProxyLocation(), 1.0, 1,
                    object : ParticleSpawner {
                        override fun spawn(location: taboolib.common.util.Location) {
                            spawnColor(1, location, 124, 25, 30, 2F)
                        }
                    }).show()
                target.damage(value, player)
                entity.setPose(BukkitPose.SPIN_ATTACK)
                CooldownAPI.set(player, "SummonedHuoLing_${entity.uniqueId}", 15)
            }
        }
        return true
    }

    override fun onUpdate(): Boolean {
        setTimeName()
        player?.let {
            val attribute = PlayerManager.getPlayerData(it).attribute
            val value = (attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1) * 0.5
            TargetRange.get(getLocation(), 8.0).forEach { z ->
                Team.canAttack(it, z).let { target ->
                    if (target){
                        this.target = z
                        attack(z, value + 1)
                        return@forEach
                    }
                }
            }
        }
        return super.onUpdate()
    }

    open fun setTimeName() {
        val time = (overTime - System.currentTimeMillis()) / 1000.0
        entity.setCustomName("${time.toInt()}s")
    }

}