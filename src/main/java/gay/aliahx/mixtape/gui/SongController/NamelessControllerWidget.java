package gay.aliahx.mixtape.gui.SongController;

import dev.isxander.yacl3.api.Controller;
import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.ControllerWidget;
import net.minecraft.client.gui.DrawContext;

public class NamelessControllerWidget<T extends Controller<?>> extends ControllerWidget {
    protected final T control;

    public NamelessControllerWidget(T control, YACLScreen screen, Dimension<Integer> dim) {
        super(control, screen, dim);
        this.control = control;
    }

    @Override
    protected int getHoveredControlWidth() {return 0;}

    @Override
    public void render(DrawContext graphics, int mouseX, int mouseY, float delta) {
        hovered = isMouseOver(mouseX, mouseY);
        drawButtonRect(graphics, getDimension().x(), getDimension().y(), getDimension().xLimit(), getDimension().yLimit(), isHovered(), isAvailable());

        drawValueText(graphics, mouseX, mouseY, delta);
        if (isHovered()) {
            drawHoveredControl(graphics, mouseX, mouseY, delta);
        }
    }
}
