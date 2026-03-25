public abstract class Minigame {
    protected boolean isPass;

    public void setPass(boolean isPass){
        this.isPass = isPass;
    }
    public boolean getPass(){
        return isPass;
    }

    public abstract void play();
    public abstract boolean checkWincondition();
}