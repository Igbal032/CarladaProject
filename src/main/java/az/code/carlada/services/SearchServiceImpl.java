package az.code.carlada.services;

import az.code.carlada.components.ModelMapperComponent;
import az.code.carlada.daos.SearchDAO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;
import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.models.Listing;
import az.code.carlada.models.Subscription;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    SearchDAO searchDAO;
    ModelMapperComponent mapperService;

    public SearchServiceImpl(SearchDAO searchDAO, ModelMapperComponent mapperService) {
        this.searchDAO = searchDAO;
        this.mapperService = mapperService;
    }

    @Override
    public PaginationDTO<ListingListDTO> searchListings(Map<String, String> params) {
        Page<Listing> l = searchDAO.searchListingsByPage(mapperService.modelMapper.map(params, SearchDTO.class));
        List<ListingListDTO> listDTOS = l.getContent().stream()
                .map(i -> mapperService.convertListingToListDto(i)).collect(Collectors.toList());
        return new PaginationDTO<>(l.hasNext(), l.hasPrevious(), l.getTotalPages(), l.getNumber(), l.getTotalElements(), listDTOS);
    }
}
