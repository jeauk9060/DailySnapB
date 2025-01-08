package com.dailysnap.summation.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.dailysnap.summation.dao.ArticleNewsDAO;
import com.dailysnap.summation.model.ArticleNewsVO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final ArticleNewsDAO articleNewsDAO; // 뉴스 DAO 추가

    // 생성자
    public EmailService(JavaMailSender mailSender, ArticleNewsDAO articleNewsDAO) {
        this.mailSender = mailSender;
        this.articleNewsDAO = articleNewsDAO;
    }
    // 이메일 전송 메서드 (오늘 날짜 뉴스 조회 및 전송)
    public String sendTodayNewsEmail(String toEmail) {
        try {
            // 오늘 날짜 기준 뉴스 조회
            String today = LocalDate.now().toString();
            List<ArticleNewsVO> articles = articleNewsDAO.findArticlesByDate(LocalDate.parse(today));

            // 뉴스 데이터 검증
            if (articles == null || articles.isEmpty()) {
                return "오늘 날짜의 뉴스 데이터가 없습니다.";
            }

            // HTML 본문 생성
            StringBuilder htmlContent = new StringBuilder("<h1>오늘의 뉴스</h1>");
            for (ArticleNewsVO article : articles) {
                htmlContent.append("<div style='margin-bottom:20px;'>")
                            .append("<h3>").append(article.getTitle()).append("</h3>")
                            .append("<p>").append(article.getSourceName()).append("</p>")
                            .append("<a href='").append(article.getLink()).append("'>기사 보기</a><br>")
                            .append("<img src='").append(article.getThumbnail()).append("' style='width:150px;height:auto;'><br>")
                            .append("</div>");
            }

            // 이메일 전송
            sendHtmlEmail(toEmail, "오늘의 뉴스 요약", htmlContent.toString());
            return "이메일 전송 성공!";
        } catch (MessagingException e) {
            e.printStackTrace();
            return "이메일 전송 실패: " + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "오류 발생: " + e.getMessage();
        }
    }

    // HTML 형식 이메일 전송
    public void sendHtmlEmail(String toEmail, String subject, String htmlContent) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setFrom("km802694@gmail.com"); // 발신자 이메일
        helper.setTo("skrktn7@naver.com"); // 수신자 이메일
        helper.setSubject("오늘의 뉴스");
        helper.setText(htmlContent, true);

        mailSender.send(message);
        System.out.println("이메일 전송 완료!");
    }

	public void sendHtmlEmail(String email, String subject, List<ArticleNewsVO> articles) {
		// TODO Auto-generated method stub
		
	}

}
