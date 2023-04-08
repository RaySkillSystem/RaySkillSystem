package top.maplex.rayskillsystem.skill.tools.buff.impl

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import java.util.*

object BuffHuiChun : AbstractBuff {


    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val id: String = "回春"
    override val name: String = "&a回春"
    override val info: String = "&e周期恢复血量 4.5%阵法强度"
    override val icon: Material = Material.BONE

    //每秒触发一次
    override fun onTick(target: LivingEntity, level: Int, time: Long, from: UUID): Boolean {
        val player = Bukkit.getPlayer(from) ?: return false
        Circle(target.location.toProxyLocation(), 1.0, 10.0, 1,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(5, location.add(0.0, 1.0, 0.0), 51, 255, 51, 2F)
                }
            }).show()
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = (attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1)
        target.health += (value * 0.045)
        return true
    }


}