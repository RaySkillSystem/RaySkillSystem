package top.maplex.rayskillsystem.api.script.reader

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import top.maplex.rayskillsystem.RaySkillSystem
import top.maplex.rayskillsystem.api.script.ScriptManager
import top.maplex.rayskillsystem.api.script.ScriptSkillImpl
import top.maplex.rayskillsystem.utils.toConsole


object ScriptReaderManager {

    val scriptManager by lazy {
        ScriptManager(RaySkillSystem.plugin, "script/default.js")
    }

    fun reload() {
        scriptManager.reload()
    }

    @Awake(LifeCycle.ACTIVE)
    fun readAll() {
        toConsole("开始预编译JavaScript脚本",true)
        scriptManager.compiledScripts.forEach { t, u ->
            scriptManager.getVariable(t, "script_type")?.let {
                when (it as String) {
                    "skill","Skill","SKILL" -> {
                        readSkill(t)
                    }
                }
            }
        }
    }

    fun readSkill(name: String) {
        val type = runCatching {
            scriptManager.getVariable(name, "type")
        }.getOrNull() as? String ?: "default"
        ScriptSkillImpl(name, type)
    }


}
