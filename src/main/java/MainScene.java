package main.java;

import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.ResourceBundle;

public class MainScene implements Initializable {
    private  double[][] matrixH1 = {
            {1,-1},
            {0,1}};
    private double[][] backMatrixH1 = {
            {1,1},
            {0,1}};

    private double[][] matrixH2 = {
            {1,-2,1},
            {0,2,-2},
            {0,0,1}};
    private double[][] backMatrixH2 = {
            {1,1,1},
            {0,1.0/2.0,1},
            {0,0,1}};

    private double[][] matrixH3 = {
            {1,-3,3,-1},
            {0,3,-6,3},
            {0,0,3,-3},
            {0,0,0,1}};
    private double[][] backMatrixH3 = {
            {1,1,1,1},
            {0,1.0/3.0,2.0/3.0,1},
            {0,0,1.0/3.0,1},
            {0,0,0,1}};

    private double [][] matrixH4 = {
            {1,-4,6,-4,1},
            {0,4,-12,12,-4},
            {0,0,6,-12,6},
            {0,0,0,4,-4},
            {0,0,0,0,1}};
    private double [][] backMatrixH4 = {
            {1,1,1,1,1},
            {0,1.0/4.0,1.0/2.0,3.0/4.0,1},
            {0,0,1.0/6.0,1.0/2.0,1},
            {0,0,0,1.0/4.0,1},
            {0,0,0,0,1}};

    private double[][] matrixH5 = {
            {1,-5,10,-10,5,-1},
            {0,5,-20,30,-20,5},
            {0,0,10,-30,30,-10},
            {0,0,0,10,-20,10},
            {0,0,0,0,5,-5},
            {0,0,0,0,0,1}};

    private double[][] backMatrixH5 = {
            {1,1,1,1,1,1},
            {0,1.0/5.0,2.0/5.0,3.0/5.0,4.0/5.0,1},
            {0,0,1.0/10.0,3.0/10.0,3.0/5.0,1},
            {0,0,0,1.0/10.0,2.0/5.0,1},
            {0,0,0,0,1./5.,1},
            {0,0,0,0,0,1}};

    private static int speed = 40;

    @FXML
    private ChoiceBox<String> orderChoiceBox;

    @FXML
    private Label startPointLabel;
    @FXML
    private Label endPointLabel;
    @FXML
    private Label tempPoint1Label;
    @FXML
    private Label tempPoint2Label;
    @FXML
    private Label tempPoint3Label;
    @FXML
    private Label tempPoint4Label;

    @FXML
    private Label amountPoints;

    @FXML
    private Slider speedSlider;

    @FXML
    private Button stopButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button buildButton;

    @FXML
    private Button upOrderButton;

    @FXML
    private Button downOrderButton;

    @FXML
    private Canvas canvas;
    private static GraphicsContext graphicsContext;

    private DecimalFormat dec = new DecimalFormat("#0.000");

    private Bezier bezier;
    private HashMap<String, Point> pointsMap = new HashMap<>();
    private int bezierOrder = 0;
    private boolean bezierOk = false;

    private Point tempPoint;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setLineWidth(2);
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        if (bezierOrder != 0){
                            if (tempPoint != null){
                                tempPoint.delete();
                            }
                            if (!bezierOk){
                                for (Point point: pointsMap.values()){
                                    point.paint(Color.BLACK);
                                }
                            }
                            tempPoint = new Point(event.getX(), event.getY());
                            tempPoint.paint(Color.BLACK);
                        }
                    }
                });

        String[] orders = new String[]{"First", "Second", "Third", "Fourth", "Fifth"};
        String[] ordersRu = new String[]{"Первый", "Второй", "Третий", "Четвертый", "Пятый"};
        orderChoiceBox.setItems(FXCollections.observableArrayList(ordersRu));
        orderChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                resetButtonAction();
                switch (orders[newValue.intValue()]){
                    case "First": bezierOrder = 1;
                        tempPoint1Label.setVisible(false);
                        tempPoint1Label.setDisable(true);
                        tempPoint2Label.setVisible(false);
                        tempPoint2Label.setDisable(true);
                        tempPoint3Label.setVisible(false);
                        tempPoint3Label.setDisable(true);
                        tempPoint4Label.setVisible(false);
                        tempPoint4Label.setDisable(true);
                        amountPoints.setText("Выбрано точек: " + 0 + "/" + 2);
                        break;
                    case "Second": bezierOrder = 2;
                        tempPoint1Label.setVisible(true);
                        tempPoint1Label.setDisable(false);
                        tempPoint2Label.setVisible(false);
                        tempPoint2Label.setDisable(true);
                        tempPoint3Label.setVisible(false);
                        tempPoint3Label.setDisable(true);
                        tempPoint4Label.setVisible(false);
                        tempPoint4Label.setDisable(true);
                        amountPoints.setText("Выбрано точек: " + 0 + "/" + 3);
                        break;
                    case "Third": bezierOrder = 3;
                        tempPoint1Label.setVisible(true);
                        tempPoint1Label.setDisable(false);
                        tempPoint2Label.setVisible(true);
                        tempPoint2Label.setDisable(false);
                        tempPoint3Label.setVisible(false);
                        tempPoint3Label.setDisable(true);
                        tempPoint4Label.setVisible(false);
                        tempPoint4Label.setDisable(true);
                        amountPoints.setText("Выбрано точек: " + 0 + "/" + 4);
                        break;
                    case "Fourth": bezierOrder = 4;
                        tempPoint1Label.setVisible(true);
                        tempPoint1Label.setDisable(false);
                        tempPoint2Label.setVisible(true);
                        tempPoint2Label.setDisable(false);
                        tempPoint3Label.setVisible(true);
                        tempPoint3Label.setDisable(false);
                        tempPoint4Label.setVisible(false);
                        tempPoint4Label.setDisable(true);
                        amountPoints.setText("Выбрано точек: " + 0 + "/" + 5);
                        break;
                    case "Fifth": bezierOrder = 5;
                        tempPoint1Label.setVisible(true);
                        tempPoint1Label.setDisable(false);
                        tempPoint2Label.setVisible(true);
                        tempPoint2Label.setDisable(false);
                        tempPoint3Label.setVisible(true);
                        tempPoint3Label.setDisable(false);
                        tempPoint4Label.setVisible(true);
                        tempPoint4Label.setDisable(false);
                        amountPoints.setText("Выбрано точек: " + 0 + "/" + 6);
                        break;
                }
                amountPoints.setText("Выбрано точек: " + 0 + "/" + (bezierOrder + 1));
            }
        });
        speedSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, //
                                Number oldValue, Number newValue) {
                switch (newValue.intValue()){
                    case 1: speed = 100;
                        break;
                    case 2: speed = 90;
                        break;
                    case 3: speed = 80;
                        break;
                    case 4: speed = 70;
                        break;
                    case 5: speed = 60;
                        break;
                    case 6: speed = 50;
                        break;
                    case 7: speed = 40;
                        break;
                    case 8: speed = 30;
                        break;
                    case 9: speed = 20;
                        break;
                    case 10: speed = 10;
                        break;
                }
            }
        });

        bezier = new Bezier();
    }

    public void startPointLabelClicked(){
        if ((tempPoint != null) && (orderChoiceBox.getValue() != null)){
            pointsMap.put("Start point", tempPoint);
            startPointLabel.setText("Начальная точка:\n(" +
                    dec.format(tempPoint.getX()) + "; " + dec.format(tempPoint.getY()) + ")");
            amountPoints.setText("Выбрано точек: " + pointsMap.size() + "/" + (bezierOrder + 1));
        }
    }

    public void endPointLabelClicked(){
        if ((tempPoint != null) && (orderChoiceBox.getValue() != null)){
            pointsMap.put("End point", tempPoint);
            endPointLabel.setText("Конечная точка:\n(" +
                    dec.format(tempPoint.getX()) + "; " + dec.format(tempPoint.getY()) + ")");
            amountPoints.setText("Выбрано точек: " + pointsMap.size() + "/" + (bezierOrder + 1));
        }
    }

    public void tempPoint1Clicked(){
        if ((tempPoint != null) && (orderChoiceBox.getValue() != null)){
            pointsMap.put("Temp point 1", tempPoint);
            tempPoint1Label.setText("Первая промежуточная точка:\n(" +
                    dec.format(tempPoint.getX()) + "; " + dec.format(tempPoint.getY()) + ")");
            amountPoints.setText("Выбрано точек: " + pointsMap.size() + "/" + (bezierOrder + 1));
        }
    }

    public void tempPoint2Clicked(){
        if ((tempPoint != null) && (orderChoiceBox.getValue() != null)){
            pointsMap.put("Temp point 2", tempPoint);
            tempPoint2Label.setText("Вторая промежуточная точка:\n(" +
                    dec.format(tempPoint.getX()) + "; " + dec.format(tempPoint.getY()) + ")");
            amountPoints.setText("Выбрано точек: " + pointsMap.size() + "/" + (bezierOrder + 1));
        }
    }

    public void tempPoint3Clicked(){
        if ((tempPoint != null) && (orderChoiceBox.getValue() != null)){
            pointsMap.put("Temp point 3", tempPoint);
            tempPoint3Label.setText("Третья промежуточная точка:\n(" +
                    dec.format(tempPoint.getX()) + "; " + dec.format(tempPoint.getY()) + ")");
            amountPoints.setText("Выбрано точек: " + pointsMap.size() + "/" + (bezierOrder + 1));
        }
    }

    public void tempPoint4Clicked(){
        if ((tempPoint != null) && (orderChoiceBox.getValue() != null)){
            pointsMap.put("Temp point 4", tempPoint);
            tempPoint4Label.setText("Четвертая промежуточная точка:\n(" +
                    dec.format(tempPoint.getX()) + "; " + dec.format(tempPoint.getY()) + ")");
            amountPoints.setText("Выбрано точек: " + pointsMap.size() + "/" + (bezierOrder + 1));
        }
    }

    public void buildButtonAction(){
        if (pointsMap.size() != (bezierOrder + 1)){
            Label label = new Label("Выберите все необходимые точки!");
            StackPane secondaryLayout = new StackPane();
            secondaryLayout.getChildren().add(label);
            Scene secondScene = new Scene(secondaryLayout, 300, 100);
            Stage newWindow = new Stage();
            newWindow.setTitle("Message");
            newWindow.setScene(secondScene);
            newWindow.setX(Main.primaryStage.getX() + 300);
            newWindow.setY(Main.primaryStage.getY() + 250);
            newWindow.initModality(Modality.APPLICATION_MODAL);
            newWindow.setResizable(false);
            newWindow.show();
        }
        else{
            Timeline timeline = bezier.getTimeline();
            if (timeline != null){
                timeline.stop();
            }
            clearCanvas();
            switch (bezierOrder){
                case 1: bezier.order1(pointsMap);
                    break;
                case 2: bezier.order2(pointsMap);
                    break;
                case 3: bezier.order3(pointsMap);
                    break;
                case 4: bezier.order4(pointsMap);
                    break;
                case 5: bezier.order5(pointsMap);
                    break;
            }
            bezierOk = true;
        }
    }

    public void upOrderButtonAction(){
        if ((!pointsMap.isEmpty()) && bezierOk){
            Timeline timeline = bezier.getTimeline();
            if (timeline != null){
                timeline.stop();
            }
            if (bezierOrder == 1){
                HashMap<String, Point> newPointsMap = new HashMap<>();
                Point newTempPoint1 = new Point((1.0/2.0)*pointsMap.get("Start point").getX()+(1.0-(1.0/2.0))*pointsMap.get("End point").getX(),
                        (1.0/2.0)*pointsMap.get("Start point").getY()+(1.0-(1.0/2.0))*pointsMap.get("End point").getY());
                newPointsMap.put("Start point", pointsMap.get("Start point"));
                newPointsMap.put("Temp point 1", newTempPoint1);
                newPointsMap.put("End point", pointsMap.get("End point"));
                pointsMap = newPointsMap;
                bezier.order2(pointsMap);
                bezierOrder = 2;
                amountPoints.setText("Порядок кривой изменился с 1 на 2");
            }
            else if (bezierOrder == 2){
                HashMap<String, Point> newPointsMap = new HashMap<>();
                Point newTempPoint1 = new Point((1.0/3.0)*pointsMap.get("Start point").getX()+(1.0-(1.0/3.0))*pointsMap.get("Temp point 1").getX(),
                        (1.0/3.0)*pointsMap.get("Start point").getY()+(1.0-(1.0/3.0))*pointsMap.get("Temp point 1").getY());
                Point newTempPoint2 = new Point((2.0/3.0)*pointsMap.get("Temp point 1").getX()+(1.0-(2.0/3.0))*pointsMap.get("End point").getX(),
                        (2.0/3.0)*pointsMap.get("Temp point 1").getY()+(1.0-(2.0/3.0))*pointsMap.get("End point").getY());

                newPointsMap.put("Start point", pointsMap.get("Start point"));
                newPointsMap.put("Temp point 1", newTempPoint1);
                newPointsMap.put("Temp point 2", newTempPoint2);
                newPointsMap.put("End point", pointsMap.get("End point"));
                pointsMap = newPointsMap;
                bezier.order3(pointsMap);
                bezierOrder = 3;
                amountPoints.setText("Порядок кривой изменился с 2 на 3");
            }
            else if (bezierOrder == 3){
                HashMap<String, Point> newPointsMap = new HashMap<>();
                Point newTempPoint1 = new Point((1.0/4.0)*pointsMap.get("Start point").getX()+(1.0-(1.0/4.0))*pointsMap.get("Temp point 1").getX(),
                        (1.0/4.0)*pointsMap.get("Start point").getY()+(1.0-(1.0/4.0))*pointsMap.get("Temp point 1").getY());
                Point newTempPoint2 = new Point((2.0/4.0)*pointsMap.get("Temp point 1").getX()+(1.0-(2.0/4.0))*pointsMap.get("Temp point 2").getX(),
                        (2.0/4.0)*pointsMap.get("Temp point 1").getY()+(1.0-(2.0/4.0))*pointsMap.get("Temp point 2").getY());
                Point newTempPoint3 = new Point((3.0/4.0)*pointsMap.get("Temp point 2").getX()+(1.0-(3.0/4.0))*pointsMap.get("End point").getX(),
                        (3.0/4.0)*pointsMap.get("Temp point 2").getY()+(1.0-(3.0/4.0))*pointsMap.get("End point").getY());

                newPointsMap.put("Start point", pointsMap.get("Start point"));
                newPointsMap.put("Temp point 1", newTempPoint1);
                newPointsMap.put("Temp point 2", newTempPoint2);
                newPointsMap.put("Temp point 3", newTempPoint3);
                newPointsMap.put("End point", pointsMap.get("End point"));
                pointsMap = newPointsMap;
                bezier.order4(pointsMap);
                bezierOrder = 4;
                amountPoints.setText("Порядок кривой изменился с 3 на 4");
            }
            else if (bezierOrder == 4){
                HashMap<String, Point> newPointsMap = new HashMap<>();
                Point newTempPoint1 = new Point((1.0/5.0)*pointsMap.get("Start point").getX()+(1.0-(1.0/5.0))*pointsMap.get("Temp point 1").getX(),
                        (1.0/5.0)*pointsMap.get("Start point").getY()+(1.0-(1.0/5.0))*pointsMap.get("Temp point 1").getY());
                Point newTempPoint2 = new Point((2.0/5.0)*pointsMap.get("Temp point 1").getX()+(1.0-(2.0/5.0))*pointsMap.get("Temp point 2").getX(),
                        (2.0/5.0)*pointsMap.get("Temp point 1").getY()+(1.0-(2.0/5.0))*pointsMap.get("Temp point 2").getY());
                Point newTempPoint3 = new Point((3.0/5.0)*pointsMap.get("Temp point 2").getX()+(1.0-(3.0/5.0))*pointsMap.get("Temp point 3").getX(),
                        (3.0/5.0)*pointsMap.get("Temp point 2").getY()+(1.0-(3.0/5.0))*pointsMap.get("Temp point 3").getY());
                Point newTempPoint4 = new Point((4.0/5.0)*pointsMap.get("Temp point 3").getX()+(1.0-(4.0/5.0))*pointsMap.get("End point").getX(),
                        (4.0/5.0)*pointsMap.get("Temp point 3").getY()+(1.0-(4.0/5.0))*pointsMap.get("End point").getY());

                newPointsMap.put("Start point", pointsMap.get("Start point"));
                newPointsMap.put("Temp point 1", newTempPoint1);
                newPointsMap.put("Temp point 2", newTempPoint2);
                newPointsMap.put("Temp point 3", newTempPoint3);
                newPointsMap.put("Temp point 4", newTempPoint4);
                newPointsMap.put("End point", pointsMap.get("End point"));
                pointsMap = newPointsMap;
                bezier.order5(pointsMap);
                bezierOrder = 5;
                amountPoints.setText("Порядок кривой изменился с 4 на 5");
            }
        }
        else{
            System.out.println("Error!");
        }
    }

    public void downOrderButtonAction() {
       if ((!pointsMap.isEmpty()) && bezierOk) {
           Timeline timeline = bezier.getTimeline();
           if (timeline != null){
               timeline.stop();
           }
           if (bezierOrder == 2){
               double[][] matrixU = new double[2][pointsMap.size()];

               matrixU[0][0] = (int) pointsMap.get("Start point").getX();
               matrixU[0][1] = (int) pointsMap.get("Temp point 1").getX();
               matrixU[0][2] = (int) pointsMap.get("End point").getX();
               matrixU[1][0] = (int) pointsMap.get("Start point").getY();
               matrixU[1][1] = (int) pointsMap.get("Temp point 1").getY();
               matrixU[1][2] = (int) pointsMap.get("End point").getY();

               System.out.println();
               System.out.println("Матрица U");
               for (int i = 0; i < matrixU.length; i++) {
                   for (int j = 0; j < matrixU[0].length; j++) {
                       System.out.format("%6f ", matrixU[i][j]);
                   }
                   System.out.println();
               }
               System.out.println();

               System.out.println("Матрица H2");
               for (int i = 0; i < matrixH2.length; i++) {
                   for (int j = 0; j < matrixH2[0].length; j++) {
                       System.out.format("%6f ", matrixH2[i][j]);
                   }
                   System.out.println();
               }
               System.out.println();

               double[][] matrixRes = new double[matrixU.length][matrixH2[0].length];
               for (int i = 0; i < matrixU.length; i++) {
                   for (int j = 0; j < matrixH2[0].length; j++) {
                       for (int k = 0; k < matrixH2.length; k++) {
                           matrixRes[i][j] += matrixU[i][k] * matrixH2[k][j];
                       }
                   }
               }

               System.out.println("Матрица S2=U*H2");
               for (int i = 0; i < matrixRes.length; i++) {
                   for (int j = 0; j < matrixRes[0].length; j++) {
                       System.out.format("%6f ", matrixRes[i][j]);
                   }
                   System.out.println();
               }
               System.out.println();

               int d = 0;
               for (int i = matrixRes[0].length - 1; i >= 0 ; i--) {
                   if ((((matrixRes[0][i] <= 30)) && (matrixRes[0][i] >= -30)) && ((matrixRes[1][i] <= 30) && (matrixRes[1][i] >= -30))){
                       d++;
                   }
                   else{
                       break;
                   }
               }
               System.out.println("Дефект степени d=" + d);
               int newBezierOrder = bezierOrder - d;
               if (d != 0) {
                   System.out.println("Порядок кривой можно уменьшить до n-d=" + (bezierOrder - d) + "\n");

                   double[][] matrixL = new double[matrixH2.length][matrixH2[0].length - d];
                   for (int i = 0; i < matrixL.length; i++) {
                       for (int j = 0; j < matrixL[0].length; j++) {
                           matrixL[i][j] = matrixH2[i][j];
                       }
                   }

                   System.out.println("Матрица L2" + newBezierOrder);
                   for (int i = 0; i < matrixL.length; i++) {
                       for (int j = 0; j < matrixL[0].length; j++) {
                           System.out.format("%6f ", matrixL[i][j]);
                       }
                       System.out.println();
                   }
                   System.out.println();

                   double[][] tempMatrixH = new double[1][1];

                   switch (bezierOrder - d) {
                       case 1:
                           tempMatrixH = Arrays.copyOf(backMatrixH1, backMatrixH1.length);
                           break;
                       case 2:
                           tempMatrixH = Arrays.copyOf(backMatrixH2, backMatrixH2.length);
                           break;
                       case 3:
                           tempMatrixH = Arrays.copyOf(backMatrixH3, backMatrixH3.length);
                           break;
                       case 4:
                           tempMatrixH = Arrays.copyOf(backMatrixH4, backMatrixH4.length);
                           break;
                       case 5:
                           tempMatrixH = Arrays.copyOf(backMatrixH5, backMatrixH5.length);
                           break;
                   }

                   double[][] matrixLH = new double[matrixL.length][matrixL[0].length];
                   for (int i = 0; i < matrixL.length; i++) {
                       for (int j = 0; j < tempMatrixH[0].length; j++) {
                           for (int k = 0; k < tempMatrixH.length; k++) {
                               matrixLH[i][j] += matrixL[i][k] * tempMatrixH[k][j];
                           }
                       }
                   }

                   System.out.println("Матрица L2" + newBezierOrder + "H" + newBezierOrder);
                   for (int i = 0; i < matrixLH.length; i++) {
                       for (int j = 0; j < matrixLH[0].length; j++) {
                           System.out.format("%6f ", matrixLH[i][j]);
                       }
                       System.out.println();
                   }

                   HashMap<String, Point> newPointsMap = new HashMap<>();
                   if (newBezierOrder == 1) {
                       newPointsMap.put("Start point", pointsMap.get("Start point"));
                       newPointsMap.put("End point", pointsMap.get("End point"));
                       pointsMap = newPointsMap;
                       bezier.order1(pointsMap);
                       bezierOrder = 1;
                       amountPoints.setText("Порядок кривой изменился с 2 на 1");
                   }
               } else {
                   System.out.println("Изменить порядок кривой нельзя");
                   amountPoints.setText("Изменить порядок кривой нельзя");
               }
           }
           else if (bezierOrder == 3) {
               double[][] matrixU = new double[2][pointsMap.size()];

               matrixU[0][0] = (int) pointsMap.get("Start point").getX();
               matrixU[0][1] = (int) pointsMap.get("Temp point 1").getX();
               matrixU[0][2] = (int) pointsMap.get("Temp point 2").getX();
               matrixU[0][3] = (int) pointsMap.get("End point").getX();
               matrixU[1][0] = (int) pointsMap.get("Start point").getY();
               matrixU[1][1] = (int) pointsMap.get("Temp point 1").getY();
               matrixU[1][2] = (int) pointsMap.get("Temp point 2").getY();
               matrixU[1][3] = (int) pointsMap.get("End point").getY();

               System.out.println();
               System.out.println("Матрица U");
               for (int i = 0; i < matrixU.length; i++) {
                   for (int j = 0; j < matrixU[0].length; j++) {
                       System.out.format("%6f ", matrixU[i][j]);
                   }
                   System.out.println();
               }
               System.out.println();

               System.out.println("Матрица H3");
               for (int i = 0; i < matrixH3.length; i++) {
                   for (int j = 0; j < matrixH3[0].length; j++) {
                       System.out.format("%6f ", matrixH3[i][j]);
                   }
                   System.out.println();
               }
               System.out.println();

               double[][] matrixRes = new double[matrixU.length][matrixH3[0].length];
               for (int i = 0; i < matrixU.length; i++) {
                   for (int j = 0; j < matrixH3[0].length; j++) {
                       for (int k = 0; k < matrixH3.length; k++) {
                           matrixRes[i][j] += matrixU[i][k] * matrixH3[k][j];
                       }
                   }
               }

               System.out.println("Матрица S3=U*H3");
               for (int i = 0; i < matrixRes.length; i++) {
                   for (int j = 0; j < matrixRes[0].length; j++) {
                       System.out.format("%6f ", matrixRes[i][j]);
                   }
                   System.out.println();
               }
               System.out.println();

               int d = 0;
               for (int i = matrixRes[0].length - 1; i >= 0 ; i--) {
                   if ((((matrixRes[0][i] <= 30)) && (matrixRes[0][i] >= -30)) && ((matrixRes[1][i] <= 30) && (matrixRes[1][i] >= -30))){
                       d++;
                   }
                   else{
                       break;
                   }
               }
               System.out.println("Дефект степени d=" + d);
               int newBezierOrder = bezierOrder - d;
               if (d != 0) {
                   System.out.println("Порядок кривой можно уменьшить до n-d=" + (bezierOrder - d) + "\n");

                   double[][] matrixL = new double[matrixH3.length][matrixH3[0].length - d];
                   for (int i = 0; i < matrixL.length; i++) {
                       for (int j = 0; j < matrixL[0].length; j++) {
                           matrixL[i][j] = matrixH3[i][j];
                       }
                   }

                   System.out.println("Матрица L3" + newBezierOrder);
                   for (int i = 0; i < matrixL.length; i++) {
                       for (int j = 0; j < matrixL[0].length; j++) {
                           System.out.format("%6f ", matrixL[i][j]);
                       }
                       System.out.println();
                   }
                   System.out.println();

                   double[][] tempMatrixH = new double[1][1];

                   switch (bezierOrder - d) {
                       case 1:
                           tempMatrixH = Arrays.copyOf(backMatrixH1, backMatrixH1.length);
                           break;
                       case 2:
                           tempMatrixH = Arrays.copyOf(backMatrixH2, backMatrixH2.length);
                           break;
                       case 3:
                           tempMatrixH = Arrays.copyOf(backMatrixH3, backMatrixH3.length);
                           break;
                       case 4:
                           tempMatrixH = Arrays.copyOf(backMatrixH4, backMatrixH4.length);
                           break;
                       case 5:
                           tempMatrixH = Arrays.copyOf(backMatrixH5, backMatrixH5.length);
                           break;
                   }

                   double[][] matrixLH = new double[matrixL.length][matrixL[0].length];
                   for (int i = 0; i < matrixL.length; i++) {
                       for (int j = 0; j < tempMatrixH[0].length; j++) {
                           for (int k = 0; k < tempMatrixH.length; k++) {
                               matrixLH[i][j] += matrixL[i][k] * tempMatrixH[k][j];
                           }
                       }
                   }

                   System.out.println("Матрица L3" + newBezierOrder + "H" + newBezierOrder);
                   for (int i = 0; i < matrixLH.length; i++) {
                       for (int j = 0; j < matrixLH[0].length; j++) {
                           System.out.format("%6f ", matrixLH[i][j]);
                       }
                       System.out.println();
                   }

                   HashMap<String, Point> newPointsMap = new HashMap<>();
                   if (newBezierOrder == 1) {
                       newPointsMap.put("Start point", pointsMap.get("Start point"));
                       newPointsMap.put("End point", pointsMap.get("End point"));
                       pointsMap = newPointsMap;
                       bezier.order1(pointsMap);
                       bezierOrder = 1;
                       amountPoints.setText("Порядок кривой изменился с 3 на 1");
                   }
                   if (newBezierOrder == 2) {
                       double x = 0;
                       double y = 0;
                       if (matrixLH[0][1] != 0.0) {
                           x += matrixLH[0][1] * pointsMap.get("Start point").getX();
                           y += matrixLH[0][1] * pointsMap.get("Start point").getY();
                       }
                       if (matrixLH[1][1] != 0.0) {
                           x += matrixLH[1][1] * pointsMap.get("Temp point 1").getX();
                           y += matrixLH[1][1] * pointsMap.get("Temp point 1").getY();
                       }
                       if (matrixLH[2][1] != 0.0) {
                           x += matrixLH[2][1] * pointsMap.get("Temp point 2").getX();
                           y += matrixLH[2][1] * pointsMap.get("Temp point 2").getY();
                       }
                       if (matrixLH[3][1] != 0.0) {
                           x += matrixLH[3][1] * pointsMap.get("End point").getX();
                           y += matrixLH[3][1] * pointsMap.get("End point").getY();
                       }
                       Point newTempPoint1 = new Point(x, y);
                       newPointsMap.put("Start point", pointsMap.get("Start point"));
                       newPointsMap.put("Temp point 1", newTempPoint1);
                       newPointsMap.put("End point", pointsMap.get("End point"));
                       pointsMap = newPointsMap;
                       bezier.order2(pointsMap);
                       bezierOrder = 2;
                       amountPoints.setText("Порядок кривой изменился с 3 на 2");
                   }
               } else {
                   System.out.println("Изменить порядок кривой нельзя");
                   amountPoints.setText("Изменить порядок кривой нельзя");
               }
           }
           else if (bezierOrder == 4) {
                   double[][] matrixU = new double[2][pointsMap.size()];

                   matrixU[0][0] = (int) pointsMap.get("Start point").getX();
                   matrixU[0][1] = (int) pointsMap.get("Temp point 1").getX();
                   matrixU[0][2] = (int) pointsMap.get("Temp point 2").getX();
                   matrixU[0][3] = (int) pointsMap.get("Temp point 3").getX();
                   matrixU[0][4] = (int) pointsMap.get("End point").getX();
                   matrixU[1][0] = (int) pointsMap.get("Start point").getY();
                   matrixU[1][1] = (int) pointsMap.get("Temp point 1").getY();
                   matrixU[1][2] = (int) pointsMap.get("Temp point 2").getY();
                   matrixU[1][3] = (int) pointsMap.get("Temp point 3").getY();
                   matrixU[1][4] = (int) pointsMap.get("End point").getY();

                   System.out.println();
                   System.out.println("Матрица U");
                   for (int i = 0; i < matrixU.length; i++) {
                       for (int j = 0; j < matrixU[0].length; j++) {
                           System.out.format("%6f ", matrixU[i][j]);
                       }
                       System.out.println();
                   }
                   System.out.println();

                   System.out.println("Матрица H4");
                   for (int i = 0; i < matrixH4.length; i++) {
                       for (int j = 0; j < matrixH4[0].length; j++) {
                           System.out.format("%6f ", matrixH4[i][j]);
                       }
                       System.out.println();
                   }
                   System.out.println();

                   double[][] matrixRes = new double[matrixU.length][matrixH4[0].length];
                   for (int i = 0; i < matrixU.length; i++) {
                       for (int j = 0; j < matrixH4[0].length; j++) {
                           for (int k = 0; k < matrixH4.length; k++) {
                               matrixRes[i][j] += matrixU[i][k] * matrixH4[k][j];
                           }
                       }
                   }

                   System.out.println("Матрица S4=U*H4");
                   for (int i = 0; i < matrixRes.length; i++) {
                       for (int j = 0; j < matrixRes[0].length; j++) {
                           System.out.format("%6f ", matrixRes[i][j]);
                       }
                       System.out.println();
                   }
                   System.out.println();

                   int d = 0;
                   for (int i = matrixRes[0].length - 1; i >= 0 ; i--) {
                       if ((((matrixRes[0][i] <= 30)) && (matrixRes[0][i] >= -30)) && ((matrixRes[1][i] <= 30) && (matrixRes[1][i] >= -30))){
                           d++;
                       }
                       else{
                           break;
                       }
                   }
                   System.out.println("Дефект степени d=" + d);
                   int newBezierOrder = bezierOrder - d;
                   if (d != 0) {
                       System.out.println("Порядок кривой можно уменьшить до n-d=" + (bezierOrder - d) + "\n");

                       double[][] matrixL = new double[matrixH4.length][matrixH4[0].length - d];
                       for (int i = 0; i < matrixL.length; i++) {
                           for (int j = 0; j < matrixL[0].length; j++) {
                               matrixL[i][j] = matrixH4[i][j];
                           }
                       }

                       System.out.println("Матрица L42");
                       for (int i = 0; i < matrixL.length; i++) {
                           for (int j = 0; j < matrixL[0].length; j++) {
                               System.out.format("%6f ", matrixL[i][j]);
                           }
                           System.out.println();
                       }
                       System.out.println();

                       double[][] tempMatrixH = new double[1][1];

                       switch (bezierOrder - d) {
                           case 1:
                               tempMatrixH = Arrays.copyOf(backMatrixH1, backMatrixH1.length);
                               break;
                           case 2:
                               tempMatrixH = Arrays.copyOf(backMatrixH2, backMatrixH2.length);
                               break;
                           case 3:
                               tempMatrixH = Arrays.copyOf(backMatrixH3, backMatrixH3.length);
                               break;
                           case 4:
                               tempMatrixH = Arrays.copyOf(backMatrixH4, backMatrixH4.length);
                               break;
                           case 5:
                               tempMatrixH = Arrays.copyOf(backMatrixH5, backMatrixH5.length);
                               break;
                       }

                       double[][] matrixLH = new double[matrixL.length][matrixL[0].length];
                       for (int i = 0; i < matrixL.length; i++) {
                           for (int j = 0; j < tempMatrixH[0].length; j++) {
                               for (int k = 0; k < tempMatrixH.length; k++) {
                                   matrixLH[i][j] += matrixL[i][k] * tempMatrixH[k][j];
                               }
                           }
                       }

                       System.out.println("Матрица L42H2");
                       for (int i = 0; i < matrixLH.length; i++) {
                           for (int j = 0; j < matrixLH[0].length; j++) {
                               System.out.format("%6f ", matrixLH[i][j]);
                           }
                           System.out.println();
                       }

                       HashMap<String, Point> newPointsMap = new HashMap<>();
                       if (newBezierOrder == 1) {
                           newPointsMap.put("Start point", pointsMap.get("Start point"));
                           newPointsMap.put("End point", pointsMap.get("End point"));
                           pointsMap = newPointsMap;
                           bezier.order1(pointsMap);
                           bezierOrder = 1;
                           amountPoints.setText("Порядок кривой изменился с 4 на 1");
                       }
                       if (newBezierOrder == 2) {
                           double x = 0;
                           double y = 0;
                           if (matrixLH[0][1] != 0.0) {
                               x += matrixLH[0][1] * pointsMap.get("Start point").getX();
                               y += matrixLH[0][1] * pointsMap.get("Start point").getY();
                           }
                           if (matrixLH[1][1] != 0.0) {
                               x += matrixLH[1][1] * pointsMap.get("Temp point 1").getX();
                               y += matrixLH[1][1] * pointsMap.get("Temp point 1").getY();
                           }
                           if (matrixLH[2][1] != 0.0) {
                               x += matrixLH[2][1] * pointsMap.get("Temp point 2").getX();
                               y += matrixLH[2][1] * pointsMap.get("Temp point 2").getY();
                           }
                           if (matrixLH[3][1] != 0.0) {
                               x += matrixLH[3][1] * pointsMap.get("Temp point 3").getX();
                               y += matrixLH[3][1] * pointsMap.get("Temp point 3").getY();
                           }
                           if (matrixLH[4][1] != 0.0) {
                               x += matrixLH[4][1] * pointsMap.get("End point").getX();
                               y += matrixLH[4][1] * pointsMap.get("End point").getY();
                           }
                           Point newTempPoint1 = new Point(x, y);
                           newPointsMap.put("Start point", pointsMap.get("Start point"));
                           newPointsMap.put("Temp point 1", newTempPoint1);
                           newPointsMap.put("End point", pointsMap.get("End point"));
                           pointsMap = newPointsMap;
                           bezier.order2(pointsMap);
                           bezierOrder = 2;
                           amountPoints.setText("Порядок кривой изменился с 4 на 2");
                       }
                       if (newBezierOrder == 3) {
                           double x1 = 0;
                           double y1 = 0;
                           double x2 = 0;
                           double y2 = 0;
                           if (matrixLH[0][1] != 0.0) {
                               x1 += matrixLH[0][1] * pointsMap.get("Start point").getX();
                               y1 += matrixLH[0][1] * pointsMap.get("Start point").getY();
                           }
                           if (matrixLH[1][1] != 0.0) {
                               x1 += matrixLH[1][1] * pointsMap.get("Temp point 1").getX();
                               y1 += matrixLH[1][1] * pointsMap.get("Temp point 1").getY();
                           }
                           if (matrixLH[2][1] != 0.0) {
                               x1 += matrixLH[2][1] * pointsMap.get("Temp point 2").getX();
                               y1 += matrixLH[2][1] * pointsMap.get("Temp point 2").getY();
                           }
                           if (matrixLH[3][1] != 0.0) {
                               x1 += matrixLH[3][1] * pointsMap.get("Temp point 3").getX();
                               y1 += matrixLH[3][1] * pointsMap.get("Temp point 3").getY();
                           }
                           if (matrixLH[4][1] != 0.0) {
                               x1 += matrixLH[4][1] * pointsMap.get("End point").getX();
                               y1 += matrixLH[4][1] * pointsMap.get("End point").getY();
                           }

                           if (matrixLH[0][2] != 0.0) {
                               x2 += matrixLH[0][2] * pointsMap.get("Start point").getX();
                               y2 += matrixLH[0][2] * pointsMap.get("Start point").getY();
                           }
                           if (matrixLH[1][2] != 0.0) {
                               x2 += matrixLH[1][2] * pointsMap.get("Temp point 1").getX();
                               y2 += matrixLH[1][2] * pointsMap.get("Temp point 1").getY();
                           }
                           if (matrixLH[2][2] != 0.0) {
                               x2 += matrixLH[2][2] * pointsMap.get("Temp point 2").getX();
                               y2 += matrixLH[2][2] * pointsMap.get("Temp point 2").getY();
                           }
                           if (matrixLH[3][2] != 0.0) {
                               x2 += matrixLH[3][2] * pointsMap.get("Temp point 3").getX();
                               y2 += matrixLH[3][2] * pointsMap.get("Temp point 3").getY();
                           }
                           if (matrixLH[4][2] != 0.0) {
                               x2 += matrixLH[4][2] * pointsMap.get("End point").getX();
                               y2 += matrixLH[4][2] * pointsMap.get("End point").getY();
                           }
                           Point newTempPoint1 = new Point(x1, y1);
                           Point newTempPoint2 = new Point(x2, y2);
                           newPointsMap.put("Start point", pointsMap.get("Start point"));
                           newPointsMap.put("Temp point 1", newTempPoint1);
                           newPointsMap.put("Temp point 2", newTempPoint2);
                           newPointsMap.put("End point", pointsMap.get("End point"));
                           pointsMap = newPointsMap;
                           bezier.order3(pointsMap);
                           bezierOrder = 3;
                           amountPoints.setText("Порядок кривой изменился с 4 на 3");
                       }
                   } else {
                       System.out.println("Изменить порядок кривой нельзя");
                       amountPoints.setText("Изменить порядок кривой нельзя");
                   }
           }
           else if (bezierOrder == 5) {
               double[][] matrixU = new double[2][pointsMap.size()];

               matrixU[0][0] = (int) pointsMap.get("Start point").getX();
               matrixU[0][1] = (int) pointsMap.get("Temp point 1").getX();
               matrixU[0][2] = (int) pointsMap.get("Temp point 2").getX();
               matrixU[0][3] = (int) pointsMap.get("Temp point 3").getX();
               matrixU[0][4] = (int) pointsMap.get("Temp point 4").getX();
               matrixU[0][5] = (int) pointsMap.get("End point").getX();
               matrixU[1][0] = (int) pointsMap.get("Start point").getY();
               matrixU[1][1] = (int) pointsMap.get("Temp point 1").getY();
               matrixU[1][2] = (int) pointsMap.get("Temp point 2").getY();
               matrixU[1][3] = (int) pointsMap.get("Temp point 3").getY();
               matrixU[1][4] = (int) pointsMap.get("Temp point 4").getY();
               matrixU[1][5] = (int) pointsMap.get("End point").getY();

               System.out.println();
               System.out.println("Матрица U");
               for (int i = 0; i < matrixU.length; i++) {
                   for (int j = 0; j < matrixU[0].length; j++) {
                       System.out.format("%6f ", matrixU[i][j]);
                   }
                   System.out.println();
               }
               System.out.println();

               System.out.println("Матрица H5");
               for (int i = 0; i < matrixH5.length; i++) {
                   for (int j = 0; j < matrixH5[0].length; j++) {
                       System.out.format("%6f ", matrixH5[i][j]);
                   }
                   System.out.println();
               }
               System.out.println();

               double[][] matrixRes = new double[matrixU.length][matrixH5[0].length];
               for (int i = 0; i < matrixU.length; i++) {
                   for (int j = 0; j < matrixH5[0].length; j++) {
                       for (int k = 0; k < matrixH5.length; k++) {
                           matrixRes[i][j] += matrixU[i][k] * matrixH5[k][j];
                       }
                   }
               }

               System.out.println("Матрица S5=U*H5");
               for (int i = 0; i < matrixRes.length; i++) {
                   for (int j = 0; j < matrixRes[0].length; j++) {
                       System.out.format("%6f ", matrixRes[i][j]);
                   }
                   System.out.println();
               }
               System.out.println();

               int d = 0;
               for (int i = matrixRes[0].length - 1; i >= 0 ; i--) {
                   if ((((matrixRes[0][i] <= 30)) && (matrixRes[0][i] >= -30)) && ((matrixRes[1][i] <= 30) && (matrixRes[1][i] >= -30))){
                       d++;
                   }
                   else{
                       break;
                   }
               }
               System.out.println("Дефект степени d=" + d);
               int newBezierOrder = bezierOrder - d;
               if (d != 0) {
                   System.out.println("Порядок кривой можно уменьшить до n-d=" + (bezierOrder - d) + "\n");

                   double[][] matrixL = new double[matrixH5.length][matrixH5[0].length - d];
                   for (int i = 0; i < matrixL.length; i++) {
                       for (int j = 0; j < matrixL[0].length; j++) {
                           matrixL[i][j] = matrixH5[i][j];
                       }
                   }

                   System.out.println("Матрица L5" + newBezierOrder);
                   for (int i = 0; i < matrixL.length; i++) {
                       for (int j = 0; j < matrixL[0].length; j++) {
                           System.out.format("%6f ", matrixL[i][j]);
                       }
                       System.out.println();
                   }
                   System.out.println();

                   double[][] tempMatrixH = new double[1][1];

                   switch (bezierOrder - d) {
                       case 1:
                           tempMatrixH = Arrays.copyOf(backMatrixH1, backMatrixH1.length);
                           break;
                       case 2:
                           tempMatrixH = Arrays.copyOf(backMatrixH2, backMatrixH2.length);
                           break;
                       case 3:
                           tempMatrixH = Arrays.copyOf(backMatrixH3, backMatrixH3.length);
                           break;
                       case 4:
                           tempMatrixH = Arrays.copyOf(backMatrixH4, backMatrixH4.length);
                           break;
                       case 5:
                           tempMatrixH = Arrays.copyOf(backMatrixH5, backMatrixH5.length);
                           break;
                   }

                   double[][] matrixLH = new double[matrixL.length][matrixL[0].length];
                   for (int i = 0; i < matrixL.length; i++) {
                       for (int j = 0; j < tempMatrixH[0].length; j++) {
                           for (int k = 0; k < tempMatrixH.length; k++) {
                               matrixLH[i][j] += matrixL[i][k] * tempMatrixH[k][j];
                           }
                       }
                   }

                   System.out.println("Матрица L5" + newBezierOrder + "H" + newBezierOrder);
                   for (int i = 0; i < matrixLH.length; i++) {
                       for (int j = 0; j < matrixLH[0].length; j++) {
                           System.out.format("%6f ", matrixLH[i][j]);
                       }
                       System.out.println();
                   }

                   HashMap<String, Point> newPointsMap = new HashMap<>();
                   if (newBezierOrder == 1) {
                       newPointsMap.put("Start point", pointsMap.get("Start point"));
                       newPointsMap.put("End point", pointsMap.get("End point"));
                       pointsMap = newPointsMap;
                       bezier.order1(pointsMap);
                       bezierOrder = 1;
                       amountPoints.setText("Порядок кривой изменился с 5 на 1");
                   }
                   else if (newBezierOrder == 2) {
                       double x = 0;
                       double y = 0;
                       if (matrixLH[0][1] != 0.0) {
                           x += matrixLH[0][1] * pointsMap.get("Start point").getX();
                           y += matrixLH[0][1] * pointsMap.get("Start point").getY();
                       }
                       if (matrixLH[1][1] != 0.0) {
                           x += matrixLH[1][1] * pointsMap.get("Temp point 1").getX();
                           y += matrixLH[1][1] * pointsMap.get("Temp point 1").getY();
                       }
                       if (matrixLH[2][1] != 0.0) {
                           x += matrixLH[2][1] * pointsMap.get("Temp point 2").getX();
                           y += matrixLH[2][1] * pointsMap.get("Temp point 2").getY();
                       }
                       if (matrixLH[3][1] != 0.0) {
                           x += matrixLH[3][1] * pointsMap.get("Temp point 3").getX();
                           y += matrixLH[3][1] * pointsMap.get("Temp point 3").getY();
                       }
                       if (matrixLH[4][1] != 0.0) {
                           x += matrixLH[4][1] * pointsMap.get("Temp point 4").getX();
                           y += matrixLH[4][1] * pointsMap.get("Temp point 4").getY();
                       }
                       if (matrixLH[5][1] != 0.0) {
                           x += matrixLH[5][1] * pointsMap.get("End point").getX();
                           y += matrixLH[5][1] * pointsMap.get("End point").getY();
                       }
                       Point newTempPoint1 = new Point(x, y);
                       newPointsMap.put("Start point", pointsMap.get("Start point"));
                       newPointsMap.put("Temp point 1", newTempPoint1);
                       newPointsMap.put("End point", pointsMap.get("End point"));
                       pointsMap = newPointsMap;
                       bezier.order2(pointsMap);
                       bezierOrder = 2;
                       amountPoints.setText("Порядок кривой изменился с 5 на 2");
                   }
                   else if (newBezierOrder == 3) {
                       double x1 = 0;
                       double y1 = 0;
                       double x2 = 0;
                       double y2 = 0;
                       if (matrixLH[0][1] != 0.0) {
                           x1 += matrixLH[0][1] * pointsMap.get("Start point").getX();
                           y1 += matrixLH[0][1] * pointsMap.get("Start point").getY();
                       }
                       if (matrixLH[1][1] != 0.0) {
                           x1 += matrixLH[1][1] * pointsMap.get("Temp point 1").getX();
                           y1 += matrixLH[1][1] * pointsMap.get("Temp point 1").getY();
                       }
                       if (matrixLH[2][1] != 0.0) {
                           x1 += matrixLH[2][1] * pointsMap.get("Temp point 2").getX();
                           y1 += matrixLH[2][1] * pointsMap.get("Temp point 2").getY();
                       }
                       if (matrixLH[3][1] != 0.0) {
                           x1 += matrixLH[3][1] * pointsMap.get("Temp point 3").getX();
                           y1 += matrixLH[3][1] * pointsMap.get("Temp point 3").getY();
                       }
                       if (matrixLH[4][1] != 0.0) {
                           x1 += matrixLH[4][1] * pointsMap.get("Temp point 4").getX();
                           y1 += matrixLH[4][1] * pointsMap.get("Temp point 4").getY();
                       }
                       if (matrixLH[5][1] != 0.0) {
                           x1 += matrixLH[5][1] * pointsMap.get("End point").getX();
                           y1 += matrixLH[5][1] * pointsMap.get("End point").getY();
                       }

                       if (matrixLH[0][2] != 0.0) {
                           x2 += matrixLH[0][2] * pointsMap.get("Start point").getX();
                           y2 += matrixLH[0][2] * pointsMap.get("Start point").getY();
                       }
                       if (matrixLH[1][2] != 0.0) {
                           x2 += matrixLH[1][2] * pointsMap.get("Temp point 1").getX();
                           y2 += matrixLH[1][2] * pointsMap.get("Temp point 1").getY();
                       }
                       if (matrixLH[2][2] != 0.0) {
                           x2 += matrixLH[2][2] * pointsMap.get("Temp point 2").getX();
                           y2 += matrixLH[2][2] * pointsMap.get("Temp point 2").getY();
                       }
                       if (matrixLH[3][2] != 0.0) {
                           x2 += matrixLH[3][2] * pointsMap.get("Temp point 3").getX();
                           y2 += matrixLH[3][2] * pointsMap.get("Temp point 3").getY();
                       }
                       if (matrixLH[4][2] != 0.0) {
                           x2 += matrixLH[4][2] * pointsMap.get("Temp point 4").getX();
                           y2 += matrixLH[4][2] * pointsMap.get("Temp point 4").getY();
                       }
                       if (matrixLH[5][2] != 0.0) {
                           x2 += matrixLH[5][2] * pointsMap.get("End point").getX();
                           y2 += matrixLH[5][2] * pointsMap.get("End point").getY();
                       }
                       Point newTempPoint1 = new Point(x1, y1);
                       Point newTempPoint2 = new Point(x2, y2);
                       newPointsMap.put("Start point", pointsMap.get("Start point"));
                       newPointsMap.put("Temp point 1", newTempPoint1);
                       newPointsMap.put("Temp point 2", newTempPoint2);
                       newPointsMap.put("End point", pointsMap.get("End point"));
                       pointsMap = newPointsMap;
                       bezier.order3(pointsMap);
                       bezierOrder = 3;
                       amountPoints.setText("Порядок кривой изменился с 5 на 3");
                   }
                   else if (newBezierOrder == 4) {
                       double x1 = 0;
                       double y1 = 0;
                       double x2 = 0;
                       double y2 = 0;
                       double x3 = 0;
                       double y3 = 0;
                       if (matrixLH[0][1] != 0.0) {
                           x1 += matrixLH[0][1] * pointsMap.get("Start point").getX();
                           y1 += matrixLH[0][1] * pointsMap.get("Start point").getY();
                       }
                       if (matrixLH[1][1] != 0.0) {
                           x1 += matrixLH[1][1] * pointsMap.get("Temp point 1").getX();
                           y1 += matrixLH[1][1] * pointsMap.get("Temp point 1").getY();
                       }
                       if (matrixLH[2][1] != 0.0) {
                           x1 += matrixLH[2][1] * pointsMap.get("Temp point 2").getX();
                           y1 += matrixLH[2][1] * pointsMap.get("Temp point 2").getY();
                       }
                       if (matrixLH[3][1] != 0.0) {
                           x1 += matrixLH[3][1] * pointsMap.get("Temp point 3").getX();
                           y1 += matrixLH[3][1] * pointsMap.get("Temp point 3").getY();
                       }
                       if (matrixLH[4][1] != 0.0) {
                           x1 += matrixLH[4][1] * pointsMap.get("Temp point 4").getX();
                           y1 += matrixLH[4][1] * pointsMap.get("Temp point 4").getY();
                       }
                       if (matrixLH[5][1] != 0.0) {
                           x1 += matrixLH[5][1] * pointsMap.get("End point").getX();
                           y1 += matrixLH[5][1] * pointsMap.get("End point").getY();
                       }

                       if (matrixLH[0][2] != 0.0) {
                           x2 += matrixLH[0][2] * pointsMap.get("Start point").getX();
                           y2 += matrixLH[0][2] * pointsMap.get("Start point").getY();
                       }
                       if (matrixLH[1][2] != 0.0) {
                           x2 += matrixLH[1][2] * pointsMap.get("Temp point 1").getX();
                           y2 += matrixLH[1][2] * pointsMap.get("Temp point 1").getY();
                       }
                       if (matrixLH[2][2] != 0.0) {
                           x2 += matrixLH[2][2] * pointsMap.get("Temp point 2").getX();
                           y2 += matrixLH[2][2] * pointsMap.get("Temp point 2").getY();
                       }
                       if (matrixLH[3][2] != 0.0) {
                           x2 += matrixLH[3][2] * pointsMap.get("Temp point 3").getX();
                           y2 += matrixLH[3][2] * pointsMap.get("Temp point 3").getY();
                       }
                       if (matrixLH[4][2] != 0.0) {
                           x2 += matrixLH[4][2] * pointsMap.get("Temp point 4").getX();
                           y2 += matrixLH[4][2] * pointsMap.get("Temp point 4").getY();
                       }
                       if (matrixLH[5][2] != 0.0) {
                           x2 += matrixLH[5][2] * pointsMap.get("End point").getX();
                           y2 += matrixLH[5][2] * pointsMap.get("End point").getY();
                       }

                       if (matrixLH[0][3] != 0.0) {
                           x3 += matrixLH[0][3] * pointsMap.get("Start point").getX();
                           y3 += matrixLH[0][3] * pointsMap.get("Start point").getY();
                       }
                       if (matrixLH[1][3] != 0.0) {
                           x3 += matrixLH[1][3] * pointsMap.get("Temp point 1").getX();
                           y3 += matrixLH[1][3] * pointsMap.get("Temp point 1").getY();
                       }
                       if (matrixLH[2][3] != 0.0) {
                           x3 += matrixLH[2][3] * pointsMap.get("Temp point 2").getX();
                           y3 += matrixLH[2][3] * pointsMap.get("Temp point 2").getY();
                       }
                       if (matrixLH[3][3] != 0.0) {
                           x3 += matrixLH[3][3] * pointsMap.get("Temp point 3").getX();
                           y3 += matrixLH[3][3] * pointsMap.get("Temp point 3").getY();
                       }
                       if (matrixLH[4][3] != 0.0) {
                           x3 += matrixLH[4][3] * pointsMap.get("Temp point 4").getX();
                           y3 += matrixLH[4][3] * pointsMap.get("Temp point 4").getY();
                       }
                       if (matrixLH[5][3] != 0.0) {
                           x3 += matrixLH[5][3] * pointsMap.get("End point").getX();
                           y3 += matrixLH[5][3] * pointsMap.get("End point").getY();
                       }
                       Point newTempPoint1 = new Point(x1, y1);
                       Point newTempPoint2 = new Point(x2, y2);
                       Point newTempPoint3 = new Point(x3, y3);
                       newPointsMap.put("Start point", pointsMap.get("Start point"));
                       newPointsMap.put("Temp point 1", newTempPoint1);
                       newPointsMap.put("Temp point 2", newTempPoint2);
                       newPointsMap.put("Temp point 3", newTempPoint3);
                       newPointsMap.put("End point", pointsMap.get("End point"));
                       pointsMap = newPointsMap;
                       bezier.order4(pointsMap);
                       bezierOrder = 4;
                       amountPoints.setText("Порядок кривой изменился с 5 на 4");
                   }
               } else {
                   System.out.println("Изменить порядок кривой нельзя");
                   amountPoints.setText("Изменить порядок кривой нельзя");
               }
           }
           else {
               System.out.println("Error!");
           }
       }
    }

    public void resetButtonAction(){
        startPointLabel.setText("Начальная точка");
        endPointLabel.setText("Конечная точка");
        tempPoint1Label.setText("Первая промежуточная точка");
        tempPoint2Label.setText("Вторая промежуточная точка");
        tempPoint3Label.setText("Третья промежуточная точка");
        tempPoint4Label.setText("Четвертая промежуточная точка");
        amountPoints.setText("Выбрано точек: " + 0 + "/" + bezierOrder);
        pointsMap.clear();
        tempPoint = null;
        bezierOk = false;
        Timeline timeline = bezier.getTimeline();
        if (timeline != null){
            timeline.stop();
        }
        clearCanvas();
    }

    public void stopButtonAction(){
        Timeline timeline = bezier.getTimeline();
        if (timeline != null){
            timeline.stop();
        }
        clearCanvas();
    }


    public void clearCanvas(){
        graphicsContext.clearRect(0, 0, 650, 625);
    }

    public static GraphicsContext getGraphicsContext(){
        return graphicsContext;
    }

    public static int getSpeed() {
        return speed;
    }
}
