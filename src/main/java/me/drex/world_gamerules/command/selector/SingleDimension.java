package me.drex.world_gamerules.command.selector;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.server.level.ServerLevel;

import java.util.Collection;
import java.util.List;

public class SingleDimension implements DimensionSelector {
    @Override
    public Builders builder() {
        return new Builders(Commands.argument("dimension", DimensionArgument.dimension()));
    }

    @Override
    public Collection<ServerLevel> getLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        return List.of(DimensionArgument.getDimension(context, "dimension"));
    }
}
