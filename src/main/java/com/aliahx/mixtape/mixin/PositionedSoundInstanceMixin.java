package com.aliahx.mixtape.mixin;

import com.aliahx.mixtape.Mixtape;
import com.aliahx.mixtape.config.ModConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(PositionedSoundInstance.class)
public class PositionedSoundInstanceMixin{

    @Inject(method = "music(Lnet/minecraft/sound/SoundEvent;)Lnet/minecraft/client/sound/PositionedSoundInstance;", at = @At("RETURN"), cancellable = true)
    private static void musicMixin(SoundEvent sound, CallbackInfoReturnable<PositionedSoundInstance> cir) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        if(config.mainConfig.enabled) {
            sound = switch (config.mainConfig.musicType) {
                case AUTOMATIC -> sound;
                case CREATIVE -> SoundEvents.MUSIC_CREATIVE;
                case CREDITS -> SoundEvents.MUSIC_CREDITS;
                case DRAGON -> SoundEvents.MUSIC_DRAGON;
                case END -> SoundEvents.MUSIC_END;
                case GAME -> SoundEvents.MUSIC_GAME;
                case MENU -> SoundEvents.MUSIC_MENU;
                case NETHER_BASALT_DELTAS -> SoundEvents.MUSIC_NETHER_BASALT_DELTAS;
                case NETHER_CRIMSON_FOREST -> SoundEvents.MUSIC_NETHER_CRIMSON_FOREST;
                case NETHER_NETHER_WASTES -> SoundEvents.MUSIC_NETHER_NETHER_WASTES;
                case NETHER_SOUL_SAND_VALLEY -> SoundEvents.MUSIC_NETHER_SOUL_SAND_VALLEY;
                case NETHER_WARPED_FOREST -> SoundEvents.MUSIC_NETHER_WARPED_FOREST;
                case OVERWORLD_DEEP_DARK -> SoundEvents.MUSIC_OVERWORLD_DEEP_DARK;
                case OVERWORLD_DRIPSTONE_CAVES -> SoundEvents.MUSIC_OVERWORLD_DRIPSTONE_CAVES;
                case OVERWORLD_FROZEN_PEAKS -> SoundEvents.MUSIC_OVERWORLD_FROZEN_PEAKS;
                case OVERWORLD_GROVE -> SoundEvents.MUSIC_OVERWORLD_GROVE;
                case OVERWORLD_JAGGED_PEAKS -> SoundEvents.MUSIC_OVERWORLD_JAGGED_PEAKS;
                case OVERWORLD_JUNGLE_AND_FOREST -> SoundEvents.MUSIC_OVERWORLD_JUNGLE_AND_FOREST;
                case OVERWORLD_LUSH_CAVES -> SoundEvents.MUSIC_OVERWORLD_LUSH_CAVES;
                case OVERWORLD_MEADOW -> SoundEvents.MUSIC_OVERWORLD_MEADOW;
                case OVERWORLD_OLD_GROWTH_TAIGA -> SoundEvents.MUSIC_OVERWORLD_OLD_GROWTH_TAIGA;
                case OVERWORLD_SNOWY_SLOPES -> SoundEvents.MUSIC_OVERWORLD_SNOWY_SLOPES;
                case OVERWORLD_STONY_PEAKS -> SoundEvents.MUSIC_OVERWORLD_STONY_PEAKS;
                case OVERWORLD_SWAMP -> SoundEvents.MUSIC_OVERWORLD_SWAMP;
                case UNDER_WATER -> SoundEvents.MUSIC_UNDER_WATER;
            };
        }

        if(config.mainConfig.enabled && config.gameConfig.creativeMusicPlaysInSurvival && (sound.getId().toString().equals("minecraft:music.game")) || sound.getId().toString().contains("minecraft:music.overworld.")) {
            sound = SoundEvents.MUSIC_CREATIVE;
        }

        Mixtape.debugCurrentMusicType = sound.getId().toString();

        float volume = 100.0F;
        if (config.mainConfig.enabled && config.mainConfig.varyPitch) {
            switch (sound.getId().toString()) {
                case "minecraft:music.menu" -> {
                    if (!config.menuConfig.enabled) cir.setReturnValue(null);
                    volume = config.menuConfig.volume;
                }
                case "minecraft:music.creative" -> {
                    if (!config.creativeConfig.enabled) cir.setReturnValue(null);
                    volume = config.creativeConfig.volume;
                }
                case "minecraft:music.end" -> {
                    if (!config.endConfig.enabled) cir.setReturnValue(null);
                    volume = config.endConfig.volume;
                }
                case "minecraft:music.under_water" -> {
                    if (!config.underwaterConfig.enabled) cir.setReturnValue(null);
                    volume = config.underwaterConfig.volume;
                }
                case "minecraft:music.credits" -> {
                    if (!config.creditsConfig.enabled) cir.setReturnValue(null);
                    volume = config.creditsConfig.volume;
                }
                case "minecraft:music.game", "minecraft:music.overworld.deep_dark", "minecraft:music.overworld.dripstone_caves", "minecraft:music.overworld.grove", "minecraft:music.overworld.jagged_peaks", "minecraft:music.overworld.lush_caves", "minecraft:music.overworld.swamp", "minecraft:music.overworld.jungle_and_forest", "minecraft:music.overworld.old_growth_taiga", "minecraft:music.overworld.meadow", "minecraft:music.overworld.frozen_peaks", "minecraft:music.overworld.snowy_slopes", "minecraft:music.overworld.stony_peaks" -> {
                    if (!config.gameConfig.enabled) cir.setReturnValue(null);
                    volume = config.gameConfig.volume;
                }
                case "minecraft:music.nether.nether_wastes", "minecraft:music.nether.warped_forest", "minecraft:music.nether.soul_sand_valley", "minecraft:music.nether.crimson_forest", "minecraft:music.nether.basalt_deltas" -> {
                    if (!config.netherConfig.enabled) cir.setReturnValue(null);
                    volume = config.netherConfig.volume;
                }
            }
            Random random = new Random();
            long note = random.nextLong((config.mainConfig.maxNoteChange - config.mainConfig.minNoteChange) + 1) + config.mainConfig.minNoteChange;
            cir.setReturnValue(new PositionedSoundInstance(sound.getId(), SoundCategory.MUSIC, volume / 100, (float) Math.pow(2.0D, (double) (note) / 12.0D), SoundInstance.createRandom(), false, 0, SoundInstance.AttenuationType.NONE, 0.0D, 0.0D, 0.0D, false));
        }
    }

    @Inject(method = "record", at = @At("RETURN"), cancellable = true)
    private static void recordMixin(SoundEvent sound, double x, double y, double z, CallbackInfoReturnable<PositionedSoundInstance> cir) {
        ModConfig config = AutoConfig.getConfigHolder(ModConfig.class).getConfig();
        if(config.mainConfig.enabled) {
            if(config.jukeboxConfig.dogReplacesCat && sound.getId().toString().equals("minecraft:music_disc.cat")) {
                sound = new SoundEvent(new Identifier("mixtape:music.dog"));
            } else if(config.jukeboxConfig.elevenReplaces11 && sound.getId().toString().equals("minecraft:music_disc.11")) {
                sound = new SoundEvent(new Identifier("mixtape:music.eleven"));
            }
            cir.setReturnValue(new PositionedSoundInstance(sound.getId(), SoundCategory.RECORDS, config.jukeboxConfig.volume / 100, 1.0F, SoundInstance.createRandom(), false, 0, SoundInstance.AttenuationType.LINEAR, x, y, z, config.jukeboxConfig.mono));
        }
    }
}
