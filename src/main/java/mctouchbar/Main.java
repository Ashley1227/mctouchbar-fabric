package mctouchbar;

import blue.endless.jankson.Jankson;
import blue.endless.jankson.JsonElement;
import blue.endless.jankson.JsonObject;
import io.github.cottonmc.jankson.JanksonFactory;
import mctouchbar.logging.Logger;
import mctouchbar.widget.Widgets;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.event.client.ClientTickCallback;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;


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

//		MinecraftClient.getInstance().openScreen(new ClientCottonScreen(new OptionsGui()));
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
