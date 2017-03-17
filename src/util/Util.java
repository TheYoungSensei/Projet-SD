package util;

public class Util {

  public static java.util.Scanner scanner = new java.util.Scanner(System.in);

  public static boolean lireCharOouN(char carac) {
    do {
      if (carac == 'o' || carac == 'O') {
        return true;
      } else if (carac == 'n' || carac == 'N') {
        return false;
      }
      carac = scanner.next().charAt(0);
    } while (true);
  }
  
  public static int lireEntier() {
    int entier;
    try {
      entier = Integer.valueOf(scanner.nextLine());
    } catch (NumberFormatException fbn) {
      entier = lireEntier();
    }
    return entier;
  }
}
