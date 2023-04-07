package top.maplex.rayskillsystem.skill.tools.mechanism.flag.event

import taboolib.platform.type.BukkitProxyEvent
import java.util.UUID

class FlagEndEvent(
    val uuid: UUID,
    val key: String
) : BukkitProxyEvent() {

    fun run(): FlagEndEvent {
        call()
        return this
    }

}