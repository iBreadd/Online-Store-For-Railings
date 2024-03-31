package com.example.RailingShop;

<<<<<<< HEAD
=======
import com.example.RailingShop.Entity.User.Employee;
>>>>>>> f5b31b8270c98ffe5093cc1cdadac42b8f772a8f
import com.example.RailingShop.Entity.User.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;

public class MyUserDetails implements UserDetails {
    private User user;
    private Employee employee;

    public MyUserDetails(User user){
        this.user = user;

    }
    public MyUserDetails(Employee employee){

        this.employee = employee;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(employee.getRole());

        return Arrays.asList(authority);
    }

    @Override
    public String getPassword() {

//        return user.getPassword();

        return employee.getPassword();

    }

    @Override
    public String getUsername() {

//        return user.getUsername();

        return employee.getUsername();

    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
