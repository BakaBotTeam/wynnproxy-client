package baka.wynnproxy;

import baka.wynnproxy.config.ProxyConfig;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;

@Environment(EnvType.CLIENT)
public class ModMenuIntegration implements ModMenuApi{
    @Override
    public ConfigScreenFactory<Screen> getModConfigScreenFactory() {
        return parent -> AutoConfig.getConfigScreen(ProxyConfig.class, parent).get();
    }
}
