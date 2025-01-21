package com.jeroenj.hud;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Unique;

@Environment(EnvType.CLIENT)
public class SkillScreen extends Screen {

    @Unique
    private final ScreenTabsWidget screenTabsWidget = new ScreenTabsWidget(100, 100);

    protected SkillScreen() {
        super(Text.literal("Skills"));
    }

    @Override
    protected void init() {
        super.init();

    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

    }
}
