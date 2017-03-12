package task;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Task1 implements Task {

  private Map<Integer, Sommet> sommets = new HashMap<Integer, Sommet>();
  private Map<String, Sommet> titres = new HashMap<String, Sommet>();
  private Map<Sommet, Integer> couts;
  // private Set<Sommet> sommetsAtteints;
  private Map<Sommet, Sommet> ancetres;
  private Set<Sommet> sommetsTrajet;
  private Sommet sommet;
  private final static int DISTANCE = 1;


  public void affichage() {
    for (int i : sommets.keySet()) {
      System.out.println(sommets.get(i).getPageWiki().getTitre());
      for (Sommet som : sommets.get(i).getArcs()) {
        System.out.println("Lien vers : " + som.getPageWiki().getTitre());
      }
    }
  }

  public List<Sommet> algorithme(Sommet sommetDepart, Sommet sommetArrivee) {
    sommetsTrajet = new HashSet<Sommet>();
    couts = new HashMap<Sommet, Integer>();
    ancetres = new HashMap<Sommet, Sommet>();
    couts.put(sommetDepart, 0);
    sommetsTrajet.add(sommetDepart);
    this.sommet = sommetArrivee;
    while(sommetsTrajet.size() > 0) {
      Sommet sommet = getMinimum(sommetsTrajet);
      sommetsTrajet.remove(sommet);
      List<Sommet> chemin = distanceMinimale(sommet);
      if (chemin != null)
        return chemin;
    }
    return null;
  }
  
  private List<Sommet> distanceMinimale(Sommet sommet) {
    List<Sommet> voisins = getVoisins(sommet);
    for (Sommet s : voisins) {
      if(distance(s) > distance(sommet) + distanceEntre(sommet, s)) {
        couts.put(s, distance(sommet) + distanceEntre(sommet, s));
        ancetres.put(s, sommet);
        sommetsTrajet.add(s);
      }
      if (s.equals(this.sommet)) {
        List<Sommet> chemin = chemin(s);
        sommetsTrajet.removeAll(sommetsTrajet);
        return chemin;
      }
    }
    return null;
  }
  
  private List<Sommet> chemin(Sommet sommet) {
    List<Sommet> chemin = new LinkedList<Sommet>();
    //int poidsTotal = 0;
    Sommet etape = sommet;
    if(ancetres.get(etape) == null) {
      return null;
    }
    chemin.add(etape);
    while (ancetres.get(etape) != null) {
      etape = ancetres.get(etape);
      chemin.add(etape);
      //poidsTotal = etape.getPageWiki().getTaille();
    }
    Collections.reverse(chemin);
    return chemin;
  }
  
  private List<Sommet> getVoisins(Sommet sommet)  {
    return sommet.getArcs();
  }
  
  private int distanceEntre(Sommet s1, Sommet s2) {
    return s2.getPageWiki().getTaille();
  }
  
  private Sommet getMinimum(Set<Sommet> sommets) {
    Sommet minimum = null;
    for (Sommet sommet : sommets) {
      if(minimum == null){
        minimum = sommet;
      } else {
        if(distance(sommet) < distance(minimum)) {
          minimum = sommet;
        }
      }
    }
    return minimum;
  }
  
  private int distance(Sommet sommet) {
    Integer i = couts.get(sommet);
    if (i == null) {
      return Integer.MAX_VALUE;
    } else {
      return i;
    }
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

  @Override
  public Sommet getSommetByString(String s) {
    return this.titres.get(s);
  }
}
