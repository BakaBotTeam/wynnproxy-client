package baka.wynnproxy.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "proxy_settings")
public class ProxyConfig implements ConfigData {
    public String url;
    public String secret;
    public String googleApiReverseProxyHost;

}
