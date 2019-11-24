package mctouchbar.widget;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import com.thizzer.jtouchbar.item.view.TouchBarView;
import com.thizzer.jtouchbar.item.view.action.TouchBarViewAction;
import io.github.cottonmc.cotton.gui.client.ClientCottonScreen;
import mctouchbar.Main;
import mctouchbar.gui.OptionsGui;
import mctouchbar.logging.Logger;
import mctouchbar.widget.widgets.*;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFWNativeCocoa;

import java.util.HashMap;

public class Widgets {
    public static HashMap<String, Widget> widgetStorage = new HashMap<>();

    public static long window;

    public static JTouchBar jTouchBar;

    public static Widget debugToggle = new DebugButtonWidget();

    public static Widget fps = new FPSDisplayWidget();

    public static Widget cardinalDirectionDisplay = new CardinalDirectionDisplayWidget();
    public static Widget headingDisplay = new HeadingDisplayWidget();

    public static Widget fovSlider = new FOVSliderWidget();
    public static Widget fovDisplay = new FOVDisplayWidget();

    public static void init() {
        try {
            register(debugToggle);

            register(fps);

            register(cardinalDirectionDisplay);
            register(headingDisplay);

            register(fovSlider);
            register(fovDisplay);

            Main.config.enabledWidgets = loadWidgets(Main.config.enabledWidgets);
            Logger.info("MCTouchbar widgets initialized successfully!");
        } catch (Exception e) {
            Logger.error("Error initializing MCTouchbar widgets :C");
            e.printStackTrace();
        }
//        jTouchBar = new JTouchBar();
        try {
            window = MinecraftClient.getInstance().window.getHandle();
            update();
        } catch (Exception e) {
            e.printStackTrace();
            Logger.error("Error initializing  JTouchbar");
        }
    }

    public static void register(Widget w) {
        widgetStorage.put(w.ID, w);
        Logger.info("Registered widget " + w.ID);
    }

    public static void update() {
//        jTouchBar.hide(window);
        jTouchBar = new JTouchBar();
        jTouchBar.setCustomizationIdentifier("Minecraft");

        for (Widget w : Main.config.enabledWidgets) {
            if (w != null) {
                jTouchBar.addItem(w.touchBarItem);
            }
        }

        TouchBarButton optionsBtn = new TouchBarButton();
        optionsBtn.setTitle("Touchbar Options");

        optionsBtn.setAction(new TouchBarViewAction() {
            @Override
            public void onCall(TouchBarView view) {
                MinecraftClient.getInstance().openScreen(new ClientCottonScreen(new OptionsGui()));
                Logger.info("Testing epic gamer");
            }
        });
        jTouchBar.addItem(new TouchBarItem("optionsBtn", optionsBtn, true));
        jTouchBar.show(
                GLFWNativeCocoa.glfwGetCocoaWindow(window)
        );
    }

    public static boolean usingWidget(String s) {
        for (Widget w : Main.config.enabledWidgets) {
            if (w.ID == s) {
                return true;
            }
        }
        return false;
    }

    public static void tick() {
        for (Widget w : Main.config.enabledWidgets) {
            if (w != null) {
                w.tick();
            }
        }
    }

    public static Widget[] loadWidgets(Widget[] list) {
        Widget[] ret = new Widget[9];
        int i = 0;
        for (Widget w : list) {
            if (w == null) {
                i++;
                continue;
            }
            if (widgetStorage.containsKey(w.ID)) {
                ret[i] = widgetStorage.get(w.ID);

                ret[i].config = w.config;
            } else {
                Logger.error("Touchbar widget \"" + w.ID + "\" not found. Skipping...");
            }
            i++;
        }
        return ret;
    }

    public static boolean isEnabled(String s) {
        for (Widget s1 : Main.config.enabledWidgets) {
            if (s.equals(s1.ID)) {
                return true;
            }
        }
        return false;
    }
}
