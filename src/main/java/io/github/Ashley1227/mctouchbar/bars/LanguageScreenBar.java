package io.github.Ashley1227.mctouchbar.bars;

import com.thizzer.jtouchbar.common.Color;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.text.TranslatableText;

public class LanguageScreenBar extends ScreenTouchBar {
    private TouchBarButton btn_ok;
    private TouchBarButton btn_utf8;

    private LanguageOptionsScreen minecraftScreen;


    public LanguageScreenBar(Screen screen) {
        super(screen);
        this.minecraftScreen = (LanguageOptionsScreen) screen;

        btn_ok = new TouchBarButton();
        btn_ok.setTitle(ScreenTexts.DONE.asString());
        btn_ok.setAction(e -> {
            LOGGER.warn("done for language not implemented yet");
            //((LanguageOptionsScreen)this.minecraftScreen).
        });
        btn_ok.setBezelColor(Color.CONTROL_COLOR);
        this.touchBar.addItem(new TouchBarItem("btn_ok", btn_ok));

        btn_utf8 = new TouchBarButton();
        btn_utf8.setTitle(new TranslatableText("options.forceUnicodeFont").asString()); // TODO: add current state
        btn_utf8.setBezelColor(Color.DISABLED_CONTROL_TEXT);
        btn_utf8.setAction(e -> {
            LOGGER.warn("unicode force not implemented");
            //this.minecraftScreen.
            //this.minecraftScreen.
        });
        this.touchBar.addItem(new TouchBarItem("btn_utf8", btn_utf8));
    }

}
