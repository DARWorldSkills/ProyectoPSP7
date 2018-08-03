package com.aprendiz.ragp.proyectopsp7.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ManagerDB {
    GestorDB gestorDB;
    Context context;
    SQLiteDatabase db;

    public ManagerDB(Context context) {
        this.context = context;
    }

    public void openDBWrite(){
        gestorDB = new GestorDB(context);
        db = gestorDB.getWritableDatabase();
    }

    public void openDBrRead(){
        gestorDB = new GestorDB(context);
        db = gestorDB.getReadableDatabase();
    }


    //Métodos para el listado de datos de las tablas PROJECT, TIMELOG, DEFECTLOG Y PPS
    public List<Project> selectProjects(){
        List<Project> results = new ArrayList<>();
        openDBrRead();
        Cursor cursor = db.rawQuery("SELECT * FROM PROJECT",null);
        if (cursor.moveToFirst()){
            do {
                Project project = new Project();
                project.setId(cursor.getInt(0));
                project.setName(cursor.getString(1));
                results.add(project);
            }while (cursor.moveToNext());
        }

        cursor.close();
        closeDB();
        return results;
    }

    public List<CTimeLog> selectTimeLogs(int project){
        List<CTimeLog> results = new ArrayList<>();
        openDBrRead();
        Cursor cursor = db.rawQuery("SELECT * FROM TIMELOG WHERE PROJECT="+project,null);
        if (cursor.moveToFirst()){
            do {
                CTimeLog cTimeLog = new CTimeLog();
                cTimeLog.setId(cursor.getInt(0));
                cTimeLog.setPhase(cursor.getString(1));
                cTimeLog.setStart(cursor.getString(2));
                cTimeLog.setInterrupcion(cursor.getInt(3));
                cTimeLog.setStop(cursor.getString(4));
                cTimeLog.setDelta(cursor.getInt(5));
                cTimeLog.setComments(cursor.getString(6));
                cTimeLog.setProject(cursor.getInt(7));
                results.add(cTimeLog);
            }while (cursor.moveToNext());
        }

        cursor.close();
        closeDB();
        return results;
    }


    public List<CDefectLog> selectDefectLogs(int project){
        List<CDefectLog> results = new ArrayList<>();
        openDBrRead();
        Cursor cursor = db.rawQuery("SELECT * FROM DEFECTLOG WHERE PROJECT="+project,null);
        if (cursor.moveToFirst()){
            do {
                CDefectLog cDefectLog = new CDefectLog();
                cDefectLog.setId(cursor.getInt(0));
                cDefectLog.setDate(cursor.getString(1));
                cDefectLog.setType(cursor.getString(2));
                cDefectLog.setFixtime(cursor.getString(3));
                cDefectLog.setPhaseI(cursor.getString(4));
                cDefectLog.setPhaseR(cursor.getString(5));
                cDefectLog.setComments(cursor.getString(6));
                cDefectLog.setId(cursor.getInt(7));
                results.add(cDefectLog);
            }while (cursor.moveToNext());
        }

        cursor.close();
        closeDB();
        return results;
    }


    public List<CPPS> selectCpps(int project){
        List<CPPS> results = new ArrayList<>();
        openDBrRead();
        Cursor cursor = db.rawQuery("SELECT * FROM PPS WHERE PROJECT="+project,null);
        if (cursor.moveToFirst()){
            do {
                CPPS cpps = new CPPS();
                cpps.setId(cursor.getInt(0));
                cpps.setTime(cursor.getInt(1));
                cpps.setProject(cursor.getInt(2));
                results.add(cpps);
            }while (cursor.moveToNext());
        }

        cursor.close();
        closeDB();
        return results;
    }


    //Métodos para el ingreso de datos de las tablas PROJECT, TIMELOG, DEFECTLOG Y PPS
    public void insertProject(Project project){
        openDBWrite();
        ContentValues values = new ContentValues();
        values.put("NAME",project.getName());
        db.insert("PROJECT",null,values);
        closeDB();
    }

    public void insertTimeLog(CTimeLog cTimeLog){
        openDBWrite();
        ContentValues values = new ContentValues();
        values.put("PHASE",cTimeLog.getPhase());
        values.put("START",cTimeLog.getStart());
        values.put("INTERRUPCION",cTimeLog.getInterrupcion());
        values.put("STOP",cTimeLog.getStop());
        values.put("DELTA",cTimeLog.getDelta());
        values.put("COMMENTS",cTimeLog.getComments());
        values.put("PROJECT",cTimeLog.getProject());
        db.insert("TIMELOG",null,values);
        closeDB();
    }


    public void insertDefectLog(CDefectLog cDefectLog){
        openDBWrite();
        ContentValues values = new ContentValues();
        values.put("DATE",cDefectLog.getDate());
        values.put("TYPE",cDefectLog.getType());
        values.put("FIXTIME",cDefectLog.getFixtime());
        values.put("PHASEI",cDefectLog.getPhaseI());
        values.put("PHASER",cDefectLog.getPhaseR());
        values.put("COMMENTS",cDefectLog.getComments());
        values.put("PROJECT",cDefectLog.getProject());
        db.insert("DEFECTLOG",null,values);
        closeDB();
    }


    public void insertPPS(CPPS cpps){
        openDBWrite();
        ContentValues values = new ContentValues();
        values.put("TIME",cpps.getTime());
        values.put("PROJECT",cpps.getProject());
        db.insert("PPS",null,values);
        closeDB();
    }


    //Métodos para la actualización de datos de las tablas PROJECT, TIMELOG, DEFECTLOG Y PPS
    public void updateProject(Project project){
        openDBWrite();
        ContentValues values = new ContentValues();
        values.put("NAME",project.getName());
        String[] parameters ={Integer.toString(project.getId())};
        db.update("PROJECT",values,"IDPROJECT=?",parameters);
        closeDB();
    }

    public void updateTimeLog(CTimeLog cTimeLog){
        openDBWrite();
        ContentValues values = new ContentValues();
        values.put("PHASE",cTimeLog.getPhase());
        values.put("START",cTimeLog.getStart());
        values.put("INTERRUPCION",cTimeLog.getInterrupcion());
        values.put("STOP",cTimeLog.getStop());
        values.put("DELTA",cTimeLog.getDelta());
        values.put("COMMENTS",cTimeLog.getComments());
        String[] parameters ={Integer.toString(cTimeLog.getId())};
        db.update("TIMELOG",values,"IDTIMELOG=?",parameters);
        closeDB();
    }


    public void updateDefectLog(CDefectLog cDefectLog){
        openDBWrite();
        ContentValues values = new ContentValues();
        values.put("DATE",cDefectLog.getDate());
        values.put("START",cDefectLog.getType());
        values.put("FIXTIME",cDefectLog.getFixtime());
        values.put("PHASEI",cDefectLog.getPhaseI());
        values.put("PHASER",cDefectLog.getPhaseR());
        values.put("COMMENTS",cDefectLog.getComments());
        String[] parameters ={Integer.toString(cDefectLog.getId())};
        db.update("DEFECTLOG",values,"IDDEFECTLOG=?",parameters);
        closeDB();
    }


    public void updatePPS(CPPS cpps){
        openDBWrite();
        ContentValues values = new ContentValues();
        values.put("TIME",cpps.getTime());
        String[] parameters ={Integer.toString(cpps.getId())};
        db.update("PPS",values,"IDPPS=?",parameters);
        closeDB();
    }


    //Métodos para la eliminación de datos de las tablas PROJECT, TIMELOG, DEFECTLOG Y PPS

    public void deleteProject(Project project){
        openDBWrite();
        ContentValues values = new ContentValues();
        values.put("NAME",project.getName());
        String[] parameters ={Integer.toString(project.getId())};
        db.update("PROJECT",values,"IDPROJECT=?",parameters);
        closeDB();
    }

    public void deleteTimeLog(CTimeLog cTimeLog){
        openDBWrite();
        String[] parameters ={Integer.toString(cTimeLog.getId())};
        db.delete("TIMELOG","IDTIMELOG=?",parameters);
        closeDB();
    }


    public void deleteDefectLog(CDefectLog cDefectLog){
        openDBWrite();
        String[] parameters ={Integer.toString(cDefectLog.getId())};
        db.delete("DEFECTLOG","IDDEFECTLOG=?",parameters);
        closeDB();
    }


    public void deletePPS(CPPS cpps){
        openDBWrite();
        String[] parameters ={Integer.toString(cpps.getId())};
        db.delete("PPS","IDPPS=?",parameters);
        closeDB();
    }


    public List<Results> consultaDeYuli(int timeP, int proyecto){
        List<Results> results = new ArrayList<>();
        openDBrRead();
        Cursor cursor = db.rawQuery("SELECT PHASE, DELTA FROM TIMELOG WHERE PROJECT ="+proyecto+";",null);
        if (cursor.moveToFirst()){
            do {
                Results tmp = new Results();
                tmp.setPhase(cursor.getString(0));
                tmp.setTime(cursor.getInt(1));
                double p= (tmp.getTime() / timeP)*100;
                tmp.setPercent((int) p);
                results.add(tmp);

            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDB();

        return results;
    }

    public List<Results> consultaDeYuli1(int timeP, int proyecto){
        List<Results> results = new ArrayList<>();
        openDBrRead();
        Cursor cursor = db.rawQuery("SELECT PHASEI, DELTA FROM DEFECTLOG WHERE PROJECT ="+proyecto+";",null);
        if (cursor.moveToFirst()){
            do {
                Results tmp = new Results();
                tmp.setPhase(cursor.getString(0));
                tmp.setTime(cursor.getInt(1));
                double p= (tmp.getTime() / timeP)*100;
                tmp.setPercent((int) p);
                results.add(tmp);

            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDB();

        return results;
    }

    public List<Results> consultaDeYuli2(int timeP, int proyecto){
        List<Results> results = new ArrayList<>();
        openDBrRead();
        Cursor cursor = db.rawQuery("SELECT PHASER, DELTA FROM DEFECTLOG WHERE PROJECT ="+proyecto+";",null);
        if (cursor.moveToFirst()){
            do {
                Results tmp = new Results();
                tmp.setPhase(cursor.getString(0));
                tmp.setTime(cursor.getInt(1));
                double p= (tmp.getTime() / timeP)*100;
                tmp.setPercent((int) p);
                results.add(tmp);

            }while (cursor.moveToNext());
        }
        cursor.close();
        closeDB();

        return results;
    }





    public void closeDB(){
        db.close();
    }


}
