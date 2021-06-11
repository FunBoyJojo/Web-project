//package com.testplus.testplus.configuration;
//
//import com.testplus.testplus.models.User;
//import com.testplus.testplus.repo.UserRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service("userDetailsService")
//public class UserDetailsServiceImpl implements UserDetailsService {
//
//    @Autowired
//    private UserRepo userRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//
//
//        User user = userRepo.findByLogin(s);
//        UserDetails userDetails =
//                org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
//                        .username(user.getLogin())
//                        .password(user.getPassword())
//                        .roles(user.getRoles().toString())
//                        .build();
//
//        return userDetails;
//    }
//}
