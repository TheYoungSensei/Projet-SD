import java.util.List;

import task.Algorithme;
import task.Bfs;
import task.Dijkstra;
import task.LireFichier;
import task.Sommet;
import util.Util;

public class Main {

  public static java.util.Scanner scanner;
  private static Algorithme bfs;
  private static Algorithme dijkstra;

  public static void main(String[] args) {
    initialisationProgramme();
    char boucle;
    do {
      scanner = new java.util.Scanner(System.in);
      menuPrincipal();
      System.out.println("Voulez vous continuer ? (O/N)");
      boucle = scanner.next().charAt(0);
    } while (Util.lireCharOouN(boucle));
    System.out.println("Merci d'avoir utilisé notre programme, à la prochaine fois ....");
  }

  private static void menuPrincipal() {
    System.out.println("--------------------------------------------------------");
    System.out.println("          Bienvenue sur le nouveau Wikipedia");
    System.out.println("--------------------------------------------------------");
    System.out.println("1. Chercher le chemin le plus court");
    System.out.println("2. Chercher le chemin le moins couteux");
    int choix = Util.lireEntier();
    switch (choix) {
      case 1:
        plusCourt();
        break;
      case 2:
        moinsCher();
        break;
      default:
        System.out.println("Mauvais chiffre entré, faîtes attention la prochaine fois");
    }
  }

  private static void plusCourt() {
    
    String sommetDepart = getStringSommet("Veuillez entrer un sommet de départ :");
    String sommetArrivee = getStringSommet("Veuilliez entrer le sommet d'arrivée : ");
    List<Sommet> chemin = bfs.algorithme(bfs.getSommetByString(sommetDepart), bfs.getSommetByString(sommetArrivee));
    affichageChemin(chemin);
    System.out.println("Le chemin est constitué de " + bfs.getPoidsTotal() + " page(s)");
  }

  private static String getStringSommet(String message) {
    String sommet;
    do {
      System.out.println(message);
      sommet = scanner.nextLine();
    } while (!bfs.existe(sommet));
    return sommet;
  }
  
  private static void moinsCher() {
    String sommetDepart = getStringSommet("Veuillez entrer un sommet de départ");    
    String sommetArrivee = getStringSommet("Veuillez entrer un sommet d'arrivée");
    List<Sommet> chemin = dijkstra.algorithme(dijkstra.getSommetByString(sommetDepart), dijkstra.getSommetByString(sommetArrivee));
    if(chemin == null){
        System.out.println("Aucun chemin entre les deux pages !");
    }
    //affichage Chemin
    else{
         affichageChemin(chemin);
         System.out.println("Il vous a fallu : " + dijkstra.getPoidsTotal() + " kb pour effectuer cette recherche");
    }
   
  }

  private static void affichageChemin(List<Sommet> chemin) {
    for(int i =0; i< chemin.size();i++){
            System.out.println(i+") "+chemin.get(i).getPageWiki().getTitre());
        }
  }

  private static void initialisationProgramme() {
    bfs = new Bfs();
    LireFichier lectureBfs = new LireFichier(bfs);
    lectureBfs.lectureFichier();
    dijkstra = new Dijkstra();
    LireFichier lectureDijkstra = new LireFichier(dijkstra);
    lectureDijkstra.lectureFichier();
  }
}
