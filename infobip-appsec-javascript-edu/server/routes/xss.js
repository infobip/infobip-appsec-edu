var express = require('express');
var router = express.Router();

router.post('/', function(req, res, next) {
    var name = req.body.name;
    res.json({ value:req.body.name });
});

module.exports = router;