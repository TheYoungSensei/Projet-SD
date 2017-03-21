package task;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Dijkstra implements Algorithme {
	
	private TreeMap<Integer, LinkedList<Sommet>> hashPoidsSommets = new TreeMap<Integer, LinkedList<Sommet>>((i1, i2) -> i1 - i2);
	private HashSet<Sommet> setSommetsAtteints = new HashSet<Sommet>();

	private HashMap<Sommet, Integer> hashSommetPoids = new HashMap<Sommet, Integer>();
	private HashMap<String, Sommet> hashTitres = new HashMap<String, Sommet>();
	private HashMap<Sommet, Sommet> hashAntecedents = new HashMap<Sommet, Sommet>();
	private HashMap<Integer, Sommet> hashSommetId = new HashMap<Integer, Sommet>();

	private Sommet sommetDepart;
	private Sommet sommetArrivee;

	private int poidsTotal;

	public int getPoidsTotal() {
		return poidsTotal;
	}

	public void affichage() {
		for (Sommet sommet : hashTitres.values()) {
			System.out.println(sommet.getPageWiki().getTitre());
			for (Sommet som : sommet.getArcs()) {
				System.out.println("Lien vers : " + som.getPageWiki().getTitre());
			}
		}
	}

	private Sommet sommetPoidsPlusFaibleNonParcouru() {
		LinkedList<Sommet> sommets = this.hashPoidsSommets.firstEntry().getValue();
		Sommet s = sommets.poll();
		if (sommets.isEmpty())
			this.hashPoidsSommets.pollFirstEntry();
		this.setSommetsAtteints.add(s);
		return s;
	}

	public boolean setSommetDepart(Sommet s) {
		if (!this.hashSommetPoids.containsKey(s))
			return false;
		this.sommetDepart = s;
		return true;
	}

	public boolean setSommetArrivee(Sommet s) {
		if (!this.hashSommetPoids.containsKey(s))
			return false;
		this.sommetArrivee = s;
		return true;
	}

	private void putSommetInHashAntecedents(Sommet sommetCourant, Sommet sommetAntecedent) {
		this.hashAntecedents.put(sommetCourant, sommetAntecedent);

	}

	private void putSommetInHashPoidsSommets(Sommet sommet, int newPoids, int oldPoids) {
		if (this.hashPoidsSommets.containsKey(oldPoids)) {
			LinkedList<Sommet> l = hashPoidsSommets.get(oldPoids);
			l.remove(sommet);
			if (l.isEmpty())
				hashPoidsSommets.remove(oldPoids);
		}
		if (this.hashPoidsSommets.containsKey(newPoids)) {
			this.hashPoidsSommets.get(newPoids).add(sommet);
		} else {
			LinkedList<Sommet> liste = new LinkedList<Sommet>();
			liste.add(sommet);
			this.hashPoidsSommets.put(newPoids, liste);
		}
	}

	private void modifyPoids(Sommet sommet, int newPoids, int oldPoids) {
		putSommetInHashPoidsSommets(sommet, newPoids, oldPoids);
		putSommetInHashSommetPoids(sommet, newPoids);
	}

	private void putSommetInHashSommetPoids(Sommet sommet, int poids) {
		this.hashSommetPoids.put(sommet, poids);
	}

	private void putSommetInHashTitre(Sommet sommet) {
		this.hashTitres.put(sommet.getPageWiki().getTitre(), sommet);
	}

	private void putSommetInHashSommetId(Sommet sommet) {
		this.hashSommetId.put(sommet.getPageWiki().getIdentifiantProjet(), sommet);
	}

	public void putSommetInit(Sommet sommet) {
		putSommetInHashAntecedents(sommet, null);
		modifyPoids(sommet, Integer.MAX_VALUE - 1, Integer.MAX_VALUE);
		putSommetInHashTitre(sommet);
		putSommetInHashSommetId(sommet);
	}

	@Override
	public Sommet getSommet(Integer i) {
		if (!this.hashSommetId.containsKey(i))
			return null;
		else
			return this.hashSommetId.get(i);
	}

	@Override
	public Sommet getSommetByString(String titre) {
		if (!this.hashTitres.containsKey(titre))
			return null;
		else
			return this.hashTitres.get(titre);
	}

	@Override
	public boolean existe(String s) {
		return this.hashTitres.containsKey(s);
	}

	@Override
	public List<Sommet> algorithme(Sommet depart, Sommet Arrive) {
		setSommetDepart(depart);
		setSommetArrivee(Arrive);
		Sommet sommetCourant = this.sommetDepart;
		setSommetsAtteints.add(sommetCourant);
		modifyPoids(sommetCourant, 0, hashSommetPoids.get(sommetCourant));
		while (!sommetCourant.equals(sommetArrivee)) {
			for (Sommet sommetFils : sommetCourant.getArcs()) {
				if (!setSommetsAtteints.contains(sommetFils)) {
					if (sommetFils.equals(Arrive)) {
						modifyPoids(sommetFils,
								hashSommetPoids.get(sommetCourant) + sommetFils.getPageWiki().getTaille(),
								hashSommetPoids.get(sommetFils));
						hashAntecedents.put(sommetFils, sommetCourant);
						sommetCourant = sommetFils;
						return trouverChemin(sommetCourant);
					}
					if (hashSommetPoids.get(sommetCourant) + sommetFils.getPageWiki().getTaille() < hashSommetPoids
							.get(sommetFils) || hashSommetPoids.get(sommetFils) == Integer.MAX_VALUE - 1) {
						modifyPoids(sommetFils,
								hashSommetPoids.get(sommetCourant) + sommetFils.getPageWiki().getTaille(),
								hashSommetPoids.get(sommetFils));
						hashAntecedents.put(sommetFils, sommetCourant);
					}
				}
			}
			sommetCourant = sommetPoidsPlusFaibleNonParcouru();
			if (sommetCourant == null) {
				return new LinkedList<Sommet>();
			}
		}
		if (!sommetCourant.equals(sommetArrivee))
			return new LinkedList<Sommet>();
		else {
			return trouverChemin(sommetCourant);
		}
	}

	private List<Sommet> trouverChemin(Sommet sommetCourant) {
		this.poidsTotal = hashSommetPoids.get(sommetCourant);
		LinkedList<Sommet> cheminTemp = new LinkedList<Sommet>();
		cheminTemp.push(sommetCourant);
		while (!sommetCourant.equals(sommetDepart)) {
			if (hashAntecedents.get(sommetCourant) == null) {
				return null;
			}
			sommetCourant = hashAntecedents.get(sommetCourant);
			cheminTemp.push(sommetCourant);
		}
		return cheminTemp;
	}

}
