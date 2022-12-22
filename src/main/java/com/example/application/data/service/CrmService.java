package com.example.application.data.service;

import com.example.application.data.entity.Interests;
import com.example.application.data.entity.User;
import com.example.application.data.repository.InterestsRepository;
import com.example.application.data.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final UserRepository contactRepository;
    private final InterestsRepository interestsRepository;


    public CrmService(UserRepository contactRepository,
                      InterestsRepository interestsRepository
                     ) {
        this.contactRepository = contactRepository;
        this.interestsRepository = interestsRepository;
    }

    public List<User> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public void deleteContact(User user) {
        contactRepository.delete(user);
    }

    public void saveContact(User user) {
        if (user == null) {
            System.err.println("User is null. Are you sure you have connected your form to the application?");
            return;
        }
        contactRepository.save(user);
    }

    public List<Interests> findAllCompanies() {
        return interestsRepository.findAll();
    }


}
