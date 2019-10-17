package commands;

public abstract class Command implements Describable {

    private final String NAME;
    private final int ARGS_COUNT;
    private String arguments;

    /**
     * @param name - command name
     * @param argsCount - count of arguments. If it less than 0 - infinity
     */
    public Command(String name,int argsCount){
        NAME = name;
        ARGS_COUNT = argsCount;
    }

    public abstract void execute();

    public String getArguments() {
        return arguments;
    }

    public void setArguments(String arguments) {
        this.arguments = arguments;
    }

    @Override
    public String toString() {
        return "Command " + NAME;
    }

    public String getNAME() {
        return NAME;
    }

    public int getARGS_COUNT() {
        return ARGS_COUNT;
    }
}