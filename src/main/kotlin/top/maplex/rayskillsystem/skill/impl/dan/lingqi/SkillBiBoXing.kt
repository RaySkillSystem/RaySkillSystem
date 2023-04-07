package top.maplex.rayskillsystem.skill.impl.dan.lingqi

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ray.mintcat.aboleth.api.AbolethAPI
import ray.mintcat.aboleth.api.AbolethAction
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.platform.util.buildItem
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.impl.dan.def.YuanSu
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffBiBoXing

object SkillBiBoXing : AbstractSkill, YuanSu {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "碧波行"

    override val type: String = "炼丹师"

    override val cooldown: Long = 1 * 20

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillBiBoXing.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "water", 3)
    }

    override fun onRun(player: Player, level: Int): Boolean {
        BuffManager.add(player, BuffBiBoXing.id, 1, (5 * 20).toLong(), player.uniqueId)
        return true
    }


}