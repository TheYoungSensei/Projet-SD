package task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Task1 implements Task {

  private HashMap<Integer, Sommet> sommets = new HashMap<Integer, Sommet>();
  private HashMap<String, Sommet> titres = new HashMap<String, Sommet>();



  public void affichage() {
    for (int i : sommets.keySet()) {
      System.out.println(sommets.get(i).getPageWiki().getTitre());
      for (Sommet som : sommets.get(i).getArcs()) {
        System.out.println("Lien vers : " + som.getPageWiki().getTitre());
      }
    }
  }

  public LinkedList<Sommet> algorithme(Sommet sommetDepart, Sommet sommetArrivee) {

    HashMap<Sommet, NoeudArbreBsf> noeudsArbres = new HashMap<Sommet, NoeudArbreBsf>();
    HashSet<Sommet> sommetsAtteints = new HashSet<Sommet>();
    LinkedList<Sommet> parcours = new LinkedList<Sommet>();
    LinkedList<Sommet> cheminARenvoyer = new LinkedList<Sommet>();

    if (sommetDepart.equals(sommetArrivee)) {
      System.out.println(sommetArrivee.getPageWiki().getTitre());
      cheminARenvoyer.add(sommetArrivee);
      return cheminARenvoyer;
    }


    parcours.add(sommetDepart);

    // debut d'arbre
    NoeudArbreBsf noeudDepart = new NoeudArbreBsf(sommetDepart, null);
    noeudsArbres.put(sommetDepart, noeudDepart);

    while (!parcours.isEmpty()) {
      Sommet courant = parcours.poll();
      sommetsAtteints.add(courant);
      List<Sommet> sommetsSortant = courant.getArcs();

      // pour touts les sommet sortant non traités.
      for (Sommet sommet : sommetsSortant) {

        if (!sommetsAtteints.contains(sommet)) {
          // je les ajoute dans la file et dans mon arbre des noeuds
          parcours.add(sommet);
          NoeudArbreBsf noeud = new NoeudArbreBsf(sommet, courant);
          noeudsArbres.put(sommet, noeud);
          // si le sommet d'arrive est atteint
          if (sommet.equals(sommetArrivee)) {
            System.out.println("trouvé dit : ");
            cheminARenvoyer = trouverCheminArbre(sommetArrivee, noeudsArbres);
            for (Sommet sommet2 : cheminARenvoyer) {
              System.out.println("->" + sommet2.getPageWiki().getTitre());

            }
          }
        }
      }


    }

    return cheminARenvoyer;
  }

  private LinkedList<Sommet> trouverCheminArbre(Sommet sommetArrivee,
      HashMap<Sommet, NoeudArbreBsf> noeudsArbres) {
    LinkedList<Sommet> chemin = new LinkedList<Sommet>();
    NoeudArbreBsf noeud = noeudsArbres.get(sommetArrivee);
    while (noeud != null) {
      chemin.push(noeud.getSommet());
      Sommet parent = noeud.getParent();
      noeud = noeudsArbres.get(parent);
    }
    return chemin;
  }



  public boolean existe(String nomSommet) {
    return titres.containsKey(nomSommet);
  }

  public boolean putSommet(Sommet sommet) {
    return this.sommets.put(sommet.getPageWiki().getIdentifiantProjet(), sommet) != null;
  }

  public boolean putTitre(Sommet sommet) {
    return this.titres.put(sommet.getPageWiki().getTitre(), sommet) != null;
  }

  public Sommet getSommet(Integer key) {
    return this.sommets.get(key);
  }

  public Sommet getSommetByString(String titre) {

    return this.titres.get(titre);
  }

  private class NoeudArbreBsf {
    private Sommet sommet;
    private Sommet parent;

    public NoeudArbreBsf(Sommet sommet, Sommet parent) {
      this.sommet = sommet;
      this.parent = parent;
    }

    public Sommet getSommet() {
      return sommet;
    }



    public Sommet getParent() {
      return parent;
    }


  }

}
