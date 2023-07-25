package top.maplex.rayskillsystem.skill.impl

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.entity.EntityTypes
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.summoned.expand.Follow
import top.maplex.rayskillsystem.skill.tools.summoned.impl.SummonedAdyeshach
import top.maplex.rayskillsystem.utils.auto.RaySkillSystem

@RaySkillSystem
object SkillZhao : AbstractSkill {

    override val name: String = "测试召唤"

    override val type: String = "无"

    override fun getCooldown(livingEntity: LivingEntity, level: Int): Long {
        return 3 * 20
    }

    override fun onCondition(livingEntity: LivingEntity, level: Int): Boolean {
        return true
    }

    override fun onRun(livingEntity: LivingEntity, level: Int): Boolean {
        val entity = Adyeshach.api().getPublicEntityManager().create(
            EntityTypes.PIG, livingEntity.location
        ) {
            it.isCollision = false
            it.setCustomName("测试召唤物")
            it.setNoGravity(false)
            it.setCustomNameVisible(true)
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
