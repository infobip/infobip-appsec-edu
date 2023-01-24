var express = require('express');
var router = express.Router();
var fs = require('fs');
var path = require('path');

router.post('/', function(req, res, next) {
    var name = req.body.name;
    var rootDirectory = './public/';
    var filename = path.join(rootDirectory,name);
    var resolved = path.resolve(filename);

    if (resolved.startsWith(path.resolve(rootDirectory))) {

    fs.readFile(filename, "UTF-8", function (err, data) {
        
        if (err) {
            res.json({ value:"File does not exist!" });
        }
        else {
        res.json({ value:data });
        }
    
    });
    } 
    else {
    res.json({ value:"Nice try!" });

}
});

module.exports = router;