package gay.aliahx.mixtape.mixin;

import gay.aliahx.mixtape.gui.SongController.SongControllerElement;
import net.minecraft.client.gui.tab.Tab;
import net.minecraft.client.gui.tab.TabManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(TabManager.class)
public class TabManagerMixin {
    @Inject(method = "setCurrentTab", at = @At("HEAD"))
    private void setCurrentTabMixin(Tab tab, boolean clickSound, CallbackInfo ci) {
        SongControllerElement.StopAllSongs((TabManager) (Object) this);
    }
}
