package main.java;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

public class Bezier {
    private int bezierOrder;
    private HashMap<String, Point> pointsMap;
    private static GraphicsContext graphicsContext;
    private Timeline timeline;
    private static int speed;

    static {
        graphicsContext = MainScene.getGraphicsContext();
    }

    public void order1(HashMap<String, Point> pointsMap){
        if (pointsMap.containsKey("Start point") && pointsMap.containsKey("End point")){
            speed = MainScene.getSpeed();
            this.bezierOrder = 1;
            this.pointsMap = pointsMap;
            Line finalLine = new Line(pointsMap.get("Start point"), pointsMap.get("End point"));

            ArrayList<Point> bezier1PointsList = new ArrayList<>();
            for (double t = 0.00; t <= 1.001; t += 0.01){
                Point pointFromFinalLine = finalLine.pointByT(t);
                bezier1PointsList.add(pointFromFinalLine);
            }

            ArrayList<Line> bezier1LinesList = new ArrayList<>();
            for (int i = 1; i <= bezier1PointsList.size() - 1; i++){
                bezier1LinesList.add(new Line(bezier1PointsList.get(i - 1), bezier1PointsList.get(i)));
            }

            timeline = new Timeline(new KeyFrame(Duration.millis(speed),
                    new EventHandler<ActionEvent>() {
                        private int i = 0;

                        @Override
                        public void handle(ActionEvent t) {
                            graphicsContext.clearRect(0, 0, 650, 625);
                            for (int j = 0; j <= i - 1; j++){
                                bezier1LinesList.get(j).paint(Color.RED);
                            }
                            i++;
                        }
                    }));
            timeline.setCycleCount(101);
            timeline.play();
        }
        else{
            System.out.println("Error!");
        }
    }

    public void order2(HashMap<String, Point> pointsMap){
        if (pointsMap.containsKey("Start point") && pointsMap.containsKey("End point") && pointsMap.containsKey("Temp point 1")){
            speed = MainScene.getSpeed();
            this.bezierOrder = 2;
            this.pointsMap = pointsMap;
            Line line1 = new Line(pointsMap.get("Start point"), pointsMap.get("Temp point 1"));
            Line line2 = new Line(pointsMap.get("Temp point 1"), pointsMap.get("End point"));

            ArrayList<Point> bezier2PointsList = new ArrayList<>();
            ArrayList<Line>  finalLinesList = new ArrayList<>();
            for (double t = 0.00; t <= 1.001; t += 0.01){
                Point pointFromLine1 = line1.pointByT(t);
                Point pointFromLine2 = line2.pointByT(t);
                Line finalLine = new Line(pointFromLine1,pointFromLine2);
                finalLinesList.add(finalLine);

                Point pointFromFinalLine = finalLine.pointByT(t);
                bezier2PointsList.add(pointFromFinalLine);
            }

            ArrayList<Line> bezier2LinesList = new ArrayList<>();
            for (int i = 1; i <= bezier2PointsList.size() - 1; i++){
                bezier2LinesList.add(new Line(bezier2PointsList.get(i - 1), bezier2PointsList.get(i)));
            }

            timeline = new Timeline(new KeyFrame(Duration.millis(speed),
                    new EventHandler<ActionEvent>() {
                        private int i = 0;

                        @Override
                        public void handle(ActionEvent t) {
                            graphicsContext.clearRect(0, 0, 650, 625);
                            line1.paint(Color.GRAY);
                            line2.paint(Color.GRAY);
                            finalLinesList.get(i).paint(Color.GREEN);
                            for (int j = 0; j <= i - 1; j++){
                                bezier2LinesList.get(j).paint(Color.RED);
                            }
                            i++;
                        }
                    }));
            timeline.setCycleCount(101);
            timeline.play();
        }
        else{
            System.out.println("Error!");
        }
    }

    public void order3(HashMap<String, Point> pointsMap){
        if (pointsMap.containsKey("Start point") && pointsMap.containsKey("End point")
                && pointsMap.containsKey("Temp point 1") && pointsMap.containsKey("Temp point 2")){
            speed = MainScene.getSpeed();
            this.bezierOrder = 3;
            this.pointsMap = pointsMap;
            Line line1 = new Line(pointsMap.get("Start point"), pointsMap.get("Temp point 1"));
            Line line2 = new Line(pointsMap.get("Temp point 1"), pointsMap.get("Temp point 2"));
            Line line3 = new Line(pointsMap.get("Temp point 2"), pointsMap.get("End point"));

            ArrayList<Point> bezier3PointsList = new ArrayList<>();
            ArrayList<Line> tempLines1List = new ArrayList<>();
            ArrayList<Line> tempLines2List = new ArrayList<>();
            ArrayList<Line> finalLinesList = new ArrayList<>();
            for (double t = 0.00; t <= 1.001; t += 0.01){
                Point pointFromLine1 = line1.pointByT(t);
                Point pointFromLine2 = line2.pointByT(t);
                Point pointFromLine3 = line3.pointByT(t);
                Line tempLine1 = new Line(pointFromLine1, pointFromLine2);
                tempLines1List.add(tempLine1);
                Line tempLine2 = new Line(pointFromLine2, pointFromLine3);
                tempLines2List.add(tempLine2);

                Point pointFromTempLine1 = tempLine1.pointByT(t);
                Point pointFromTempLine2 = tempLine2.pointByT(t);
                Line finalLine = new Line(pointFromTempLine1,pointFromTempLine2);
                finalLinesList.add(finalLine);

                Point pointFromFinalLine = finalLine.pointByT(t);
                bezier3PointsList.add(pointFromFinalLine);
            }

            ArrayList<Line> bezier3LinesList = new ArrayList<>();
            for (int i = 1; i <= bezier3PointsList.size() - 1; i++){
                bezier3LinesList.add(new Line(bezier3PointsList.get(i - 1), bezier3PointsList.get(i)));
            }

            timeline = new Timeline(new KeyFrame(Duration.millis(speed),
                    new EventHandler<ActionEvent>() {
                        private int i = 0;

                        @Override
                        public void handle(ActionEvent t) {
                            graphicsContext.clearRect(0, 0, 650, 625);
                            line1.paint(Color.GRAY);
                            line2.paint(Color.GRAY);
                            line3.paint(Color.GRAY);
                            tempLines1List.get(i).paint(Color.GREEN);
                            tempLines2List.get(i).paint(Color.GREEN);
                            finalLinesList.get(i).paint(Color.BLUE);
                            for (int j = 0; j <= i - 1; j++){
                                bezier3LinesList.get(j).paint(Color.RED);
                            }
                            i++;
                        }
                    }));
            timeline.setCycleCount(101);
            timeline.play();
        }
        else{
            System.out.println("Error!");
        }
    }

    public void order4(HashMap<String, Point> pointsMap){
        if (pointsMap.containsKey("Start point") && pointsMap.containsKey("End point") && pointsMap.containsKey("Temp point 1")
                && pointsMap.containsKey("Temp point 2") && pointsMap.containsKey("Temp point 3")) {
            speed = MainScene.getSpeed();
            this.bezierOrder = 4;
            this.pointsMap = pointsMap;
            Line line1 = new Line(pointsMap.get("Start point"), pointsMap.get("Temp point 1"));
            Line line2 = new Line(pointsMap.get("Temp point 1"), pointsMap.get("Temp point 2"));
            Line line3 = new Line(pointsMap.get("Temp point 2"), pointsMap.get("Temp point 3"));
            Line line4 = new Line(pointsMap.get("Temp point 3"), pointsMap.get("End point"));

            ArrayList<Point> bezier4PointsList = new ArrayList<>();
            ArrayList<Line> temp1Lines1List = new ArrayList<>();
            ArrayList<Line> temp1Lines2List = new ArrayList<>();
            ArrayList<Line> temp1Lines3List = new ArrayList<>();
            ArrayList<Line> temp2Lines1List = new ArrayList<>();
            ArrayList<Line> temp2Lines2List = new ArrayList<>();
            ArrayList<Line> finalLinesList = new ArrayList<>();
            for (double t = 0.00; t <= 1.001; t += 0.01){
                Point pointFromLine1 = line1.pointByT(t);
                Point pointFromLine2 = line2.pointByT(t);
                Point pointFromLine3 = line3.pointByT(t);
                Point pointFromLine4 = line4.pointByT(t);
                Line temp1Line1 = new Line(pointFromLine1, pointFromLine2);
                temp1Lines1List.add(temp1Line1);
                Line temp1Line2 = new Line(pointFromLine2, pointFromLine3);
                temp1Lines2List.add(temp1Line2);
                Line temp1Line3 = new Line(pointFromLine3, pointFromLine4);
                temp1Lines3List.add(temp1Line3);

                Point pointFromTemp1Line1 = temp1Line1.pointByT(t);
                Point pointFromTemp1Line2 = temp1Line2.pointByT(t);
                Point pointFromTemp1Line3 = temp1Line3.pointByT(t);
                Line temp2Line1 = new Line(pointFromTemp1Line1, pointFromTemp1Line2);
                temp2Lines1List.add(temp2Line1);
                Line temp2Line2 = new Line(pointFromTemp1Line2, pointFromTemp1Line3);
                temp2Lines2List.add(temp2Line2);

                Point pointFromTemp2Line1 = temp2Line1.pointByT(t);
                Point pointFromTemp2Line2 = temp2Line2.pointByT(t);
                Line finalLine = new Line(pointFromTemp2Line1,pointFromTemp2Line2);
                finalLinesList.add(finalLine);

                Point pointFromFinalLine = finalLine.pointByT(t);
                bezier4PointsList.add(pointFromFinalLine);
            }

            ArrayList<Line> bezier4LinesList = new ArrayList<>();
            for (int i = 1; i <= bezier4PointsList.size() - 1; i++){
                bezier4LinesList.add(new Line(bezier4PointsList.get(i - 1), bezier4PointsList.get(i)));
            }

            timeline = new Timeline(new KeyFrame(Duration.millis(speed),
                    new EventHandler<ActionEvent>() {
                        private int i = 0;

                        @Override
                        public void handle(ActionEvent t) {
                            graphicsContext.clearRect(0, 0, 650, 625);
                            line1.paint(Color.GRAY);
                            line2.paint(Color.GRAY);
                            line3.paint(Color.GRAY);
                            line4.paint(Color.GRAY);
                            temp1Lines1List.get(i).paint(Color.GREEN);
                            temp1Lines2List.get(i).paint(Color.GREEN);
                            temp1Lines3List.get(i).paint(Color.GREEN);
                            temp2Lines1List.get(i).paint(Color.BLUE);
                            temp2Lines2List.get(i).paint(Color.BLUE);
                            finalLinesList.get(i).paint(Color.HOTPINK);
                            for (int j = 0; j <= i - 1; j++){
                                bezier4LinesList.get(j).paint(Color.RED);
                            }
                            i++;
                        }
                    }));
            timeline.setCycleCount(101);
            timeline.play();
        }
        else {
            System.out.println("Error!");
        }
    }

    public void order5(HashMap<String, Point> pointsMap) {
        if (pointsMap.containsKey("Start point") && pointsMap.containsKey("End point") && pointsMap.containsKey("Temp point 1")
                && pointsMap.containsKey("Temp point 2") && pointsMap.containsKey("Temp point 3") && pointsMap.containsKey("Temp point 4")) {
            speed = MainScene.getSpeed();
            this.bezierOrder = 5;
            this.pointsMap = pointsMap;
            Line line1 = new Line(pointsMap.get("Start point"), pointsMap.get("Temp point 1"));
            Line line2 = new Line(pointsMap.get("Temp point 1"), pointsMap.get("Temp point 2"));
            Line line3 = new Line(pointsMap.get("Temp point 2"), pointsMap.get("Temp point 3"));
            Line line4 = new Line(pointsMap.get("Temp point 3"), pointsMap.get("Temp point 4"));
            Line line5 = new Line(pointsMap.get("Temp point 4"), pointsMap.get("End point"));

            ArrayList<Point> bezier5PointsList = new ArrayList<>();
            ArrayList<Line> temp1Lines1List = new ArrayList<>();
            ArrayList<Line> temp1Lines2List = new ArrayList<>();
            ArrayList<Line> temp1Lines3List = new ArrayList<>();
            ArrayList<Line> temp1Lines4List = new ArrayList<>();
            ArrayList<Line> temp2Lines1List = new ArrayList<>();
            ArrayList<Line> temp2Lines2List = new ArrayList<>();
            ArrayList<Line> temp2Lines3List = new ArrayList<>();
            ArrayList<Line> temp3Lines1List = new ArrayList<>();
            ArrayList<Line> temp3Lines2List = new ArrayList<>();
            ArrayList<Line> finalLinesList = new ArrayList<>();
            for (double t = 0.00; t <= 1.001; t += 0.01){
                Point pointFromLine1 = line1.pointByT(t);
                Point pointFromLine2 = line2.pointByT(t);
                Point pointFromLine3 = line3.pointByT(t);
                Point pointFromLine4 = line4.pointByT(t);
                Point pointFromLine5 = line5.pointByT(t);
                Line temp1Line1 = new Line(pointFromLine1, pointFromLine2);
                temp1Lines1List.add(temp1Line1);
                Line temp1Line2 = new Line(pointFromLine2, pointFromLine3);
                temp1Lines2List.add(temp1Line2);
                Line temp1Line3 = new Line(pointFromLine3, pointFromLine4);
                temp1Lines3List.add(temp1Line3);
                Line temp1Line4 = new Line(pointFromLine4, pointFromLine5);
                temp1Lines4List.add(temp1Line4);

                Point pointFromTemp1Line1 = temp1Line1.pointByT(t);
                Point pointFromTemp1Line2 = temp1Line2.pointByT(t);
                Point pointFromTemp1Line3 = temp1Line3.pointByT(t);
                Point pointFromTemp1Line4 = temp1Line4.pointByT(t);
                Line temp2Line1 = new Line(pointFromTemp1Line1, pointFromTemp1Line2);
                temp2Lines1List.add(temp2Line1);
                Line temp2Line2 = new Line(pointFromTemp1Line2, pointFromTemp1Line3);
                temp2Lines2List.add(temp2Line2);
                Line temp2Line3 = new Line(pointFromTemp1Line3, pointFromTemp1Line4);
                temp2Lines3List.add(temp2Line3);

                Point pointFromTemp2Line1 = temp2Line1.pointByT(t);
                Point pointFromTemp2Line2 = temp2Line2.pointByT(t);
                Point pointFromTemp2Line3 = temp2Line3.pointByT(t);
                Line temp3Line1 = new Line(pointFromTemp2Line1, pointFromTemp2Line2);
                temp3Lines1List.add(temp3Line1);
                Line temp3Line2 = new Line(pointFromTemp2Line2, pointFromTemp2Line3);
                temp3Lines2List.add(temp3Line2);

                Point pointFromTemp3Line1 = temp3Line1.pointByT(t);
                Point pointFromTemp3Line2 = temp3Line2.pointByT(t);
                Line finalLine = new Line(pointFromTemp3Line1,pointFromTemp3Line2);
                finalLinesList.add(finalLine);

                Point pointFromFinalLine = finalLine.pointByT(t);
                bezier5PointsList.add(pointFromFinalLine);
            }
            ArrayList<Line> bezier5LinesList = new ArrayList<>();
            for (int i = 1; i <= bezier5PointsList.size() - 1; i++){
                bezier5LinesList.add(new Line(bezier5PointsList.get(i - 1), bezier5PointsList.get(i)));
            }

            timeline = new Timeline(new KeyFrame(Duration.millis(speed),
                    new EventHandler<ActionEvent>() {
                        private int i = 0;

                        @Override
                        public void handle(ActionEvent t) {
                            graphicsContext.clearRect(0, 0, 650, 625);
                            line1.paint(Color.GRAY);
                            line2.paint(Color.GRAY);
                            line3.paint(Color.GRAY);
                            line4.paint(Color.GRAY);
                            line5.paint(Color.GRAY);
                            temp1Lines1List.get(i).paint(Color.GREEN);
                            temp1Lines2List.get(i).paint(Color.GREEN);
                            temp1Lines3List.get(i).paint(Color.GREEN);
                            temp1Lines4List.get(i).paint(Color.GREEN);
                            temp2Lines1List.get(i).paint(Color.BLUE);
                            temp2Lines2List.get(i).paint(Color.BLUE);
                            temp2Lines3List.get(i).paint(Color.BLUE);
                            temp3Lines1List.get(i).paint(Color.HOTPINK);
                            temp3Lines2List.get(i).paint(Color.HOTPINK);
                            finalLinesList.get(i).paint(Color.ORANGE);
                            for (int j = 0; j <= i - 1; j++){
                                bezier5LinesList.get(j).paint(Color.RED);
                            }
                            i++;
                        }
                    }));
            timeline.setCycleCount(101);
            timeline.play();
        }
        else {
            System.out.println("Error!");
        }
    }

    public Timeline getTimeline() {
        return timeline;
    }
}
