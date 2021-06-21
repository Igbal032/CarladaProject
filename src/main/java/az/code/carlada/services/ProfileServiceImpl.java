package az.code.carlada.services;

import az.code.carlada.daos.ProfileDAO;
import az.code.carlada.dtos.TransactionListDTO;
import az.code.carlada.enums.Status;
import az.code.carlada.models.Transaction;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    ProfileDAO profileDAO;
    public ProfileServiceImpl(ProfileDAO userDAO) {
        this.profileDAO = userDAO;
    }

    @Override
    public TransactionListDTO addAmount(String username, Double amount) {
        Transaction transaction = profileDAO.addAmount(username,amount);
        return convertTranToDTO(transaction);
    }
    @Override
    public TransactionListDTO payForVipStatus(Long listingId, String username) {
        Transaction transaction = profileDAO.payForListingStatus(listingId,Status.VIP,username);
        return convertTranToDTO(transaction);
    }
    @Override
    public TransactionListDTO payForStandardStatus(Long listingId, String username) {
        Transaction transaction = profileDAO.payForListingStatus(listingId,Status.STANDARD,username);
        return convertTranToDTO(transaction);
    }

    public TransactionListDTO convertTranToDTO(Transaction transaction){
        TransactionListDTO transactionListDTO = TransactionListDTO.builder()
                .amount(transaction.getAmount())
                .listingId(transaction.getListingId())
                .createdAt(transaction.getCreatedDate())
                .transactionType(transaction.getTransactionType())
                .build();
        return transactionListDTO;
    }
}
