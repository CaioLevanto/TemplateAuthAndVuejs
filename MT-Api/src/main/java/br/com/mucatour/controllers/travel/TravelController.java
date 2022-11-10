package br.com.mucatour.controllers.travel;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.mucatour.exception.TravelException;
import br.com.mucatour.model.travel.enums.StatusTravel;
import br.com.mucatour.payload.request.viagem.ViagemRequest;
import br.com.mucatour.services.TravelService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/travel")
public class TravelController {

    TravelService travelService;

    @Autowired
    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @GetMapping("/status")
    public ResponseEntity<?> getStatus() {
        return ResponseEntity.ok(StatusTravel.getListEnum());
    }

    @PostMapping("/request")
    public ResponseEntity<?> requestTravel(@Valid @RequestBody ViagemRequest trDto) throws TravelException {
        return travelService.request(trDto);
    }

}