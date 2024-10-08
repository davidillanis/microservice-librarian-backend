package org.microservice.librarian.service.implentation;

import jakarta.persistence.EntityNotFoundException;
import org.microservice.librarian.client.UserRoleClient;
import org.microservice.librarian.model.entity.LoanEntity;
import org.microservice.librarian.model.repository.LoanRepository;
import org.microservice.librarian.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private UserRoleClient userRoleClient;


    @Override
    public void createEntity(LoanEntity obj) {
        userRoleClient.getUserRoleByUsername(obj.getStudentEntity())
                .orElseThrow(()->new EntityNotFoundException("This student by id "+obj.getStudentEntity()+" does not exist"));
        userRoleClient.getUserRoleByUsername(obj.getLibrarianEntity())
                .orElseThrow(()->new EntityNotFoundException("This librarian by id "+obj.getLibrarianEntity()+" does not exist"));
        loanRepository.save(obj);
    }

    @Override
    public List<LoanEntity> getListEntity() {
        return loanRepository.findAll().stream().map(loan -> {
            loan.getCopyBookEntity().setBookEntity(null);
            loan.getCopyBookEntity().setLoanEntities(null);
            loan.getCopyBookEntity().setRequestEntities(null);
            return loan;
        }).collect(Collectors.toList());
    }

    @Override
    public LoanEntity getEntity(Integer id) {
        LoanEntity loanEntity = loanRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("This id "+id+" does not exist!"));
        loanEntity.getCopyBookEntity().setBookEntity(null);
        loanEntity.getCopyBookEntity().setLoanEntities(null);
        loanEntity.getCopyBookEntity().setRequestEntities(null);
        return loanEntity;
    }

    @Override
    public void updateEntity(LoanEntity obj) {
        LoanEntity loanEntity=loanRepository.findById(obj.getIdPrest())
                .orElseThrow(()->new EntityNotFoundException("This id "+obj.getIdPrest()+" does not exist!"));
        userRoleClient.getUserRoleByUsername(obj.getStudentEntity())
                .orElseThrow(()->new EntityNotFoundException("This student by id "+obj.getStudentEntity()+" does not exist"));
        userRoleClient.getUserRoleByUsername(obj.getLibrarianEntity())
                .orElseThrow(()->new EntityNotFoundException("This librarian by id "+obj.getLibrarianEntity()+" does not exist"));
        loanEntity.setFechPres(obj.getFechPres());
        loanEntity.setFechDevoPres(obj.getFechDevoPres());
        loanEntity.setObsePres(obj.getObsePres());
        loanEntity.setEstaPres(obj.getEstaPres());
        loanEntity.setCopyBookEntity(obj.getCopyBookEntity());
        loanEntity.setLibrarianEntity(obj.getLibrarianEntity());
        loanEntity.setStudentEntity(obj.getStudentEntity());
        loanRepository.save(loanEntity);
    }

    @Override
    public void deleteEntity(Integer id) {
        LoanEntity loanEntity=loanRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("This id "+id+" does not exist!"));
        loanRepository.delete(loanEntity);
    }
}
