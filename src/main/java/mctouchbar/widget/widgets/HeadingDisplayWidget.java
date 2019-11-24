package mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarTextField;
import mctouchbar.widget.Widget;
import net.minecraft.client.MinecraftClient;

public class HeadingDisplayWidget extends Widget {
    TouchBarTextField touchBarTextField = new TouchBarTextField();

//    public String ID = "widget.mctouchbar.fps";

    public HeadingDisplayWidget() {
        this.setID("widget.mctouchbar.heading");

        this.touchBarItem = new TouchBarItem(ID, touchBarTextField, true);
    }

    @Override
    public void tick() {
        super.tick();
        if(MinecraftClient.getInstance().cameraEntity != null) {
            touchBarTextField.setStringValue(Math.round(MinecraftClient.getInstance().cameraEntity.yaw) % 360 + "°");
        }
    }
}
