package com.example.myapplication.loans.domain.model

sealed interface LoanResult {
    class Success(val data: Credit, val status: String) : LoanResult
    class Error(val error: String, val status: String) : LoanResult
}