package ar.com.ada.api.pagada.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.pagada.entities.Deudor;
import ar.com.ada.api.pagada.repos.DeudorRepository;

@Service
public class DeudorService {

    @Autowired
    DeudorRepository deudorRepository;
	public List<Deudor> listarDeudores() {
		return deudorRepository.findAll();
	}
    
    public void crearDeudor (Deudor deudor){

        deudorRepository.save(deudor);
    }
}