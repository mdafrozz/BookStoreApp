package com.bridgelabz.bookstoreapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.bookstoreapp.dto.BookDTO;
import com.bridgelabz.bookstoreapp.dto.ResponseDTO;
import com.bridgelabz.bookstoreapp.exception.BookException;
import com.bridgelabz.bookstoreapp.model.BookModel;
import com.bridgelabz.bookstoreapp.repository.UserRepository;
import com.bridgelabz.bookstoreapp.service.IBookService;
import com.bridgelabz.bookstoreapp.util.TokenUtil;

@RestController
@RequestMapping("/book")
public class BookController {
    
	@Autowired
    IBookService bookService;
	
	@Autowired
	UserRepository userRepo;
    
	@Autowired
    TokenUtil tokenUtil;
   
	//Welcome Message
    @RequestMapping(value = {"", "/", "/home"}, method = RequestMethod.GET)
    public String homePage() {
        return "Hello! Welcome to Book Store App..!!";
    }
    //Inserting Data
    @PostMapping("/insert/{utoken}")
    public ResponseEntity<ResponseDTO> addBookDetails(@PathVariable String utoken,@RequestBody BookDTO bookDTO){
    	try{
   		int id = tokenUtil.decodeToken(utoken);
   		do {
   		String response = bookService.addBook(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("Book Details Added", response);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        }while(userRepo.findById(id).isPresent());}
    	catch(Exception e){throw new BookException("Invalid User/ Token");}

    }
    
    //Get all Book Details
    @GetMapping("/getall")
    public ResponseEntity<ResponseDTO> getAllBookDetails(BookDTO bookDTO){
        List<BookModel> bookList = bookService.getAllBooks(bookDTO);
        ResponseDTO responseDTO = new ResponseDTO("All Book Details, Number of Books: "+bookList.size(), bookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    //Get the book details by Book ID
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<ResponseDTO> getBookDataById(@PathVariable int id) {
    	BookModel bookModel = bookService.getBookById(id);
        ResponseDTO responseDTO = new ResponseDTO("Book details with ID: "+id,bookModel);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    //Delete book details by ID
    @DeleteMapping("/delete/{book_id}/{utoken}")
    public ResponseEntity <ResponseDTO> deleteBookDataByID(@PathVariable int book_id,@PathVariable String utoken) {
    	try{
       		int id = tokenUtil.decodeToken(utoken);
       	do {
       	String bName = bookService.deleteBook(book_id);
        ResponseDTO responseDTO = new ResponseDTO(bName,"Deleted Successfully: ");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
       	}while(userRepo.findById(id).isPresent());}
    	catch(Exception e){throw new BookException("Invalid User/ Token");}
    }
    
    //Search by Book
    @GetMapping("/searchby/{bookName}")
    public ResponseEntity<ResponseDTO> SearchByBookName(@PathVariable String bookName) {
    	List<BookModel> bookList = bookService.searchbyBookName(bookName);
        ResponseDTO responseDTO = new ResponseDTO("Number of Books: "+bookList.size(),bookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    //Update by Book ID
    @PutMapping("/updatebook/{book_id}/{utoken}")
    public ResponseEntity<ResponseDTO> updateBookbyId(@PathVariable int book_id,@PathVariable String utoken,
    		@RequestBody BookDTO bookDTO) {
    	try{
       		int id = tokenUtil.decodeToken(utoken);
       	do {
   		BookModel bookModel = bookService.updateBookbyId(bookDTO, book_id);
        ResponseDTO responseDTO= new ResponseDTO(bookModel.getBookName(),"Updated Successfully");
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
       	}while(userRepo.findById(id).isPresent());}
    	catch(Exception e){throw new BookException("Invalid User/ Token");}
    }
    
    //Sorting in Ascending order by price
    @GetMapping("/sortbyasc")
    public ResponseEntity<ResponseDTO> sortingAscending(){
        List<BookModel> bookList = bookService.sortAscending();
        ResponseDTO responseDTO = new ResponseDTO("Sorted in Ascending order i.e Price Low-High", bookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    
    //Sorting in Descending order by price
    @GetMapping("/sortbydsc")
    public ResponseEntity<ResponseDTO> sortingDescending(){
        List<BookModel> bookList = bookService.sortDescending();
        ResponseDTO responseDTO = new ResponseDTO("Sorted in Descending order i.e Price High-Low", bookList);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
	
	  //update quantity by Book ID
	  @PutMapping("/updateQty/{id}") 
	  public ResponseEntity<ResponseDTO> updateQuantityById(@PathVariable int id,@RequestParam int qty) { 
	  BookModel response = bookService.updateQuantity(id,qty); 
	  ResponseDTO responseDTO= new ResponseDTO("Book Quantity updated..", response); 
	  return new ResponseEntity<ResponseDTO>(responseDTO, HttpStatus.OK); 
	}
	 
}
