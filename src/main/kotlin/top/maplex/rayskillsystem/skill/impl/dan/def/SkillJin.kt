package top.maplex.rayskillsystem.skill.impl.dan.def

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.submit
import taboolib.platform.util.buildItem
import taboolib.platform.util.toProxyLocation
import top.maplex.panlingcore.common.core.PlayerManager
import top.maplex.panlingcore.common.core.player.attribute.AttributeEnum
import top.maplex.rayskillsystem.caster.dan.AbstractDanCast
import top.maplex.rayskillsystem.caster.dan.DanFurnaceLunchEvent
import top.maplex.rayskillsystem.skill.AbstractSkill
import top.maplex.rayskillsystem.skill.tools.Team
import top.maplex.rayskillsystem.skill.tools.mechanism.damage.damage
import top.maplex.rayskillsystem.skill.tools.mechanism.effect.spawnColor
import top.maplex.rayskillsystem.skill.tools.target.TargetSingle
import top.maplex.rayskillsystem.utils.getString


object SkillJin : AbstractSkill, YuanSu, AbstractDanCast {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "金元素"

    override val type: String = "炼丹师"

    override val cooldown: Long = 50

    override val itemId: String = "panling:metal"

    @SubscribeEvent
    fun onLunch(event: DanFurnaceLunchEvent) {
        lunch(this, event)
    }

    override fun showItem(player: Player, level: Int): ItemStack {
        return buildItem(Material.PAPER) {
            this.name = "&f${SkillJin.name}"
            colored()
        }
    }

    override fun onCondition(player: Player, level: Int): Boolean {
        return take(player, "metal")
    }

    override fun onRun(player: Player, level: Int): Boolean {
        val target = TargetSingle.get(player, 6.0, 1.5) {
            Team.canAttack(player, this) && this !is Player
        } ?: return false
        val attribute = PlayerManager.getPlayerData(player).attribute
        val value = attribute.getAttribute(AttributeEnum.ARRAY_STRENGTH) + 1
        damage(player, target, value * 3.5)
        spawnColor(1, target.location.toProxyLocation(), 255, 238, 111, 3F)

        //铁砧特效
        val targetLocation = target.location.add(0.0, 2.0, 0.0)
        if (targetLocation.block.type == Material.AIR) {
            spawn(target.location.add(0.0, 2.0, 0.0))
        }
        return true
    }

    fun spawn(loc: Location) {
        val fallingBlock = loc.world?.spawnFallingBlock(loc, Material.ANVIL.createBlockData()) ?: return
        fallingBlock.dropItem = false
        fallingBlock.velocity = Vector(0, -1, 0)
        fallingBlock.setHurtEntities(false)

        submit(delay = 17) {
            if (fallingBlock.location.block.type == Material.ANVIL) {
                fallingBlock.location.block.type = Material.AIR
            }
            fallingBlock.remove()
        }
    }


}