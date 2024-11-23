package baka.wynnproxy.mixins;

import baka.wynnproxy.ProxyClient;
import baka.wynnproxy.utils.Utils;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.client.network.CookieStorage;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.realms.util.JsonUtils;
import net.minecraft.network.packet.s2c.common.ServerTransferS2CPacket;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.Objects;

@Mixin(ClientCommonNetworkHandler.class)
public class MixinClientCommonNetworkHandler {
    @Shadow @Final protected ServerInfo serverInfo;
    @Shadow @Final protected MinecraftClient client;
    @Shadow @Final protected Screen postDisconnectScreen;
    @Shadow @Final protected Map<Identifier, byte[]> serverCookies;

    @Inject(method = "onServerTransfer", at = @At(value = "INVOKE", target = "Lnet/minecraft/network/ClientConnection;handleDisconnection()V", shift = At.Shift.AFTER), cancellable = true)
    private void onServerTransfer(ServerTransferS2CPacket packet, CallbackInfo ci) {
        if (ProxyClient.CONFIG.proxyConfig.url == null || ProxyClient.CONFIG.proxyConfig.url.isEmpty() || ProxyClient.CONFIG.proxyConfig.secret == null || ProxyClient.CONFIG.proxyConfig.secret.isEmpty()) {
            ProxyClient.LOGGER.error("Proxy server is not configured, please configure it in the mod menu");
            return;
        }
        ci.cancel();
        ProxyClient.LOGGER.info("Sending request to WynnProxy server");
        long time = System.currentTimeMillis();
        String url = ProxyClient.CONFIG.proxyConfig.url + "?name=" + this.client.getSession().getUsername() + 
                "&ts=" + time + "&host=" + packet.host() + "&port=" + packet.port();
        String signRaw = this.client.getSession().getUsername() + ":" + time + ":" + packet.host() + ":" + packet.port() + ":" + ProxyClient.CONFIG.proxyConfig.secret;
        String sign = "";
        try {
            sign = Utils.sha256(signRaw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        url += "&sign=" + sign;
        JsonObject response = Utils.httpGet(url);
        if (JsonUtils.getIntOr("code", response, -1) != 0) {
            throw new RuntimeException("Failed to contact with WynnProxy server, reason: " + JsonUtils.getStringOr("msg", response, "Unknown"));
        }
        ProxyClient.LOGGER.info("Server transfer to " + packet.host() + ":" + packet.port() + ", modify to origin server");
        int port = 25565;
        String address = serverInfo.address;
        if (address.contains(":")) {
            String[] split = address.split(":");
            address = split[0];
            port = Integer.parseInt(split[1]);
        }
        ServerAddress serverAddress = new ServerAddress(address, port);
        ConnectScreen.connect(Objects.requireNonNullElseGet(this.postDisconnectScreen, TitleScreen::new), this.client, serverAddress, this.serverInfo, false, new CookieStorage(this.serverCookies));
    }
}
