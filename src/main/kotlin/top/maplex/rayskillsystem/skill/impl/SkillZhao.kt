package top.maplex.rayskillsystem.skill.impl

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.entity.EntityTypes
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.summoned.impl.SummonedAdyeshach

object SkillZhao : AbstractSkill {

    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val name: String = "测试召唤"

    override val type: String = "无"

    override val cooldown: Long = 3 * 20

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val entity = Adyeshach.api().getPublicEntityManager().create(
            EntityTypes.CAT, player.location
        ) {
            it.isCollision = false
        }
        SummonedAdyeshach(
            player.uniqueId,
            entity,
            1.0, 1.0, 1.0,
            System.currentTimeMillis() + (1000 * 20)
        )
        return true
    }


}