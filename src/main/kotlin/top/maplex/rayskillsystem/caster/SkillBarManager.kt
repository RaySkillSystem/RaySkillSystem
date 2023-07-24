package top.maplex.rayskillsystem.caster

import github.saukiya.sxitem.event.SXItemSpawnEvent
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import taboolib.common.platform.Schedule
import taboolib.common.platform.event.SubscribeEvent
import top.maplex.rayskillsystem.RaySkillSystem
import top.maplex.rayskillsystem.skill.SkillManager
import top.maplex.rayskillsystem.utils.cooldown.CooldownAPI
import top.maplex.rayskillsystem.utils.getInt
import top.maplex.rayskillsystem.utils.getString
import top.maplex.rayskillsystem.utils.ifAir
import top.maplex.rayskillsystem.utils.set
import java.text.SimpleDateFormat
import java.util.*


object SkillBarManager {

    @Schedule(period = 5)
    fun schedule() {
        Bukkit.getOnlinePlayers().forEach {
            update(it)
        }
    }

    fun enable(): Boolean {
        return RaySkillSystem.config.getBoolean("SkillBar.enable", true)
    }

    fun update(player: Player) {
        (0..9).forEach {
            update(player, it)
        }
    }

    fun update(player: Player, slot: Int) {
        val item = player.inventory.getItem(slot) ?: return
        val skillName = item.getString("RaySkill.type")
        item.apply {
            set("RaySkill.type", skillName)
            val cooldown = CooldownAPI.getTimeLong(player, "Skill_$skillName")
            val am = when {
                cooldown <= 0 -> 1
                cooldown <= 2000L -> 2
                else -> (cooldown / 1000).toInt()
            }
            amount = am.coerceIn(1, 128)
        }
    }

    @SubscribeEvent
    fun onRight(event: PlayerInteractEvent) {
        val item = event.item.ifAir() ?: return
        val skillName = item.getString("RaySkill.type")
        if (skillName == "null") {
            return
        }
        val skillLevel = item.getInt("RaySkill.level", 1)
        event.isCancelled = true
        SkillManager.eval(event.player, skillName, skillLevel)
    }

    @SubscribeEvent
    fun lin(event: PlayerItemHeldEvent) {
        val player = event.player
        val new = event.newSlot
        val item = player.inventory.getItem(new) ?: return
        val skillName = item.getString("RaySkill.type")
        if (skillName == "null") {
            return
        }
        if (!enable()) {
            return
        }
        val skillLevel = item.getInt("RaySkill.level", 1)
        event.isCancelled = true
        SkillManager.eval(player, skillName, skillLevel)
    }

    @SubscribeEvent
    fun item(event: InventoryClickEvent) {
        val item = event.currentItem ?: return
        val skillName = item.getString("RaySkill.type")
        if (skillName == "null") {
            return
        }
        item.amount = 1
    }

    @SubscribeEvent
    fun itemSpawn(event: SXItemSpawnEvent) {
        val id = event.ig.key
        if (id.contains("技能")) {
            val now = System.currentTimeMillis()
            val f = SimpleDateFormat("yyyy年MMM:dd日HH:mm:ss:SSS")
            event.item.set("RaySkillInfo.getter", event.player.name)
            event.item.set("RaySkillInfo.time", f.format(now))
            event.item.set("RaySkillInfo.uuid", UUID.randomUUID().toString())
        }
    }


}
