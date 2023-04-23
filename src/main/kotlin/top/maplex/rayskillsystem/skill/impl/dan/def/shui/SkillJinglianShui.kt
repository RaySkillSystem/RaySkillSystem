package top.maplex.rayskillsystem.skill.impl.dan.def.shui

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
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
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffYiBuChenXu
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import top.maplex.rayskillsystem.skill.tools.target.TargetSingle
import top.maplex.rayskillsystem.utils.getString


object SkillJinglianShui : AbstractSkill, YuanSu, AbstractDanCast {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "精炼水元素"

    override val type: String = "炼丹师"

    //浓缩600
    override val cooldown: Long = 160

    override val itemId: String = "panling:refined_water"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }


    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "refined_water")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetRange.get(player, 6.0, true)
        Circle(player.location.toProxyLocation(), 6.0, 1.0, 1,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(5, location.add(0.0, 1.0, 0.0), 37, 204, 247, 2F)
                }
            }).show()
        target.forEach {
            if (!Team.canAttack(player, it)) {
                it.fireTicks = 0
                it.removePotionEffect(PotionEffectType.POISON)
                it.removePotionEffect(PotionEffectType.WITHER)
                it.removePotionEffect(PotionEffectType.BLINDNESS)
                it.removePotionEffect(PotionEffectType.CONFUSION)
                it.removePotionEffect(PotionEffectType.HUNGER)
                BuffManager.add(it, BuffYiBuChenXu.id, 1, 20 * 10, player.uniqueId)
            }
        }
        return true
    }


}