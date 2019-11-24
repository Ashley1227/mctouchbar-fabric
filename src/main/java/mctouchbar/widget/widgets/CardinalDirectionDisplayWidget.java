package mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarTextField;
import mctouchbar.widget.Widget;
import net.minecraft.client.MinecraftClient;

public class CardinalDirectionDisplayWidget extends Widget {

    TouchBarTextField touchBarTextField = new TouchBarTextField();

    public CardinalDirectionDisplayWidget() {
        this.setID("widget.mctouchbar.facing");

        this.touchBarItem = new TouchBarItem(ID, touchBarTextField, true);
    }

    @Override
    public void tick() {
        super.tick();
        if(MinecraftClient.getInstance().player != null) {
            touchBarTextField.setStringValue(MinecraftClient.getInstance().player.getMovementDirection().toString().toUpperCase());
        }
    }
}
