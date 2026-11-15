
package dev.orevision.mixin;

import dev.orevision.OreVisionClient;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class BlockStateMixin {

    @Inject(
        method = "getRenderType",
        at = @At("HEAD"),
        cancellable = true
    )
    private void orevision$renderBlocks(
        CallbackInfoReturnable<BlockRenderType> cir
    ) {
        if (!OreVisionClient.isEnabled()) {
            return;
        }

        BlockState state = (BlockState) (Object) this;

        if (isOre(state)) {
            cir.setReturnValue(BlockRenderType.MODEL);
        } else if (state.getFluidState().isEmpty()) {
            cir.setReturnValue(BlockRenderType.INVISIBLE);
        }
    }

    @Inject(
        method = "isOpaqueFullCube()Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void orevision$disableOpacity(
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (!OreVisionClient.isEnabled()) {
            return;
        }

        cir.setReturnValue(false);
    }

    @Inject(
        method = "isSideInvisible(Lnet/minecraft/block/BlockState;Lnet/minecraft/util/math/Direction;)Z",
        at = @At("HEAD"),
        cancellable = true
    )
    private void orevision$showOreFaces(
        BlockState state,
        net.minecraft.util.math.Direction direction,
        CallbackInfoReturnable<Boolean> cir
    ) {
        if (!OreVisionClient.isEnabled()) {
            return;
        }

        BlockState current = (BlockState) (Object) this;

        if (isOre(current)) {
            cir.setReturnValue(false);
        }
    }

    private static boolean isOre(BlockState state) {
        Identifier id = Registries.BLOCK.getId(state.getBlock());
        String path = id.getPath();

        return path.endsWith("_ore")
            || path.equals("ancient_debris")
            || path.equals("raw_iron_block")
            || path.equals("raw_copper_block")
            || path.equals("raw_gold_block");
    }
}