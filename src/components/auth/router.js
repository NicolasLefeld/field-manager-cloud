const router = require("express").Router();
const {
  userAndPasswordCreate,
  userAndPasswordLogin,
  googleLogin,
} = require("./controller");

router.use(function (req, res, next) {
  res.header(
    "Access-Control-Allow-Headers",
    "x-access-token, Origin, Content-Type, Accept"
  );
  next();
});

router.post("/signup", async (req, res) => {
  const { email, password } = req.body;

  const { status, body } = await userAndPasswordCreate(email, password);

  res.status(status).json(body);
});

router.post("/signin", async (req, res) => {
  const { email, password } = req.body;

  const { status, body } = await userAndPasswordLogin(email, password);

  res.status(status).json(body);
});

router.post("/google/signup", async (req, res) => {
  const { status, body } = await googleLogin();

  res.status(status).json(body);
});

module.exports = router;
