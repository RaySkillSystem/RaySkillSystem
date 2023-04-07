package top.maplex.rayskillsystem.skill.tools.target

import org.bukkit.entity.LivingEntity
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.shape.Ray
import taboolib.platform.util.toProxyLocation
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.impl.getEntity

object TargetSingle {

    /**
     * 看到的实体第一个
     * @param source 触发者
     * @param range 范围
     * @param tolerance 容错率
     */
    fun get(
        source: LivingEntity,
        range: Double,
        tolerance: Double,
        filter: LivingEntity.() -> Boolean = { true },
    ): LivingEntity? {
        val list = getLivingTargets(source, range, tolerance, filter)
        return list.firstOrNull()
    }

    fun getLivingTargets(
        source: LivingEntity,
        range: Double,
        tolerance: Double,
        filter: LivingEntity.() -> Boolean = { true },
    ): List<LivingEntity> {
        return Ray(source.eyeLocation.toProxyLocation(),
            source.eyeLocation.toProxyLocation().direction,
            range,
            object : ParticleSpawner {
                override fun spawn(location: taboolib.common.util.Location) {
                    //spawnColor(0, location, Particle.FIREWORKS_SPARK)
                }
            }).getEntity(tolerance).filter {
            filter.invoke(it)
        }
    }

}