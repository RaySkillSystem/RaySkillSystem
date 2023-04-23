package top.maplex.rayskillsystem.skill.tools.buff.impl

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Schedule
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.utils.info
import java.util.*
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage as Damage

object BuffYiBuChenXu : AbstractBuff {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val id: String = "逸尘步虚"
    override val name: String = "&b逸尘步虚"
    override val info: String = "&b免疫摔伤与缓降效果"
    override val icon: Material = Material.FEATHER

    override fun onJoin(target: LivingEntity, level: Int, time: Long, from: UUID): Boolean {
        target.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, time.toInt(), 2))
        return true
    }

    @SubscribeEvent
    fun onDamage(event: EntityDamageEvent) {
        val level = BuffManager.getBuff(event.entity, id)
        if (level <= 0) {
            return
        }
        if (event.cause == EntityDamageEvent.DamageCause.FALL) {
            event.isCancelled = true
        }
    }

}