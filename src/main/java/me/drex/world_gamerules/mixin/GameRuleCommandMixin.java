package me.drex.world_gamerules.mixin;

import com.mojang.brigadier.context.CommandContext;
import eu.pb4.placeholders.api.PlaceholderContext;
import me.drex.message.api.Message;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.GameRuleCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.Iterator;
import java.util.Map;

@Mixin(GameRuleCommand.class)
public abstract class GameRuleCommandMixin {

    /**
     * @author Drex
     * @reason Fail-fast mod incompatibility. This mod changes gamerules to be per world. This patch reverts this
     * change for the /gamerule command to ensure vanilla parity.
     */
    @Overwrite
    static <T extends GameRules.Value<T>> int setRule(CommandContext<CommandSourceStack> context, GameRules.Key<T> key) {
        CommandSourceStack src = context.getSource();
        Iterator<ServerLevel> iterator = src.getServer().getAllLevels().iterator();
        T value = null;
        while (iterator.hasNext()) {
            value = iterator.next().getGameRules().getRule(key);
            value.setFromArgument(context, "value");
        }
        assert value != null;
        T finalValue = value;
        src.sendSuccess(() -> Message.message("world-gamerules.commands.gamerule.set", Map.of(
            "gamerule", Component.literal(key.getId()),
            "value", Component.literal(finalValue.toString())
        )), true);
        return value.getCommandResult();
    }

    /**
     * @author Drex
     * @reason Fail-fast mod incompatibility.
     */
    @Overwrite
    static <T extends GameRules.Value<T>> int queryRule(CommandSourceStack src, GameRules.Key<T> key) {
        T value = src.getLevel().getGameRules().getRule(key);
        src.sendSuccess(() -> Message.message("world-gamerules.commands.gamerule.query", Map.of(
            "gamerule", Component.literal(key.getId()),
            "value", Component.literal(value.toString())
        ), PlaceholderContext.of(src)), false);
        return value.getCommandResult();
    }

}
