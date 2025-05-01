package com.example.medicationapp.controller

import android.content.Context
import com.example.medicationapp.database.AppDatabase
import com.example.medicationapp.model.Report

class ReportController(context: Context) {
    private val reportDao = AppDatabase.getDatabase(context).reportDao()

    suspend fun generateReport(report: Report) {
        reportDao.insertReport(report)
    }

    suspend fun viewReports(managerId: Int): List<Report> {
        return reportDao.getReportsByManager(managerId)
    }
}
