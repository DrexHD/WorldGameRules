package me.drex.world_gamerules.command.selector;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class NamespaceDimensions implements DimensionSelector {
    @Override
    public Builders builder() {
        RequiredArgumentBuilder<CommandSourceStack, String> second = Commands.argument("namespace", StringArgumentType.word()).suggests((context, builder) -> {
            for (ResourceKey<Level> levelResourceKey : context.getSource().getServer().levelKeys()) {
                builder.suggest(levelResourceKey.location().getNamespace());
            }
            return builder.buildFuture();
        });
        LiteralArgumentBuilder<CommandSourceStack> first = Commands.literal("@namespace");
        return new Builders(first, second);
    }

    @Override
    public Collection<ServerLevel> getLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        String namespace = StringArgumentType.getString(context, "namespace");
        List<ServerLevel> result = new LinkedList<>();
        for (ServerLevel level : context.getSource().getServer().getAllLevels()) {
            if (level.dimension().location().getNamespace().equals(namespace)) {
                result.add(level);
            }
        }
        return result;
    }
}
