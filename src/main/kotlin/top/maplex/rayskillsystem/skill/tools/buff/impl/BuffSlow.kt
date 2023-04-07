package top.maplex.rayskillsystem.skill.tools.buff.impl

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

object BuffSlow : AbstractBuff {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val id: String = "减速"
    override val name: String = "&b减速"
    override val info: String = "&e移动速度减少"
    override val icon: Material = Material.ICE

    override fun onJoin(target: LivingEntity, level: Int, time: Long): Boolean {
        target.addPotionEffect(PotionEffect(PotionEffectType.SLOW, time.toInt(), level))
        return true
    }


}