package top.maplex.rayskillsystem.api.script.auto

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class InputEngine(
    val key: String,
)
