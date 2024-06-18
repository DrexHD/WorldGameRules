package me.drex.world_gamerules.command.selector;

import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;

import java.util.Collection;

public class AllDimensions implements DimensionSelector {
    @Override
    public Builders builder() {
        return new Builders(Commands.literal("@all"));
    }

    @Override
    public Collection<ServerLevel> getLevels(CommandContext<CommandSourceStack> context) {
        return Lists.newArrayList(context.getSource().getServer().getAllLevels());
    }
}
