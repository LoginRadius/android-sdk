package com.loginradius.sdk.util;

import com.github.sendgrid.SendGrid;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Sends direct messages through Google/Yahoo
 *
 */
public class MailSender {
	private String user;
	private String password;

	private static MailSender instance;
	/**
	 * Used to get the current instance of Mailsender class
	 * @param c Application context
	 * @return
	 */
	
	public static MailSender getInstance(Context c) {
		if (instance == null)
			instance = new MailSender(c);
		return instance;
	}

	private MailSender(Context c) {
		this.user = c.getString(com.loginradius.sdk.R.string.smtp_user);
		this.password = c.getString(com.loginradius.sdk.R.string.smtp_password);	
	}

	/**
	 * Send mail to recipients
	 * @param subject String subject text
	 * @param body String body text
	 * @param sender Email address of sender
	 * @param recipients CSV email addresses of recipients
	 * @param asyncHandler callback handler
	 */
	public synchronized void sendMail(String subject, String body, String sender, String recipients,
			AsyncHandler<Boolean> asyncHandler)  {

		SendGrid sendgrid = new SendGrid(user, password);
		
		recipients = recipients.replace(" ", "");
		for (String rec : recipients.split(","))
			sendgrid.addTo(rec);
		
		sendgrid.setFrom(sender);
		sendgrid.setSubject(subject);
		sendgrid.setText(body);
		
		SendAsync(sendgrid, asyncHandler);
	}

	private void SendAsync(SendGrid sendgrid, final AsyncHandler<Boolean> asyncHandler) {

		new SendEmailTask() {
			protected void onPostExecute(Boolean result) {
				if (!result) asyncHandler.onFailure(new Throwable("Send Mail failed"), "lr_MAIL_ERROR");
				else asyncHandler.onSuccess(true);
			}
		}.execute(sendgrid);		
	}

	private class SendEmailTask extends AsyncTask<SendGrid, Void, Boolean> {
		@Override
		protected Boolean doInBackground(SendGrid... sendgrids) {
			if (sendgrids.length == 0 || sendgrids[0] == null) return false;

			sendgrids[0].send();
			return true;


		}
	}

}
