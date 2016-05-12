package cn.agilean.web.controller;

import cn.agilean.model.Book;
import cn.agilean.repository.BookRepository;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @ApiOperation(value="根据书籍编号保存书籍",notes="根据书籍编号保存书籍详细信息")
    @RequestMapping(method = RequestMethod.POST, value = "/{bookId}")
    public Map<String, Object> createBook(@ApiParam(required=true, name="bookId", value="书籍编号") @PathVariable("bookId") String bookId, @RequestBody Map<String, Object> bookMap) {
        Book book = new Book(bookMap.get("name").toString(),
                bookMap.get("isbn").toString(),
                bookMap.get("author").toString(),
                Integer.parseInt(bookMap.get("pages").toString()));
        book.setId(bookId);
        bookRepository.save(book);
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("message", "Book created successfully");
        response.put("book", book);
        return response;
    }

    @ApiOperation(value="Get the currently logged in users details",notes="Uses the remote user logged in")
    @RequestMapping(method = RequestMethod.GET, value = "/{bookId}")
    public Book getBookDetails(@PathVariable("bookId") String bookId) {
        return bookRepository.findOne(bookId);
    }

    @ApiOperation(value="update the currently logged in users details",notes="Uses the remote user logged in")
    @RequestMapping(method = RequestMethod.PUT, value="/{bookId}")
    public Map<String, Object> editBook(@PathVariable("bookId") String bookId,
                                        @RequestBody Map<String, Object> bookMap){
        Book book = new Book(bookMap.get("name").toString(),
                bookMap.get("isbn").toString(),
                bookMap.get("author").toString(),
                Integer.parseInt(bookMap.get("pages").toString()));
        book.setId(bookId);

        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put("message", "Book Updated successfully");
        response.put("book", bookRepository.save(book));
        return response;
    }
}
