package top.maplex.rayskillsystem.skill.tools.buff.impl

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager

object BuffLieShan : AbstractBuff {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val id: String = "列山"
    override val name: String = "&a列山"
    override val info: String = "&e减少受到的 30% 伤害"
    override val icon: Material = Material.SHIELD

    @SubscribeEvent
    fun damage(event: EntityDamageEvent) {
        val entity = event.entity
        if (entity is LivingEntity) {
            val level = BuffManager.getBuff(entity, id)
            if (level == 0) {
                return
            }
            event.damage = event.damage - (event.damage * (0.3))
        }

    }


}