package top.maplex.rayskillsystem

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.getDataFolder
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import taboolib.platform.BukkitPlugin


object RaySkillSystem : Plugin() {

    @Config
    lateinit var config: ConfigFile

    val plugin by lazy {
        Bukkit.getPluginManager().getPlugin("RaySkillSystem") as JavaPlugin
    }

}
