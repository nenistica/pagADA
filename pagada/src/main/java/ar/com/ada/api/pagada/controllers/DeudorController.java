package ar.com.ada.api.pagada.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.pagada.entities.Deudor;
import ar.com.ada.api.pagada.models.request.DeudorRequest;
import ar.com.ada.api.pagada.models.response.GenericResponse;
import ar.com.ada.api.pagada.services.DeudorService;

@RestController
public class DeudorController {
    
    /*     
    Crear/Obtener Deudores
	GET /api/deudores
	POST /api/deudores
    */

@Autowired
    DeudorService deudorService;

    @GetMapping("/api/deudores")
    public ResponseEntity<List<Deudor>> listarDeudores() {
        List<Deudor> deudores;
        // to do :obtener lista de deudores a través del service y lo guardamos en la
        // varable deudores

        deudores = deudorService.listarDeudores();
        return ResponseEntity.ok(deudores);

    }
    
    //Se hace el POST 
    @PostMapping("/api/deudores")
    public ResponseEntity<GenericResponse> crearDeudor(@RequestBody DeudorRequest deuR) {
        GenericResponse gr = new GenericResponse();

        // to do: hacer validaciones y crear la empresa a traves del service

        Deudor deu = new Deudor();
        deu.setPaisId(deuR.paisId);
        deu.setTipoIdImpositivo(deuR.tipoIdImpositivo);
        deu.setIdImpositivo(deuR.idImpositivo);
        deu.setNombre(deuR.nombre);

        deudorService.crearDeudor(deu);
        

        if (deu.getDeudorId() != null) {
            gr.isOk = true;
            gr.id = deu.getDeudorId();
            gr.message = "Deudor generado con exito.";
            return ResponseEntity.ok(gr);
        }

        gr.isOk = false;
        gr.message = "No se pudo crear el deudor.";

        return ResponseEntity.badRequest().body(gr); // http 400
    }
}