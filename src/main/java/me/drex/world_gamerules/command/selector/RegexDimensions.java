package me.drex.world_gamerules.command.selector;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerLevel;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class RegexDimensions implements DimensionSelector {
    @Override
    public Builders builder() {
        RequiredArgumentBuilder<CommandSourceStack, String> last = Commands.argument("regex", StringArgumentType.string());
        LiteralArgumentBuilder<CommandSourceStack> first = Commands.literal("@regex");
        return new Builders(first, last);
    }

    @Override
    public Collection<ServerLevel> getLevels(CommandContext<CommandSourceStack> context) {
        String regex = StringArgumentType.getString(context, "regex");
        List<ServerLevel> result = new LinkedList<>();
        for (ServerLevel level : context.getSource().getServer().getAllLevels()) {
            if (level.dimension().location().toString().matches(regex)) {
                result.add(level);
            }
        }
        return result;
    }
}
