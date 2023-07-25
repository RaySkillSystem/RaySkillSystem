package top.maplex.rayskillsystem.skill.impl

import ink.ptms.adyeshach.core.Adyeshach
import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.impl.RayAdder

//@RaySkillSystem
object SkillPao : AbstractSkill {

    override val name: String = "测试抛射物"

    override val type: String = "无"

    override fun getCooldown(livingEntity: LivingEntity, level: Int): Long {
        return 3 * 20
    }

    override fun onCondition(livingEntity: LivingEntity, level: Int): Boolean {
        return true
    }

    override fun onRun(livingEntity: LivingEntity, level: Int): Boolean {
        val entity = Adyeshach.api().getPublicEntityManager().create(
            ink.ptms.adyeshach.core.entity.EntityTypes.ARMOR_STAND, livingEntity.location
        ) {
            it.isCollision = false
            it.setNoGravity(false)
            it.setCustomName("测试弹道")
            it.setCustomNameVisible(true)
        }
        RayAdder.backShow(
            livingEntity.eyeLocation,
            40.0,
            0.5,
            0.5,
            spawner = {

            },
            false,
            10L,
            action = { it: org.bukkit.Location ->
                entity.teleport(it)
            },
            near = { it: org.bukkit.entity.LivingEntity ->
                println("1")
            },
            over = {
                entity.remove()
            }, stepTick = 1, outTime = 1000L
        )
        println("Running")
        return true
    }


}
