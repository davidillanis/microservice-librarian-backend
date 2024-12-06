package org.microservice.librarian.service.implentation;

import jakarta.persistence.EntityNotFoundException;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookIssueClient bookIssueClient;
    @Autowired
    private LoanRepository loanRepository;

    @Override
    public void createEntity(BookEntity obj) {
        bookRepository.save(obj);
    }

    @Override
    public List<BookEntity> getListEntity() {
        return bookRepository.findAll();
    }

    @Override
    public BookEntity getEntity(Integer id) {
        return bookRepository.findById(id).orElseThrow(()->new EntityNotFoundException("this "+id+" id not exist"));
    }

    @Override
    public void updateEntity(BookEntity obj) {
        System.out.println(obj);
        BookEntity bookEntity = bookRepository.findById(obj.getIdLibr())
                .orElseThrow(()->new EntityNotFoundException("this id book not exist"));
        bookEntity.setIdLibr(obj.getIdLibr());
        bookEntity.setTituLibr(obj.getTituLibr());
        bookEntity.setDescLibr(obj.getDescLibr());
        bookEntity.setFechPublLibr(obj.getFechPublLibr());
        bookEntity.setEditLibr(obj.getEditLibr());
        bookEntity.setAutoLibr(obj.getAutoLibr());
        bookEntity.setUrlImage(obj.getUrlImage());
        //bookEntity.setCopyBookEntities(obj.getCopyBookEntities());
        bookRepository.save(bookEntity);
    }

    @Override
    public void deleteEntity(Integer id) {
        BookEntity bookEntity = bookRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("this id "+id+" not exist"));
        bookRepository.delete(bookEntity);
    }

    @Override
    public BookEntity findBookEntityByIsbnLibr(String isbn) {
        return bookRepository.findBookEntityByIsbnLibr(isbn)
                .orElseThrow(()->new EntityNotFoundException("This isbn "+isbn+" not exist or this null"));
    }


    @Override
    public void createBookIssue(BookIssueRequest obj) {
        BookEntity bookEntity=BookEntity.builder()
                .tituLibr(obj.getTituLibr())
                .isbnLibr(obj.getIsbnLibr())
                .descLibr(obj.getDescLibr())
                .fechPublLibr(obj.getFechPublLibr())
                .editLibr(obj.getEditLibr())
                .autoLibr(obj.getAutoLibr())
                .urlImage(obj.getUrlImage())
                .build();

        BookIssueDTO bookIssueDTO= BookIssueDTO.builder().isbnLibr(obj.getIsbnLibr()).listIssue(obj.getListIssue()).build();
         bookRepository.save(bookEntity);
         bookIssueClient.createBookIssue(bookIssueDTO);
    }

    @Override
    public List<BookEntity> getListPopularCopyBook() {
        return bookRepository.getListPopularCopyBook().isEmpty()?
                bookRepository.findAll():bookRepository.getListPopularCopyBook();
    }

    @Override
    public CopyBookEntity searchCopyBookAvailable(String isbn) {
        /*return bookRepository.findBookEntityByIsbnLibr(isbn)
                .map(BookEntity::getCopyBookEntities)
                .flatMap(copyBooks -> copyBooks.stream()
                        .filter(copyBook -> loanRepository.findTopByCopyBookEntity_CodiEjemOrderByIdPrestDesc(copyBook.getCodiEjem())
                                .map(LoanEntity::getEstaPres)
                                .filter(estaPres -> estaPres == 1)
                                .isPresent() && copyBook.isHabiEjem() && copyBook.isHabiEjem())
                        .findFirst())
                .orElse(null);*/
        return bookRepository.findBookEntityByIsbnLibr(isbn)
                .map(BookEntity::getCopyBookEntities)
                .flatMap(copyBooks -> copyBooks.stream()
                        .filter(copyBook ->{
                            if(loanRepository.findTopByCopyBookEntity_CodiEjemOrderByIdPrestDesc(copyBook.getCodiEjem()).orElse(null)!=null) {
                                return loanRepository.findTopByCopyBookEntity_CodiEjemOrderByIdPrestDesc(copyBook.getCodiEjem())
                                        .map(LoanEntity::getEstaPres)
                                        .filter(estaPres -> estaPres == 1)
                                        .isPresent() && copyBook.isHabiEjem() && copyBook.isHabiEjem();
                            }
                            return true;
                        }).findFirst())
                .orElse(null);
    }

    @Override
    public List<CopyBookEntity> listCopyBookAvailable(String isbn) {
        List<CopyBookEntity> copyBookEntityList = bookRepository.findBookEntityByIsbnLibr(isbn)
                .map(BookEntity::getCopyBookEntities)
                .map(copyBooks -> copyBooks.stream()
                        .filter(copyBook ->
                                loanRepository.findTopByCopyBookEntity_CodiEjemOrderByIdPrestDesc(copyBook.getCodiEjem())
                                        .map(LoanEntity::getEstaPres)
                                        .filter(estaPres -> estaPres == 1)
                                        .isPresent()
                                        && copyBook.isHabiEjem()
                        )
                        .collect(Collectors.toList())
                )
                .orElse(Collections.emptyList());

        return copyBookEntityList.stream().peek(copyBook->{
                copyBook.getBookEntity().setCopyBookEntities(null);
                Optional.ofNullable(copyBook.getRequestEntities())
                    .ifPresent(requests -> requests.forEach(copy -> copy.setCopyBookEntity(null)));
                Optional.ofNullable(copyBook.getLoanEntities())
                    .ifPresent(loans -> loans.forEach(copy -> copy.setCopyBookEntity(null)));
            }
        ).collect(Collectors.toList());
    }
}
