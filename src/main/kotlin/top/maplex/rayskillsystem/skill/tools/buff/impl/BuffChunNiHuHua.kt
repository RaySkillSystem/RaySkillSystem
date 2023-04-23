package top.maplex.rayskillsystem.skill.tools.buff.impl

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.heal
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor

object BuffChunNiHuHua : AbstractBuff {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val id: String = "春泥护花"
    override val name: String = "&a春泥护花"
    override val info: String = "&e每次受伤减少5% 并恢复血量"
    override val icon: Material = Material.CORNFLOWER

    @SubscribeEvent
    fun damage(event: EntityDamageByEntityEvent) {
        val entity = event.entity
        val buff = BuffManager.data.getOrDefault(entity.uniqueId, hashMapOf())[id] ?: return
        val level = buff.level
        if (level == 0) {
            return
        }
        buff.level -= 1
        val from = Bukkit.getPlayer(buff.from) ?: return
        event.damage = event.damage - (event.damage * (0.05))

        val attribute = PlayerManager.getPlayerData(from).attribute
        val value = attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1
        heal(from, entity as LivingEntity, value * 0.05)

        Circle(entity.location.toProxyLocation(), 1.5, 1.0, 1,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(1, location.add(0.0, 1.0, 0.0), 102, 136, 158, 1F)
                }
            }).play()

    }


}