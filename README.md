# Library_Mgmt
The program simulates the management of a library. All classes are found in the libraryMgmt package. The main class is LibraryMgmt.
The TestApp class in the example package contains examples and presents the main test cases but not all.
Exceptions are thrown using the LMException class; only the specified checks must be carried out and not all possible ones.
If a method throws an exception there is no change in the data present in the main class

R0 : Dates

Method void setCurrentDate (LocalDate date) is called once and defines the current date. 

Method LocalDate getCurrentDate () returns the current date. 

Method void addDays (long nOfDays) moves forward the current date by the given number of days. 

R1 : Books and Users

The library can have multiple copies (volumes) of the same book. Method  String addBook (String title, int nVolumes, String... authors)  inserts a book and the 
corresponding volumes. A book has a title and a list of authors' names. Each volume has a unique progressive integer index starting at 1.

The result shows the range of the indices of the volumes entered; the range includes the lower and upper index separated by a colon.

The method throws an exception if the title is repeated. 

The volumes are initially available; when a volume is loaned it becomes unavailable and when it is returned it becomes available. 

Method  String addUser (String name, int maxNofBooks, int duration) inserts a user with the name, the maximum number of books he/she can have on loan at the same time, the (maximum) duration in days of their loans. The result shows name, maxNofBooks and duration separated by colons (':'). The method throws an exception if the user is repeated. 

R2 : Loans

Method int addLoan (String user, String title)  generates a loan for the specified user and title. The loan records the volume index, the due date of the loan (which is the current date plus the max duration of the loan for that user) and the return date which is initially missing. The volume belongs to the book indicated by the title and is the one with the lowest index among those available. Each loan has a unique progressive integer index starting at 1. The result gives the index of the loan. 
The method throws an exception in three cases: the user has the maximum number of volumes on active loan (i.e., not yet returned), the user is late in returning some volume, (i.e., a loan is not yet returned and the due date is before the current date), there is no available volume with the required title. 

Method LocalDate closeLoan (int loanIndex) closes the loan indicated by the loanIndex. It sets the return date of the loan to the currentDate of the system and makes the volume available. The number of books on loan to the user is decreased by one. The method gives the return date. 

Method String getLoanInfo (int loanIndex) returns information about the loan indicated by the loanIndex. The information is a string consisting of 5 elements separated by colons: the user€™ name, the index of the loan, the index of the volume, the due date (String) and the state. 

The state (String) can assume 3 values: ongoing, when the loan has not yet been closed (i.e., the return date is missing) and the due date is not before the current date; overdue, when the loan has not yet been closed and the due date is before the current date; closed, when the loan has been closed (i.e., there is a return date). 
Method int numberOfBooks (String userName) gives the number of volumes on loan to the user indicated (i.e., loans have state ongoing or overdue)

R3 : Statistics

Method  authorsByTitle() groups authors by title. The keys are the title of the books and are sorted alphabetically, the lists of authors are also sorted alphabetically. 

Method  numberOfTotalLoansByUser() provides the total number of loans (including closed ones) for each user (name). The keys are the names of the users sorted alphabetically. Users who have not made loans are ignored.

R4 : Queries

Method List dailyOverdue() returns the list of loans not yet closed whose due date is equal to the current date. The method returns the loan indexes. 

Method long availableVolumes(String title) returns the number of volumes available for the book with the given title. A volume is considered available if it is not on loan. 

Method double averageDelay(String userName) returns the average delay of loan returns. The delay is the number of days between the due date and the return date, considering the delay of all loans returned before the due date as a zero days delay. Only closed loans are computed.
