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
import javafx.scene.layout.VBox;
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

        board.getChildren().add(imageView);


        Image highExitImage = new Image(getClass().getResourceAsStream(URI_BASE+"HighExit.png"));
        Image railExitImage = new Image(getClass().getResourceAsStream(URI_BASE+"RailExit.png"));

        ImageView highExitimageView1 = new ImageView();
        ImageView highExitimageView2 = new ImageView();
        ImageView highExitimageView3 = new ImageView();
        ImageView highExitimageView4 = new ImageView();
        ImageView highExitimageView5 = new ImageView();
        ImageView highExitimageView6 = new ImageView();

        ImageView railExitimageView1 = new ImageView();
        ImageView railExitimageView2 = new ImageView();
        ImageView railExitimageView3 = new ImageView();
        ImageView railExitimageView4 = new ImageView();
        ImageView railExitimageView5 = new ImageView();
        ImageView railExitimageView6 = new ImageView();


        highExitimageView1.setFitWidth(GRID_LENGTH);
        highExitimageView1.setFitHeight(GRID_LENGTH);
        highExitimageView1.setImage(highExitImage);

        railExitimageView1.setFitWidth(GRID_LENGTH);
        railExitimageView1.setFitHeight(GRID_LENGTH);
        railExitimageView1.setImage(railExitImage);

        highExitimageView2.setFitWidth(GRID_LENGTH);
        highExitimageView2.setFitHeight(GRID_LENGTH);
        highExitimageView2.setImage(highExitImage);

        railExitimageView2.setFitWidth(GRID_LENGTH);
        railExitimageView2.setFitHeight(GRID_LENGTH);
        railExitimageView2.setImage(railExitImage);

        highExitimageView3.setFitWidth(GRID_LENGTH);
        highExitimageView3.setFitHeight(GRID_LENGTH);
        highExitimageView3.setImage(highExitImage);

        railExitimageView3.setFitWidth(GRID_LENGTH);
        railExitimageView3.setFitHeight(GRID_LENGTH);
        railExitimageView3.setImage(railExitImage);

        highExitimageView4.setFitWidth(GRID_LENGTH);
        highExitimageView4.setFitHeight(GRID_LENGTH);
        highExitimageView4.setImage(highExitImage);

        railExitimageView4.setFitWidth(GRID_LENGTH);
        railExitimageView4.setFitHeight(GRID_LENGTH);
        railExitimageView4.setImage(railExitImage);

        highExitimageView5.setFitWidth(GRID_LENGTH);
        highExitimageView5.setFitHeight(GRID_LENGTH);
        highExitimageView5.setImage(highExitImage);

        railExitimageView5.setFitWidth(GRID_LENGTH);
        railExitimageView5.setFitHeight(GRID_LENGTH);
        railExitimageView5.setImage(railExitImage);

        highExitimageView6.setFitWidth(GRID_LENGTH);
        highExitimageView6.setFitHeight(GRID_LENGTH);
        highExitimageView6.setImage(highExitImage);

        railExitimageView6.setFitWidth(GRID_LENGTH);
        railExitimageView6.setFitHeight(GRID_LENGTH);
        railExitimageView6.setImage(railExitImage);


        HBox hb1 = new HBox();
        hb1.getChildren().addAll(highExitimageView1, railExitimageView1, highExitimageView2);
        hb1.setSpacing(GRID_LENGTH);
        hb1.setLayoutX(220);
        hb1.setLayoutY(5);
        root.getChildren().add(hb1);


        HBox hb2 = new HBox();
        highExitimageView3.setRotate(180);
        highExitimageView4.setRotate(180);
        railExitimageView2.setRotate(180);
        hb2.getChildren().addAll(highExitimageView3, railExitimageView2, highExitimageView4);
        hb2.setSpacing(GRID_LENGTH);
        hb2.setLayoutX(220);
        hb2.setLayoutY(5+7*GRID_LENGTH);
        root.getChildren().add(hb2);


        VBox vb1 = new VBox();
        railExitimageView3.setRotate(270);
        highExitimageView5.setRotate(270);
        railExitimageView4.setRotate(270);
        vb1.getChildren().addAll(railExitimageView3,highExitimageView5,railExitimageView4);
        vb1.setSpacing(GRID_LENGTH);
        vb1.setLayoutX(87);
        vb1.setLayoutY(142);
        root.getChildren().add(vb1);

        VBox vb2 = new VBox();
        railExitimageView5.setRotate(90);
        highExitimageView6.setRotate(90);
        railExitimageView6.setRotate(90);
        vb2.getChildren().addAll(railExitimageView5,highExitimageView6,railExitimageView6);
        vb2.setSpacing(GRID_LENGTH);
        vb2.setLayoutX(87+7*GRID_LENGTH);
        vb2.setLayoutY(140);
        root.getChildren().add(vb2);
    }

    /*
    private void makeTiles(String diceRoll){

    }*/

    void makePlacement(String placement){

        tiles.getChildren().clear();

        for(int i=0;i<placement.length();i=i+5){
            String placementSegment = placement.substring(i,i+5);
            makeOnePlacement(placementSegment);

        }
    }
    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makeOnePlacement(String placement)  {

        //tiles.getChildren().clear();

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

        tileImageView.setLayoutX(130+((int)placement.charAt(3)-48)*GRID_LENGTH);
        tileImageView.setLayoutY(50+(placement.charAt(2)-'A')*GRID_LENGTH);

        tiles.getChildren().add(tileImageView);
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
