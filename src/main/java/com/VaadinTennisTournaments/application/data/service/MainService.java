package com.VaadinTennisTournaments.application.data.service;

import com.VaadinTennisTournaments.application.data.entity.ATP.ATPPlayer;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATPPunctation;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATPResult;
import com.VaadinTennisTournaments.application.data.entity.Register.RegisterUser;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.User.UserRanking;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTAResult;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTAPunctation;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.User.User;
import com.VaadinTennisTournaments.application.data.entity.ATP.ATP;
import com.VaadinTennisTournaments.application.data.entity.Tournament.Rank;
import com.VaadinTennisTournaments.application.data.entity.WTA.WTA;
import com.VaadinTennisTournaments.application.data.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.List;

@org.springframework.stereotype.Service
public class MainService {
    PasswordEncoder passwordEncoder;
    private final RegisterUserRepository registerUserRepository;
    private final UserRepository userRepository;
    private final UserRankingRepository userRankingRepository;
    private final WTARepository wtaRepository;
    private final WTAResultRepository wtaResultRepository;
    private final WTAPunctationRepository wtaPunctationRepository;
    private final ATPRepository atpRepository;
    private final ATPResultRepository atpResultRepository;
    private final ATPPunctationRepository atpPunctationRepository;
    private final ATPPlayerRepository atpPlayerRepository;
    private final InterestsRepository interestsRepository;
    private final StageRepository stageRepository;
    private final RankRepository rankRepository;
    public MainService(RegisterUserRepository registerUserRepository, UserRepository userRepository, UserRankingRepository userRankingRepository ,
                       WTARepository wtaRepository, WTAResultRepository wtaResultRepository, WTAPunctationRepository wtaPunctationRepository,
                       ATPRepository atpRepository, ATPResultRepository atpResultRepository, ATPPunctationRepository atpPunctationRepository,
                       ATPPlayerRepository atpPlayerRepository,
                       InterestsRepository interestsRepository, StageRepository stageRepository, RankRepository rankRepository,
                       PasswordEncoder passwordEncoder
                       ) {
        this.registerUserRepository = registerUserRepository;
        this.userRepository = userRepository;
        this.userRankingRepository = userRankingRepository;
        this.wtaRepository = wtaRepository;
        this.wtaResultRepository = wtaResultRepository;
        this.wtaPunctationRepository = wtaPunctationRepository;
        this.atpRepository = atpRepository;
        this.atpResultRepository = atpResultRepository;
        this.atpPunctationRepository = atpPunctationRepository;
        this.atpPlayerRepository = atpPlayerRepository;
        this.interestsRepository = interestsRepository;
        this.stageRepository = stageRepository;
        this.rankRepository = rankRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<RegisterUser> findAllRegisterUsers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return registerUserRepository.findAll();
        } else {
            return registerUserRepository.search(stringFilter);
        }
    }
    public void deleteRegisterUser(RegisterUser user) {
        registerUserRepository.delete(user);
    }

    public void saveRegisterUser(RegisterUser user) {
        if (user == null) {
            System.err.println("WTA is null. Are you sure you have connected your form to the application?");
            return;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("User");
            registerUserRepository.save(user);
    }

    public List<User> findAllUsers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty() ) {
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

    public List<ATPPlayer> findAllATPPlayers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return atpPlayerRepository.findAll();
        } else {
            return atpPlayerRepository.search(stringFilter);
        }
    }
    public void deleteATPPlayer(ATPPlayer atpPlayer) {
        atpPlayerRepository.delete(atpPlayer);
    }

    public void saveATPPlayer(ATPPlayer atpPlayer) {
        if (atpPlayer == null) {
            System.err.println("ATP playeris null. Are you sure you have connected your form to the application?");
            return;
        }
        atpPlayerRepository.save(atpPlayer);
    }




    public List<UserRanking> findAllUserRankings(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRankingRepository.findAll();
        } else {
            return userRankingRepository.search(stringFilter);
        }
    }
    public void deleteUserRanking(UserRanking userRanking) {
        userRankingRepository.delete(userRanking);
    }

    public void saveUserRanking(UserRanking userRanking) {
        if (userRanking == null) {
            System.err.println("WTA is null. Are you sure you have connected your form to the application?");
            return;
        }

        userRankingRepository.save(userRanking);
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

    public List<WTAResult> findAllWTAResults(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return wtaResultRepository.findAll();
        } else {
            return wtaResultRepository.search(stringFilter);
        }
    }
    public void deleteWTAResult(WTAResult wtaResult){
        wtaResultRepository.delete(wtaResult);
    }

    public void saveWTAResult(WTAResult wtaResult){
        if (wtaResult == null) {
            System.err.println("Results are null. Are you sure you have connected your form to the application?");
            return;
        }
        wtaResultRepository.save(wtaResult);
    }
    public List<ATPResult> findAllATPResults(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return atpResultRepository.findAll();
        } else {
            return atpResultRepository.search(stringFilter);
        }
    }
    public void deleteATPResult(ATPResult atpResult){
        atpResultRepository.delete(atpResult);
    }

    public void saveATPResult(ATPResult atpResult){
        if (atpResult== null) {
            System.err.println("Results are null. Are you sure you have connected your form to the application?");
            return;
        }
        atpResultRepository.save(atpResult);
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

    public List<Interests> findAllInterests() {
        return interestsRepository.findAll();
    }
    public List<Stage> findAllStages() {
        return stageRepository.findAll();
    }
    public List<Rank> findAllRanks() {
        return rankRepository.findAll();
    }

}
