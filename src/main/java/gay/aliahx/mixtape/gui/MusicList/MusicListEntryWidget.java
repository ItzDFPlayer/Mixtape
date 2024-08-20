package gay.aliahx.mixtape.gui.MusicList;

import com.google.common.collect.ImmutableList;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.AbstractWidget;
import dev.isxander.yacl3.gui.TooltipButtonWidget;
import dev.isxander.yacl3.gui.YACLScreen;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.text.Text;

public class MusicListEntryWidget extends AbstractWidget implements ParentElement {
    private final TooltipButtonWidget removeButton;
    private final AbstractWidget entryWidget;

    private final MusicListOption<?> musicListOption;
    private final MusicListOptionEntry<?> musicListOptionEntry;

    private final String optionNameString;

    private Element focused;
    private boolean dragging;

    public MusicListEntryWidget(YACLScreen screen, MusicListOptionEntry<?> musicListOptionEntry, AbstractWidget entryWidget) {
        super(entryWidget.getDimension().withHeight(Math.max(entryWidget.getDimension().height(), 20) - ((musicListOptionEntry.parentGroup().indexOf(musicListOptionEntry) == musicListOptionEntry.parentGroup().options().size() - 1) ? 0 : 2))); // -2 to remove the padding
        this.musicListOptionEntry = musicListOptionEntry;
        this.musicListOption = musicListOptionEntry.parentGroup();
        this.optionNameString = musicListOptionEntry.name().getString().toLowerCase();
        this.entryWidget = entryWidget;

        Dimension<Integer> dim = entryWidget.getDimension();
        entryWidget.setDimension(dim.clone().expand(-20, 0));

        removeButton = new TooltipButtonWidget(screen, dim.xLimit() - 20, dim.y(), 20, 20, Text.literal("❌"), Text.translatable("yacl.list.remove"), btn -> {
            musicListOption.removeEntry(musicListOptionEntry);
            updateButtonStates();
        });

        updateButtonStates();
    }

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
        updateButtonStates(); // update every render in case option becomes available/unavailable

        removeButton.setY(getDimension().y());
        entryWidget.setDimension(entryWidget.getDimension().withY(getDimension().y()));

        removeButton.render(graphics, mouseX, mouseY, delta);
        entryWidget.render(graphics, mouseX, mouseY, delta);
    }

    protected void updateButtonStates() {
        removeButton.active = musicListOption.available();
    }

    @Override
    public void unfocus() {
        entryWidget.unfocus();
    }

    @Override
    public void appendNarrations(NarrationMessageBuilder builder) {
        entryWidget.appendNarrations(builder);
    }

    @Override
    public boolean matchesSearch(String query) {
        return optionNameString.contains(query.toLowerCase());
    }

    @Override
    public List<? extends Element> children() {
        return ImmutableList.of(entryWidget, removeButton);
    }

    @Override
    public boolean isDragging() {
        return dragging;
    }

    @Override
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    @Nullable
    @Override
    public Element getFocused() {
        return focused;
    }

    @Override
    public void setFocused(@Nullable Element focused) {
        this.focused = focused;
    }
}
