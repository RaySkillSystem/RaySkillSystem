package top.maplex.rayskillsystem

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.module.configuration.Config
import taboolib.module.configuration.ConfigFile


object RaySkillSystem : Plugin() {

    @Config
    lateinit var config: ConfigFile

}
