package com.test.zoom.controller;

import org.springframework.scheduling.annotation.Scheduled;

public class ScheduleController {

    @Scheduled(cron = "0 0 0 * * *")  // 매일 자정에 실행 (cron 표현식)
    public void updateOverdueDatesForAllLoans() {
//        List<Loan> loans = loanRepository.findAll();
//        for (Loan loan : loans) {
//            loan.calculateOverdueDate(); // 연체일 계산
//            loanRepository.save(loan);  // 연체일을 갱신하여 저장
//            loanRepository.save(loan);  // 대출상태를 갱신하여 저장
//        }
    }
}
