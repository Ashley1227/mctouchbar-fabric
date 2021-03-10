package io.github.Ashley1227.mctouchbar.bars;

import com.thizzer.jtouchbar.JTouchBar;
import io.github.Ashley1227.mctouchbar.TouchBarManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFWNativeCocoa;

public abstract class ScreenTouchBar {
    protected JTouchBar touchBar;
    protected MinecraftClient client;
    protected Screen minecraftScreen;

    protected static Logger LOGGER = LogManager.getLogger();


    public ScreenTouchBar(Screen screen) {
        this.touchBar = new JTouchBar();
        this.client = MinecraftClient.getInstance();
        this.minecraftScreen = screen;
    }

    private long getNativeHandle() {
        return GLFWNativeCocoa.glfwGetCocoaWindow(TouchBarManager.getHandle());
    }

    public void show() {
        this.touchBar.show(this.getNativeHandle());
    }

    public void hide() {
        this.touchBar.hide(this.getNativeHandle());
    }
}
