package distributore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Distributore {
	private List<Bevanda> bevandeDisponibili;
	private List<Colonna> colonne;
	private List<Tessera> tessere;
	
	public Distributore() {
		this.bevandeDisponibili = new ArrayList<Bevanda>();
		this.colonne =  new ArrayList<Colonna>();
		this.tessere =  new ArrayList<Tessera>();
	}
	
	public int lattineDisponibili(String codiceBevanda) {		
		Colonna c = new Colonna();
		int numLattine = 0;
		Iterator<Colonna> it = colonne.iterator();
		while(it.hasNext()){
			c = it.next();
			if(c.getBevanda().getCodice().equals(codiceBevanda))
				numLattine += c.getNumLattine();
		}		
		return numLattine;
	}
	
	public void aggiungiBevanda(Bevanda bevanda) {		
		if(bevandeDisponibili.contains(bevanda))
			System.out.println("Bevanda già inserita");
		else {
			bevandeDisponibili.add(bevanda);		
			System.out.println("Bevanda " + bevanda.getName() + " inserita");
		}	
	}

	public void aggiungiTessera(Tessera tessera) {		
		if(tessere.contains(tessera))
			System.out.println("Tessera già inserita");
		else {
			tessere.add(tessera);
			System.out.println("Tessera " + tessera.getCodice() + " inserita");
		}	
	}
	
	public void aggiornaColonna(int numColonna, Bevanda bevanda, int numLattine) {		
		try{
			colonne.remove(numColonna - 1);
		}catch(IndexOutOfBoundsException e){}	
		colonne.add(numColonna - 1 , new Colonna(bevanda, numLattine));
		System.out.println("Colonna " + numColonna + " aggiornata");
	}
	
	public int eroga(String codiceBevanda, int codiceTessera) {
		Bevanda bevanda = getBevandaFromCodice(codiceBevanda);				
		if(bevanda == null) {
			System.out.println("Codice bevanda non valido");
			return -1;
		} 			 
		Tessera tessera = getTesseraFromCodice(codiceTessera);
		if(tessera == null) {
			System.out.println("Codice tessera non valido");
			return -1;
		}
		if(tessera.getCredito() < bevanda.getPrezzo()) {
			System.out.println("Credito nella tessera insufficiente!!");
			return -1;
		}
		
		Iterator<Colonna> it = colonne.iterator();
		boolean bevandaPresente = false;
		Colonna c;
		int numColonna;
		while(it.hasNext() && !bevandaPresente) {
			c = it.next();
			if(c.getBevanda().equals(bevanda) && c.getNumLattine() > 0) {
				bevandaPresente = true;
				c.setNumLattine(c.getNumLattine() - 1);
				tessera.setCredito(tessera.getCredito() - c.getBevanda().getPrezzo());	
				numColonna = colonne.indexOf(c) + 1;
				System.out.println("Bevanda erogata dalla colonna " + numColonna);
				return numColonna;
			}
		}
		System.out.println("La bevanda richiesta non è presente nel distributore");
		return -1;				
	}
	
	public Bevanda getBevandaFromCodice(String codiceBevanda) {
		Iterator<Bevanda> it = bevandeDisponibili.iterator();
		Bevanda bevanda = new Bevanda();
		
		while(it.hasNext()){
			bevanda = it.next();
			if(bevanda.getCodice().equals(codiceBevanda))
				return bevanda;
				
		}				
		return null;
	}
	
	public Tessera getTesseraFromCodice(int codiceTessera) {
		Iterator<Tessera> it = tessere.iterator();		
		Tessera tessera = new Tessera();							
		while(it.hasNext()){
			tessera = it.next();
			if(tessera.getCodice() == codiceTessera)
				return tessera;												
		}		
		return null;
	}
	
	public String visualizzaBevande() {
		Iterator<Bevanda> it = bevandeDisponibili.iterator();
		String str = "";
		while(it.hasNext()){
			str += it.next().toString();
			str += "\n";
		}
		return str;
	}
	
	public String visualizzaTessere() {
		Iterator<Tessera> it = tessere.iterator();
		String str = "";
		while(it.hasNext()){
			str += it.next().toString();
			str += "\n";
		}
		return str;
	}
	
	public String visualizzaDistributore() {
		Iterator<Colonna> it = colonne.iterator();
		String str = "";
		while(it.hasNext()){
			str += it.next().toString();
			str += "\n";
		}
		return str;
	}
	
	public static void main(String[] args) {
		Distributore distributore = new Distributore(); //istanzio distributore
		
		//Inizializzazione per lettura da tastiera
		InputStreamReader reader = new InputStreamReader (System.in); 		
		BufferedReader myInput = new BufferedReader (reader);
		String input = "";		
		int scelta = 0;
		
		boolean esci = false, esciSottomenu = false, test = false; //condizioni di uscita dai menu/sottomenu		
				
		//Dichiarazione variabili ausiliarie
		String codiceBev = "",nomeBev = "";
		int codiceTes = 0,numLattine = 0, numColonna = 0;
		float prezzoBev = 0, credito = 0;
		Bevanda b = new Bevanda();
		Tessera t = new Tessera();				
		
		while(!esci){//loop infinito menu principale
			System.out.println(distributore.menuStr(0));//mostra menu principale			
			try {		
				input = myInput.readLine(); //leggi scelta per menu principale
				try{
					scelta = Integer.parseInt(input);					
					switch(scelta) {
						
						/*******************************************************
						GESTIONE DISTRIBUTORE
						*******************************************************/
						case 1:{ 
							while(!esciSottomenu) { //loop sottomenu								
								System.out.println(distributore.menuStr(1)); //visualizzo sottomenu								
								
								input = myInput.readLine(); //leggi scelta sottomenu
								
								try{
									scelta = Integer.parseInt(input);
									switch(scelta){
										
										/*******************************************************
										GESTIONE DISTRIBUTORE: AGGIUNGI BEVANDA
										*******************************************************/
										case 1:{											
											System.out.println("\n\nInserisci codice bevanda : ");
											codiceBev = myInput.readLine();
											System.out.println("Inserisci nome bevanda : ");
											nomeBev = myInput.readLine();
											while(!test){
												try{
													System.out.println("Inserisci prezzo bevanda : ");												
													prezzoBev = Float.parseFloat(myInput.readLine());
													test = true;
												}catch(NumberFormatException e){
													System.out.println("Inserisci un prezzo valido");													
												}
											}
											test = false;
											distributore.aggiungiBevanda(new Bevanda(nomeBev,codiceBev,prezzoBev));											
										}break;										
										
										/*******************************************************
										GESTIONE DISTRIBUTORE: AGGIORNA COLONNA
										*******************************************************/
										case 2:{
											System.out.println(distributore.visualizzaBevande());
											while(!test){
												System.out.println("Codice bevanda da inserire nel distributore: ");
												codiceBev = myInput.readLine();											
												b = distributore.getBevandaFromCodice(codiceBev);
												if(b == null)
													System.out.println("Codice bevanda errato");
												else 
													test = true;
											}
											test = false;
											while(!test){
												try{
													System.out.println("Numero di lattine da inserire: ");											
													numLattine = Integer.parseInt(myInput.readLine());
													test = true;
												}catch(NumberFormatException e){
													System.out.println("Inserisci un numero di lattine valido");
												}
											}
											test = false;
											while(!test){
												try{
													System.out.println("Numero di colonna dove inserire: ");											
													numColonna = Integer.parseInt(myInput.readLine());
													if(numColonna != 1 && numColonna != 2 && numColonna != 3 && numColonna != 4)
														System.out.println("Inserisci un numero di colonna valido (da 1 a 4)");	
													else	
														test = true;
												}catch(NumberFormatException e){
													System.out.println("Inserisci un numero di colonna valido");
												}
											}
											test = false;
											distributore.aggiornaColonna(numColonna, b, numLattine);																					
										}break;
										
										/*******************************************************
										GESTIONE DISTRIBUTORE: VISUALIZZA BEVANDE
										*******************************************************/
										case 3:{
											System.out.println(distributore.visualizzaBevande());											
										}break;
										case 4:esciSottomenu = true;break;																		
										default:{
											System.out.println("Scelta non valida");
										}
									}
								}catch(NumberFormatException e){
									System.out.println("Scelta non valida");									
								}	
							}
							esciSottomenu = false;							
						}break; //end gestione distributore			
						
						/*******************************************************
						GESTIONE TESSERE
						*******************************************************/
						case 2:{ 
							while(!esciSottomenu){								
								System.out.println(distributore.menuStr(2)); //visualizzo sottomenu								
								input = myInput.readLine(); //ottengo scelta sottomenu
								try{
									scelta = Integer.parseInt(input);								
									switch(scelta){
										
									/*******************************************************
										GESTIONE TESSERE : AGGIUNGI TESSERA
										*******************************************************/
										case 1:{
											while(!test){
												try{
													System.out.println("Inserisci il codice della tessera: ");											
													codiceTes = Integer.parseInt(myInput.readLine());
													test = true;
												}catch(NumberFormatException e){
													System.out.println("Inserisci un codice valido (numero)");
												}
											}
											test = false;
											while(!test){
												try{
													System.out.println("Inserisci credito tessera : ");												
													credito = Float.parseFloat(myInput.readLine());
													test = true;
												}catch(NumberFormatException e){
													System.out.println("Inserisci un credito valido");													
												}
											}
											test = false;
											distributore.aggiungiTessera(new Tessera(codiceTes, credito));											
										}break;
										
										/*******************************************************
										GESTIONE TESSERE: RICARICA TESSERA
										*******************************************************/
										case 2:{
											System.out.println(distributore.visualizzaTessere());
											while(!test){
												try{
													System.out.println("Inserisci il codice della tessera da ricaricare: ");											
													codiceTes = Integer.parseInt(myInput.readLine());
													t = distributore.getTesseraFromCodice(codiceTes);
													if(t != null)
														test = true;
													else
														System.out.println("Inserisci un codice valido");
												}catch(NumberFormatException e){
													System.out.println("Inserisci un codice valido");
												}
											}
											test = false;
											while(!test){
												try{
													System.out.println("Inserisci importo da ricaricare : ");												
													credito = Float.parseFloat(myInput.readLine());
													test = true;
												}catch(NumberFormatException e){
													System.out.println("Inserisci un importo valido");													
												}
											}
											test = false;											
											t.caricaTessera(credito);
											System.out.println("Tessera ricaricata con successo");
										}break;
										
										/*******************************************************
										GESTIONE TESSERE: VISUALIZZA TESSERE
										*******************************************************/
										case 3:{
											System.out.println(distributore.visualizzaTessere());
										}break;
										case 4:esciSottomenu = true;break;																		
										default:{
											System.out.println("Scelta non valida");
										}
									}
								}catch(NumberFormatException e ){
									System.out.println("Scelta non valida");
								}	
								
							}
							esciSottomenu = false;
						}break; //end gestione tessere
						
						/*******************************************************
						EROGAZIONE
						*******************************************************/
						case 3:{//erogazione
							while(!esciSottomenu){								
								System.out.println(distributore.menuStr(3)); //visualizzo sottomenu								
								input = myInput.readLine(); //ottengo scelta sottomenu
								try{
									scelta = Integer.parseInt(input);									
									switch(scelta){
										
										/*******************************************************
										EROGAZIONE : VISUALIZZA DISTRIBUTORE
										*******************************************************/
										case 1:{
											System.out.println(distributore.visualizzaDistributore());
										}break;
										
										/*******************************************************
										EROGAZIONE : EFFETTUA EROGAZIONE
										*******************************************************/
										case 2:{
											while(!test){
												try{
													System.out.println("Inserisci il codice della tessera da usare: ");											
													codiceTes = Integer.parseInt(myInput.readLine());
													test = true;
													
												}catch(NumberFormatException e){
													System.out.println("Inserisci un codice valido");
												}
											}
											test = false;
											System.out.println("Inserisci codice bevanda : ");
											codiceBev = myInput.readLine();
											distributore.eroga(codiceBev, codiceTes);											
										}break;	
										
										/*******************************************************
										EROGAZIONE : LATTINE DISPONIBILI PER UNA BEVANDA
										*******************************************************/
										case 3 : {
											while(!test) {
												System.out.println("Inserisci codice bevanda");
												codiceBev = myInput.readLine();
												b = distributore.getBevandaFromCodice(codiceBev);
												if(b == null)
													System.out.println("Codice bevanda non valido");
												else
													test = true;												
											}
											System.out.println("Ci sono " + distributore.lattineDisponibili(codiceBev) + " lattine di " + b.getName() + " nel distributore");
											test = false;
										}break;
										case 4:esciSottomenu = true;break;																		
										default:{
											System.out.println("Scelta non valida");										
										}
									}
								}catch(NumberFormatException e){
									System.out.println("Scelta non valida");
								}
							}
							esciSottomenu = false;
						}break; //end erogazione
						case 4:{
							esci = true;
						}break; 
						default:{
							System.out.println("Scelta non valida");
						}
					}
				}catch(NumberFormatException e){
					System.out.println("Scelta non valida");
				}

			}catch(IOException e) {
				System.out.println(e);
			}					
		} //end while				
	}	
	
	private String menuStr(int scelta) {
		String str = "";
		switch(scelta){
			case 0 : {
				str += "********************************************************\n";
				str += "1) Gestione Distributore\n";
				str += "2) Gestione Tessere\n";
				str += "3) Erogazione\n";
				str += "4) Esci\n\n";
				str += "Selezionare un'azione :";				
				return str;
			}
			case 1:{
				str += "********************************************************\n";
				str += "1) Aggiungi Bevanda\n";
				str += "2) Aggiorna Colonna\n";
				str += "3) Visualizza Bevande disponibili\n";
				str += "4) Indietro\n\n";
				str += "Selezionare un'azione :";				
				return str;				
			}						
			case 2:{
				str += "********************************************************\n";
				str += "1) Aggiungi Tessera\n";
				str += "2) Ricarica Tessera\n";
				str += "3) Visualizza Tessere\n";
				str += "4) Indietro\n\n";
				str += "Selezionare un'azione :";				
				return str;
			} 					
			case 3:{
				str += "********************************************************\n";
				str += "1) Visualizza distributore\n";
				str += "2) Effettua erogazione\n";
				str += "3) Controlla disponibilità di una certa bevanda\n";
				str += "4) Indietro\n\n";
				str += "Selezionare un'azione :";				
				return str;
			}
			default: {
				str += "Scelta non valida";
				return str;				
			}
		}			
	}	
}
