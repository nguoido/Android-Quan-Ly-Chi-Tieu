package com.vivhp.qlct.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.vivhp.qlct.Model.ModelHistory;
import com.vivhp.qlct.Model.Model_Phannhom;
import com.vivhp.qlct.Model.Model_Taikhoan;
import com.vivhp.qlct.Model.Model_Thongke;
import com.vivhp.qlct.Model.Model_Thuchi;

import java.util.ArrayList;



public class DataBaseHelper extends SQLiteOpenHelper {

    ContentValues values;


    private static final String TAG = "SQLite";

    //Tên Cơ Sở Dữ Liệu
    private static String Database_Name = "QLCT.db";
    //Phiên Bản Cơ Sở Dữ Liệu Hiện Tại
    private static int Database_Version = 5;

    public DataBaseHelper(Context context) {
        super(context, Database_Name, null, Database_Version);
    }

    /**     --- DỮ LIỆU CŨ ---
     * Tạo các bảng trong cơ sở dữ liệu
     * 1. Bảng Tài Khoản
     * + id - int - PKey AI
     * + tentk - text
     * + sotien - int
     * <p>
     * 2. Bảng Phân Nhóm
     * + manhom - int - PKey AI
     * + tennhom - text
     * + tenkhoan - text
     * <p>
     * 3. Bảng Thu Chi
     * + mathuchi - int - PKey AI
     * + id_tk - int - FKey (Bảng Tài Khoản)  on delete cascade
     * + sotien - int
     * + ngay - Date
     * + manhom - int - FKey (Bảng Phân Nhóm) on delete cascade
     * <p>
     * 4. Bảng Giao Dịch
     * + magiaodich - int - PKey AI
     * + lydo - text
     * + trang thai - text
     * + gio - time
     * + mathuchi - int - FKey (Bảng Thu Chi)
     **/

    //Tên Bảng
    private static final String TABLE_TAIKHOAN = "tbltaikhoan";
    private static final String TABLE_PHANNHOM = "tblphannhom";
    private static final String TABLE_THUCHI = "tblthuchi";
    private static final String TABLE_GIAODICH = "tblgiaodich";

    /**
     * Các Trường trong bảng
     **/
    //COLUMN TAIKHOAN
    private static final String COLUMN_TAIKHOAN_ID_TK = "_id";
    private static final String COLUMN_TAIKHOAN_TENTK = "tentk";
    private static final String COLUMN_TAIKHOAN_LOAIHINH = "loaihinh";
    private static final String COLUMN_TAIKHOAN_SOTIEN = "sotien";

    //COLUMN PHANNHOM
    private static final String COLUMN_PHANNHOM_MANHOM = "manhom";
    private static final String COLUMN_PHANNHOM_TENNHOM = "tennhom";
    private static final String COLUMN_PHANNHOM_TENKHOAN = "tenkhoan";

    //COLUMN THUCHI
    private static final String COLUMN_THUCHI_MATHUCHI = "mathuchi";
    private static final String COLUMN_THUCHI_SOTIEN = "sotien";
    private static final String COLUMN_THUCHI_NGAY = "ngay";
    private static final String COLUMN_THUCHI_LYDO = "lydo";
    private static final String COLUMN_THUCHI_MANHOM = "manhom";
    private static final String COLUMN_THUCHI_ID_TK = "id_tk";


    //Create table
    String taikhoan = "CREATE TABLE " + TABLE_TAIKHOAN + "("
            + COLUMN_TAIKHOAN_ID_TK + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_TAIKHOAN_TENTK + " TEXT, "
            + COLUMN_TAIKHOAN_LOAIHINH + " TEXT, "
            + COLUMN_TAIKHOAN_SOTIEN + " TEXT" + ")";

    String phannhom = "CREATE TABLE " + TABLE_PHANNHOM + "("
            + COLUMN_PHANNHOM_MANHOM + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_PHANNHOM_TENNHOM + " TEXT, "
            + COLUMN_PHANNHOM_TENKHOAN + " TEXT" + ")";

    String thuchi = "CREATE TABLE " + TABLE_THUCHI + "("
            + COLUMN_THUCHI_MATHUCHI + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_THUCHI_SOTIEN + " INT, "
            + COLUMN_THUCHI_NGAY + " DATE, "
            + COLUMN_THUCHI_LYDO + " TEXT, "
            + COLUMN_THUCHI_ID_TK + " INT CONSTRAINT " + COLUMN_THUCHI_ID_TK + " REFERENCES " + TABLE_TAIKHOAN + "(" + COLUMN_TAIKHOAN_ID_TK + ")" + " ON DELETE CASCADE,"
            + COLUMN_THUCHI_MANHOM + " INT CONSTRAINT " + COLUMN_THUCHI_MANHOM + " REFERENCES " + TABLE_PHANNHOM + "(" + COLUMN_PHANNHOM_MANHOM + ")" + " ON DELETE CASCADE"
            + ")";

    String TAIKHOAN1 = "INSERT INTO " + TABLE_TAIKHOAN + "  Values ('1','Ví','Tiền Mặt','');";
    String TAIKHOAN2 = "INSERT INTO " + TABLE_TAIKHOAN + "  Values ('2','Thẻ tín dụng','Thẻ Tín Dụng','0');";
    String TAIKHOAN3 = "INSERT INTO " + TABLE_TAIKHOAN + "  Values ('3','Tiền tiết kiệm','Thẻ Tín Dụng','2220');";

    //Default insert row to phan nhom
    String nhomthu1 = "INSERT INTO " + TABLE_PHANNHOM + "  Values ('1','Tiền Lương','Khoản Thu');";
    String nhomthu2 = "INSERT INTO " + TABLE_PHANNHOM + "  Values ('3','Tiền Trợ Cấp','Khoản Thu');";
    String nhomthu3 = "INSERT INTO " + TABLE_PHANNHOM + "  Values ('2','Tiền Được Cho/Tặng','Khoản Thu');";
    String nhomthu4 = "INSERT INTO " + TABLE_PHANNHOM + "  Values ('4','Tiền Tiết Kiệm','Khoản Thu');";

    String nhomchi1 = "INSERT INTO " + TABLE_PHANNHOM + "  Values ('6','Tiền Ăn uống','Khoản Chi');";
    String nhomchi2 = "INSERT INTO " + TABLE_PHANNHOM + "  Values ('7','Tiền Xăng/Đi lại','Khoản Chi');";
    String nhomchi3 = "INSERT INTO " + TABLE_PHANNHOM + "  Values ('8','Tiền Trà Đá','Khoản Chi');";
    String nhomchi4 = "INSERT INTO " + TABLE_PHANNHOM + "  Values ('9','Tiền Thẻ Game/Điện Thoại','Khoản Chi');";
    String nhomchi5 = "INSERT INTO " + TABLE_PHANNHOM + "  Values ('10','Tiền Đi chơi','Khoản Chi');";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
        db.execSQL(taikhoan);
        //Default insert 3 row to table tai khoan
        db.execSQL(TAIKHOAN1);
        db.execSQL(TAIKHOAN2);
        db.execSQL(TAIKHOAN3);
        //END

        db.execSQL(phannhom);
        //Default insert 4 row to table Phan nhom = khoan thu
        db.execSQL(nhomthu1);
        db.execSQL(nhomthu2);
        db.execSQL(nhomthu3);
        db.execSQL(nhomthu4);
        //Default insert 5 row to table Phan nhom = khoan Chi
        db.execSQL(nhomchi1);
        db.execSQL(nhomchi2);
        db.execSQL(nhomchi3);
        db.execSQL(nhomchi4);
        db.execSQL(nhomchi5);

        db.execSQL(thuchi);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < newVersion) {
            Log.i(TAG, "MyDatabaseHelper.onUpgrade ... ");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAIKHOAN);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_PHANNHOM);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_THUCHI);
        }
        onCreate(db);
    }

    /**
     * CREATE
     * - CREATE
     * - READ
     * - UPDATE
     * - DELETE
     * TABLE_TAIKHOAN
     **/

    /**
     * Done
     **/
    //Thêm Tài Khoản
    public boolean addTaikhoan(Model_Taikhoan taikhoan) {
        SQLiteDatabase database = this.getWritableDatabase();

        values = new ContentValues();

        values.put(COLUMN_TAIKHOAN_TENTK, taikhoan.getTentk());
        values.put(COLUMN_TAIKHOAN_LOAIHINH, taikhoan.getLoaihinh());
        values.put(COLUMN_TAIKHOAN_SOTIEN, taikhoan.getSotien());

        //Inserting Row to TAi Khoan
        return database.insert(TABLE_TAIKHOAN, null, values) != -1;
//        database.close();
    }

    /**
     * Done
     **/
    //Đọc từng Tài khoản
    public Model_Taikhoan getTaikhoan(int id) {
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.query(TABLE_TAIKHOAN, new String[]{COLUMN_TAIKHOAN_ID_TK, COLUMN_TAIKHOAN_TENTK, COLUMN_TAIKHOAN_LOAIHINH, COLUMN_TAIKHOAN_SOTIEN}, COLUMN_TAIKHOAN_ID_TK + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Model_Taikhoan taikhoan = new Model_Taikhoan(
                cursor.getString(cursor.getColumnIndex(COLUMN_TAIKHOAN_TENTK)),
                cursor.getString(cursor.getColumnIndex(COLUMN_TAIKHOAN_LOAIHINH)),
                cursor.getInt(cursor.getColumnIndex(COLUMN_TAIKHOAN_SOTIEN)));

        return taikhoan;
    }

    /**
     * Done
     **/
    //Đọc tất cả tài khoản
    public ArrayList<Model_Taikhoan> getAllTaiKhoan() {
        ArrayList<Model_Taikhoan> taikhoanList = new ArrayList<Model_Taikhoan>();
        //Select all query
        String selectQuery = "Select " + COLUMN_TAIKHOAN_TENTK + ", " + COLUMN_TAIKHOAN_LOAIHINH + ", " + COLUMN_TAIKHOAN_SOTIEN + " from " + TABLE_TAIKHOAN;

        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        //chạy vòng lặp và thêm tất cả rows vào list
        if (cursor.moveToFirst()) {
            do {
                Model_Taikhoan taikhoan = new Model_Taikhoan();
                taikhoan.setTentk(cursor.getString(cursor.getColumnIndex(COLUMN_TAIKHOAN_TENTK)));
                taikhoan.setLoaihinh(cursor.getString(cursor.getColumnIndex(COLUMN_TAIKHOAN_LOAIHINH)));
                taikhoan.setSotien(cursor.getInt(cursor.getColumnIndex(COLUMN_TAIKHOAN_SOTIEN)));

                //Them vao list
                taikhoanList.add(taikhoan);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return taikhoanList;
    }

    /**
     * Done
     **/
    //Đọc tất cả TÊN tài khoản
    public ArrayList<Model_Taikhoan> getAllTenTaiKhoan() {
        ArrayList<Model_Taikhoan> taikhoanList = new ArrayList<Model_Taikhoan>();
        //Select all query
        String selectQuery1 = "select " + COLUMN_TAIKHOAN_ID_TK + ", " + COLUMN_TAIKHOAN_TENTK + ", " + COLUMN_TAIKHOAN_LOAIHINH + " from " + TABLE_TAIKHOAN;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery1, null);

        //chạy vòng lặp và thêm tất cả rows vào list
        if (cursor.moveToFirst()) {
            do {
                Model_Taikhoan taikhoan = new Model_Taikhoan();
                taikhoan.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_TAIKHOAN_ID_TK)));
                taikhoan.setTentk(cursor.getString(cursor.getColumnIndex(COLUMN_TAIKHOAN_TENTK)));
                taikhoan.setLoaihinh(cursor.getString(cursor.getColumnIndex(COLUMN_TAIKHOAN_LOAIHINH)));
                //Them vao list
                taikhoanList.add(taikhoan);
            } while (cursor.moveToNext());
        }

        return taikhoanList;
    }

    //Get tài khoản cuối cùng
    public Model_Taikhoan getFinalTenTaiKhoan() {
        Model_Taikhoan taikhoanList = new Model_Taikhoan();
        //Select all query
        String selectQuery1 = "select " + COLUMN_TAIKHOAN_ID_TK + ", " + COLUMN_TAIKHOAN_TENTK + ", " + COLUMN_TAIKHOAN_LOAIHINH + " from " + TABLE_TAIKHOAN;

        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery1, null);

        //chạy vòng lặp và thêm tất cả rows vào list
        if (cursor.getCount() > 0) {
            cursor.moveToLast();

            taikhoanList.set_id(cursor.getInt(cursor.getColumnIndex(COLUMN_TAIKHOAN_ID_TK)));
            taikhoanList.setTentk(cursor.getString(cursor.getColumnIndex(COLUMN_TAIKHOAN_TENTK)));
            taikhoanList.setLoaihinh(cursor.getString(cursor.getColumnIndex(COLUMN_TAIKHOAN_LOAIHINH)));
        }

        return taikhoanList;
    }

    /**
     * Done
     **/
    //Get Tổng số tiền có trong tài khoản
    public int getTotalMoney() {
        String selectSum = "select SUM (" + COLUMN_TAIKHOAN_SOTIEN + ") " + "AS TOTALMONEY FROM " + TABLE_TAIKHOAN;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectSum, null);
        if (cursor != null)
            cursor.moveToFirst();

        int money = cursor.getInt(cursor.getColumnIndex("TOTALMONEY"));
        return money;
    }


    //Get Tien theo id
    public int getTienTk(int _id) {
        int tien_by_id = 0;
        String selectQuery = "SELECT sotien FROM tbltaikhoan where _id = ?";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, new String[]{String.valueOf(_id)});
        if (cursor != null && cursor.moveToFirst()) {
            int sotien = cursor.getInt(cursor.getColumnIndex("sotien"));
            tien_by_id = sotien;
        }
        return tien_by_id;
    }

    //Update tien theo id
    public int updateTienTk(Model_Taikhoan taikhoan) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TAIKHOAN_SOTIEN, taikhoan.getSotien());

        //Update row
        return database.update(TABLE_TAIKHOAN, values, COLUMN_TAIKHOAN_ID_TK + " = ?", new String[]{String.valueOf(taikhoan.get_id())});
    }
    /**
     * TABLE TAIKHOAN
     * END
     **/

    /**
     * CREATE
     * - CREATE
     * - READ
     * - UPDATE
     * - DELETE
     * TABLE_PHANNHOM
     **/
    /**
     * Done
     **/
    //Thêm nhóm
    public boolean addNhom(Model_Phannhom phannhom) {
        SQLiteDatabase database = this.getWritableDatabase();

        values = new ContentValues();
        values.put(COLUMN_PHANNHOM_TENNHOM, phannhom.getTennhom());
        values.put(COLUMN_PHANNHOM_TENKHOAN, phannhom.getTenkhoan());

        //Insert to phannhom
        return database.insert(TABLE_PHANNHOM, null, values) != -1;
//        database.close();
    }

    public void updateNhom(int id, String nhom, String khoan) {
        SQLiteDatabase database = this.getWritableDatabase();

        values = new ContentValues();
        values.put(COLUMN_PHANNHOM_TENNHOM, nhom);
        values.put(COLUMN_PHANNHOM_TENKHOAN, khoan);
        database.update(TABLE_PHANNHOM, values, " manhom =? ", new String[]{String.valueOf(id)});
    }

    /**
     * Done
     **/
    //Đọc tất cả
    public ArrayList<Model_Phannhom> getAllNhoms() {
        ArrayList<Model_Phannhom> list = new ArrayList<Model_Phannhom>();
        //Select Query
        String query = "Select * from " + TABLE_PHANNHOM;
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(query, null);

        //Loop to add row to list
        if (cursor.moveToFirst()) {
            do {
                Model_Phannhom phannhom = new Model_Phannhom();
                phannhom.setManhom(cursor.getInt(cursor.getColumnIndex(COLUMN_PHANNHOM_MANHOM)));
                phannhom.setTennhom(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENNHOM)));
                phannhom.setTenkhoan(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENKHOAN)));

                //Add to List
                list.add(phannhom);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    /**
     * Done
     **/
    //Đọc theo khoản thu
    public ArrayList<Model_Phannhom> getKhoanThu() {
        ArrayList<Model_Phannhom> list = new ArrayList<Model_Phannhom>();
        //Select Query
        String queryKT = "Select * from " + TABLE_PHANNHOM + " where " + COLUMN_PHANNHOM_TENKHOAN + "=\"Khoản Thu\"";
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(queryKT, null);

        //Loop to add row to list
        if (cursor.moveToFirst()) {
            do {
                Model_Phannhom phannhom = new Model_Phannhom();
                phannhom.setManhom(cursor.getInt(cursor.getColumnIndex(COLUMN_PHANNHOM_MANHOM)));
                phannhom.setTennhom(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENNHOM)));
                phannhom.setTenkhoan(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENKHOAN)));

                //Add to List
                list.add(phannhom);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }
//
    /**
     * Done
     **/
    //Đọc theo khoản Chi
    public ArrayList<Model_Phannhom> getKhoanChi() {
        ArrayList<Model_Phannhom> list = new ArrayList<Model_Phannhom>();
        //Select Query
        String queryKT = "Select * from " + TABLE_PHANNHOM + " where " + COLUMN_PHANNHOM_TENKHOAN + "=\"Khoản Chi\"";
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(queryKT, null);

        //Loop to add row to list
        if (cursor.moveToFirst()) {
            do {
                Model_Phannhom phannhom = new Model_Phannhom();
                phannhom.setManhom(cursor.getInt(cursor.getColumnIndex(COLUMN_PHANNHOM_MANHOM)));
                phannhom.setTennhom(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENNHOM)));
                phannhom.setTenkhoan(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENKHOAN)));

                //Add to List
                list.add(phannhom);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    /**
     * Done
     **/
    //Đọc theo cột Tên Nhóm nếu Tên nhóm thuộc khoản Thu
    public ArrayList<Model_Phannhom> getTenNhomThu() {
        ArrayList<Model_Phannhom> list = new ArrayList<Model_Phannhom>();
        //Select Query
        String queryKT = "Select " + COLUMN_PHANNHOM_MANHOM + "," + COLUMN_PHANNHOM_TENNHOM + " from " + TABLE_PHANNHOM + " WHERE " + COLUMN_PHANNHOM_TENKHOAN + "=\"Khoản Thu\"";
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(queryKT, null);
        //Loop to add row to list
        if (cursor.moveToFirst()) {
            do {
                Model_Phannhom phannhom = new Model_Phannhom();
                phannhom.setManhom(cursor.getInt(cursor.getColumnIndex(COLUMN_PHANNHOM_MANHOM)));
                phannhom.setTennhom(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENNHOM)));
                //Add to List
                list.add(phannhom);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    //Đọc theo cột Tên Nhóm nếu Tên nhóm thuộc khoản Thu
    public Model_Phannhom getLastTenNhomThu() {
       Model_Phannhom phannhomthu = new Model_Phannhom();
        //Select Query
        String queryKT = "Select " + COLUMN_PHANNHOM_MANHOM + "," + COLUMN_PHANNHOM_TENNHOM + " from " + TABLE_PHANNHOM + " WHERE " + COLUMN_PHANNHOM_TENKHOAN + "=\"Khoản Thu\"";
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(queryKT, null);
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            phannhomthu.setManhom(cursor.getInt(cursor.getColumnIndex(COLUMN_PHANNHOM_MANHOM)));
            phannhomthu.setTennhom(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENNHOM)));
        }
        Log.e("ma nhom Thu: ", String.valueOf(phannhomthu.getManhom()));
        Log.e("Ten nhom Thu: ", phannhomthu.getTennhom().toString());

        return phannhomthu;
    }

    /**
     * Done
     **/
    //Đọc theo cột Tên Nhóm nếu Tên nhóm thuộc khoản Chi
    public ArrayList<Model_Phannhom> getTenNhomChi() {
        ArrayList<Model_Phannhom> list = new ArrayList<Model_Phannhom>();
        //Select Query
        String queryKC = "Select " + COLUMN_PHANNHOM_MANHOM + "," + COLUMN_PHANNHOM_TENNHOM + " from " + TABLE_PHANNHOM + " WHERE " + COLUMN_PHANNHOM_TENKHOAN + "=\"Khoản Chi\"";
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(queryKC, null);
        if (cursor.moveToFirst()) {
            do {
                Model_Phannhom phannhom = new Model_Phannhom();
                phannhom.setManhom(cursor.getInt(cursor.getColumnIndex(COLUMN_PHANNHOM_MANHOM)));
                phannhom.setTennhom(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENNHOM)));
                //Add to List
                list.add(phannhom);

            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    //Get nhom chi cuoi
    public Model_Phannhom getLastTenNhomChi() {
        Model_Phannhom phannhomchi = new Model_Phannhom();
        //Select Query
        String queryKC = "Select " + COLUMN_PHANNHOM_MANHOM + "," + COLUMN_PHANNHOM_TENNHOM + " from " + TABLE_PHANNHOM + " WHERE " + COLUMN_PHANNHOM_TENKHOAN + "=\"Khoản Chi\"";
        SQLiteDatabase database = this.getReadableDatabase();

        Cursor cursor = database.rawQuery(queryKC, null);
        //Loop to add row to list
        if (cursor.getCount() > 0) {
            cursor.moveToLast();
            phannhomchi.setManhom(cursor.getInt(cursor.getColumnIndex(COLUMN_PHANNHOM_MANHOM)));
            phannhomchi.setTennhom(cursor.getString(cursor.getColumnIndex(COLUMN_PHANNHOM_TENNHOM)));
            Log.e("ma nhom Chi: ", String.valueOf(phannhomchi.getManhom()));
            Log.e("Ten nhom Chi: ", phannhomchi.getTennhom().toString());
        }
        return phannhomchi;
    }

    /**
     * Done - Bỏ không sử dụng.
     **/
    //Xoá nhóm
    public void deleteNhom(Model_Phannhom phannhom) {
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_PHANNHOM, COLUMN_PHANNHOM_MANHOM + "=?", new String[]{String.valueOf(phannhom.getManhom())});
        database.close();
    }
    /**
     * TABLE PHANNHOM
     * END
     **/


    /**
     * CREATE
     * - CREATE
     * - READ
     * - UPDATE
     * - DELETE
     * TABLE_Thu Chi
     **/

    //Them Khoan Thu / Chi
    public void addThuChi(Model_Thuchi thuchi) {
        SQLiteDatabase database = this.getWritableDatabase();
        values = new ContentValues();
        values.put(COLUMN_THUCHI_SOTIEN, thuchi.getSotien());
        values.put(COLUMN_THUCHI_NGAY, thuchi.getNgay());
        values.put(COLUMN_THUCHI_LYDO, thuchi.getLydo());
        values.put(COLUMN_THUCHI_ID_TK, thuchi.getId_tk());
        values.put(COLUMN_THUCHI_MANHOM, thuchi.getManhom());

        database.insert(TABLE_THUCHI, null, values);
        database.close();
    }

    //Get Chi Tiet Thu Chi
    public ArrayList<ModelHistory> getThuChi(String ngay) {

        ArrayList<ModelHistory> list = new ArrayList<>();

        //Select Query

        String squery = "SELECT       tblthuchi.sotien, lydo, tblphannhom.tenkhoan, tennhom, tbltaikhoan.tentk\n" +
                "FROM            tblphannhom INNER JOIN\n" +
                "                         tblthuchi ON tblphannhom.manhom = tblthuchi.manhom INNER JOIN\n" +
                "                        tbltaikhoan ON tblthuchi.id_tk = tbltaikhoan._id\n" +
                "WHERE ngay = " + "'" +ngay + "'";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(squery, null);
        ModelHistory history = new ModelHistory();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                history = new ModelHistory(cursor.getInt(cursor.getColumnIndex("sotien")),
                        cursor.getString(cursor.getColumnIndex("tennhom")),
                        cursor.getString(cursor.getColumnIndex("lydo")),
                        cursor.getString(cursor.getColumnIndex("tentk")),
                        cursor.getString(cursor.getColumnIndex("tenkhoan")));
                list.add(history);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }
    //get NGroup ngay
    public ArrayList<Model_Thongke> getGroupChiNgay(String ngay) {

        ArrayList<Model_Thongke> list = new ArrayList<>();

        String squery = "SELECT        tblphannhom.tennhom, tblphannhom.tenkhoan, SUM (tblthuchi.sotien) AS TotalMoney\n" +
                "\n" +
                "              FROM          tblphannhom INNER JOIN\n" +
                "                                   tblthuchi ON tblphannhom.manhom = tblthuchi.manhom INNER JOIN\n" +
                "                                   tbltaikhoan ON tblthuchi.id_tk = tbltaikhoan._id\n" +
                "          \n" +
                "                where tblphannhom.tenkhoan = \"Khoản Chi\" AND ngay like '" + ngay +"' group by tblphannhom.tennhom\n";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(squery, null);
        Model_Thongke thongke = new Model_Thongke();
        if (cursor.moveToFirst() != true){
            list.add(new Model_Thongke(0, "Chưa có nhóm", "Chưa có khoản"));
        }
        if (cursor != null && cursor.moveToFirst()) {
            do {
                thongke = new Model_Thongke(cursor.getInt(cursor.getColumnIndex("TotalMoney")),
                        cursor.getString(cursor.getColumnIndex("tennhom")),
                        cursor.getString(cursor.getColumnIndex("tenkhoan")));
                list.add(thongke);
            }while (cursor.moveToNext());
        }
        cursor.close();

        Log.e("List sized: ", String.valueOf(list.size()));

        return list;
    }

    public ArrayList<Model_Thongke> getGroupChi(String ngay) {

        ArrayList<Model_Thongke> list = new ArrayList<>();

        String squery = "SELECT        tblphannhom.tennhom, tblphannhom.tenkhoan, SUM (tblthuchi.sotien) AS TotalMoney\n" +
                "\n" +
                "              FROM          tblphannhom INNER JOIN\n" +
                "                                   tblthuchi ON tblphannhom.manhom = tblthuchi.manhom INNER JOIN\n" +
                "                                   tbltaikhoan ON tblthuchi.id_tk = tbltaikhoan._id\n" +
                "          \n" +
                "                where tblphannhom.tenkhoan = \"Khoản Chi\" AND ngay like '%" + ngay +"%' group by tblphannhom.tennhom\n";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(squery, null);
        Model_Thongke thongke = new Model_Thongke();
        if (cursor.moveToFirst() != true){
            list.add(new Model_Thongke(0, "Chưa có nhóm", "Chưa có khoản"));
        }
        if (cursor != null && cursor.moveToFirst()) {
            do {
                thongke = new Model_Thongke(cursor.getInt(cursor.getColumnIndex("TotalMoney")),
                        cursor.getString(cursor.getColumnIndex("tennhom")),
                        cursor.getString(cursor.getColumnIndex("tenkhoan")));
                list.add(thongke);
            }while (cursor.moveToNext());
        }
        cursor.close();

        Log.e("List sized: ", String.valueOf(list.size()));

        return list;
    }

    //get group thu theo ngay
    public ArrayList<Model_Thongke> getGroupThuNgay(String ngay) {

        ArrayList<Model_Thongke> list = new ArrayList<>();

        String squery = "SELECT        tblphannhom.tennhom, tblphannhom.tenkhoan, SUM (tblthuchi.sotien) AS TotalMoney\n" +
                "\n" +
                "              FROM          tblphannhom INNER JOIN\n" +
                "                                   tblthuchi ON tblphannhom.manhom = tblthuchi.manhom INNER JOIN\n" +
                "                                   tbltaikhoan ON tblthuchi.id_tk = tbltaikhoan._id\n" +
                "          \n" +
                "                where tblphannhom.tenkhoan = \"Khoản Thu\" AND ngay like '" + ngay +"' group by tblphannhom.tennhom\n";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(squery, null);
        Model_Thongke thongke = new Model_Thongke();
        if (cursor.moveToFirst() != true){
            list.add(new Model_Thongke(0, "Chưa có nhóm", "Chưa có khoản"));
        } if (cursor != null && cursor.moveToFirst()) {
            do {
                thongke = new Model_Thongke(cursor.getInt(cursor.getColumnIndex("TotalMoney")),
                        cursor.getString(cursor.getColumnIndex("tennhom")),
                        cursor.getString(cursor.getColumnIndex("tenkhoan")));
                list.add(thongke);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public ArrayList<Model_Thongke> getGroupThu(String ngay) {

        ArrayList<Model_Thongke> list = new ArrayList<>();

        String squery = "SELECT        tblphannhom.tennhom, tblphannhom.tenkhoan, SUM (tblthuchi.sotien) AS TotalMoney\n" +
                "\n" +
                "              FROM          tblphannhom INNER JOIN\n" +
                "                                   tblthuchi ON tblphannhom.manhom = tblthuchi.manhom INNER JOIN\n" +
                "                                   tbltaikhoan ON tblthuchi.id_tk = tbltaikhoan._id\n" +
                "          \n" +
                "                where tblphannhom.tenkhoan = \"Khoản Thu\" AND ngay like '%" + ngay +"%' group by tblphannhom.tennhom\n";

        SQLiteDatabase database = this.getWritableDatabase();

        Cursor cursor = database.rawQuery(squery, null);
        Model_Thongke thongke = new Model_Thongke();
        if (cursor.moveToFirst() != true){
            list.add(new Model_Thongke(0, "Chưa có nhóm", "Chưa có khoản"));
        } if (cursor != null && cursor.moveToFirst()) {
            do {
                thongke = new Model_Thongke(cursor.getInt(cursor.getColumnIndex("TotalMoney")),
                        cursor.getString(cursor.getColumnIndex("tennhom")),
                        cursor.getString(cursor.getColumnIndex("tenkhoan")));
                list.add(thongke);
            }while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }


    //Get ngay trong thu chi
    public ArrayList<String> getNgay() {
        ArrayList<String> arr = new ArrayList<String>();
        SQLiteDatabase database = this.getReadableDatabase();
        String query = "select ngay from tblthuchi group by ngay";
        Cursor cursor = database.rawQuery(query, null);
        if (cursor.moveToFirst() != true) {
            arr.add("Chưa Có Giao Dịch");
        } else {
            while (cursor.isAfterLast() == false) {
                arr.add(cursor.getString(cursor.getColumnIndex("ngay")));
                cursor.moveToNext();
            }
        }
        return arr;
    }

    /**
     * TABLE THUCHI
     * END
     **/

}
