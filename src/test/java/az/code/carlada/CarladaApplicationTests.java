package az.code.carlada;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.daos.interfaces.SearchDAO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;
import az.code.carlada.repositories.ListingRepo;
import az.code.carlada.services.SearchServiceImpl;
import az.code.carlada.services.interfaces.ListingService;
import az.code.carlada.services.interfaces.SearchService;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

import static org.mockito.Mockito.when;

@SpringBootTest
class CarladaApplicationTests {

//    @InjectMocks
//    private SearchServiceImpl searchService;
//
//    @MockBean
//    private StudentRepository studentRepository;
//    @MockBean
//    SearchDAO searchDAO;
//    @MockBean
//    ModelMapperComponent mapperService;

//    @Autowired
//    private ListingService listingService;

    @MockBean
    private ListingRepo listingRepository;

    @Test
    public void getListingsTest(){
        //when(listingService.getAllVipListing(0,100)).thenReturn(Stream.of)
        Assertions.assertEquals(1, listingRepository.findAll().size());
    }

//    @Test
//    void contextLoads() {
//        Map<String, String> params = null;
//        params.put("Color","blue");
//        PaginationDTO<ListingListDTO> result = searchService.searchListings(params);
//        System.out.println(result);
//    }

}
