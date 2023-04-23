package top.maplex.rayskillsystem.skill.tools.buff.impl

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.event.entity.EntityDamageEvent
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.module.effect.shape.Heart
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.heal
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor

object BuffJieHuoLing : AbstractBuff {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val id: String = "劫火令"
    override val name: String = "&4劫火令"
    override val info: String = "&4受到额外伤害"
    override val icon: Material = Material.FIRE

    @SubscribeEvent
    fun damageX(event: EntityDamageByEntityEvent) {
        val entity = event.entity as? LivingEntity ?: return
        val buff = BuffManager.data.getOrDefault(entity.uniqueId, hashMapOf())[id] ?: return
        val level = buff.level
        if (level == 0) {
            return
        }
        val from = Bukkit.getPlayer(buff.from) ?: return
        if (event.damager == from) {
            return
        }

        val attribute = PlayerManager.getPlayerData(from).attribute
        val value = (attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1) * 1.25
        damage(from, entity, value)
        Heart(1.5, 1.5, entity.location.toProxyLocation(), 1L,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(1, location.add(0.0, 1.0, 0.0), Particle.FLAME)
                }
            }).show()

    }


}