package top.maplex.rayskillsystem.skill.tools.summoned

import taboolib.common.platform.Schedule
import java.util.concurrent.ConcurrentHashMap

object SummonedManager {

    val data = ConcurrentHashMap.newKeySet<SummonedEntity>()


    @Schedule(period = 10)
    fun update() {
        data.forEach {
            it.onUpdate()
        }
    }

}