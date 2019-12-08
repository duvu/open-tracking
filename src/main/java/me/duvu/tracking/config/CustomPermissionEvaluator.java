package me.duvu.tracking.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 * @author beou on 10/15/17 05:40
 */

@Slf4j
public class CustomPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        log.info("checking permission");
        if ((authentication == null) || (targetDomainObject == null) || !(permission instanceof String)){
            return false;
        }

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        //AbstractEntity abstractEntity = (AbstractEntity) targetDomainObject;
        log.info("User {} trying to access {} - {} with permission {}",
                customUserDetails.getUsername(),
                targetDomainObject.getClass().getSimpleName(),
                //targetDomainObject.getId(),
                0,
                permission.toString());

        //customUserDetails.getMaxRole()

//        String targetType = targetDomainObject.getClass().getSimpleName().toUpperCase();
//
//        String perm = ((String) permission).toUpperCase();
//        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
//        Long companyId = principal.getCompanyId();
//        Set<Long> managedCompanyIds = principal.getManagedCompanyIds();
//        // Long accountId = principal.getAccount().getId();
//        Long accountId = principal.getAccountId();
//
//        List<String> roleList = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
//        if (roleList.contains(RolesList.ROLE_LORD)) {
//            return true;
//        } else if (roleList.contains(RolesList.ROLE_SYSTEM_ADMIN)) {
//            // check managed company ids
//            return true;
//        } /*else if (roleList.contains(RolesList.ROLE_COMPANY_ADMIN)) {
//            //-- return #true only same company
//            return adminAccessible(companyId, accountId,  targetDomainObject);
//        } else if (roleList.contains(RolesList.ROLE_COMPANY_MOD)) {
//            //--
//            return hasPrivilege(authentication, targetType, perm);
//        } else if (roleList.contains(RolesList.ROLE_COMPANY_USER)) {
//            //-- normal user
//            return hasPrivilege(authentication, targetType, perm);
//        } else {
//            return hasPrivilege(authentication, targetType.toUpperCase(), perm);
//        }*/
        return true;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        if ((authentication == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        String perm = permission.toString();
        log.info("User {} trying to access {} - {} with permission {}",
                customUserDetails.getUsername(),
                targetType,
                targetId,
                permission.toString());


        switch (perm) {
            case "create":
                break;
            case "update":
                break;
            case "delete":
                break;
        }


        return true;
    }

}
