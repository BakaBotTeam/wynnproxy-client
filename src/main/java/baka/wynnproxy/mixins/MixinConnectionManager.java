package baka.wynnproxy.mixins;

import baka.wynnproxy.ProxyClient;
import com.wynntils.core.mod.ConnectionManager;
import com.wynntils.mc.event.ConnectionEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ConnectionManager.class, remap = false)
public class MixinConnectionManager {
    @Shadow private boolean isConnected;

    @Inject(method = "onConnected", at = @At("RETURN"))
    public void onConnected(ConnectionEvent.ConnectedEvent e, CallbackInfo ci) {
        this.isConnected = true;
    }
}
