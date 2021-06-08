package com.github.javaruncommunity.jrtb.command;

public enum CommandName {
    START("/start"),
    STOP("/stop"),
    HELP("/help"),
    NO("/not_command"),
    STAT("/stat"),
    ADD_GROUP_SUB("/addgroupsub"),
    LIST_GROUP_SUB("/listgroupsub"),
    DELETE_GROUP_SUB("/deletegroupsub"),
    ADMIN_HELP("/ahelp");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName() {
        return commandName;
    }
}
