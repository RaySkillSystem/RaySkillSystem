package top.maplex.rayskillsystem.skill.tools.attribute.impl

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.serverct.ersha.AttributePlus
import org.serverct.ersha.api.annotations.AutoRegister
import org.serverct.ersha.api.component.SubAttribute
import org.serverct.ersha.attribute.enums.AttributeType
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import top.maplex.rayskillsystem.skill.tools.attribute.AbstractAttribute


const val SKILL_COOLDOWN = "技能冷却"

@AutoRegister
object CooldownAttribute : SubAttribute(
    -1, 1.0,
    SKILL_COOLDOWN,
    AttributeType.OTHER,
    "rss_cooldown"
)

object AttributePlusHook : AbstractAttribute {

    override val name: String = "AttributePlus"

    @Awake(LifeCycle.ENABLE)
    fun init() {
        Bukkit.getPluginManager().getPlugin("AttributePlus")?.let {
            register()
        }
    }

    override fun getCooldown(player: Player): Double {
        val attribute = AttributePlus.attributeManager.getAttributeData(player)
        return attribute.getRandomValue(SKILL_COOLDOWN).toDouble()
    }

}
