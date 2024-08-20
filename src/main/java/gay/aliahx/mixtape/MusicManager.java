package gay.aliahx.mixtape;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;

import java.util.List;
import java.util.Map;

import static gay.aliahx.mixtape.Mixtape.client;
import static gay.aliahx.mixtape.Mixtape.config;

public class MusicManager {
    public Map<String, Entry> music;
    public static Map<String, JsonElement> albums;

    public static final Identifier ALBUM_COVERS = new Identifier(Mixtape.MOD_ID, "textures/gui/album_covers.png");

    public MusicManager(JsonObject[] jsonArray) {
        music = Maps.newHashMap();
        albums = Maps.newHashMap();

        for (Map.Entry<String, JsonElement> entry : jsonArray[0].entrySet()) {
            music.put(entry.getKey(), new Entry((JsonObject) entry.getValue()));
        }

        for (Map.Entry<String, JsonElement> entry : jsonArray[1].entrySet()) {
            albums.put(entry.getKey(), entry.getValue());
        }
    }

    public Entry getEntry(String name) {
        if(music.containsKey(name)) {
            return music.get(name);
        } else {
            JsonObject object = new JsonObject();
            String nameP1 = name.substring(0, 1).toUpperCase();
            String nameP2 = name.substring(1);
            object.addProperty("name", (nameP1 + nameP2).replaceAll("_", " "));
            object.addProperty("artist", "");
            object.addProperty("album", "Unknown Album");
            object.addProperty("identifier", "");
            return new Entry(object);
        }
    }

    public static class Entry {
        private final String name;
        private final String artist;
        private final String album;
        private final Identifier identifier;

        public Entry(JsonObject json) {
            this.name = JsonHelper.getString(json, "name");
            this.artist = JsonHelper.getString(json, "artist");
            this.album = JsonHelper.getString(json, "album");
            if(JsonHelper.hasElement(json, "identifier")) {
                if (JsonHelper.getString(json, "identifier") == "") {
//                    System.out.println("Missing identifier for " + name);
                }
                this.identifier = new Identifier(JsonHelper.getString(json, "identifier"));
            } else {
                this.identifier = new Identifier("");
//                System.out.println("Missing identifier for " + name);
            }
        }

        public String getName() {
            return name;
        }
        public String getArtist() {
            return artist;
        }
        public String getAlbum() {
            return album;
        }
        public Identifier getIdentifier() {
            return identifier;
        }
    }

    public static class AlbumCover {
        private final int x;
        private final int y;

        public AlbumCover(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public void drawIcon(DrawContext context, int x, int y) {
            context.drawTexture(ALBUM_COVERS, x, y, 0, this.x * 20, this.y * 20, 20, 20, 100, 100);
        }
    }

    public static AlbumCover getAlbumCover(String albumString) {
        if(albums.containsKey(albumString)) {
            JsonObject object = albums.get(albumString).getAsJsonObject();
            return new AlbumCover(object.get("x").getAsInt(), object.get("y").getAsInt());
        }
        return new AlbumCover(0, 0);
    }

    public static SoundEvent getRandomSoundEvent(String musicType) {
        String song;
        List<String> songs = switch(musicType) {
            case "minecraft:music.menu" -> config.menu.songs;
            case "minecraft:music.creative" -> config.creative.songs;
            case "minecraft:music.end" -> config.end.endSongs;
            case "minecraft:music.dragon" -> config.end.dragonSongs;
            case "minecraft:music.under_water" -> config.underwater.songs;
            case "minecraft:music.credits" -> config.credits.songs;
            case "minecraft:music.game" -> config.game.songs;
            default -> {
                if(musicType.contains("overworld")) {
                    if(!config.game.specificOverworldMusic) {
                        yield config.game.songs;
                    }
                    String zone = musicType.substring(26);
                    String configListString = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, zone) + "Songs";
                    try {
                        yield (List<String>) config.game.getClass().getField(configListString).get(config.game);
                    } catch (Exception ignored) {}
                } else if(musicType.contains("nether")) {
                    if(!config.nether.specificNetherMusic) {
                        yield config.nether.songs;
                    }
                    String zone = musicType.substring(23);
                    String configListString = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, zone) + "Songs";
                    try {
                        yield (List<String>) config.nether.getClass().getField(configListString).get(config.nether);
                    } catch (Exception ignored) {}
                }

                Mixtape.LOGGER.error("Unknown music type: " + musicType);
                yield config.game.songs;
            }
        };

        if(songs.size() == 0) return SoundEvents.INTENTIONALLY_EMPTY;

        song = songs.get((int) (Math.random() * songs.size()));
        SoundEvent test = SoundEvent.of(Mixtape.musicManager.getEntry(song).getIdentifier());
        System.out.println(test + " :3");
        return test;
    }
}
