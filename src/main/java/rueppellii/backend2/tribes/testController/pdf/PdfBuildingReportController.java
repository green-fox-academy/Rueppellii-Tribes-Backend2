package rueppellii.backend2.tribes.testController.pdf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rueppellii.backend2.tribes.building.persistence.model.Building;
import rueppellii.backend2.tribes.building.service.BuildingService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@RestController
public class PdfBuildingReportController {

    private BuildingService buildingService;

    @Autowired
    public PdfBuildingReportController(BuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @RequestMapping(value = "/api/building/pdf", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> kingdomReport() throws IOException {

        List<Building> buildings = buildingService.findAll();

        ByteArrayInputStream bis = GeneratePdfReport.buildingReport(buildings);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=buildingsreport.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }
}
