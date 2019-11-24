package mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import com.thizzer.jtouchbar.item.view.TouchBarSlider;
import com.thizzer.jtouchbar.item.view.TouchBarView;
import com.thizzer.jtouchbar.item.view.action.TouchBarViewAction;
import com.thizzer.jtouchbar.slider.SliderActionListener;
import mctouchbar.widget.Widget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class FOVSliderWidget extends Widget {

    TouchBarSlider touchBarSlider = new TouchBarSlider();

//    public String ID = "widget.mctouchbar.fps";

    public FOVSliderWidget() {
        this.setID("widget.mctouchbar.fov_slider");
        touchBarSlider.setMinValue(0.5);
        touchBarSlider.setMaxValue(180);
        touchBarSlider.setActionListener((slider, value) -> {
            MinecraftClient.getInstance().options.fov = value;
        });
        this.touchBarItem = new TouchBarItem(ID, touchBarSlider, true);
    }

    @Override
    public void tick() {
        super.tick();
    }
}
