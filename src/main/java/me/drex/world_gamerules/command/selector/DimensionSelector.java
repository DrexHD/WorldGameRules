package me.drex.world_gamerules.command.selector;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerLevel;

import java.util.Collection;

public interface DimensionSelector {

    Builders builder();

    Collection<ServerLevel> getLevels(CommandContext<CommandSourceStack> context) throws CommandSyntaxException;

    record Builders(ArgumentBuilder<CommandSourceStack, ?> first, ArgumentBuilder<CommandSourceStack, ?> second) {
        Builders(ArgumentBuilder<CommandSourceStack, ?> builder) {
            this(builder, builder);
        }

        public void connect() {
            if (first() != second()) {
                first().then(second());
            }
        }
    }

}
