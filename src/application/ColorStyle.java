package application;

import javafx.scene.Node;

public enum ColorStyle {
    GREEN("#538D4E"),
    YELLOW("#B59F3B"),
    GREY("#3A3A3C"),
	LIGHTGREY("#818384");

    private final String colorCode;

    ColorStyle(String colorCode) {
        this.colorCode = colorCode;
    }

    public void applyStyle(Node node) {
        node.setStyle("-fx-background-color: " + colorCode + ";");
    }
}
