package top.maplex.rayskillsystem.skill.tools.summoned

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.Schedule
import taboolib.common.platform.event.SubscribeEvent
import java.util.concurrent.ConcurrentHashMap

object SummonedManager {

    val data = ConcurrentHashMap.newKeySet<SummonedEntity>()


    @Schedule(period = 5)
    fun update() {
        data.forEach {
            it.onUpdate()
        }
    }

    @Awake(LifeCycle.DISABLE)
    fun close() {
        data.forEach {
            it.delete()
        }
    }

}