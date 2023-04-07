package top.maplex.rayskillsystem.skill.tools.mechanism.flag.event

import taboolib.platform.type.BukkitProxyEvent
import java.util.UUID

class FlagAddEvent(
    val uuid: UUID,
    val key: String
) : BukkitProxyEvent() {

    fun run(): FlagAddEvent {
        call()
        return this
    }

}