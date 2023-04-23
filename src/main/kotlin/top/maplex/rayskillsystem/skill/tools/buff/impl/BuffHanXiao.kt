package top.maplex.rayskillsystem.skill.tools.buff.impl

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager

object BuffHanXiao : AbstractBuff {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val id: String = "寒啸"
    override val name: String = "&c寒啸"
    override val info: String = "&e造成的伤害减少 20%"
    override val icon: Material = Material.SHIELD

    @SubscribeEvent
    fun damage(event: EntityDamageByEntityEvent) {
        val entity = event.damager
        if (entity is LivingEntity) {
            val level = BuffManager.getBuff(entity, id)
            if (level == 0) {
                return
            }
            event.damage = event.damage - (event.damage * (0.2))
        }

    }


}