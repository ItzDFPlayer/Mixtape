package gay.aliahx.mixtape.gui.MusicList;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.AbstractWidget;
import dev.isxander.yacl3.gui.YACLScreen;
import net.minecraft.text.Text;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;
import java.util.function.Function;

@ApiStatus.Internal
public final class MusicListOptionEntryImpl<T> implements MusicListOptionEntry<T> {
    private final MusicListOptionImpl<T> group;

    private T value;

    private final Binding<T> binding;
    private final Controller<T> controller;

    MusicListOptionEntryImpl(MusicListOptionImpl<T> group, T initialValue, @NotNull Function<MusicListOptionEntry<T>, Controller<T>> controlGetter) {
        this.group = group;
        this.value = initialValue;
        this.binding = new EntryBinding();
        this.controller = new EntryController<>(controlGetter.apply(this), this);
    }

    @Override
    public @NotNull Text name() {
        return group.name();
    }

    @Override
    public @NotNull OptionDescription description() {
        return group.description();
    }

    @Override
    public @NotNull Text tooltip() {
        return Text.empty();
    }

    @Override
    public @NotNull Controller<T> controller() {
        return controller;
    }

    @Override
    public @NotNull Binding<T> binding() {
        return binding;
    }

    @Override
    public boolean available() {
        return parentGroup().available();
    }

    @Override
    public void setAvailable(boolean available) {

    }

    @Override
    public MusicListOption<T> parentGroup() {
        return group;
    }

    @Override
    public boolean changed() {
        return false;
    }

    @Override
    public @NotNull T pendingValue() {
        return value;
    }

    @Override
    public void requestSet(T value) {
        binding.setValue(value);
    }

    @Override
    public boolean applyValue() {
        return false;
    }

    @Override
    public void forgetPendingValue() {

    }

    @Override
    public void requestSetDefault() {

    }

    @Override
    public boolean isPendingValueDefault() {
        return false;
    }

    @Override
    public boolean canResetToDefault() {
        return false;
    }

    @Override
    public void addListener(BiConsumer<Option<T>, T> changedListener) {

    }

    /**
     * Open in case mods need to find the real controller type.
     */
    @ApiStatus.Internal
    public record EntryController<T>(Controller<T> controller, MusicListOptionEntryImpl<T> entry) implements Controller<T> {
        @Override
        public Option<T> option() {
            return controller.option();
        }

        @Override
        public Text formatValue() {
            return controller.formatValue();
        }

        @Override
        public AbstractWidget provideWidget(YACLScreen screen, Dimension<Integer> widgetDimension) {
            return new MusicListEntryWidget(screen, entry, controller.provideWidget(screen, widgetDimension));
        }
    }

    private class EntryBinding implements Binding<T> {
        @Override
        public void setValue(T newValue) {
            value = newValue;
            group.callListeners();
        }

        @Override
        public T getValue() {
            return value;
        }

        @Override
        public T defaultValue() {
            throw new UnsupportedOperationException();
        }
    }
}
