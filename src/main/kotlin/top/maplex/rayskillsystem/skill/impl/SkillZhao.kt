package top.maplex.rayskillsystem.skill.impl

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.entity.EntityTypes
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.summoned.impl.SummonedAdyeshach
import top.maplex.rayskillsystem.utils.auto.RaySkillSystem

@RaySkillSystem
object SkillZhao : AbstractSkill {

    override val name: String = "测试召唤"

    override val type: String = "无"

    override val cooldown: Long = 3 * 20

    override fun onCondition(livingEntity: LivingEntity, level: Int): Boolean {
        return true
    }

    override fun onRun(livingEntity: LivingEntity, level: Int): Boolean {
        val entity = Adyeshach.api().getPublicEntityManager().create(
            EntityTypes.CAT, livingEntity.location
        ) {
            it.isCollision = false
        }
        SummonedAdyeshach(
            livingEntity.uniqueId,
            entity,
            1.0, 1.0, 1.0,
            System.currentTimeMillis() + (1000 * 20)
        )
        return true
    }


}
