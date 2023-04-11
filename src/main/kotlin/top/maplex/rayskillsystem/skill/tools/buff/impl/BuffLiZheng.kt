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
import java.util.UUID
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage as Damage

object BuffLiZheng : AbstractBuff {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val id: String = "离争"
    override val name: String = "&c离争"
    override val info: String = "&e叠加五次后造成一次高额伤害"
    override val icon: Material = Material.STRING

    override fun onJoin(target: LivingEntity, level: Int, time: Long, from: UUID): Boolean {
        if (level >= 5) {
            BuffManager.clearBuff(target, id)
            val value = target.health * 0.05
            val player = Bukkit.getPlayer(from) ?: return false
            target.damage(value, player)
        }
        return true
    }


}