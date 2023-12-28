package guru.springframework.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbeerservice.bootstrap.BeerLoader;
import guru.springframework.msscbeerservice.services.BeerService;
import guru.springframework.msscbeerservice.web.model.BeerDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static guru.springframework.msscbeerservice.web.enums.BeerStyleEnum.ALE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    private static final String API_V1_BEER = "/api/v1/beer/";
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get(API_V1_BEER + UUID.randomUUID())
                        .accept(APPLICATION_JSON))
                .andExpect(status()
                        .isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        when(beerService.saveNewBeer(any(BeerDto.class))).thenReturn(getValidBeerDto());
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post(API_V1_BEER)
                        .contentType(APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void updateBeer() throws Exception {
        when(beerService.updateBeerById(any(UUID.class), any(BeerDto.class))).thenReturn(getValidBeerDto());
        BeerDto beerDto = getValidBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put(API_V1_BEER + UUID.randomUUID())
                        .contentType(APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    BeerDto getValidBeerDto() {
        return BeerDto.builder()
                .beerName("My Beer")
                .beerStyle(ALE)
                .price(BigDecimal.valueOf(2.99))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }
}