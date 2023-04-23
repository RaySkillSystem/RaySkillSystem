package top.maplex.rayskillsystem.skill.impl.dan.def.shui

import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffectType
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.impl.dan.def.YuanSu
import top.maplex.rayskillsystem.skill.tools.target.TargetRange


object SkillShui : AbstractSkill, YuanSu, AbstractDanCast {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "水元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 50

    override val itemId: String = "panling:water"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "water")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetRange.get(player, 5.0, true)
        target.forEach {
            it.removePotionEffect(PotionEffectType.POISON)
            it.removePotionEffect(PotionEffectType.WITHER)
        }
        return true
    }


}