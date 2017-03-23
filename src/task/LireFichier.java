package task;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LireFichier {

	private static String FICHIER = "WikipediaL.txt";
	private Algorithme task;
	private int nombrePages;

	public LireFichier(Algorithme task) {
		super();
		this.task = task;
	}

	public int getNombrePages() {
		return this.nombrePages;
	}

	public void lectureFichier() {
		try (BufferedReader br = new BufferedReader(new FileReader(FICHIER))) {
			String[] tailles = br.readLine().split(" ");
			int n = Integer.valueOf(tailles[0]); // Nombre de Sommets
			int m = Integer.valueOf(tailles[1]); // Nombre de liens
			for (int i = 0; i < n; i++) {
				nombrePages++;
				String[] page = br.readLine().split(" ");
				int identifiantProjet = Integer.valueOf(page[0]);
				int identifiantWikipedia = Integer.valueOf(page[1]);
				int taille = Integer.valueOf(page[2]);
				String titre = "";
				for (int mot = 3; mot < page.length; mot++) {
					if (mot == page.length - 1) {
						titre += page[mot];
					} else {
						titre += page[mot] + " ";
					}
					titre = titre.trim();
				}
				PageWikipedia pageWiki = new PageWikipedia(identifiantProjet, identifiantWikipedia, taille, titre);
				Sommet sommet = new Sommet(pageWiki);
				task.putSommetInit(sommet);
			}
			for (int i = 0; i < m; i++) {
				String toSplit = br.readLine();
				if (toSplit != null) {
					String[] lien = toSplit.split(" ");
					int sommetDepart = 0;
					int sommetArrivee = 0;
					try {
						sommetDepart = Integer.valueOf(lien[0]);
						sommetArrivee = Integer.valueOf(lien[1]);
						if (task.getSommet(sommetDepart) != null)
							task.getSommet(sommetDepart).ajouterArc(task.getSommet(sommetArrivee));
					} catch (NumberFormatException fbn) {
						// IGNORE => ASCII Errors
					}
				}
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
