package me.tsblock.autoreconnectmod.mixin;

import me.tsblock.autoreconnectmod.AutoReconnectClientMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ConnectScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public abstract class MixinDisconnectedScreen extends Screen {
    @Shadow @Final private Screen parent;
    private ButtonWidget autoReconnectButton;
    private ButtonWidget reconnectButton;
    public MixinDisconnectedScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void init(CallbackInfo info) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        AbstractButtonWidget toMenuWidget = this.buttons.get(0);
        autoReconnectButton = this.addButton(new ButtonWidget(this.width / 2 - 100, toMenuWidget.y + 25, 200, 20, new TranslatableText("gui.autoreconnect.autoreconnectbutton", AutoReconnectClientMod.getStatusText(), AutoReconnectClientMod.getTimeLeft()), (buttonWidget) -> {
            AutoReconnectClientMod.enabled = !AutoReconnectClientMod.enabled;
        }));
        reconnectButton = this.addButton(new ButtonWidget(this.width / 2 - 100, toMenuWidget.y + 45, 200, 20, new TranslatableText("gui.autoreconnect.reconnectbutton"), (buttonWidget -> {
            minecraftClient.disconnect();
            minecraftClient.openScreen(new ConnectScreen(new TitleScreen(), minecraftClient, AutoReconnectClientMod.getLastServerInfo()));
        })));
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void render(CallbackInfo info) {
        autoReconnectButton.setMessage(new TranslatableText("gui.autoreconnect.autoreconnectbutton", AutoReconnectClientMod.getStatusText(), AutoReconnectClientMod.getTimeLeft()));
    }

}
