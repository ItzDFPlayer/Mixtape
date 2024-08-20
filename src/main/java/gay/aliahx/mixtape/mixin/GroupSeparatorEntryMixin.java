package gay.aliahx.mixtape.mixin;

import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.gui.LowProfileButtonWidget;
import dev.isxander.yacl3.gui.OptionListWidget;
import gay.aliahx.mixtape.gui.MusicList.MusicListOption;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(OptionListWidget.GroupSeparatorEntry.class)
public abstract class GroupSeparatorEntryMixin {


    @Shadow
    @Final
    protected OptionGroup group;


    @Shadow @Final protected LowProfileButtonWidget expandMinimizeButton;

    @Shadow public abstract void setExpanded(boolean expanded);

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void render(DrawContext graphics, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo ci) {
        if (group instanceof MusicListOption<?> musicListOption) {
            expandMinimizeButton.visible = true;
            expandMinimizeButton.active = true;
            if(musicListOption.adding()) {
                setExpanded(true);
                expandMinimizeButton.visible = false;
                ci.cancel();
            }
        }
    }
}