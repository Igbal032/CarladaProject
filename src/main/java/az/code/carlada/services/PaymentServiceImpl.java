package az.code.carlada.services;

import az.code.carlada.daos.PaymentDAO;
import az.code.carlada.dtos.ListingGetDTO;
import az.code.carlada.enums.Status;
import az.code.carlada.models.Listing;
import az.code.carlada.utils.BasicUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{

    PaymentDAO paymentDAO;

    public PaymentServiceImpl(PaymentDAO paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    @Override
    public Listing payForListingStatus(Long listingId, String statusType, String username) {
        Status status = BasicUtil.getEnumFromString(Status.class,statusType);
        Listing listing = paymentDAO.payForListingStatus(listingId, status, username);
        return listing;
    }
}
