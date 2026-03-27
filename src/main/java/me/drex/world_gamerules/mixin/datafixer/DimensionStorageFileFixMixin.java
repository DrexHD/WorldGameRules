package me.drex.world_gamerules.mixin.datafixer;

import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.filefix.FileFix;
import net.minecraft.util.filefix.access.FileRelation;
import net.minecraft.util.filefix.fixes.DimensionStorageFileFix;
import net.minecraft.util.filefix.operations.FileFixOperations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DimensionStorageFileFix.class)
public abstract class DimensionStorageFileFixMixin extends FileFix {
    public DimensionStorageFileFixMixin(Schema schema) {
        super(schema);
    }

    @Inject(method = "makeFixer", at = @At("HEAD"))
    private void fixData(CallbackInfo ci) {
        this.addFileFixOperation(FileFixOperations.applyInFolders(FileRelation.DIMENSIONS_DATA, List.of(FileFixOperations.move("gamerules.dat", "world-gamerules/gamerules.dat"))));
    }
}
