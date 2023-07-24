package top.maplex.rayskillsystem.skill.tools.mechanism.effect.impl

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import taboolib.common.platform.function.submit
import taboolib.common.util.Location
import taboolib.common.util.Vector
import taboolib.module.effect.ParticleObj
import taboolib.module.effect.ParticleSpawner
import taboolib.module.effect.Playable
import taboolib.platform.util.toBukkitLocation

class EffectMissile(
    var start: Location,
    var end: Location,
    var step: Double,
    override var period: Long,
    spawner: ParticleSpawner,
    val amount: Int = 1,
    val wall: Boolean = false,
    val tickCall: EffectMissile.() -> Unit = {},
    val callBack: LivingEntity.() -> Boolean,
) : ParticleObj(spawner), Playable {

    private var vector: Vector? = null
    private var length = 0.0
    private var currentStep = 0.0

    init {
        resetVector()
    }

    override fun calculateLocations(): List<Location> {
        return listOf()
    }

    override fun show() {
        var i = 0.0
        while (i < length) {
            val vectorTemp = vector!!.clone().multiply(i)
            spawnParticle(start.clone().add(vectorTemp))
            i += step
        }
    }

    override fun play() {
        submit(period = period) {
            if (currentStep > length) {
                cancel()
                return@submit
            }
            tickCall.invoke(this@EffectMissile)
            currentStep += step
            val vectorTemp = vector!!.clone().multiply(currentStep)
            val now = start.clone().add(vectorTemp)
            spawnParticle(now)
            if (!wall && now.toBukkitLocation().block.type != Material.AIR) {
                cancel()
                return@submit
            }
            val getts = now.toBukkitLocation().world?.getNearbyEntities(now.toBukkitLocation(), 0.25, 0.25, 0.25)
                ?: return@submit
            if (getts.isNotEmpty()) {
                getts.forEachIndexed { index, livingEntity ->
                    if (livingEntity is LivingEntity) {
                        if (amount > index && callBack.invoke(livingEntity)) {
                            cancel()
                            return@submit
                        }
                    }
                }
            }
        }
    }

    override fun playNextPoint() {
        currentStep += step
        val vectorTemp = vector!!.clone().multiply(currentStep)
        spawnParticle(start.clone().add(vectorTemp))
        if (currentStep > length) {
            currentStep = 0.0
        }
    }

    /**
     * 利用给定的坐标设置线的起始坐标
     *
     * @param start 起始坐标
     * @return [Line]
     */
    fun setStart(start: Location): EffectMissile {
        this.start = start
        resetVector()
        return this
    }

    /**
     * 利用给定的坐标设置线的终点坐标
     *
     * @param end 终点
     * @return [Line]
     */
    fun setEnd(end: Location): EffectMissile {
        this.end = end
        resetVector()
        return this
    }

    /**
     * 设置每个粒子之间的间隔
     *
     * @param step 间隔
     * @return [Line]
     */
    fun setStep(step: Double): EffectMissile {
        this.step = step
        resetVector()
        return this
    }

    /**
     * 手动重设线的向量
     */
    fun resetVector() {
        vector = end.clone().subtract(start).toVector()
        length = vector!!.length()
        vector!!.normalize()
    }

    companion object {
        fun buildLine(locA: Location, locB: Location, step: Double, spawner: ParticleSpawner) {
            val vectorAB = locB.clone().subtract(locA).toVector()
            val vectorLength = vectorAB.length()
            vectorAB.normalize()
            var i = 0.0
            while (i < vectorLength) {
                spawner.spawn(locA.clone().add(vectorAB.clone().multiply(i)))
                i += step
            }
        }
    }
}
