package task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

public class Task1 implements Task {
  private TreeMap<Integer, ArrayList<Sommet>> hashPoidsSommets = new TreeMap<Integer, ArrayList<Sommet>>();
  private HashSet<Sommet> setSommetsNonAtteints = new HashSet<Sommet>();
  
  private HashMap<Sommet, Integer> hashSommetPoids =new HashMap<Sommet,Integer>();
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
    for(Sommet sommet : hashTitres.values()) {
      System.out.println(sommet.getPageWiki().getTitre());
      for(Sommet som : sommet.getArcs()) {
        System.out.println("Lien vers : " + som.getPageWiki().getTitre());
      }
    }
  }
  
  public List<Sommet> algorithmeDijkstra() {
	  Sommet sommetCourant = this.sommetDepart;
	  setSommetsNonAtteints.remove(sommetCourant);
	  modifyPoids(sommetCourant, 0, hashSommetPoids.get(sommetCourant));
	  for(Sommet sommetFils: sommetCourant.getArcs()){
		  if(!setSommetsNonAtteints.contains(sommetFils)) continue;
		  if(hashSommetPoids.get(sommetCourant) + sommetFils.getPageWiki().getTaille() < hashSommetPoids.get(sommetFils) || hashSommetPoids.get(sommetFils)==Integer.MAX_VALUE-1){
			  modifyPoids(sommetFils, hashSommetPoids.get(sommetCourant)+ sommetFils.getPageWiki().getTaille(), hashSommetPoids.get(sommetFils));
			  hashAntecedents.put(sommetFils, sommetCourant);
		  }
	  }
	  while(!sommetCourant.equals(sommetArrivee) && !setSommetsNonAtteints.isEmpty() ){
		  sommetCourant = sommetPoidsPlusFaibleNonParcouru();
		  if(sommetCourant == null) {
			  return null;
		  }
		  for(Sommet sommetFils: sommetCourant.getArcs()){
			  if(!setSommetsNonAtteints.contains(sommetFils)) continue;
			  if(hashSommetPoids.get(sommetCourant) + sommetFils.getPageWiki().getTaille() < hashSommetPoids.get(sommetFils) || hashSommetPoids.get(sommetFils)==Integer.MAX_VALUE-1){
				  modifyPoids(sommetFils, hashSommetPoids.get(sommetCourant) + sommetFils.getPageWiki().getTaille(),hashSommetPoids.get(sommetFils));
				  hashAntecedents.put(sommetFils, sommetCourant);
			  }
		  }
	  }
	  if(!sommetCourant.equals(sommetArrivee))
		  return null;
	  else{
		  this.poidsTotal = hashSommetPoids.get(sommetCourant);
		  LinkedList<Sommet> cheminTemp = new LinkedList<Sommet>();
		  cheminTemp.push(sommetCourant);
		  while(!sommetCourant.equals(sommetDepart)){
			  if(hashAntecedents.get(sommetCourant) == null) {
				  return null;
			  }
			  sommetCourant =hashAntecedents.get(sommetCourant);
			  cheminTemp.push(sommetCourant);
		  }
		  return cheminTemp;
	  }
  }
  
  private Sommet sommetPoidsPlusFaibleNonParcouru(){
	  ArrayList<Sommet> sommets = this.hashPoidsSommets.firstEntry().getValue();
	  Sommet s = sommets.remove(0);
	  if(sommets.isEmpty())
		  this.hashPoidsSommets.remove(hashSommetPoids.get(s));
	  this.setSommetsNonAtteints.remove(s);
	  return s;
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
		if(!this.hashSommetPoids.containsKey(s))
			return false;
		this.sommetDepart = s;
		return true;
	}

	@Override
	public boolean setSommetArrivee(Sommet s) {
		if(!this.hashSommetPoids.containsKey(s))
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

	private void putSommetInHashPoidsSommets(Sommet sommet, int newPoids, int oldPoids) {
		if(this.hashPoidsSommets.containsKey(oldPoids)){
			ArrayList<Sommet> l = hashPoidsSommets.get(oldPoids);
			for(int i=0; i <l.size();i++){
				if(l.get(i).equals(sommet)) l.remove(i);
			}
			if(l.isEmpty()) hashPoidsSommets.remove(oldPoids);
		}
		if(this.hashPoidsSommets.containsKey(newPoids)){
			this.hashPoidsSommets.get(newPoids).add(sommet);
		}
		else{
			ArrayList<Sommet> liste = new ArrayList<Sommet>();
			liste.add(sommet);
			this.hashPoidsSommets.put(newPoids, liste);	
		}	
	}
	
	private void modifyPoids(Sommet sommet, int newPoids, int oldPoids){
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
		
	 @Override
	public void putSommetInit(Sommet sommet) {
		 putSommetInSetNonAtteints(sommet);
		 putSommetInHashAntecedents(sommet, null);
		 modifyPoids(sommet, Integer.MAX_VALUE-1, Integer.MAX_VALUE);
		 putSommetInHashTitre(sommet);
		 putSommetInHashSommetId(sommet);		
	}


}
