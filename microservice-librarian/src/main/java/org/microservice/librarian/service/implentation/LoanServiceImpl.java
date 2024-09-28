package org.microservice.librarian.service.implentation;

import org.microservice.librarian.client.UserRoleClient;
import org.microservice.librarian.model.entity.LoanEntity;
import org.microservice.librarian.model.repository.LoanRepository;
import org.microservice.librarian.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRoleClient userRoleClient;


    @Override
    public boolean createEntity(LoanEntity obj) {
        try {
            userRoleClient.getUserRoleByUsername(obj.getStudentEntity());
            userRoleClient.getUserRoleByUsername(obj.getLibrarianEntity());
            loanRepository.save(obj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<LoanEntity> getListEntity() {
        return loanRepository.findAll();
    }

    @Override
    public LoanEntity getEntity(Integer id) {
        return loanRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateEntity(LoanEntity obj) {
        LoanEntity loanEntity=loanRepository.findById(obj.getIdPrest()).orElse(null);
        try {
            userRoleClient.getUserRoleByUsername(obj.getStudentEntity());
            userRoleClient.getUserRoleByUsername(obj.getLibrarianEntity());
            loanEntity.setFechPres(obj.getFechPres());
            loanEntity.setFechDevoPres(obj.getFechDevoPres());
            loanEntity.setObsePres(obj.getObsePres());
            loanEntity.setEstaPres(obj.getEstaPres());
            loanEntity.setCopyBookEntity(obj.getCopyBookEntity());
            loanEntity.setLibrarianEntity(obj.getLibrarianEntity());
            loanEntity.setStudentEntity(obj.getStudentEntity());
            loanRepository.save(loanEntity);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteEntity(Integer id) {
        LoanEntity loanEntity=loanRepository.findById(id).orElse(null);
        if(loanEntity!=null){
            loanRepository.delete(loanEntity);
            return true;
        }
        return false;
    }
}
