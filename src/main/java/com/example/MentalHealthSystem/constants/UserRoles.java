package com.example.MentalHealthSystem.constants;

public enum UserRoles {
    PATIENT("ROLE_PATIENT"),
    ADMIN("ROLE_ADMIN"),
    DOCTOR("ROLE_DOCTOR");

    private String roleName;
    public static UserRoles getEnumByValue(String value) {
        for (UserRoles userRole : UserRoles.values()) {
            if (userRole.getRoleName().equalsIgnoreCase(value)) {
                return userRole;
            }
        }
        return null;
    }

    private String getRoleName() {
        return roleName;
    }

    UserRoles(String role) {
        this.roleName = role;
    }
}
