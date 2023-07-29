package top.maplex.rayskillsystem.api.script.reader

import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.library.xseries.parseToXMaterial
import top.maplex.rayskillsystem.RaySkillSystem
import top.maplex.rayskillsystem.api.script.ScriptManager
import top.maplex.rayskillsystem.api.script.data.ScriptBuffImpl
import top.maplex.rayskillsystem.api.script.data.ScriptSkillImpl
import top.maplex.rayskillsystem.api.script.data.ScriptTeamImpl
import top.maplex.rayskillsystem.utils.toConsole


object ScriptReaderManager {

    val scriptManager by lazy {
        ScriptManager(RaySkillSystem.plugin, "script/default.js")
    }

    val loader = ArrayList<String>()

    @Awake(LifeCycle.ACTIVE)
    fun readAll() {
        loader.clear()
        toConsole("开始预编译JavaScript脚本", true)
        scriptManager.compiledScripts.forEach { (t, u) ->
            if (u.file.path.toString().contains("SingleLoad")) {
                if (!loader.contains(u.file.path.toString())) {
                    scriptManager.callLoader(t)
                }
                return@forEach
            }
            scriptManager.getVariable(t, "script_type")?.let {
                when (it as String) {
                    "skill", "Skill", "SKILL" -> {
                        readSkill(t)
                    }

                    "buff", "Buff", "BUFF" -> {
                        readBuff(t)
                    }

                    "team", "Team", "TEAM" -> {
                        readTeam(t)
                    }
                }
            }
        }
    }

    fun readTeam(name: String) {
        ScriptTeamImpl(name)
    }

    fun readSkill(name: String) {
        val type = runCatching {
            scriptManager.getVariable(name, "type")
        }.getOrNull() as? String ?: "default"
        ScriptSkillImpl(name, type)
    }

    fun readBuff(name: String) {
        val id = runCatching {
            scriptManager.getVariable(name, "id")
        }.getOrNull() as? String ?: return
        val info = runCatching {
            scriptManager.getVariable(name, "info")
        }.getOrNull() as? String ?: ""

        val icon = runCatching {
            scriptManager.getVariable(name, "icon")
        }.getOrNull() as? String ?: "APPLE"

        ScriptBuffImpl(name, id, info, icon.parseToXMaterial().parseMaterial()!!)
    }

}
