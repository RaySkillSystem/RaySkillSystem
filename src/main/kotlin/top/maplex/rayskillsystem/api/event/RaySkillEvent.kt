package top.maplex.rayskillsystem.api.event

import taboolib.platform.type.BukkitProxyEvent

open class RaySkillEvent : BukkitProxyEvent() {

    inline fun <reified T : RaySkillEvent> callEvent(): T {
        call()
        return this as T
    }

    inline fun <reified T : RaySkillEvent> T.callEventBack(cancelled: T.() -> Unit = {}): T {
        call()
        if (isCancelled) {
            cancelled()
        }
        return this
    }

}
