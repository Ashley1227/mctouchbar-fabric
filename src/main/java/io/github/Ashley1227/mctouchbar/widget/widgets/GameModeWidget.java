package io.github.Ashley1227.mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.common.Color;
import com.thizzer.jtouchbar.item.PopoverTouchBarItem;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import io.github.Ashley1227.mctouchbar.widget.Widget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.GameMode;

// TODO: implement observer?
public class GameModeWidget extends Widget {
    private ClientPlayerInteractionManager interactionManager;
    private JTouchBar gamemodes_bar;

    private TouchBarButton btn_curr;
    private PopoverTouchBarItem popover_button;

    // buttons
    private TouchBarButton btn_survival;
    private TouchBarButton btn_creative;
    private TouchBarButton btn_adventure;
    private TouchBarButton btn_spectator;

    public GameModeWidget(ClientPlayerInteractionManager interactionManager) {
        this.interactionManager = interactionManager;

        this.gamemodes_bar = new JTouchBar();
        this.popover_button = new PopoverTouchBarItem("gamemode_switcher");
        initButtons();
    }

    private void initButtons() {
        if (this.interactionManager == null) {
            return;
        }
        this.btn_survival = this.newButton(GameMode.SURVIVAL);
        this.btn_creative = this.newButton(GameMode.CREATIVE);
        this.btn_adventure = this.newButton(GameMode.ADVENTURE);
        this.btn_spectator = this.newButton(GameMode.SPECTATOR);

        btn_curr = this.newButton(interactionManager.getCurrentGameMode());
        //btn_curr.setTitle(I18n.translate(interactionManager.getCurrentGameMode().getTranslatableName().asString()));
        btn_curr.setTitle(interactionManager.getCurrentGameMode().getName());

        popover_button.setPopoverTouchBar(this.gamemodes_bar);
        popover_button.setView(btn_curr);
    }

    // TODO: should this be deprecated?
    public GameModeWidget() {
        this(MinecraftClient.getInstance().interactionManager);
    }

    public TouchBarItem getTouchBarItem() {
        return this.popover_button;
    }

    // Replace with mixin hook
    public void tick() {
        if (this.interactionManager == null && MinecraftClient.getInstance().interactionManager != null) {
            this.interactionManager = MinecraftClient.getInstance().interactionManager;
            this.initButtons();
        }
    }

    protected void setGameMode(GameMode gameMode) {
        // todo update button
        this.interactionManager.setGameMode(gameMode);
    }

    private TouchBarButton newButton(GameMode gameMode) {
        TouchBarButton ret = new TouchBarButton();
        //ret.setTitle(I18n.translate(gameMode.getTranslatableName().asString()));
        ret.setTitle(gameMode.getName());
        ret.setBezelColor(Color.BLUE);
        ret.setAction(v -> {
            this.setGameMode(gameMode);
        });

        // TODO: coloring
        /*TouchBarItem item = new TouchBarItem(gameMode.getName());
        item.setView(ret);*/
        this.gamemodes_bar.addItem(new TouchBarItem(gameMode.getName(), ret));
        return ret;
    }
}
