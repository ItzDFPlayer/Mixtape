package gay.aliahx.mixtape.gui.SongController;

import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.gui.controllers.string.StringController;

public class SongController extends StringController implements ISongController<String> {
    public SongController(Option<String> option) {
        super(option);
    }
}