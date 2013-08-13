package XO;

public class Players {

    private final String NO_NAME = "N/A";

    public final int MIN_NAME_LENGHT = 2;

    public final int MAX_NAME_LENGHT = 15;

    private String namePlayerFirst = NO_NAME;
    private String namePlayerSecond = NO_NAME;

    public void setNamePlayerFirst(String namePlayerFirst) {
        this.namePlayerFirst = namePlayerFirst;
    }

    public void setNamePlayerSecond(String namePlayerSecond) {
        this.namePlayerSecond = namePlayerSecond;
    }

    public String getNamePlayerFirst() {
        return namePlayerFirst;
    }

    public String getNamePlayerSecond() {
        return namePlayerSecond;
    }
}
