/*******************
 * Performs functions of an extra (non-starring) role
 *******************/
public class ExtraRole extends Role{
    public void payout(){}

    public boolean actAttempt(int roll){
        return false;
    }

}
