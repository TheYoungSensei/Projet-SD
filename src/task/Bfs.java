package task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Bfs implements Algorithme {

  private HashMap<Integer, Sommet> sommets = new HashMap<Integer, Sommet>();
  private HashMap<String, Sommet> titres = new HashMap<String, Sommet>();
  private int nombreNoeud;

  public void affichage() {
    for (String i : titres.keySet()) {
      System.out.println(i);
    }
  }

  public List<Sommet> algorithme(Sommet sommetDepart, Sommet sommetArrivee) {
    this.nombreNoeud = 0;
    LinkedList<Sommet> mesSommets = new LinkedList<Sommet>();
    Set<Sommet> tousMesSommets = new HashSet<Sommet>();
    Map<Sommet, Sommet> ancetres = new HashMap<Sommet, Sommet>();
    Sommet courant = sommetDepart;
    tousMesSommets.add(courant);
    while (!courant.equals(sommetArrivee)) {
      for (Sommet sommet : courant.getArcs()) {
        if (!tousMesSommets.contains(sommet)) {
          ancetres.put(sommet, courant);
          mesSommets.add(sommet);
          tousMesSommets.add(sommet);
        }
      }
      if (mesSommets.isEmpty())
        return new LinkedList<Sommet>();
      courant = mesSommets.pollFirst();
      if (courant == null)
        return new LinkedList<Sommet>();
    }
    return trouverCheminArbre(sommetArrivee, ancetres);
  }

  private LinkedList<Sommet> trouverCheminArbre(Sommet sommetArrivee,
      Map<Sommet, Sommet> ancetres) {
    LinkedList<Sommet> chemin = new LinkedList<Sommet>();
    Sommet sommet = sommetArrivee;
    while (sommet != null) {
      this.nombreNoeud++;
      chemin.push(sommet);
      sommet = ancetres.get(sommet);
    }
    return chemin;
  }

  public boolean existe(String nomSommet) {
    return titres.containsKey(nomSommet);
  }

  public void putSommetInit(Sommet sommet) {
    this.titres.put(sommet.getPageWiki().getTitre(), sommet);
    this.sommets.put(sommet.getPageWiki().getIdentifiantProjet(), sommet);
  }

  public Sommet getSommet(Integer key) {
    return this.sommets.get(key);
  }

  public Sommet getSommetByString(String titre) {

    return this.titres.get(titre);
  }

  @Override
  public int getPoidsTotal() {
    return nombreNoeud;
  }
}
