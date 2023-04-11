package top.maplex.rayskillsystem.skill.impl.bow.yuling

import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.submit
import taboolib.platform.util.toProxyLocation
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetSingle
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import top.maplex.rayskillsystem.utils.info


object SkillZhengDu : AbstractSkill {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "争渡"

    override val type: String = "弓箭手"

    override val cooldown: Long = 0


    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetSingle.get(player, 6.0, 1.5) {
            Team.canAttack(player, this) && this !is Player
        } ?: return false
        SkillYuLing.getPlayerYuLing(player).forEach {
            it.target = target
            spawnColor(1, target.location.clone().add(0.0, 2.0, 0.0).toProxyLocation(), 177, 213, 200, 3F)
        }
        if (CooldownAPI.check(player, "争渡")) {
            player.info("已激活 争渡Buff!")
            SkillYuLing.getPlayerYuLing(player).forEach {
                it.zhengDu = true
                submit(delay = 10 * 20) {
                    it.zhengDu = false
                }
            }
            CooldownAPI.set(player, "争渡", 30 * 20)
        }
        return true
    }


}