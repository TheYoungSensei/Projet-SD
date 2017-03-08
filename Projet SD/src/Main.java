
public class Main {
  
  public static java.util.Scanner scanner = new java.util.Scanner(System.in);
  
  public static void main(String[] args) {
    Task1 task = new Task1();
    LireFichier lecture = new LireFichier(task);
    lecture.lectureFichier();
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
    task.affichage();
  }
}
