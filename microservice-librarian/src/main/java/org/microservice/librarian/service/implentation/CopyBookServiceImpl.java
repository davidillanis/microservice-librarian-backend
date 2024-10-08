package org.microservice.librarian.service.implentation;

import jakarta.persistence.EntityNotFoundException;
import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.model.repository.CopyBookRepository;
import org.microservice.librarian.service.CopyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CopyBookServiceImpl implements CopyBookService {
    @Autowired
    private CopyBookRepository copyBookRepository;

    @Override
    public void createEntity(CopyBookEntity obj) {
        if(obj==null){
            throw new EntityNotFoundException("This Entity is null");
        }
        if(copyBookRepository.existsById(obj.getCodiEjem())){
            throw new EntityNotFoundException("This ID "+obj.getCodiEjem()+" already exists");
        }
        copyBookRepository.save(obj);
    }

    @Override
    public List<CopyBookEntity> getListEntity() {
        return copyBookRepository.findAll().stream()
                .peek(copyBook -> {
                    //clearEntityRelations
                    copyBook.getBookEntity().setCopyBookEntities(null);
                    Optional.ofNullable(copyBook.getRequestEntities())
                            .ifPresent(requests -> requests.forEach(copy -> copy.setCopyBookEntity(null)));
                    Optional.ofNullable(copyBook.getLoanEntities())
                            .ifPresent(loans -> loans.forEach(copy -> copy.setCopyBookEntity(null)));

                }).collect(Collectors.toList());

    }


    @Override
    public CopyBookEntity getEntity(String id) {
        CopyBookEntity copyBook = copyBookRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Copy book by id "+id+" was not found"));

        //clearEntityRelations
        copyBook.getBookEntity().setCopyBookEntities(null);
        //System.out.println(copyBook.getRequestEntities());
        //copyBook.getRequestEntities().stream().forEach(copy -> copy.setCopyBookEntity(null));
        //copyBook.getLoanEntities().stream().forEach(copy -> copy.setCopyBookEntity(null));
        Optional.ofNullable(copyBook.getRequestEntities())
                .ifPresent(requests -> requests.forEach(copy -> copy.setCopyBookEntity(null)));
        Optional.ofNullable(copyBook.getLoanEntities())
                .ifPresent(loans -> loans.forEach(copy -> copy.setCopyBookEntity(null)));
        return copyBook;
    }

    @Override
    public void updateEntity(CopyBookEntity obj) {
        CopyBookEntity copyBook=copyBookRepository.findById(obj.getCodiEjem())
                .orElseThrow(()->new EntityNotFoundException("Copy book by id "+obj.getCodiEjem()+" was not found"));

        copyBook.setLocaEjem(obj.getLocaEjem());
        copyBook.setEstaEjem(obj.getEstaEjem());
        copyBook.setHabiEjem(obj.isHabiEjem());
        if(obj.getBookEntity()!=null) {
            copyBook.setBookEntity(obj.getBookEntity());
        }
        //copyBook.setRequestEntities(obj.getRequestEntities());
        //copyBook.setLoanEntities(obj.getLoanEntities());
        copyBookRepository.save(copyBook);
    }

    @Override
    public void deleteEntity(String id) {
        CopyBookEntity copyBook=copyBookRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Copy book by id "+id+" was not found"));
        copyBookRepository.delete(copyBook);
    }
}