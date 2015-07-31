package distributore;

public class Bevanda {
	private String name, codice;
	private float prezzo;
	
	public Bevanda() {
		super();
	}
	
	public Bevanda(String name, String codice, float prezzo) {
		super();
		this.name = name;
		this.codice = codice;
		this.prezzo = prezzo;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCodice() { 
		return codice;
	}
	
	public void setCodice(String codice) {
		this.codice = codice;
	}
	
	public float getPrezzo() {
		return prezzo;
	}
	
	public void setPrezzo(float prezzo) {
		this.prezzo = prezzo;
	}

	public boolean equals(Bevanda bevanda) {
		if(this.codice == bevanda.getCodice())
			return true;
		return false;
	}
	
	public String toString(){ //OVERLOAD. SOVRASCRIVO IL METODO TO STRING DELLA CLASSE OBJECT
		return "Nome: " + this.name + ", Codice: " + this.codice + ", Prezzo: " + this.prezzo; 
	}
}
