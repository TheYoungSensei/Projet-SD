package task;

import java.util.List;

public interface Task {

  public abstract boolean putSommet(Sommet sommet);
  public abstract boolean putTitre(Sommet sommet);
  public abstract Sommet getSommet(Integer i);
  public abstract Sommet getSommetByString(String s);
  public abstract boolean existe(String s);
  public abstract void affichage();
  public abstract List<Sommet> algorithme(Sommet sommetDepart, Sommet sommetArrivee);
}
