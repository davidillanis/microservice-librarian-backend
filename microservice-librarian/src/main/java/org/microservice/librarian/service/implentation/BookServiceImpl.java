package org.microservice.librarian.service.implentation;

import org.microservice.librarian.client.BookIssueClient;
import org.microservice.librarian.model.entity.BookEntity;
import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.model.entity.LoanEntity;
import org.microservice.librarian.model.repository.BookRepository;
import org.microservice.librarian.model.repository.LoanRepository;
import org.microservice.librarian.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.microservice.librarian.util.dto.BookIssueDTO;
import org.microservice.librarian.util.http.request.BookIssueRequest;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookIssueClient bookIssueClient;
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public boolean createEntity(BookEntity obj) {
        if(obj!=null){
            bookRepository.save(obj);
            return true;
        }
        return false;
    }

    @Override
    public List<BookEntity> getListEntity() {
        return bookRepository.findAll();
    }

    @Override
    public BookEntity getEntity(Integer id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateEntity(BookEntity obj) {
        if(obj!=null) {
            BookEntity bookEntity = bookRepository.findById(obj.getIdLibr()).orElse(null);
            if(bookEntity!=null) {
                bookEntity.setTituLibr(obj.getTituLibr());
                bookEntity.setDescLibr(obj.getDescLibr());
                bookEntity.setFechPublLibr(obj.getFechPublLibr());
                bookEntity.setEditLibr(obj.getEditLibr());
                bookEntity.setAutoLibr(obj.getAutoLibr());
                //bookEntity.setCopyBookEntities(obj.getCopyBookEntities());
                bookRepository.save(bookEntity);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public boolean deleteEntity(Integer id) {
        if(id!=null && bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
        return false;
    }

    @Override
    public Optional<BookEntity> findBookEntityByIsbnLibr(String isbn) {
        if(isbn!=null){
            return bookRepository.findBookEntityByIsbnLibr(isbn);
        }
        return Optional.empty();
    }


    @Override
    public Boolean createBookIssue(BookIssueRequest obj) {
        if(obj!=null) {
            BookEntity bookEntity=BookEntity.builder()
                    .tituLibr(obj.getTituLibr())
                    .isbnLibr(obj.getIsbnLibr())
                    .descLibr(obj.getDescLibr())
                    .fechPublLibr(obj.getFechPublLibr())
                    .editLibr(obj.getEditLibr())
                    .autoLibr(obj.getAutoLibr())
                    .build();

            BookIssueDTO bookIssueDTO= BookIssueDTO.builder().isbnLibr(obj.getIsbnLibr()).listIssue(obj.getListIssue()).build();

            bookRepository.save(bookEntity);
            bookIssueClient.createBookIssue(bookIssueDTO);
            return true;
        }

        return false;
    }

    @Override
    public List<BookEntity> getListPopularCopyBook() {
        return bookRepository.getListPopularCopyBook();
    }

    @Override
    public CopyBookEntity searchCopyBookAvailable(String isbn) {
        BookEntity bookEntity=bookRepository.findBookEntityByIsbnLibr(isbn).orElse(null);

        return bookRepository.findBookEntityByIsbnLibr(isbn)
                .map(BookEntity::getCopyBookEntities)
                .flatMap(copyBooks -> copyBooks.stream()
                        .filter(copyBook -> loanRepository.findTopByCopyBookEntity_CodiEjemOrderByIdPrestDesc(copyBook.getCodiEjem())
                                .map(LoanEntity::getEstaPres)
                                .filter(estaPres -> estaPres == 1)
                                .isPresent() && copyBook.isHabiEjem() && copyBook.isHabiEjem())
                        .findFirst())
                .orElse(null);
    }
}
