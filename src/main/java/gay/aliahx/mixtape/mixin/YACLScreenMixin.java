package gay.aliahx.mixtape.mixin;

import dev.isxander.yacl3.gui.YACLScreen;
import gay.aliahx.mixtape.gui.SongController.SongControllerElement;
import net.minecraft.client.gui.tab.TabManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(YACLScreen.class)
public class YACLScreenMixin {
    @Shadow @Final public TabManager tabManager;

    @Inject(method = "close", at = @At("HEAD"))
    public void finishOrSaveMixin(CallbackInfo ci) {
        SongControllerElement.StopAllSongs(tabManager);
    }
}
