package com.utd.edu.cs4389.cometBank.controller;

import com.utd.edu.cs4389.cometBank.service.ActionsService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/bankingApp/actions")
public class ActionsController {
    @Autowired
    private ActionsService actionsService;

    public ActionsController(ActionsService actionsService) {
        this.actionsService = actionsService;
    }
    public ActionsController(){}

    @PutMapping("/withdraw/checking")
    public void withdrawFromChecking(Double amt, HttpSession session) {
        actionsService.withdrawFromChecking(amt, session);
    }

    @PutMapping("/deposit/checking")
    public void depositToChecking(Double amt, HttpSession session) {
        actionsService.depositToChecking(amt, session);
    }

    @PutMapping("/withdraw/savings")
    public void withdrawFromSavings(Double amt, HttpSession session) {
        actionsService.withdrawFromSaving(amt, session);
    }

    @PutMapping("/deposit/savings")
    public void depositToSavings(Double amt, HttpSession session) {
        actionsService.depositToSaving(amt, session);
    }

    @PutMapping("/checkingTo/savings")
    public void checkingToSavingsTransfer(Double amt, HttpSession session) {
        actionsService.checkingToSavingTransfer(amt, session);
    }

    @PutMapping("savingsTo/checking")
    public void savingsToCheckingTransfer(Double amt, HttpSession session) {
        actionsService.savingToCheckingTransfer(amt, session);
    }
}
