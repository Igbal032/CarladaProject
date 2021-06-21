package az.code.carlada.daos;

import az.code.carlada.dtos.ListingCreationDTO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.dtos.ListingListDTO;
import az.code.carlada.enums.Status;
import az.code.carlada.exceptions.ListingNotFound;
import az.code.carlada.models.Listing;
import az.code.carlada.repositories.ListingRepo;
import az.code.carlada.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ListingDaoImpl implements ListingDAO{

    ListingRepo listingRepository;

    public ListingDaoImpl(ListingRepo listingRepository) {
        this.listingRepository = listingRepository;
    }

        @Override
        public List<Listing> getAllListing() {
            List<Listing> listings = listingRepository.findAll();
            if(listings.isEmpty())
                throw new ListingNotFound("List is empty");
            return  listings;
        }

    @Override
    public List<Listing> getAllVipListing() {
        List<Listing> listings = listingRepository.getAllByTypeEquals(Status.VIP);
        if(listings.isEmpty())
            throw new ListingNotFound("List is empty");
        return  listings;
    }

    @Override
    public List<Listing> getAllListingByUsername(String username) {
        List<Listing> listings = listingRepository.getAllByAppUserUsername(username);
        if(listings.isEmpty())
            throw new ListingNotFound("List is empty");
        return  listings;
    }

    @Override
    public Listing getAllListingByUsernameById(String username, Long id) {
        Listing listing = listingRepository.getListingByAppUserUsernameAndId(username, id);
        if (listing==null){
            throw new ListingNotFound("Not such a listing");
        }
        return listingRepository.getListingByAppUserUsernameAndId(username, id);
    }

    @Override
    public Listing getListingById(Long id) {
        Optional<Listing> listing = listingRepository.findById(id);
        System.out.println(listing);
        if(!listing.isPresent())
            throw new ListingNotFound("Not such a listing");
        return listing.get();
    }

    @Override
    public Listing createListing(Listing listing) {
        return listingRepository.save(listing);
    }

    @Override
    public void delete(long id) {
        if(!listingRepository.findById(id).isPresent())
            throw new ListingNotFound("Not such a listing");
        else
            listingRepository.deleteById(id);
    }
}
