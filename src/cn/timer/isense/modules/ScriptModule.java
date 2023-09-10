package cn.timer.isense.modules;

import cn.timer.isense.script.Script;
import lombok.Getter;

public class ScriptModule extends AbstractModule {
    @Getter
    private final Script script;

    public ScriptModule(String name, String scriptSource) {
        super(name);
        try {
            this.script = new Script(scriptSource);
        } catch (NoSuchFieldException | NoSuchMethodException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        init();
    }

    public void init() {
        this.script.runBlock("init");
    }
}
