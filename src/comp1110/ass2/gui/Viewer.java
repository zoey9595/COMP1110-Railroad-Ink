package comp1110.ass2.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import static comp1110.ass2.RailroadInk.*;

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
    private final Group randomDice = new Group();
    private final Group setUpDice = new Group();
    private final Group specialDices = new Group();
    private final Group scores = new Group();
    private final Group computer = new Group();


    TextField textField;
    int countNum = 0;
    StringBuilder stringBuilder = new StringBuilder();
    StringBuilder comStringBuilder = new StringBuilder();

    private final Text completionText = new Text("Well done!");

    class FXPiece extends ImageView {

        String tile;

        /**
         * Construct a tile with a matching picture.
         * Set the tile size.
         *
         * @param tile A tile 5-character string
         */
        FXPiece(String tile) {
            setImage(new Image(Viewer.class.getResource(URI_BASE + tile.substring(0,2) + ".png").toString()));
            this.tile = tile;
            setFitHeight(GRID_LENGTH);
            setFitWidth(GRID_LENGTH);
            setPickOnBounds(true);
        }
    }

    /**
     * This class extends FXPiece with the capacity for it to be dragged and dropped,
     * and snap-to-grid.
     */
    class DraggableFXPiece extends FXPiece {

        // the position in the window where the piece should be when not on the board
        double homeX, homeY;
        // the real-time mouse position
        double mouseX, mouseY;
        int rotation = 0;

        /**
         * Construct a draggable piece
         * @param tile A tile 5-character string
         */
        DraggableFXPiece(String tile) {
            super(tile);

            // scroll to change orientation
            setOnScroll(event -> {
                this.rotate();
                event.consume();
            });

            // mouse press indicates begin of drag
            setOnMousePressed(event -> {
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
            });

            // mouse is being dragged
            setOnMouseDragged(event -> {
                double movementX = event.getSceneX() - mouseX;
                double movementY = event.getSceneY() - mouseY;
                this.setLayoutX(getLayoutX() + movementX);
                this.setLayoutY(getLayoutY() + movementY);
                mouseX = event.getSceneX();
                mouseY = event.getSceneY();
                event.consume();
            });

            // drag is complete
            setOnMouseReleased(event -> {
                snapToGrid();
            });
        }

        /*
         * Snap the tile to the nearest grid (if it is over the board)
         */
        private void snapToGrid() {
            if(onBoard()){
                // Reset the tile string by the location and rotation
                int row = (int)((mouseY-50) / GRID_LENGTH);
                char rowChar = (char) ('A' + row);
                int col = (int)((mouseX-100) / GRID_LENGTH);
                char colChar = (char)('0' + col);
                //rotation = (int) (this.getRotate()/90);
                char rot = (char)('0' + (rotation%8));
                changeName(rowChar,colChar,rot);
                System.out.println("The tile is " + tile);

                String temporary = stringBuilder.toString() + tile;
                System.out.println("Temporary string is " + temporary);
                if(isValidPlacementSequence(temporary)){
                    // Put the tile on the nearest grid
                    setLayoutY(50+row*GRID_LENGTH);
                    setLayoutX(100+col*GRID_LENGTH);

                    setUpUsedTiles();
                    randomDice.getChildren().remove(this);

                    stringBuilder.append(tile);
                    System.out.println("This connection is valid, and the boardstring is " + stringBuilder + " right now.");
                }else {
                    System.out.println("This connection is invalid.");
                    snapToHome();
                }
            }else{
                System.out.println("This tile is not on the board.");
                snapToHome();
            }
        }

        // Set up the used tile to be not changed
        private void setUpUsedTiles(){
            ImageView i = new ImageView(this.getImage());
            if((rotation%8)<4) {

                i.setRotate((rotation%8)*90);
            }else{
                i.setScaleX(-1);
                i.setRotate((rotation%8)*90);
            }
            i.setFitHeight(GRID_LENGTH);
            i.setFitWidth(GRID_LENGTH);
            i.setLayoutY(this.getLayoutY());
            i.setLayoutX(this.getLayoutX());
            setUpDice.getChildren().add(i);
        }

        // Change the tile string
        private void changeName(char row, char col, char rot){
            this.tile = tile.substring(0,2) + row + col + rot;
        }


        /**
         * @return true if the piece is on the board
         */
        private boolean onBoard() {
            return mouseX <= 730 && mouseX >= 100 && mouseY <= 680 && mouseY>= 50;
        }

        /**
         * Snap the tile to its home position (if it is not on the broad)
         */
        private void snapToHome() {
            setLayoutX(homeX);
            setLayoutY(homeY);
            setScaleX(1);
            setRotate(0);
        }

        /**
         * Rotate the tile by 90 degrees.
         */
        private void rotate() {

            if(rotation%8 < 3){
                setRotate(getRotate() + 90);
                rotation++;
            }else if(rotation%8 == 3){
                setRotate(getRotate() + 90);
                setScaleX(-1);
                rotation++;
            }else if(rotation%8 < 7){
                setRotate(getRotate()+90);
                rotation++;
            }else if(rotation%8 == 7){
                setRotate(getRotate() + 90);
                setScaleX(1);
                rotation++;
                //rotation=0;
            }
            System.out.println("rotation is " + rotation);
            System.out.println(this.getRotate());

        }
    }

    /*
     * Draw a basic empty board and entrances in the window.
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
        inBorder.setStrokeWidth(2);
        inBorder.setStroke(Color.RED);

        Rectangle outBorder = new Rectangle();
        outBorder.setX(0);
        outBorder.setY(0);
        outBorder.setWidth(GRID_LENGTH*7);
        outBorder.setHeight(GRID_LENGTH*7);
        outBorder.setFill(null);
        outBorder.setStrokeWidth(4);
        outBorder.setStroke(Color.BLACK);

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

    /*
     * Draw a basic empty board and entrances in the window.
     */
    private void makeComputerBoard(){

        computer.getChildren().clear();

        Rectangle inBorder = new Rectangle();
        inBorder.setX(100+GRID_LENGTH*2);
        inBorder.setY(50+GRID_LENGTH*2);
        inBorder.setWidth(GRID_LENGTH*3);
        inBorder.setHeight(GRID_LENGTH*3);
        inBorder.setFill(null);
        inBorder.setStrokeWidth(2);
        inBorder.setStroke(Color.RED);

        Rectangle outBorder = new Rectangle();
        outBorder.setX(100);
        outBorder.setY(50);
        outBorder.setWidth(GRID_LENGTH*7);
        outBorder.setHeight(GRID_LENGTH*7);
        outBorder.setFill(null);
        outBorder.setStrokeWidth(4);
        outBorder.setStroke(Color.BLACK);

        Pane p = new Pane();

        Rectangle [][] rec = new Rectangle [7][7];

        for(int i=0; i<7; i++){
            for(int j=0; j<7; j++){
                rec[i][j] = new Rectangle();
                rec[i][j].setX(100+i * GRID_LENGTH);
                rec[i][j].setY(50+j * GRID_LENGTH);
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

        computer.getChildren().addAll(p,inBorder,outBorder);

        Image highExitImage = new Image(getClass().getResourceAsStream(URI_BASE+"HighExit.png"));
        Image railExitImage = new Image(getClass().getResourceAsStream(URI_BASE+"RailExit.png"));

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
        computer.getChildren().add(hb1);

        HBox hb2 = new HBox();
        highExit[2].setRotate(180);
        highExit[3].setRotate(180);
        railExit[1].setRotate(180);
        hb2.getChildren().addAll(highExit[2], railExit[1], highExit[3]);
        hb2.setSpacing(GRID_LENGTH);
        hb2.setLayoutX(190);
        hb2.setLayoutY(5+7*GRID_LENGTH);
        computer.getChildren().add(hb2);

        VBox vb1 = new VBox();
        railExit[2].setRotate(270);
        highExit[4].setRotate(270);
        railExit[3].setRotate(270);
        vb1.getChildren().addAll(railExit[2],highExit[4],railExit[3]);
        vb1.setSpacing(GRID_LENGTH);
        vb1.setLayoutX(54);
        vb1.setLayoutY(142);
        computer.getChildren().add(vb1);

        VBox vb2 = new VBox();
        railExit[4].setRotate(90);
        highExit[5].setRotate(90);
        railExit[5].setRotate(90);
        vb2.getChildren().addAll(railExit[4],highExit[5],railExit[5]);
        vb2.setSpacing(GRID_LENGTH);
        vb2.setLayoutX(54+7*GRID_LENGTH);
        vb2.setLayoutY(140);
        computer.getChildren().add(vb2);
    }

    /**
     * Draw a full placement in the window, removing any previously drawn one.
     *
     * @param placement A full tile placement
     */
    void makePlacement(String placement){

        //tiles.getChildren().clear();

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

    /**
     * Set 6 special dices, only 3 can be used in the game.
     */
    private void setSpecialDices(){
        specialDices.getChildren().clear();

        specialDices.setLayoutX(VIEWER_WIDTH-GRID_LENGTH*1.25);
        specialDices.setLayoutY(40);

        Rectangle border = new Rectangle();
        border.setHeight(40 + GRID_LENGTH * 6 + 20*5);
        border.setWidth(GRID_LENGTH+12);
        border.setLayoutX(-3);
        border.setLayoutY(-3);
        border.setFill(null);
        border.setStrokeWidth(3);
        border.setStroke(Color.SILVER);

        Label label = new Label("Special Tiles");
        label.setFont(new Font("American Typewriter", 16));

        String[] specialTilePlacements = new String[6];
        DraggableFXPiece[] draggableSpeicalTiles = new DraggableFXPiece[6];

        for(int i=0;i<6;i++){
            specialTilePlacements[i] = "S"+ (char)('0'+i) +"A00";
            draggableSpeicalTiles[i] = new DraggableFXPiece(specialTilePlacements[i]);
            draggableSpeicalTiles[i].homeX = 4;
            draggableSpeicalTiles[i].homeY = 30 + GRID_LENGTH * i + 20*i;
            draggableSpeicalTiles[i].setLayoutX(4);
            draggableSpeicalTiles[i].setLayoutY(30 + GRID_LENGTH * i + 20*i);
        }

        specialDices.getChildren().addAll(border,label,draggableSpeicalTiles[0], draggableSpeicalTiles[1],
                draggableSpeicalTiles[2], draggableSpeicalTiles[3], draggableSpeicalTiles[4], draggableSpeicalTiles[5]);
    }

    /**
     *  Make the dice can be dragged and draw them on the right side of the board.
     */
    private void generateRandomTiles(){

        randomDice.getChildren().clear();

        randomDice.setLayoutX(VIEWER_WIDTH-GRID_LENGTH*2.5);
        randomDice.setLayoutY(40);

        Rectangle border = new Rectangle();
        border.setHeight(GRID_LENGTH * 4 + 20*5.5);
        border.setWidth(GRID_LENGTH+8);
        border.setLayoutX(-3);
        border.setLayoutY(-3);
        border.setFill(null);
        border.setStrokeWidth(3);
        border.setStroke(Color.SILVER);

        Label label = new Label("     Tiles");
        label.setFont(new Font("American Typewriter", 18));

        String s = generateDiceRoll();
        //computerRandam = s;

        Button AIMove  = new Button("AI Move");
        AIMove.setLayoutX(705);
        AIMove.setLayoutY(VIEWER_HEIGHT-50);
        controls.getChildren().add(AIMove);

        String comMove = generateMove(comStringBuilder.toString(),s);
        comStringBuilder.append(comMove);

        AIMove.setOnAction(e -> {
            computer.getChildren().remove(tiles);
            makePlacement(comStringBuilder.toString());
            computer.getChildren().add(tiles);
        });

        String[] tilePlacements = new String[4];
        DraggableFXPiece[] draggableTiles = new DraggableFXPiece[4];

        tilePlacements[0] = s.substring(0,2)+"A00";
        tilePlacements[1] = s.substring(2,4)+"A00";
        tilePlacements[2] = s.substring(4,6)+"A00";
        tilePlacements[3] = s.substring(6)+"A00";

        for(int i=0; i<4; i++) {
            draggableTiles[i] = new DraggableFXPiece(tilePlacements[i]);
            draggableTiles[i].homeY = 30 + GRID_LENGTH * i + 20*i;
            draggableTiles[i].setLayoutY(30 + GRID_LENGTH * i + 20*i);
        }

        randomDice.getChildren().addAll(border,label,draggableTiles[0], draggableTiles[1], draggableTiles[2], draggableTiles[3]);
    }

    /**
     * Generate four random dices
     */
    private void setRandomTiles(){
        Button generator  = new Button("Generate");
        generator.setLayoutX(510);
        generator.setLayoutY(VIEWER_HEIGHT - 50);

        generator.setOnAction(e -> {
            countNum++;
            System.out.println(countNum);
            if (countNum < 8) {
                generateRandomTiles();
            }else{
                getScore();
                showCompletion();
            }
        });

        //root.getChildren().add(generator);
        controls.getChildren().add(generator);
    }

    /**
     * Count the score
     */
    private void getScore(){

        scores.getChildren().clear();

        scores.setLayoutX(VIEWER_WIDTH-GRID_LENGTH*2.5);
        scores.setLayoutY(GRID_LENGTH*6-3);

        int scoreNum = getBasicScore(stringBuilder.toString());
        int scoreCom = getBasicScore(comStringBuilder.toString());
        //getAdvancedScore(stringBuilder.toString());

        Rectangle border = new Rectangle();
        border.setHeight(GRID_LENGTH*2);
        border.setWidth(GRID_LENGTH);
        border.setFill(null);
        border.setStrokeWidth(3);
        border.setStroke(Color.RED);

        Rectangle borderCom = new Rectangle();
        borderCom.setLayoutX(VIEWER_WIDTH-GRID_LENGTH*2.5);
        borderCom.setLayoutY(GRID_LENGTH*6-3);
        borderCom.setHeight(GRID_LENGTH*2);
        borderCom.setWidth(GRID_LENGTH);
        borderCom.setFill(null);
        borderCom.setStrokeWidth(3);
        borderCom.setStroke(Color.RED);

        Label label1 = new Label("SCORE");
        label1.setFont(new Font("American Typewriter", 22));
        label1.setLayoutX(10);
        label1.setLayoutY(10);

        Label label2 = new Label( "" + scoreNum);
        label2.setFont(new Font("American Typewriter", 40));
        label2.setLayoutX(24);
        label2.setLayoutY(75);

        Label label3 = new Label("SCORE");
        label3.setFont(new Font("American Typewriter", 22));
        label3.setLayoutX(VIEWER_WIDTH-GRID_LENGTH*2.5);
        label3.setLayoutY(GRID_LENGTH*6+3);

        Label label4 = new Label( "" + scoreCom);
        label4.setFont(new Font("American Typewriter", 40));
        label4.setLayoutX(VIEWER_WIDTH-GRID_LENGTH*2.5);
        label4.setLayoutY(GRID_LENGTH*6+35);

        scores.getChildren().addAll(border,label1,label2);
        computer.getChildren().addAll(borderCom,label3,label4);
    }

    /**
     * Create the message to be displayed when the player completes the puzzle.
     */
    private void makeCompletion() {
        DropShadow ds = new DropShadow();
        ds.setOffsetY(4.0f);
        ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
        completionText.setFill(Color.RED);
        completionText.setEffect(ds);
        completionText.setCache(true);
        completionText.setFont(Font.font("American Typewriter", FontWeight.EXTRA_BOLD, 80));
        completionText.setLayoutX(VIEWER_WIDTH / 2.0-380);
        completionText.setLayoutY(350);
        completionText.setTextAlignment(TextAlignment.CENTER);
        board.getChildren().add(completionText);
    }

    /**
     * Show the completion message
     */
    private void showCompletion() {
        completionText.toFront();
        completionText.setOpacity(1);
    }

    /**
     * IQStars the completion message
     */
    private void hideCompletion() {
        completionText.toBack();
        completionText.setOpacity(0);
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(150);
        Button button = new Button("Refresh");
        button.setOnAction(e -> {
            makePlacement(textField.getText());
            textField.clear();
        });
        Button button2 = new Button("New Game");
        button2.setOnAction(e -> {
            newGame();
        });

        Image notice = new Image(Viewer.class.getResource(URI_BASE + "question.png").toString());
        ImageView noticeImageView = new ImageView(notice);
        noticeImageView.setFitHeight(30);
        noticeImageView.setFitWidth(30);
        Button button3 = new Button();
        button3.setGraphic(noticeImageView);
        button3.setOnMouseClicked(e-> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setWidth(500);
            alert.titleProperty().set("Guide");
            alert.headerTextProperty().set("1. Singal Player\n" +
                    "   a. If you want to play a solo game, please directly click the generate button.\n" +
                    "   b. Then click the generate button, and make you own move by dragging it on the board\n" +
                    "   c. After 7 rounds generation, the final score will show!\n" +
                    " \n" +
                    "2. Play with the computer\n" +
                    "   a. If you want to play with a computer, please click the VS Computer button first.\n" +
                    "   b. Then you need to move first after click the generate button, when you finish,\n" +
                    "      click the AI Move button, the computer will generate move.\n" +
                    "   c. Repeat the generate action seven times, then the score will show!");
            alert.showAndWait();
        });

        Image music = new Image(Viewer.class.getResource(URI_BASE + "music.png").toString());
        ImageView musicImageView = new ImageView(music);
        musicImageView.setFitHeight(30);
        musicImageView.setFitWidth(30);

        HBox hb = new HBox();
        hb.getChildren().addAll(label1, textField, button2, button);
        hb.setSpacing(10);
        hb.setLayoutX(100);
        hb.setLayoutY(VIEWER_HEIGHT - 50);
        controls.getChildren().add(hb);
        controls.getChildren().add(button3);
    }

    /**
     * Start a new game, resetting everything as necessary
     */
    private void newGame() {
        try {
            makePlacement("");
            stringBuilder = new StringBuilder();
            comStringBuilder = new StringBuilder();
            hideCompletion();
            randomDice.getChildren().clear();
            setUpDice.getChildren().clear();
            scores.getChildren().clear();
            computer.getChildren().clear();
            setSpecialDices();
            countNum = 0;

        } catch (IllegalArgumentException e) {
            System.err.println("Uh oh. " + e);
            e.printStackTrace();
            Platform.exit();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("StepsGame Viewer");
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        Stage aiStage = new Stage();
        aiStage.setTitle("Computer Player");
        Scene aiScene = new Scene(computer, VIEWER_WIDTH, VIEWER_HEIGHT);

        // Set computer player
        Button vsComputer = new Button("VS Computer");
        vsComputer.setLayoutX(590);
        vsComputer.setLayoutY(VIEWER_HEIGHT-50);

        vsComputer.setOnAction(e->{

            makeComputerBoard();

            aiStage.setScene(aiScene);
            aiStage.show();
        });

        root.getChildren().add(vsComputer);
        root.getChildren().add(controls);
        root.getChildren().add(board);
        root.getChildren().add(tiles);
        root.getChildren().add(randomDice);
        root.getChildren().add(setUpDice);
        root.getChildren().add(specialDices);
        root.getChildren().add(scores);

        makeControls();
        makeBoard();
        setRandomTiles();
        setSpecialDices();
        makeCompletion();
        hideCompletion();

        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}