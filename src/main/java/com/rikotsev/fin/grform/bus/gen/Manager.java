package com.rikotsev.fin.grform.bus.gen;

/**
 * In charge of executing business logic for a certain module/functionality
 */
public interface Manager {

    /**
     * Executes the action
     */
    void manage() throws Exception;
}
