package com.github.veivel.commandr.tag;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.github.veivel.commandr.Commandr.id;

public class ModItemTags {
    public static final TagKey<Item> YOUR_TAG = TagKey.create(Registries.ITEM, id("your_tag"));
}
