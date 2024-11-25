package org.microservice.librarian.service.implentation;

import jakarta.persistence.EntityNotFoundException;
import org.microservice.librarian.client.UserRoleClient;
import org.microservice.librarian.model.entity.RequestEntity;
import org.microservice.librarian.model.repository.RequestRepository;
import org.microservice.librarian.service.RequestService;
import org.microservice.librarian.util.dto.StudentEntityDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RequestServiceImpl implements RequestService {
    @Autowired
    private RequestRepository requestRepository;
    @Autowired
    private UserRoleClient userRoleClient;

    @Override
    public void createEntity(RequestEntity obj) {
        userRoleClient.getUserRoleByUsername(obj.getStudentEntity())
                .orElseThrow(()->new EntityNotFoundException("This student by id "+obj.getStudentEntity()+" does not exist"));

        if (requestRepository.existsRequestEntityByCopyBookEntity_CodiEjemAndStudentEntityAndEstaSoli(
                obj.getCopyBookEntity().getCodiEjem(), obj.getStudentEntity(), obj.getEstaSoli()).orElse(false)) {
            throw new EntityNotFoundException("El libro ya está solicitado por usted");
        }

        requestRepository.save(obj);
    }

    @Override
    public List<RequestEntity> getListEntity() {
        return requestRepository.findAll();
    }

    @Override
    public List<RequestEntity> getListEntityByUsername(String username){
        StudentEntityDTO student=userRoleClient.getStudentByUsername(username)
                .orElseThrow(()->new EntityNotFoundException("This student by id "+username+" does not exist"));

        return  requestRepository.findAll().stream().filter(request->request.getStudentEntity()==student.getIdAlumn()).toList();
    }

    @Override
    public RequestEntity getEntity(Integer id) {
        return requestRepository.findById(id).orElseThrow(()->new EntityNotFoundException("this "+id+ "not exist"));
    }

    @Override
    public void updateEntity(RequestEntity obj) {
        RequestEntity requestEntity = requestRepository.findById(obj.getIdSoli())
                .orElseThrow(()->new EntityNotFoundException("this "+obj.getIdSoli()+ "not exist"));

        userRoleClient.getUserRoleByUsername(obj.getStudentEntity())
                .orElseThrow(()->new EntityNotFoundException("This student by id "+obj.getStudentEntity()+" does not exist"));
        requestEntity.setEstaSoli(obj.getEstaSoli());
        requestEntity.setFechSoli(obj.getFechSoli());
        requestEntity.setStudentEntity(obj.getStudentEntity());
        requestEntity.setCopyBookEntity(obj.getCopyBookEntity());
        requestRepository.save(requestEntity);
    }

    @Override
    public void deleteEntity(Integer id) {
        RequestEntity requestEntity = requestRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("this "+id+ "not exist"));
        requestRepository.delete(requestEntity);
    }
}
