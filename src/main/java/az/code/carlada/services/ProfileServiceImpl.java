package az.code.carlada.services;

import az.code.carlada.daos.interfaces.TransactionDAO;
import az.code.carlada.dtos.TransactionListDTO;
import az.code.carlada.models.Transaction;
import az.code.carlada.services.interfaces.ProfileService;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    TransactionDAO transactionDAO;

    public ProfileServiceImpl(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    @Override
    public TransactionListDTO addAmount(String username, Double amount) {
        return convertTranToDTO(transactionDAO.addAmount(username,amount));
    }
    @Override
    public TransactionListDTO payForVipStatus(Long listingId, String username) {
        return convertTranToDTO(transactionDAO.payForListingStatus(listingId,"VIP",username));
    }
    @Override
    public TransactionListDTO payForStandardStatus(Long listingId, String username) {
        return convertTranToDTO(transactionDAO.payForListingStatus(listingId,"STANDARD",username));
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
