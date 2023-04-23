package top.maplex.rayskillsystem.skill.impl.dan.def.tu

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
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
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffJieZe
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.heal
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetRange

object SkillJinglianTu : AbstractSkill, YuanSu, AbstractDanCast {
    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "精炼土元素"

    override val type: String = "炼丹师"

    //浓缩600
    override val cooldown: Long = 20 * 30

    override val itemId: String = "panling:refined_earth"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "refined_earth")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        TargetRange.get(player, 8.0, true).forEach {
            if (!Team.canAttack(player, it) && BuffManager.getBuff(it, BuffJieZe.id) <= 0) {
                if (it.hasPotionEffect(PotionEffectType.ABSORPTION)) {
                    val get = it.getPotionEffect(PotionEffectType.ABSORPTION)
                    val levelz = get!!.amplifier
                    it.removePotionEffect(PotionEffectType.ABSORPTION)
                    it.addPotionEffect(PotionEffectType.ABSORPTION.createEffect(1800 * 20, levelz * 2))
                    BuffManager.add(it, BuffJieZe.id, 1, 20 * 60, player.uniqueId)
                }
            }
        }
        return true
    }
}