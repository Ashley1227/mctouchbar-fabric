package io.github.Ashley1227.mctouchbar;

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
import io.github.Ashley1227.mctouchbar.config.MCTouchbarConfig;
import io.github.Ashley1227.mctouchbar.widget.Widget;
import io.github.Ashley1227.mctouchbar.widget.config.WidgetConfig;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.fabricmc.loader.api.FabricLoader;
import org.lwjgl.glfw.GLFWNativeCocoa;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TouchBarManager {
	public static MCTouchbarConfig config = new MCTouchbarConfig();
	public static final File CFG_FILE = new File(FabricLoader.getInstance().getConfigDirectory(), "mctouchbar.json");

	public static long handle;
	public static JTouchBar jTouchBar;

	public static void init() {
		loadConfig();
		ClientTickCallback.EVENT.register(client -> {
			for (int i = 0; i < config.widgets.size(); i++) {
				Widget w = config.widgets.get(i);
				WidgetConfig c = config.config.get(i);

				w.tick(c, i);
			}
		});
	}
	public static void regenTouchbar() {
		jTouchBar = new JTouchBar();

//        addScrubber();

		for (int i = 0; i < config.widgets.size(); i++) {
			Widget w = config.widgets.get(i);
			WidgetConfig c = config.config.get(i);

			w.addToTouchbar(jTouchBar, i, c);
		}
		jTouchBar.show(GLFWNativeCocoa.glfwGetCocoaWindow(handle));
	}
	private static void addScrubber() {
		// scrubber
		TouchBarScrubber touchBarScrubber = new TouchBarScrubber();
		touchBarScrubber.setActionListener(new ScrubberActionListener() {
			@Override
			public void didSelectItemAtIndex(TouchBarScrubber scrubber, long index) {
				System.out.println("Selected Scrubber Index: " + index);
			}
		});
		touchBarScrubber.setDataSource(new ScrubberDataSource() {
			@Override
			public ScrubberView getViewForIndex(TouchBarScrubber scrubber, long index) {
				if (index == 0) {
					ScrubberTextItemView textItemView = new ScrubberTextItemView();
					textItemView.setIdentifier("ScrubberItem_1");
					textItemView.setStringValue("Scrubber TextItem");

					return textItemView;
				} else {
					ScrubberImageItemView imageItemView = new ScrubberImageItemView();
					imageItemView.setIdentifier("ScrubberItem_2");
					try {
						Image immsgs = new Image(new FileInputStream("/Users/computer/Desktop/Coding/MinecraftMods/MCTouchbar/src/main/resources/assets/mctouchbar/icon.png"));
						imageItemView.setImage(immsgs);
					} catch(IOException e) {
						e.printStackTrace();
					}

//                    MinecraftClient.getInstance().getItemRenderer().renderGuiItem(new ItemStack(Items.ACACIA_LOG, 1), 0, 0);
//					OptionParser optionParser = new OptionParser();
//					optionParser.allowsUnrecognizedOptions();
//					OptionSpec<File> optionSpec4 = optionParser.accepts("assetsDir").withRequiredArg().ofType(File.class);
//
//					File file2 = optionSet.has(optionSpec4) ? (File)getOption(optionSet, optionSpec4) : new File(file, "assets/");

//					try {
//					    Identifier item = Registry.ITEM.getId(Items.STONE);
//						LOGGER.info(item);
//						MinecraftClient.getInstance().getResourceManager().getResource(new Identifier("minecraft:textures/atlas/mob_effects.png"));
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//					textureManager.getTexture(new Identifier("minecraft:textures/atlas/blocks.png")).getSprite(new Identifier("minecraft:block/oak_door_top"));
					imageItemView.setAlignment(ImageAlignment.CENTER);

					return imageItemView;
				}
			}

			@Override
			public int getNumberOfItems(TouchBarScrubber scrubber) {
				return 2;
			}
		});

		jTouchBar.addItem(new TouchBarItem("Scrubber_1", touchBarScrubber, true));
	}
	public static void loadConfig() {
		config = config.readFromJSON(CFG_FILE);
		if (config == null) {
			config = new MCTouchbarConfig();
		}
		config.generateUntil(10);
	}

	public static void saveConfig() {
		config.writeToJSON(CFG_FILE);
	}
}
