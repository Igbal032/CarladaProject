package az.code.carlada;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.daos.interfaces.SearchDAO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;
import az.code.carlada.services.SearchServiceImpl;
import az.code.carlada.services.interfaces.SearchService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Map;

@SpringBootTest
class CarladaApplicationTests {

    @InjectMocks
    private SearchServiceImpl searchService;

//    @MockBean
//    private StudentRepository studentRepository;
    @MockBean
    SearchDAO searchDAO;
    @MockBean
    ModelMapperComponent mapperService;

    @Test
    void contextLoads() {
        Map<String, String> params = null;
        params.put("Color","blue");
        PaginationDTO<ListingListDTO> result = searchService.searchListings(params);
        System.out.println(result);
    }

}
