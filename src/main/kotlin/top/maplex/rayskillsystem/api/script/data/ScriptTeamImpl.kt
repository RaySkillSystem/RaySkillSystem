package top.maplex.rayskillsystem.api.script.data

import org.bukkit.entity.LivingEntity
import top.maplex.rayskillsystem.api.script.reader.ScriptReaderManager
import top.maplex.rayskillsystem.skill.tools.team.AbstractTeam

data class ScriptTeamImpl(
    override val name: String
) : AbstractTeam {

    init {
        register()
    }

    override fun canAttack(damager: LivingEntity, entity: LivingEntity): Boolean {
        damager.getType()
        return runCatching {
            ScriptReaderManager.scriptManager.callFunction(name, "canAttack", damager, entity)
        }.getOrNull() as? Boolean ?: true
    }

}
