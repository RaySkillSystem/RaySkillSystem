package top.maplex.rayskillsystem.skill.impl.dan.def

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.buildItem
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffHuiChun
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor


object SkillMu : AbstractSkill, YuanSu, AbstractDanCast {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "木元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 50

    override val itemId: String = "panling:wood"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillMu.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "wood")
    }
    override fun onRun(player: Player, level: Int): Boolean {
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1
        val heal = (value / 2) + 9

        val max = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0

        if (player.health + heal >= max) {
            player.health = max
        } else {
            player.health += heal
        }
        BuffManager.add(player, BuffHuiChun.id, 1, 100L, player.uniqueId)
        Circle(player.location.toProxyLocation(), 1.5, 8.0, 1,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(2, location.add(0.0, 1.0, 0.0), 51, 255,51, 2F)
                }
            }
        ).play()
        return true
    }
}