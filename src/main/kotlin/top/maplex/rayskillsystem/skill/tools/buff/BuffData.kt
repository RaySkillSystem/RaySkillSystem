package top.maplex.rayskillsystem.skill.tools.buff

import java.util.UUID

data class BuffData(
    val name: String,
    var level: Int,
    var overtime: Long,
    var from: UUID,
)
