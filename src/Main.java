import java.util.List;

import task.LireFichier;
import task.Sommet;
import task.Task;
import task.Task1;
import util.Util;

public class Main {

  public static java.util.Scanner scanner = new java.util.Scanner(System.in);
  private static Task task;

  public static void main(String[] args) {
    initialisationProgramme();
    char boucle;
    do {
      menuPrincipal();
      System.out.println("Voulez vous continuer ? (O/N)");
      boucle = scanner.next().charAt(0);
    } while (Util.lireCharOouN(boucle));
    System.out.println("Merci d'avoir utilisé notre programme, à la prochaine fois ....");
  }

  private static void menuPrincipal() {
    scanner = new java.util.Scanner(System.in);
    System.out.println("--------------------------------------------------------");
    System.out.println("          Bienvenue sur le nouveau Wikipedia");
    System.out.println("--------------------------------------------------------");
    System.out.println("1. Calculer le plus court chemin d'un sommet à un autre");
    int choix = Integer.valueOf(scanner.nextLine());
    switch (choix) {
      case 1:
        plusCourt();
        break;
      default:
        System.out.println("Mauvais chiffre entré, faîtes attention la prochaine fois");
    }
  }

  private static void plusCourt() {
    String sommetDepart;
    do {
      System.out.println("Veuillez entrer un sommet de départ :");
      sommetDepart = scanner.nextLine();
    } while (!task.existe(sommetDepart));
    String sommetArrivee;
    do {
      System.out.println("Veuilliez entrer le sommet d'arrivée : ");
      sommetArrivee = scanner.nextLine();
    } while (!task.existe(sommetArrivee));
    List<Sommet> chemin = task.algorithme(task.getSommetByString(sommetDepart), task.getSommetByString(sommetArrivee));
    if(chemin == null) {
      System.out.println("Pas de chemin trouvé");
    } else {
      System.out.println("Voici le chemin le plus court : ");
      for (int i = 0; i < chemin.size(); i++) {
        System.out.println(i + ") " + chemin.get(i).getPageWiki().getTitre());
      }
    }
  }

  private static void initialisationProgramme() {
    task = new Task1();
    LireFichier lecture = new LireFichier(task);
    lecture.lectureFichier();
  }
}
