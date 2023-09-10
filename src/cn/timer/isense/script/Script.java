package cn.timer.isense.script;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class Script {
    private final CopyOnWriteArrayList<Runnable> stacks = new CopyOnWriteArrayList<>();
    private final Map<String, CopyOnWriteArrayList<Runnable>> codeBlocks = new HashMap<>();
    private final Map<String, Object> objectsPool = new HashMap<>();

    public Script(String source) throws NoSuchFieldException, ClassNotFoundException, NoSuchMethodException {
        Compiler.compile(stacks, codeBlocks, source, objectsPool, true);
        stacks.forEach(Runnable::run);
    }

    public void runBlock(String name) {
        CopyOnWriteArrayList<Runnable> codeBlock = codeBlocks.get(name);
        if (codeBlock != null)
            codeBlock.forEach(Runnable::run);
    }
}
