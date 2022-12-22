package com.example.application.data.service;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.User;
import com.example.application.data.entity.Status;
import com.example.application.data.repository.CompanyRepository;
import com.example.application.data.repository.UserRepository;
import com.example.application.data.repository.StatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CrmService {

    private final UserRepository contactRepository;
    private final CompanyRepository companyRepository;


    public CrmService(UserRepository contactRepository,
                      CompanyRepository companyRepository
                     ) {
        this.contactRepository = contactRepository;
        this.companyRepository = companyRepository;
    }

    public List<User> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return contactRepository.findAll();
        } else {
            return contactRepository.search(stringFilter);
        }
    }

    public long countContacts() {
        return contactRepository.count();
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

    public List<Company> findAllCompanies() {
        return companyRepository.findAll();
    }


}
