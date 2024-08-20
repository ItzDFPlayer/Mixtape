package gay.aliahx.mixtape.gui.SongController;


import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.impl.controller.AbstractControllerBuilderImpl;

public class SongControllerBuilderImpl extends AbstractControllerBuilderImpl<String> implements SongControllerBuilder {
    public SongControllerBuilderImpl(Option<String> option) {
        super(option);
    }

    @Override
    public Controller<String> build() {
        return new SongController(option);
    }
}
