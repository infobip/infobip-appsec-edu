var express = require('express');
var router = express.Router();
const EmailHelper= require('../helpers/EmailHelper');

router.post('/', function(req, res, next) {
    var name = req.body.name;
    //var sanitize = name.replace(/[{()}]/g, '')
    if (name) {
        return EmailHelper.sendEmail(name)
        .then(data => {
            return res.send(data)
        })
        .catch(err => {
            return res.send(err)});
    }
});

module.exports = router;