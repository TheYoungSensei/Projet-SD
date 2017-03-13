package task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class Task1 implements Task {
  
  private HashMap<Sommet, Integer> hashPoids = new HashMap<Sommet, Integer>();
  private HashMap<String, Sommet> hashTitres = new HashMap<String, Sommet>();
  private HashSet<Sommet> setSommetsNonAtteints;
  private HashMap<Sommet, Sommet> hashAntecedents = new HashMap<Sommet, Sommet>();
  private HashMap<Integer, Sommet> hashSommetId = new HashMap<Integer, Sommet>();
  private Sommet sommetDepart;
  private Sommet sommetArrivee;
  
  
  public void affichage() {
    for(Sommet sommet : hashPoids.keySet()) {
      System.out.println(sommet.getPageWiki().getTitre());
      for(Sommet som : sommet.getArcs()) {
        System.out.println("Lien vers : " + som.getPageWiki().getTitre());
      }
    }
  }
  
  public ArrayList<Sommet> algorithmeDijkstra() {
	  Sommet sommetCourant = this.sommetDepart;
	  setSommetsNonAtteints.remove(sommetCourant);
	  hashPoids.put(sommetCourant, 0);
	  for(Sommet sommetFils: sommetCourant.getArcs()){
		  if(!setSommetsNonAtteints.contains(sommetFils)) continue;
		  if(hashPoids.get(sommetCourant) +1 < hashPoids.get(sommetFils) || hashPoids.get(sommetFils)==-1){
			  hashPoids.put(sommetFils, hashPoids.get(sommetCourant)+1);
			  hashAntecedents.put(sommetFils, sommetCourant);
		  }
	  }
	  while(!sommetCourant.equals(sommetArrivee)){
		  sommetCourant = sommetPoidsPlusFaibleNonParcouru();
		  setSommetsNonAtteints.remove(sommetCourant);
		  for(Sommet sommetFils: sommetCourant.getArcs()){
			  if(!setSommetsNonAtteints.contains(sommetFils)) continue;
			  if(hashPoids.get(sommetCourant) +1 < hashPoids.get(sommetFils) || hashPoids.get(sommetFils)==-1){
				  hashPoids.put(sommetFils, hashPoids.get(sommetCourant)+1);
				  hashAntecedents.put(sommetFils, sommetCourant);
			  }
		  }
	  }
	  if(setSommetsNonAtteints.isEmpty() && !sommetCourant.equals(sommetArrivee))
		  return null;
	  else{
		  ArrayList<Sommet> cheminTemp = new ArrayList();
		  cheminTemp.add(sommetCourant);
		  while(!sommetCourant.equals(sommetDepart)){
			  cheminTemp.add(hashAntecedents.get(sommetCourant));
			  sommetCourant =hashAntecedents.get(sommetCourant);
		  }
		  ArrayList<Sommet> chemin = new ArrayList();
		  for(int i= cheminTemp.size()-1; i>=0; i--){
			  chemin.add(cheminTemp.get(i));
		  }
		  return chemin;
		  
	  }
  }
  
  private Sommet sommetPoidsPlusFaibleNonParcouru(){
	  return null;
  }


	@Override
	public Sommet getSommetById(Integer i) {
		if(!this.hashSommetId.containsKey(i))
			return null;
		else return this.hashSommetId.get(i);
	}
	
	@Override
	public Sommet getSommetByTitre(String s) {
		if(!this.hashTitres.containsKey(s))
			return null;
		else return this.hashTitres.get(s);
	}

	@Override
	public boolean setSommetDepart(Sommet s) {
		if(!this.hashPoids.containsKey(s))
			return false;
		this.sommetDepart = s;
		return true;
	}

	@Override
	public boolean setSommetArrivee(Sommet s) {
		if(!this.hashPoids.containsKey(s))
			return false;
		this.sommetArrivee = s;
		return true;
	}

	private void putSommetInSetNonAtteints(Sommet sommet) {
		this.setSommetsNonAtteints.add(sommet);
		
	}

	private void putSommetInHashAntecedents(Sommet sommetCourant, Sommet sommetAntecedent) {
		this.hashAntecedents.put(sommetCourant, sommetAntecedent );
		
	}

	private void putSommetInHashPoids(Sommet sommet, int poids) {
		this.hashPoids.put(sommet, poids);
		
	}

	private void putSommetInHashTitre(Sommet sommet) {
		this.hashTitres.put(sommet.getPageWiki().getTitre(), sommet);
		
	}


	private void putSommetInHashSommetId(Sommet sommet) {
		this.hashSommetId.put(sommet.getPageWiki().getIdentifiantProjet(), sommet);
		
	}
	
	
	 @Override
	public void putSommetInit(Sommet sommet) {
		 putSommetInSetNonAtteints(sommet);
		 putSommetInHashAntecedents(sommet, null);
		 putSommetInHashPoids(sommet, -1);
		 putSommetInHashTitre(sommet);
		
	}


}
