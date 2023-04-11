package top.maplex.rayskillsystem.caster.bow

import org.bukkit.event.player.PlayerInteractEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.platform.util.isLeftClick
import taboolib.platform.util.isRightClick
import top.maplex.panlingitem.api.PanLingAPI
import top.maplex.panlingitem.api.PanLingStatic
import top.maplex.rayskillsystem.skill.SkillManager
import top.maplex.rayskillsystem.skill.impl.bow.yuling.SkillBaiChuanGuiHai
import top.maplex.rayskillsystem.skill.impl.bow.yuling.SkillBaiShuangYin
import top.maplex.rayskillsystem.skill.impl.bow.yuling.SkillKunShanXuanFeng
import top.maplex.rayskillsystem.skill.impl.bow.yuling.SkillZhengDu
import top.maplex.rayskillsystem.utils.error
import top.maplex.rayskillsystem.utils.getString
import top.maplex.rayskillsystem.utils.ifAir

object BowCaster {


    @SubscribeEvent
    fun onRun(event: PlayerInteractEvent) {
        val player = event.player
        val item = event.item.ifAir() ?: return

        if (item.getString("bow.type") != "秋江白鹭") {
            return
        }
        if (PanLingAPI.getPlayerData(event.player, PanLingStatic.JOB) != "1" && !player.isOp) {
            event.player.error("你的职业不允许使用秋江白鹭")
            return
        }
        event.isCancelled = true

        if (player.isSneaking) {
            if (event.isLeftClick()) {
                SkillManager.eval(player, SkillBaiChuanGuiHai.name, 1)
                return
            }
            if (event.isRightClick()) {
                SkillManager.eval(player, SkillKunShanXuanFeng.name, 1)
                return
            }
        } else {
            if (event.isLeftClick()) {
                SkillManager.eval(player, SkillZhengDu.name, 1)
                return
            }
            if (event.isRightClick()) {
                SkillManager.eval(player, SkillBaiShuangYin.name, 1)
                return
            }
        }
    }

}