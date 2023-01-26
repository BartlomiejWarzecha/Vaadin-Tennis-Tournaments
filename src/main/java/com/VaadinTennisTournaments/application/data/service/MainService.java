package com.VaadinTennisTournaments.application.data.service;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATPPunctation;
import com.VaadinTennisTournaments.application.data.entity.Interests;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTAPunctation;
import com.VaadinTennisTournaments.application.data.entity.Stage;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATP;
import com.VaadinTennisTournaments.application.data.entity.Rank;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTA;
import com.VaadinTennisTournaments.application.data.entity.Result.Result;
import com.VaadinTennisTournaments.application.data.repository.*;

import java.util.List;

@org.springframework.stereotype.Service
public class MainService {

    private final UserRepository userRepository;
    private final WTARepository wtaRepository;
    private final ATPRepository atpRepository;
    private final ResultRepository resultRepository;

    private final WTAPunctationRepository wtaPunctationRepository;
    private final ATPPunctationRepository atpPunctationRepository;

    private final InterestsRepository interestsRepository;
    private final StageRepository stageRepository;
    private final RankRepository rankRepository;
    public MainService(UserRepository userRepository, WTARepository wtaRepository, ATPRepository atpRepository, ResultRepository resultRepository, InterestsRepository interestsRepository, StageRepository stageRepository,
                       RankRepository rankRepository, WTAPunctationRepository wtaPunctationRepository, ATPPunctationRepository atpPunctationRepository
    ) {
        this.userRepository = userRepository;
        this.wtaRepository = wtaRepository;
        this.atpRepository = atpRepository;
        this.resultRepository = resultRepository;
        this.interestsRepository = interestsRepository;
        this.stageRepository = stageRepository;
        this.rankRepository = rankRepository;
        this.wtaPunctationRepository = wtaPunctationRepository;
        this.atpPunctationRepository = atpPunctationRepository;

    }
    public List<User> findAllUsers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.search(stringFilter);
        }
    }
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void saveUser(User user) {
        if (user == null) {
            System.err.println("WTA is null. Are you sure you have connected your form to the application?");
            return;
        }
        userRepository.save(user);
    }
    public List<Interests> findAllInterests() {
        return interestsRepository.findAll();
    }
    public List<Stage> findAllStages() {
        return stageRepository.findAll();
    }
    public List<Rank> findAllRanks() {
        return rankRepository.findAll();
    }
    public List<WTA> findAllWTA(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return wtaRepository.findAll();
        } else {
            return wtaRepository.search(stringFilter);
        }
    }
        public void deleteWTA (WTA wta){
            wtaRepository.delete(wta);
        }
        public void saveWTA (WTA wta){
            if (wta == null) {
                System.err.println("WTA Tournament is null. Are you sure you have connected your form to the application?");
                return;
            }
            wtaRepository.save(wta);
        }
    public List<ATP> findAllATP(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return atpRepository.findAll();
        } else {
            return atpRepository.search(stringFilter);
        }
    }
    public void deleteATP (ATP atp){
        atpRepository.delete(atp);
    }

    public void saveATP (ATP atp){
        if (atp == null) {
            System.err.println("ATP Tournament is null. Are you sure you have connected your form to the application?");
            return;
        }
        atpRepository.save(atp);
    }

    public List<Result> findAllResult(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return resultRepository.findAll();
        } else {
            return resultRepository.search(stringFilter);
        }
    }
    public void deleteResult (Result result){
        resultRepository.delete(result);
    }

    public void saveResult (Result result){
        if (result == null) {
            System.err.println("Results are null. Are you sure you have connected your form to the application?");
            return;
        }
        resultRepository.save(result);
    }

    public List<WTAPunctation> findAllWTAPunctation(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return wtaPunctationRepository.findAll();
        } else {
            return wtaPunctationRepository.search(stringFilter);
        }
    }
    public void deleteWTAPunctation(WTAPunctation WTAPunctation){
        wtaPunctationRepository.delete(WTAPunctation);
    }

    public void saveWTAPunctation(WTAPunctation WTAPunctation){
        if (WTAPunctation == null) {
            System.err.println("Puncation are null. Are you sure you have connected your form to the application?");
            return;
        }
        wtaPunctationRepository.save(WTAPunctation);
    }
    public List<ATPPunctation> findAllATPPunctation(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return atpPunctationRepository.findAll();
        } else {
            return atpPunctationRepository.search(stringFilter);
        }
    }
    public void deleteATPPunctation(ATPPunctation atpPunctation){
        atpPunctationRepository.delete(atpPunctation);
    }

    public void saveATPPunctation(ATPPunctation atpPunctation){
        if (atpPunctation == null) {
            System.err.println("Puncation are null. Are you sure you have connected your form to the application?");
            return;
        }
        atpPunctationRepository.save(atpPunctation);
    }
}
