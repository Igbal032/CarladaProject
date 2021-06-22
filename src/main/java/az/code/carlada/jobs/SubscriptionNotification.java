package az.code.carlada.jobs;

import az.code.carlada.daos.SearchDAO;
import az.code.carlada.daos.SubscriptionDAO;
import az.code.carlada.models.AppUser;
import az.code.carlada.models.Listing;
import az.code.carlada.services.SearchService;
import az.code.carlada.services.SubscriptionService;
import az.code.carlada.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SubscriptionNotification implements Job {
    SearchDAO searchDAO;
    ModelMapperUtil mapperUtil;
    SubscriptionDAO subDAO;

    public SubscriptionNotification(SearchDAO searchDAO,
                                    ModelMapper modelMapper,
                                    SubscriptionDAO subDAO) {
        this.searchDAO = searchDAO;
        this.mapperUtil = ModelMapperUtil.builder().modelMapper(modelMapper).build();
        this.subDAO = subDAO;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        List<AppUser> appUsers = subDAO.getAllSubscriptions().stream().parallel()
//                .flatMap(i -> searchDAO.searchAllListings(i).stream())
//                .peek(System.out::println)
//                .map(Listing::getAppUser)
//                .collect(Collectors.toList());
    }
}
