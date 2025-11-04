package com.mrgamerind.reconnect;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public abstract class ReconnectButtonMixin extends Screen {
    protected ReconnectButtonMixin(Text title) { super(title); }

    @Inject(method = "init", at = @At("TAIL"))
    private void addReconnectButton(CallbackInfo ci) {
        DisconnectedScreen screen = (DisconnectedScreen) (Object) this;
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Reconnect"), button -> {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.getCurrentServerEntry() != null) {
                client.setScreen(null);
                client.connect(client.getCurrentServerEntry());
            }
        }).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());
    }
}
