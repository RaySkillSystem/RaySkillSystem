package top.maplex.rayskillsystem.skill.tools.attribute.impl

import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Player
import org.serverct.ersha.AttributePlus
import org.serverct.ersha.api.AttributeAPI
import org.serverct.ersha.api.annotations.AutoRegister
import org.serverct.ersha.api.component.SubAttribute
import org.serverct.ersha.attribute.enums.AttributeType
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.submit
import top.maplex.rayskillsystem.skill.tools.attribute.AbstractAttribute
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap


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
            outAttribute()
        }
    }

    // 属性持有者 -> 源头 -> 过期时间
    val time = ConcurrentHashMap<UUID, HashMap<String, Long>>()

    private fun outAttribute() {
        submit(period = 10) {
            time.forEach z@{ (uuid, map) ->
                map.forEach { (source, time) ->
                    if (time < System.currentTimeMillis()) {
                        val target = Bukkit.getEntity(uuid) as? LivingEntity ?: return@forEach
                        val data = AttributePlus.attributeManager.getAttributeData(target)
                        AttributeAPI.takeSourceAttribute(data, source)
                    }
                }
            }
        }
    }

    override fun getAttribute(livingEntity: LivingEntity, attribute: String, default: Double): Double {
        val attributeData = AttributePlus.attributeManager.getAttributeData(livingEntity)
        return attributeData.getRandomValue(attribute).toDouble()
    }

    override fun addAttribute(livingEntity: LivingEntity, attribute: String, value: Double, source: String) {
        val data = AttributePlus.attributeManager.getAttributeData(livingEntity)
        AttributeAPI.addSourceAttribute(data, source, listOf("${attribute}: ${value}"), false)
    }

    override fun tempAttribute(
        livingEntity: LivingEntity,
        attribute: String,
        value: Double,
        source: String,
        tick: Long,
        force: Boolean
    ) {
        val data = AttributePlus.attributeManager.getAttributeData(livingEntity)
        AttributeAPI.addSourceAttribute(data, source, listOf("${attribute}: $value"), false)
        val millisecond = tick * 50
        if (force) {
            time.computeIfAbsent(livingEntity.uniqueId) { HashMap() }[source] = System.currentTimeMillis() + millisecond
        } else {
            val old = time.computeIfAbsent(livingEntity.uniqueId) { HashMap() }[source] ?: System.currentTimeMillis()
            time.computeIfAbsent(livingEntity.uniqueId) { HashMap() }[source] = millisecond + old
        }
    }

    override fun removeAttribute(livingEntity: LivingEntity, source: String) {
        val data = AttributePlus.attributeManager.getAttributeData(livingEntity)
        AttributeAPI.takeSourceAttribute(data, source)
    }

    override fun getCooldown(livingEntity: LivingEntity): Double {
        val attribute = AttributePlus.attributeManager.getAttributeData(livingEntity)
        return attribute.getRandomValue(SKILL_COOLDOWN).toDouble()
    }

}
