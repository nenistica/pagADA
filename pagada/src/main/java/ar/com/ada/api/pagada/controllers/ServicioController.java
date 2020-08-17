package ar.com.ada.api.pagada.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import ar.com.ada.api.pagada.entities.*;
import ar.com.ada.api.pagada.models.request.ServicioRequest;
import ar.com.ada.api.pagada.models.response.GenericResponse;
import ar.com.ada.api.pagada.services.*;
import ar.com.ada.api.pagada.services.ServicioService.ServicioValidacionEnum;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServicioController {

    @Autowired
    EmpresaService empresaService;

    @Autowired
    DeudorService deudorService;

    @Autowired
    ServicioService servicioService;

    @PostMapping("/api/servicios")
    public ResponseEntity<GenericResponse> crearServicio(@RequestBody ServicioRequest servicioReq) {

        GenericResponse genericResponse = new GenericResponse();
        // 1) instanciar el objeto(en este caso el servicio)
        // 2) validar servicio ,si no es todo ok q devuleva un 400
        // 3) crear en la base de datos

        // Comenzamos con el 1) instanciar el objeto(en este caso el servicio)
        Servicio servicio = new Servicio();
        Empresa empresa = empresaService.buscarEmpresaPorId(servicioReq.empresaId);

        servicio.setEmpresa(empresa);

        Deudor deudorEncontrado = deudorService.buscarDeudorPorId(servicioReq.deudorId);
        servicio.setDeudor(deudorEncontrado);

        TipoServicio tipoServicio = servicioService.buscarTipoServicioPorId(servicioReq.tipoServicioId);
        servicio.setTipoServicio(tipoServicio);

        servicio.setTipoComprobante(servicioReq.tipoComprobanteId);
        servicio.setNumero(servicioReq.numero);
        servicio.setFechaEmision(servicioReq.fechaEmision);
        servicio.setFechaVencimiento(servicioReq.fechaVencimiento);
        servicio.setImporte(servicioReq.importe);
        servicio.setMoneda(servicioReq.moneda);
        servicio.setCodigoBarras(servicioReq.codigoBarras);
        servicio.setEstadoId(servicioReq.estadoId);

        // Comenzamos on la seccion 2) validar servicio ,si no es todo ok q devuleva un
        // 400
        ServicioValidacionEnum servicioVResultado;
        servicioVResultado = servicioService.validarServicio(servicio);

        if (servicioVResultado != ServicioValidacionEnum.OK) {
            genericResponse.isOk = false;
            genericResponse.message = "Hubo un error en la validacion del servicio" + servicioVResultado;

            return ResponseEntity.badRequest().body(genericResponse); // Error http 400
        }

        // Comenzamos con la 3) crear en la base de datos
        servicioService.crearSevicio(servicio);

        if (servicio.getServicioId() == null) {
            genericResponse.isOk = false;
            genericResponse.message = "No se pudo crear el servicio";
            return ResponseEntity.badRequest().body(genericResponse);

        } else {
            genericResponse.isOk = true;
            genericResponse.id = servicio.getServicioId();
            genericResponse.message = "Se creó el servicio éxitosamente.";
            return ResponseEntity.ok(genericResponse);
        }

        
    } 
    
    @GetMapping("/api/servicios")
    public ResponseEntity<List<Servicio>> listarServicios(
            @RequestParam(name = "empresa", required = false) Integer empresa,
            @RequestParam(name = "deudor", required = false) Integer deudor,
            @RequestParam(name = "historico", required = false) boolean historico,
            @RequestParam(name = "codigo", required = false) String codigo) {

        List<Servicio> servicios = new ArrayList<>();

        if (codigo != null) {
            servicios = servicioService.listarPorCodigoBarras(codigo);
        } else if (empresa != null && deudor == null) {
            servicios = servicioService.listarServiciosPendientesPorEmpresaId(empresa);
        } else if (empresa != null && deudor != null && historico == false) {
            servicios = servicioService.PendientesPorEmpresaIdYDeudorId(empresa, deudor);
        } else if (empresa != null && deudor != null && historico == true) {
            servicios = servicioService.historicoPorEmpresaIdYDeudorId(empresa, deudor);
        } else {
            servicios = servicioService.listarServicios();
        }
        return ResponseEntity.ok(servicios);
    }


} 