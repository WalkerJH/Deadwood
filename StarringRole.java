/*******************
 * Performs functions of an starring (non-extra) role
 *******************/
public class StarringRole extends Role {
    public void payout(){}

    public boolean actAttempt(int roll){
        return false;
    }
}
