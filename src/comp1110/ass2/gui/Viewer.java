package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * A very simple viewer for tile placements in the Railroad Ink game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various tile placements.
 */
public class Viewer extends Application {
    /* board layout */
    private static final int VIEWER_WIDTH = 1024;
    private static final int VIEWER_HEIGHT = 768;
    private static final int GRID_LENGTH = 90;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Group board = new Group();
    private final Group tiles = new Group();

    TextField textField;

    /*
     * Draw a basic empty board in the window.
     */
    private void makeBoard(){
        board.setLayoutX(130);
        board.setLayoutY(50);
        board.getChildren().clear();

        Image boardImage = new Image(getClass().getResourceAsStream(URI_BASE+"original board.JPG"));
        ImageView imageView = new ImageView();
        imageView.setFitWidth(GRID_LENGTH*7);
        imageView.setFitHeight(GRID_LENGTH*7);
        imageView.setImage(boardImage);

        /*Image highExitImage = new Image(getClass().getResourceAsStream(URI_BASE+"HighExit.png"));
        ImageView highExitimageView = new ImageView();
        highExitimageView.setFitWidth(GRID_LENGTH);
        highExitimageView.setFitHeight(GRID_LENGTH);
        highExitimageView.setImage(highExitImage);
        highExitimageView.setLayoutX(85);
        highExitimageView.setLayoutY(0);*/

        board.getChildren().add(imageView);
        //board.getChildren().add(highExitimageView);
    }

    /*
    private void makeTiles(String diceRoll){

    }*/

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement)  {

        tiles.getChildren().clear();

        Image tileImage = new Image(getClass().getResourceAsStream(URI_BASE+placement.charAt(0)+
                placement.charAt(1)+ ".png"));
        ImageView tileImageView = new ImageView();
        tileImageView.setFitHeight(GRID_LENGTH);
        tileImageView.setFitWidth(GRID_LENGTH);
        tileImageView.setImage(tileImage);

        int tileOrientation = (int)placement.charAt(4) - 48;
        if(tileOrientation<4){
            for(int i=0;i<tileOrientation;i++)
                tileImageView.setRotate(tileImageView.getRotate()+90);
        }else{
            tileImageView.setScaleX(-1);
            for(int i=0;i<tileOrientation-4;i++)
                tileImageView.setRotate(tileImageView.getRotate()+90);
        }


        tiles.getChildren().add(tileImageView);

        tiles.setLayoutX(130+((int)placement.charAt(3)-48)*GRID_LENGTH);
        tiles.setLayoutY(50+(placement.charAt(2)-'A')*GRID_LENGTH);

        // FIXME Task 4: implement the simple placement viewer
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button button = new Button("Refresh");
        button.setOnAction(e -> {
            makePlacement(textField.getText());
            textField.clear();
        });
        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button);
        hb.setSpacing(10);
        hb.setLayoutX(130);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("StepsGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);
        root.getChildren().add(controls);
        root.getChildren().add(board);
        root.getChildren().add(tiles);

        makeControls();
        makeBoard();
        //makePlacement("A2B16");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
