package com.aprendiz.ragp.proyectopsp7.models;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String DATABASE_NAME ="psp.db";
    public static final int DATABASE_VERSION =1;

    public static final String TableProject ="CREATE TABLE PROJECT (IDPROJECT INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT);";
    public static final String TableTimeLog ="CREATE TABLE TIMELOG (IDTIMELOG INTEGER PRIMARY KEY AUTOINCREMENT, PHASE TEXT, START TEXT, INTERRUPCION INTEGER, STOP TEXT, DELTA INTEGER, COMMENTS TEXT, PROJECT INTEGER, " +
            "FOREIGN KEY (PROJECT) REFERENCES PROJECT(IDPROJECT));";
    public static final String TableDefectLog ="CREATE TABLE DEFECTLOG (IDDEFECTLOG INTEGER PRIMARY KEY AUTOINCREMENT, DATE TEXT, TYPE TEXT, FIXTIME TEXT, PHASEI TEXT, PHASER TEXT, COMMENTS TEXT, PROJECT INTEGER, " +
            "FOREIGN KEY (PROJECT) REFERENCES PROJECT(IDPROJECT));";

    public static final String TablePPS ="CREATE TABLE PPS (IDPPS INTEGER PRIMARY KEY AUTOINCREMENT, TIME INT, PROJECT INTEGER, FOREIGN KEY (PROJECT) REFERENCES PROJECT(IDPROJECT));";


    public static List<String> phases = new ArrayList<>();

    public static void inputPhases(){
        phases = new ArrayList<>();
        phases.add("PLAN");
        phases.add("DLD");
        phases.add("CODE");
        phases.add("COMPILE");
        phases.add("UT");
        phases.add("PM");

    }





}
