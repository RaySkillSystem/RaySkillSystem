var script_type = "Skill";

function getCooldown(livingEntity, level) {
    return 20;
}

function onCondition(livingEntity, level) {
    return true;
}

function onPreRun(livingEntity, level) {
    Utils.toPlayer(livingEntity).sendMessage(
        "Run之前运行 返回false会结束下面的方法"
    );
    return true;
}

function onRun(livingEntity, level) {
    Utils.toPlayer(livingEntity).sendMessage("Hello~");
    return true;
}

function onOver(livingEntity, level) {
    return true;
}
