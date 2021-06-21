package az.code.carlada.services;

import az.code.carlada.daos.UserDAO;
import az.code.carlada.daos.UserDAOImpl;
import az.code.carlada.dtos.TransactionListDTO;
import az.code.carlada.enums.Status;
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
        Transaction transaction = userDAO.addAmount(username,amount);
        return convertTranToDTO(transaction);
    }
    @Override
    public TransactionListDTO payForVipStatus(Long listingId, String username) {
        Transaction transaction = userDAO.payForListingStatus(listingId,Status.VIP,username);
        return convertTranToDTO(transaction);
    }
    @Override
    public TransactionListDTO payForStandardStatus(Long listingId, String username) {
        Transaction transaction = userDAO.payForListingStatus(listingId,Status.STANDARD,username);
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
