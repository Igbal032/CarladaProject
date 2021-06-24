package az.code.carlada.services;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;
import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.models.Listing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SearchServiceImplTest {

//    @InjectMocks
//    private SearchServiceImpl searchService;
//
//    @Mock
//    private SearchDAO searchDAO;
//    @Mock
//    private ModelMapperComponent mapperService;
//
//    @Test
//    public void testSearch(){
//        Map<String, String> params = Map.of(
//                "Mark","Mercedes",
//                "Model","E-class");
//        when(searchDAO.searchListingsByPage(Page<SearchDTO.class>))
//                .then();
//        PaginationDTO<ListingListDTO> result  = searchService.searchListings(params);
//        assertEquals(result.getItems().size(),2);
//    }

}