const nodemailer = require('nodemailer');
const nunjucks = require('nunjucks');
require('dotenv').config();

module.exports= {
async sendEmail(emailAddress) {

    return new Promise(async(resolve, reject) => {
     try {
         let message= { to: emailAddress, 
                        subject: "Subscription to meowsec!",
     };

        message.html = nunjucks.renderString(`<p><b>Hello</b> <i> ${emailAddress}</i></p>`);
        let transporter = nodemailer.createTransport({
            host: 'smtp.gmail.com',
            port: 465,
            secure: true,
            auth: {user:process.env.GMAIL_USER, pass:process.env.GMAIL_PASS},
            logger: true
        });
        transporter.sendMail(message);
        transporter.close();
        resolve({ response:'Email has been sent' });
     }
     catch(e) {
         reject({ response:"Something went wrong" });
     }
    });

}




}
