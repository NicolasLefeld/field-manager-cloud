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
  try {
    const { email, password } = req.body;

    const rs = await userAndPasswordCreate(email, password);

    res.status(200).json(rs);
  } catch (error) {
    res.status(500).json("Error while creating the user");
  }
});

router.post("/signin", async (req, res) => {
  try {
    const { email, password } = req.body;

    const rs = await userAndPasswordLogin(email, password);

    res.status(200).json(rs);
  } catch (error) {
    res.status(500).json("Error while login");
  }
});

router.post("/google/signin", async (req, res) => {
  const { email } = req.body;

  const status = await googleLogin(email);

  res.status(status);
});

module.exports = router;
