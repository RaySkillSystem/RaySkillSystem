package top.maplex.rayskillsystem.skill.impl.dan.def.mu

import org.bukkit.Material
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.takeItem
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.impl.dan.def.YuanSu
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffChunNiHuHua
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.heal
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import top.maplex.rayskillsystem.skill.tools.target.TargetSingle
import top.maplex.rayskillsystem.utils.getString


object SkillNongSuoMu : AbstractSkill, YuanSu, AbstractDanCast {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "浓缩木元素"

    override val type: String = "炼丹师"

    //浓缩600
    override val cooldown: Long = 600

    override val itemId: String = "panling:again_refined_wood"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }


    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "panling:again_refined_wood")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        Circle(player.location.toProxyLocation(), 10.0, 1.0, 1,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(1, location.add(0.0, 1.0, 0.0), 107, 121, 142, 1F)
                }
            }).show()
        TargetRange.get(player.location, 10.0).forEach {
            if (!Team.canAttack(player, it)) {
                val attribute = PlayerManager.getPlayerData(player).attribute
                val value = attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1
                heal(player, it, value * 1.25)
                BuffManager.add(it, BuffChunNiHuHua.id, 8, 20 * 120, player.uniqueId)
            }
        }
        return true
    }


}