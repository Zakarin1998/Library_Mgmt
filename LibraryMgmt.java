package libraryMgmt;

import java.util.*;
import java.time.*;
import java.time.temporal.ChronoUnit;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

public class LibraryMgmt {
	//R0
	LocalDate date;
	//R1
	private Map<String, Book> books = new HashMap<>();
	private Map<String, User> users = new HashMap<>();
	private Map<Integer, Loan> loans = new HashMap<>();
	
	private int progressivo=1;//numero progressivo per il numero del volume
	//R2
	
	private int indiceLoan=1;//indice progressivo dei prestiti
	
	

	//R0
	/**
	 * Defines the current date
	 * @param date current date
	 */
	public void setCurrentDate(LocalDate date) {
		this.date = date;
	}

	/**
	 * retrieves current library system date
	 * @return current date
	 */
	public LocalDate getCurrentDate () {
		return date;
	}

	/**
	 * Moves current date forward
	 * @param nOfDays number of days forward
	 */
	public void addDays (long nOfDays) {
		this.date = date.plusDays(nOfDays);
		//ogni volta, fai partire le verifiche su tutti i loan
		for(Loan l : loans.values()) {
			l.setState(date);
		}
	}


	//R1
	/**
	 * Add a new book with corresponding volumes
	 * 
	 * @param title    title of the book
	 * @param nVolumes number of volumes available
	 * @param authors  list of authors
	 * @return volume index range
	 * @throws LMException
	 */
	public String addBook(String title, int nVolumes, String... authors) throws LMException {
		
		if(books.containsKey(title)) throw new LMException("Book already exists");
		
		List<String> auth = new ArrayList<>();
		
		for(String s : authors) {
			auth.add(s);
		}
		
		Book b = new Book(title,nVolumes,auth,progressivo);
		this.progressivo += nVolumes;
		
		books.put(title, b);
		
		return b.toString();
	}

	/**
	 * Adds a new user with relative parameters
	 * 
	 * @param name
	 * @param maxNofBooks
	 * @param duration
	 * @return
	 * @throws LMException
	 */
	public String addUser (String name, int maxNofBooks, int duration) throws LMException {
		if(users.containsKey(name)) throw new LMException("User already exists");
		
		User u = new User(name,maxNofBooks,duration);
		
		users.put(name,u);
		
		return u.toString();
	}

	//R2
	/**
	 * Adds a new volume loan in the system.
	 * 
	 * @param user : user name
	 * @param title: book title
	 * @return loan index
	 * @throws LMException
	 */
	public int addLoan (String user, String title) throws LMException {
		User u = users.get(user);
		Book b = books.get(title);
		LocalDate restituzione = this.date.plusDays(u.getDuration());
		LocalDate data = null;
		
		if(u.getInCorso() == u.getMaxNofBooks()) throw new LMException("Max books reached");
		if(u.inRitardo(date)) throw new LMException("User " + user + " has a pending book");
		if(!b.thereIsAVolume()) throw new LMException("Book is not available at the moment");
		
		Loan l = new Loan(b.getPrimoVolLibero(),indiceLoan,restituzione,data,u,b);
		u.addLoan(l,indiceLoan);//inserisce loan in utente u, con indiceLoan 
		b.addLoan(l,indiceLoan);//inserisce loan in libro b, con indiceLoan
		loans.put(indiceLoan,l);
		
		return indiceLoan++;
	}

	/**
	 * Retrieves loan information
	 * 
	 * @param loanIndex
	 * @return information as string
	 */
	public String getLoanInfo (int loanIndex) {
		return loans.get(loanIndex).toString();
	}

	/**
	 * Closes a loan
	 * 
	 * @param loanIndex loan index
	 * @return loan return date
	 */
	public LocalDate closeLoan (int loanIndex)  { //throws LMException
		Loan l = loans.get(loanIndex);
		
		l.setEffettiva(date);//registro la data di consegna
		l.setClosed();;//segno loan come chiusa
		
		User u = l.getUser();
		Book b = l.getBook();
		//tolgo loan dall'utente
		u.removeLoan(loanIndex);
		//tolgo loan dai volumi di libri
		b.removeLoan(loanIndex);
		
		return this.date;
	}


	/**
	 * Retrieves number of volumes currently on loan to user
	 * @param user
	 * @return number of volumes
	 */
	public int numberOfBooks (String user) {
		return users.get(user).getInCorso();
	}

	//R3  statistics

	/**
	 * Returns map of authors grouped by title
	 * 
	 * @return map title -> author list
	 */
	public TreeMap<String, ArrayList<String>> authorsByTitle() {
		TreeMap<String, ArrayList<String>> authorsByTitle = new TreeMap<>();
		//lista di libri
		List<String> orderedBooks = books.values().stream()
				.sorted(comparing(Book::getTitle))
				.map(Book::getTitle)
				.collect(toList());
		
		for(String titolo : orderedBooks) {
			authorsByTitle.put(
					titolo,//key
					(ArrayList<String>) books.get(titolo).getAuthors().stream().sorted().collect(toList()) );//value
		}
		
		return authorsByTitle;
	}


	/**
	 * Retrieves total loans for users (including closed ones)
	 * 
	 * @return map user -> loan number
	 */
	public TreeMap<String, Integer> numberOfTotalLoansByUser() {
		TreeMap<String,Integer> numOfLoanByUser = new TreeMap<>();
		//lista di utenti
		List<String> orderedUsers = users.values().stream()
				.sorted(comparing(User::getName))
				.map(User::getName)
				.collect(toList());
		
		for(String user : orderedUsers) {
			if(users.get(user).getLoans().size()==0) break;
			numOfLoanByUser.put( user , users.get(user).getLoans().size() );
		}
		
		return numOfLoanByUser;
	}

	//R4  queries

	/**
	 * returns the list of loans whose due date is equal to the current date.
	 * 
	 * @return list of loan indexes
	 */
	public List<Integer> dailyOverdue(){
		List<Integer> dailyOverdue = new ArrayList<>();
		
		for(Loan l : loans.values()) {
			if(l.getRestituzione().until(this.date,ChronoUnit.DAYS)==0){
				dailyOverdue.add(l.getIndexLoan());
			}
		}
		
		return dailyOverdue;
	}

	/**
	 * returns the average delay of loan returns for given user
	 * @param userName
	 * @return
	 */
	public double averageDelay(String userName) {
		User u = users.get(userName);
		return u.getAverageDelay();
	}

	/**
	 * returns the number of volumes available for the book with the given title
	 * @param title
	 * @return number of available volumes
	 */
	public long availableVolumes(String title) {
		Book b = books.get(title);
		return b.getFreeVolumes();
	}


}
