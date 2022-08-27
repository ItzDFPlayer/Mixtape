package com.unjust1ce.mixtape.config;

import com.unjust1ce.mixtape.Mixtape;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class MixtapePacks {
    public static void init() {
        Identifier cassettes = new Identifier("mixtape", "cassettes");
        Identifier noCavesAndCliffs = new Identifier("mixtape", "no_caves_and_cliffs");
        Identifier noCavesAndCliffsPlusUnusedMusic = new Identifier("mixtape", "no_caves_and_cliffs_plus_unused_music");
        Identifier unusedMusic = new Identifier("mixtape", "unused_music");
        Identifier end0x10c = new Identifier("mixtape", "0x10c_in_the_end");
        Identifier noNetherUpdate = new Identifier("mixtape", "no_nether_update");
        Identifier noNetherUpdatePlusUnusedMusic = new Identifier("mixtape", "no_nether_update_plus_unused_music");
        FabricLoader.getInstance().getModContainer("mixtape").ifPresent(
                container -> ResourceManagerHelper.registerBuiltinResourcePack(cassettes, container, ResourcePackActivationType.NORMAL));
        FabricLoader.getInstance().getModContainer("mixtape").ifPresent(
                container -> ResourceManagerHelper.registerBuiltinResourcePack(noCavesAndCliffs, container, ResourcePackActivationType.NORMAL));
        FabricLoader.getInstance().getModContainer("mixtape").ifPresent(
                container -> ResourceManagerHelper.registerBuiltinResourcePack(noCavesAndCliffsPlusUnusedMusic, container, ResourcePackActivationType.NORMAL));
        FabricLoader.getInstance().getModContainer("mixtape").ifPresent(
                container -> ResourceManagerHelper.registerBuiltinResourcePack(unusedMusic, container, ResourcePackActivationType.NORMAL));
        FabricLoader.getInstance().getModContainer("mixtape").ifPresent(
                container -> ResourceManagerHelper.registerBuiltinResourcePack(end0x10c, container, ResourcePackActivationType.NORMAL));
        FabricLoader.getInstance().getModContainer("mixtape").ifPresent(
                container -> ResourceManagerHelper.registerBuiltinResourcePack(noNetherUpdate, container, ResourcePackActivationType.NORMAL));
        FabricLoader.getInstance().getModContainer("mixtape").ifPresent(
                container -> ResourceManagerHelper.registerBuiltinResourcePack(noNetherUpdatePlusUnusedMusic, container, ResourcePackActivationType.NORMAL));
    }
}
