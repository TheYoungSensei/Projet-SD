package task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    HashSet<Sommet> sommetsAtteints = new HashSet<Sommet>();
    LinkedList<Sommet> parcours = new LinkedList<Sommet>();
    List<Sommet> cheminARenvoyer = new LinkedList<Sommet>();
    Map<Sommet, Sommet> ancetres = new HashMap<Sommet, Sommet>();
    
    if (sommetDepart.equals(sommetArrivee)) {
      //System.out.println(sommetArrivee.getPageWiki().getTitre());
      cheminARenvoyer.add(sommetArrivee);
      this.nombreNoeud = 1;
      return cheminARenvoyer;
    }
    parcours.add(sommetDepart);
    /* Debut du parcours de  l'arbre */
    Sommet courant = sommetDepart;
    while (!parcours.isEmpty()) {
      // pour touts les sommet sortant non traités.
      for (Sommet sommet : courant.getArcs()) {
        if (!sommetsAtteints.contains(sommet)) {
          // je les ajoute dans la file et dans mon arbre des noeud
          parcours.add(sommet);
          ancetres.put(sommet, courant);
          // si le sommet d'arrive est atteint
         if (sommet.equals(sommetArrivee)) {
            courant = sommet;
            return trouverCheminArbre(sommetArrivee, ancetres);
          }
        }
      }
      courant = parcours.poll();
      sommetsAtteints.add(courant);
    }
    return trouverCheminArbre(sommetArrivee, ancetres);
  }

  private LinkedList<Sommet> trouverCheminArbre(Sommet sommetArrivee,
      Map<Sommet, Sommet> ancetres) {
    LinkedList<Sommet> chemin = new LinkedList<Sommet>();
    Sommet sommet = sommetArrivee;
    this.nombreNoeud = 0;
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
