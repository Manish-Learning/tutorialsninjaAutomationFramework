package tutorialsninja.tests;

import java.util.Properties;

import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import tutorialsninja.base.Base;
import utils.CommonUtils;

public class verifyGmailEmailDetailsTC_RF_002 extends Base{

	WebDriver driver;
	
	@AfterMethod
	public void teatDown()
	{
		if(driver!=null)
		{
		driver.quit();
		}
	}
	
	@Test
	public void verifyGmailEmailDetails() throws InterruptedException {
		
		driver = openBrowserToAutomateEmailFlow();
		String email = "kumarmanish1509@gmail.com";
		String gmailAppPassCode = "jwhx eban xmuy aqok";
		String link = null;
		
		driver.get("https://amazon.in");
		driver.findElement(By.xpath("//span[text()='Hello, sign in']")).click();
		driver.findElement(By.id("ap_email_login")).clear();
		driver.findElement(By.id("ap_email_login")).sendKeys(email);
		driver.findElement(By.xpath("//span[@id='continue']")).click();
		
		driver.findElement(By.linkText("Forgot password?")).click();
		driver.findElement(By.id("ap_email")).clear();
		driver.findElement(By.id("ap_email")).sendKeys(email);
		driver.findElement(By.id("continue")).click();
		
		driver.findElement(By.id("ap_zip")).sendKeys("800001");
		
		driver.findElement(By.id("continue")).click();
		
		System.out.println("Holding the program for 10 sec");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		  ChatGpt command : I want to make my java program access my gmail account using the app password
		  and email, then what is the code I need to write for reading the subject of the email. retrieving the from 
		  email address and the body content of the email.
		 * 
		 */
		//MVN Repo link to add dependency : https://mvnrepository.com/artifact/com.sun.mail/javax.mail
		
		// Gmail IMAP configuration
        String host = "imap.gmail.com";
        String port = "993";
        String username = email; // Your Gmail address
        String appPassword = gmailAppPassCode; // Your app password
        String expectedSubject = "amazon.in: Password recovery";
        String expectedFromEmail = "\"amazon.in\" <account-update@amazon.in>";
        String expectedBodyContent = "Someone is attempting to reset the password of your account.";

        try {
            // Mail server connection properties
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imap.host", host);
            properties.put("mail.imap.port", port);
            properties.put("mail.imap.ssl.enable", "true");
           
            // Connect to the mail server
            Session emailSession = Session.getDefaultInstance(properties);
            Store store = emailSession.getStore("imaps");
            store.connect(host, username, appPassword); // replace email password with App password
            
            // Open the inbox folder
            Folder inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_ONLY);
            
            // Search for unread emails
            Message[] messages = inbox.search(new FlagTerm(new Flags(Flags.Flag.SEEN), false));
            
            boolean found = false;
            for(int i = messages.length - 1; i >= 0; i--) {
            	
            	Message message = messages[i];
            
                if (message.getSubject().contains(expectedSubject)) {
                    found = true; 
                   /* 
                    System.out.println("Email Subject :" + message.getSubject());
                    System.out.println("Email From : " + message.getFrom()[0].toString());
                    System.out.println("Email Body : " + getTextFromMessage(message));
                   */ 
                    Assert.assertEquals(message.getSubject(),expectedSubject);
                    Assert.assertEquals(message.getFrom()[0].toString(), expectedFromEmail);
                    String actualEmailBody = CommonUtils. getTextFromMessage(message);
                    Assert.assertTrue(actualEmailBody.contains(expectedBodyContent));
                    
                    String[] ar = actualEmailBody.split("600\">");
                    String linkPart = ar[1];
                    String[] arr = linkPart.split("</a>");
     
                    link = arr[0].trim();
                    System.out.println("Link in Email : " + link);
                    
                    break;
                }
            }

            if (!found) {
                System.out.println("No confirmation email found.");
            }

            // Close the store and folder objects
            inbox.close(false);
            store.close();

        }catch(Exception e) {
        	e.printStackTrace();
        }
        
        driver.navigate().to(link);
        
        Assert.assertTrue(driver.findElement(By.name("customerResponseDenyButton")).isDisplayed());
       
	}
	}


