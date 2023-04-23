package top.maplex.rayskillsystem.skill.impl.dan.def.mu

import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.impl.dan.def.YuanSu
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffHuiChun
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.heal
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetRange

object SkillJinglianMu : AbstractSkill, YuanSu, AbstractDanCast {
    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "精炼木元素"

    override val type: String = "炼丹师"

    //浓缩600
    override val cooldown: Long = 20 * 10

    override val itemId: String = "panling:refined_wood"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "refined_wood")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = (attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1)
        TargetRange.get(player, 8.0, true).forEach {
            heal(player, it, value)
            if (!Team.canAttack(player, it)) {
                BuffManager.add(it, BuffHuiChun.id, 1, 20 * 6L, player.uniqueId)
            }
        }
        Circle(player.location.toProxyLocation(), 8.0, 1.0, 1,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(1, location.add(0.0, 1.0, 0.0), 42, 110, 63, 2F)
                }
            }).show()
        return true
    }
}