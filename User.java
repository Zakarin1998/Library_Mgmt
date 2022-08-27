package libraryMgmt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static java.util.stream.Collectors.*;
import java.time.LocalDate;

public class User {
	
	private String name;
	private int maxNofBooks;
	private int duration;
	private int inCorso; 
	//R2
	private Map<Integer,Loan> loans = new HashMap<>();
	
	//CTOR
	public User(String name, int maxNofBooks, int duration) {
		this.name = name;
		this.maxNofBooks = maxNofBooks;
		this.duration = duration;
		this.inCorso=0;
	}
	
	//GET AND SET
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getMaxNofBooks() {
		return maxNofBooks;
	}
	public void setMaxNofBooks(int maxNofBooks) {
		this.maxNofBooks = maxNofBooks;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public int getInCorso() {
		return inCorso;
	}
	
	@Override
	public String toString() {
		return name + ":" + maxNofBooks + ":" + duration;
	}

	public void addLoan(Loan l, int loanIndex) {
		loans.put(loanIndex, l);
		inCorso++;
	}
	
	public void removeLoan(int loanIndex) {
		//loans.remove(loanIndex);
		inCorso--;
	}

	public List<Loan> getLoans() {
		return loans.values().stream().collect(toList());
	}

	public boolean inRitardo(LocalDate dateNow) {
		for(Loan l : this.getLoans()) {
			l.setState(dateNow);
			if(l.isOverdue()) {
				return true;
			}
			
		}
		return false;
	}

	public double getAverageDelay() {
		long delay=0, count=0;
		
		for(Loan l : loans.values()) {
			if(l.isClosed()) {
				if(l.getEffettiva().compareTo(l.getRestituzione())>0) {//se ho superato la data prevista
					delay += l.getDelay();count++;
				}else {
					count++;
				}
			}
		}
		return (count==0) ? 0:(double)((double)delay/count);
	}

}
