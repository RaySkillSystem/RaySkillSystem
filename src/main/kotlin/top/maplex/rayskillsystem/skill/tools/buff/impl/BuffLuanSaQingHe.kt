package top.maplex.rayskillsystem.skill.tools.buff.impl

import org.bukkit.Material
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff

object BuffLuanSaQingHe : AbstractBuff {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val id: String = "乱撒清荷"
    override val name: String = "&a乱撒清荷"
    override val info: String = "&e持续期间炼化元素无冷却"
    override val icon: Material = Material.APPLE


}