const router = require("express").Router();
const { activate } = require("./controller");

router.use(function (req, res, next) {
  res.header(
    "Access-Control-Allow-Headers",
    "x-access-token, Origin, Content-Type, Accept"
  );
  next();
});

router.post("/", async (req, res) => {
  const ip = req.headers["x-forwarded-for"];
  
  const { status, body } = await activate(ip, req.query);

  res.status(status).json(body);
});

module.exports = router;
