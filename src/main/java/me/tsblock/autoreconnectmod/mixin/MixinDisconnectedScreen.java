package me.tsblock.autoreconnectmod.mixin;

import me.tsblock.autoreconnectmod.AutoReconnectMod;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.AbstractButtonWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisconnectedScreen.class)
public abstract class MixinDisconnectedScreen extends Screen {
    @Shadow @Final private Screen parent;
    private ButtonWidget reconnectButton;
    public MixinDisconnectedScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void init(CallbackInfo info) {
        AbstractButtonWidget toMenuWidget = this.buttons.get(0);
        reconnectButton = this.addButton(new ButtonWidget(this.width / 2 - 100, toMenuWidget.y + 25, 200, 20, new TranslatableText("gui.autoreconnect.reconnectbutton", AutoReconnectMod.getStatusText(), AutoReconnectMod.getTimeLeft()).asFormattedString(), (buttonWidget) -> {
            AutoReconnectMod.enabled = !AutoReconnectMod.enabled;
        }));
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void render(CallbackInfo info) {
        reconnectButton.setMessage(new TranslatableText("gui.autoreconnect.reconnectbutton", AutoReconnectMod.getStatusText(), AutoReconnectMod.getTimeLeft()).asFormattedString());
    }

}
