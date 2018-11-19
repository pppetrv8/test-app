package com.example.pppetrv.testapplication.store.db.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.example.pppetrv.testapplication.store.db.room.entity.BankDepartmentEntity
import io.reactivex.Flowable

@Dao
interface BankDepartmentDao {

    @Query("select * from bank_department")
    abstract fun getBankDepartments(): Flowable<List<BankDepartmentEntity>>

    @Query("select * from bank_department WHERE bank_id = :bankId")
    abstract fun getBankDepartment(bankId: Int): Flowable<BankDepartmentEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBankDepartment(bank: BankDepartmentEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBankDepartments(card: List<BankDepartmentEntity>)

}