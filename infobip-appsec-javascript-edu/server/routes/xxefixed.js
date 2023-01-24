var express = require('express');
var router = express.Router();
var libxml = require("libxmljs");
var multer = require('multer');
const app = require('../app');
const storage = multer.memoryStorage();
const upload = multer({ storage: storage });


router.post('/', upload.single('file'), async function(req, res, next) {

  try {
    const xml = req.file.buffer.toString("utf-8");
    const doc = libxml.parseXmlString(xml, { noent: false });
    res.status(200).send(doc.toString());
    
} catch (err) {
    res.send(err.toString());
    res.sendStatus(500);
}
});

module.exports = router;