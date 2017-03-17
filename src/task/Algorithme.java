package task;

import java.util.List;

public interface Algorithme {

  public abstract void putSommetInit(Sommet sommet);

  public abstract Sommet getSommet(Integer i);

  public abstract Sommet getSommetByString(String titre);

  public abstract boolean existe(String s);

  public abstract void affichage();

  public abstract List<Sommet> algorithme(Sommet depart, Sommet Arrive);

  public abstract int getPoidsTotal();
}