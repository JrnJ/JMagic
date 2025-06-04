package com.jeroenj.datagen;

import net.minecraft.data.DataProvider;
import net.minecraft.data.DataWriter;
import org.jetbrains.annotations.ApiStatus;

import java.util.concurrent.CompletableFuture;

// Check:
// FabricLanguageProvider.class
// DataProvider.class
public abstract class StructureProvider implements DataProvider {
    public abstract void generateStructures();

    @Override
    public CompletableFuture<?> run(DataWriter writer) {
        return null;
    }

    @Override
    public String getName() {
        return "Structure";
    }

    @FunctionalInterface
    @ApiStatus.NonExtendable
    public interface StructureBuilder {
        void add(String var1, String var2);
    }
}
