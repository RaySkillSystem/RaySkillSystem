package top.maplex.rayskillsystem.skill.impl.bow.yuling

import ink.ptms.adyeshach.core.Adyeshach
import ink.ptms.adyeshach.core.entity.EntityTypes
import ink.ptms.adyeshach.core.entity.manager.ManagerType
import ink.ptms.adyeshach.core.entity.type.AdyPanda
import ink.ptms.adyeshach.core.entity.type.AdyPolarBear
import ink.ptms.adyeshach.impl.entity.trait.impl.setTraitTitle
import org.bukkit.entity.Player
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.module.chat.colored
import top.maplex.rayskillsystem.skill.AbstractSkill


object SkillBaiShuangYin : AbstractSkill {

    @Awake(LifeCycle.ENABLE)
    fun onEnable() {
        register()
    }

    override val name: String = "白霜吟"

    override val type: String = "弓箭手"

    override val cooldown: Long = 3 * 60 * 20

    override fun onCondition(player: Player, level: Int): Boolean {
        return true
    }

    override fun onRun(player: Player, level: Int): Boolean {

        val api = Adyeshach.api().getPublicEntityManager(ManagerType.TEMPORARY)
        val entity = api.create(EntityTypes.PANDA, player.location).apply {
            setCustomName("初始化")
            setCustomNameVisible(true)
            setNoGravity(true)
            if (this is AdyPanda) {
                setBaby(true)
            }
            moveSpeed = 0.5
            setTraitTitle(listOf("[${player.name}]", "&b御灵·噬铁兽").colored())
        }
        SkillYuLing.data.add(
            SummonedYuLingBear(
                player.uniqueId,
                entity,
                -1.0, 1.0, 1.0,
                System.currentTimeMillis() + (90000)
            ).apply {
                ridicule()
            }
        )

        return true
    }


}