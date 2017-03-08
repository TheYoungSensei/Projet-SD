import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

public class LireFichier {

  private static String FICHIER = "WikipediaL.txt";
  private Task1 task;
  
  

  public LireFichier(Task1 task) {
    super();
    this.task = task;
  }
  
  public void lectureFichier() {
    try (BufferedReader br = new BufferedReader(new FileReader(FICHIER))) {
      String[] tailles = br.readLine().split(" ");
      int n = Integer.valueOf(tailles[0]); //Nombre de Sommets
      int m = Integer.valueOf(tailles[1]); //Nombre de liens
      for(int i = 0; i < n; i++) {
        String[] page = br.readLine().split(" ");
        int identifiantProjet = Integer.valueOf(page[0]);
        int identifiantWikipedia = Integer.valueOf(page[1]);
        int taille = Integer.valueOf(page[2]);
        String titre = "";
        for(int mot = 3; mot < page.length; mot++) {
          if(mot == page.length -1) {
            titre += page[mot];
          } else {
            titre += page[mot] + " ";
          }
        }
        PageWikipedia pageWiki = new PageWikipedia(identifiantProjet, identifiantWikipedia, taille, titre);
        Sommet sommet = new Sommet(pageWiki);
        task.sommets.put(identifiantProjet, sommet);
        task.titres.put(titre, sommet);
      }
      for(int i = 0; i < m; i++) {
       String[] lien = br.readLine().split(" ");
       int sommetDepart = Integer.valueOf(lien[0]);
       int sommetArrivee = Integer.valueOf(lien[1]);
       task.sommets.get(sommetDepart).arcsSortants.add(task.sommets.get(sommetArrivee));
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }
}
