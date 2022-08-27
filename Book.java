package libraryMgmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Book {
	
	private String title;
	private int nVolumes;
	private List<String> authors;
	
	private Map<Integer,Volume> volumes = new HashMap<>();
	
	private Map<Integer,Loan> loans = new HashMap<>();
	
	private int inizio, fine;
	
	public Book(String title, int nVolumes, List<String> authors, int progressivo) {
		this.title = title;
		this.nVolumes=nVolumes;
		this.setAuthors(authors);
		this.setInizio(progressivo);
		this.setFine(progressivo+nVolumes-1);
		
		for(int i=progressivo;i<=fine;i++) {
			volumes.put(i,new Volume() );
		}
	}
	
	//get e set
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public int getnVolumes() {
		return nVolumes;
	}
	public void setnVolumes(int nVolumes) {
		this.nVolumes = nVolumes;
	}

	public List<String> getAuthors() {
		return authors;
	}
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}

	public Map<Integer, Volume> getVolumes() {
		return volumes;
	}
	public void setVolumes(Map<Integer, Volume> volumes) {
		this.volumes = volumes;
	}
	public int getFreeVolumes() {
		int count=0;
		for(Volume v : volumes.values()) {
			if(!v.isPreso()) count++;
		}
		return count;
	}
	
	public int getInizio() {
		return inizio;
	}
	public void setInizio(int inizio) {
		this.inizio = inizio;
	}
	
	public int getFine() {
		return fine;
	}
	public void setFine(int fine) {
		this.fine = fine;
	}
	
	@Override
	public String toString() {
		return inizio + ":" + fine;
	}

	public void addLoan(Loan l, int indexLoan) {
		loans.put(indexLoan, l);
	}
	//ritorna l'indice del primo volume libero
	public int getPrimoVolLibero() {
		int i=inizio; Volume v;
		
		for(i=inizio;i<=fine;i++) {
			v=volumes.get(i);
			if( !v.isPreso() ) {//volume libero
				v.preso(); return i;
			}else if( v.isPreso() ) {//volume non libero
				//non faccio nulla(vado avanti)
			}else if( v.isPreso() && i==fine) {//ultimo volume non libero
				return -1;
			}
		}
		
		return 0;
	}

	public boolean thereIsAVolume() {
		if(this.getFreeVolumes()==0) {
			return false;
		}
		return true;
	}

	public void removeLoan(int loanIndex) {
		Loan l = loans.get(loanIndex);	
		volumes.get(l.getIndexVolume()).restituito();;
		//loans.remove(loanIndex);
	}
}