public interface Obserable {
    public void add(Observer observer);
    public void remove(Observer observer);
    public void notifyObserver(String message);
    
}
