package gay.aliahx.mixtape.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.api.NameableEnum;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ModConfig {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final File CONFIG_FILE = FabricLoader.getInstance().getConfigDir().resolve("mixtape.json").toFile();
    public static ModConfig INSTANCE = loadConfigFile(CONFIG_FILE);

    public enum SongLocation implements NameableEnum {
        OPTIONS_SCREEN,
        PAUSE_SCREEN;
        @Override
        public Text getDisplayName() {
            return Text.translatable("config.mixtape.songLocation." + this.name().toLowerCase());
        }
    }

    public enum MusicType implements NameableEnum {
        AUTOMATIC,
        CREATIVE,
        CREDITS,
        DRAGON,
        END,
        GAME,
        MENU,
        NETHER_BASALT_DELTAS,
        NETHER_CRIMSON_FOREST,
        OVERWORLD_DEEP_DARK,
        OVERWORLD_DRIPSTONE_CAVES,
        OVERWORLD_GROVE,
        OVERWORLD_JAGGED_PEAKS,
        OVERWORLD_LUSH_CAVES,
        OVERWORLD_SWAMP,
        OVERWORLD_FOREST,
        OVERWORLD_OLD_GROWTH_TAIGA,
        OVERWORLD_MEADOW,
        OVERWORLD_CHERRY_GROVE,
        NETHER_NETHER_WASTES,
        OVERWORLD_FROZEN_PEAKS,
        OVERWORLD_SNOWY_SLOPES,
        NETHER_SOUL_SAND_VALLEY,
        OVERWORLD_STONY_PEAKS,
        NETHER_WARPED_FOREST,
        OVERWORLD_FLOWER_FOREST,
        OVERWORLD_DESERT,
        OVERWORLD_BADLANDS,
        OVERWORLD_JUNGLE,
        OVERWORLD_SPARSE_JUNGLE,
        OVERWORLD_BAMBOO_JUNGLE,
        UNDER_WATER;

        @Override
        public Text getDisplayName() {
            return Text.translatable("config.mixtape.musicType." + this.name().toLowerCase());
        }
    }
    public MainConfig main = new MainConfig();
    public MusicToastConfig musicToast = new MusicToastConfig();
    public MenuConfig menu = new MenuConfig();
    public CreativeConfig creative = new CreativeConfig();
    public GameConfig game = new GameConfig();
    public UnderwaterConfig underwater = new UnderwaterConfig();
    public EndConfig end = new EndConfig();
    public NetherConfig nether = new NetherConfig();
    public CreditsConfig credits = new CreditsConfig();
    public JukeboxConfig jukebox = new JukeboxConfig();

    public static class MainConfig {
        public boolean enabled = true;
        public boolean varyPitch = false;
        public long maxNoteChange = 3;
        public long minNoteChange = -3;
        public boolean noDelayBetweenSongs = false;
        public boolean playKeybindReplacesCurrentSong = false;
        public boolean skipKeybindStartsNextSong = false;
        public MusicType musicType = MusicType.AUTOMATIC;
        public boolean pauseMusicWhenGamePaused = true;
        public boolean stopMusicWhenSwitchingDimensions = true;
        public boolean stopMusicWhenLeftGame = true;
        public boolean enableDebugInfo = true;
        public boolean avoidRepetition = false;
        public double songListPercent = 50;
        public boolean showCurrentSong = true;
        public SongLocation songLocation = SongLocation.PAUSE_SCREEN;
        public List<String> favouriteSongs = List.of();
        public boolean hideUpdateBadge = false;
    }

    public static class MusicToastConfig {
        public boolean enabled = true;
        public boolean showArtistName = true;
        public boolean showAlbumName = true;
        public boolean showAlbumCover = true;
        public boolean useDiscItemAsAlbumCover = false;
        public boolean hideJukeboxHotbarMessage = true;
        public boolean useHotbarInsteadOfToast = false;
        public boolean toastMakesSound = false;
        public boolean remainForFullSong = false;
        public int toastDisplayTime = 7500;
    }

    public static class MenuConfig {
        public boolean enabled = true;
        public int minSongDelay = 20;
        public int maxSongDelay = 600;
        public float volume = 100;
        public List<String> songs = List.of(
            "mutation",
            "moog_city_2",
            "beginning_2",
            "floating_trees",
            "a_familiar_room",
            "bromeliad",
            "crescent_dunes",
            "echo_in_the_wind"
        );
    }

    public static class CreativeConfig {
        public boolean enabled = true;
        public int minSongDelay = 12000;
        public int maxSongDelay = 24000;
        public float volume = 100;
        public List<String> songs = List.of(
            "a_familiar_room",
            "minecraft",
            "clark",
            "sweden",
            "comforting_memories",
            "floating_dream",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "key",
            "oxygene",
            "one_more_day",
            "dry_hands",
            "wet_hands",
            "mice_on_venus",
            "biome_fest",
            "blind_spots",
            "haunt_muskie",
            "aria_math",
            "dreiton",
            "taswell"
        );
    }

    public static class GameConfig {
        public boolean enabled = true;
        public int minSongDelay = 12000;
        public int maxSongDelay = 24000;
        public float volume = 100;
        public boolean specificOverworldMusic = true;
        public List<String> songs = List.of(
            "a_familiar_room",
            "minecraft",
            "clark",
            "sweden",
            "comforting_memories",
            "floating_dream",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "key",
            "oxygene",
            "one_more_day",
            "dry_hands",
            "wet_hands",
            "mice_on_venus",
            "crescent_dunes",
            "echo_in_the_wind",
            "bromeliad",
            "infinite_amethyst",
            "wending",
            "aerie",
            "firebugs",
            "labyrinthine",
            "stand_tall"
        );

        public List<String> gameSongs = List.of(
            "a_familiar_room",
            "minecraft",
            "clark",
            "sweden",
            "comforting_memories",
            "floating_dream",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "key",
            "oxygene",
            "one_more_day",
            "dry_hands",
            "wet_hands",
            "mice_on_venus"
        );

        public List<String> badlandsSongs = List.of(
            "crescent_dunes",
            "echo_in_the_wind",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "key",
            "oxygene",
            "one_more_day",
            "dry_hands",
            "wet_hands",
            "mice_on_venus"
        );

        public List<String> bambooJungleSongs = List.of(
            "bromeliad",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "key",
            "oxygene",
            "one_more_day",
            "dry_hands",
            "wet_hands",
            "mice_on_venus"
        );

        public List<String> cherryGroveSongs = List.of(
            "bromeliad",
            "minecraft",
            "clark",
            "sweden",
            "echo_in_the_wind",
            "left_to_bloom"
        );

        public List<String> deepDarkSongs = List.of("ancestry");

        public List<String> desertSongs = List.of(
            "crescent_dunes",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "key",
            "oxygene",
            "one_more_day"
        );

        public List<String> dripstoneCavesSongs = List.of(
            "an_ordinary_day",
            "subwoofer_lullaby",
            "danny",
            "infinite_amethyst",
            "key",
            "oxygene",
            "wending"
        );

        public List<String> flowerForestSongs = List.of(
            "bromeliad",
            "echo_in_the_wind",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "key",
            "oxygene"
        );

        public List<String> forestSongs = List.of(
    "bromeliad",
            "minecraft",
            "clark",
            "sweden",
            "comforting_memories",
            "floating_dream",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "key",
            "oxygene",
            "one_more_day",
            "dry_hands",
            "wet_hands",
            "mice_on_venus",
            "aerie",
            "firebugs",
            "labyrinthine"
        );

        public List<String> frozenPeaksSongs = List.of(
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "dry_hands",
            "wet_hands",
            "mice_on_venus",
            "stand_tall"
        );

        public List<String> groveSongs = List.of(
            "minecraft",
            "clark",
            "sweden",
            "comforting_memories",
            "infinite_amethyst",
            "key",
            "oxygene",
            "mice_on_venus",
            "wending"
        );

        public List<String> jaggedPeaksSongs = List.of(
            "floating_dream",
            "subwoofer_lullaby",
            "living_mice",
            "key",
            "oxygene",
            "dry_hands",
            "wet_hands",
            "mice_on_venus",
            "stand_tall",
            "wending"
        );

        public List<String> jungleSongs = List.of(
            "bromeliad",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "key",
            "oxygene",
            "dry_hands",
            "wet_hands",
            "mice_on_venus"
        );

        public List<String> lushCavesSongs = List.of(
            "an_ordinary_day",
            "minecraft",
            "clark",
            "sweden",
            "echo_in_the_wind",
            "floating_dream",
            "one_more_day",
            "mice_on_venus",
            "aerie",
            "firebugs",
            "labyrinthine"
        );

        public List<String> meadowSongs = List.of(
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "one_more_day"
        );

        public List<String> oldGrowthTaigaSongs = List.of(
            "minecraft",
            "clark",
            "sweden",
            "comforting_memories",
            "floating_dream",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "key",
            "oxygene",
            "one_more_day",
            "dry_hands",
            "wet_hands",
            "mice_on_venus",
            "aerie",
            "firebugs",
            "labyrinthine"
        );

        public List<String> snowySlopesSongs = List.of(
            "an_ordinary_day",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "one_more_day",
            "stand_tall"
        );

        public List<String> sparseJungleSongs = List.of(
            "bromeliad",
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "left_to_bloom",
            "key",
            "oxygene",
            "dry_hands",
            "wet_hands",
            "mice_on_venus"
        );

        public List<String> stonyPeaksSongs = List.of(
            "subwoofer_lullaby",
            "living_mice",
            "haggstrom",
            "danny",
            "dry_hands",
            "wet_hands",
            "mice_on_venus",
            "stand_tall",
            "wending"
        );

        public List<String> swampSongs = List.of(
            "aerie",
            "firebugs",
            "labyrinthine"
        );
    }

    public static class UnderwaterConfig {
        public boolean enabled = true;
        public int minSongDelay = 12000;
        public int maxSongDelay = 24000;
        public float volume = 100;
        public List<String> songs = List.of(
                "axolotl",
                "dragon_fish",
                "shuniji"
        );
    }

    public static class EndConfig {
        public boolean enabled = true;
        public int minSongDelay = 6000;
        public int maxSongDelay = 24000;
        public float volume = 100;
        public List<String> endSongs = List.of("the_end");
        public List<String> dragonSongs = List.of("boss");
    }

    public static class NetherConfig {
        public boolean enabled = true;
        public int minSongDelay = 12000;
        public int maxSongDelay = 24000;
        public float volume = 100;
        public boolean specificNetherMusic = true;
        public List<String> songs = List.of(
            "concrete_halls",
            "dead_voxel",
            "warmth",
            "ballad_of_the_cats",
            "so_below",
            "chrysopoeia",
            "rubedo"
        );
        public List<String> basaltDeltasSongs = List.of(
            "concrete_halls",
            "dead_voxel",
            "warmth",
            "ballad_of_the_cats",
            "so_below"
        );
        public List<String> crimsonForestSongs = List.of(
            "chrysopoeia",
            "concrete_halls",
            "dead_voxel",
            "warmth",
            "ballad_of_the_cats"
        );
        public List<String> netherWastesSongs = List.of(
            "concrete_halls",
            "dead_voxel",
            "warmth",
            "ballad_of_the_cats",
            "rubedo"
        );
        public List<String> soulSandValleySongs = List.of(
            "concrete_halls",
            "dead_voxel",
            "warmth",
            "ballad_of_the_cats",
            "so_below"
        );
        public List<String> warpedForestSongs = List.of();
    }

    public static class CreditsConfig {
        public boolean enabled = true;
        public float volume = 100;
        public List<String> songs = List.of("alpha");
    }

    public static class JukeboxConfig {
        public boolean mono = false;
        public float distance = 56;
        public boolean elevenReplaces11 = false;
        public boolean dogReplacesCat = false;
        public boolean droopyLikesYourFaceReplacesWard = false;
        public boolean turnDownMusic = true;
        public float volume = 400;
    }

    public static Object getCategoryDefaults(String category) {
        return switch(category) {
            case "main" -> new MainConfig();
            case "musicToast" -> new MusicToastConfig();
            case "menu" -> new MenuConfig();
            case "creative" -> new CreativeConfig();
            case "game" -> new GameConfig();
            case "underwater" -> new UnderwaterConfig();
            case "end" -> new EndConfig();
            case "nether" -> new NetherConfig();
            case "credits" -> new CreditsConfig();
            case "jukebox" -> new JukeboxConfig();
            default -> throw new IllegalStateException("Unexpected value: " + category);
        };
    }

    public static Object getCategory(String category) {
        return switch (category) {
            case "main" -> ModConfig.INSTANCE.main;
            case "musicToast" -> ModConfig.INSTANCE.musicToast;
            case "menu" -> ModConfig.INSTANCE.menu;
            case "creative" -> ModConfig.INSTANCE.creative;
            case "game" -> ModConfig.INSTANCE.game;
            case "underwater" -> ModConfig.INSTANCE.underwater;
            case "end" -> ModConfig.INSTANCE.end;
            case "nether" -> ModConfig.INSTANCE.nether;
            case "credits" -> ModConfig.INSTANCE.credits;
            case "jukebox" -> ModConfig.INSTANCE.jukebox;
            default -> null;
        };
    }

    public void save() {
        saveConfigFile(CONFIG_FILE);
    }

    private static ModConfig loadConfigFile(File file) {
        ModConfig config = null;

        if (file.exists()) {
            try (BufferedReader fileReader = new BufferedReader( new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                config = GSON.fromJson(fileReader, ModConfig.class);
            } catch (IOException e) {
                throw new RuntimeException("Problem occurred when trying to load config: ", e);
            }
        }

        if (config == null) {
            config = new ModConfig();
        }

        config.saveConfigFile(file);
        return config;
    }

    private void saveConfigFile(File file) {
        try (Writer writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}