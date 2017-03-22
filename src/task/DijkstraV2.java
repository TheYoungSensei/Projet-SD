package task;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class DijkstraV2 implements Algorithme {

	private Map<String, Sommet> titres = new HashMap<String, Sommet>();
	private Map<Sommet, Sommet> antecedents = new HashMap<Sommet, Sommet>();
	private Map<Integer, Sommet> ids = new HashMap<Integer, Sommet>();

	private SortedMap<Sommet, Integer> etiquettesProvisoires;
	private SortedMap<Sommet, Integer> etiquettesDefinitives;

	private int poidsTotal;

	public int getPoidsTotal() {
		return poidsTotal;
	}

	public void affichage() {
		for (Sommet sommet : titres.values()) {
			System.out.println(sommet.getPageWiki().getTitre());
			for (Sommet som : sommet.getArcs()) {
				System.out.println("Lien vers : " + som.getPageWiki().getTitre());
			}
		}
	}

	@Override
	public void putSommetInit(Sommet sommet) {
		this.titres.put(sommet.getPageWiki().getTitre(), sommet);
	    this.ids.put(sommet.getPageWiki().getIdentifiantProjet(), sommet);
	}

	@Override
	public Sommet getSommetByString(String titre) {
		return this.titres.get(titre);
	}

	@Override
	public boolean existe(String s) {
		return titres.containsKey(s);
	}

	@Override
	public List<Sommet> algorithme(Sommet depart, Sommet arrive) {
		etiquettesProvisoires = new TreeMap<Sommet, Integer>();
		etiquettesDefinitives = new TreeMap<Sommet, Integer>();
		
		this.poidsTotal = 0;
		Sommet courant = depart;
		courant.setPoids(0);
		this.etiquettesDefinitives.put(courant, 0);
		while(!courant.equals(arrive)) {
			for(Sommet s : courant.getArcs()) {
				if(!etiquettesDefinitives.containsKey(s)) {
					if(etiquettesProvisoires.containsKey(s)) {
						if(etiquettesProvisoires.get(s) > etiquettesDefinitives.get(courant) + s.getPageWiki().getTaille()) {
							s.setPoids(etiquettesDefinitives.get(courant) + s.getPageWiki().getTaille());
							this.etiquettesProvisoires.put(s, etiquettesDefinitives.get(courant) + s.getPageWiki().getTaille());
							this.antecedents.put(s, courant);
						}
					} else {
						s.setPoids(this.etiquettesDefinitives.get(courant) + s.getPageWiki().getTaille());
						this.etiquettesProvisoires.put(s, (etiquettesDefinitives.get(courant) + s.getPageWiki().getTaille()));
						this.antecedents.put(s, courant);
					}
				}
			}
			if(etiquettesProvisoires.isEmpty()){
				return new LinkedList<Sommet>();
			} 
			if(courant.getPageWiki().getTitre().equals("Java"))
				System.out.println("WTF");
			courant = etiquettesProvisoires.firstKey();
			this.etiquettesDefinitives.put(courant, this.etiquettesProvisoires.get(courant));
			etiquettesProvisoires.remove(courant);
		}
		LinkedList<Sommet> chemin = new LinkedList<Sommet>();
		Sommet etape = arrive;
		while(!etape.equals(depart)) {
			this.poidsTotal += etape.getPageWiki().getTaille();
			chemin.push(etape);
			etape = antecedents.get(etape);
		}
		chemin.push(depart);
		return chemin;
	}
	
	public void affichageEtiquettes() {
		for(Sommet s : this.etiquettesDefinitives.keySet()) {
			if(s.getPageWiki().getTitre().equals("Java"))
				System.out.println(s.getPageWiki().getTitre());
		}
	}

	@Override
	public Sommet getSommet(Integer i) {
		return ids.get(i);
	}
	
}
