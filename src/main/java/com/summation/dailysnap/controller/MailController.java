package com.summation.dailysnap.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

import java.util.List;

import com.summation.dailysnap.service.MailService;
import com.summation.dailysnap.model.MailVO;

@RestController
@RequestMapping("/api")
@CrossOrigin
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;

    // 전체 사용자 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<MailVO>> getMailList() {
        List<MailVO> mailList = mailService.getMailList();
        return ResponseEntity.ok(mailList);
    }

    // ID로 사용자 조회
    @GetMapping("/{id}")
    public ResponseEntity<MailVO> getMailById(@PathVariable Long id) {
        MailVO mail = mailService.getMailById(id);
        if (mail != null) {
            return ResponseEntity.ok(mail);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 사용자 저장
    @PostMapping("/save")
    public ResponseEntity<String> saveMail(@RequestBody MailVO mailVO) {
        mailService.saveMail(mailVO);
        return ResponseEntity.ok("Mail saved successfully!");
    }

    // 사용자 정보 업데이트
    @PutMapping("/update")
    public ResponseEntity<String> updateMail(@RequestBody MailVO mailVO) {
        int updated = mailService.updateMail(mailVO);
        if (updated > 0) {
            return ResponseEntity.ok("Mail updated successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ID로 사용자 삭제
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteMailById(@PathVariable Long id) {
        int deleted = mailService.deleteMailById(id);
        if (deleted > 0) {
            return ResponseEntity.ok("Mail deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
