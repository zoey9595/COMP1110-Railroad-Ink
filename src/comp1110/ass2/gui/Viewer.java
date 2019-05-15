package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import static comp1110.ass2.RailroadInk.generateDiceRoll;
import static comp1110.ass2.RailroadInk.isTilePlacementWellFormed;

/**
 * Author: Yuqing Zhai
 *
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
    private final Group drop = new Group();

    TextField textField;
    String[] tilePlacements = new String[1000];

    /*
     * Draw a basic empty board in the window.
     */
    private void makeBoard(){
        board.setLayoutX(100);
        board.setLayoutY(50);
        board.getChildren().clear();

        Rectangle inBorder = new Rectangle();
        inBorder.setX(GRID_LENGTH*2);
        inBorder.setY(GRID_LENGTH*2);
        inBorder.setWidth(GRID_LENGTH*3);
        inBorder.setHeight(GRID_LENGTH*3);
        inBorder.setFill(null);
        inBorder.setStrokeWidth(5);
        inBorder.setStroke(Color.RED);

        Rectangle outBorder = new Rectangle();
        inBorder.setX(0);
        inBorder.setY(0);
        inBorder.setWidth(GRID_LENGTH*7);
        inBorder.setHeight(GRID_LENGTH*7);
        outBorder.setFill(null);
        inBorder.setStrokeWidth(3);
        inBorder.setStroke(Color.BLACK);

        Pane p = new Pane();

        Rectangle [][] rec = new Rectangle [7][7];

        for(int i=0; i<7; i++){
            for(int j=0; j<7; j++){
                rec[i][j] = new Rectangle();
                rec[i][j].setX(i * GRID_LENGTH);
                rec[i][j].setY(j * GRID_LENGTH);
                rec[i][j].setWidth(GRID_LENGTH);
                rec[i][j].setHeight(GRID_LENGTH);
                if((i >= 2 && i < 5) && (j >=2 && j<5)) {
                    rec[i][j].setFill(Color.LAVENDERBLUSH);
                    rec[i][j].setStroke(Color.BLACK);

                }else{
                    rec[i][j].setFill(Color.ALICEBLUE);
                    rec[i][j].setStroke(Color.BLACK);
                }
                p.getChildren().add(rec[i][j]);
            }
        }

        board.getChildren().addAll(p,inBorder,outBorder);

        Image highExitImage = new Image(getClass().getResourceAsStream(URI_BASE+"HighExit.png"));
        Image railExitImage = new Image(getClass().getResourceAsStream(URI_BASE+"RailExit.png"));

        //Image railExitImagenew = new Image(new FileInputStream("src/comp1110/ass2/gui/assets/RailExit.png"));

        ImageView[] highExit = new ImageView[6];
        ImageView[] railExit = new ImageView[6];

        for (int i=0; i<6; i++){
            highExit[i] = new ImageView();
            railExit[i] = new ImageView();
            highExit[i].setImage(highExitImage);
            railExit[i].setImage(railExitImage);
            highExit[i].setFitWidth(GRID_LENGTH);
            highExit[i].setFitHeight(GRID_LENGTH);
            railExit[i].setFitWidth(GRID_LENGTH);
            railExit[i].setFitHeight(GRID_LENGTH);
        }

        HBox hb1 = new HBox();
        hb1.getChildren().addAll(highExit[0], railExit[0], highExit[1]);
        hb1.setSpacing(GRID_LENGTH);
        hb1.setLayoutX(190);
        hb1.setLayoutY(5);
        root.getChildren().add(hb1);


        HBox hb2 = new HBox();
        highExit[2].setRotate(180);
        highExit[3].setRotate(180);
        railExit[1].setRotate(180);
        hb2.getChildren().addAll(highExit[2], railExit[1], highExit[3]);
        hb2.setSpacing(GRID_LENGTH);
        hb2.setLayoutX(190);
        hb2.setLayoutY(5+7*GRID_LENGTH);
        root.getChildren().add(hb2);


        VBox vb1 = new VBox();
        railExit[2].setRotate(270);
        highExit[4].setRotate(270);
        railExit[3].setRotate(270);
        vb1.getChildren().addAll(railExit[2],highExit[4],railExit[3]);
        vb1.setSpacing(GRID_LENGTH);
        vb1.setLayoutX(54);
        vb1.setLayoutY(142);
        root.getChildren().add(vb1);

        VBox vb2 = new VBox();
        railExit[4].setRotate(90);
        highExit[5].setRotate(90);
        railExit[5].setRotate(90);
        vb2.getChildren().addAll(railExit[4],highExit[5],railExit[5]);
        vb2.setSpacing(GRID_LENGTH);
        vb2.setLayoutX(54+7*GRID_LENGTH);
        vb2.setLayoutY(140);
        root.getChildren().add(vb2);
    }


    class FXPiece extends ImageView {

        String tile;
        //int row;
        //int col;
        //int rotation;
        //List<Grid> grids = new ArrayList<>();

        private Image getImageForTile(String tile) {
            Image[] images = new Image[15];

            for (int i = 0; i < 15; i++) {
                if (i < 6) {
                    images[i] = new Image(getClass().getResourceAsStream(URI_BASE + 'A' + i + ".png"));
                } else if (i < 9) {
                    images[i] = new Image(getClass().getResourceAsStream(URI_BASE + 'B' + (i - 6) + ".png"));
                } else {
                    images[i] = new Image(getClass().getResourceAsStream(URI_BASE + 'S' + (i - 9) + ".png"));
                }
            }

            switch (tile) {
                case "A0":
                    return images[0];
                case "A1":
                    return images[1];
                case "A2":
                    return images[2];
                case "A3":
                    return images[3];
                case "A4":
                    return images[4];
                case "A5":
                    return images[5];
                case "B0":
                    return images[6];
                case "B1":
                    return images[7];
                case "B2":
                    return images[8];
                case "S0":
                    return images[9];
                case "S1":
                    return images[10];
                case "S2":
                    return images[11];
                case "S3":
                    return images[12];
                case "S4":
                    return images[13];
                case "S5":
                    return images[14];
            }
            return null;
        }

        /**
         * Construct a piece at a particular place on the
         * board at a given orientation.
         *
         * @param tile a five-character tile string
         */
        FXPiece(String tile) {
            this.tile = tile;
            setImage(getImageForTile(tile.substring(0, 2)));
            //this.row = tile.charAt(2);
            //this.col = tile.charAt(3);
            //this.rotation = tile.charAt(4) - '0';
            setFitHeight(GRID_LENGTH);
            setFitWidth(GRID_LENGTH);
        }
    }

    /**
     * This class extends FXPatch with the capacity for it to be dragged and dropped,
     * and snap-to-grid.
     */
    class DraggableFXPiece extends FXPiece {

        double homeX, homeY;         // the position in the window where the piece should be when not on the board
        double mouseX, mouseY;
        /**
         * Construct a draggable piece
         *
         * @param tile The piece identifier ('A' - 'L')
         */
        DraggableFXPiece(String tile) {
            super(tile);
            homeX = 0;
            homeY = 0;
            setLayoutX(homeX);
            setLayoutY(homeY);

            setOnMousePressed(event -> {      // mouse press indicates begin of drag
                System.out.println("pressed");
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });

            setOnMouseDragged(event -> {      // mouse is being dragged
                toFront();
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                setLayoutX(getLayoutX() + movementX);
                setLayoutY(getLayoutY() + movementY);
                //drag(movementX, movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
                System.out.println(getLayoutX());
            });

            setOnMouseReleased(event -> {     // drag is complete
                System.out.println("released");
                snapToGrid();
            });

            /* event handlers */
            setOnScroll(event -> {            // scroll to change orientation
                event.consume();
            });

        }

        protected void drag(double movementX, double movementY){
            setLayoutX(getLayoutX() + movementX);
            setLayoutY(getLayoutY() + movementY);
        }

        /*
         * Snap the piece to the nearest grid position (if it is over the grid)
         */
        private void snapToGrid() {
            if(onBoard()){
                setLayoutX(100 + 5 * GRID_LENGTH);
                setLayoutY(50 + 5 * GRID_LENGTH);
            }else{
                snapToHome();
            }
        }


        /**
         * @return true if the piece is on the board
         */
        private boolean onBoard() {
            return getLayoutX() > (VIEWER_HEIGHT - 730) && (getLayoutX() < 100)
                    && getLayoutY() > (VIEWER_HEIGHT - 680) && (getLayoutY() < 50);
        }

        /**
         * Snap the piece to its home position (if it is not on the grid)
         */
        private void snapToHome() {
            setLayoutX(homeX);
            setLayoutY(homeY);
            setRotate(0);
            //piecePlacements[piece.ordinal()] = IQStars.NOT_PLACED;
            //setOpacity(1.0);
        }

        /**
         * Rotate the piece by 60 degrees.
         */
        /*private void rotate() {
            rotation = (rotation + 1) % 6;
            updateRotation();
        }*/

        /**
         * Determine the grid-position of the origin of the piece (0 .. 12)
         * or -1 if it is off the grid, taking into account its rotation.
         */
        private void setPosition() {
            //row = (int) ((getLayoutY()-50)/90);
            //double xOffset = row % 2 == 0 ? 0 : 0.5;
            //col = (int) ((getLayoutX()-100)/90);
        }

        /**
         * @return the piece placement represented as a string
         */
        public String toString() {
            return "" + tilePlacements;
        }
    }


    private String getPlacementString() {
        StringBuilder sb = new StringBuilder();
        for (String tilePlacementString : tilePlacements) {
            sb.append(tilePlacementString);
        }
        return sb.toString();
    }

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

        tileImageView.setLayoutX(100+((int)placement.charAt(3)-48)*GRID_LENGTH);
        tileImageView.setLayoutY(50+(placement.charAt(2)-'A')*GRID_LENGTH);

        tiles.getChildren().add(tileImageView);
        // FIXME Task 4: implement the simple placement viewer
    }

    private void makeTiles(){
        Button generator  = new Button("Generate");
        generator.setLayoutX(600);
        generator.setLayoutY(VIEWER_HEIGHT - 50);

        generator.setOnAction(e -> {

            drop.getChildren().clear();

            Label label = new Label("   Tiles:");
            label.setFont(new Font("American Typewriter", 20));

            String s = generateDiceRoll();
            DraggableFXPiece[] tiles = new DraggableFXPiece[4];
            String[] orginaldices = new String[4];

            orginaldices[0] = s.substring(0,2)+"000";
            orginaldices[1] = s.substring(2,4)+"000";
            orginaldices[2] = s.substring(4,6)+"000";
            orginaldices[3] = s.substring(6)+"000";

            for(int i=0; i<4; i++) {
                tiles[i] = new DraggableFXPiece(orginaldices[i]);
                System.out.println(orginaldices[i]);
            }

            VBox vb = new VBox();
            vb.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
            vb.setSpacing(15);
            vb.setLayoutX(VIEWER_WIDTH-GRID_LENGTH-100);
            vb.setLayoutY(50);
            vb.getChildren().addAll(label,tiles[0], tiles[1], tiles[2], tiles[3]);
            drop.getChildren().add(vb);
        });

        root.getChildren().add(generator);
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
        root.getChildren().add(drop);

        makeControls();
        makeBoard();
        makeTiles();

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

