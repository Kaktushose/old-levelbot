package de.nordrheintvplay.discord.levelbot.commands.framework;

public class Command {

    private String invoke;
    private String[] args;

    public Command(String invoke, String[] args) {
        this.invoke = invoke;
        this.args = args;
    }

    public String getInvoke() {
        return invoke;
    }

    public String[] getArgs() {
        return args;
    }

}
