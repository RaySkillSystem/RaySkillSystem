package top.maplex.rayskillsystem.api.script;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import taboolib.library.configuration.ConfigurationSection;
import top.maplex.rayskillsystem.RaySkillSystem;

import javax.script.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ScriptManager {

    private final JavaPlugin plugin;

    private final String[] defaultFile;

    private final ConcurrentHashMap<String, CompiledScript> compiledScripts = new ConcurrentHashMap<>();

    private Compilable compilableEngine;

    private Invocable invocable;

    public ScriptManager(JavaPlugin plugin, String... defaultFile) {
        this.plugin = plugin;
        this.defaultFile = defaultFile;
        reload();
    }

    /**
     * 重新加载
     */
    public void reload() {
        try {
            initEngine();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        File scriptFiles = new File(plugin.getDataFolder(), "script");
        if (!scriptFiles.exists() || Objects.requireNonNull(scriptFiles.listFiles()).length == 0) {
            Arrays.stream(defaultFile).forEach(fileName -> plugin.saveResource(fileName, true));
        }
        try {
            compiledScripts.clear();
            loadScriptFile(scriptFiles);
        } catch (IOException | ScriptException e) {
            plugin.getLogger().warning("Load scripts error, Please see reported as follows");
            e.printStackTrace();
            compiledScripts.clear();
            return;
        }
        plugin.getLogger().info("Loaded " + compiledScripts.size() + " Scripts");
    }

    /**
     * 初始化引擎
     */
    private void initEngine() throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, IllegalAccessException {
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("js");
        Class<?> clazz = Class.forName(System.getProperty("java.class.version").startsWith("52") ?
                "jdk.internal.dynalink.beans.StaticClass" :
                "jdk.dynalink.beans.StaticClass");
        Method method = clazz.getMethod("forClass", Class.class);
        engine.put("Bukkit", method.invoke(null, Bukkit.class));
        engine.put("Arrays", method.invoke(null, Arrays.class));
        engine.put("Utils", method.invoke(null, Utils.class));
        compilableEngine = (Compilable) engine;
        invocable = (Invocable) engine;
        compiledScripts.clear();
        StringBuilder stringBuilder = new StringBuilder();
        ConfigurationSection scriptLib = RaySkillSystem.config.getConfigurationSection("ScriptLib");
        if (scriptLib == null) return;
        for (String key : scriptLib.getKeys(false)) {
            stringBuilder.append("var ").append(key).append(" = ").append(scriptLib.getString(key)).append("\n");
        }
        try {
            compilableEngine.compile(stringBuilder.toString());
        } catch (ScriptException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加载数据-遍历文件读取script
     *
     * @param files File
     */
    public void loadScriptFile(File files) throws IOException, ScriptException {
        for (File file : files.listFiles()) {
            if (file.getName().startsWith("NoLoad")) continue;
            if (file.isDirectory()) {
                loadScriptFile(file);
            } else if (file.getName().endsWith(".js")) {
                InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
                CompiledScript compiled = compilableEngine.compile(inputStreamReader);
                compiledScripts.put(file.getName().replace(".js", ""), compiled);
                inputStreamReader.close();
            }
        }
    }

    /**
     * 加载一个文件
     */
    public void loadScriptFileOne(File file) throws IOException, ScriptException {
        if (file.getName().startsWith("NoLoad")) {
            return;
        }
        if (!file.isDirectory() && file.getName().endsWith(".js")) {
            InputStreamReader inputStreamReader = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);
            CompiledScript compiled = compilableEngine.compile(inputStreamReader);
            compiledScripts.put(file.getName().replace(".js", ""), compiled);
            inputStreamReader.close();
        }
    }

    /**
     * 执行script
     *
     * @param scriptName   脚本名
     * @param functionName 方法名
     * @param args         参数
     * @return script回参
     */
    public Object callFunction(String scriptName, String functionName, Object... args) throws Exception {
        CompiledScript compiled = compiledScripts.get(scriptName);
        if (compiled == null) {
            throw new Exception("Script not found: " + scriptName);
        }
        compiled.eval();
        return invocable.invokeFunction(functionName, args);
    }

    public Object getVariable(String scriptName, String variableName) throws Exception {
        CompiledScript compiled = compiledScripts.get(scriptName);
        if (compiled == null) {
            throw new Exception("Script not found: " + scriptName);
        }
        compiled.eval();
        Bindings bindings = compiled.getEngine().getBindings(javax.script.ScriptContext.ENGINE_SCOPE);
        return bindings.get(variableName);
    }

    public ConcurrentHashMap<String, CompiledScript> getCompiledScripts() {
        return compiledScripts;
    }

    /**
     * script工具类
     */
    public static class Utils {

        @SafeVarargs
        public static <T> List<T> mutableList(T... args) {
            return Arrays.stream(args).collect(Collectors.toList());
        }

        public static String fromPlaceholderAPI(Player player, String str) {
            return "";
        }

        public static String asColor(String string) {
            return ChatColor.translateAlternateColorCodes('&', string);
        }

        public static Player toPlayer(LivingEntity livingEntity) {
            return (Player) livingEntity;
        }

    }
}
