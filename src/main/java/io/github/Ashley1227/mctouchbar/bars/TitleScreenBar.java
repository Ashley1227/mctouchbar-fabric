package io.github.Ashley1227.mctouchbar.bars;

import com.thizzer.jtouchbar.common.Color;
import com.thizzer.jtouchbar.common.Image;
import com.thizzer.jtouchbar.common.ImagePosition;
import com.thizzer.jtouchbar.item.TouchBarItem;
import com.thizzer.jtouchbar.item.view.TouchBarButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screen.option.LanguageOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.ResourceTexture;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class TitleScreenBar extends ScreenTouchBar implements TickableTouchBar {
    private static final Identifier ACCESSIBILITY_ICON_TEXTURE = new Identifier("textures/gui/accessibility.png");
    private static final Logger LOGGER = LogManager.getLogger();


    private boolean pictureSet;
    private TouchBarButton btn_language;
    private TouchBarButton btn_accessibility;

    public TitleScreenBar(Screen screen) {
        super(screen);

        this.pictureSet = false;

        btn_language = new TouchBarButton();
        btn_language.setImagePosition(ImagePosition.ONLY);
        btn_language.setAction(e -> {
            this.client.openScreen(new LanguageOptionsScreen(this.minecraftScreen, this.client.options, this.client.getLanguageManager()));
        });
        btn_language.setBezelColor(Color.LIGHT_GRAY);
        this.touchBar.addItem(new TouchBarItem("languagebutton", btn_language));

        btn_accessibility = new TouchBarButton();
        btn_accessibility.setImagePosition(ImagePosition.ONLY);
        btn_accessibility.setAction(e -> {
            this.client.openScreen(new AccessibilityOptionsScreen(this.minecraftScreen, this.client.options));
        });
        btn_accessibility.setBezelColor(Color.ORANGE);
        this.touchBar.addItem(new TouchBarItem("accessibilityIcon", btn_accessibility));
    }

    static private BufferedImage createImageFromBytes(byte[] imageData) throws IOException {
        ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
        return ImageIO.read(bais);
    }

    private static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;

    }

    private void loadImage() throws IOException {
        ResourceTexture.TextureData languageTexture = new ResourceTexture.TextureData(null).load(this.client.getResourceManager(), ButtonWidget.WIDGETS_LOCATION);;
        LOGGER.info("trying to load image");
        NativeImage image = languageTexture.getImage();
        if (image != null) {
            BufferedImage bufferedImage = TitleScreenBar.createImageFromBytes(image.getBytes());
            bufferedImage = bufferedImage.getSubimage(0, 106, 20, 20);


            this.btn_language.setImage(new Image(TitleScreenBar.toByteArray(bufferedImage, "png")));
            this.pictureSet = true;
        }
        languageTexture = new ResourceTexture.TextureData(null).load(this.client.getResourceManager(), ACCESSIBILITY_ICON_TEXTURE);;
        image = languageTexture.getImage();
        if (image != null) {
            BufferedImage bufferedImage = TitleScreenBar.createImageFromBytes(image.getBytes());
            bufferedImage = bufferedImage.getSubimage(0, 0, 20, 20);


            this.btn_accessibility.setImage(new Image(TitleScreenBar.toByteArray(bufferedImage, "png")));
            this.pictureSet = true;
        }
    }

    public void tick() {
        if (!this.pictureSet) {
            try {
                this.loadImage();
            } catch (IOException e) {
                LOGGER.warn("Could not load languageTexture: %s", e);
            }
        }
    }
}
