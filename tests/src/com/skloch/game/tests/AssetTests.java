package com.skloch.game.tests;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.skloch.game.Energy;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(GdxTestRunner.class)
public class AssetTests {

    @Test
    public void testIfEastCampusExists() {
        assertTrue(Gdx.files.internal("../assets/East Campus/Campus East.tiled-session").exists());
        assertTrue(Gdx.files.internal("../assets/East Campus/east_campus.tmx").exists());
    }

    @Test
    public void testIfEastCampusTexturesExists() {
        assertTrue(Gdx.files.internal("../assets/East Campus/Textures/compsci.png").exists());
        assertTrue(Gdx.files.internal("../assets/East Campus/Textures/Constantine.png").exists());
        assertTrue(Gdx.files.internal("../assets/East Campus/Textures/piazza.png").exists());
        assertTrue(Gdx.files.internal("../assets/East Campus/Textures/RCH.png").exists());
        assertTrue(Gdx.files.internal("../assets/East Campus/Textures/StarRealmsCozyForestPack24x24.png").exists());
    }

    @Test
    public void testIfEastCampusTilemapsExists() {
        assertTrue(Gdx.files.internal("../assets/East Campus/Tilemaps/compsci.tsx").exists());
        assertTrue(Gdx.files.internal("../assets/East Campus/Tilemaps/Constantine.tsx").exists());
        assertTrue(Gdx.files.internal("../assets/East Campus/Tilemaps/piazza.tsx").exists());
        assertTrue(Gdx.files.internal("../assets/East Campus/Tilemaps/RCH.tsx").exists());
        assertTrue(Gdx.files.internal("../assets/East Campus/Tilemaps/StarRealmsCozyForestPack24x24.tsx").exists());
    }

    @Test
    public void testIfIconsExists() {
        assertTrue(Gdx.files.internal("../assets/Icons/icon_16x16.png").exists());
        assertTrue(Gdx.files.internal("../assets/Icons/icon_32x32.png").exists());
        assertTrue(Gdx.files.internal("../assets/Icons/icon_128x128.png").exists());
    }

    @Test
    public void testIfTextExists() {
        assertTrue(Gdx.files.internal("../assets/Text/credits.txt").exists());
        assertTrue(Gdx.files.internal("../assets/Text/tutorial_text.txt").exists());
    }
}
