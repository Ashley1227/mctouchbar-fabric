package io.github.Ashley1227.mctouchbar;

import com.mojang.blaze3d.systems.RenderSystem;
import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.common.Image;
import com.thizzer.jtouchbar.common.ImageAlignment;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarScrubber;
import com.thizzer.jtouchbar.scrubber.ScrubberActionListener;
import com.thizzer.jtouchbar.scrubber.ScrubberDataSource;
import com.thizzer.jtouchbar.scrubber.view.ScrubberImageItemView;
import com.thizzer.jtouchbar.scrubber.view.ScrubberTextItemView;
import com.thizzer.jtouchbar.scrubber.view.ScrubberView;
import io.github.Ashley1227.mctouchbar.util.FramebufferUtils;
import io.github.Ashley1227.mctouchbar.widget.Widget;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFWNativeCocoa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MCTouchbar extends DrawableHelper implements ClientModInitializer {

	public static final String MODID = "mctouchbar";

	public static final Logger LOGGER = LogManager.getLogger(MCTouchbar.class);

	public static boolean isMac = System.getProperty("os.name").toLowerCase().startsWith("mac");

	@Deprecated
	public static int i = 0;

	@Override
	public void onInitializeClient() {
		if (isMac) {
			TouchBarManager.init();

			HudRenderCallback.EVENT.register((e, a) -> {
				TextRenderer renderer = MinecraftClient.getInstance().textRenderer;
				//renderer.draw(Integer.toString(i), 0, 0, 0xffffff); // TODO: reenable
				i++;
			});
			LOGGER.debug("MCTouchbar initialized");
		} else {
			LOGGER.info("Client is not running a Mac, skipping MCTouchbar initialization.");
		}


	}

	public static void onWindowLoad(long handleOwO) {
		TouchBarManager.handle = handleOwO;
	}
}
