package az.code.carlada.services;

import az.code.carlada.daos.UserDAO;
import az.code.carlada.dtos.TransactionListDTO;
import az.code.carlada.models.Transaction;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    UserDAO userDAO;

    public ProfileServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public TransactionListDTO addAmount(String username, Double amount) {
        return convertTranToDTO(userDAO.addAmount(username,amount));
    }
    @Override
    public TransactionListDTO payForVipStatus(Long listingId, String username) {
        return convertTranToDTO(userDAO.payForListingStatus(listingId,"VIP",username));
    }
    @Override
    public TransactionListDTO payForStandardStatus(Long listingId, String username) {
        return convertTranToDTO(userDAO.payForListingStatus(listingId,"STANDARD",username));
    }

    public TransactionListDTO convertTranToDTO(Transaction transaction){
        TransactionListDTO transactionListDTO = TransactionListDTO.builder()
                .amount(transaction.getAmount())
                .listingId(transaction.getListingId())
                .createdAt(transaction.getCreatedDate())
                .build();
        return transactionListDTO;
    }
}
