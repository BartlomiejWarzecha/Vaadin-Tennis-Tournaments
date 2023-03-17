package com.VaadinTennisTournaments.application.data.service;

import com.VaadinTennisTournaments.application.data.entity.HowToPlay;
import com.VaadinTennisTournaments.application.data.entity.atp.*;
import com.VaadinTennisTournaments.application.data.entity.register.RegisterUser;
import com.VaadinTennisTournaments.application.data.entity.tournament.Interests;
import com.VaadinTennisTournaments.application.data.entity.user.UserRanking;
import com.VaadinTennisTournaments.application.data.entity.wta.*;
import com.VaadinTennisTournaments.application.data.entity.tournament.Stage;
import com.VaadinTennisTournaments.application.data.entity.user.User;
import com.VaadinTennisTournaments.application.data.entity.tournament.Rank;
import com.VaadinTennisTournaments.application.data.repository.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@org.springframework.stereotype.Service
public class MainService {
    private final RegisterUserRepository registerUserRepository;
    private final UserRepository userRepository;
    private final UserRankingRepository userRankingRepository;
    private final WTARepository wtaRepository;
    private final WTAResultRepository wtaResultRepository;
    private final WTAPunctationRepository wtaPunctationRepository;
    private final WTAPlayerRepository wtaPlayerRepository;
    private final WTATournamentRepository wtaTournamentRepository;
    private final ATPRepository atpRepository;
    private final ATPResultRepository atpResultRepository;
    private final ATPPunctationRepository atpPunctationRepository;
    private final ATPPlayerRepository atpPlayerRepository;
    private final ATPTournamentRepository atpTournamentRepository;
    private final InterestsRepository interestsRepository;
    private final StageRepository stageRepository;
    private final RankRepository rankRepository;
    private final PasswordEncoder passwordEncoder;
    private final HowToPlayRepository howToPlayRepository;

    public MainService(RegisterUserRepository registerUserRepository, UserRepository userRepository, UserRankingRepository userRankingRepository,
                       WTARepository wtaRepository, WTAResultRepository wtaResultRepository, WTAPunctationRepository wtaPunctationRepository,
                       WTAPlayerRepository wtaPlayerRepository, WTATournamentRepository wtaTournamentRepository,
                       ATPRepository atpRepository, ATPResultRepository atpResultRepository,
                       ATPPunctationRepository atpPunctationRepository, ATPPlayerRepository atpPlayerRepository, ATPTournamentRepository atpTournamentRepository,
                       InterestsRepository interestsRepository, HowToPlayRepository howToPlayRepository,
                       StageRepository stageRepository, RankRepository rankRepository, PasswordEncoder passwordEncoder
    ) {
        this.registerUserRepository = registerUserRepository;
        this.userRepository = userRepository;
        this.userRankingRepository = userRankingRepository;
        this.wtaRepository = wtaRepository;
        this.wtaResultRepository = wtaResultRepository;
        this.wtaPunctationRepository = wtaPunctationRepository;
        this.wtaPlayerRepository = wtaPlayerRepository;
        this.wtaTournamentRepository = wtaTournamentRepository;
        this.atpRepository = atpRepository;
        this.atpResultRepository = atpResultRepository;
        this.atpPunctationRepository = atpPunctationRepository;
        this.atpPlayerRepository = atpPlayerRepository;
        this.atpTournamentRepository = atpTournamentRepository;
        this.interestsRepository = interestsRepository;
        this.stageRepository = stageRepository;
        this.rankRepository = rankRepository;
        this.passwordEncoder = passwordEncoder;
        this.howToPlayRepository = howToPlayRepository;
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
            System.err.println("Data from register user is null. Are you sure you have connected your form to the application?");

            return;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles("User");
        registerUserRepository.save(user);
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
            System.err.println("User is null. Are you sure you have connected your form to the application?");
            return;
        }
        userRepository.save(user);
    }

    public List<WTAPlayer> findAllWTAPlayers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return wtaPlayerRepository.findAll();
        } else {
            return wtaPlayerRepository.search(stringFilter);
        }
    }

    public void deleteWTAPlayer(WTAPlayer wtaPlayer) {
        wtaPlayerRepository.delete(wtaPlayer);
    }

    public void saveWTAPlayer(WTAPlayer wtaPlayer) {
        wtaPlayerRepository.save(wtaPlayer);
    }

    public List<ATPPlayer> findAllATPPlayers(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return atpPlayerRepository.findAll();
        } else {
            return atpPlayerRepository.search(stringFilter);
        }
    }

    public List<WTATournament> findAllWTATournaments(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return wtaTournamentRepository.findAll();
        } else {
            return wtaTournamentRepository.search(stringFilter);
        }
    }

    public void deleteWTATournament(WTATournament wtaTournament) {
        wtaTournamentRepository.delete(wtaTournament);
    }

    public void saveWTATournament(WTATournament wtaTournament) {
        if (wtaTournament == null) {
            System.err.println("ATP player is null. Are you sure you have connected your form to the application?");
            return;
        }
        wtaTournamentRepository.save(wtaTournament);
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

    public List<ATPTournament> findAllATPTournaments(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return atpTournamentRepository.findAll();
        } else {
            return atpTournamentRepository.search(stringFilter);
        }
    }

    public void deleteATPTournament(ATPTournament atpTournament) {
        atpTournamentRepository.delete(atpTournament);
    }

    public void saveATPTournament(ATPTournament atpTournament) {
        if (atpTournament == null) {
            System.err.println("ATP tournament is null. Are you sure you have connected your form to the application?");
            return;
        }
        atpTournamentRepository.save(atpTournament);
    }

    public List<User> findAllAtpUsers() {
        return userRepository.findAllAtpUsers();
    }

    public List<User> findAllWtaUsers() {
        return userRepository.findAllWtaUsers();
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

    public void deleteWTA(WTA wta) {
        wtaRepository.delete(wta);
    }

    public void saveWTA(WTA wta) {
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

    public void deleteATP(ATP atp) {
        atpRepository.delete(atp);
    }

    public void saveATP(ATP atp) {
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

    public void deleteWTAResult(WTAResult wtaResult) {
        wtaResultRepository.delete(wtaResult);
    }

    public void saveWTAResult(WTAResult wtaResult) {
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

    public void deleteATPResult(ATPResult atpResult) {
        atpResultRepository.delete(atpResult);
    }

    public void saveATPResult(ATPResult atpResult) {
        if (atpResult == null) {
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

    public void deleteWTAPunctation(WTAPunctation WTAPunctation) {
        wtaPunctationRepository.delete(WTAPunctation);
    }

    public void saveWTAPunctation(WTAPunctation WTAPunctation) {
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

    public void deleteATPPunctation(ATPPunctation atpPunctation) {
        atpPunctationRepository.delete(atpPunctation);
    }

    public void saveATPPunctation(ATPPunctation atpPunctation) {
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

    public List<HowToPlay> findAllHowToPlay(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return howToPlayRepository.findAll();
        } else {
            return howToPlayRepository.search(stringFilter);
        }
    }

    public void deleteHowToPlay(HowToPlay howToPlay) {
        howToPlayRepository.delete(howToPlay);
    }

    public void saveHowToPlay(HowToPlay howToPlay) {
        if (howToPlay == null) {
            System.err.println("Data from how to play is null. Are you sure you have connected your form to the application?");
            return;
        }
        howToPlayRepository.save(howToPlay);
    }
}

