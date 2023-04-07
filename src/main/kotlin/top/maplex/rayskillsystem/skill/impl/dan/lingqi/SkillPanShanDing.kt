package top.maplex.rayskillsystem.skill.impl.dan.lingqi

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import ray.mintcat.aboleth.api.AbolethAPI
import ray.mintcat.aboleth.api.AbolethAction
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.submit
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.buildItem
import taboolib.platform.util.toProxyLocation
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.impl.dan.def.YuanSu
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffLieShan
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffNoMove
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffSlow
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetRange

object SkillPanShanDing : AbstractSkill,YuanSu {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "磐山定"

    override val type: String = "炼丹师"

    override val cooldown: Long = 1 * 20

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillPanShanDing.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "earth", 3)
    }

    override fun onRun(player: Player, level: Int): Boolean {
        Circle(player.location.toProxyLocation(), 5.0, 10.0, 1,
            object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(5, location.add(0.0, 1.0, 0.0), 95, 67, 33, 2F)
                }
            }).play()
        val targets = TargetRange.get(player, 5.0, false)
        BuffManager.add(player, BuffLieShan.id, 1, 5 * 20,player.uniqueId)
        targets.forEach {
            if (Team.canAttack(player,it)){
                BuffManager.add(it, BuffSlow.id, 3, (20 * 2).toLong(), player.uniqueId)
            }
        }
        submit(delay = 20 * 2) {
            targets.forEach {
                if (Team.canAttack(player,it)){
                    spawnColor(5, it.location.toProxyLocation().add(0.0, 2.25, 0.0), 95, 67, 33, 2F)
                    BuffManager.add(it, BuffNoMove.id, 1, (20 * 1.5).toLong(), player.uniqueId)
                }
            }
        }

        return true
    }


}