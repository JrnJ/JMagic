package com.jeroenj.world.biome;

import com.jeroenj.JMagic;
import com.jeroenj.world.biome.surface.JMagicMaterialRules;
import terrablender.api.Regions;
import terrablender.api.SurfaceRuleManager;
import terrablender.api.TerraBlenderApi;

public class JMagicTerrablenderApi implements TerraBlenderApi {
    @Override
    public void onTerraBlenderInitialized() {
        Regions.register(new JMagicOverworldRegion(JMagic.id("overworld"), 4));

        // https://www.youtube.com/watch?v=5oICKtjsRvc (12:33)
        // SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, JMagic.MOD_ID, JMagicMaterialRules.makeRules());
    }
}
