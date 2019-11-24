package mctouchbar;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import com.thizzer.jtouchbar.item.view.TouchBarView;
import com.thizzer.jtouchbar.item.view.action.TouchBarViewAction;
import io.github.cottonmc.cotton.gui.client.ClientCottonScreen;
import io.github.cottonmc.jankson.JanksonFactory;
import mctouchbar.gui.OptionsGui;
import mctouchbar.logging.Logger;
import mctouchbar.widget.Widget;
import mctouchbar.widget.Widgets;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.event.client.ClientTickCallback;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFWNativeCocoa;


import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

public class Main implements ClientModInitializer {
	public static String MODID = "mctouchbar";
	public static volatile MCTouchbarConfig config;

	public static final Jankson jankson = JanksonFactory.createJankson();

	public static boolean started = false;

	@Override
	public void onInitializeClient() {
		try {
			loadConfig();
			ClientTickCallback.EVENT.register(e ->
			{
				if (!started) {
					onWindowLoad();
				}
				started = true;
				Widgets.tick();
			});
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void onWindowLoad() {
		Widgets.init();
//		long window = MinecraftClient.getInstance().window.getHandle();
//		JTouchBar jTouchBar = new JTouchBar();
//		jTouchBar.setCustomizationIdentifier("Minecraft");
//
//		TouchBarButton optionsBtn = new TouchBarButton();
//		optionsBtn.setTitle("Touchbar Options");
//
//		optionsBtn.setAction(new TouchBarViewAction() {
//			@Override
//			public void onCall( TouchBarView view ) {
////				MinecraftClient.getInstance().openScreen(new ClientCottonScreen(new OptionsGui()));
//				Logger.info("Testing epic gamer");
//			}
//		});
//		jTouchBar.addItem(new TouchBarItem("optionsBtn", optionsBtn, true));
//		jTouchBar.show(
//				GLFWNativeCocoa.glfwGetCocoaWindow(window)
//		);
	}
	public static MCTouchbarConfig loadConfig() {
		try {
			File file = new File(FabricLoader.getInstance().getConfigDirectory(),"mctouchbar.json5");

			if (!file.exists()) saveConfig(new MCTouchbarConfig());

			JsonObject json = jankson.load(file);
			config =  jankson.fromJson(json, MCTouchbarConfig.class);

		} catch (Exception e) {
			Logger.error("Error loading config: " + e.getMessage());
		}
		return config;
	}

	public static void saveConfig(MCTouchbarConfig config) {
		try {
			File file = new File(FabricLoader.getInstance().getConfigDirectory(),"mctouchbar.json5");

			JsonElement json = jankson.toJson(config);
			String result = json.toJson(true, true);
			try (FileOutputStream out = new FileOutputStream(file, false)) {
				out.write(result.getBytes(StandardCharsets.UTF_8));
			}
		} catch (Exception e) {
			Logger.error("Error saving config: " + e.getMessage());
		}

	}
	
}
