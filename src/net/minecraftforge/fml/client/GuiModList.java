package net.minecraftforge.fml.client;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;

import java.io.IOException;
import java.util.List;

public class GuiModList extends GuiScreen {
    private final GuiScreen parentScreen;

    public GuiModList(GuiScreen parentScreen) {
        this.parentScreen = parentScreen;
    }

    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height - 38, 150, 20, "Done"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Mod List", this.width / 2, 16, 0xFFFFFF);

        List<ModContainer> mods = Loader.instance().getActiveModList();
        if (mods.isEmpty()) {
            this.drawCenteredString(this.fontRendererObj, "(no mods loaded)", this.width / 2, this.height / 2 - 4, 0xAAAAAA);
        } else {
            int y = 32;
            for (ModContainer mod : mods) {
                String line = mod.getName() + " " + mod.getVersion() + " (" + mod.getModId() + ")";
                this.drawCenteredString(this.fontRendererObj, line, this.width / 2, y, 0xFFFFFF);
                y += 12;
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
