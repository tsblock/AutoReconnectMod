package me.tsblock.autoreconnectmod.mixin;

import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.Screen;
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
    private long endTime;
    public MixinDisconnectedScreen(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("RETURN"))
    private void init(CallbackInfo info) {
        endTime = System.currentTimeMillis() + 5000;
        AbstractButtonWidget toMenuButton = this.buttons.get(0);
        this.buttons.add(new ButtonWidget(this.width / 2, toMenuButton.y - 100, 200, 20, new TranslatableText("gui.autoreconnect.reconnectbutton").asString(), button -> {

        }));
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void render(CallbackInfo info) {

    }

}
