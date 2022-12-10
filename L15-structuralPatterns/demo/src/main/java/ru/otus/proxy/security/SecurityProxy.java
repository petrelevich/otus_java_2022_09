package ru.otus.proxy.security;

public class SecurityProxy implements SecurityAccess {

    private final SecurityAccess securityAccess;

    public SecurityProxy(SecurityAccess securityAccess) {
        this.securityAccess = securityAccess;
    }

    @Override
    public void access() {
        System.out.println("before");
        securityAccess.access();
        System.out.println("after");
    }
}
