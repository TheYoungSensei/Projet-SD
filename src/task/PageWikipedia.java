package task;

public class PageWikipedia {
  

  private int identifiantProjet;
  private int identifiantWikipedia;
  private int taille;
  private String titre;
  
  public PageWikipedia(int identifiantProjet, int identifiantWikipedia, int taille, String titre) {
    super();
    this.identifiantProjet = identifiantProjet;
    this.identifiantWikipedia = identifiantWikipedia;
    this.taille = taille;
    this.titre = titre;
  }

  public int getIdentifiantProjet() {
    return identifiantProjet;
  }

  public int getIdentifiantWikipedia() {
    return identifiantWikipedia;
  }

  public int getTaille() {
    return taille;
  }

  public String getTitre() {
    return titre;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + identifiantProjet;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PageWikipedia other = (PageWikipedia) obj;
    if (identifiantProjet != other.identifiantProjet)
      return false;
    return true;
  }
  
  
}
