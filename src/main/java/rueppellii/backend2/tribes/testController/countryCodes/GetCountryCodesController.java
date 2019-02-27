package rueppellii.backend2.tribes.testController.countryCodes;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
public class GetCountryCodesController {

    @GetMapping("/countrycodes")
    @ResponseStatus(HttpStatus.OK)
    public CountryCodesDTO getCountryCodes() throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://restcountries-v1.p.rapidapi.com/all")
                .header("X-RapidAPI-Key", "62ecb153f5mshc9134e66c2e716bp13f7a7jsn7c8bd11b8be3")
                .asJson();
        JSONArray jsonArray = response.getBody().getArray();
        CountryCodesDTO countryCodesDTO = new CountryCodesDTO();
        List<String> countryCodes = IntStream.range(0, jsonArray.length())
                .mapToObj(index -> ((JSONObject)jsonArray.get(index)).optString("alpha3Code"))
                .collect(Collectors.toList());
        countryCodesDTO.setCountryCodes(countryCodes);
        return countryCodesDTO;
    }
}
