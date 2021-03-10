package io.github.Ashley1227.mctouchbar.bars;

import com.thizzer.jtouchbar.JTouchBar;
import io.github.Ashley1227.mctouchbar.TouchBarManager;
import io.github.Ashley1227.mctouchbar.widget.widgets.GameModeWidget;
import io.github.Ashley1227.mctouchbar.widget.widgets.HeadingWidget;
import org.lwjgl.glfw.GLFWNativeCocoa;

public class InGameTouchBar {
    private JTouchBar touchbar;
    private HeadingWidget headingWidget;
    private GameModeWidget gameModeSwitcher;

    public InGameTouchBar() {
        this.touchbar = new JTouchBar();

        this.headingWidget = new HeadingWidget();
        this.headingWidget.addToTouchbar(this.touchbar);
        this.gameModeSwitcher = new GameModeWidget();
        this.touchbar.addItem(this.gameModeSwitcher.getTouchBarItem());
    }

    public void tick() {
        // TODO: tick gameModeSwitcher
        gameModeSwitcher.tick();
        headingWidget.tick();
    }

    public void show() {
        // TODO: this should not be here

        this.touchbar.show(GLFWNativeCocoa.glfwGetCocoaWindow(TouchBarManager.getHandle()));
    }
}
