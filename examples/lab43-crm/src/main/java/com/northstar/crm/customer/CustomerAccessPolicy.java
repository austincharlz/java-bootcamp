package com.northstar.crm.customer;

import java.util.Locale;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

@Component("customerAccessPolicy")
public class CustomerAccessPolicy {

    public boolean canReadCustomer(Authentication authentication, String publicId) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        if (AuthorityUtils.authorityListToSet(authentication.getAuthorities()).contains("ROLE_ADMIN")) {
            return true;
        }

        return publicId != null
                && authentication.getName() != null
                && publicId.equalsIgnoreCase(authentication.getName().trim().toLowerCase(Locale.ROOT));
    }
}
