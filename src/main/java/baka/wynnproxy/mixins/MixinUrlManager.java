package baka.wynnproxy.mixins;

import baka.wynnproxy.ProxyClient;
import com.wynntils.core.net.UrlId;
import com.wynntils.core.net.UrlManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = UrlManager.class, remap = false)
public class MixinUrlManager {
    @Inject(method = "getUrlInfo", at = @At("RETURN"), cancellable = true)
    public void getUrlInfo(UrlId urlId, CallbackInfoReturnable<UrlManager.UrlInfo> cir) {
        if (cir.getReturnValue().url() == null) return;
        ProxyClient.LOGGER.info("getUrlInfo {}", cir.getReturnValue().url());
        if (cir.getReturnValue().url().contains("translate.googleapis.com") && ProxyClient.CONFIG.proxyConfig.googleApiReverseProxyHost != null && !ProxyClient.CONFIG.proxyConfig.googleApiReverseProxyHost.isEmpty()) {
            cir.setReturnValue(new UrlManager.UrlInfo(cir.getReturnValue().url().replace("translate.googleapis.com", ProxyClient.CONFIG.proxyConfig.googleApiReverseProxyHost),
                    cir.getReturnValue().arguments(), cir.getReturnValue().method(), cir.getReturnValue().encoding(), cir.getReturnValue().md5()));
            ProxyClient.LOGGER.info("Google Translate API request to {}", cir.getReturnValue().url());
        }
    }
}
