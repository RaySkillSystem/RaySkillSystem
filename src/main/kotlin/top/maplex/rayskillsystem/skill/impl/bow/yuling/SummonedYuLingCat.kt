package top.maplex.rayskillsystem.skill.impl.bow.yuling

import ink.ptms.adyeshach.core.bukkit.BukkitPose
import ink.ptms.adyeshach.core.entity.EntityInstance
import ink.ptms.adyeshach.core.entity.type.AdyMob
import org.bukkit.Location
import org.bukkit.entity.LivingEntity
import taboolib.module.chat.colored
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.panlingitem.api.PanLingAPI
import top.maplex.panlingitem.api.PanLingStatic
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffLiZheng
import top.maplex.rayskillsystem.skill.tools.summoned.impl.SummonedAdyeshach
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import java.util.*

open class SummonedYuLingCat(
    override val master: UUID,
    override val entity: EntityInstance,
    override var deviationX: Double,
    override var deviationZ: Double,
    override var deviationY: Double,
    override val overTime: Long,
    override var follow: Boolean = true,
    override var distance: Int = 10,
    open var target: LivingEntity? = null,
    open var damageDistanc: Double = 2.0,
    open var zhengDu: Boolean = false,
) : SummonedAdyeshach(master, entity, deviationX, deviationZ, deviationY, overTime, follow, distance) {

    override fun move(destination: Location): Boolean {
        return super.move(destination)
    }

    override fun attack(target: LivingEntity, value: Double): Boolean {
        move(getPos(target.location))
        entity.controllerLookAt(target)
        (entity as? AdyMob)?.setAgressive(true)
        player?.let { player ->
            if (!CooldownAPI.check(player, "SummonedYuLing_${entity.uniqueId}")) {
                return false
            }
            follow = false
            if (getLocation().distance(target.location) < damageDistanc) {
                if (zhengDu) {
                    BuffManager.plus(target, BuffLiZheng.id, 5 * 20, 5, master)
                }
                target.damage(value, player)
                follow = true
                if (target.isDead) {
                    followEval(this@SummonedYuLingCat, player.location)
                }
                (entity as? AdyMob)?.setAgressive(false)
                entity.setPose(BukkitPose.SPIN_ATTACK)
                CooldownAPI.set(player, "SummonedYuLing_${entity.uniqueId}", 20)
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
                    val attribute = PlayerManager.getPlayerData(player).attribute
                    val value = (attribute.getAttribute(AttributeEnum.BOW_STRENGTH) + 1) * 0.25
                    //val value = PanLingAPI.getPlayerData(player, PanLingStatic.STRENGTH_BOW).toInt() * 0.2
                    attack(target!!, value + 1)
                }
            }
        }
        return super.onUpdate()
    }

    open fun setTimeName() {
        val time = (overTime - System.currentTimeMillis()) / 1000.0
        if (zhengDu) {
            entity.setCustomName("${time.toInt()}s &e[争]".colored())
        } else {
            entity.setCustomName("${time.toInt()}s")
        }
    }

    fun getPos(source: Location): Location {
        return source.clone()
            .add(source.clone().direction.normalize().setY(0).multiply((-1..1).random().toDouble()))
            .add(source.clone().apply { yaw += 90 }.direction.normalize().setY(0).multiply((-1..1).random().toDouble()))
    }

}