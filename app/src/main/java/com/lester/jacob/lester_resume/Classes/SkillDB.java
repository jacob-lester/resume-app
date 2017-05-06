package com.lester.jacob.lester_resume.Classes;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SkillDB {

    public static final String DB_NAME = "skill.db";
    public static final int DB_VERISON = 1;
    public static final String SKILL_TABLE = "skill";

    public static final String SKILL_ID = "_id";
    public static final int SKILL_ID_COL = 0;

    public static final String SKILL_NAME = "name";
    public static final int SKILL_NAME_COL = 1;

    public static final String SKILL_DESC = "desc";
    public static final int SKILL_DESC_COL = 2;

    public static final String CREATE_SKILL_TABLE =
            "CREATE TABLE " + SKILL_TABLE + " (" +
                    SKILL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    SKILL_NAME + " TEXT NOT NULL UNIQUE, " +
                    SKILL_DESC + " TEXT);";

    public static final String DROP_SKILL_TABLE =
            "DROP TABLE IF EXISTS " + SKILL_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {

        private Context mContext;
        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mContext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SKILL_TABLE);

            Gson gson = new Gson();
            String jsonOutput = jsonToStringFromAssetFolder(mContext);
            Type listType = new TypeToken<List<Skill>>(){}.getType();
            List<Skill> skills = (List<Skill>) gson.fromJson(jsonOutput, listType);

            for(int i = 1; i <= skills.size(); i++) {
                db.execSQL("INSERT INTO skill VALUES (" + i + ", '" + skills.get(i-1).getName() + "', '" + skills.get(i-1).getDesc() + "')");
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.d("Skill ", "Upgrading db from version " + oldVersion + " to " + newVersion);
            db.execSQL(SkillDB.DROP_SKILL_TABLE);
            onCreate(db);
        }
    }

    private SQLiteDatabase db;
    private DBHelper dbHelper;

    public SkillDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERISON);
    }

    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWritableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null) {
            db.close();
        }
    }

    public Skill getSkill(int id) {
        String where = SKILL_ID + "= ?";
        String[] whereArgs = { Integer.toString(id) };
        this.openReadableDB();
        Cursor cursor = db.query(SKILL_TABLE, null, where, whereArgs, null, null, null);
        cursor.moveToNext();
        Skill skill = getSkillFromCursor(cursor);
        if (cursor != null) {
            cursor.close();
        }
        this.closeDB();
        return skill;
    }

    public ArrayList<Skill> getSkills() {
        this.openReadableDB();
        Cursor cursor = db.query(SKILL_TABLE, null, null, null, null, null, "name");
        ArrayList<Skill> skills = new ArrayList<>();
        while (cursor.moveToNext()) {
            skills.add(getSkillFromCursor(cursor));
        }

        if (cursor != null) {
            cursor.close();
        }

        this.closeDB();
        return skills;
    }

    private static Skill getSkillFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0) {
            return null;
        } else {
            try {
                Skill skill = new Skill(
                        cursor.getInt(SKILL_ID_COL),
                        cursor.getString(SKILL_NAME_COL),
                        cursor.getString(SKILL_DESC_COL));
                return skill;
            } catch (Exception e) {
                return null;
            }
        }
    }

    public long insertSkill(Skill skill) {
        ContentValues cv = new ContentValues();
        cv.put(SKILL_NAME, skill.getName());
        cv.put(SKILL_DESC, skill.getDesc());

        this.openWritableDB();
        long rowID = db.insert(SKILL_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public int updateSkill(Skill skill) {
        ContentValues cv = new ContentValues();
        cv.put(SKILL_NAME, skill.getName());
        cv.put(SKILL_DESC, skill.getDesc());

        String where = SKILL_ID + "= ?";
        String[] whereArgs = { String.valueOf(skill.getId()) };

        this.openWritableDB();
        int rowCount = db.update(SKILL_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deleteSkill(int id) {
        String where = SKILL_ID + "= ?";
        String[] whereArgs = { Integer.toString(id) };
        this.openWritableDB();
        int rowCount = db.delete(SKILL_TABLE, where, whereArgs);
        this.closeDB();
        return rowCount;
    }

    public static String jsonToStringFromAssetFolder(Context context) {
        byte[] data = null;
        try {
            AssetManager manager = context.getAssets();
            InputStream file = manager.open("skills.json");

            data = new byte[file.available()];
            file.read(data);
            file.close();
        } catch (IOException e) {

        }


        return new String(data);
    }
}
