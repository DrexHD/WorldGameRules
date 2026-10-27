package me.drex.world_gamerules.util;

import eu.pb4.placeholders.api.ParserContext;
import eu.pb4.placeholders.api.node.TextNode;
import eu.pb4.placeholders.api.parsers.NodeParser;
import eu.pb4.placeholders.api.parsers.TagLikeParser;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import xyz.nucleoid.server.translations.api.Localization;
import xyz.nucleoid.server.translations.api.LocalizationTarget;

import java.util.Map;
import java.util.function.Function;

public interface LocalizedMessage {

    ParserContext.Key<Function<String, Component>> STATIC_PLACEHOLDERS = ParserContext.Key.of("world-gamerules:static_placeholders");
    NodeParser PARSER = NodeParser.builder()
        .quickText()
        .simplifiedTextFormat()
        .placeholders(TagLikeParser.PLACEHOLDER_USER, STATIC_PLACEHOLDERS)
        .build();

    static MutableComponent localized(String key, CommandSourceStack src) {
        return localized(key, Map.of(), src);
    }

    static MutableComponent localized(String key, Map<String, Component> placeholders, CommandSourceStack src) {
        LocalizationTarget target = src.getPlayer() != null ? LocalizationTarget.of(src.getPlayer()) : LocalizationTarget.ofSystem();
        String message = Localization.raw(key, target);
        if (message == null) {
            message = key;
        }
        ParserContext parserContext = ParserContext.of();
        parserContext.with(STATIC_PLACEHOLDERS, placeholderGetter(placeholders));
        return (MutableComponent) PARSER.parseComponent(TextNode.of(message), parserContext);
    }

    private static Function<String, Component> placeholderGetter(Map<String, Component> placeholders) {
        return key -> placeholders.getOrDefault(key, Component.literal("${" + key + "}"));
    }

}
