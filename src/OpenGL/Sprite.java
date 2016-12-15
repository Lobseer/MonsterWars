package OpenGL;

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
    GRASS("tile_grass"), WATER("tile_water"), MOUNTAIN("tile_mountain"),
    SPAWN1("tile_spawn_monster_1"), SPAWN2("tile_spawn_monster_2"), SPAWN3("tile_spawn_monster_3"),
    MONSTER("monster"), SKELET("skelet");

    private Texture texture;

    private Sprite(String texturename){
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
