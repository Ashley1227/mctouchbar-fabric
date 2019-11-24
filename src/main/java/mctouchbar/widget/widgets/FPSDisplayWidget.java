package mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarTextField;
import mctouchbar.widget.Widget;
import net.minecraft.client.MinecraftClient;

public class FPSDisplayWidget extends Widget {
    TouchBarTextField touchBarTextField = new TouchBarTextField();

//    public String ID = "widget.mctouchbar.fps";

    public FPSDisplayWidget() {
        this.setID("widget.mctouchbar.fps");

        this.touchBarItem = new TouchBarItem(ID, touchBarTextField, true);
    }

    @Override
    public void tick() {
        super.tick();
        touchBarTextField.setStringValue(Integer.toString(MinecraftClient.getCurrentFps()) + " FPS");
    }
}
