package impl.service;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.*;

/**
 * Class description
 *
 * @author lobseer
 * @version 14.12.2016
 */

public enum Sprite {
    ///Файлы с именами circle и cherries должны лежать по адресу
    /// %папка проекта%/res/ в расширении .png
    GRASS("tile_grass"), WATER("tile_water"), MOUNTAIN("tile_mountain"), HIGHT_MOUNTAIN("tile_high_mountain"),
    BUILDING("tile_building") , SPAWN1("tile_spawn_monster_1"), SPAWN2("tile_spawn_monster_2"), SPAWN3("tile_spawn_monster_3"),
    MONSTER("monster"), SKELETON("skeleton"), PIG("pig"),
    N0("n0"),N1("n1"),N2("n2"),N3("n3"),N4("n4"),N5("n5"),N6("n6"),N7("n7"),N8("n8"),N9("n9"),
    ERROR("error");

    private Texture texture;

    Sprite(String texturename){
        try {
            this.texture = TextureLoader.getTexture("PNG", new FileInputStream(new File("res/"+texturename+".png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Texture getTexture(){
        return this.texture;
    }
}
