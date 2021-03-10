package io.github.Ashley1227.mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.common.Color;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import io.github.Ashley1227.mctouchbar.widget.Widget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

import java.util.Random;

public class TestWidget extends Widget {

	private TouchBarButton button;
	private Color[] colors = {Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.BLUE,Color.PURPLE};
	private int i;

	public TestWidget() {
		super();

	}

	@Override
	public void tick() {
		super.tick();
		if(this.button != null) {
			this.button.setBezelColor(this.colors[++i % colors.length]);
		}
	}

	@Override
	public void addToTouchbar(JTouchBar jTouchBar) {
		super.addToTouchbar(jTouchBar);
		Screen screen = MinecraftClient.getInstance().currentScreen;
		button = addButtonToTouchbar(new TranslatableText("widget.mctouchbar.test"), e -> {
			Util.getOperatingSystem().open("https://youtu.be/dQw4w9WgXcQ");
		});
		Random random = new Random();
		i = random.nextInt(colors.length);
	}
}
