package com.yrbase.soulpermission.exception;

/**
 * @author cd5160866
 */
public class InitException extends IllegalStateException {

    public InitException() {
        super("auto init failed ,you need invoke SoulPermission.init() in your application");
    }
}
