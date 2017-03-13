package task;

import java.util.ArrayList;

public interface Task {
	
  public abstract void putSommetInit(Sommet sommet);	

  public abstract Sommet getSommetById(Integer i);
  public abstract Sommet getSommetByTitre(String s);
  public abstract boolean setSommetDepart(Sommet s);
  public abstract boolean setSommetArrivee(Sommet s);
  public abstract void affichage();
  public abstract ArrayList<Sommet> algorithmeDijkstra();
}
