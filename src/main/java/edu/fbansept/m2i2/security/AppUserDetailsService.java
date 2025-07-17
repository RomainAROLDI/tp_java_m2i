package edu.fbansept.m2i2.security;

import edu.fbansept.m2i2.dao.UtilisateurDao;
import edu.fbansept.m2i2.model.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    protected UtilisateurDao utilisateurDao;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<Utilisateur> optionalUtilisateur = utilisateurDao.findByEmail(email);

        if(optionalUtilisateur.isEmpty()) {
            throw new UsernameNotFoundException("Email introuvable : " + email);
        }

        return new AppUserDetails(optionalUtilisateur.get());
    }
}
