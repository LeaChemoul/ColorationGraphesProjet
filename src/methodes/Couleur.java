package methodes;

import org.jetbrains.annotations.Contract;

public enum Couleur {

    PINK("crimson"),
    CYAN("cyan4"),
    GOLDEN("darkgoldenrod3"),
    GREEN("darkolivegreen2"),
    ORANGE("darkorange2"),
    PURPLE("darkorchid2"),
    BLUE("deepskyblue2"),
    CORALL("coral1"),
    GREEN2("chartreuse1"),
    RED("firebrick1"),
    SALMON("darksalmon"),
    LIGHTBROWN("peru"),
    GREY("snow4"),
    INDIGO("indigo"),
    BROWN("sienna4"),
    MINT("mintcream"),
    OLIVE("olivedrab"),
    DARKRED("red4"),
    DARKBROWN("maroon"),
    SLATEBLUE("slateblue"),
    LIGHTPINK("pink"),
    MAGENTA("magenta"),
    ROYALBLUE("royalblue"),
    MEDIUMBLUE("mediumblue"),
    BLACK("black"),
    DARKBLUE("navyblue");

    private String c;

    Couleur(String c) {
        this.c = c;
    }

    @Contract(pure = true)
    public String getC() {
        return c;
    }
}
