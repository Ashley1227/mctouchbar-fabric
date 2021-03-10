package io.github.Ashley1227.mctouchbar.widget.widgets;

import com.thizzer.jtouchbar.JTouchBar;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import io.github.Ashley1227.mctouchbar.util.FramebufferUtils;
import io.github.Ashley1227.mctouchbar.widget.Widget;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class SuperDebugWidget extends Widget {
	public TouchBarButton button;

	public SuperDebugWidget() {

	}

	@Override
	public void tick() {
		super.tick();
//        if(button != null)

	}

	@Override
	public void addToTouchbar(JTouchBar jTouchBar) {
		super.addToTouchbar(jTouchBar);
		button = addButtonToTouchbar("widget.mctouchbar.debug", true, e -> {
			button.setImage(FramebufferUtils.getItemStackImg(Math.random() > 0.9 ? new ItemStack(Items.ACACIA_LOG) : new ItemStack(Items.ACACIA_DOOR), 64));
		});
		button.setImage(FramebufferUtils.getItemStackImg(Math.random() > 0.9 ? new ItemStack(Items.ACACIA_LOG) : new ItemStack(Items.ACACIA_DOOR), 64));
	}
}
