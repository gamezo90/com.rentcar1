package com.noirix.security;

import com.noirix.domain.Role;
import com.noirix.domain.SystemRoles;
import com.noirix.domain.User;
import com.noirix.domain.hibernate.HibernateUser;
import com.noirix.repository.springdata.RolesSpringDataRepository;
import com.noirix.repository.springdata.UserSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserSpringDataRepository userRepository;

    private final RolesSpringDataRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            /*Find user in DB*/
            Optional<HibernateUser> searchResult = userRepository.findByLogin(username);

            if (searchResult.isPresent()) {
                HibernateUser user = searchResult.get();

                /*We are creating Spring Security User object*/

                return new org.springframework.security.core.userdetails.User(
                        user.getCredentials().getLogin(),
                        user.getCredentials().getPassword(),
//                        ["ROLE_USER", "ROLE_ADMIN"]
                        AuthorityUtils.commaSeparatedStringToAuthorityList(
                                roleRepository.findRolesByUserId(user.getId())
                                        .stream()
                                        .map(Role::getRoleName)
                                        //.map(SystemRoles::name)
                                        .collect(Collectors.joining(","))
                        )
                );
            } else {
                throw new UsernameNotFoundException(String.format("No user found with login '%s'.", username));
            }
        } catch (Exception e) {
            throw new UsernameNotFoundException("User with this login not found");
        }
    }
}
