package gay.aliahx.mixtape.gui.MusicList;

import com.google.common.collect.ImmutableList;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.ControllerBuilder;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.text.Text;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.ApiStatus.Internal;

public interface MusicListOption<T> extends ListOption<T> {
    @NotNull ImmutableList<ListOptionEntry<T>> options();

    @Internal
    int numberOfEntries();

    @Internal
    int maximumNumberOfEntries();

    @Internal
    int minimumNumberOfEntries();

    @Internal
    ListOptionEntry<T> insertNewEntryToTop();

    @Internal
    void insertEntry(int var1, MusicListOptionEntry<?> var2);

    @Internal
    int indexOf(MusicListOptionEntry<?> var1);

    @Internal
    void removeEntry(MusicListOptionEntry<?> var1);

    static <T> MusicListOption.Builder<T> createBuilder() {
        return new MusicListOptionImpl.BuilderImpl();
    }

    static <T> MusicListOption.Builder<T> createBuilder(Class<T> typeClass) {
        return createBuilder();
    }

    boolean adding();

    void setAdding(boolean adding);

    interface Builder<T> {
        Builder<T> name(@NotNull Text var1);

        Builder<T> description(@NotNull OptionDescription var1);

        Builder<T> initial(@NotNull T var1);

        Builder<T> controller(@NotNull Function<Option<T>, ControllerBuilder<T>> var1);

        Builder<T> customController(@NotNull Function<MusicListOptionEntry<T>, Controller<T>> var1);

        Builder<T> binding(@NotNull Binding<List<T>> var1);

        Builder<T> binding(@NotNull List<T> var1, @NotNull Supplier<@NotNull List<T>> var2, @NotNull Consumer<@NotNull List<T>> var3);

        Builder<T> available(boolean var1);

        Builder<T> minimumNumberOfEntries(int var1);

        Builder<T> maximumNumberOfEntries(int var1);

        Builder<T> collapsed(boolean var1);

        MusicListOption<T> build();
    }
}
