package top.maplex.rayskillsystem.skill.impl.dan.def

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.buildItem
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.target.TargetRange

object SkillJinglianMu: AbstractSkill, YuanSu, AbstractDanCast {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        SkillMu.register()
    }

    override val name: String = "精炼木元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 50

    override val itemId: String = "panling:refined_wood"
    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "refined_wood")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetRange.get(player, 8.0, true).filter {
            !Team.canAttack(player, it)
        }.let {
            if (it.size >=2) {
                it.subList(0,1)
            } else{
                it
            }
        }



        return true
    }




}