package top.maplex.rayskillsystem.skill.impl.bow.yuling

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import top.maplex.rayskillsystem.skill.AbstractSkill


object SkillBaiChuanGuiHai : AbstractSkill {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "百川归海"

    override val type: String = "弓箭手"

    override val cooldown: Long = 2 * 60 * 20

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val amount = (SkillYuLing.getPlayerYuLing(player).size + 1) * 0.1
        val maxHeal = player.getAttribute(Attribute.GENERIC_MAX_HEALTH)?.value ?: 20.0
        val value = (maxHeal) * amount
        if (player.health + value >= maxHeal) {
            player.health = maxHeal
        } else {
            player.health += value
        }
        SkillYuLing.getPlayerYuLing(player).forEach {
            it.delete()
        }
        val location = player.location
        val direction = location.direction.normalize()
        val jumpVector = direction.clone().multiply(-0.8).setY(0.3)
        player.velocity = jumpVector
        return true
    }


}