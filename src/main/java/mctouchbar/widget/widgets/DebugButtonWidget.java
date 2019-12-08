package mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import com.thizzer.jtouchbar.item.view.TouchBarTextField;
import com.thizzer.jtouchbar.item.view.TouchBarView;
import com.thizzer.jtouchbar.item.view.action.TouchBarViewAction;
import mctouchbar.widget.Widget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;

public class DebugButtonWidget extends Widget {
//    TouchBarTextField touchBarTextField = new TouchBarTextField();
    TouchBarButton touchBarButton = new TouchBarButton();

//    public String ID = "widget.mctouchbar.fps";

    public DebugButtonWidget() {
        this.setID("widget.mctouchbar.debug");
        touchBarButton.setTitle(I18n.translate("widget.mctouchbar.debug"));
        touchBarButton.setAction(new TouchBarViewAction() {
            @Override
            public void onCall(TouchBarView view) {
//                MinecraftClient.getInstance().debugRenderer.
                MinecraftClient.getInstance().options.debugEnabled = !MinecraftClient.getInstance().options.debugEnabled;
            }
        });

        this.touchBarItem = new TouchBarItem(ID, touchBarButton, true);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
