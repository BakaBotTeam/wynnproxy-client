package baka.wynnproxy;

import baka.wynnproxy.config.MainConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class ProxyClient  implements ClientModInitializer {
    public static final String MOD_ID = "wynnproxy";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static MainConfig CONFIG;

    @Override
    public void onInitializeClient() {
        AutoConfig.register(MainConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        // Intuitive way to load a config :)
        CONFIG = AutoConfig.getConfigHolder(MainConfig.class).getConfig();
    }
}