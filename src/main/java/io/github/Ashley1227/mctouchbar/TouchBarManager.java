package io.github.Ashley1227.mctouchbar;

import io.github.Ashley1227.mctouchbar.bars.LanguageScreenBar;
import io.github.Ashley1227.mctouchbar.bars.ScreenTouchBar;
import io.github.Ashley1227.mctouchbar.bars.TickableTouchBar;
import io.github.Ashley1227.mctouchbar.bars.TitleScreenBar;
import io.github.Ashley1227.mctouchbar.widget.Widget;
import io.github.Ashley1227.mctouchbar.widget.widgets.DebugWidget;
import io.github.Ashley1227.mctouchbar.widget.widgets.HeadingWidget;
import io.github.Ashley1227.mctouchbar.widget.widgets.SuperDebugWidget;
import io.github.Ashley1227.mctouchbar.widget.widgets.TestWidget;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TouchBarManager {
	protected static long handle;
	private static Logger LOGGER = LogManager.getLogger(TouchBarManager.class);
	//private static JTouchBar jTouchBar_Game;
	public static TouchBarManager Manager = new TouchBarManager();
	private ScreenTouchBar currentScreenBar;

	public enum CurrentBar {
		None,
		TitleBar,
		LanguageBar,
		SelectWorldBar,
		InGameBar,
	}
	public CurrentBar currentBarType;

	private Widget[] widgets;

	private TouchBarManager() {
		//currentBar = CurrentBar.None;
	}

	public void init() {
		widgets = new Widget[] {
			new TestWidget(),
			new DebugWidget(),
			new SuperDebugWidget(),
			new HeadingWidget(),
//			new CommandWidget("Execute", "/say hi");
		};

		ClientTickCallback.EVENT.register(client -> {
			Manager.tick();
		});

	}

	public static long getHandle() {
		return handle;
	}

	public void tick() {
		this.regenTouchbar();
		if (this.currentScreenBar instanceof TickableTouchBar) {
			((TickableTouchBar)this.currentScreenBar).tick();
		}
	}
	
	public void regenTouchbar() {
		Screen currentScreen = MinecraftClient.getInstance().currentScreen;
		// should never occur
		if (currentScreen == null && MinecraftClient.getInstance().world == null) {
			LOGGER.warn("Where am I with screen and world = null???");
			return;
		}

		if (currentScreen instanceof TitleScreen && currentBarType != CurrentBar.TitleBar) {
			currentBarType = CurrentBar.TitleBar;
			currentScreenBar = new TitleScreenBar(currentScreen);
			currentScreenBar.show();
		} else if (currentScreen instanceof LanguageOptionsScreen && currentBarType != CurrentBar.LanguageBar) {
			currentBarType = CurrentBar.LanguageBar;
			currentScreenBar = new LanguageScreenBar(currentScreen);
			currentScreenBar.show();
		} else if (currentScreen instanceof SelectWorldScreen && currentBarType != CurrentBar.SelectWorldBar) {

		} else {
			// There should not be a TouchBar?
			// TODO: what to do about ticks after redrawing?
			//currentScreenBar.hide();
		}

		/*jTouchBar_Game = new JTouchBar();

//        addScrubber();

		/*for (Widget w : widgets) {
			w.addToTouchbar(jTouchBar);
		}*//*
		widgets[1].addToTouchbar(jTouchBar_Game);
		widgets[3].addToTouchbar(jTouchBar_Game);
		jTouchBar_Game.show(GLFWNativeCocoa.glfwGetCocoaWindow(handle));*/
		// FIXME: DETECT with net.minecraft.client.gui.screen
		/*System.out.print("Current Screen: ");
		System.out.println(MinecraftClient.getInstance().currentScreen.getClass().getName());
		if (MinecraftClient.getInstance().getServer() == null) {
			//System.out.println("no server");
			return;
		}
		if (MinecraftClient.getInstance().isInSingleplayer() ||MinecraftClient.getInstance().getServer().isRemote()) {
			this.showInGameBar();
		} else {
			//System.out.println("where am I?");
		}*/
	}

}
