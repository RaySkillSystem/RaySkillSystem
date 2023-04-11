package top.maplex.rayskillsystem.skill.impl.bow.yuling

import ink.ptms.adyeshach.core.entity.EntityInstance
import ink.ptms.adyeshach.core.entity.type.AdyMob
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import taboolib.module.chat.colored
import taboolib.module.effect.createLine
import taboolib.module.effect.createRay
import taboolib.module.effect.shape.Ray
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingitem.api.PanLingAPI
import top.maplex.panlingitem.api.PanLingStatic
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffLiZheng
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.summoned.impl.SummonedAdyeshach
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import java.util.*

open class SummonedYuLingXuanFeng(
    override val master: UUID,
    override val entity: EntityInstance,
    override var deviationX: Double,
    override var deviationZ: Double,
    override var deviationY: Double,
    override val overTime: Long,
    override var follow: Boolean = true,
    override var distance: Int = 10,
    var heal: Double = 200.0,
) : SummonedYuLingCat(master, entity, deviationX, deviationZ, deviationY, overTime, follow, distance) {

    override fun move(destination: Location): Boolean {
        entity.moveSpeed = 0.5
        return super.move(destination)
    }

    override fun attack(target: LivingEntity, value: Double): Boolean {
        entity.controllerLookAt(target)
        player?.let { player ->
            if (!CooldownAPI.check(player, "SummonedYuLing_${entity.uniqueId}")) {
                return false
            }
            createLine(
                getLocation().clone().add(0.0, 1.5, 0.0).toProxyLocation(),
                target.location.clone().add(0.0, 1.5, 0.0).toProxyLocation(),
                0.5,
                1L,
            ) {
                spawnColor(5, it, 110, 155, 197, 2F)
            }.show()
            if (getLocation().distance(target.location) < damageDistanc) {
                if (zhengDu) {
                    BuffManager.plus(target, BuffLiZheng.id, 5 * 20, 5, master)
                }
                target.damage(value, player)
                CooldownAPI.set(player, "SummonedYuLing_${entity.uniqueId}", 15)
            }
        }
        return true
    }

    override fun injury(source: LivingEntity, value: Double): Boolean {
        if (value <= heal) {
            heal -= value
        } else {
            delete()
        }
        return true
    }

    override fun setTimeName() {
        val time = (overTime - System.currentTimeMillis()) / 1000.0
        if (zhengDu) {
            entity.setCustomName("${time.toInt()}s &c${heal} &e[争]".colored())
        } else {
            entity.setCustomName("${time.toInt()}s &c${heal} ".colored())
        }
    }

    override fun onUpdate(): Boolean {
        setTimeName()
        if (heal <= 0.0) {
            delete()
            return false
        }
        player?.let { player ->
            if (target != null) {
                if (target!!.isDead || getLocation().distance(target!!.location) >= 20) {
                    target = null
                } else {
                    val value = PanLingAPI.getPlayerData(player, PanLingStatic.STRENGTH_BOW).toInt() * 0.22
                    attack(target!!, value + 1)
                }
            }
        }
        return super.onUpdate()
    }

}