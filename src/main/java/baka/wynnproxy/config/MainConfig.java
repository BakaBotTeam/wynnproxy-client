package baka.wynnproxy.config;

import baka.wynnproxy.ProxyClient;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = ProxyClient.MOD_ID)
public class MainConfig extends PartitioningSerializer.GlobalData
{
    @ConfigEntry.Category("proxy_settings")
    @ConfigEntry.Gui.TransitiveObject
    public ProxyConfig proxyConfig = new ProxyConfig();
}
