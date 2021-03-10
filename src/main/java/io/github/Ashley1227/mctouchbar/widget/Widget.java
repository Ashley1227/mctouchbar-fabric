package io.github.Ashley1227.mctouchbar.widget;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.common.Color;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import com.thizzer.jtouchbar.item.view.TouchBarSlider;
import com.thizzer.jtouchbar.item.view.TouchBarTextField;
import com.thizzer.jtouchbar.item.view.TouchBarView;
import com.thizzer.jtouchbar.item.view.action.TouchBarViewAction;
import com.thizzer.jtouchbar.slider.SliderActionListener;
import io.github.Ashley1227.mctouchbar.MCTouchbar;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFWNativeCocoa;

public class Widget {

	protected JTouchBar jTouchBar;

	public Widget() {
	}

	/**
	 * @param jTouchBar JTouchBar instance that's being added to
	 * @param config the widget's configuration
	 *
	 *               Called when the TouchBar gets reloaded.
	 */
	public void addToTouchbar(JTouchBar jTouchBar) {
		this.jTouchBar = jTouchBar;
	}

	public void tick() {
	}

	/**
	 * @param title the label of the button you're adding
	 * @param action lambda expression to be executed when you press the button. Takes an event parameter, but it's not really useful
	 *               Adds a button to the TouchBar
	 * @return the TouchBarButton object that was just generated
	 */
	public TouchBarButton addButtonToTouchbar(String title, boolean translatable, TouchBarViewAction action) {
		TouchBarButton btn = new TouchBarButton();
		btn.setTitle(translatable ? I18n.translate(title) : title);
		btn.setAction(action);
		btn.setBezelColor(Color.DARK_GRAY);
		this.jTouchBar.addItem(new TouchBarItem(title, btn));
		return btn;
	}
	/**
	 * @param title the TranslatableText object that will determine the button's label
	 * @param action lambda expression to be executed when you press the button. Takes an event parameter, but it's not really useful
	 *               Adds a button to the TouchBar
	 * @return the TouchBarButton object that was just generated
	 */
	public TouchBarButton addButtonToTouchbar(Text title, TouchBarViewAction action) {
		TouchBarButton btn = new TouchBarButton();
		btn.setTitle(title.asString());

		btn.setAction(action);

		btn.setBezelColor(Color.DARK_GRAY);

		this.jTouchBar.addItem(new TouchBarItem(title.asString(), btn));

		return btn;
	}

	public TouchBarSlider addSliderToTouchbar(TranslatableText title, int min, int max, SliderActionListener actionListener) {
		TouchBarSlider slider = new TouchBarSlider();
		slider.setMinValue(min);
		slider.setMaxValue(max);

		slider.setActionListener(actionListener);

		this.jTouchBar.addItem(new TouchBarItem(title.asString(), slider));

		return slider;
	}
	/**
	 *            Adds a Minecraft Text object to the TouchBar
	 * @return the TouchBarTextField that was just generated
	 */
	public TouchBarTextField addTextToTouchbar(Text text) {
		String title = text.asString();

		TouchBarTextField textField = new TouchBarTextField();
		textField.setStringValue(title == null ? "" : title);

		this.jTouchBar.addItem(new TouchBarItem(title, textField));

		return textField;
	}

	/**
	 *            Adds a string to the TouchBar
	 * @param translatable if the first argument is a translation key or not
	 * @return the TouchBarTextField that was just generated
	 */
	public TouchBarTextField addStringToTouchbar(String str, boolean translatable) {
		String title = translatable ? I18n.translate(str) : str;

		TouchBarTextField textField = new TouchBarTextField();
		textField.setStringValue(title == null ? "" : title);

		this.jTouchBar.addItem(new TouchBarItem(title, textField));

		return textField;
	}
}
