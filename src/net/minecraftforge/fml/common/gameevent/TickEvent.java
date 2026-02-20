package net.minecraftforge.fml.common.gameevent;

import net.minecraftforge.fml.common.eventhandler.Event;

public class TickEvent extends Event {
    public enum Phase { START, END }
    public enum Type { CLIENT, SERVER, RENDER, WORLD, PLAYER }

    public final Phase phase;
    public final Type type;

    public TickEvent(Type type, Phase phase) {
        this.type = type;
        this.phase = phase;
    }

    public static class ClientTickEvent extends TickEvent {
        public ClientTickEvent(Phase phase) {
            super(Type.CLIENT, phase);
        }
    }

    public static class PlayerTickEvent extends TickEvent {
        public PlayerTickEvent(Phase phase) {
            super(Type.PLAYER, phase);
        }
    }
}
