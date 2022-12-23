package com.example.application.data.service;

import com.example.application.data.entity.User.Interests;
import com.example.application.data.entity.User.User;
import com.example.application.data.entity.ATP.ATP;
import com.example.application.data.entity.Stage;
import com.example.application.data.entity.WTA.WTA;
import com.example.application.data.repository.InterestsRepository;
import com.example.application.data.repository.StageRepository;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.repository.WTARepository;
import com.example.application.data.repository.ATPRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final UserRepository userRepository;
    private final WTARepository wtaRepository;
    private final ATPRepository atpRepository;
    private final InterestsRepository interestsRepository;
    private final StageRepository stageRepository;

    public CrmService(UserRepository userRepository, WTARepository wtaRepository, ATPRepository atpRepository,
                      InterestsRepository interestsRepository, StageRepository stageRepository

    ) {
        this.userRepository = userRepository;
        this.wtaRepository = wtaRepository;
        this.atpRepository = atpRepository;
        this.interestsRepository = interestsRepository;
        this.stageRepository = stageRepository;
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
}
