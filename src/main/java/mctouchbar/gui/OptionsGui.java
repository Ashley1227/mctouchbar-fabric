package mctouchbar.gui;

import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.*;
import mctouchbar.Main;
import mctouchbar.widget.Widgets;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.SettingsScreen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.text.BaseText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

import java.util.ArrayList;
import java.util.List;

public class OptionsGui extends LightweightGuiDescription {
    public static OptionsGui instance;
    public static OptionsScreen screen;

    public int scroll = 0;

    public WButton[] widgetSelectorButtons;
    public WButton[] slotButtons = new WButton[9];
    public WButton remove;

    public int selectedButton = 0;

    public WPlainPanel root;

    public OptionsGui() {
        root = new WPlainPanel();
        setRootPanel(root);
        root.setSize(300, 200);

        WLabel label = new WLabel(new TranslatableText("gui.mctouchbar.options.label"), 0xFFFFFF);
        root.add(label, 0, 0);

        WButton up = new WButton(new TranslatableText("gui.mctouchbar.options.scroll_up"));
        WButton down = new WButton(new TranslatableText("gui.mctouchbar.options.scroll_down"));


        root.add(up, 300, 16, 16, 16);
        up.setOnClick(() -> {
            scroll = Math.max(scroll - 1, 0);
            if (scroll == 0) {
                up.setEnabled(false);
            }
            down.setEnabled(true);
            updateButtons();
        });
        down.setOnClick(() -> {
            scroll = Math.min(scroll + 1, Widgets.widgetStorage.size() - 5);
            if (scroll == Widgets.widgetStorage.size() - 5) {
                down.setEnabled(false);
            }
            up.setEnabled(true);
//				scroll++;
            updateButtons();
        });
        root.add(down, 300, 38, 16, 16);

        updateButtons();

        WButton done = new WButton(new TranslatableText("gui.done"));
        done.setOnClick(() -> {
            MinecraftClient.getInstance().openScreen(new SettingsScreen(new TitleScreen(), MinecraftClient.getInstance().options));
            MinecraftClient.getInstance().openScreen(new SettingsScreen(new TitleScreen(), MinecraftClient.getInstance().options));
        });
        root.add(done, 0, 74 + 26 * 5, 140, 1);

    }

    public void updateButtons() {
        WButton remove = new WButton(new TranslatableText("gui.mctouchbar.options.remove"));
        remove.setOnClick(() -> {
            Main.config.enabledWidgets[selectedButton] = null;

            Widgets.update();
            updateButtons();
            Main.saveConfig(Main.config);
        });

        root.add(remove, 0, 16, 140, 1);

        widgetSelectorButtons = new WButton[5];

        List<String> keys = new ArrayList(Widgets.widgetStorage.keySet());

        for (int i = 0; i < 5; i++) {
            widgetSelectorButtons[i] = new WButton(new TranslatableText(keys.get(scroll + i)));
            final int x = i;
            widgetSelectorButtons[i].setOnClick(() -> {

                Main.config.enabledWidgets[selectedButton] = Widgets.widgetStorage.get(keys.get(x + scroll));

                Widgets.update();
                updateButtons();
                Main.saveConfig(Main.config);
            });
            root.add(widgetSelectorButtons[i], 0, 58 + 26 * i, 140, 1);
            if (i + scroll > Widgets.widgetStorage.size() - 2) {
                break;
            }
        }


        slotButtons = new WButton[9];

        for (int i = 0; i < 9; i++) {
            final int x = i;
            slotButtons[i] = new WButton(new BaseText() {
                @Override
                public String asString() {
                    String s = new TranslatableText("gui.mctouchbar.options.slot." + x).asString();
                    if (Main.config.enabledWidgets[x] != null) {
                        s += ": " + new TranslatableText(Main.config.enabledWidgets[x].ID).asString();
                    } else {
                        s += ": " + new TranslatableText("gui.mctouchbar.options.empty").asString();
                    }
                    return s;
                }

                @Override
                public Text copy() {
                    return null;
                }
            });
            slotButtons[i].setOnClick(() -> {
                selectedButton = x;
                updateButtons();
//					slotButtons[x].setEnabled(false);
            });
            if (i == selectedButton) {
                slotButtons[i].setEnabled(false);
            }
            root.add(slotButtons[i], 156, 26 * i, 140, 1);

        }
    }

    @Override
    public void addPainters() {
//		getRootPanel().setBackgroundPainter(BackgroundPainter.VANILLA); // This is done automatically though
    }
}