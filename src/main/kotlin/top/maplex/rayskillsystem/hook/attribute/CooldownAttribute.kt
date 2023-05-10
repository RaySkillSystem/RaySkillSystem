package top.maplex.rayskillsystem.hook.attribute

import org.serverct.ersha.api.annotations.AutoRegister
import org.serverct.ersha.api.component.SubAttribute
import org.serverct.ersha.attribute.enums.AttributeType

/**
 * 技能冷却缩减程度
 * 100点应该减少 100%
 */

const val SKILL_COOLDOWN = "技能冷却"

@AutoRegister
object CooldownAttribute : SubAttribute(
    -1, 1.0,
    SKILL_COOLDOWN,
    AttributeType.OTHER,
    "lankle_cooldown"
)