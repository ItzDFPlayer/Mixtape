package gay.aliahx.mixtape.gui.SongController;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.controller.ControllerBuilder;

public interface SongControllerBuilder extends ControllerBuilder<String> {
    static SongControllerBuilder create(Option<String> option) {
        return new SongControllerBuilderImpl(option);
    }
}
