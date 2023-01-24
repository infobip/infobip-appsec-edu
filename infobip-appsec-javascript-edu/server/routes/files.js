var express = require('express');
var router = express.Router();
var fs = require('fs');

router.post('/', function(req, res, next) {
    var name = req.body.name;
    fs.readFile(`./public/${name}`, "UTF-8", function (err, data) {
        if (err) {
            res.json({ value:"File does not exist!" });
        }
        else {
        res.json({ value:data });
    }
      });
    
});

module.exports = router;