package net.minecraftforge.client.event;

import net.minecraftforge.fml.common.eventhandler.Event;

public class RenderGameOverlayEvent extends Event {
    public enum ElementType {
        ALL, HOTBAR, HEALTH, ARMOR, FOOD, HEALTHMOUNT, AIR, PORTAL, CROSSHAIRS,
        BOSSHEALTH, CHAT, PLAYER_LIST, DEBUG, TEXT, HELMET, EXPERIENCE, JUMPBAR,
        VIGNETTE, POTION_ICONS, SUBTITLES
    }

    public final float partialTicks;
    public final ElementType type;

    public RenderGameOverlayEvent(float partialTicks, ElementType type) {
        this.partialTicks = partialTicks;
        this.type = type;
    }

    public static class Pre extends RenderGameOverlayEvent {
        public Pre(float partialTicks, ElementType type) {
            super(partialTicks, type);
        }

        @Override
        public boolean isCancelable() {
            return true;
        }
    }

    public static class Post extends RenderGameOverlayEvent {
        public Post(float partialTicks, ElementType type) {
            super(partialTicks, type);
        }
    }
}
