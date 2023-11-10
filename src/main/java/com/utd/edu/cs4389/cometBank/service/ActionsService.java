package com.utd.edu.cs4389.cometBank.service;

import jakarta.servlet.http.HttpSession;

public interface ActionsService {
    void withdrawFromChecking(Double amt, HttpSession session);

    void depositToChecking(Double amt, HttpSession session);

    void withdrawFromSaving(Double amt, HttpSession session);

    void depositToSaving(Double amt, HttpSession session);

    void checkingToSavingTransfer(Double amt, HttpSession session);

    void savingToCheckingTransfer(Double amt, HttpSession session);
}
