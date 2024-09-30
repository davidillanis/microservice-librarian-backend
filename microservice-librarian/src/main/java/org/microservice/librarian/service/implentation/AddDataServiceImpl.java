package org.microservice.librarian.service.implentation;

import org.microservice.librarian.model.entity.BookEntity;
import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.model.entity.LoanEntity;
import org.microservice.librarian.model.entity.RequestEntity;
import org.microservice.librarian.service.BookService;
import org.microservice.librarian.service.CopyBookService;
import org.microservice.librarian.service.LoanService;
import org.microservice.librarian.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Service
public class AddDataServiceImpl {
    @Autowired
    private BookService bookService;
    @Autowired
    private CopyBookService copyBookService;
    @Autowired
    private RequestService requestService;
    @Autowired
    private LoanService loanService;

    public void addDataBase(){
        addBook();
        addCopyBook();
        addRequest();
        addLoan();
    }

    private void addBook(){
        List<BookEntity> bookEntities= Arrays.asList(
                BookEntity.builder().idLibr(0).tituLibr("To Kill a Mockingbird").isbnLibr("9780061120084").fechPublLibr(LocalDate.now()).editLibr("J.B. Lippincott & Co.").autoLibr("Harper Lee").copyBookEntities(null).build(),
                BookEntity.builder().idLibr(0).tituLibr("1984").isbnLibr("9780451524935").fechPublLibr(LocalDate.now()).editLibr("Secker & Warburg").autoLibr("George Orwell").copyBookEntities(null).build(),
                BookEntity.builder().idLibr(0).tituLibr("Pride and Prejudice").isbnLibr("9780679783268").fechPublLibr(LocalDate.of(1813, 1, 28)).editLibr("T. Egerton, Whitehall").autoLibr("Jane Austen").copyBookEntities(null).build(),
                BookEntity.builder().idLibr(0).tituLibr("The Catcher in the Rye").isbnLibr("9780316769488").fechPublLibr(LocalDate.of(1951, 7, 16)).editLibr("Little, Brown and Company").autoLibr("J.D. Salinger").copyBookEntities(null).build(),
                BookEntity.builder().idLibr(0).tituLibr("The Great Gatsby").isbnLibr("9780743273565").fechPublLibr(LocalDate.of(1925, 4, 10)).editLibr("Charles Scribner's Sons").autoLibr("F. Scott Fitzgerald").copyBookEntities(null).build(),
                BookEntity.builder().idLibr(0).tituLibr("Moby-Dick").isbnLibr("9781503280786").fechPublLibr(LocalDate.of(1851, 10, 18)).editLibr("Harper & Brothers").autoLibr("Herman Melville").copyBookEntities(null).build(),
                BookEntity.builder().idLibr(0).tituLibr("War and Peace").isbnLibr("9780199232765").fechPublLibr(LocalDate.of(1869, 1, 1)).editLibr("The Russian Messenger").autoLibr("Leo Tolstoy").copyBookEntities(null).build(),
                BookEntity.builder().idLibr(0).tituLibr("The Hobbit").isbnLibr("9780547928227").fechPublLibr(LocalDate.of(1937, 9, 21)).editLibr("George Allen & Unwin").autoLibr("J.R.R. Tolkien").copyBookEntities(null).build(),
                BookEntity.builder().idLibr(0).tituLibr("Fahrenheit 451").isbnLibr("9781451673319").fechPublLibr(LocalDate.of(1953, 10, 19)).editLibr("Ballantine Books").autoLibr("Ray Bradbury").copyBookEntities(null).build(),
                BookEntity.builder().idLibr(0).tituLibr("Brave New World").isbnLibr("9780060850524").fechPublLibr(LocalDate.of(1932, 1, 1)).editLibr("Chatto & Windus").autoLibr("Aldous Huxley").copyBookEntities(null).build()
        );
        bookEntities.stream().forEach(bookEntity -> bookService.createEntity(bookEntity));
    }

    private void addCopyBook(){
        List<CopyBookEntity> copyBookEntities=List.of(
                CopyBookEntity.builder().codiEjem("C001").locaEjem("LOCATION01-A").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(1)).build(),
                CopyBookEntity.builder().codiEjem("C002").locaEjem("LOCATION02-B").estaEjem("Regular").habiEjem(true).bookEntity(bookService.getEntity(1)).build(),
                CopyBookEntity.builder().codiEjem("C003").locaEjem("LOCATION03-C").estaEjem("Malo").habiEjem(true).bookEntity(bookService.getEntity(2)).build(),
                CopyBookEntity.builder().codiEjem("C004").locaEjem("LOCATION04-A").estaEjem("Malo").habiEjem(false).bookEntity(bookService.getEntity(2)).build(),
                CopyBookEntity.builder().codiEjem("C005").locaEjem("LOCATION05-B").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(3)).build(),
                CopyBookEntity.builder().codiEjem("C006").locaEjem("LOCATION02-B").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(3)).build(),
                CopyBookEntity.builder().codiEjem("C007").locaEjem("LOCATION01-C").estaEjem("Malo").habiEjem(true).bookEntity(bookService.getEntity(4)).build(),
                CopyBookEntity.builder().codiEjem("C008").locaEjem("LOCATION04-C").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(4)).build(),
                CopyBookEntity.builder().codiEjem("C009").locaEjem("LOCATION03-B").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(5)).build(),
                CopyBookEntity.builder().codiEjem("C010").locaEjem("LOCATION02-B").estaEjem("Malo").habiEjem(true).bookEntity(bookService.getEntity(5)).build(),
                CopyBookEntity.builder().codiEjem("C011").locaEjem("LOCATION03-B").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(6)).build(),
                CopyBookEntity.builder().codiEjem("C012").locaEjem("LOCATION01-A").estaEjem("Malo").habiEjem(true).bookEntity(bookService.getEntity(6)).build(),
                CopyBookEntity.builder().codiEjem("C013").locaEjem("LOCATION02-A").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(7)).build(),
                CopyBookEntity.builder().codiEjem("C014").locaEjem("LOCATION03-B").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(7)).build(),
                CopyBookEntity.builder().codiEjem("C015").locaEjem("LOCATION01-C").estaEjem("Malo").habiEjem(true).bookEntity(bookService.getEntity(8)).build(),
                CopyBookEntity.builder().codiEjem("C016").locaEjem("LOCATION02-A").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(8)).build(),
                CopyBookEntity.builder().codiEjem("C017").locaEjem("LOCATION05-B").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(9)).build(),
                CopyBookEntity.builder().codiEjem("C018").locaEjem("LOCATION01-C").estaEjem("Malo").habiEjem(true).bookEntity(bookService.getEntity(9)).build(),
                CopyBookEntity.builder().codiEjem("C019").locaEjem("LOCATION02-A").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(10)).build(),
                CopyBookEntity.builder().codiEjem("C020").locaEjem("LOCATION03-B").estaEjem("Bueno").habiEjem(true).bookEntity(bookService.getEntity(10)).build()
        );
        copyBookEntities.stream().forEach(t->copyBookService.createEntity(t));
    }

    private void addRequest(){
        List<RequestEntity> requestEntities = List.of(
                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C001")).studentEntity(1).build(),
                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C002")).studentEntity(1).build(),
                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C003")).studentEntity(1).build(),

                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C004")).studentEntity(2).build(),
                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C001")).studentEntity(2).build(),
                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C005")).studentEntity(2).build(),

                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C001")).studentEntity(3).build(),
                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C006")).studentEntity(3).build(),

                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C001")).studentEntity(4).build(),
                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C007")).studentEntity(4).build(),

                RequestEntity.builder().idSoli(0).EstaSoli("Pendiente").fechSoli(LocalDate.now())
                        .copyBookEntity(copyBookService.getEntity("C001")).studentEntity(5).build()
        );

        requestEntities.forEach(request->requestService.createEntity(request));
    }

    private void addLoan(){
        List<LoanEntity> loanEntities = List.of(
                // libro 1
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C001")).studentEntity(1).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C002")).studentEntity(1).librarianEntity(1).build(),

                // libro 2
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C003")).studentEntity(2).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C003")).studentEntity(2).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C003")).studentEntity(2).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C004")).studentEntity(2).librarianEntity(1).build(),

                // libro 3
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C005")).studentEntity(3).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C006")).studentEntity(3).librarianEntity(1).build(),

                // libro 4
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C007")).studentEntity(1).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C008")).studentEntity(1).librarianEntity(1).build(),

                // libro 5
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C009")).studentEntity(1).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C010")).studentEntity(2).librarianEntity(1).build(),

                // libro 6
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C011")).studentEntity(1).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C012")).studentEntity(2).librarianEntity(1).build(),

                // libro 7
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C013")).studentEntity(1).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C014")).studentEntity(1).librarianEntity(1).build(),

                // libro 8
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C015")).studentEntity(1).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C016")).studentEntity(1).librarianEntity(1).build(),

                // libro 9
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C017")).studentEntity(1).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C018")).studentEntity(1).librarianEntity(1).build(),

                // libro 10
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C019")).studentEntity(1).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(0)
                        .copyBookEntity(copyBookService.getEntity("C020")).studentEntity(1).librarianEntity(1).build(),
                LoanEntity.builder().idPrest(0).fechPres(LocalDate.now()).fechDevoPres(LocalDate.now()).obsePres(null).estaPres(1)
                        .copyBookEntity(copyBookService.getEntity("C020")).studentEntity(1).librarianEntity(1).build()
        );

        loanEntities.forEach(loan->loanService.createEntity(loan));
    }
}
