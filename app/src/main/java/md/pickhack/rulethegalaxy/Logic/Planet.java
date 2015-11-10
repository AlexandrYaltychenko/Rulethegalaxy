package md.pickhack.rulethegalaxy.Logic;

/**
 * Created by alexialt on 24.10.15.
 */
public class Planet {
    String user_name;
    String planet_name;
    long public_id;
    String private_id;
    long cor1;
    long cor2;
    String buildings;
    long resources;
    public Planet(String user_name, String planet_name, String public_id, String private_id, String cor1, String cor2, String buildings, String resources){
        this.user_name = user_name;
        this.planet_name = planet_name;
        this.public_id = Long.parseLong(public_id);
        this.private_id = private_id;
        this.cor1 = Long.parseLong(cor1);
        this.cor2 = Long.parseLong(cor2);
        this.buildings = buildings;
        this.resources = Long.parseLong(resources);

    }
}
