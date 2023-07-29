package top.maplex.rayskillsystem

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import taboolib.common.LifeCycle
import taboolib.common.io.newFolder
import taboolib.common.platform.Awake
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.getDataFolder
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile
import taboolib.platform.BukkitPlugin
import top.maplex.rayskillsystem.api.script.ScriptEventListener
import top.maplex.rayskillsystem.api.script.reader.ScriptReaderManager


object RaySkillSystem : Plugin() {

    @Config
    lateinit var config: ConfigFile

    val plugin by lazy {
        Bukkit.getPluginManager().getPlugin("RaySkillSystem") as JavaPlugin
    }

    @Awake(LifeCycle.ENABLE)
    fun init() {
        newFolder(getDataFolder(), "/script/SingleLoad/",create = true)
    }

    fun reload() {
        ScriptEventListener.unRegisterAll()
        ScriptReaderManager.scriptManager.reload()
        ScriptReaderManager.readAll()
    }

}
