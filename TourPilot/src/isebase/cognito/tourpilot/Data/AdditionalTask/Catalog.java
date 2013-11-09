package isebase.cognito.tourpilot.Data.AdditionalTask;

public class Catalog {
	
	public enum eCatalogType{
	  btyp_kk, btyp_pk,  btyp_sa, btyp_pr; 
	}
	
	private eCatalogType catalogType;
		
	public eCatalogType getCatalogType() {
		return catalogType;
	}

	public void setCatalogType(eCatalogType catalogType) {
		this.catalogType = catalogType;
	}

	public String getName(){
		return getCatalogName(catalogType);
	}
	
	public Catalog(eCatalogType catalogType){
		setCatalogType(catalogType);
	}	
	
	public static String getCatalogName(eCatalogType catalogType){
		switch (catalogType) {
			case btyp_kk:
				return "Krankenkassenleistung";
			case btyp_pk:
				return "Pfllegekassenleistung";
			case btyp_sa:
				return "Sozialamtsleistung";
			case btyp_pr:
				return "Privateleistung";
			default:
				return "";
		}				
	}	
	
	@Override
	public String toString(){
		return getName();
	}
	
}
