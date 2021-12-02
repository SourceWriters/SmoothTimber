package com.syntaxphoenix.spigot.smoothtimber.utilities;

import com.syntaxphoenix.syntaxapi.utils.java.lang.StringBuilder;

import org.bukkit.ChatColor;

public final class Centering {

    private Centering() {}

    public static String center(String input) {
        return center(input, "\n");
    }

    public static String center(String input, String split) {
        String[] sentences = input.split(split);
        int length = identifyLength(sentences);
        StringBuilder builder = new StringBuilder();
        for (String sentence : sentences) {
            apply(builder, length, sentence).append(split);
        }
        String output = builder.toString();
        return output.substring(0, output.length() - 1);
    }

    public static String[] center(String... input) {
        int length = identifyLength(input);
        String[] output = new String[input.length];
        StringBuilder builder = new StringBuilder();
        int index = 0;
        for (String sentence : input) {
            output[index++] = apply(builder, length, sentence).toStringClear();
        }
        return output;
    }

    private static StringBuilder apply(StringBuilder builder, int length, String sentence) {
        int size = ChatColor.stripColor(sentence).length();
        int difference = length - size;
        int spaces = difference / 2;
        for (int current = 0; current < spaces; current++) {
            builder.append(' ');
        }
        return builder.append(sentence);
    }

    private static int identifyLength(String[] input) {
        int length = 0;
        for (String sentence : input) {
            int size = ChatColor.stripColor(sentence).length();
            if (size > length) {
                length = size;
            }
        }
        return length;
    }

}
