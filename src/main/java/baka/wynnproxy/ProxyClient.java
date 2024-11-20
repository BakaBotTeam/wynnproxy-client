package baka.wynnproxy;

import baka.wynnproxy.config.MainConfig;
import baka.wynnproxy.config.ProxyConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProxyClient  implements ModInitializer {
    public static final String MOD_ID = "WynnProxy";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static final MainConfig CONFIG = AutoConfig.register(MainConfig.class,
            PartitioningSerializer.wrap(JanksonConfigSerializer::new)).getConfig();

    @Override
    public void onInitialize() {
    }
}