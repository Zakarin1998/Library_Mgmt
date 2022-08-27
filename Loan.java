package libraryMgmt;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Loan {
	

	public enum Stato{ ongoing , overdue , closed }
	
	private int indexVolume;
	private int indexLoan;
	private LocalDate restituzione;
	private LocalDate effettiva;
	private User utente;
	private Book book;
	private Stato stato;
	
	
	public Loan(int indexVolume, int indiceLoan, LocalDate restituzione, LocalDate data, User utente, Book book) {
		this.indexVolume=indexVolume;
		this.indexLoan=indiceLoan;
		this.restituzione=restituzione;
		this.effettiva=data;
		this.utente=utente;
		this.book=book;
		this.stato=Stato.ongoing;
	}
	
	
	public int getIndexVolume() {
		return indexVolume;
	}
	public void setIndexVolume(int indice) {
		this.indexVolume = indice;
	}
	public int getIndexLoan() {
		return indexLoan;
	}
	public void setIndexLOan(int indexLoan) {
		this.indexLoan = indexLoan;
	}
	public LocalDate getRestituzione() {
		return restituzione;
	}
	public void setRestituzione(LocalDate restituzione) {
		this.restituzione = restituzione;
	}
	public LocalDate getEffettiva() {
		return effettiva;
	}
	public void setEffettiva(LocalDate effettiva) {
		this.effettiva = effettiva;
	}
	public User getUser() {
		return utente;
	}
	public Book getBook() {
		return book;
	}
	
	@Override
	public String toString() {
		return utente.getName() + ":" 
				+ indexLoan + ":" 
				+ indexVolume + ":" 
				+ restituzione.toString() + ":" 
				+ stato.toString();
	}

	public boolean isOverdue() {
		return (stato==Stato.overdue);
	}
	public boolean isOngoing() {
		return (stato==Stato.ongoing);
	}
	public boolean isClosed() {
		return (stato==Stato.closed);
	}

	public void setState(LocalDate date) {
		if(this.effettiva==null) {//se non ho ancora restituito
			if(date.compareTo(restituzione)>0) {//se ho superato la data prevista
				this.stato=Stato.overdue;
			}
		}
		else {
			this.stato=Stato.closed;
		}
	}
	
	public void setClosed() {
		this.stato=Stato.closed;
	}


	public long getDelay() {
		return restituzione.until(effettiva, ChronoUnit.DAYS);
	}
}
