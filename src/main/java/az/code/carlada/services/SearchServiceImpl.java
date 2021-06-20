package az.code.carlada.services;

import az.code.carlada.daos.SearchDAO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.dtos.PaginationDTO;
import az.code.carlada.dtos.SearchDTO;
import az.code.carlada.models.Listing;
import az.code.carlada.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {
    SearchDAO searchDAO;
    ModelMapperUtil mapperUtil;

    public SearchServiceImpl(SearchDAO searchDAO, ModelMapper modelMapper) {
        this.searchDAO = searchDAO;
        this.mapperUtil = new ModelMapperUtil(modelMapper);
    }

    public PaginationDTO<ListingListDTO> searchListings(Map<String, String> params) {
        Page<Listing> l = searchDAO.searchListingsByPage(mapperUtil.modelMapper.map(params, SearchDTO.class));
        List<ListingListDTO> listDTOS = l.getContent().stream()
                .map(i -> mapperUtil.convertListingToListDto(i)).collect(Collectors.toList());
        return new PaginationDTO<>(l.hasNext(), l.hasPrevious(), l.getTotalPages(), l.getNumber(), l.getTotalElements(), listDTOS);
    }
}
