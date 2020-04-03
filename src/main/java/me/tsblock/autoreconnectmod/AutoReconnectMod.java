package me.tsblock.autoreconnectmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.network.ServerInfo;

@Environment(EnvType.CLIENT)
public class AutoReconnectMod implements ClientModInitializer {
    public static ServerInfo lastServerInfo;
    public static int countdownTick;
    public static int reconnectTick = 5 * 20;
    public static boolean enabled;
    @Override
    public void onInitializeClient() {
        ClientTickCallback.EVENT.register(minecraftClient -> onClientTick());
    }

    private void onClientTick() {
        MinecraftClient minecraft = MinecraftClient.getInstance();
        if (minecraft.getCurrentServerEntry() != null) {
            lastServerInfo = minecraft.getCurrentServerEntry();
        }
        if (minecraft.currentScreen instanceof DisconnectedScreen) {

        }
    }
}
