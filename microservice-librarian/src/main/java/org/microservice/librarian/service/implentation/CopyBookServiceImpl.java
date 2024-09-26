package org.microservice.librarian.service.implentation;

import org.microservice.librarian.model.entity.CopyBookEntity;
import org.microservice.librarian.model.repository.CopyBookRepository;
import org.microservice.librarian.service.CopyBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CopyBookServiceImpl implements CopyBookService {
    @Autowired
    private CopyBookRepository copyBookRepository;

    @Override
    public boolean createEntity(CopyBookEntity obj) {
        if(obj!=null){
            copyBookRepository.save(obj);
            return true;
        }
        return false;
    }

    @Override
    public List<CopyBookEntity> getListEntity() {
        return copyBookRepository.findAll();
    }

    @Override
    public CopyBookEntity getEntity(String id) {
        return copyBookRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateEntity(CopyBookEntity obj) {
        CopyBookEntity copyBook=copyBookRepository.findById(obj.getCodiEjem()).orElse(null);
        if(copyBook!=null){
            copyBook.setLocaEjem(obj.getLocaEjem());
            copyBook.setEstaEjem(obj.getEstaEjem());
            copyBook.setHabiEjem(obj.isHabiEjem());
            copyBook.setBookEntity(obj.getBookEntity());
            copyBook.setEstaEjem(obj.getEstaEjem());
            //copyBook.setRequestEntities(obj.getRequestEntities());
            //copyBook.setLoanEntities(obj.getLoanEntities());
            copyBookRepository.save(copyBook);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteEntity(String id) {
        CopyBookEntity copyBook=copyBookRepository.findById(id).orElse(null);
        if(copyBook!=null){
            copyBookRepository.delete(copyBook);
            return true;
        }
        return false;
    }
}