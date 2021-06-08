package com.codingdojo.exam.services;
import java.util.List;
import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.codingdojo.exam.models.Idea;
import com.codingdojo.exam.models.User;
import com.codingdojo.exam.models.UserIdea;
import com.codingdojo.exam.repositories.IdeaRepository;
import com.codingdojo.exam.repositories.UserIdeaRepository;
import com.codingdojo.exam.repositories.UserRepository;

@Service
public class MainService{
    private final UserRepository userRepository;
    private final IdeaRepository ideaRepository;
    private final UserIdeaRepository userIdeaRepository;
    
    public MainService(UserRepository userRepository, IdeaRepository ideaRepository, UserIdeaRepository userIdeaRepository) {
        this.userRepository = userRepository;
        this.ideaRepository = ideaRepository;
        this.userIdeaRepository = userIdeaRepository;
    }
    
    // register user and hash their password
    public User registerUser(User user) {
        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashed);
        user.setEmail(user.getEmail().toLowerCase());
        return userRepository.save(user);
    }
    
    // find user by email
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    // find user by id
    public User findUserById(Long id) {
    	Optional<User> u = userRepository.findById(id);
    	
//    	if(u.isPresent()) {
//            return u.get();
//    	} else {
//    	    return null;
//    	}
    	return userRepository.findById(id).orElse(null);
    }
    	
    
    // authenticate user
    public boolean authenticateUser(String email, String password) {
        // first find the user by email
        User user = userRepository.findByEmail(email);
        // if we can't find it by email, return false
        if(user == null) {
            return false;
        } else {
            // if the passwords match, return true, else, return false
            if(BCrypt.checkpw(password, user.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
    }
    
    public Idea createAIdea(Idea idea) {
    	return this.ideaRepository.save(idea);
    }
    public List <Idea> findAllIdeas(){
    	return (List<Idea>)this.ideaRepository.findAll();
    }
    
    public Idea findOneIdea(Long id) {
    	return this.ideaRepository.findById(id).orElse(null);
    }
    
    public Idea updateAIdea(Idea idea) {
 	   return this.ideaRepository.save(idea);
    }
    
    public void deleteAIdea(Idea idea) {
    	this.ideaRepository.delete(idea);
    }
    
    public UserIdea createAssociation(UserIdea userLike) {
    	return this.userIdeaRepository.save(userLike);
    }
    
    public User unlikeIdea(User u) {
    	return this.userRepository.save(u);
    }
}
