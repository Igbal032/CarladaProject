package az.code.carlada.services;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.daos.interfaces.SearchDAO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;
import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.enums.BodyType;
import az.code.carlada.models.Listing;
import az.code.carlada.services.interfaces.SearchService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SearchServiceImplTest {
    @Autowired
    SearchDAO searchDAO;
    @Autowired
    ModelMapperComponent mapperComponent;
    @Autowired
    SearchService searchService;


    @Test
    void searchListings() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        SearchDTO requestParams = SearchDTO.builder().page(0).count(10).bodyType(BodyType.BUS).barterOption(true).build();
        Map<String, String> requestMap = mapper.convertValue(requestParams, Map.class);

        Page<Listing> lPage = searchDAO.searchListingsByPage(requestParams);

        List<ListingListDTO> listDTOS = lPage.getContent().stream()
                .map(i -> mapperComponent.convertListingToListDto(i))
                .collect(Collectors.toList());

        PaginationDTO<ListingListDTO> listDTOPaginationDTO = new PaginationDTO<>(lPage.hasNext(),
                lPage.hasPrevious(),
                lPage.getTotalPages(), lPage.getNumber(), lPage.getTotalElements(), listDTOS);

        assertEquals(listDTOPaginationDTO, searchService.searchListings(requestMap));
    }
}