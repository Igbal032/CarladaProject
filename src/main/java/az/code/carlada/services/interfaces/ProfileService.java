package az.code.carlada.services.interfaces;

import az.code.carlada.dtos.TransactionListDTO;

public interface ProfileService {
    TransactionListDTO addAmount(String username, Double amount);
    TransactionListDTO payForVipStatus(Long listingId,  String username);
    TransactionListDTO payForStandardStatus(Long listingId,  String username);
}
