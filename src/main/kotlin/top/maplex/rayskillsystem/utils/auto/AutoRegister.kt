package top.maplex.rayskillsystem.utils.auto

import taboolib.common.LifeCycle
import taboolib.common.io.runningClasses
import taboolib.common.platform.Awake

object AutoRegister {

    @Awake(LifeCycle.ACTIVE)
    fun eval() {
        runningClasses.forEach {
            if (it.isAnnotationPresent(RaySkillSystem::class.java)) {
                it.getDeclaredMethod("register").invoke(null)
            }
        }
    }

}
