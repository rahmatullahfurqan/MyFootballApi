package com.example.rahma.myfootballapi.api

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.rahma.myfootballapi.model.Favorite
import com.example.rahma.myfootballapi.model.FavoriteTeam
import org.jetbrains.anko.db.*

class myDatabaseTeamHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoriteTeamLeague.db", null, 1) {
    companion object {
        private var instance: myDatabaseTeamHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): myDatabaseTeamHelper {
            if (instance == null) {
                instance = myDatabaseTeamHelper(ctx.applicationContext)
            }
            return instance as myDatabaseTeamHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteTeam.TABLE_FAVORITE_TEAM, true,
                FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.ID_TEAM to TEXT,
                FavoriteTeam.STR_TEAM to TEXT,
                FavoriteTeam.STR_TEAM_BADGE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

val Context.databaseTeam: myDatabaseTeamHelper
    get() = myDatabaseTeamHelper.getInstance(applicationContext)