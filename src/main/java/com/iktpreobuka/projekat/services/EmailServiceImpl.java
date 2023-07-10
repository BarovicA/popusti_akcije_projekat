package com.iktpreobuka.projekat.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.projekat.entities.VoucherEntity;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;

    public void EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    } 
    
    	@Override
        public void sendVoucherEmail(String userEmail, VoucherEntity voucher) {
            try {
                MimeMessage message = emailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setTo(userEmail);
                helper.setSubject("Your Voucher");
                String voucherTable = createVoucherTableHtml(voucher);
                helper.setText(voucherTable, true);
                emailSender.send(message);
            } catch (MessagingException e) {
            	e.printStackTrace();
                throw new RuntimeException("An error occurred while sending the email", e);
            }
        }
        
    	@Override
        public String createVoucherTableHtml(VoucherEntity voucher) {
            StringBuilder sb = new StringBuilder();
            sb.append("<!DOCTYPE html><html><head>");
            sb.append("<title>Voucher</title>");
            sb.append("<style>table { border:4px double #161601; border-collapse:collapse; padding:10px;}");
            sb.append("table th { border:4px double #161601; padding:10px; background: #f0f0f0; color: #313030;}");
            sb.append("table td { border:4px double #161601; text-align:center; padding:10px; background: #ffffff; color: #313030;}</style></head>");
            sb.append("<body><table><thead><tr>");
            sb.append("<th>Buyer</th>");
            sb.append("<th>Offer</th>");
            sb.append("<th>Price</th>");
            sb.append("<th>Expiries date</th>");
            sb.append("</tr></thead><tbody><tr>");
            sb.append("<td>" + voucher.getUser().getFirstName() + " " + voucher.getUser().getLastName() + "</td>");
            sb.append("<td>" + voucher.getOffer().getOfferName() + "</td>");
            sb.append("<td>" + voucher.getOffer().getActionPrice() + "</td>");
            sb.append("<td>" + voucher.getExpirationDate()+ "</td>");
            sb.append("</tr></tbody></table></body></html>");
            return sb.toString();
        }    
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
}
