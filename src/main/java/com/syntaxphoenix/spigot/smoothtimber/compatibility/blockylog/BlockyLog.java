package com.syntaxphoenix.spigot.smoothtimber.compatibility.blockylog;

import com.syntaxphoenix.spigot.smoothtimber.SmoothTimber;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.CompatibilityAddon;
import com.syntaxphoenix.spigot.smoothtimber.compatibility.IncompatiblePluginException;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.LocationResolver;
import com.syntaxphoenix.spigot.smoothtimber.utilities.locate.Locator;
import com.syntaxphoenix.spigot.smoothtimber.utilities.plugin.PluginPackage;

public class BlockyLog extends CompatibilityAddon {

    private LocationResolver resolver;

    @Override
    public void onEnable(final PluginPackage pluginPackage, final SmoothTimber smoothTimber) throws Exception {
        switch (pluginPackage.getVersion().major()) {
        case 1:
            resolver = new BlockyLogResolver_v1_x();
            break;
        case 2:
            resolver = new BlockyLogResolver_v2_x();
            break;
        default:
            throw new IncompatiblePluginException(pluginPackage, "1.X", "2.X");
        }
        Locator.setLocationResolver(resolver);
    }

    @Override
    public void onDisable(final SmoothTimber smoothTimber) throws Exception {
        resolver = null;
        Locator.setLocationResolver(null);
    }

    public final LocationResolver getLocationResolver() {
        return resolver;
    }

}
