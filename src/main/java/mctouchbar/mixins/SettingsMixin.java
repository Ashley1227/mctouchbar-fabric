package mctouchbar.mixins;

import mctouchbar.gui.OptionsGui;
import mctouchbar.logging.Logger;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.server.network.packet.UpdateDifficultyC2SPacket;
import net.minecraft.text.Text;
import net.minecraft.world.Difficulty;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SettingsScreen.class)
public abstract class SettingsMixin extends Screen {

    protected SettingsMixin(Text text_1) {
        super(text_1);
    }
    @Inject(at = @At("TAIL"), method = "init")
    private void mixinInit(CallbackInfo cbi) {
        this.addButton(new ButtonWidget(this.width / 2 - 100, this.height / 6 + 142, 200, 20, I18n.translate("gui.mctouchbar.options.label", new Object[0]), (buttonWidget_1) -> {
            this.minecraft.openScreen(OptionsGui.screen);
        }));
    }
}
