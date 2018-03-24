package com.websecurity.service;

import java.util.ArrayList;
import java.util.List;

import com.websecurity.dao.AppRoleDAO;
import com.websecurity.dao.AppUserDAO;
import com.websecurity.models.AppUser;
import com.websecurity.models.AppUserDto;
import com.websecurity.utils.EncryptedPasswordUtils;
import com.websecurity.error.UsernameAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserDAO appUserDAO;

    @Autowired
    private AppRoleDAO appRoleDAO;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AppUser appUser = findUserByUserName(userName);

        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }

        System.out.println("Found User: " + appUser);

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.appRoleDAO.getRoleNames(appUser.getUserId());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getEncryptedPassword(), grantList);

        return userDetails;
    }

    private AppUser findUserByUserName(String userName) {
        AppUser appUser = this.appUserDAO.findUserAccount(userName);
        if (appUser != null) {
            return appUser;
        }
        return null;
    }


    public AppUser registerNewUserAccount(AppUserDto appUserDto)
            throws UsernameAlreadyExistsException {

        if(findUserByUserName(appUserDto.getUserName()) != null) {
            throw new UsernameAlreadyExistsException(
                    "There is an account with username: " + appUserDto.getUserName());
        }

        AppUser appUser = new AppUser();
        appUser.setUserName(appUserDto.getUserName());
        appUser.setEncryptedPassword(EncryptedPasswordUtils.encrytePassword(appUserDto.getPassword()));
        appUser.setFirstName(appUserDto.getFirstName());
        appUser.setLastName(appUserDto.getLastName());
        appUser.setEmail(appUserDto.getEmail());

        return appUser;
    }
}
