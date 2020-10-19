
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import library.entities.IBook;
import library.entities.ICalendar;
import library.entities.ILibrary;
import library.entities.ILoan;
import library.entities.IPatron;
import library.entities.Library;
import library.entities.helpers.BookHelper;
import library.entities.helpers.CalendarFileHelper;
import library.entities.helpers.LoanHelper;
import library.entities.helpers.PatronHelper;

class TestBug2 {
	private LoanHelper iLoanhelper;
	private PatronHelper iPatronHelper;
	private BookHelper iBookHelper;
	private ILibrary library;
	private  CalendarFileHelper calendarHelper;
	private ILoan iLoan;
	private IBook iBook;
	private IPatron iPatron;
	private  ICalendar calendar;
	
	int patronId;
	int bookId = 1;
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}  

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		iLoanhelper = new LoanHelper();
		iPatronHelper = new PatronHelper();
		iBookHelper = new BookHelper();

		iPatron = iPatronHelper.makePatron("sonal", "keraliya", "dalsaniya.sonal46@gmail.com", 123456789L, patronId);
		iBook = iBookHelper.makeBook("Jack Falk", "Testing Computer Software", "12345", 1);
		library = new Library(iBookHelper, iPatronHelper, iLoanhelper);
		iLoan = iLoanhelper.makeLoan(iBook, iPatron);
		calendarHelper = new CalendarFileHelper();
		calendar = calendarHelper.loadCalendar();

		
		patronId = 1;
		bookId = 1;
		patronId = 1;
	}

	@AfterEach
	void tearDown() throws Exception {
		
	}

	@Test
	void testOverdueFine() {
		// arrange
		
		library.commitLoan(iLoan); // loan one book1 patron1
		System.out.println("Loan Due Date:" + iLoan.getDueDate());  // print loan due date
		calendar.incrementDate(4);
		library.checkCurrentLoansOverDue();  // update loan related data
		System.out.println("Current Date:" +calendar.getDate()); // print current date
		
		// act
		double overDueFine = 0.0;
		if (iLoan.isOverDue()) {
			overDueFine = library.calculateOverDueFine(iLoan);
			iLoan.getPatron().incurFine(overDueFine);
		}
		System.out.println("Actual Fine:" +overDueFine); // print fine 
		// assert
		assertEquals(2.00, overDueFine, 0.00);
		
		
	}

}
