package top.maplex.rayskillsystem.utils.auto

import taboolib.common.LifeCycle
import taboolib.common.io.runningClasses
import taboolib.common.platform.Awake

object AutoRegister {

    /**
     * 自动注册注解
     */
    @Awake(LifeCycle.ACTIVE)
    fun eval() {
        runningClasses.forEach { clazz ->
            if (clazz.isAnnotationPresent(RaySkillSystem::class.java)) {
                val function = clazz.getMethod("register")
                function.isAccessible = true

                val target = clazz.getDeclaredConstructor()
                target.isAccessible = true
                val instance = target.newInstance()
                function.invoke(instance)
            }
        }
    }

}
