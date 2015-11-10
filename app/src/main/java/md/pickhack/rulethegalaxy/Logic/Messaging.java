package md.pickhack.rulethegalaxy.Logic;

/**
 * Created by alexialt on 25.10.15.
 */
public class Messaging {
    private int new_messages;
    private User user;
    public Messaging(User user){
        this.user = user;
    }
    public int getNewMessagesCount(){
        return new_messages;
    }
}
