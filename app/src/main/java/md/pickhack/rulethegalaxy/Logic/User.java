package md.pickhack.rulethegalaxy.Logic;

import java.util.ArrayList;

/**
 * Created by alexialt on 24.10.15.
 */
public class User {
    public ArrayList<Planet> planets;
    private String nickname;
    private String private_id;
    public User (String nickname, String private_id){
        this.nickname = nickname;
        this.private_id = private_id;
    }
    public String getNickname(){
        return nickname;
    }
}
