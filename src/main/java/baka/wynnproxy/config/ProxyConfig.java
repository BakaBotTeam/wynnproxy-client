package baka.wynnproxy.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "proxy_settings")
public class ProxyConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip
    public String ip;

    @ConfigEntry.BoundedDiscrete(min = 1, max = 65535)
    @ConfigEntry.Gui.Tooltip
    public int port;

    @ConfigEntry.Gui.Tooltip
    public String secret;

}
