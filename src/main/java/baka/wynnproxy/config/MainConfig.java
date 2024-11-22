package baka.wynnproxy.config;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "WynnProxy")
public class MainConfig extends PartitioningSerializer.GlobalData
{
    @ConfigEntry.Category("proxy_settings")
    @ConfigEntry.Gui.TransitiveObject
    public ProxyConfig proxyConfig = new ProxyConfig();

}
