package top.maplex.rayskillsystem.skill.impl.bow.yuling

import ink.ptms.adyeshach.core.entity.EntityInstance
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Mob
import org.bukkit.entity.Villager
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.common.platform.function.submit
import taboolib.module.chat.colored
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingitem.api.PanLingAPI
import top.maplex.panlingitem.api.PanLingStatic
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import java.util.*


class SummonedYuLingBear(
    override val master: UUID,
    override val entity: EntityInstance,
    override var deviationX: Double,
    override var deviationZ: Double,
    override var deviationY: Double,
    override val overTime: Long,
    override var follow: Boolean = true,
    override var distance: Int = 10,
    override var target: LivingEntity? = null,
    override var damageDistanc: Double = 1.8,
    override var zhengDu: Boolean = false,
) : SummonedYuLingCat(master, entity, deviationX, deviationZ, deviationY, overTime, follow, distance) {

    val villager by lazy {
        val entity = getLocation().world?.spawn(getLocation(), Villager::class.java) {
            it.addPotionEffect(PotionEffect(PotionEffectType.INVISIBILITY, Int.MAX_VALUE, 0, false, false))
            it.isSilent = true
            it.isGlowing = false
            it.isInvulnerable = true
            it.setAI(false)
            it.setBaby()
            it.addPotionEffect(PotionEffect(PotionEffectType.FIRE_RESISTANCE, Int.MAX_VALUE, 0, false, false))
        }!!
        entity
    }

    override fun move(destination: Location): Boolean {
        return super.move(destination)
    }

    override fun onUpdate(): Boolean {
        villager.fireTicks = 0
        setTimeName()
        player?.let { player ->
            if (target != null) {
                if (target!!.isDead || getLocation().distance(target!!.location) >= 30) {
                    target = null
                } else {
                    val value = PanLingAPI.getPlayerData(player, PanLingStatic.STRENGTH_BOW).toInt() * 0.3
                    attack(target!!, value + 1)
                }
            }
        }
        if (!villager.isDead) {
            villager.teleport(getLocation())
        }
        return super.onUpdate()
    }

    override fun setTimeName() {
        val time = (overTime - System.currentTimeMillis()) / 1000.0
        if (zhengDu) {
            entity.setCustomName("${time.toInt()}s &e[争]".colored())
        } else {
            entity.setCustomName("${time.toInt()}s")
        }
    }

    fun ridicule() {
        TargetRange.get(villager, 10.0, false).forEach {
            if (it is Mob) {
                it.target = villager
                spawnColor(5, it.location.clone().add(0.0, 2.0, 0.0).toProxyLocation(), 69, 70, 94, 3F)
            }
        }
        getLocation().world?.playSound(getLocation(), Sound.ENTITY_POLAR_BEAR_HURT, 1F, 1F)
        follow = false
        submit(delay = 20 * 10) {
            villager.remove()
            follow = true
        }

    }
}
