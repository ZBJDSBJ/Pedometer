package com.alost.pedometer;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class generatePedometerDataBase {
    public static Entity PedometerCard;
    public static Entity calenderCard;


    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(1, "com.lost.zou.pedometer.data.model.database.core");
        schema.enableKeepSectionsByDefault();

        addPedometerCard(schema);
        addCalendarCard(schema);


        new DaoGenerator().generateAll(schema, "./workpalce/Pedometer/greendaolib/src/main/java/");

    }

    //计步
    private static void addPedometerCard(Schema schema) {
        PedometerCard = schema.addEntity("PedometerCardEntity");
        PedometerCard.setTableName("PedometerCardLog");
        //默认的id自主增加
        PedometerCard.addIdProperty().autoincrement();
        //消耗的卡路里
        PedometerCard.addDoubleProperty("calories");
        //日期，并且是唯一的
        PedometerCard.addStringProperty("date").notNull().unique();
        //步数
        PedometerCard.addIntProperty("stepCount");
        //步行的距离
        PedometerCard.addDoubleProperty("distanceInMeters");
        //完成打卡后的描述语句
        PedometerCard.addStringProperty("description");

        //打卡完成后的目标的步数
        PedometerCard.addIntProperty("targetStepCount");

        //是否完成了目标值，也就是打卡了0未打，1就已打
        PedometerCard.addIntProperty("status");
    }

    private static void addCalendarCard(Schema schema) {
        calenderCard = schema.addEntity("CalendarCardEntity");
        calenderCard.setTableName("CalendarCard");
        //自动生成id
        calenderCard.addIdProperty().autoincrement();

        //日期
        calenderCard.addStringProperty("date").notNull();

        //卡的标题
        calenderCard.addStringProperty("title").notNull();

        //card id
        Property cardIdProperty = calenderCard.addLongProperty("cardId").getProperty();
        calenderCard.addToOne(PedometerCard, cardIdProperty);

        //是否已经打卡了
        calenderCard.addIntProperty("status").notNull();
    }

}
