import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Task1 implements Task {
  
  public HashMap<Integer, Sommet> sommets = new HashMap<Integer, Sommet>();
  public HashMap<String, Sommet> titres = new HashMap<String, Sommet>();
  public HashSet<Sommet> sommetsAtteints;
  
  
  public void affichage() {
    for(int i : sommets.keySet()) {
      System.out.println(sommets.get(i).getPageWiki().getTitre());
      for(Sommet som : sommets.get(i).arcsSortants) {
        System.out.println("Lien vers : " + som.getPageWiki().getTitre());
      }
    }
  }
  
  public List<Sommet> algorithme(Sommet sommetDepart, Sommet sommetArrivee) {
    this.sommetsAtteints = new HashSet<Sommet>();
    List<Sommet> chemin = new ArrayList<Sommet>();
    return null; //TODO
  }
  
  public boolean existe(String nomSommet) {
    return titres.containsKey(nomSommet);
  }
}
