package io.github.ashley1227.mctouchbar.util;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.thizzer.jtouchbar.common.Image;
import com.thizzer.jtouchbar.common.ImageName;
import io.github.ashley1227.mctouchbar.MCTouchbar;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.Matrix3f;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;
import java.util.Arrays;

public class FramebufferUtils extends DrawableHelper {

	public static ItemRenderer itemRenderer;

	private static IntBuffer pixelBuffer;
	private static int[] pixelValues;


	public static int i = 0;

	public static int saveFramebufferToFile(Framebuffer buffer, File f, int width, int height, boolean cut) {
		try {
			BufferedImage bufferedimage = getImageFromFrameBuffer(buffer, width, height);
			ImageIO.write(bufferedimage, "png", f);
		} catch (Exception e) {
			MCTouchbar.LOGGER.error("Cannot save framebuffer to file " + f);
			MCTouchbar.LOGGER.error(e + ": " + e.getMessage());
			MCTouchbar.LOGGER.error(Arrays.toString(e.getStackTrace()));
		}
		return 0;
	}

	public static BufferedImage getImageFromFrameBuffer(Framebuffer buffer, int width, int height) {
		int k = buffer.textureWidth * buffer.textureHeight;
		if (pixelBuffer == null || pixelBuffer.capacity() < k) {
			pixelBuffer = BufferUtils.createIntBuffer(k);
			pixelValues = new int[k];
		}
		GL11.glPixelStorei(GL11.GL_PACK_ALIGNMENT, 1);
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		pixelBuffer.clear();
		GlStateManager.bindTexture(buffer.colorAttachment); // buffer.frameBufferTexture
		GL11.glGetTexImage(GL11.GL_TEXTURE_2D, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixelBuffer);
		pixelBuffer.get(pixelValues);

		/*TextureUtil.*/
		processPixelValues(pixelValues, buffer.textureWidth, buffer.textureHeight);
		BufferedImage bufferedimage = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		int l = buffer.textureHeight - buffer.viewportHeight;
		bufferedimage.setRGB(0, 0, width, height, pixelValues, l * buffer.textureWidth, buffer.viewportWidth);
//        Image image = new Image(pixelValues);
		return bufferedimage;
	}
//    public static NativeImage getImageFromFrameBuffer(Framebuffer framebuffer, int width, int height) {
//        width = framebuffer.textureWidth;
//        height = framebuffer.textureHeight;
//        NativeImage nativeImage = new NativeImage(width, height, false);
//        RenderSystem.bindTexture(framebuffer.depthAttachment);
//        nativeImage.loadFromTextureImage(0, true);
//        nativeImage.mirrorVertically();
//        return nativeImage;
//    }

	public static byte[] getImgBytes(BufferedImage image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			ImageIO.write(image, "PNG", baos);
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		return baos.toByteArray();
	}


	public static void processPixelValues(int[] pixelValues, int width, int height) {
		int[] tempPixelValues = new int[width];
		int i = height / 2;

		for (int j = 0; j < i; ++j) {
			System.arraycopy(pixelValues, j * width, tempPixelValues, 0, width);
			System.arraycopy(pixelValues, (height - 1 - j) * width, pixelValues, j * width, width);
			System.arraycopy(tempPixelValues, 0, pixelValues, (height - 1 - j) * width, width);
		}
	}

	public static Image getItemStackImg(ItemStack stack, int outputSize) {
		if (stack == null)
			return new Image(ImageName.NSImageNameTouchBarColorPickerFill, false);

		Framebuffer fb = renderItem(stack, outputSize);


		Image image = new Image(
				getImgBytes(
						getImageFromFrameBuffer(
								fb,
								outputSize, outputSize)
				)
		);
//		Image image = new Image(fb.)
		if (fb != MinecraftClient.getInstance().getFramebuffer()) {
//            fb.delete();
		}
		return image;
	}


	public static void saveItemStackPic(ItemStack stack, int outputSize) {
		Framebuffer fb = renderItem(stack, outputSize);
		File f = new File("pictures/item/" + outputSize + "x/" + stack.getItem().toString().replaceAll("[^0-9a-zA-Z]{1}", "_") + ".png");
		f.getParentFile().mkdirs();
		saveFramebufferToFile(fb, f, outputSize, outputSize, false);

		if (fb != MinecraftClient.getInstance().getFramebuffer()) {
//            fb.delete();
		}

		MCTouchbar.LOGGER.info("saving pic" + MCTouchbar.i);
	}

	public static void renderItemThing(Framebuffer mb, ItemStack stack, int outputSize) {
		if (itemRenderer == null)
//            itemRenderer = new ItemRenderer(MinecraftClient.getInstance().getTextureManager(), MinecraftClient.getInstance().getBakedModelManager(), ItemColors.create(BlockColors.create()));
			itemRenderer = MinecraftClient.getInstance().getItemRenderer();
//		RenderSystem.scalef(MinecraftClient.getInstance().getFramebuffer().viewportHeight/MinecraftClient.getInstance().getFramebuffer().viewportWidth, 1, 1f);
//		RenderSystem.loadIdentity();

//		itemRenderer.renderGuiItemIcon(stack, 0, 0);
//		itemRenderer.renderGuiItemOverlay(MinecraftClient.getInstance().textRenderer, stack, 0, 0, "e");

		MatrixStack matrices = new MatrixStack();
		BufferBuilder builder = new BufferBuilder(4096);
//
		drawItem(64, 64, outputSize, stack, matrices, VertexConsumerProvider.immediate(builder), 15728880, stack.getCount());
	}

	public static void drawItem(double x, double y, float scale, ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
		matrices.push();
		matrices.translate(x, y, 0.0001);
		matrices.translate(0, 0, -0.01);
		matrices.multiply(new Quaternion(Vector3f.NEGATIVE_Z, 180, true));
		matrices.multiply(new Quaternion(Vector3f.NEGATIVE_Y, 180, true));
		matrices.scale(scale, scale, 0.0001f);

		if (vertexConsumers instanceof VertexConsumerProvider.Immediate)
			((VertexConsumerProvider.Immediate) vertexConsumers).draw();

		BakedModel model = MinecraftClient.getInstance().getItemRenderer().getModels().getModel(stack);
		if (model.hasDepth())
			RenderSystem.setupGui3DDiffuseLighting();
		else
			RenderSystem.setupGuiFlatDiffuseLighting();
		matrices.peek().getNormal().load(Matrix3f.scale(1, -1, 1));
		MinecraftClient.getInstance().getItemRenderer().renderItem(stack, ModelTransformation.Mode.GUI, light, overlay, matrices, vertexConsumers);

		if (vertexConsumers instanceof VertexConsumerProvider.Immediate)
			((VertexConsumerProvider.Immediate) vertexConsumers).draw();
		matrices.pop();
	}

	private static Framebuffer FRAME_BUFFER;
//	private static int prevGuiScale;

	public static Framebuffer renderItem(ItemStack stack, int outputSize) {
		Framebuffer mb = MinecraftClient.getInstance().getFramebuffer();

		if (FRAME_BUFFER == null) {
//			FRAME_BUFFER = new Framebuffer(64, 64, true, true);
			FRAME_BUFFER = new Framebuffer(512, 512, true, true);

//			FRAME_BUFFER = new Framebuffer(mb.viewportWidth, mb.viewportHeight, true, true);
//			prevGuiScale = MinecraftClient.getInstance().options.guiScale;
		}

		Framebuffer fb = FRAME_BUFFER;
//		fb.resize(mb.viewportWidth, mb.viewportHeight, true);

		fb.clear(true);
		fb.beginWrite(true);

		RenderSystem.pushMatrix();
		renderItemThing(mb, stack, outputSize);

		mb.beginWrite(true);
		RenderSystem.popMatrix();

		MCTouchbar.LOGGER.info("renderItem" + MCTouchbar.i);

		File f = new File("pics/" + i++ + ".png");
		f.getParentFile().mkdirs();

		saveFramebufferToFile(fb, f, outputSize, outputSize, false);
		return fb;
	}

}