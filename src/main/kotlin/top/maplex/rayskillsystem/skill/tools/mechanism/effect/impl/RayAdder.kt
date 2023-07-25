package top.maplex.rayskillsystem.skill.tools.mechanism.effect.impl

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import taboolib.common.platform.function.submit
import taboolib.common.util.Vector
import taboolib.module.effect.createRay
import taboolib.module.effect.shape.Ray
import taboolib.platform.util.toBukkitLocation
import taboolib.platform.util.toProxyLocation
import java.util.*
import java.util.function.Consumer

object RayAdder {
    fun backShow(
        origin: Location,
        maxLength: Double,
        step: Double,
        range: Double = 0.5,
        spawner: Consumer<Location>,
        //是否可以穿墙 true 则不可以
        wall: Boolean = true,
        //动画播放速度
        period: Long = 0L,
        //每一帧的步长
        stepTick: Int = 1,
        //播放时执行
        action: Consumer<Location> = Consumer { },
        //碰到实体后执行
        near: Consumer<LivingEntity> = Consumer { },
        //结束时执行
        over: Consumer<Any> = Consumer { },
        outTime: Long = 10000L,
    ) {
        val mark = System.currentTimeMillis() + outTime
        createRay(origin.toProxyLocation(), origin.toProxyLocation().direction, maxLength, step, range, Ray.RayStopType.MAX_LENGTH, period) {
            spawner.accept(it.toBukkitLocation())
        }.backShow(wall, period, stepTick, {
            action.accept(this)
        }, {
            near.accept(this)
        }, {
            over.accept(this)
        })
        submit(period = 20) {
            if (System.currentTimeMillis() > mark) {
                over.accept(this)
                cancel()
            }
        }

    }

    fun getEntity(ray: Ray, tolerance: Double): MutableList<LivingEntity> {
        return ray.getEntity(tolerance)
    }

}

fun Ray.backShow(
    //是否可以穿墙 true 则不可以
    wall: Boolean = true,
    //动画播放速度
    period: Long = 0L,
    //每一帧的步长
    stepTick: Int = 1,
    //播放时执行
    action: Location.() -> Unit = {},
    //碰到实体后执行
    near: LivingEntity.() -> Unit = {},
    //结束时执行
    over: () -> Unit = {},
): Ray {
    var i = 0.0
    submit(period = period) {
        if (i > maxLength) {
            over.invoke()
            cancel()
        }
        var a = stepTick
        val list = mutableListOf<UUID>()
        while (a <= 0) {
            a--
            val vectorTemp = direction.clone().multiply(i)
            val spawnLocation = origin.clone().add(vectorTemp)
            spawnParticle(spawnLocation)
            action.invoke(spawnLocation.toBukkitLocation())
            //每一帧只会给一个目标造成一次伤害
            if (wall && spawnLocation.toBukkitLocation().block.type != Material.AIR) {
                over.invoke()
                return@submit
            }
            spawnLocation.toBukkitLocation().world?.getNearbyEntities(spawnLocation.toBukkitLocation(), 0.2, 0.2, 0.2) {
                it is LivingEntity
            }?.forEach {
                (it as? LivingEntity)?.let { player ->
                    if (!list.contains(player.uniqueId)) {
                        near.invoke(player)
                        list.add(player.uniqueId)
                    }
                }
            }
            i += step
        }
    }


    return this
}

fun Ray.getEntity(tolerance: Double): MutableList<LivingEntity> {
    var i = 0.0
    val list = mutableListOf<LivingEntity>()
    while (i < maxLength) {
        val vectorTemp = direction.clone().multiply(i)
        val spawnLocation = origin.clone().add(vectorTemp)
        spawnParticle(spawnLocation)
        spawnLocation.toBukkitLocation().world
            ?.getNearbyEntities(spawnLocation.toBukkitLocation(), tolerance, 3.0, tolerance)?.forEach {
                (it as? LivingEntity)?.let { player ->
                    if (!list.contains(player)) {
                        list.add(player)
                    }
                }
            }
        i += step
    }
    return list
}
