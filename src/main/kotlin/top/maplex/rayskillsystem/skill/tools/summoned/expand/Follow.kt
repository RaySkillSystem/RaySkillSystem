package top.maplex.rayskillsystem.skill.tools.summoned.expand

import org.bukkit.Bukkit
import org.bukkit.Location
import top.maplex.rayskillsystem.skill.tools.summoned.SummonedEntity

/**
 * 跟随框架
 */
interface Follow {

    //偏移位置
    var deviationX: Double
    var deviationZ: Double
    var deviationY: Double

    //是否可以移动
    var follow: Boolean
    //距离原点多少距离进行强制tp
    var distance: Int

    fun followEval(data: SummonedEntity, source: Location) {
        if (!Bukkit.getOfflinePlayer(data.master).isOnline) {
            data.delete()
            return
        }
        val location = data.getLocation() ?: return
        val pos = source.clone()
            .add(source.clone().direction.normalize().setY(0).multiply(deviationZ))
            .add(source.clone().apply { yaw += 90 }.direction.normalize().setY(0).multiply(deviationX))
            .add(0.0, deviationY, 0.0)
        if (data.getLocation()!!.world != pos.world) {
            data.teleport(pos)
            return
        }
        if (follow) {
            if (source.distance(location) >= distance) {
                data.teleport(pos)
                return
            }
            data.move(pos)
        }
    }

}