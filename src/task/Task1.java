package task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedMap;
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
  
  
  public void affichage() {
    for(Sommet sommet : hashTitres.values()) {
      System.out.println(sommet.getPageWiki().getTitre());
      for(Sommet som : sommet.getArcs()) {
        System.out.println("Lien vers : " + som.getPageWiki().getTitre());
      }
    }
  }
  
  public ArrayList<Sommet> algorithmeDijkstra() {
	  Sommet sommetCourant = this.sommetDepart;
	  setSommetsNonAtteints.remove(sommetCourant);
	  modifyPoids(sommetCourant,0, hashSommetPoids.get(sommetCourant));
	  for(Sommet sommetFils: sommetCourant.getArcs()){
		  if(!setSommetsNonAtteints.contains(sommetFils)) continue;
		  if(hashSommetPoids.get(sommetCourant) +1 < hashSommetPoids.get(sommetFils) || hashSommetPoids.get(sommetFils)==-1){
			  modifyPoids(sommetFils, hashSommetPoids.get(sommetCourant)+1, hashSommetPoids.get(sommetFils));
			  hashAntecedents.put(sommetFils, sommetCourant);
		  }
	  }
	  while(!sommetCourant.equals(sommetArrivee) && !setSommetsNonAtteints.isEmpty() ){
		  sommetCourant = sommetPoidsPlusFaibleNonParcouru();
		  if(sommetCourant == null) {
			  return null;
		  }
		  setSommetsNonAtteints.remove(sommetCourant);
		  for(Sommet sommetFils: sommetCourant.getArcs()){
			  if(!setSommetsNonAtteints.contains(sommetFils)) continue;
			  if(hashSommetPoids.get(sommetCourant) +1 < hashSommetPoids.get(sommetFils) || hashSommetPoids.get(sommetFils)==-1){
				  modifyPoids(sommetFils, hashSommetPoids.get(sommetCourant)+1,hashSommetPoids.get(sommetFils));
				  hashAntecedents.put(sommetFils, sommetCourant);
			  }
		  }
	  }
	  if(!sommetCourant.equals(sommetArrivee))
		  return null;
	  else{
		  ArrayList<Sommet> cheminTemp = new ArrayList<Sommet>();
		  cheminTemp.add(sommetCourant);
		  while(!sommetCourant.equals(sommetDepart)){
			  sommetCourant =hashAntecedents.get(sommetCourant);
			  cheminTemp.add(sommetCourant);
		  }
		  ArrayList<Sommet> chemin = new ArrayList<Sommet>();
		  for(int i= cheminTemp.size()-1; i>=0; i--){
			  chemin.add(cheminTemp.get(i));
		  }
		  return chemin;
		  
	  }
  }
  
  private Sommet sommetPoidsPlusFaibleNonParcouru(){
	  for(ArrayList<Sommet> l : this.hashPoidsSommets.values()){
		  if(hashSommetPoids.get(l.get(0)) ==-1) continue;
		  for(int i=0;i < l.size(); i++){
			  if(setSommetsNonAtteints.contains(l.get(i))){
				  System.out.println("yoyoyoyoy"+hashSommetPoids.get(l.get(i)));
				  Sommet s =l.remove(i);
				  if(l.isEmpty()) hashPoidsSommets.remove(hashSommetPoids.get(s));
				  return s;
			  }	  
		  }	  
	  }
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
		 modifyPoids(sommet, -1, -2);
		 putSommetInHashTitre(sommet);
		 putSommetInHashSommetId(sommet);
		
	}


}
