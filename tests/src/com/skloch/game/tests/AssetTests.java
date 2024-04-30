package com.skloch.game.tests;
import com.badlogic.gdx.Gdx;
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
    public void testIfInterfaceBlockyInterfaceDataExists() {
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/avatar2.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/button_disabled.9.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/button_over.9.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/button_up.9.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/MotorolaScreentype.fnt").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/MotorolaScreentype.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/nk57-monospace.rg-bold.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/nk57-monospace.rg-bold.fnt").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/PixeloidMono edited(2).png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/PixeloidMono edited(2).fnt").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/scroll_bar.9.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/scroll_bar_v.9.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/slider_bar.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/slider_button.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/window.9.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/scroll_bar.9.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/W95FA(3).png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface_data/W95FA(3).fnt").exists());
    }

    @Test
    public void testIfInterfaceEnergyBarExists() {
        assertTrue(Gdx.files.internal("../assets/Interface/Energy Bar/bar_outline.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/Energy Bar/green_bar.png").exists());
    }

    @Test
    public void testIfInterfaceExists() {
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface.atlas").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface.json").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/BlockyInterface.png").exists());
        assertTrue(Gdx.files.internal("../assets/Interface/Blockyinterface.scmp").exists());
    }

    @Test
    public void testIfMusicExists() {
        assertTrue(Gdx.files.internal("../assets/Music/OverworldMusic.mp3").exists());
        assertTrue(Gdx.files.internal("../assets/Music/Streetlights.ogg").exists());
    }

    @Test
    public void testIfSoundsExists() {
        assertTrue(Gdx.files.internal("../assets/Sounds/Button.wav").exists());
        assertTrue(Gdx.files.internal("../assets/Sounds/DialogueOpen.wav").exists());
        assertTrue(Gdx.files.internal("../assets/Sounds/DialogueOption.wav").exists());
        assertTrue(Gdx.files.internal("../assets/Sounds/footstep1.ogg").exists());
        assertTrue(Gdx.files.internal("../assets/Sounds/footstep1 grass.ogg").exists());
        assertTrue(Gdx.files.internal("../assets/Sounds/footstep2.ogg").exists());
        assertTrue(Gdx.files.internal("../assets/Sounds/footstep2 grass.ogg").exists());
        assertTrue(Gdx.files.internal("../assets/Sounds/Pause01.wav").exists());
        assertTrue(Gdx.files.internal("../assets/Sounds/Walking.wav").exists());
    }

    @Test
    public void testIfTextExists() {
        assertTrue(Gdx.files.internal("../assets/Text/credits.txt").exists());
        assertTrue(Gdx.files.internal("../assets/Text/tutorial_text.txt").exists());
    }

    @Test
    public void testIfTextFieldExists() {
        assertTrue(Gdx.files.internal("../assets/TextField/TextField.atlas").exists());
        assertTrue(Gdx.files.internal("../assets/TextField/TextField.json").exists());
        assertTrue(Gdx.files.internal("../assets/TextField/TextField.json").exists());
        assertTrue(Gdx.files.internal("../assets/TextField/W95FA(1).fnt").exists());
    }
}
