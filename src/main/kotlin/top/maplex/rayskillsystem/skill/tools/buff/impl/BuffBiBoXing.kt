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
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage as Damage

object BuffBiBoXing : AbstractBuff {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val id: String = "碧波行"
    override val name: String = "&a碧波行"
    override val info: String = "&e对周围目标造成窒息效果"
    override val icon: Material = Material.STRING

    override fun onJoin(target: LivingEntity, level: Int, time: Long): Boolean {
        target.addPotionEffect(PotionEffect(PotionEffectType.SPEED, time.toInt(), 3))
        return super.onJoin(target, level, time)
    }

    @Schedule(period = 20)
    fun update() {
        BuffManager.data.toMap().forEach { (t, u) ->
            val player = Bukkit.getPlayer(t) ?: return@forEach
            val level = u[id]?.level ?: return@forEach
            if (level == 0) {
                return@forEach
            }
            Circle(player.location.toProxyLocation(), 2.5, 10.0, 1,
                object : ParticleSpawner {
                    override fun spawn(location: Location) {
                        spawnColor(5, location.add(0.0, 1.0, 0.0), 37, 204, 247, 2F)
                    }
                }).show()
            val attribute = PlayerManager.getPlayerData(player).attribute
            val value = (attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1) * 0.8
            player.getNearbyEntities(2.5, 5.0, 2.5).forEach { entity ->
                if (entity is LivingEntity) {
                    val damage = entity.health * 0.03
                    Damage(player, entity, damage + value)
                    spawnColor(
                        10,
                        entity.location.toProxyLocation().add(0.0, 1.5, 0.0),
                        90, 164, 174, 4F
                    )
                }
            }
        }
    }


}