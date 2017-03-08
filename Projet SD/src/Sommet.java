import java.util.HashSet;

public class Sommet {

  public HashSet<Sommet> arcsSortants = new HashSet<Sommet>();
  private PageWikipedia pageWiki;
  
  public Sommet(PageWikipedia pageWiki) {
    super();
    this.pageWiki = pageWiki;
  }

  public PageWikipedia getPageWiki() {
    return pageWiki;
  }

  public void setPageWiki(PageWikipedia pageWiki) {
    this.pageWiki = pageWiki;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((pageWiki == null) ? 0 : pageWiki.hashCode());
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
    Sommet other = (Sommet) obj;
    if (pageWiki == null) {
      if (other.pageWiki != null)
        return false;
    } else if (!pageWiki.equals(other.pageWiki))
      return false;
    return true;
  } 
  
  
  
}
