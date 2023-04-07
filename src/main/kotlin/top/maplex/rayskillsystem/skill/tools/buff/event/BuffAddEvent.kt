package top.maplex.rayskillsystem.skill.tools.buff.event

import org.bukkit.entity.LivingEntity
import taboolib.platform.type.BukkitProxyEvent
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff
import top.maplex.rayskillsystem.skill.tools.buff.BuffData

class BuffAddEvent(
    val target: LivingEntity,
    val buff: AbstractBuff,
    val level: Int,
    val tick: Long,
) : BukkitProxyEvent()