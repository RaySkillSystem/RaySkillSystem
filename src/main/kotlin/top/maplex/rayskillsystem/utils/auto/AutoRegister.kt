package top.maplex.rayskillsystem.utils.auto

import taboolib.common.LifeCycle
import taboolib.common.io.runningClasses
import taboolib.common.platform.Awake

object AutoRegister {

    @Awake(LifeCycle.ACTIVE)
    fun eval() {
        runningClasses.forEach { clazz ->
            if (clazz.isAnnotationPresent(RaySkillSystem::class.java)) {
                val function = clazz.getMethod("register")
                function.isAccessible = true

                val target =
                function.invoke(clazz.newInstance())
            }
        }
    }

}
