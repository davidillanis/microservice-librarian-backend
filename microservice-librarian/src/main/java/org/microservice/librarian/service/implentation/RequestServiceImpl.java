package org.microservice.librarian.service.implentation;

import org.microservice.librarian.client.UserRoleClient;
import org.microservice.librarian.model.entity.RequestEntity;
import org.microservice.librarian.model.repository.RequestRepository;
import org.microservice.librarian.service.RequestService;
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
    public boolean createEntity(RequestEntity obj) {
        try {
            userRoleClient.getUserRoleByUsername(obj.getStudentEntity());
            requestRepository.save(obj);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<RequestEntity> getListEntity() {
        return requestRepository.findAll();
    }

    @Override
    public RequestEntity getEntity(Integer id) {
        return requestRepository.findById(id).orElse(null);
    }

    @Override
    public boolean updateEntity(RequestEntity obj) {
        RequestEntity requestEntity = requestRepository.findById(obj.getIdSoli()).orElse(null);
        try {
            userRoleClient.getUserRoleByUsername(obj.getStudentEntity());
            requestEntity.setEstaSoli(obj.getEstaSoli());
            requestEntity.setFechSoli(obj.getFechSoli());
            requestEntity.setStudentEntity(obj.getStudentEntity());
            requestEntity.setCopyBookEntity(obj.getCopyBookEntity());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteEntity(Integer id) {
        RequestEntity requestEntity = requestRepository.findById(id).orElse(null);
        if(requestEntity!=null){
            requestRepository.delete(requestEntity);
            return true;
        }
        return false;
    }
}
