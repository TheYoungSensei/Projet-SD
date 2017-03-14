import java.util.ArrayList;

import task.LireFichier;
import task.Sommet;
import task.Task;
import task.Task1;
import util.Util;

public class Main {

  public static java.util.Scanner scanner = new java.util.Scanner(System.in);
  private static Task task;

  public static void main(String[] args) {
  
    char boucle;
    do {
      initialisationProgramme();
      task.affichage();
      menuPrincipal();
      System.out.println("Voulez vous continuer ? (O/N)");
      boucle = scanner.next().charAt(0);
    } while (Util.lireCharOouN(boucle));
    System.out.println("Merci d'avoir utilis� notre programme, � la prochaine fois ....");
  }

  private static void menuPrincipal() {
    System.out.println("--------------------------------------------------------");
    System.out.println("          Bienvenue sur le nouveau Wikipedia");
    System.out.println("--------------------------------------------------------");
    System.out.println("1. Calculer le plus court chemin d'un sommet � un autre");
    scanner = new java.util.Scanner(System.in);
    int choix = Integer.valueOf(scanner.nextLine());
    switch (choix) {
      case 1:
        plusCourt();
        break;
      default:
        System.out.println("Mauvais chiffre entr�, fa�tes attention la prochaine fois");
    }
  }

  private static void plusCourt() {
    String sommetDepart;
    do {
      System.out.println("Veuillez entrer un sommet de d�part :");
      sommetDepart = scanner.nextLine();
    } while (task.getSommetByTitre(sommetDepart)==null);
    task.setSommetDepart(task.getSommetByTitre(sommetDepart));
    String sommetArrivee;
    do {
      System.out.println("Veuilliez entrer le sommet d'arriv�e : ");
      sommetArrivee = scanner.nextLine();
    } while (task.getSommetByTitre(sommetDepart)==null);
    task.setSommetArrivee(task.getSommetByTitre(sommetArrivee));
    ArrayList<Sommet> chemin = task.algorithmeDijkstra();
    if(chemin == null){
    	System.out.println("Aucun chemin entre les deux pages !");
    }
    //affichage Chemin
    else{
		 for(int i =0; i< chemin.size();i++){
		    	System.out.println(i+". "+chemin.get(i).getPageWiki().getTitre());
		    }
    }
   
  }

  private static void initialisationProgramme() {
    task = new Task1();
    LireFichier lecture = new LireFichier(task);
    lecture.lectureFichier();
  }
}
