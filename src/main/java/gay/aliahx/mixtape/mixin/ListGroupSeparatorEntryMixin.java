package gay.aliahx.mixtape.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.isxander.yacl3.api.ListOption;
import dev.isxander.yacl3.gui.OptionListWidget;
import dev.isxander.yacl3.gui.TextScaledButtonWidget;
import dev.isxander.yacl3.gui.TooltipButtonWidget;
import gay.aliahx.mixtape.gui.MusicList.MusicListOption;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

import static net.minecraft.util.math.MathHelper.clamp;

@Mixin(OptionListWidget.ListGroupSeparatorEntry.class)
public abstract class ListGroupSeparatorEntryMixin {

    @Mutable
    @Shadow @Final private TooltipButtonWidget addListButton;
    @Shadow @Final private TextScaledButtonWidget resetListButton;

    @Unique
    private TextFieldWidget editBox;
//    @Unique
//    private TooltipButtonWidget c418Button;
//    @Unique
//    private TooltipButtonWidget aaronButton;
//    @Unique
//    private TooltipButtonWidget kumiButton;
//    @Unique
//    private TooltipButtonWidget lenaButton;
    @Unique
    private TooltipButtonWidget closeButton;
    @Unique
    private boolean adding = false;
    @Unique
    private String filter = "";
    @Unique
    private String searchText = "";
    @Unique
    private int tick = 0;

    @Shadow @Final private ListOption<?> listOption;

    @Shadow public abstract List<? extends Element> children();

    @Inject(method = "<init>", at = @At("RETURN"))
    private void init(OptionListWidget this$0, ListOption<?> group, Screen screen, CallbackInfo ci) {
        if(group instanceof MusicListOption<?>) {
            addListButton = new TooltipButtonWidget(screen, resetListButton.getX() - 20, -50, 20, 20, Text.literal("+"), Text.translatable("config.mixtape.musicList.add_song"), btn -> {
                adding = true;
                editBox.setFocused(true);
            });
            closeButton = new TooltipButtonWidget(screen, resetListButton.getX(), 0, 20, 20, Text.literal("❌"), Text.translatable("config.mixtape.musicList.close"), btn -> adding = false);
//            lenaButton = new TooltipButtonWidget(screen, resetListButton.getX() - 27, 0, 27, 20, Text.literal("Lena"), Text.translatable("config.mixtape.musicList.filter.lena"), btn -> filter = "lena");
//            kumiButton = new TooltipButtonWidget(screen, lenaButton.getX() - 27, 0, 27, 20, Text.literal("Kumi"), Text.translatable("config.mixtape.musicList.filter.kumi"), btn -> filter = "kumi");
//            aaronButton = new TooltipButtonWidget(screen, kumiButton.getX() - 33, 0, 33, 20, Text.literal("Aaron"), Text.translatable("config.mixtape.musicList.filter.aaron"), btn -> filter = "aaron");
//            c418Button = new TooltipButtonWidget(screen, aaronButton.getX() - 27, 0, 27, 20, Text.literal("C418"), Text.translatable("config.mixtape.musicList.filter.c418"), btn -> filter = "c418");
            editBox = new TextFieldWidget(MinecraftClient.getInstance().textRenderer, this$0.getRowLeft() + 1, 0, 100, 18, this.editBox, Text.empty());
            editBox.setChangedListener(s -> searchText = s);
            editBox.active = true;
        }
    }

    @Unique
    public boolean charTyped(char chr, int modifiers) {
        return this.editBox.charTyped(chr, modifiers);
    }

    @Unique
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.editBox.keyPressed(keyCode, scanCode, modifiers);
    }

    @Unique
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean focused = false;
        for (Element child : children()) {
            child.setFocused(false);
            if (child.mouseClicked(mouseX, mouseY, button) && child.getClass().getTypeName().equals("net.minecraft.client.gui.widget.TextFieldWidget")) {
                child.setFocused(true);
                focused = true;
            }
        }
        return focused;
    }

    @Inject(method = "render", at = @At("RETURN"))
    private void render(DrawContext graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        tick++;
        if (listOption instanceof MusicListOption<?> musicListOption) {
            musicListOption.setAdding(adding);
            int buttonY = y + entryHeight / 2 - 9;
            addListButton.setY(buttonY);
            resetListButton.setY(buttonY);
            closeButton.setY(-20);
//            c418Button.setY(-20);
//            aaronButton.setY(-20);
//            kumiButton.setY(-20);
//            lenaButton.setY(-20);
            addListButton.visible = true;
            if (adding) {
                addListButton.visible = false;
//                editBox.setWidth(clamp(entryWidth - 10 - 114 - 12, 0, 500));
                editBox.setWidth(clamp(entryWidth - 10 - 12, 0, 500));
                closeButton.setY(buttonY);
                resetListButton.setY(-20);
//                c418Button.setY(buttonY);
//                aaronButton.setY(buttonY);
//                kumiButton.setY(buttonY);
//                lenaButton.setY(buttonY);
                editBox.setY(buttonY + 1);
//                c418Button.render(graphics, mouseX, mouseY, tickDelta);
//                aaronButton.render(graphics, mouseX, mouseY, tickDelta);
//                kumiButton.render(graphics, mouseX, mouseY, tickDelta);
//                lenaButton.render(graphics, mouseX, mouseY, tickDelta);
                editBox.render(graphics, mouseX, mouseY, tickDelta);
                if ((tick %= 3) == 0) {
//                    editBox.tick();
                }
            }
        }

    }

    @Inject(method = "children", at = @At("RETURN"), cancellable = true)
    private void children(CallbackInfoReturnable<List<? extends Element>> cir) {
        List returnList = cir.getReturnValue();
//        returnList = ImmutableList.builder().addAll(returnList).addAll(List.of(c418Button, aaronButton, kumiButton, lenaButton, closeButton, editBox)).build();
        returnList = ImmutableList.builder().addAll(returnList).addAll(List.of(closeButton, editBox)).build();
        cir.setReturnValue(returnList);
    }
}
