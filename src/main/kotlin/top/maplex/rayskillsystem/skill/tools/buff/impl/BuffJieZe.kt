package top.maplex.rayskillsystem.skill.tools.buff.impl

import org.bukkit.Material
import org.bukkit.entity.LivingEntity
import org.bukkit.potion.PotionEffectType
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import top.maplex.rayskillsystem.skill.tools.buff.AbstractBuff

object BuffJieZe : AbstractBuff {


    @Awake(LifeCycle.LOAD)
    fun onEnable() {
        register()
    }

    override val id: String = "竭泽"
    override val name: String = "&6竭泽"
    override val info: String = "&e效果持续期间无法再次受到精炼土效果"
    override val icon: Material = Material.STONE

    override fun onOver(target: LivingEntity, level: Int, time: Long): Boolean {
        target.removePotionEffect(PotionEffectType.ABSORPTION)
        return super.onOver(target, level, time)
    }

}