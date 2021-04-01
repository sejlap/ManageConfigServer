package ServiceTests;

import com.example.rentservice.rentservice.Controllers.RealEstateController;
import com.example.rentservice.rentservice.ErrorHandling.GlobalErrorHandler;
import com.example.rentservice.rentservice.ErrorHandling.InvalidRequestException;
import com.example.rentservice.rentservice.Models.RealEstate;
import com.example.rentservice.rentservice.Services.RealEstateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;

@SpringBootTest
@AutoConfigureMockMvc
public class RealEstateControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RealEstateService realEstateService;

    @Before("")
    public void setup() {
        this.mvc = MockMvcBuilders.standaloneSetup(RealEstateController.class)
                .setControllerAdvice(new GlobalErrorHandler())
                .build();
    }

    @Test
    public void findRealEstateById_ShouldReturnOkWithResult() throws Exception {
        RealEstate expected = new RealEstate("TestBane", 250000.0, "Adresa 123", "BiH", "Sarajevo", "Namjestena kuca", false, new Date(), new Date(), null, null);
        expected.setRealEstateId(1);

        given(realEstateService.findRealEstateById(1)).willReturn(new ResponseEntity<>(expected, HttpStatus.OK));

        mvc.perform(get("/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.realEstateId", is(expected.getRealEstateId())))
                .andExpect(jsonPath("$.name", is("TestBane")));
    }

    @Test
    public void findRealEstateById_ShouldReturn400_WhenIdIsInvalid() throws Exception {
        given(realEstateService.findRealEstateById(anyInt()))
                .willThrow(new InvalidRequestException("Received Id is not valid."));

        mvc.perform(get("/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addNewRealEstate_ShouldReturnOkWithResult() throws Exception {
        RealEstate expected = new RealEstate("TestBane", 250000.0, "Adresa 123", "BiH", "Sarajevo", "Namjestena kuca", false, new Date(), new Date(), null, null);
        expected.setRealEstateId(1);

        given(realEstateService.saveRealEstate(expected)).willReturn(new ResponseEntity<>(expected, HttpStatus.OK));

        mvc.perform(post("/add")
                .content(new ObjectMapper().writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void addNewRealEstate_ShouldReturn400_WhenRequestIsInvalid() throws Exception {
        RealEstate expected = new RealEstate("", 250000.0, "Adresa 123", "BiH", "", "Namjestena kuca", false, new Date(), new Date(), null, null);
        expected.setRealEstateId(1);

        given(realEstateService.saveRealEstate(ArgumentMatchers.<RealEstate>any()))
                .willThrow(new InvalidRequestException("Properties that must be provided: Name, City."));

        mvc.perform(post("/add")
                .content(new ObjectMapper().writeValueAsString(expected))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

}