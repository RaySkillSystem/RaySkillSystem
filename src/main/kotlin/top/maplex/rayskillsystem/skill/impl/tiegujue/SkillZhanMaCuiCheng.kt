package top.maplex.rayskillsystem.skill.impl.tiegujue

import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.submit
import taboolib.common.util.Location
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Astroid
import taboolib.platform.util.buildItem
import taboolib.platform.util.toProxyLocation
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.buff.BuffManager
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffLieShan
import top.maplex.rayskillsystem.skill.tools.buff.impl.BuffPoZhenTieJia
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetSingle
import java.util.*

object SkillZhanMaCuiCheng : AbstractSkill {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "断马摧城"

    override val type: String = "铁骨诀"

    override val cooldown: Long = 1 * 20

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillZhanMaCuiCheng.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetSingle.getLivingTargets(player, 3.0, 2.0).firstOrNull {
            it !is Player && Team.canAttack(player, it)
        } ?: return false
        Astroid(2.0, target.location.toProxyLocation(), object : ParticleSpawner {
            override fun spawn(location: Location) {
                spawnColor(0, location, Particle.FIREWORKS_SPARK)
            }
        }).show()
        submit(delay = 3) {
            Astroid(1.0, target.location.toProxyLocation().clone().add(0.0, 1.0, 0.0), object : ParticleSpawner {
                override fun spawn(location: Location) {
                    spawnColor(0, location, Particle.FIREWORKS_SPARK)
                }
            }).show()
        }
        var armor = (player.getAttribute(Attribute.GENERIC_ARMOR)?.value ?: 1.0) * 3
        val poZhen = BuffManager.getBuff(player, BuffPoZhenTieJia.id)
        if (poZhen != 0) {
            armor *= ((poZhen * 0.5) + 1)
            BuffManager.clearBuff(player, BuffPoZhenTieJia.id)
        }
        val lieChuan = BuffManager.getBuff(player, BuffLieShan.id)
        if (lieChuan != 0) {
            armor *= (lieChuan + 1)
            BuffManager.clearBuff(player, BuffLieShan.id)
        }
        damage(player, listOf(target), armor)
        return true
    }


}