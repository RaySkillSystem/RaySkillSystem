package top.maplex.rayskillsystem.skill.tools.buff.impl

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import java.util.*

object BuffFire : AbstractBuff {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val id: String = "燃烧"
    override val name: String = "&c燃烧"
    override val info: String = "&e持续减少生命最大值血量"
    override val icon: Material = Material.ICE

    override fun onTick(target: LivingEntity, level: Int, time: Long, from: UUID): Boolean {
        val value = (target.health * (1 + 0.02 * level)) - target.health
        //max_hp * (1 + 0.02 * level)
        val entity = Bukkit.getEntity(from)
        target.damage(value, entity)
        target.fireTicks += 1
        return super.onTick(target, level, time, from)
    }

}