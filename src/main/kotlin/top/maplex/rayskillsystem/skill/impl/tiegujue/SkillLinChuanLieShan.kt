package top.maplex.rayskillsystem.skill.impl.tiegujue

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Circle
import taboolib.platform.util.buildItem
import taboolib.platform.util.toProxyLocation
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffLieShan
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.taunt
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetRange
import java.util.*

object SkillLinChuanLieShan : AbstractSkill {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "临川列山"

    override val type: String = "铁骨诀"

    override val cooldown: Long = 30 * 20

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillLinChuanLieShan.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val targets = TargetRange.get(player, 3.0, false)
        if (targets.isEmpty()) {
            return false
        }
        Circle(player.location.toProxyLocation(), 3.0, 0.25, 1, object : ParticleSpawner {
            override fun spawn(location: Location) {
                spawnColor(2, location, Particle.FIREWORKS_SPARK)
            }
        }).show()
        taunt(player, targets, 300 * ((level * 0.1) + 1))
        BuffManager.add(player, BuffLieShan.id, 1, 15 * 20,player.uniqueId)
        return true
    }


}