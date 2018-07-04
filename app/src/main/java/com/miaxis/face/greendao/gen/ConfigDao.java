package com.miaxis.face.greendao.gen;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.miaxis.face.bean.Config;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONFIG".
*/
public class ConfigDao extends AbstractDao<Config, Long> {

    public static final String TABLENAME = "CONFIG";

    /**
     * Properties of entity Config.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property Ip = new Property(1, String.class, "ip", false, "IP");
        public final static Property Port = new Property(2, int.class, "port", false, "PORT");
        public final static Property UpTime = new Property(3, String.class, "upTime", false, "UP_TIME");
        public final static Property PassScore = new Property(4, float.class, "passScore", false, "PASS_SCORE");
        public final static Property Banner = new Property(5, String.class, "banner", false, "BANNER");
        public final static Property IntervalTime = new Property(6, int.class, "intervalTime", false, "INTERVAL_TIME");
        public final static Property OrgId = new Property(7, String.class, "orgId", false, "ORG_ID");
        public final static Property OrgName = new Property(8, String.class, "orgName", false, "ORG_NAME");
        public final static Property NetFlag = new Property(9, boolean.class, "netFlag", false, "NET_FLAG");
        public final static Property QueryFlag = new Property(10, boolean.class, "queryFlag", false, "QUERY_FLAG");
        public final static Property Password = new Property(11, String.class, "password", false, "PASSWORD");
        public final static Property VerifyMode = new Property(12, int.class, "verifyMode", false, "VERIFY_MODE");
        public final static Property WhiteFlag = new Property(13, boolean.class, "whiteFlag", false, "WHITE_FLAG");
        public final static Property BlackFlag = new Property(14, boolean.class, "blackFlag", false, "BLACK_FLAG");
    }


    public ConfigDao(DaoConfig config) {
        super(config);
    }
    
    public ConfigDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONFIG\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"IP\" TEXT," + // 1: ip
                "\"PORT\" INTEGER NOT NULL ," + // 2: port
                "\"UP_TIME\" TEXT," + // 3: upTime
                "\"PASS_SCORE\" REAL NOT NULL ," + // 4: passScore
                "\"BANNER\" TEXT," + // 5: banner
                "\"INTERVAL_TIME\" INTEGER NOT NULL ," + // 6: intervalTime
                "\"ORG_ID\" TEXT," + // 7: orgId
                "\"ORG_NAME\" TEXT," + // 8: orgName
                "\"NET_FLAG\" INTEGER NOT NULL ," + // 9: netFlag
                "\"QUERY_FLAG\" INTEGER NOT NULL ," + // 10: queryFlag
                "\"PASSWORD\" TEXT," + // 11: password
                "\"VERIFY_MODE\" INTEGER NOT NULL ," + // 12: verifyMode
                "\"WHITE_FLAG\" INTEGER NOT NULL ," + // 13: whiteFlag
                "\"BLACK_FLAG\" INTEGER NOT NULL );"); // 14: blackFlag
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONFIG\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Config entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String ip = entity.getIp();
        if (ip != null) {
            stmt.bindString(2, ip);
        }
        stmt.bindLong(3, entity.getPort());
 
        String upTime = entity.getUpTime();
        if (upTime != null) {
            stmt.bindString(4, upTime);
        }
        stmt.bindDouble(5, entity.getPassScore());
 
        String banner = entity.getBanner();
        if (banner != null) {
            stmt.bindString(6, banner);
        }
        stmt.bindLong(7, entity.getIntervalTime());
 
        String orgId = entity.getOrgId();
        if (orgId != null) {
            stmt.bindString(8, orgId);
        }
 
        String orgName = entity.getOrgName();
        if (orgName != null) {
            stmt.bindString(9, orgName);
        }
        stmt.bindLong(10, entity.getNetFlag() ? 1L: 0L);
        stmt.bindLong(11, entity.getQueryFlag() ? 1L: 0L);
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(12, password);
        }
        stmt.bindLong(13, entity.getVerifyMode());
        stmt.bindLong(14, entity.getWhiteFlag() ? 1L: 0L);
        stmt.bindLong(15, entity.getBlackFlag() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Config entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
 
        String ip = entity.getIp();
        if (ip != null) {
            stmt.bindString(2, ip);
        }
        stmt.bindLong(3, entity.getPort());
 
        String upTime = entity.getUpTime();
        if (upTime != null) {
            stmt.bindString(4, upTime);
        }
        stmt.bindDouble(5, entity.getPassScore());
 
        String banner = entity.getBanner();
        if (banner != null) {
            stmt.bindString(6, banner);
        }
        stmt.bindLong(7, entity.getIntervalTime());
 
        String orgId = entity.getOrgId();
        if (orgId != null) {
            stmt.bindString(8, orgId);
        }
 
        String orgName = entity.getOrgName();
        if (orgName != null) {
            stmt.bindString(9, orgName);
        }
        stmt.bindLong(10, entity.getNetFlag() ? 1L: 0L);
        stmt.bindLong(11, entity.getQueryFlag() ? 1L: 0L);
 
        String password = entity.getPassword();
        if (password != null) {
            stmt.bindString(12, password);
        }
        stmt.bindLong(13, entity.getVerifyMode());
        stmt.bindLong(14, entity.getWhiteFlag() ? 1L: 0L);
        stmt.bindLong(15, entity.getBlackFlag() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public Config readEntity(Cursor cursor, int offset) {
        Config entity = new Config( //
            cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // ip
            cursor.getInt(offset + 2), // port
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // upTime
            cursor.getFloat(offset + 4), // passScore
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // banner
            cursor.getInt(offset + 6), // intervalTime
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // orgId
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // orgName
            cursor.getShort(offset + 9) != 0, // netFlag
            cursor.getShort(offset + 10) != 0, // queryFlag
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // password
            cursor.getInt(offset + 12), // verifyMode
            cursor.getShort(offset + 13) != 0, // whiteFlag
            cursor.getShort(offset + 14) != 0 // blackFlag
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Config entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setIp(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPort(cursor.getInt(offset + 2));
        entity.setUpTime(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPassScore(cursor.getFloat(offset + 4));
        entity.setBanner(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setIntervalTime(cursor.getInt(offset + 6));
        entity.setOrgId(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setOrgName(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setNetFlag(cursor.getShort(offset + 9) != 0);
        entity.setQueryFlag(cursor.getShort(offset + 10) != 0);
        entity.setPassword(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setVerifyMode(cursor.getInt(offset + 12));
        entity.setWhiteFlag(cursor.getShort(offset + 13) != 0);
        entity.setBlackFlag(cursor.getShort(offset + 14) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Config entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Config entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Config entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
