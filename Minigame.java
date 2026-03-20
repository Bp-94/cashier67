public abstract class Minigame {
    protected boolean isFinish;
    protected boolean isPass;

    public void setFinish(boolean isFinish){
        this.isFinish = isFinish;
    }
    public boolean getFinish(){
        return isFinish;
    }
    public void setPass(boolean isPass){
        this.isPass = isPass;
    }
    public boolean getPass(){
        return isPass;
    }

    public abstract void play();
    public abstract boolean checkWincondition();
}