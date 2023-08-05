package pisi.unitedmeows.meowlib.etc;

public enum MLibSettings {
    ASYNC_WAIT_DELAY("ASYNC_WAIT_NEXT", Long.class, 200L),
    ASYNC_POLL_WAIT_DELAY("ASYNC_POLL_WAIT_DELAY", Long.class, 1L),
    ASYNC_WORKER_BUSY("ASYNC_WORKER_BUSY", Long.class, 500L),
    ASYNC_AWAIT_CHECK_DELAY("ASYNC_AWAIT_CHECK_DELAY", Long.class, 50L),
    ASYNC_NWORKING_TIME("ASYNC_NWORKING_TIME", Long.class, 8000L),
    ASYNC_CHECK_BUSY("ASYNC_CHECK_BUSY", Long.class, 20L);

    private String name;
    private Class<?> type;
    private Object value;
    MLibSettings(String name, Class<?> type, Object value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Class<?> getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }
}
