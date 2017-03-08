package task;

public interface Task {

  public abstract boolean putSommet(Sommet sommet);
  public abstract boolean putTitre(Sommet sommet);
  public abstract Sommet getSommet(Integer i);
  public abstract boolean existe(String s);
  public abstract void affichage();
}
