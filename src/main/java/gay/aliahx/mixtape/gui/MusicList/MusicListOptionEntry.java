package gay.aliahx.mixtape.gui.MusicList;

import com.google.common.collect.ImmutableSet;
import dev.isxander.yacl3.api.ListOptionEntry;
import dev.isxander.yacl3.api.OptionFlag;
import org.jetbrains.annotations.NotNull;

public interface MusicListOptionEntry <T> extends ListOptionEntry<T> {
    MusicListOption<T> parentGroup();

    @Override
    default @NotNull ImmutableSet<OptionFlag> flags() {
        return parentGroup().flags();
    }

    @Override
    default boolean available() {
        return parentGroup().available();
    }
}
