package top.maplex.rayskillsystem.api.script.auto

import taboolib.common.LifeCycle
import taboolib.common.io.runningClasses
import taboolib.common.platform.Awake
import top.maplex.rayskillsystem.utils.toConsole

object InputEngineImpl {

    val list = HashMap<String, Any>()

    /**
     * 类方法自动注入Js引擎
     */
    @Awake(LifeCycle.ENABLE)
    fun eval() {
        runningClasses.forEach { clazz ->
            if (clazz.isAnnotationPresent(InputEngine::class.java)) {
                val target = clazz.getDeclaredConstructor()
                target.isAccessible = true
                val instance = target.newInstance()
                val annotation = clazz.getAnnotation(InputEngine::class.java)
                list[annotation.key] = instance
                toConsole("注入工具类: ${annotation.key}",true)
            }
        }
    }

}
