package io.github.Ashley1227.mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.view.TouchBarTextField;
import io.github.Ashley1227.mctouchbar.widget.Widget;
import net.minecraft.client.MinecraftClient;

public class HeadingWidget extends Widget {
	private TouchBarTextField textField;

	public HeadingWidget() {

	}
	@Override
	public void tick() {
		if(this.textField != null)
			this.textField.setStringValue(getHeadingString());
	}

	@Override
	public void addToTouchbar(JTouchBar jTouchBar) {
		super.addToTouchbar(jTouchBar);
		this.textField = addStringToTouchbar(getHeadingString(), false);
		this.textField.setStringValue(" -000° ");
	}
	public String getHeadingString() {
		if(MinecraftClient.getInstance().player == null) {
			return " ";
		} else {
			return Math.round(MinecraftClient.getInstance().player.yaw % 360) + "° " + MinecraftClient.getInstance().player.getHorizontalFacing();
		}
	}
}
