package io.github.Ashley1227.mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.JTouchBar;
import io.github.Ashley1227.mctouchbar.widget.Widget;
import net.minecraft.client.MinecraftClient;

public class CommandWidget extends Widget {

	private String title;
	private String command;

	public CommandWidget(String title, String command) {
		super();
		this.title = title;
		this.command = command;
	}

	@Override
	public void addToTouchbar(JTouchBar jTouchBar) {
		super.addToTouchbar(jTouchBar);

		addButtonToTouchbar(title, false, e -> {
			MinecraftClient.getInstance().player.sendChatMessage(command);
		});

	}
}
