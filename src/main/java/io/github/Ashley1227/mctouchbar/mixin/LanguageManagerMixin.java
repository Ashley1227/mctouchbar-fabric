package io.github.ashley1227.mctouchbar.mixin;

import io.github.ashley1227.mctouchbar.TouchBarManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.LanguageManager;
import net.minecraft.resource.ResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LanguageManager.class)
public class LanguageManagerMixin {

	@Inject(at = @At("TAIL"), method = "Lnet/minecraft/client/resource/language/LanguageManager;reloadResources(Ljava/util/List;)V")
	private void constructor(List<ResourcePack> list, CallbackInfo ci) {
		if(MinecraftClient.getInstance().IS_SYSTEM_MAC)
			TouchBarManager.reloadTouchbar();
	}
}
