package top.maplex.rayskillsystem.api.script;

import javax.script.CompiledScript;
import java.io.File;
import java.nio.file.Path;

public class ScriptTempData {

    private final File file;

    private final CompiledScript compiledScript;

    public ScriptTempData(File file, CompiledScript compiledScript) {
        this.file = file;
        this.compiledScript = compiledScript;
    }


    public CompiledScript getCompiledScript() {
        return compiledScript;
    }

    public File getFile() {
        return file;
    }
}
