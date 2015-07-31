package distributore;

public class Colonna {
	private Bevanda bevanda;
	private int numLattine;
	
	public Colonna() {
		super();
	}
	
	public Colonna(Bevanda bevanda, int numLattine) {
		super();
		this.bevanda = bevanda;
		this.numLattine = numLattine;
	}
	
	public Bevanda getBevanda() {
		return bevanda;
	}
	
	public void setBevanda(Bevanda bevanda) {
		this.bevanda = bevanda;
	}
	
	public int getNumLattine() {
		return numLattine;
	}
	
	public void setNumLattine(int numLattine) {
		this.numLattine = numLattine;
	}
	
	public String toString() { //OVERLOAD. SOVRASCRIVO IL METODO TO STRING DELLA CLASSE OBJECT
		return "Bevanda: " + this.bevanda.toString() + ", Lattine disponibili : " + this.numLattine;
	}	
}
