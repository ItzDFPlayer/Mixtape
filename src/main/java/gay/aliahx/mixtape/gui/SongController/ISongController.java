package gay.aliahx.mixtape.gui.SongController;

import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.AbstractWidget;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.string.IStringController;

public interface ISongController <T> extends IStringController<T> {
    @Override
    default AbstractWidget provideWidget(YACLScreen screen, Dimension<Integer> widgetDimension) {
        return new SongControllerElement(this, screen, widgetDimension, true);
    }
}
