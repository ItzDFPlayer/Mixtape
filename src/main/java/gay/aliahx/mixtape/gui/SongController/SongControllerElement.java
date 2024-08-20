package gay.aliahx.mixtape.gui.SongController;

import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.OptionListWidget;
import dev.isxander.yacl3.gui.TooltipButtonWidget;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.tab.ListHolderWidget;
import dev.isxander.yacl3.gui.utils.GuiUtils;
import gay.aliahx.mixtape.Mixtape;
import gay.aliahx.mixtape.MusicManager;
import gay.aliahx.mixtape.gui.MusicList.MusicListEntryWidget;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;

import java.util.Objects;

public class SongControllerElement extends NamelessControllerWidget<ISongController<?>> {
    private final TooltipButtonWidget listenButton, favouriteButton;
    private final SoundInstance soundInstance;
    private boolean listening, favourite;

    protected final boolean instantApply;

    protected String inputField;
    protected Dimension<Integer> inputFieldBounds;
    protected boolean inputFieldFocused;

    protected int renderOffset;

    private final Text emptyText;

    public SongControllerElement(ISongController<?> control, YACLScreen screen, Dimension<Integer> dim, boolean instantApply) {
        super(control, screen, dim);
        this.instantApply = instantApply;
        inputField = control.getString();
        inputFieldFocused = false;
        emptyText = Text.literal("Click to select...").formatted(Formatting.GRAY);
        control.option().addListener((opt, val) -> inputField = control.getString());
        setDimension(dim);
        soundInstance = PositionedSoundInstance.master(SoundEvent.of(Mixtape.musicManager.getEntry(inputField).getIdentifier()), 1.0F);

        listenButton = new TooltipButtonWidget(screen, dim.x(), dim.y(), 20, 20, Text.literal("▶"), Text.translatable("config.mixtape.musicList.listen"), btn -> {
            playDownSound();
            boolean temp = !listening;
            if(temp) {
                StopAllSongs(screen.tabManager);
                Mixtape.previewingSong = true;
                Mixtape.soundManager.play(soundInstance);
            } else {
                stopSong();
            }
            listening = temp;
        });

        favouriteButton = new TooltipButtonWidget(screen, dim.x() + 20, dim.y(), 20, 20, Text.literal("♡"), Text.translatable("config.mixtape.musicList.favourite"), btn -> {
            playDownSound();
            favourite = !favourite;
            if(favourite) {
                Mixtape.config.main.favouriteSongs.add(inputField);
            } else {
                Mixtape.config.main.favouriteSongs.remove(inputField);
            }
            Mixtape.config.save();
        });
    }

    public void stopSong() {
        Mixtape.previewingSong = false;
        listening = false;
        Mixtape.soundManager.stop(soundInstance);
    }

    public boolean isListening() {
        return listening;
    }
    public boolean isFavourite() {
        return favourite;
    }


    @Override
    protected void drawValueText(DrawContext graphics, int mouseX, int mouseY, float delta) {
        Text valueText = getValueText();

        if (!isHovered()) valueText = Text.literal(GuiUtils.shortenString(valueText.getString(), textRenderer, getDimension().width() - 50, "...")).setStyle(valueText.getStyle());

        int textX = getDimension().xLimit() - textRenderer.getWidth(valueText) + renderOffset - getXPadding();

        drawScrollableText(graphics, textRenderer, valueText, Math.max(textX, getDimension().x() + 45), getTextY() - 4, inputFieldBounds.xLimit() - 1, inputFieldBounds.yLimit() + 4, getValueColor());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (isAvailable() && getDimension().isPointInside((int) mouseX, (int) mouseY)) {
            if(listenButton.isHovered()) {
                listenButton.onPress();
            } else if (favouriteButton.isHovered()) {
                favouriteButton.onPress();
            } else {
                if (inputFieldBounds.isPointInside((int) mouseX, (int) mouseY)) {
//                    setFocused(true);
                }
            }

            return true;
        } else {
            inputFieldFocused = false;
        }

        return false;
    }

    @Override
    public void setFocused(boolean focused) {
        super.setFocused(focused);
        inputFieldFocused = focused;
    }

    @Override
    public void unfocus() {
        super.unfocus();
        inputFieldFocused = false;
        renderOffset = 0;
        if (!instantApply) updateControl();
    }

    @Override
    public void setDimension(Dimension<Integer> dim) {
        super.setDimension(dim);

        Text valueText = getValueText();
        int width = Math.max(6, Math.min(textRenderer.getWidth(valueText), getDimension().width() - 9));
        inputFieldBounds = Dimension.ofInt(dim.xLimit() - getXPadding() - width, dim.centerY() - textRenderer.fontHeight / 2, width, textRenderer.fontHeight);
    }

    @Override
    public boolean isHovered() {
        return super.isHovered() || inputFieldFocused;
    }

    protected void updateControl() {
        control.setFromString(inputField);
    }

    @Override
    protected Text getValueText() {
        if (!inputFieldFocused && inputField.isEmpty())
            return emptyText;

        MusicManager.Entry entry = Mixtape.musicManager.getEntry(inputField);
        return Objects.equals(inputField, "") ? Text.literal(inputField) : Text.literal((Objects.equals(entry.getArtist(), "") ?  "" : entry.getArtist() + " - ") + entry.getName());
    }

    protected static void drawScrollableText(DrawContext context, TextRenderer textRenderer, Text text, int left, int top, int right, int bottom, int color) {
        int i = textRenderer.getWidth(text);
        int var10000 = top + bottom;
        Objects.requireNonNull(textRenderer);
        int j = (var10000 - 9) / 2 + 1;
        int k = right - left;
        if (i > k) {
            int l = i - k;
            double d = (double) Util.getMeasuringTimeMs() / 1000.0;
            double e = Math.max((double)l * 0.5, 3.0);
            double f = Math.sin(1.5707963267948966 * Math.cos(6.283185307179586 * d / e)) / 2.0 + 0.5;
            double g = MathHelper.lerp(f, 0.0, l);
            context.enableScissor(left, top, right + 1, bottom);
            context.drawTextWithShadow(textRenderer, text, left - (int)g, j, color);
            context.disableScissor();
        } else {
            context.drawCenteredTextWithShadow(textRenderer, text, (left + right) / 2, j, color);
        }
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
        hovered = isMouseOver(mouseX, mouseY);
        favourite = Mixtape.config.main.favouriteSongs.contains(control.getString());
        listening = listening && Mixtape.soundManager.isPlaying(soundInstance);

        listenButton.setY(getDimension().y());
        favouriteButton.setY(getDimension().y());

        listenButton.render(graphics, mouseX, mouseY, delta);
        favouriteButton.render(graphics, mouseX, mouseY, delta);

        drawButtonRect(graphics, getDimension().x() + 40, getDimension().y(), getDimension().xLimit(), getDimension().yLimit(), isHovered() && (mouseX - getDimension().x() > 40), isAvailable());
        drawValueText(graphics, mouseX, mouseY, delta);

        updateButtonStates(graphics);
    }

    protected void updateButtonStates(DrawContext graphics) {
        favouriteButton.drawMessage(graphics, textRenderer, isFavourite() || favouriteButton.isHovered() ? 16777045 : 16777215);
        listenButton.setMessage(Text.literal(isListening() ? "⏸" :"▶"));
        listenButton.setTooltip(Tooltip.of(Text.translatable("config.mixtape.songController." + (isListening() ? "stop" : "listen"))));
        favouriteButton.setMessage(Text.literal(isFavourite() ? "★" :"☆"));
        favouriteButton.setTooltip(Tooltip.of(Text.translatable("config.mixtape.songController." + (isFavourite() ? "unFavourite" : "favourite"))));
    }

    public static void StopAllSongs(TabManager tabManager) {
        if(tabManager.getCurrentTab() != null) {
            tabManager.getCurrentTab().forEachChild(child -> {
                if (child instanceof ListHolderWidget<?>) {
                    OptionListWidget child2 = (OptionListWidget) ((ListHolderWidget<?>) child).children().get(0);
                    child2.children().forEach(child3 -> child3.children().forEach(child4 -> {
                        if (child4 instanceof MusicListEntryWidget) {
                            SongControllerElement element = (SongControllerElement) ((MusicListEntryWidget) child4).children().get(0);
                            element.stopSong();
                        }
                    }));
                }
            });
        }
    }
}
